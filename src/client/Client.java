package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import common.Message;
import gui.PublicChatController;
import server.ConnectedClient;

/**
 * @author Mathieu GARDETTE
 * @author Noah COUPEY
 *
 */
public class Client {

	private String address;
	private int port;
	private Socket socket;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	private String pseudo;
	private PublicChatController view;
	private String listClients;
	
	/**
	 * Constructeur de la classe Client en prenant en parametre l'adresse et le port du client qui vont former la socket.
	 * Envoie ici du pseudo au serveur et créer un thread sur la classe clientReceive pour permettre de recevoir les informations du serveur
	 * @param address du client
	 * @param port du client
	 * @param pseudo du client
	 * @param view du chat
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	public Client(String address, int port, String pseudo, PublicChatController view) throws UnknownHostException, IOException {
		super();
		this.address = address;
		this.port = port;
		this.pseudo = pseudo;
		this.view = view;
		
		this.socket = new Socket(address, port);
		out = new ObjectOutputStream(socket.getOutputStream());
		
		this.getOut().writeObject(pseudo);
		this.getOut().flush();
		
		Thread threadReceive = new Thread(new ClientReceive(this, socket));
		threadReceive.start();	
	}
	
	/**
	 * Coupe le contact entre le serveur et l'application sur la deconnexion du client
	 * @throws IOException
	 */
	public void disconnect() throws IOException {
		if(in != null) in.close();
		out.close();
		socket.close();
	}
	
	/**
	 * Getter de la liste des clients
	 * @return
	 */
	public String getListClients() {
		return this.listClients;
	}
	
	/**
	 * Méthode envoyant à la vue le message à afficher
	 * @param mess
	 */
	public void messageReceived(Message mess) {
		view.printNewMessage(mess);
	}
	
	/*
	 * Récupération de la vue correspondante au chat
	 */
	public PublicChatController getView() {
		return view;
	
	}
	
	/**
	 * Envoie de la liste de clients, passée en paramètre, à la vue
	 * @param listClients
	 */
	public void clientsListReceived(String listClients) {
		this.listClients = listClients;
		if(this.view != null) {
			view.printClientsList();
		}
	}

	
	/**
	 * Getter de la variable de classe socket
	 * @return
	 */
	public Socket getSocket() {
		return socket;
	}

	/**
	 * Getter de la variable de classe out
	 * @return
	 */
	public ObjectOutputStream getOut() {
		return out;
	}
	
	/**
	 * Getter du pseudo
	 * @return
	 */
	public String getPseudo() {
		return pseudo;
	}

}
