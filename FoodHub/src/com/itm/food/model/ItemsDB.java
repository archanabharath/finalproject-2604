package com.itm.food.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.itm.food.dao.AbstractDomain;
import com.itm.food.dao.Item;
import com.itm.food.model.db.MySQLQuery;

public class ItemsDB extends AbstractDB implements IDBAccess {

	private static final Logger log = Logger.getLogger(ItemsDB.class);

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

	public List<Item> getItemsByRestaurantId(String restaurantId) throws Exception {
		log.debug("getting Items for the RestaurantId: " + restaurantId);
		List<Item> itemList = new ArrayList<Item>();
		ResultSet rsItems;
		try {
			PreparedStatement preparestatement = this.getDBConnection()
					.prepareStatement(MySQLQuery.SQL_FETCH_ITEMS_LIST);
			preparestatement.setString(1, restaurantId);
			rsItems = preparestatement.executeQuery();
			while (rsItems.next()) {
				// ITEM_ID,ITEM_NAME,ITEM_DESCRIPTION,ITEM_PRICE,ITEM_RATING,RESTAURANT_ID
				Item item = new Item();
				item.setItemId(rsItems.getString(1));
				item.setItemName(rsItems.getString(2));
				item.setItemDesc(rsItems.getString(3));
				item.setItemPrice(rsItems.getDouble(4));
				item.setItemOverallRating(rsItems.getDouble(5));
				item.setRestaurantId(rsItems.getString(6));
				itemList.add(item);
			}
		} catch (SQLException e) {
			log.error(e.getMessage());
		}
		return itemList;

	}

}
