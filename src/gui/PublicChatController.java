package gui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import client.Client;
import common.Message;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import server.Server;
import server.ConnectedClient;

public class PublicChatController {

	private Stage currentWindow;
	private Client client;
	private Server server;
	private boolean isServer = false;
	private LocalDateTime whenConnected;
	private Alert alert = new Alert(AlertType.NONE);

    @FXML
    private Button clearTextButton;

    @FXML
    private Label connectedUsersLabel;

    @FXML
    private Button sendTextButton;

    @FXML
    private ScrollPane sideBar;

    @FXML
    private TextArea textToSend;

    @FXML
    private TextFlow textToShow;

    @FXML
    private TextFlow listToShow;
    
    @FXML
    private MenuItem saveConvoMenu;
    
    public void initialize(Stage currentwindow, Client client, Server server) {
    	this.currentWindow = currentwindow;
    	this.client = client;
    	this.whenConnected = LocalDateTime.now();
    	currentwindow.setOnCloseRequest( event -> {close();} );
    	if(server != null) {
        	this.server = server;
        	this.isServer = true;
    	}
    	String clients;
    	do {
    		clients = this.client.getListClients();
    	} while(clients == null);
        String[] listClients = clients.split(";");
      for(String clientToShow : listClients) {
        if(clientToShow != "") {
          clientToShow = clientToShow + "\n";
            Text text = new Text(clientToShow);
          listToShow.getChildren().add(text);
        }
      }
    }
    
    public void sendText() {
    	if(!textToSend.getText().trim().isEmpty()) {
			Message mess = new Message(client.getPseudo(), textToSend.getText());
			printNewMessage(mess);
			clearText();
			try {
				client.getOut().writeObject(mess);
				client.getOut().flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
    }
    
    public void clearText() {
    	textToSend.clear();
    }
    
    public void printNewMessage(Message mess) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				Text text = new Text("\n" + mess.toString());
				//text.prefWidth(text.get - 20);
				textToShow.getChildren().add(text);
			}
		});
	}
    

    public void close() {
    	if(isServer) {
    		server.broadcastMessage(new Message("Server", "Shut Down."));
    		server.closeServer();
    	}
    	else {
    		try {
				client.disconnect();
			} catch (IOException e) {
				e.printStackTrace();
			}
    	}
    }
    
    public void disableSend() {
    	sendTextButton.setDisable(true);
    }
    
    public void printClientsList() {  
    	String clients = this.client.getListClients();
        String[] listClients = clients.split(";");
    	Platform.runLater(new Runnable() {
			@Override
			public void run() {
				listToShow.getChildren().clear();
				for(String client : listClients) {
					if(client != "") {
						client = client + "\n";
				    	Text text = new Text(client);
						listToShow.getChildren().add(text);
					}
				}
				//text.prefWidth(receivedText.getPrefWidth() - 20);
				}
		});
    }

    public void saveConversation() {
    	StringBuilder sb = new StringBuilder();
    	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm, dd/MM/yyyy");
    	
    	sb.append(client.getPseudo() + " s'est connecté à " + dtf.format(whenConnected) + "\n");
    	for (Node node : textToShow.getChildren()) {
    	    if (node instanceof Text) {
    	        sb.append(((Text) node).getText());
    	    }
    	}
    	createFile(sb.toString());
    }
    
    private void createFile(String text) {
    	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy_HH-mm-ss");
    	String filePath = "C:\\Users\\" + System.getProperty("user.name") + "\\DevOpsChat\\Logs\\log_" + dtf.format(LocalDateTime.now()) + ".txt";
		try {
			FileWriter fileOut = new FileWriter(filePath);
			fileOut.write(text);
			fileOut.close();
			alert.setAlertType(AlertType.INFORMATION);
			alert.setHeaderText("File successfully created !");
			alert.setContentText("Location :\n" + filePath);
			alert.show();
		} catch (IOException e) {
			alert.setAlertType(AlertType.ERROR);
			alert.setHeaderText("Oops..");
			alert.setContentText("Couldn't save conversation.");
			alert.show();
		}
    }
}
