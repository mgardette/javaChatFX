package application;
	
import client.Client;
import client.ClientPanel;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;


public class MainGUI extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
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
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
