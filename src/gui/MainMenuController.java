package gui;

import java.io.IOException;
import java.net.BindException;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.regex.Pattern;

import client.Client;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import server.Server;

public class MainMenuController {
	
	private static final Pattern ADDRESS = Pattern.compile("^(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");
	
	private static final Pattern PORT = Pattern.compile("^([0-9]{1,4}|[1-5][0-9]{4}|6[0-4][0-9]{3}|65[0-4][0-9]{2}|655[0-2][0-9]|6553[0-5])$");
	
	private Stage currentWindow;
	private String pseudo;

	@FXML
    private TextField addressInput;

    @FXML
    private Label hostLabel;

    @FXML
    private Label joinLabel;

    @FXML
    private TextField portInput;

    @FXML
    private Button startButton;
    
    @FXML
    private Label errorLabel;
    
    public void initialize(Stage currentwindow, String pseudo) {
		this.currentWindow = currentwindow;
		this.pseudo = pseudo;
	}

    @FXML
    void hostClicked() {
    	hostLabel.setStyle("-fx-background-color :  #5fd2ea");
    	hostLabel.setTextFill(Paint.valueOf("#1f1b2f"));
    	joinLabel.setStyle("-fx-background-color :  #1f1b2f");
    	joinLabel.setTextFill(Paint.valueOf("#ffffff"));
    	addressInput.setDisable(true);
    }

    @FXML
    void joinClicked() {
    	joinLabel.setStyle("-fx-background-color :  #5fd2ea");
    	joinLabel.setTextFill(Paint.valueOf("#1f1b2f"));
    	hostLabel.setStyle("-fx-background-color :  #1f1b2f");
    	hostLabel.setTextFill(Paint.valueOf("#ffffff"));
    	addressInput.setDisable(false);
    }

    @FXML
    void startButtonClicked() throws UnknownHostException {
    	if(PORT.matcher(portInput.getText()).matches() && addressInput.isDisabled()) {
    		try{
    			Server server = new Server(Integer.parseInt(portInput.getText()));
        		bootClient(InetAddress.getLocalHost().getHostAddress(), Integer.parseInt(portInput.getText()), server);
    		} catch (BindException e) {
    			errorLabel.setText("Port already in use.");
        		errorLabel.setVisible(true);
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
    	}
    	else if(ADDRESS.matcher(addressInput.getText()).matches()) {
    		bootClient(addressInput.getText(), Integer.parseInt(portInput.getText()), null);
    	}
    	else {
    		errorLabel.setText("Invalid port and/or address");
    		errorLabel.setVisible(true);
    	}
    }
    
    public void bootClient(String address, int port, Server server) {
    	try {
    		currentWindow.close();
    		Stage stage = new Stage();
    		FXMLLoader loader = new FXMLLoader();
    		loader.setLocation(this.getClass().getResource("/gui/PublicChat.fxml"));
    		
    		Parent root = loader.load();
    		PublicChatController ctlr = loader.getController();
    		Client client = new Client(address, port, pseudo, ctlr);
    		ctlr.initialize(stage, client, server);
    		stage.setScene(new Scene(root));
    		stage.show();
		} catch (UnknownHostException e) {
			errorLabel.setText("Unknown host.");
			errorLabel.setVisible(true);
		} catch (ConnectException e) {
			errorLabel.setText("Unable to connect.");
			errorLabel.setVisible(true);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

}
