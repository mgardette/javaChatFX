package gui;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import common.DB;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * @author Noah COUPEY
 *
 */
public class AuthController {
	
	/**
	 * Stage sur lequel le controleur influe
	 */
	private Stage currentWindow;

    /**
     * Bouton de connection
     */
    @FXML
    private Button connect_button;

    /**
     * Champ de texte pour le mot de passe
     */
    @FXML
    private PasswordField pass_input;

    /**
     * Champ de texte pour le pseudo
     */
    @FXML
    private TextField pseudo_input;
    
    /**
     * Message d'erreur s'affichant s'il y en a
     */
    @FXML
    private Label input_error;
    
    /**
     * Permet d'initialier certaines informations avant que la fenêtre s'ouvre
     * @param currentwindow Stage sur lequel le controleur influe
     */
    public void initialize(Stage currentwindow) {
		this.currentWindow = currentwindow;
		currentWindow.setResizable(false);
	}
    
    /**
     * Ouvre le menu principal de l'application, sauf si l'utilisateur s'est trompé dans ses identifaints
     * @throws SQLException
     * @throws IOException
     */
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
    
    /**
     * Ouvre le menu pour se créer un nouveau compte sur l'application
     * @throws IOException
     */
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
