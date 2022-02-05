package client;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.SocketException;
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
				try {
					object = in.readObject();
					if(object instanceof Message) this.client.messageReceived((Message) object);
					else if(object instanceof String) this.client.clientsListReceived((String) object);
				} catch(SocketException e) {
					isActive = false;
				} catch(EOFException e) {
					isActive = false;
				}
			}
			client.disconnect();
			client.getView().disableSend();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	
}
