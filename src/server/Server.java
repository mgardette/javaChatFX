package server;

import java.io.IOException;
import java.net.BindException;
import java.util.ArrayList;

import client.Client;
import common.Message;

/**
 * @author Mathieu GARDETTE
 * @author Noah COUPEY
 *
 */
public class Server {

	private int port;
	private ArrayList<ConnectedClient> clients;
	private Connection connection;
	private Thread threadConnection;
	
	/**
	 * Constructeur de la classe server
	 * Initialisation de la variable port grâce au paramètre du même nom
	 * Initialisation des variables de classe clients, threadConnection et connection
	 * @param port
	 * @throws BindException
	 * @throws IOException
	 */
	public Server(int port) throws BindException, IOException {
		this.port = port;
		this.clients = new ArrayList<ConnectedClient>();
		connection = new Connection(this);
		threadConnection = new Thread(connection);
		threadConnection.start();
	}
	
	/**
	 * Récupération du nombre de personnes connectées au serveur
	 * @return
	 */
	public int getNumClients() {
		return clients.size();
	}
	
	/**
	 * Getter du port du serveur
	 * @return
	 */
	public int getPort() {
		return port;
	}
	
	/**
	 * Setter du port du serveur grâce à la variable passée en paramètre
	 * @param port
	 */
	public void setPort(int port) {
		this.port = port;
	}

	/**
	 * Méthode permettant l'ajout d'un client à la liste de clients connectés
	 * @param newClient
	 */
	public void addClient(ConnectedClient newClient) {
		this.clients.add(newClient);
	}

	/**
	 * Getter de l'adresse du serveur
	 * @return
	 */
	public String getAddress() {
		return connection.getAddress();
	}

	/**
	 * Méthode permettant d'envoyer la liste des personnes connectées à tous les utilisateurs
	 * @param pseudo
	 */
	public void broadcastList(String pseudo) {
		String listToString = "";
		// Passage de la liste en string car sinon impossible d'envoyer
		for(ConnectedClient client: this.clients) {
			listToString = listToString + ";" + client.getPseudo();
		}
		for(ConnectedClient client: clients) {
			client.sendMessage(listToString);
		}
	}
	
	/**
	 * Méthode permettant d'envoyer le message passer en paramètre à tous les utilisateurs, sauf l'envoyeur
	 * @param mess
	 */
	public void broadcastMessage(Message mess) {
		for(ConnectedClient client: clients) {
			if(!client.getPseudo().equals(mess.getSender())) {
				client.sendMessage(mess);
			}
		}
	}
	
	/**
	 * Méthode permettant de fermer la connecion au client, de l'enlever de la liste des clients et d'envoyer un message
	 * de déconnexion à tous les utilisateurs.
	 * Renvoie la liste des utilisateurs pour rafraîchir cette dernière.
	 * @param disClient
	 */
	public void disconnectedClient(ConnectedClient disClient) {
		disClient.closeClient();
		clients.remove(disClient);
		Message mess = new Message(disClient.getPseudo(), " est déconnecté.");
		broadcastMessage(mess);
		broadcastList(null);
	}
	
	/**
	 * Méthode permettant de fermer toutes les connexions au serveur à la fermeture de ce dernier, puis ferme la connexion du serveur
	 */
	public void closeServer() {
		for(ConnectedClient client : clients) {
			client.closeClient();
		}
		try {
			connection.closeServer();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Méthode permettant de fermer la connexion du client
	 * @param client
	 */
	public void closeClient(Client client) {
		for(ConnectedClient coClient : clients) {
			if(coClient.getPseudo().equals(client.getPseudo())){
				disconnectedClient(coClient);
				break;
			}
		}
	}
	
}
