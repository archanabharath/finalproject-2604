package com.itm.food.model.db;

import java.sql.Connection;
import java.sql.SQLException;

public interface IDBConnector {
	
	public void createConnection() throws ClassNotFoundException, SQLException;
	
	public Connection getConnection();
	
	public void closeConnection() throws SQLException;

}