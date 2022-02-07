package client;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

import common.Message;
import server.ConnectedClient;

/**
 * @author Noah COUPEY
 * @author Mathieu GARDETTE
 *
 */
public class ClientReceive implements Runnable {

	private Client client;
	private Socket socket;
	private ObjectInputStream in;
	
	/**
	 * Constructeur de la classe ClientReceive.
	 * Initialisation des variables de classes pass�es au pr�alable en param�tre
	 * @param client
	 * @param socket
	 */
	public ClientReceive(Client client, Socket socket) {
		super();
		this.client = client;
		this.socket = socket;
	}
	
	/**
	 * Run du thread de la classe Client.java
	 * R�cup�ration des informations envoy�es depuis le serveur
	 * Si c'est un Message on fait appel � messageReceived - Ce sera pour l'affichage du nouveau message d'un des clients
	 * Si c'est un String on fait appel � clientsListReceived - Ce sera quand un utilisateur se d�connecte/connecte pour l'ajouter/supprimer � la liste
	 */
	@Override
	public void run() {
		
		try {
			in = new ObjectInputStream(socket.getInputStream());
			boolean isActive = true;
			Object object;
			while(isActive) {
				try {
					object = in.readObject();
					if(object instanceof Message) this.client.messageReceived((Message) object);
					else if(object instanceof String) this.client.clientsListReceived((String) object);
				} catch(SocketException e) {
					isActive = false;
				} catch(EOFException e) {
					isActive = false;
				}
			}
			client.disconnect();
			client.getView().disableSend();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	
}
