package server;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

import common.Message;

/**
 * @author Noah COUPEY
 * @author Mathieu GARDETTE
 *
 */
public class ConnectedClient implements Runnable {

	private static int idCounter = 0;
	private Server server;
	private Socket socket;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private String pseudo;
	
	/**
	 * Constructeur de la classe connectedClient
	 * @param server
	 * @param socket
	 */
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
	
	/**
	 * Méthode permettant l'envoie de message à la connexion d'un client
	 */
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
					server.broadcastList(this.pseudo);
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
	
	/**
	 * Méthode permettant l'envoie d'un objet passé en paramètre
	 * @param object
	 */
	public void sendMessage(Object object) {
		try {
			this.out.writeObject(object);
			this.out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Méthode permettant la fermeture de la connexion du client
	 */
	public void closeClient() {
		try {
			this.in.close();
			this.out.close();
			this.socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Getter du pseudo du client
	 * @return
	 */
	public String getPseudo() {
		return pseudo;
	}
	
	/**
	 * Setter du pseudo du client
	 * @param pseudo
	 */
	private void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}
	
	/**
	 * Getter du compteur
	 * @return
	 */
	public int getCount() {
		return idCounter;
	}
}
