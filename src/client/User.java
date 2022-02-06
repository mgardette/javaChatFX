package client;

public class User {
	private String pseudo;
	private String mdp;
	private String photoProfil;
	private String desc;
		
	public User(String pseudo, String mdp, String photoProfil, String desc) {
		super();
		this.pseudo = pseudo;
		this.mdp = mdp;
		this.photoProfil = photoProfil;
		this.desc = desc;
	}
	
	public String getPseudo() {
		return pseudo;
	}
	
	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}
	
	public String getMdp() {
		return mdp;
	}
	
	public void setMdp(String mdp) {
		this.mdp = mdp;
	}
	
	public String getPhotoProfil() {
		return photoProfil;
	}
	
	public void setPhotoProfil(String photoProfil) {
		this.photoProfil = photoProfil;
	}
	
	public String getDesc() {
		return desc;
	}
	
	public void setDesc(String desc) {
		this.desc = desc;
	}
		
}
