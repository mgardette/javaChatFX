package application;
	
import java.io.IOException;

import client.Client;
import client.ClientPanel;
import common.DB;
import gui.AuthController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;


public class MainGUI extends Application {
	@Override
	public void start(Stage primaryStage) throws IOException {
		
		//DB.getConnection();
		
		Stage stage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(this.getClass().getResource("/gui/Auth.fxml"));
		
		Parent root = loader.load();
		stage.setScene(new Scene(root));
		stage.showAndWait();
		
		/*try {
			Group root = new Group();
			ClientPanel cp = new ClientPanel();
			root.getChildren().add(cp);
			Scene scene = new Scene(root,500,525);
			scene.setFill(Color.web("#2f3640"));
			
			Client client = new Client(this.getParameters().getRaw().get(0), 
					Integer.parseInt(this.getParameters().getRaw().get(1)));
			client.setView(cp);
			cp.setClient(client);
			
			primaryStage.setTitle("Chat");
			primaryStage.setScene(scene);
			primaryStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}*/
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
