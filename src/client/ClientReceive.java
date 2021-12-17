package client;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

import common.Message;

public class ClientReceive implements Runnable {

	private Client client;
	private Socket socket;
	private ObjectInputStream in;
	
	public ClientReceive(Client client, Socket socket) {
		super();
		this.client = client;
		this.socket = socket;
	}

	@Override
	public void run() {
		
		try {
			in = new ObjectInputStream(socket.getInputStream());
			boolean isActive = true;
			Message mess;
			while(isActive) {
				mess = (Message) in.readObject();
				if(mess != null) this.client.messageReceived(mess);
				else isActive = false;
			}
			client.disconnectedServer();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
