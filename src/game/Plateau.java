package game;

public class Plateau {

	private int[][] plateau = new int[6][7];
	
	public Plateau() {
		for(int x = 0; x < 7; x++) {
			for(int y = 0; y < 6; y++) {
				plateau[x][y] = 0;
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
		int[] colVoulue = {plateau[0][col], plateau[1][col], plateau[2][col], plateau[3][col], plateau[4][col], plateau[5][col], plateau[6][col]};
		return colVoulue;
	}
	
	public int verifier() {
		int gagnant = 0;
		for(int i = 0; i <= 5; i++) {
			gagnant = verifLigne(i);
			if(gagnant != 0) break;
		}
		if(gagnant == 0) {
			for(int i = 0; i <= 6; i++) {
				gagnant = verifColonne(i);
				if(gagnant != 0) break;
			}
		}
		if(gagnant == 0) {
			gagnant = verifDiagonalePrincipale(0, 0);
			gagnant = verifDiagonalePrincipale(0, 1);
			gagnant = verifDiagonalePrincipale(0, 2);
			gagnant = verifDiagonalePrincipale(1, 0);
			gagnant = verifDiagonalePrincipale(2, 0);
			gagnant = verifDiagonalePrincipale(3, 0);
		}
		if(gagnant == 0) {
			gagnant = verifDiagonaleSecondaire(6, 0);
			gagnant = verifDiagonaleSecondaire(6, 1);
			gagnant = verifDiagonaleSecondaire(6, 2);
			gagnant = verifDiagonaleSecondaire(5, 0);
			gagnant = verifDiagonaleSecondaire(4, 0);
			gagnant = verifDiagonaleSecondaire(3, 0);
			
		}
		
		return gagnant;
	}
	
	private int verifDiagonalePrincipale(int x, int y) {
		int streak = 0;
		int previous = 0;
		int win = 0;
		while(streak < 4 && x < 7 && y < 6) {
			if(plateau[x][y] == previous) streak++;
			else streak = 0;
			previous = plateau[x][y];
			if(streak == 4) {
				win = previous;
				break;
			}
			x++;
			y++;
		}
		return win;
	}
	
	private int verifDiagonaleSecondaire(int x, int y) {
		int streak = 0;
		int previous = 0;
		int win = 0;
		while(streak < 4 && x >= 0 && y < 6) {
			if(plateau[x][y] == previous) streak++;
			else streak = 0;
			previous = plateau[x][y];
			if(streak == 4) {
				win = previous;
			}
			x--;
			y++;
		}
		return win;
	}
	
	private int verifLigne(int ligne) {
		int streak = 0;
		int previous = 0;
		int win = 0;
		for(int player : plateau[ligne]) {
			if(player == previous) streak++;
			else streak = 0;
			previous = player;
			if(streak == 4) {
				win = previous;
				break;
			}
		}
		return win;
	}
	
	private int verifColonne(int col) {
		int streak = 0;
		int previous = 0;
		int win = 0;
		for(int player : this.getColonne(col)) {
			if(player == previous) streak++;
			else streak = 0;
			previous = player;
			if(streak == 4) {
				win = previous;
				break;
			}
		}
		return win;
	}
	
}
