package gui;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import client.Client;
import common.Message;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Modality;
import javafx.stage.Stage;
import server.Server;

/**
 * @author Mathieu GARDETTE
 * @author Noah COUPEY
 *
 */
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
    private Button sendTextButton;

    @FXML
    private ScrollPane sideBar;

    @FXML
    private TextArea textToSend;

    @FXML
    private TextFlow textToShow;

    @FXML
    private ListView<String> listToShow;
    
    @FXML
    private MenuItem saveConvoMenu;
    
    /**
     * Constructeur de la classe publicChatControlleur
     * Initialisation du client, de la fen?tre et le lien avec le serveur.
     * V?rification de si la fen?tre se ferme
     * Initialisation de la liste des diff?rents clients connect?s.
     * @param currentwindow
     * @param client
     * @param server
     */
    public void initialize(Stage currentwindow, Client client, Server server) {
    	this.currentWindow = currentwindow;
    	this.client = client;
    	this.whenConnected = LocalDateTime.now();
    	ListView<String> listToShow = new ListView<String>(); 
    	currentwindow.setOnCloseRequest( event -> {close();} );
    	if(server != null) {
        	this.server = server;
        	this.isServer = true;
        	alert.setAlertType(AlertType.INFORMATION);
			alert.setHeaderText("Server successfully created !");
			alert.setContentText("Port : " + server.getPort() + "\nAddress : " + server.getAddress());
			alert.showAndWait();
    	}
    	String clients;
    	do {
    		clients = this.client.getListClients();
    	} while(clients == null);
        String[] listClients = clients.split(";");
        //ObservableList<String> items = FXCollections.observableArrayList (client.getPseudo());
    	//listToShow.setItems(items);
        for(String clientToShow : listClients) {
	        if(clientToShow != ""/*&& !client.getPseudo().equals(clientToShow)*/) {
	        	listToShow.getItems().add(clientToShow);
	        }
      }
    }
    
    /**
     * M?thode permettant l'envoie de message.
     */
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
    
    /**
     * Nettoie le champ d'envoie de texte.
     */
    public void clearText() {
    	textToSend.clear();
    }
    
    /**
     * M?thode permettant d'afficher le profil d'un utilisateur choisi dans la liste.
     * @param event
     * @throws IOException
     */
    @FXML
    public void getProfile(MouseEvent event) throws IOException {
		String clientToShow = listToShow.getSelectionModel().getSelectedItem();
    	Stage stage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		
		loader.setLocation(this.getClass().getResource("/gui/Profile.fxml"));
		Parent root = loader.load();
		ProfileController prfl = loader.getController();
		prfl.initialize(stage, clientToShow, client, server);
		stage.setScene(new Scene(root));
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.showAndWait();
    }
    
    /**
     * Affichage d'un message envoy? par un utilisateur
     * @param mess
     */
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
    
    /**
     * M?thode concernant la fermeture de la session
     */
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
    
    /**
     * M?thode permettant de d?sactiv? l'envoie du texte
     */
    public void disableSend() {
    	sendTextButton.setDisable(true);
    }
    
    /**
     * M?thode permettant de rafra?chir ? chaque nouvelle connexion la liste d'utilisateurs
     */
    public void printClientsList() {
    	String clients = this.client.getListClients();
        String[] listClients = clients.split(";");
    	Platform.runLater(new Runnable() {
			@Override
			public void run() {
				listToShow.getItems().clear();
				for(String clientToShow : listClients) {
					if(clientToShow != "") {
			        	listToShow.getItems().add(clientToShow);
					}
				}
				//text.prefWidth(receivedText.getPrefWidth() - 20);
				}
		});
    }
    
    /**
     * M?thode permettant la sauvegarde de la conversation
     */
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
    
    /**
     * M?thode prenant en param?tre le text (String) voulant ?tre sauvegard?
     * @param text
     */
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
    
    /**
     * M?thode g?n?rant le "? propos"
     */
    public void aProposClicked() {
    	alert.setAlertType(AlertType.INFORMATION);
		alert.setHeaderText("A propos");
		alert.setContentText("Application cr??e par Noah COUPEY et Mathieu GARDETTE.");
		alert.showAndWait();
    }
}
