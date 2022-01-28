package gui;

import java.io.IOException;

import client.Client;
import common.Message;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

public class PublicChatController {

	private Stage currentWindow;
	private Client client;

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
    
    public void initialize(Stage currentwindow, Client client) {
    	this.currentWindow = currentwindow;
    	this.client = client;
    }
    
    public void sendText() {
    	if(!textToSend.getText().trim().isEmpty()) {
			Message mess = new Message("user", textToSend.getText());
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
				//text.prefWidth(receivedText.getPrefWidth() - 20);
				textToShow.getChildren().add(text);
			}
		});
	}
    
    public void setClient(Client client) {
		this.client = client;
	}

}
