package com.itm.food.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.itm.food.dao.AbstractDomain;
import com.itm.food.dao.Item;
import com.itm.food.dao.ItemRestaurant;
import com.itm.food.dao.Restaurant;
import com.itm.food.model.db.MySQLQuery;

public class ItemsDB extends AbstractDB implements IDBAccess {

	private static final Logger log = Logger.getLogger(ItemsDB.class);

	@Override
	public <T extends AbstractDomain> int add(T object) throws Exception {
		return 0;
		// TODO Auto-generated method stub
		//return null;
	}

	@Override
	public <T extends AbstractDomain> void update(T object) throws Exception {
		// TODO Auto-generated method stub

	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends AbstractDomain> T find(int id) throws Exception {
		log.debug("Getting Item for the ItemID: " + id);
		ResultSet rsItems;
		Item item = new Item();
		try {
			PreparedStatement preparestatement = this.getDBConnection().prepareStatement(MySQLQuery.SQL_ITEMS_SELECT);
			preparestatement.setInt(1, id);
			rsItems = preparestatement.executeQuery();
			while (rsItems.next()) {
				// ITEM_ID,ITEM_NAME,ITEM_DESCRIPTION,ITEM_PRICE,ITEM_RATING,RESTAURANT_ID
				item.setItemId(rsItems.getInt(1));
				item.setItemName(rsItems.getString(2));
				item.setItemDesc(rsItems.getString(3));
				item.setItemPrice(rsItems.getDouble(4));
				item.setItemOverallRating(rsItems.getDouble(5));
				item.setRestaurantId(rsItems.getInt(6));

			}
		} catch (SQLException e) {
			log.error(e.getMessage());
		}
		return (T) item;
	}

	@Override
	public <T extends AbstractDomain> void delete(T object) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(String id) throws Exception {
		// TODO Auto-generated method stub

	}

	public List<Item> getItemsByRestaurantId(int i) throws Exception {
		log.debug("Getting Items for the RestaurantId: " + i);
		List<Item> itemList = new ArrayList<Item>();
		ResultSet rsItems;
		try {
			PreparedStatement preparestatement = this.getDBConnection()
					.prepareStatement(MySQLQuery.SQL_FETCH_ITEMS_LIST);
			preparestatement.setInt(1, i);
			rsItems = preparestatement.executeQuery();
			while (rsItems.next()) {
				// ITEM_ID,ITEM_NAME,ITEM_DESCRIPTION,ITEM_PRICE,ITEM_RATING,RESTAURANT_ID
				Item item = new Item();
				item.setItemId(rsItems.getInt(1));
				item.setItemName(rsItems.getString(2));
				item.setItemDesc(rsItems.getString(3));
				item.setItemPrice(rsItems.getDouble(4));
				item.setItemOverallRating(rsItems.getDouble(5));
				item.setRestaurantId(rsItems.getInt(6));
				itemList.add(item);
			}
		} catch (SQLException e) {
			log.error(e.getMessage());
		}
		return itemList;
	}

	public ItemRestaurant getTop3HighRatedItems() {
		log.debug("Fetching the high rated top 3 items");

		// List<ItemRestaurant> itemRestaurantsList = new ArrayList<ItemRestaurant>();
		List<Item> itemsList = new ArrayList<Item>();
		List<Restaurant> restaurantsList = new ArrayList<Restaurant>();

		ItemRestaurant itemrestaurantobj = new ItemRestaurant();

		try {// I.ITEM_ID, I.ITEM_NAME, I.ITEM_RATING, R.RESTAURANT_ID, R.RESTAURANT_NAME,
				// R.RATING
			PreparedStatement preparestatement = this.getDBConnection()
					.prepareStatement(MySQLQuery.SQL_FETCH_TOP_3_ITEMS);
			ResultSet rsTopItems;
			rsTopItems = preparestatement.executeQuery();
			while (rsTopItems.next()) {
				Item topItems = new Item();

				topItems.setItemId(rsTopItems.getInt(1));
				topItems.setItemName(rsTopItems.getString(2));
				topItems.setItemOverallRating(rsTopItems.getInt(3));

				Restaurant topItemsRestaurants = new Restaurant();
				topItemsRestaurants.setRestaurantId(rsTopItems.getInt(4));
				topItemsRestaurants.setRestaurantName(rsTopItems.getString(5));
				topItems.setItemDesc(rsTopItems.getString(6));
				topItems.setItemPrice(rsTopItems.getDouble(7));
				itemsList.add(topItems);
				restaurantsList.add(topItemsRestaurants);

			}
			itemrestaurantobj.setTop3ItemsByRestaurants(restaurantsList);
			itemrestaurantobj.setTop3ItemsList(itemsList);

		} catch (ClassNotFoundException e) {
			log.error(e.getMessage());
		} catch (SQLException e) {
			log.error(e.getMessage());
		}

		return itemrestaurantobj;

	}

}
