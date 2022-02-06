package game;

public class Plateau {

	private int[][] plateau = new int[6][7];
	
	public Plateau() {
		for(int ligne = 0; ligne < 6; ligne++) {
			for(int col = 0; col < 7; col++) {
				plateau[ligne][col] = 0;
			}
		}
	}
	
	public int[][] getPlateau() {
		return plateau;
	}
	
	public int[] getLigne(int ligne) {
		return plateau[ligne];
	}
	
	public int[] getColonne(int col) {
		int[] colVoulue = {plateau[0][col], plateau[1][col], plateau[2][col], plateau[3][col], plateau[4][col], plateau[5][col]};
		return colVoulue;
	}
	
	public int getPiecePlusHauteDispo(int col) {
		int highest = -1;
		for(int ligne = this.plateau.length - 1; ligne >= 0; ligne--) {
			if(plateau[ligne][col] == 0) {
				highest = ligne;
				break;
			}
		}
		return highest;
	}
	
	public void placer(int ligne, int col, int joueur) {
		plateau[ligne][col] = joueur;
	}
	
	public int verifier() {
		int gagnant = 0;
		for(int ligne = 0; ligne < 6; ligne++) {
			gagnant = verifLigne(ligne);
			if(gagnant != 0) break;
		}
		if(gagnant == 0) {
			for(int col = 0; col < 7; col++) {
				gagnant = verifColonne(col);
				if(gagnant != 0) break;
			}
		}
		if(gagnant == 0) {
			for(int i = 0; i < 3; i++) {
				gagnant = verifDiagonalePrincipale(i, 0);
				if(gagnant != 0) break;
			}
		}
		if(gagnant == 0) {
			for(int i = 1; i < 4; i++) {
				gagnant = verifDiagonalePrincipale(0, i);
				if(gagnant != 0) break;
			}
		}
		if(gagnant == 0) {
			for(int i = 0; i < 3; i++) {
				gagnant = verifDiagonaleSecondaire(i, 6);
				if(gagnant != 0) break;
			}
		}
		if(gagnant == 0) {
			for(int i = 5; i > 2; i--) {
				gagnant = verifDiagonaleSecondaire(0, i);
				if(gagnant != 0) break;
			}
		}
		
		return gagnant;
	}
	
	private int verifDiagonalePrincipale(int ligne, int col) {
		int streak = 1;
		int previous = 0;
		int win = 0;
		int current = 0;
		while(win == 0 && ligne < 6 && col < 7) {
			current = plateau[ligne][col];
			if(current == previous && current != 0) streak++;
			else streak = 1;
			if(streak == 4) {
				win = previous;
				break;
			}
			previous = current;
			ligne++;
			col++;
		}
		return win;
	}
	
	private int verifDiagonaleSecondaire(int ligne, int col) {
		int streak = 1;
		int previous = 0;
		int win = 0;
		int current = 0;
		while(win == 0 && col >= 0 && ligne < 6) {
			current = plateau[ligne][col];
			if(current == previous && current != 0) streak++;
			else streak = 1;
			if(streak == 4) {
				win = previous;
				break;
			}
			previous = current;
			ligne++;
			col--;
		}
		return win;
	}
	
	private int verifLigne(int ligne) {
		int streak = 1;
		int previous = 0;
		int win = 0;
		for(int player : plateau[ligne]) {
			if(player == previous) streak++;
			else streak = 1;
			previous = player;
			if(streak == 4) {
				win = player;
				break;
			}
		}
		return win;
	}
	
	private int verifColonne(int col) {
		int streak = 1;
		int previous = 0;
		int win = 0;
		for(int player : this.getColonne(col)) {
			if(player == previous) streak++;
			else streak = 1;
			previous = player;
			if(streak == 4) {
				win = player;
				break;
			}
		}
		return win;
	}
	
	public void printBoard() {
		System.out.println(" -----------------------------");
		for(int[] ligne : plateau) {
			for(int valeur : ligne) {
				System.out.print(" | " + valeur);
			}
			System.out.println(" |");
		}
		System.out.println(" -----------------------------");
	}
	
}
