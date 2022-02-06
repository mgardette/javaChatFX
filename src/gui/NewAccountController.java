package gui;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import common.DB;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class NewAccountController {
	
	private Stage currentWindow;
	
    @FXML
    private Button cancel_button;

    @FXML
    private PasswordField confirm_pass;

    @FXML
    private Button create_button;

    @FXML
    private PasswordField new_pass;

    @FXML
    private TextField new_username;

    @FXML
    private Label error_message;
    
    public void initialize(Stage currentwindow) {
		this.currentWindow = currentwindow;
	}
    
    public void creer_compte() throws SQLException {
    	Statement stm = DB.getConnection().createStatement();
    	if(verif_saisie(stm)) {
    		ResultSet rs = stm.executeQuery("SELECT max(num_uti) + 1 as id FROM utilisateur");
        	rs.next();
        	rs = stm.executeQuery("INSERT INTO utilisateur VALUES(" + rs.getInt("id") + ", '" + new_username.getText() + "', '" + new_pass.getText() + "', default, null)");
        	currentWindow.close();
        	rs.close();
    	}
        stm.close();
    }
    
    private boolean verif_saisie(Statement stm) throws SQLException {
    	
    	boolean isCorrect = false;
		
    	if(new_username.getText().equals("") || new_pass.getText().equals("")) {
    		error_message.setText("Veuillez remplir tout les champs.");
    		error_message.setVisible(true);
    	}
    	else if(stm.executeQuery("SELECT pseudo FROM utilisateur WHERE pseudo = '" + new_username.getText() + "'").next()) { 
    		error_message.setText("Pseudo déjà utilisé.");
    		error_message.setVisible(true);
        }
    	else if(new_pass.getText().length() < 6) {
    		error_message.setText("Mot de passe: Minimum 6 caractères.");
    		error_message.setVisible(true);
    	}
    	else if(!new_pass.getText().equals(confirm_pass.getText())){
    		error_message.setText("Mots de passe différents.");
    		error_message.setVisible(true);
    	}
    	else {
    		isCorrect = true;
    	}
    	return isCorrect;
    }
    
    public void annuler() {
    	currentWindow.close();
    }

}
