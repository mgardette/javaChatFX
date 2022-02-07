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
	
	/**
	 * Stage actuel
	 */
	private Stage currentWindow;
	
    /**
     * Bouton d'annulation
     */
    @FXML
    private Button cancel_button;

    /**
     * Champ de texte pour confirmer le mot de passe
     */
    @FXML
    private PasswordField confirm_pass;

    /**
     * Bouton de validation de création de compte
     */
    @FXML
    private Button create_button;

    /**
     * Champ de texte pour le mot de passe
     */
    @FXML
    private PasswordField new_pass;

    /**
     * Champ de texte pour le pseudo de l'utilisateur
     */
    @FXML
    private TextField new_username;

    /**
     * Label affichant les messages d'erreur
     */
    @FXML
    private Label error_message;
    
    /**
     * Permet d'initialiser certaines informations avant que la fenêtre s'ouvre
	 * @param currentWindow Stage actuel
     */
    public void initialize(Stage currentwindow) {
		this.currentWindow = currentwindow;
	}
    
    /**
     * Permet de rentrer les informations saisies dans les champs dans la base de données
     * @throws SQLException
     */
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
    
    /**
     * Vérifie les champs de texte pour vérifier qu'ils sont conformes aux règles imposées
     * @param stm Statement SQL pour accéder à la base
     * @return True si les champs sont siasis correctement, False s'il ne le sont pas
     * @throws SQLException
     */
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
    
    /**
     * Ferme la fenêtre actuelle
     */
    public void annuler() {
    	currentWindow.close();
    }

}
