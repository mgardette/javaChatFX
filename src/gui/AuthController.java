package gui;

import java.io.IOException;
import java.net.UnknownHostException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import client.Client;
import common.DB;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AuthController {
	
	private Stage currentWindow;

    @FXML
    private Button connect_button;

    @FXML
    private PasswordField pass_input;

    @FXML
    private TextField pseudo_input;
    
    @FXML
    private Label input_error;
    
    public void initialize(Stage currentwindow) {
		this.currentWindow = currentwindow;
		currentWindow.setResizable(false);
	}
    
    public void se_connecter() throws SQLException, IOException {
    	Statement stm = DB.getConnection().createStatement();
		ResultSet rs = stm.executeQuery("SELECT pseudo FROM utilisateur WHERE pseudo = '" + pseudo_input.getText() + "' AND mdp = '" + pass_input.getText() +"'");
    	if(rs.next()) {
    		currentWindow.close();
    		Stage stage = new Stage();
    		FXMLLoader loader = new FXMLLoader();
    		loader.setLocation(this.getClass().getResource("MainMenu.fxml"));
    		
    		Parent root = loader.load();
    		MainMenuController ctlr = loader.getController();
    		ctlr.initialize(stage, pseudo_input.getText());
    		stage.setScene(new Scene(root));
    		stage.show();
    	}
    	else {
    		input_error.setVisible(true);
    		pass_input.clear();
    	}
    	rs.close();
    	stm.close();
    }
    
    public void nouveau_compte() throws IOException {
    	Stage stage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(this.getClass().getResource("/gui/NewAccount.fxml"));
		
		Parent root = loader.load();
		NewAccountController ctlr = loader.getController();
		ctlr.initialize(stage);
		stage.setScene(new Scene(root));
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.showAndWait();
    }
    
}
