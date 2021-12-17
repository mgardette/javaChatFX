package client;

import java.io.IOException;

import common.Message;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class ClientPanel extends Parent {

	private TextArea textToSend;
	private ScrollPane scrollReceivedText;
	private TextFlow receivedText;
	private Button sendBtn;
	private Button clearBtn;
	private Client client;
	
	public ClientPanel() {
		super();
		this.textToSend = new TextArea();
		textToSend.setLayoutX(50);
		textToSend.setLayoutY(400);
		textToSend.setPrefWidth(300);
		textToSend.setPrefHeight(75);
		
		this.receivedText = new TextFlow();
		receivedText.setPrefWidth(400);
		receivedText.setPrefHeight(300);
		receivedText.setVisible(true);
		
		this.scrollReceivedText = new ScrollPane();
		scrollReceivedText.setLayoutX(50);
		scrollReceivedText.setLayoutY(50);
		scrollReceivedText.setPrefWidth(400);
		scrollReceivedText.setPrefHeight(300);
		scrollReceivedText.setContent(receivedText);
		scrollReceivedText.vvalueProperty().bind(receivedText.heightProperty());
		
		this.sendBtn = new Button();
		sendBtn.setLayoutX(375);
		sendBtn.setLayoutY(400);
		sendBtn.setPrefWidth(75);
		sendBtn.setPrefHeight(25);
		sendBtn.setText("Send");
		sendBtn.setStyle("-fx-background-color: #4cd137; -fx-text-fill: #ffffff; -fx-font-weight: bold;");
		sendBtn.setOnMouseEntered(e -> sendBtn.setStyle("-fx-background-color: #44bd32; -fx-text-fill: #ffffff; -fx-font-weight: bold;"));
		sendBtn.setOnMouseExited(e -> sendBtn.setStyle("-fx-background-color: #4cd137; -fx-text-fill: #ffffff; -fx-font-weight: bold;"));
		sendBtn.setVisible(true);
		sendBtn.setOnAction(new EventHandler<ActionEvent>() {
			 @Override
			 public void handle(ActionEvent event) {
				if(!textToSend.getText().equals("")) {
					Message mess = new Message("user", textToSend.getText());
					printNewMessage(mess);
					textToSend.setText("");
					try {
						client.getOut().writeObject(mess);
						client.getOut().flush();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			 }
		});
		
		this.clearBtn = new Button();
		clearBtn.setLayoutX(375);
		clearBtn.setLayoutY(450);
		clearBtn.setPrefWidth(75);
		clearBtn.setPrefHeight(25);
		clearBtn.setText("Clear");
		clearBtn.setStyle("-fx-background-color: #e84118; -fx-text-fill: #ffffff; -fx-font-weight: bold;");
		clearBtn.setVisible(true);
		clearBtn.setOnAction(new EventHandler<ActionEvent>() {
			 @Override
			 public void handle(ActionEvent event) {
				 textToSend.setText(null);
			 }
		});
		
		this.getChildren().add(textToSend);
		this.getChildren().add(scrollReceivedText);
		this.getChildren().add(sendBtn);
		this.getChildren().add(clearBtn);
	}
	
	public void printNewMessage(Message mess) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				Text text = new Text("\n" + mess.toString());
				//text.prefWidth(receivedText.getPrefWidth() - 20);
				receivedText.getChildren().add(text);
			}
		});
	}

	public void setClient(Client client) {
		this.client = client;
	}

	
	
}
