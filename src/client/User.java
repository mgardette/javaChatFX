package client;

public class User {
	private String pseudo;
	private String mdp;
	private String photoProfil;
	private String desc;
	
	/**
	 * Constructeur de la classe User qui représente simplement un utilisateur de l'application. Permet l'accès au détail du profil
	 * @param pseudo
	 * @param mdp
	 * @param photoProfil
	 * @param desc
	 */
	public User(String pseudo, String mdp, String photoProfil, String desc) {
		super();
		this.pseudo = pseudo;
		this.mdp = mdp;
		this.photoProfil = photoProfil;
		this.desc = desc;
	}
	
	/**
	 * Getter du pseudo, renvoie un string
	 * @return
	 */
	public String getPseudo() {
		return pseudo;
	}

	/**
	 * Setter du pseudo, prend en paramètre un string
	 * @param desc
	 */
	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}
	
	/**
	 * Getter du mot de passe, renvoie un string
	 * @return
	 */
	public String getMdp() {
		return mdp;
	}

	/**
	 * Setter du mot de passe, prend en paramètre un string
	 * @param desc
	 */
	public void setMdp(String mdp) {
		this.mdp = mdp;
	}
	
	/**
	 * Getter de la photo de profil, renvoie un string
	 * @return
	 */
	public String getPhotoProfil() {
		return photoProfil;
	}

	/**
	 * Setter de la photo de profil, prend en paramètre un string
	 * @param desc
	 */
	public void setPhotoProfil(String photoProfil) {
		this.photoProfil = photoProfil;
	}

	/**
	 * Getter de la description, renvoie un string
	 * @return
	 */
	public String getDesc() {
		return desc;
	}
	
	/**
	 * Setter de la description, prend en paramètre un string
	 * @param desc
	 */
	public void setDesc(String desc) {
		this.desc = desc;
	}
		
}
