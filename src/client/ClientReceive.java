package client;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.SocketException;

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
				try {
					mess = (Message) in.readObject();
					this.client.messageReceived(mess);
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
