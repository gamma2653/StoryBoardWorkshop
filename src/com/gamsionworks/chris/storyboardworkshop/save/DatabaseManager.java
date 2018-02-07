package com.gamsionworks.chris.storyboardworkshop.save;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {
	Connection conn;
	public static final String jdbc = "jdbc:sqlite:";
	public DatabaseManager(String dbname) {
		try {
			openConnection(jdbc+dbname);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void openConnection(String dbname) throws SQLException {
		conn = DriverManager.getConnection(jdbc+dbname);
	}
}
