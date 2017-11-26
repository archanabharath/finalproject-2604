package com.itm.food.model;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.itm.food.model.db.MySQLDBConnector;

public abstract class AbstractDB {

	private static final Logger log = Logger.getLogger(AbstractDB.class);

	MySQLDBConnector connector = new MySQLDBConnector();

	public Connection getDBConnection() throws ClassNotFoundException, SQLException {
		log.debug("Getting DB Connection.");
		return connector.getConnection();
	}

	public void closeConnection() throws SQLException {
		connector.closeConnection();
		log.debug("Connection closed.");
	}

	public void setAutoCommit(boolean autoCommit) throws ClassNotFoundException, SQLException {
		log.debug("Auto commit set to: " + autoCommit);
		connector.getConnection().setAutoCommit(autoCommit);
	}

	public void commit() throws ClassNotFoundException, SQLException {
		log.debug("Committing transaction.");
		connector.getConnection().commit();
	}

	public void rollback() throws ClassNotFoundException, SQLException {
		log.debug("Rollback transaction");
		connector.getConnection().rollback();
	}

}
