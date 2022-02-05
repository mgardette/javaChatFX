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
	
	public void disconnect() throws IOException {
		if(in != null) in.close();
		out.close();
		socket.close();
	}
	
	public void messageReceived(Message mess) {
		view.printNewMessage(mess);
	}
	
	public PublicChatController getView() {
		return view;
	
}
	public void clientsListReceived(String listClients) {
		view.printClientsList(listClients);
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
