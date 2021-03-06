package com.itm.food.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.itm.food.dao.AbstractDomain;
import com.itm.food.dao.Restaurant;

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

	public ArrayList<Restaurant> searchByZip(List<Integer> zipcodes) throws SQLException {
		log.debug("Searching restaurant for zip: " + zipcodes);
		ArrayList<Restaurant> restaurantsList = new ArrayList<Restaurant>();

		// Retrieving columns
		// RESTAURANT_ID,RESTAURANT_NAME,ADDRESS1,ADDRESS2,CITY,RATING,PHONE,EMAIL
		// from
		// ofod.ofod_restaurant table
		try {
			
			StringBuilder parameterBuilder = new StringBuilder();
	        parameterBuilder.append(" (");
	        for (int i = 0; i < zipcodes.size(); i++) {
	            parameterBuilder.append("?");
	            if (zipcodes.size() > i + 1) {
	                parameterBuilder.append(",");
	            }
	        }
	        parameterBuilder.append(")");


	        String sqlQuery = "SELECT RESTAURANT_ID,RESTAURANT_NAME,DESCRIPTION,ADDRESS1,ADDRESS2,CITY,ZIPCODE,RATING,"
			+ "PHONE,EMAIL,LATITUDE,LONGITUDE FROM ofod.ofod_restaurant WHERE ZIPCODE IN" + parameterBuilder.toString() + " ORDER BY RATING DESC";
			
			PreparedStatement preparedstatement = this.getDBConnection()
					.prepareStatement(sqlQuery);
			for (int i = 1; i < zipcodes.size() + 1; i++) {
				preparedstatement.setInt(i, (int) zipcodes.get(i - 1));
	        }
			//preparedstatement.setString(1, StringUtils.join(zipcodes, ","));
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
