package com.itm.food.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.itm.food.dao.AbstractDomain;
import com.itm.food.dao.Restaurant;
import com.itm.food.model.db.MySQLQuery;

public class RestaurantDB extends AbstractDB implements IDBAccess {

	private static final Logger log = Logger.getLogger(RestaurantDB.class);

	@Override
	public <T extends AbstractDomain> int add(T object) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public <T extends AbstractDomain> void update(T object) throws Exception {
		// TODO Auto-generated method stub

	}

	
	@SuppressWarnings("unchecked")
	@Override
	public <T extends AbstractDomain> T find(int id) throws Exception {
		log.debug("Getting Restaurant for the RestaurantId: " + id);
		ResultSet rsRestaurant;
		Restaurant restaurant = new Restaurant();
		try {
			PreparedStatement preparestatement = this.getDBConnection()
					.prepareStatement(MySQLQuery.SQL_RESTAURANT_SELECT);
			preparestatement.setInt(1, id);
			rsRestaurant = preparestatement.executeQuery();
			while (rsRestaurant.next()) {
				restaurant.setRestaurantId(rsRestaurant.getInt(1));
				restaurant.setRestaurantName(rsRestaurant.getString(2));
				restaurant.setRestaurantDescription(rsRestaurant.getString(3));
				restaurant.setAddress1(rsRestaurant.getString(4));
				restaurant.setAddress2(rsRestaurant.getString(5));
				restaurant.setCity(rsRestaurant.getString(6));
				restaurant.setZipcode(rsRestaurant.getInt(7));
				restaurant.setRating(rsRestaurant.getInt(8));
				restaurant.setPhone(rsRestaurant.getString(9));
				restaurant.setEmail(rsRestaurant.getString(10));
				restaurant.setLatitude(rsRestaurant.getDouble(11));
				restaurant.setLongitude(rsRestaurant.getDouble(12));
			}
		} catch (SQLException e) {
			log.error(e.getMessage());
		}
		return (T) restaurant;
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
			int searchLimit = 20;
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
					+ "PHONE,EMAIL,LATITUDE,LONGITUDE FROM ofod.ofod_restaurant WHERE ZIPCODE IN"
					+ parameterBuilder.toString() + " ORDER BY RATING DESC LIMIT ?";

			PreparedStatement preparedstatement = this.getDBConnection().prepareStatement(sqlQuery);
		
			for (int i = 1; i < zipcodes.size() + 1; i++) {
				preparedstatement.setInt(i, (int) zipcodes.get(i - 1));
			}
			
			preparedstatement.setInt(zipcodes.size()+1, searchLimit);
			// preparedstatement.setString(1, StringUtils.join(zipcodes, ","));
			ResultSet restaurantsResultSet;
			restaurantsResultSet = preparedstatement.executeQuery();

			while (restaurantsResultSet.next()) {
				Restaurant newRestaurant = new Restaurant();

				// Fetching the restaurants column data and setting it to the
				// respective dao object
				newRestaurant.setRestaurantId(restaurantsResultSet.getInt(1));
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

		}
		return restaurantsList;

	}

	public List<Restaurant> getTop3Restaurants() {

		log.debug("getting top 3 restaurants from RestaurantDB");
		List<Restaurant> top3RestaurantsList = new ArrayList<Restaurant>();
		// RESTAURANT_ID,RESTAURANT_NAME,DESCRIPTION,ADDRESS1,ADDRESS2,CITY,ZIPCODE,PHONE,EMAIL,
		// RATING
		try {
			PreparedStatement preparestatement = this.getDBConnection()
					.prepareStatement(MySQLQuery.SQL_FETCH_TOP_3_RESTAURANTS);
			ResultSet rsTopRestaurants;
			rsTopRestaurants = preparestatement.executeQuery();
			while (rsTopRestaurants.next()) {
				Restaurant restaurant = new Restaurant();
				restaurant.setRestaurantId(rsTopRestaurants.getInt(1));
				restaurant.setRestaurantName(rsTopRestaurants.getString(2));
				restaurant.setRestaurantDescription(rsTopRestaurants.getString(3));
				restaurant.setAddress1(rsTopRestaurants.getString(4));
				restaurant.setAddress2(rsTopRestaurants.getString(5));
				restaurant.setCity(rsTopRestaurants.getString(6));
				restaurant.setZipcode(rsTopRestaurants.getInt(7));
				restaurant.setPhone(rsTopRestaurants.getString(8));
				restaurant.setEmail(rsTopRestaurants.getString(9));
				restaurant.setRating(rsTopRestaurants.getInt(10));
				
				top3RestaurantsList.add(restaurant);

			}
		} catch (ClassNotFoundException e) {
			log.error(e.getMessage());
		} catch (SQLException e) {
			log.error(e.getMessage());
		}
		return top3RestaurantsList;
	}

}
