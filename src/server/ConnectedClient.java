package server;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import common.Message;

public class ConnectedClient implements Runnable {

	private static int idCounter = 0;
	private int id;
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
		id = idCounter;
		try {
			out = new ObjectOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Nouvelle connexion, id = " + id);
	}

	@Override
	public void run() {
		try {
			in = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		boolean isActive = true;
		while (isActive) {
			try {
				Message mess = (Message) in.readObject();
				if(mess == null) {
					server.disconnectedClient(this);
					isActive = false;
				}
				else {
					mess.setSender(String.valueOf(id));
					server.broadcastMessage(mess, id);
				}
				
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}
	
	public void sendMessage(Message mess) {
		try {
			this.out.writeObject(mess);
			this.out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void closeClient() {
		try {
			this.in.close();
			this.out.close();
			this.socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public int getId() {
		return id;
	}
	
}
