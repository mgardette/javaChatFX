package common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DB {
	
	public static Connection connection;
	
	private DB() {
		try {
			connection = DriverManager.getConnection("jdbc:oracle:thin:@iutdoua-ora.univ-lyon1.fr:1521:cdb1", "p2110997", "623816");
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}
	}
	
	public static Connection getConnection() {
		return connection;
	}
	
	public ResultSet query(String sql) {
		try (Statement stm = connection.createStatement()) {
		      return stm.executeQuery(sql);
		} catch (SQLException throwables) {
			throwables.printStackTrace();
			return null;
		}
	}
	
}
