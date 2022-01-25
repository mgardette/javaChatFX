package gui;

import java.io.IOException;
import java.net.UnknownHostException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import client.Client;
import client.ClientPanel;
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
	}
    
    public void se_connecter() throws SQLException {
    	Statement stm = DB.getConnection().createStatement();
		ResultSet rs = stm.executeQuery("SELECT pseudo FROM utilisateur WHERE pseudo = '" + pseudo_input.getText() + "' AND mdp = '" + pass_input.getText() +"'");
    	if(rs.next()) {
    		currentWindow.close();
    		Group root = new Group();
			ClientPanel cp = new ClientPanel();
			root.getChildren().add(cp);
			Scene scene = new Scene(root,500,525);
			scene.setFill(Color.web("#2f3640"));
			
			try {
				Client client;
				client = new Client("127.0.0.1", 5000);
				client.setView(cp);
				cp.setClient(client);
				
				Stage stage = new Stage();
				stage.setTitle("Chat");
				stage.setScene(scene);
				stage.show();
			} catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
    	}
    	else {
    		input_error.setVisible(true);;
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
