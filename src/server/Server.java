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
		Message mess = new Message(String.valueOf(newClient.getId()), " vient de se connecter");
		broadcastMessage(mess, newClient.getId());
	}
	
	public void broadcastMessage(Message mess, int id) {
		for(ConnectedClient client: clients) {
			if(client.getId() != id) {
				client.sendMessage(mess);
			}
		}
	}
	
	public void disconnectedClient(ConnectedClient disClient) {
		disClient.closeClient();
		clients.remove(disClient);
		Message mess = new Message(String.valueOf(disClient.getId()), " vient de se déconnecter");
		broadcastMessage(mess, disClient.getId());
	}
	
}
