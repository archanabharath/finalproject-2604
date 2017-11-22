package com.itm.food.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.itm.food.dao.AbstractDomain;
import com.itm.food.model.db.MySQLQuery;

public class RestaurantDB extends AbstractDB implements IDBAccess {

	private static final Logger log = Logger.getLogger(RestaurantDB.class);

	@Override
	public <T extends AbstractDomain> String add(T object) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends AbstractDomain> void update(T object) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public <T extends AbstractDomain> T find(String id) throws Exception {

		return null;
	}

	@Override
	public <T extends AbstractDomain> void delete(T object) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(String id) throws Exception {
		// TODO Auto-generated method stub

	}

	public ArrayList<String> searchByZip(int zipcode) throws SQLException {
		log.debug("Searching restaurant by zip");
		ArrayList<String> restaurantsList = new ArrayList<String>();
		try {
			PreparedStatement preparedstatement = this.getDBConnection()
					.prepareStatement(MySQLQuery.SQL_GET_RESTAURANTS_BY_ZIP);
			preparedstatement.setInt(1, zipcode);
			ResultSet restaurantsByZip;
			restaurantsByZip = preparedstatement.executeQuery();
			while (restaurantsByZip.next()) {
				restaurantsList.add(restaurantsByZip.getString(1));

			}
			preparedstatement.close();
		} catch (Exception e) {
			log.error(e.getMessage());

		} finally {
			this.closeConnection();
		}
		return restaurantsList;

	}

}
