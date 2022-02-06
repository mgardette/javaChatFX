package gui;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import client.Client;
import client.User;
import common.DB;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import server.Server;

public class ProfileController {

    @FXML
    private Button btnPlay;

    @FXML
    private Button btnPrvChat;

    @FXML
    private Button cancelBtn;
    
    @FXML
    private TextArea desc;

    @FXML
    private Label histoFour;

    @FXML
    private Label histoOne;

    @FXML
    private Label histoThree;

    @FXML
    private Label histoTwo;
    
    @FXML
    private Label histoFive;

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
    
    private User user;
    
    private Client visiteur;
    
    private boolean profilePerso;
    
    public void initialize(Stage currentwindow, String client, Client visiteur, Server server) {
    	this.profilePerso = false;
    	// TODO requete bdd pour récupérer le client associé au pseudo
    	Statement stm;
		try {
			stm = DB.getConnection().createStatement();
			ResultSet rs = stm.executeQuery("SELECT pseudo, mdp, photo_profil, statut FROM utilisateur WHERE pseudo = '" + client + "' ");
		      //étape 5: extraire les données
		      while(rs.next()){
		         //Récupérer par nom de colonne
		         String pseudoProfil = rs.getString("pseudo");
		         String mdpProfil = rs.getString("mdp");
		         String photoProfil = rs.getString("photo_profil");
		         String descProfil = rs.getString("statut");
		         //Créer objet client
		         User clientObj = new User(pseudoProfil, mdpProfil, photoProfil, descProfil);
		     	
	     		 this.user = clientObj;
	     		 this.desc.setText(this.user.getDesc());
	     		 this.usersProfile.setText(this.user.getPseudo());
		      }
			  rs = stm.executeQuery("SELECT resultat, joueur, adversaire FROM scorejeu WHERE joueur = '" +client + "' OR adversaire ='" + client + "' ");
			  int compteur = 0;
		      //étape 5: extraire les données
		      while(rs.next() && compteur <= 4 ){
		         //Récupérer par nom de colonne
		         String result = rs.getString("resultat");
		         String player = rs.getString("joueur");
		         String opponent = rs.getString("adversaire");
		         //Créer objet client
		         
		        switch (compteur) {
				case 0: {
					this.histoOne.setText(player + " contre " + opponent + " - ");
			         switch (result) {
					case "V":
						this.histoOne.setText(this.histoOne.getText() + " victoire de " + player);
						break;
					case "N":
						this.histoOne.setText(this.histoOne.getText() + " nul");
						break;
					case "D":
						this.histoOne.setText(this.histoOne.getText() + " défaite de " + player);
						break;
					}
					break;
				}
				case 1: {
					this.histoOne.setText(player + " contre " + opponent + " - ");
			         switch (result) {
					case "V":
						this.histoOne.setText(this.histoOne.getText() + " victoire de " + player);
						break;
					case "N":
						this.histoOne.setText(this.histoOne.getText() + " nul");
						break;
					case "D":
						this.histoOne.setText(this.histoOne.getText() + " défaite de " + player);
						break;
					}
					break;
				}
				case 2: {
					this.histoOne.setText(player + " contre " + opponent + " - ");
			         switch (result) {
					case "V":
						this.histoOne.setText(this.histoOne.getText() + " victoire de " + player);
						break;
					case "N":
						this.histoOne.setText(this.histoOne.getText() + " nul");
						break;
					case "D":
						this.histoOne.setText(this.histoOne.getText() + " défaite de " + player);
						break;
					}
					break;
				}
				case 3: {
					this.histoOne.setText(player + " contre " + opponent + " - ");
			         switch (result) {
					case "V":
						this.histoOne.setText(this.histoOne.getText() + " victoire de " + player);
						break;
					case "N":
						this.histoOne.setText(this.histoOne.getText() + " nul");
						break;
					case "D":
						this.histoOne.setText(this.histoOne.getText() + " défaite de " + player);
						break;
					}
					break;
				}
				case 4: {
					this.histoOne.setText(player + " contre " + opponent + " - ");
			         switch (result) {
					case "V":
						this.histoOne.setText(this.histoOne.getText() + " victoire de " + player);
						break;
					case "N":
						this.histoOne.setText(this.histoOne.getText() + " nul");
						break;
					case "D":
						this.histoOne.setText(this.histoOne.getText() + " défaite de " + player);
						break;
					}
					break;
				}
		        }
	     		 compteur++;
		      }
		    
	    	rs.close();
	    	stm.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	this.visiteur = visiteur;
    	
    	// Vérification de si le visiteur est l'utilisateur
    	if(this.visiteur.getPseudo().equals(this.user.getPseudo()))
		{
    		this.profilePerso = true;
    		this.modifBtn.setDisable(false);
    		this.modifBtn.setVisible(true);
    		this.btnPlay.setDisable(true);
    		this.btnPlay.setVisible(false);
    		this.btnPrvChat.setDisable(true);
    		this.btnPrvChat.setVisible(false);
		}
    	
    	// Procédure pour récupérer les derniers attributs
    	
	}
    
    public void calculRank() {
    	
    }

    @FXML
    public void openPrivateChat(ActionEvent event) {

    }

    @FXML
    public void startGame(ActionEvent event) {

    }

    @FXML
    public void validModif(ActionEvent event) {
		try {
	    	Statement stm;
			stm = DB.getConnection().createStatement();
			ResultSet rs;
			rs = stm.executeQuery("UPDATE utilisateur SET statut = '" + this.desc.getText() + "' WHERE pseudo = '" + this.user.getPseudo() + "' ");
	    	rs.close();
	    	stm.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	this.desc.setEditable(false);
		this.validateBtn.setDisable(true);
		this.validateBtn.setVisible(false);
		this.cancelBtn.setDisable(true);
		this.cancelBtn.setVisible(false);
		this.modifBtn.setDisable(false);
		this.modifBtn.setVisible(true);
    }
    
    @FXML
    public void cancelModif(ActionEvent event) {
    	this.desc.setText(this.user.getDesc());
    	this.desc.setEditable(false);
		this.validateBtn.setDisable(true);
		this.validateBtn.setVisible(false);
		this.cancelBtn.setDisable(true);
		this.cancelBtn.setVisible(false);
		this.modifBtn.setDisable(false);
		this.modifBtn.setVisible(true);
    }

    @FXML
    public void modifDesc(ActionEvent event) {
    	this.desc.setEditable(true);
		this.validateBtn.setDisable(false);
		this.validateBtn.setVisible(true);
		this.cancelBtn.setDisable(false);
		this.cancelBtn.setVisible(true);
		this.modifBtn.setDisable(true);
		this.modifBtn.setVisible(false);
    }
}
