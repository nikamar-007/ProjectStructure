package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
	private static final String URL = "jdbc:mysql://localhost:3306/work_distribution_system";
	private static final String USER = "root";
	private static final String PASSWORD = "";
	private static DBConnection instance;
	private Connection connection;
	
	private DBConnection() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			this.connection = DriverManager.getConnection(URL, USER, PASSWORD);
		} catch (ClassNotFoundException | SQLException e) {
			throw new RuntimeException("Ошибка подключения к базе данных: " + e.getMessage());
		}
	}
	
	public static synchronized DBConnection getInstance() {
		if (instance == null)
			instance = new DBConnection();
		return instance;
	}
	
	public Connection getConnection() {
		try {
			if (connection == null || connection.isClosed())
				connection = DriverManager.getConnection(URL, USER, PASSWORD);
		} catch (SQLException e) {
			throw new RuntimeException("Ошибка восстановления соединения: " + e.getMessage());
		}
		return connection;
	}
}