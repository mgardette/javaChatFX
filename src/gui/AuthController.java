package gui;

import client.Client;
import client.ClientPanel;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

public class AuthController {

	//Athentification
    @FXML
    private Button connect_button;

    @FXML
    private PasswordField pass_input;

    @FXML
    private TextField pseudo_input;
    
    //Creation de compte
    @FXML
    private Button cancel_button;

    @FXML
    private PasswordField confirm_pass;

    @FXML
    private Button create_button;

    @FXML
    private TextField mail_input;

    @FXML
    private PasswordField new_pass;

    @FXML
    private TextField new_username;
    
    public void se_connecter() {
    	
    }
    
    public void creer_compte() {
    	
    }
    
}
