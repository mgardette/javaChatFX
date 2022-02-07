package server;

import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

/**
 * @author Noah COUPEY
 * @author Mathieu GARDETTE
 *
 */
public class Connection implements Runnable {
	
	/**
	 * Serveur à mettre sur écoute
	 */
	private Server server;
	
	/**
	 * Socket du serveur
	 */
	private ServerSocket serverSocket;

	/**
	 * Permet d'initialiser une socket sur un serveur grâce au port
	 * @param server Serveur sur lequel placer une socket
	 * @throws BindException
	 * @throws IOException
	 */
	public Connection(Server server) throws BindException, IOException {
		super();
		this.server = server;
		this.serverSocket = new ServerSocket(server.getPort());
	}

	/**
	 * Thread qui écoute le port donné afin d'accepter de nouveaux clients qui se connectent sur celui-ci
	 */
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

	/**
	 * Ferme le Socket du serveur
	 * @throws IOException
	 */
	public void closeServer() throws IOException {
		this.serverSocket.close();
	}
	
	/**
	 * @return L'adresse du serveur sous forme de string
	 */
	public String getAddress() {
		return serverSocket.getInetAddress().getHostAddress();
	}

}
