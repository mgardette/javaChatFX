package server;

import java.util.ArrayList;

import common.Message;

public class Server {

	private int port;
	private ArrayList<ConnectedClient> clients;
	
	public Server(int port) {
		this.port = port;
		this.clients = new ArrayList<ConnectedClient>();
		Thread threadConnection = new Thread(new Connection(this));
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
	
	public void broadcastList() {
		String listToString = "";
		// Passage de la liste en string car sinon impossible d'envoyer
		for(ConnectedClient client: this.clients) {
			listToString = client.getPseudo() + "\n";
		}
		for(ConnectedClient client: this.clients) {
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
		Message mess = new Message(disClient.getPseudo(), " vient de se déconnecter");
		broadcastMessage(mess);
		broadcastList();
	}
	
}
