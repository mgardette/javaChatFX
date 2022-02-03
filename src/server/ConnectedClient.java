package server;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import common.Message;

public class ConnectedClient implements Runnable {

	private static int idCounter = 0;
	private Server server;
	private Socket socket;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private String pseudo;
	
	public ConnectedClient(Server server, Socket socket) {
		super();
		this.server = server;
		this.socket = socket;
		idCounter++;
		try {
			out = new ObjectOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		try {
			in = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		boolean isActive = true;
		while (isActive) {
			try {
				Object received = in.readObject();
				if(received.getClass() == Message.class) {
					server.broadcastMessage((Message) received);
				}
				else if(received.getClass() == String.class) {
					setPseudo((String) received);
					server.broadcastMessage(new Message(this.pseudo, " vient de se connecter."));
					server.broadcastList();
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		
		}
	}
	
	public void sendMessage(Object object) {
		try {
			this.out.writeObject(object);
			this.out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void closeClient() {
		try {
			this.in.close();
			this.out.close();
			this.socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getPseudo() {
		return pseudo;
	}
	
	private void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}
	
}
