package gui;

import java.io.File;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import server.Server;

/**
 * @author Mathieu GARDETTE
 *
 */
public class ProfileController {

    @FXML
    private Button btnPlay;

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
    
    /**
     * Constructeur de la classe profile.
     * Contient l'initialisation du nom du profil, de l'historique des 5 derniers matchs, du classement et de la description.
     * V?rification si le visiteur est le "propri?taire" du profil pour l'activation des boutons de modifications ou la d?sactivation 
     * des boutons de chat priv? et de lancement de jeu
     * @param currentwindow
     * @param client
     * @param visiteur
     * @param server
     */
    public void initialize(Stage currentwindow, String client, Client visiteur, Server server) {
    	this.profilePerso = false;
    	// TODO requete bdd pour r?cup?rer le client associ? au pseudo
    	Statement stm;
		try {
			stm = DB.getConnection().createStatement();
			ResultSet rs = stm.executeQuery("SELECT pseudo, mdp, photo_profil, statut FROM utilisateur WHERE pseudo = '" + client + "' ");
		      //?tape 5: extraire les donn?es
		      while(rs.next()){
		         //R?cup?rer par nom de colonne
		         String pseudoProfil = rs.getString("pseudo");
		         String mdpProfil = rs.getString("mdp");
		         String photoProfil = rs.getString("photo_profil");
		         String descProfil = rs.getString("statut");
		         //Cr?er objet client
		         User clientObj = new User(pseudoProfil, mdpProfil, photoProfil, descProfil);
		     	
	     		 this.user = clientObj;
	     		 this.desc.setText(this.user.getDesc());
	     		 this.usersProfile.setText(this.user.getPseudo());
		      }
			  rs = stm.executeQuery("SELECT resultat, joueur, adversaire FROM scorejeu WHERE joueur = '" +client + "' OR adversaire ='" + client + "' ");
			  int compteur = 0;
		      while(compteur <= 4 ){		         
		        switch (compteur) {
					case 0: {
						if(rs.next())
						{
					        String result = rs.getString("resultat");
					        String player = rs.getString("joueur");
					        String opponent = rs.getString("adversaire");
							this.histoOne.setText(player + " contre " + opponent + " - ");
					         switch (result) {
							case "V":
								this.histoOne.setText(this.histoOne.getText() + " victoire de " + player);
								break;
							case "N":
								this.histoOne.setText(this.histoOne.getText() + " nul");
								break;
							case "D":
								this.histoOne.setText(this.histoOne.getText() + " d?faite de " + player);
								break;
							}
						}
						else {
							this.histoOne.setText("");
						}
						break;
					}
					case 1: {
						if(rs.next())
						{
					        String result = rs.getString("resultat");
					        String player = rs.getString("joueur");
					        String opponent = rs.getString("adversaire");
							this.histoTwo.setText(player + " contre " + opponent + " - ");
					         switch (result) {
							case "V":
								this.histoTwo.setText(this.histoTwo.getText() + " victoire de " + player);
								break;
							case "N":
								this.histoTwo.setText(this.histoTwo.getText() + " nul");
								break;
							case "D":
								this.histoTwo.setText(this.histoTwo.getText() + " d?faite de " + player);
								break;
							}
						}
						else {
							this.histoTwo.setText("");
						}
						break;
					}
					case 2: {
						if(rs.next())
						{
					        String result = rs.getString("resultat");
					        String player = rs.getString("joueur");
					        String opponent = rs.getString("adversaire");
							this.histoThree.setText(player + " contre " + opponent + " - ");
					         switch (result) {
							case "V":
								this.histoThree.setText(this.histoThree.getText() + " victoire de " + player);
								break;
							case "N":
								this.histoThree.setText(this.histoThree.getText() + " nul");
								break;
							case "D":
								this.histoThree.setText(this.histoThree.getText() + " d?faite de " + player);
								break;
							}
						}
						else {
							this.histoThree.setText("");
						}
						break;
					}
					case 3: {
						if(rs.next())
						{
					        String result = rs.getString("resultat");
					        String player = rs.getString("joueur");
					        String opponent = rs.getString("adversaire");
							this.histoFour.setText(player + " contre " + opponent + " - ");
					         switch (result) {
							case "V":
								this.histoFour.setText(this.histoFour.getText() + " victoire de " + player);
								break;
							case "N":
								this.histoFour.setText(this.histoFour.getText() + " nul");
								break;
							case "D":
								this.histoFour.setText(this.histoFour.getText() + " d?faite de " + player);
								break;
							}
						}
						else {
							this.histoFour.setText("");
						}
						break;
					}
					case 4: {
						if(rs.next())
						{
					        String result = rs.getString("resultat");
					        String player = rs.getString("joueur");
					        String opponent = rs.getString("adversaire");
							this.histoFive.setText(player + " contre " + opponent + " - ");
					         switch (result) {
							case "V":
								this.histoFive.setText(this.histoFive.getText() + " victoire de " + player);
								break;
							case "N":
								this.histoFive.setText(this.histoFive.getText() + " nul");
								break;
							case "D":
								this.histoFive.setText(this.histoFive.getText() + " d?faite de " + player);
								break;
							}
						}
						else {
							this.histoFive.setText("");
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
    	
    	// V?rification de si le visiteur est l'utilisateur
    	if(this.user != null && this.visiteur.getPseudo().equals(this.user.getPseudo()))
		{
    		this.profilePerso = true;
    		this.modifBtn.setDisable(false);
    		this.modifBtn.setVisible(true);
    		this.btnPlay.setDisable(true);
    		this.btnPlay.setVisible(false);
		}
		File file;
		try {
			file = calculRank();
		    this.rankingPicture.setImage(new Image(file.toURI().toString()));
		    this.rankingPicture.setFitWidth(150);
		    this.rankingPicture.setFitHeight(150);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
    
    /**
     * M?thode calculant le rang du joueur et retourne un string qui sera le rang
     * @return
     * @throws SQLException
     */
    public File calculRank() throws SQLException {
    	Statement stm;
    	stm = DB.getConnection().createStatement();
    	ResultSet  rs = stm.executeQuery("SELECT resultat, joueur, adversaire FROM scorejeu WHERE joueur = '" + this.user.getPseudo() + "' OR adversaire ='" + this.user.getPseudo() + "' ");
    	int nbPoints = 300;
    	int compteurV = 0;
    	int compteurN = 0;
    	int compteurD = 0;
    	int compteurP = 0;
		while(rs.next()) {
			if(rs.getString("joueur").equals(this.user.getPseudo())) {
				switch (rs.getString("resultat")) {
				case "V":
						compteurV++;
					break;
				case "N":
						compteurN++;
					break;
				case "D":
						compteurD++;
					break;
				}
				
			}
			else {
				switch (rs.getString("resultat")) {
				case "V":
						compteurD++;
					break;
				case "N":
						compteurN++;
					break;
				case "D":
						compteurV++;
					break;
				}
			}
			compteurP++;
		}
		compteurP = compteurP * 10;
		compteurV = compteurV * 10;
		int compteurT = nbPoints + (compteurV - compteurP);
		String tier = "";
		File file = new File("resources/medal_bronze.png");
		if(compteurT < 200) {
			file = new File("resources/medal_bronze.png");
		}
		else if(compteurT < 400 && compteurT > 199) {
			file = new File("resources/medal_silver.png");
		}
		else if(compteurT < 600 && compteurT > 399) {
			file = new File("resources/medal_gold.png");
		}
		else if(compteurT < 800 && compteurT > 599) {
			file = new File("resources/medal_platinum.png");
		}
		else if(compteurT > 799) {
			file = new File("resources/medal_champ.png");
		}
		rs.close();
		stm.close();
		return file;
    }

    /**
     * M?thode lan?ant une partie avec le joueur concern?
     * @param event
     */
    @FXML
    public void startGame(ActionEvent event) {

    }
    
    /**
     * Bouton validant la modification de la description
     * @param event
     */
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
    
    /**
     * Bouton annulant la modification de la description
     * @param event
     */
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
    
    /**
     * Bouton activant la possibilit? de modifier la description
     * @param event
     */
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
