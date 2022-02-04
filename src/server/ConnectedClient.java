package server;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

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
		while (!socket.isClosed()) {
			try {
				Object received = in.readObject();
				if(received.getClass() == Message.class) {
					server.broadcastMessage((Message) received);
				}
				else if(received.getClass() == String.class) {
					setPseudo((String) received);
					server.broadcastMessage(new Message(this.pseudo, " vient de se connecter."));
				}
			} catch (SocketException e) {

			} catch (EOFException e) {
				server.disconnectedClient(this);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void sendMessage(Message mess) {
		try {
			this.out.writeObject(mess);
			this.out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void closeClient() {
		try {
			this.in.close();
			this.out.close();
			this.socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getPseudo() {
		return pseudo;
	}
	
	private void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}
	
	public int getCount() {
		return idCounter;
	}
}
