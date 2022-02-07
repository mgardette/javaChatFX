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

/**
 * @author Noah COUPEY
 *
 */
public class MainMenuController {
	
	/**
	 * Regex verifiant le format de l'adresse
	 */
	private static final Pattern ADDRESS = Pattern.compile("^(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");
	
	/**
	 * Regex vérifiant le format du port
	 */
	private static final Pattern PORT = Pattern.compile("^([0-9]{1,4}|[1-5][0-9]{4}|6[0-4][0-9]{3}|65[0-4][0-9]{2}|655[0-2][0-9]|6553[0-5])$");
	
	/**
	 * Stage actuel
	 */
	private Stage currentWindow;
	
	/**
	 * Pseudo de l'utilisateur
	 */
	private String pseudo;

	/**
	 * Champ de texte pour l'adresse du serveur
	 */
	@FXML
    private TextField addressInput;

    /**
     * Label permettant de sélectionner le menu Host
     */
    @FXML
    private Label hostLabel;

    /**
     * Label permettant de selectionner le menu Join
     */
    @FXML
    private Label joinLabel;

    /**
     * Champ de texte pour le port du serveur
     */
    @FXML
    private TextField portInput;

    /**
     * Bouton pour lancer et/ou rejoindre un serveur
     */
    @FXML
    private Button startButton;
    
    /**
     * Label qui indique l'erreur survenue
     */
    @FXML
    private Label errorLabel;
    
    
    /**
     * Permet d'initialiser certaines informations avant que la fenêtre s'ouvre
	 * @param currentWindow Stage actuel
     * @param pseudo Pseudo de l'utilisateur
     */
    public void initialize(Stage currentwindow, String pseudo) {
		this.currentWindow = currentwindow;
		this.pseudo = pseudo;
		currentWindow.setResizable(false);
	}

    /**
     * Permet de changer l'affichage lorsque le label Host est cliqué
     */
    @FXML
    void hostClicked() {
    	hostLabel.setStyle("-fx-background-color :  #5fd2ea");
    	hostLabel.setTextFill(Paint.valueOf("#1f1b2f"));
    	joinLabel.setStyle("-fx-background-color :  #1f1b2f");
    	joinLabel.setTextFill(Paint.valueOf("#ffffff"));
    	addressInput.setDisable(true);
    }

    /**
     * Permet de changer l'affichage lorsque le label Join est cliqué
     */
    @FXML
    void joinClicked() {
    	joinLabel.setStyle("-fx-background-color :  #5fd2ea");
    	joinLabel.setTextFill(Paint.valueOf("#1f1b2f"));
    	hostLabel.setStyle("-fx-background-color :  #1f1b2f");
    	hostLabel.setTextFill(Paint.valueOf("#ffffff"));
    	addressInput.setDisable(false);
    }

    
    /**
     * Lance le client (et serveur si Host est selectionné).
     * Affiche l'erreur si le client ou serveur n'a pas pu se lancer.
     * @throws UnknownHostException
     */
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
    
    
    /**
     * Lance la fenêtre du chat public et initialise le client
     * @param address Adresse du serveur
     * @param port Port du serveur
     * @param server Serveur créé
     */
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
