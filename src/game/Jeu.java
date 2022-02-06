package game;

public class Jeu {
	
	private Plateau plateau;
	private String j1;
	private String j2;
	private String joueurActif;
	private boolean fini;
	
	public Jeu(String j1, String j2) {
		this.plateau = new Plateau();
		this.j1 = j1;
		this.j2 = j2;
		this.joueurActif = j1;
		this.fini = false;
	}
	
	public String getJoueurActif() {
		return joueurActif;
	}
	
	public int convertirJoueurActifEnInt() {
		int playerNum = 1;
		if(joueurActif.equals(j2)) {
			playerNum = 2;
		}
		return playerNum;
	}
	
	public String getJ1() {
		return j1;
	}
	
	public String getJ2() {
		return j2;
	}
	
	public boolean estFini() {
		return this.fini;
	}
	
	public boolean placerPiece(int col, String joueur) {
		boolean correct = false;
		if(!this.fini) {
			int ligne = plateau.getPiecePlusHauteDispo(col);
			if(ligne != -1 && joueur.equals(joueurActif)) {
				plateau.placer(ligne, col, this.convertirJoueurActifEnInt());
				correct = true;
			}
		}
		return correct;
	}
	
	public int prochainTour() {
		int winner = plateau.verifier();
		if(winner == 0) changerJoueur();
		else this.fini = true;
		return winner;
	}
	
	private void changerJoueur() {
		if(joueurActif.equals(j1)) {
			joueurActif = j2;
		}
		else joueurActif = j1;
	}

}
