package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.ArrayList;

import common.Message;
import server.ConnectedClient;

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
			Object object;
			while(isActive) {
				object = (Object) in.readObject();
				if (object instanceof Message) {
					Message mess = (Message) object;
					if(mess != null) this.client.messageReceived(mess);
				}
				else if(object instanceof String) {
					String listClients = (String) object;
					this.client.clientsListReceived(listClients);
				}
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
