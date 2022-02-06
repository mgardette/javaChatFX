package game;

/**
 * @author Noah COUPEY
 *
 */
public class Plateau {

	/**
	 * Double tableau d'int qui sert de plateau de jeu.
	 */
	private int[][] plateau = new int[6][7];
	
	/**
	 * Initialise toutes les valeur du plateau à 0
	 */
	public Plateau() {
		for(int ligne = 0; ligne < 6; ligne++) {
			for(int col = 0; col < 7; col++) {
				plateau[ligne][col] = 0;
			}
		}
	}
	
	/**
	 * @return Le plateau de jeu
	 */
	public int[][] getPlateau() {
		return plateau;
	}
	
	/**
	 * Permet d'obtenir une ligne spécifique du plateau
	 * @param ligne Le numéro de la ligne du plateau qu'on aimerai avoir
	 * @return La ligne du plateau
	 */
	public int[] getLigne(int ligne) {
		return plateau[ligne];
	}
	
	/**
	 * Permet d'obtenir une colonne spécifique du plateau
	 * @param col Le numéro de la colonne du plateau qu'on aimerai avoir
	 * @return La colonne du plateau
	 */
	public int[] getColonne(int col) {
		int[] colVoulue = {plateau[0][col], plateau[1][col], plateau[2][col], plateau[3][col], plateau[4][col], plateau[5][col]};
		return colVoulue;
	}
	
	/**
	 * @param col La colonne qu'on veut vérifier
	 * @return La première place disponible de la colonne. -1 s'il n'y a plus de place disponible.
	 */
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
	
	/**
	 * Place le numero du joueur sur le plateau
	 * @param ligne Ligne où placer le numéro
	 * @param col Colonne où placer le numéro
	 * @param joueur Numéro du joueur à placer dans le plateau
	 */
	public void placer(int ligne, int col, int joueur) {
		plateau[ligne][col] = joueur;
	}
	
	/**
	 * Vérifie chaque ligne, colonne et diagonale où il est possible de faire un puissance 4.
	 * @return Le numéro du gagnant, ou 0 sil n'y en a pas
	 */
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
	
	/**
	 * Vérifie les diagonales d'en haut à gauche à en bas à droite
	 * @param ligne Première ligne à vérifier
	 * @param col Première colonne à vérifier
	 * @return Le numéro du gagnant, 0 si aucun.
	 */
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
	
	/**
	 * Vérifie les diagonales d'en haut à droite à en bas à gauche
	 * @param ligne Première ligne à vérifier
	 * @param col Première colonne à vérifier
	 * @return Le numéro du gagnant, 0 si aucun.
	 */
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
	
	/**
	 * Vérifie une ligne du plateau
	 * @param ligne Ligne à vérifier
	 * @return Le numéro du gagnant, 0 si aucun.
	 */
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
	
	/**
	 * Vérifie une colonne du plateau
	 * @param col Colonne à vérifier
	 * @return Le numéro du gagnant, 0 si aucun.
	 */
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
	
	
	/**
	 * Fonction de débug.
	 * Permet d'afficher le plateau dans la console.
	 */
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
