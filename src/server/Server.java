package server;

import java.io.IOException;
import java.net.BindException;
import java.util.ArrayList;

import client.Client;
import common.Message;

public class Server {

	private int port;
	private ArrayList<ConnectedClient> clients;
	private Connection connection;
	private Thread threadConnection;
	
	public Server(int port) throws BindException, IOException {
		this.port = port;
		this.clients = new ArrayList<ConnectedClient>();
		connection = new Connection(this);
		threadConnection = new Thread(connection);
		threadConnection.start();
	}
	
	public int getNumClients() {
		return clients.size();
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public void addClient(ConnectedClient newClient) {
		this.clients.add(newClient);
	}

	public String getAddress() {
		return connection.getAddress();
	}

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
	
	public void broadcastMessage(Message mess) {
		for(ConnectedClient client: clients) {
			if(!client.getPseudo().equals(mess.getSender())) {
				client.sendMessage(mess);
			}
		}
	}
	
	public void disconnectedClient(ConnectedClient disClient) {
		disClient.closeClient();
		clients.remove(disClient);
		Message mess = new Message(disClient.getPseudo(), " est déconnecté.");
		broadcastMessage(mess);
		broadcastList(null);
	}
	
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
	
	public void closeClient(Client client) {
		for(ConnectedClient coClient : clients) {
			if(coClient.getPseudo().equals(client.getPseudo())){
				disconnectedClient(coClient);
				break;
			}
		}
	}
	
}
