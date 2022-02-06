package game;

/**
 * @author Noah COUPEY
 *
 */
public class Jeu {
	
	/**
	 * Le plateau de jeu
	 */
	private Plateau plateau;
	/**
	 * Le pseudo du joueur 1
	 */
	private String j1;
	/**
	 * Le pseudo du joueur 2
	 */
	private String j2;
	/**
	 * Le joueur actif (celui qui doit jouer)
	 */
	private String joueurActif;
	/**
	 * L'état du jeu
	 */
	private boolean fini;
	
	/**
	 * @param j1 Pseudo du joueur 1
	 * @param j2 Pseudo du joueur 2
	 */
	public Jeu(String j1, String j2) {
		this.plateau = new Plateau();
		this.j1 = j1;
		this.j2 = j2;
		this.joueurActif = j1;
		this.fini = false;
	}
	
	/**
	 * Retourne le joueur actif (Celui qui doit jouer)
	 * @return Le joueur actif
	 * @see String
	 */
	public String getJoueurActif() {
		return joueurActif;
	}
	
	/**
	 * Convertis le joueur actif en Integer, puis le retourne. Si le joueur actif est le joueur 1, alors la fonction retournera 1.
	 * Si le joueur actif est le joueur 2, alors il retournera 2.
	 * @return Le nombre entier correspondant au joueur actif
	 * @see Integer
	 */
	public int convertirJoueurActifEnInt() {
		int playerNum = 1;
		if(joueurActif.equals(j2)) {
			playerNum = 2;
		}
		return playerNum;
	}
	
	/**
	 * @return Le nom du joueur 1
	 */
	public String getJ1() {
		return j1;
	}
	
	/**
	 * @return Le nom du joueur 2
	 */
	public String getJ2() {
		return j2;
	}
	
	/**
	 * @return vrai si la partie est finie, faux si elle ne l'est pas.
	 */
	public boolean estFini() {
		return this.fini;
	}
	
	/**
	 * Permet de placer une pièce sur le plateau.
	 * La fonction vérifie d'abord que la partie n'est pas terminée, puis récupère la pièce la prochaine place libre sur la colonne.
	 * A moins qu'il n'y ait plus de place sur celle-ci, une pièce est placée sur la colonne.
	 * @param col Colonne sur laquelle le joueur veut placer sa pièce
	 * @return false si la partie est finie ou s'il n'y a plus de place sur la ligne, true si la pièce a été placée
	 */
	public boolean placerPiece(int col) {
		boolean correct = false;
		if(!this.fini) {
			int ligne = plateau.getPiecePlusHauteDispo(col);
			if(ligne != -1) {
				plateau.placer(ligne, col, this.convertirJoueurActifEnInt());
				correct = true;
			}
		}
		return correct;
	}
	
	/**
	 * Passe les tours.
	 * La fonctione vérifie s'il y a un gagnant. Si non, le joueur actif est changé. Si oui, le jeu est fini.
	 * @return Le numero correspondant à un des joueurs, ou 0 s'il n'y a pas de gagnant.
	 */
	public int prochainTour() {
		int winner = plateau.verifier();
		if(winner == 0) changerJoueur();
		else this.fini = true;
		return winner;
	}
	
	/**
	 * Change le joueur actif d'un joueur à l'autre
	 */
	private void changerJoueur() {
		if(joueurActif.equals(j1)) {
			joueurActif = j2;
		}
		else joueurActif = j1;
	}

}
