package server;

import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class Connection implements Runnable {
	
	private Server server;
	private ServerSocket serverSocket;

	public Connection(Server server) throws BindException, IOException {
		super();
		this.server = server;
		this.serverSocket = new ServerSocket(server.getPort());
	}

	@Override
	public void run() {
		while(!serverSocket.isClosed()) {
			Socket sockNewClient;
			try {
				sockNewClient = serverSocket.accept();
				ConnectedClient client = new ConnectedClient(server, sockNewClient);
				server.addClient(client);
				Thread threadClient = new Thread(client);
				threadClient.start();
			} catch (SocketException e) {
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}

	public void closeServer() throws IOException {
		this.serverSocket.close();
	}

}
