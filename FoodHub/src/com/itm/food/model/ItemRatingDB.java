package com.itm.food.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.log4j.Logger;

import com.itm.food.dao.AbstractDomain;
import com.itm.food.dao.ItemRating;
import com.itm.food.model.db.MySQLQuery;
import com.mysql.jdbc.Statement;

public class ItemRatingDB extends AbstractDB implements IDBAccess {

	private static final Logger log = Logger.getLogger(ItemRatingDB.class);

	@Override
	public <T extends AbstractDomain> int add(T object) throws Exception {
		ItemRating userRating = (ItemRating) object;
		log.debug("Adding UserRating for Item: " + userRating.getItemId());
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

			PreparedStatement psItemRating = this.getDBConnection().prepareStatement(MySQLQuery.SQL_ITEM_RATING_INSERT);
			psItemRating.setInt(1, userRating.getRatingId());
			psItemRating.setInt(2, userRating.getItemId());

			psItemRating.execute();

			psItemRating.close();

		} catch (Exception ex) {
			log.error(ex.getMessage());
			throw ex;
		}
		log.debug("Added UserRating for Item: " + userRating.getRatingId() + " RatingId: " + userRating.getRatingId());
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
