package com.itm.food.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.log4j.Logger;

import com.itm.food.dao.AbstractDomain;
import com.itm.food.dao.RestaurantRating;
import com.itm.food.model.db.MySQLQuery;
import com.mysql.jdbc.Statement;

public class RestaurantRatingDB extends AbstractDB implements IDBAccess {

	private static final Logger log = Logger.getLogger(RestaurantRatingDB.class);

	@Override
	public <T extends AbstractDomain> int add(T object) throws Exception {
		RestaurantRating userRating = (RestaurantRating) object;
		log.debug("Adding UserRating for Retaurant: " + userRating.getRestaurantId());
		try {
			PreparedStatement psUserRating = this.getDBConnection().prepareStatement(MySQLQuery.SQL_USER_RATING_INSERT,
					Statement.RETURN_GENERATED_KEYS);
			psUserRating.setInt(1, userRating.getCustomerId());
			psUserRating.setDouble(2, userRating.getRating());
			psUserRating.executeUpdate();
			ResultSet rsReturnKey = psUserRating.getGeneratedKeys();
			while (rsReturnKey.next()) {
				userRating.setRatingId(rsReturnKey.getInt(1));
			}

			psUserRating.close();

			PreparedStatement psRestRating = this.getDBConnection()
					.prepareStatement(MySQLQuery.SQL_RESTAURANT_RATING_INSERT);
			psRestRating.setInt(1, userRating.getRatingId());
			psRestRating.setInt(2, userRating.getRestaurantId());

			psRestRating.execute();
			psRestRating.close();

		} catch (Exception ex) {
			log.error(ex.getMessage());
			throw ex;
		}
		log.debug("Added UserRating for Restaurant: " + userRating.getRestaurantId() + " RatingId: "
				+ userRating.getRatingId());
		return userRating.getRatingId();
	}

	@Override
	public <T extends AbstractDomain> void update(T object) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public <T extends AbstractDomain> T find(int id) throws Exception {
		// TODO Auto-generated method stub
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

}
