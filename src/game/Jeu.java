package game;

public class Jeu {
	
	private int[][] plateau = new int[6][7];
	private String j1;
	private String j2;
	private String joueurActif = j1;
	
	public Jeu(String j1, String j2) {
		this.j1 = j1;
		this.j2 = j2;
	}

}
