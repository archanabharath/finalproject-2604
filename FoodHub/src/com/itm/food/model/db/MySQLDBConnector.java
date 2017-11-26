package com.itm.food.model.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.log4j.Logger;

public class MySQLDBConnector implements IDBConnector {

	private static final Logger log = Logger.getLogger(MySQLDBConnector.class);

	private static Connection mySqlConnection;

	public void createConnection() throws ClassNotFoundException, SQLException {
		String driver = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3306/ofod?autoReconnect=true&useSSL=false";
		String username = "root";
		String password = "password";

		log.debug("Connection URL - " + url);

		try {
			Class.forName(driver);
			mySqlConnection = DriverManager.getConnection(url, username, password);
			
		} catch (ClassNotFoundException e) {
			log.error(e.getMessage());
			throw e;
		} catch (SQLException se) {
			log.error(se.getMessage());
			throw se;
		}
	}

	@Override
	public Connection getConnection() throws ClassNotFoundException, SQLException {
		if (null == mySqlConnection) {
			log.debug("Creating DB Connection...");
			createConnection();
			log.debug("DB Connection created...");
		}
		return mySqlConnection;
	}

	@Override
	public void closeConnection() throws SQLException {
		try {
			mySqlConnection.close();
		} catch (SQLException e) {
			log.debug(e.getMessage());
			throw e;
		}
		log.debug("DB Connection closed");
	}

}
