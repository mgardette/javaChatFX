package game;

public class Jeu {
	
	private Plateau plateau;
	private String j1;
	private String j2;
	private String joueurActif;
	
	public Jeu(String j1, String j2) {
		this.plateau = new Plateau();
		this.j1 = j1;
		this.j2 = j2;
		this.joueurActif = j1;
	}
	
	public String getJoueurActif() {
		return joueurActif;
	}
	
	public String getJ1() {
		return j1;
	}
	
	public String getJ2() {
		return j2;
	}
	
	public int unTour() {
		int winner = plateau.verifier();
		if(winner == 0) {
			changerJoueur();
		}
		return winner;
	}
	
	private void changerJoueur() {
		if(joueurActif.equals(j1)) {
			joueurActif = j2;
		}
		else joueurActif = j1;
	}

}
