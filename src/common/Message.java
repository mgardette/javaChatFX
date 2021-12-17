package common;

import java.io.Serializable;

public class Message implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String sender;
	private String content;
	

	public Message(String sender, String content) {
		super();
		this.sender = sender;
		this.content = content;
	}

	@Override
	public String toString() {
		return "\n" + sender + " : " + content;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	
}
