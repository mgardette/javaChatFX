package game;

import java.io.IOException;

import gui.PuissanceController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class MainGame extends Application {

	@Override
	public void start(Stage primaryStage) throws IOException {
		
		Stage stage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(this.getClass().getResource("/gui/PuissanceQuatre.fxml"));
		
		Parent root = loader.load();
		PuissanceController ctlr = loader.getController();
		ctlr.initialize(stage, "Noah", "Mathieu");
		stage.setScene(new Scene(root));
		stage.showAndWait();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
