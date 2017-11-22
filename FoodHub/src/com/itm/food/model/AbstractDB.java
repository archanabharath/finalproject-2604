package com.itm.food.model;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.itm.food.model.db.MySQLDBConnector;

public abstract class AbstractDB {

	private static final Logger log = Logger.getLogger(AbstractDB.class);

	MySQLDBConnector connector = new MySQLDBConnector();

	public Connection getDBConnection() throws ClassNotFoundException, SQLException {
		log.info("Getting DB Connection.");
		connector.createConnection();
		log.info("DB Connection created");
		return connector.getConnection();
	}

	public void closeConnection() throws SQLException {
		connector.closeConnection();
		log.info("Connection closed.");
	}

}
