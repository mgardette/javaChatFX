package gui;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class PuissanceController {
	
	private String pseudoJ1;
	private String pseudoJ2;
	private Stage currentWindow;

	@FXML
	private VBox column0;

	@FXML
	private VBox column1;

	@FXML
	private VBox column2;

	@FXML
	private VBox column3;

	@FXML
	private VBox column4;

	@FXML
	private VBox column5;

	@FXML
	private VBox column6;

	@FXML
	private Label playerOneTitle;

	@FXML
	private Label playerStatus;

	@FXML
	private Label playerTwoTitle;

	@FXML
	private Label status;
	
	public void initialize(Stage currentWindow, String pseudoJ1, String pseudoJ2) {
		this.currentWindow = currentWindow;
		this.playerOneTitle.setText(pseudoJ1);
		this.playerTwoTitle.setText(pseudoJ2);
		this.playerStatus.setText(pseudoJ1);
	}

}
