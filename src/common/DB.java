package common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DB {
	
	private static DB instance;
	
	private static Connection connection;
	
	private DB() throws SQLException {
		connection = DriverManager.getConnection("jdbc:oracle:thin:@iutdoua-ora.univ-lyon1.fr:1521:cdb1", "p2110997", "623816");
	}
	
	public static DB getInstance() throws SQLException {
		if(instance == null) {
			instance = new DB();
		}
		return instance;
	}

	public static Connection getConnection() {
		return connection;
	}
	
}
