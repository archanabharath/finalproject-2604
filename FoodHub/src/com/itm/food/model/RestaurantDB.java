package com.itm.food.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.itm.food.dao.AbstractDomain;
import com.itm.food.dao.Restaurant;
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

	public ArrayList<Restaurant> searchByZip(int zipcode) throws SQLException {
		log.debug("Searching restaurant for zip: " + zipcode);
		ArrayList<Restaurant> restaurantsList = new ArrayList<Restaurant>();

		// Retrieving columns
		// RESTAURANT_ID,RESTAURANT_NAME,ADDRESS1,ADDRESS2,CITY,RATING,PHONE,EMAIL
		// from
		// ofod.ofod_restaurant table
		try {
			PreparedStatement preparedstatement = this.getDBConnection()
					.prepareStatement(MySQLQuery.SQL_GET_RESTAURANTS_BY_ZIP);
			preparedstatement.setInt(1, zipcode);
			ResultSet restaurantsResultSet;
			restaurantsResultSet = preparedstatement.executeQuery();

			while (restaurantsResultSet.next()) {
				Restaurant newRestaurant = new Restaurant();

				// Fetching the restaurants column data and setting it to the
				// respective dao object 
				newRestaurant.setRestaurantId(restaurantsResultSet.getString(1));
				newRestaurant.setRestaurantName(restaurantsResultSet.getString(2));
				newRestaurant.setRestaurantDescription(restaurantsResultSet.getString(3));
				newRestaurant.setAddress1(restaurantsResultSet.getString(4));
				newRestaurant.setAddress2(restaurantsResultSet.getString(5));
				newRestaurant.setCity(restaurantsResultSet.getString(6));
				newRestaurant.setZipcode(restaurantsResultSet.getInt(7));
				newRestaurant.setRating(restaurantsResultSet.getInt(8));
				newRestaurant.setPhone(restaurantsResultSet.getString(9));
				newRestaurant.setEmail(restaurantsResultSet.getString(10));
				newRestaurant.setLatitude(restaurantsResultSet.getDouble(11));
				newRestaurant.setLongitude(restaurantsResultSet.getDouble(12));

				// add all restaurants to the RestaurantsList ArrayList
				restaurantsList.add(newRestaurant);
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
