package application;
	
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import common.DB;
import gui.AuthController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;


/**
 * @author Noah COUPEY
 * @author Mathieu GARDETTE
 *
 */
public class MainGUI extends Application {
	@Override
	public void start(Stage primaryStage) throws IOException {
		
		try {
			DB.getInstance();
			new File("C:\\Users\\" + System.getProperty("user.name") + "\\DevOpsChat\\Logs").mkdirs();
			Stage stage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(this.getClass().getResource("/gui/Auth.fxml"));
			
			Parent root = loader.load();
			AuthController ctlr = loader.getController();
			ctlr.initialize(stage);
			stage.setScene(new Scene(root));
			stage.showAndWait();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Impossible de se connecter à la base de données.");
		}
	}
	
	/**
	 * Lance l'application principale
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
