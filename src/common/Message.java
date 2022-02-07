package common;

import java.io.Serializable;

/**
 * @author Noah COUPEY
 * @author Mathieu GARDETTE
 *
 */
public class Message implements Serializable {
	
	/**
	 * Permet d'envoyer l'objet � travers des socket
	 */
	private static final long serialVersionUID = 1L;
	private String sender;
	private String content;
	

	/**
	 * @param sender Pseudo du client qui envoie le message
	 * @param content Contenu du message
	 */
	public Message(String sender, String content) {
		super();
		this.sender = sender;
		this.content = content;
	}

	@Override
	public String toString() {
		return "\n" + sender + " : " + content;
	}

	/**
	 * @return Le pseudo du client qui a �crit le message
	 */
	public String getSender() {
		return sender;
	}

	/**
	 * Change le pseudo li� au message
	 * @param sender Le pseudo de celui qui �crit le message
	 */
	public void setSender(String sender) {
		this.sender = sender;
	}

	
}
