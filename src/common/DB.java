package common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DB {
	
	private static DB instance;
	
	private static Connection connection;
	
	/**
	 * M�thode permettant la connexion � la base de donn�es
	 * @throws SQLException
	 */
	private DB() throws SQLException {
		connection = DriverManager.getConnection("jdbc:oracle:thin:@iutdoua-ora.univ-lyon1.fr:1521:cdb1", "p2110997", "623816");
	}
	
	/**
	 * Retourne l'instance unique de la base, la cr�e s'il n'y en a pas
	 * @return L'instance de la base
	 * @throws SQLException
	 */
	public static DB getInstance() throws SQLException {
		if(instance == null) {
			instance = new DB();
		}
		return instance;
	}

	/**
	 * @return La connexion � la base
	 */
	public static Connection getConnection() {
		return connection;
	}
	
}
