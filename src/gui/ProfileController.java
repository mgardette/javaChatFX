package gui;

import client.Client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class ProfileController {

    @FXML
    private Button btnPlay;

    @FXML
    private Button btnPrvChat;

    @FXML
    private Button cancelBtn;

    @FXML
    private Label histoFour;

    @FXML
    private Label histoOne;

    @FXML
    private Label histoThree;

    @FXML
    private Label histoTwo;

    @FXML
    private Button modifBtn;

    @FXML
    private ImageView profilePicture;

    @FXML
    private Label rankingName;

    @FXML
    private ImageView rankingPicture;

    @FXML
    private Label usersProfile;

    @FXML
    private Button validateBtn;
    
    private Client client;
    
    private Client visiteur;
    
    private boolean profilePerso;
    
    public void initialize(Client client) {
    	this.profilePerso = false;
    	this.client = client;
    	usersProfile.setText(this.client.getPseudo());
    	
    	// Vérification de si le visiteur est l'utilisateur
    	if(this.visiteur.getPseudo().equals(this.client.getPseudo()))
		{
    		this.profilePerso = true;
    		this.modifBtn.setDisable(false);
    		this.modifBtn.setVisible(true);
		}
    	
    	// Procédure pour récupérer les derniers attributs
    	
	}
    
    public void setClient(Client client) {
		this.client = client;
	}

    @FXML
    void openPrivateChat(ActionEvent event) {

    }

    @FXML
    void startGame(ActionEvent event) {

    }

}
