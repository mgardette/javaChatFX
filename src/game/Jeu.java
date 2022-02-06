package game;

public class Jeu {
	
	private Plateau plateau;
	private String j1;
	private String j2;
	private String joueurActif = j1;
	
	public Jeu(String j1, String j2) {
		this.plateau = new Plateau();
		this.j1 = j1;
		this.j2 = j2;
	}

}
