package client;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

import common.Message;

/**
 * @author Noah COUPEY
 *
 */
public class ClientSend implements Runnable {
	
	private Socket socket;
	private ObjectOutputStream out;
	
	/**
	 * Constructeur de la classe ClientSend
	 * Initialisation des variables de classes passées au préalable en paramètres
	 * @param socket
	 * @param out
	 */
	public ClientSend(Socket socket, ObjectOutputStream out) {
		super();
		this.socket = socket;
		this.out = out;
	}
	
	/**
	 * Envoie du nouveau message au serveur pour qu'ensuite le serveur l'envoie à tous les utilisateurs
	 */
	@Override
	public void run() {
		Scanner sc = new Scanner(System.in);
		boolean isActive = true;
		while(isActive) {
			System.out.print("Votre message >> ");
			String m = sc.nextLine();
			if(m.equals("exit")) {
				isActive = false;
				sc.close();
			}
			else {
				Message mess = new Message("client", m);
				try {
					out.writeObject(mess);
					out.flush();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}
	
}
