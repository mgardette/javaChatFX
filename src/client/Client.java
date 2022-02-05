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

public class Client {
	
	private String address;
	private int port;
	private Socket socket;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	private String pseudo;
	private PublicChatController view;
	private String listClients;
	
	public Client(String address, int port, String pseudo) throws UnknownHostException, IOException {
		super();
		this.address = address;
		this.port = port;
		this.pseudo = pseudo;
		
		this.socket = new Socket(address, port);
		out = new ObjectOutputStream(socket.getOutputStream());
		
		this.getOut().writeObject(pseudo);
		this.getOut().flush();
		
		Thread threadReceive = new Thread(new ClientReceive(this, socket));
		threadReceive.start();	
	}
	
	public void disconnectedServer() throws IOException {
		System.out.println("Le serveur s'est déconnecté.");
		if(in != null) in.close();
		out.close();
		socket.close();
		System.exit(0);
	}
	
	public String getListClients() {
		return this.listClients;
	}
	
	public void messageReceived(Message mess) {
		view.printNewMessage(mess);
	}
	
	public void clientsListReceived(String listClients) {
		this.listClients = listClients;
		if(this.view != null) {
			view.printClientsList();
		}
	}

	public void setView(PublicChatController view) {
		this.view = view;
	}

	public Socket getSocket() {
		return socket;
	}

	public ObjectOutputStream getOut() {
		return out;
	}
	
	public String getPseudo() {
		return pseudo;
	}

}
