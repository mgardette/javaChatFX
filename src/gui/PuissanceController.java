package gui;

import java.io.File;

import game.Jeu;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class PuissanceController {
	
	private Jeu jeu;
	private Stage currentWindow;
	private String joueur;

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
		currentWindow.setResizable(false);
		this.joueur = pseudoJ1;
		this.jeu = new Jeu(pseudoJ1, pseudoJ2);
		this.playerOneTitle.setText(pseudoJ1);
		this.playerTwoTitle.setText(pseudoJ2);
		this.playerStatus.setText(pseudoJ1);
		
		this.column0.setOnMouseClicked(e -> columnClick(e, 0));
		this.column1.setOnMouseClicked(e -> columnClick(e, 1));
		this.column2.setOnMouseClicked(e -> columnClick(e, 2));
		this.column3.setOnMouseClicked(e -> columnClick(e, 3));
		this.column4.setOnMouseClicked(e -> columnClick(e, 4));
		this.column5.setOnMouseClicked(e -> columnClick(e, 5));
		this.column6.setOnMouseClicked(e -> columnClick(e, 6));
	}
	
	public void columnClick(MouseEvent e, int col) {
		
		if(jeu.placerPiece(col, joueur)) {
			ImageView imageView = new ImageView();
			File file = new File("resources/checker_one.png");
			if(jeu.getJoueurActif().equals(jeu.getJ2())) {
				file = new File("resources/checker_two.png");
			}
		    imageView.setImage(new Image(file.toURI().toString()));
		    imageView.setFitWidth(100);
		    imageView.setFitHeight(100);
		    VBox column = (VBox) e.getSource();
		    column.getChildren().add(0, imageView);
		    this.setupProchainTour();
		}
	    
	    
	}
	
	private void setupProchainTour() {
		switch(jeu.prochainTour()) {
	    case 0:
	    	playerStatus.setText(jeu.getJoueurActif());
	    	playerStatus.setStyle("-fx-text-fill : #5fd2ea");
	    	if(jeu.getJoueurActif().equals(jeu.getJ1())) {
	    		playerStatus.setStyle("-fx-text-fill : #ff3260");
	    	}
	    	break;
	    case 1:
	    	status.setText("Victoire de ");
	    	playerStatus.setText(jeu.getJ1());
	    	playerStatus.setStyle("-fx-text-fill : #ff3260");
	    	break;
	    case 2:
	    	status.setText("Victoire de ");
	    	playerStatus.setText(jeu.getJ2());
	    	playerStatus.setStyle("-fx-text-fill : #5fd2ea");
	    	break;
	    };
	}

}
