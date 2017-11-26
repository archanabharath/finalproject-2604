package com.itm.food.model;

import java.sql.PreparedStatement;

import org.apache.log4j.Logger;

import com.itm.food.dao.AbstractDomain;
import com.itm.food.dao.OrderItem;
import com.itm.food.model.db.MySQLQuery;

public class OrderItemDB extends AbstractDB implements IDBAccess {

	private static final Logger log = Logger.getLogger(OrderDB.class);
	
	@Override
	public <T extends AbstractDomain> String add(T object) throws Exception {
		OrderItem orderItem = (OrderItem) object;
		log.debug("Adding OrderItem");
		try {
			PreparedStatement preparedStatement = this.getDBConnection()
					.prepareStatement(MySQLQuery.SQL_ORDER__ITEM_INSERT);
			preparedStatement.setString(1, orderItem.getOrderId());
			preparedStatement.setString(2, orderItem.getRestaurantId());
			preparedStatement.setString(3, orderItem.getCouponId());
			preparedStatement.setString(4, orderItem.getItemId());
			preparedStatement.setDouble(5, orderItem.getItemQuantity()); 
			
			preparedStatement.execute();
			preparedStatement.close();

		} catch (Exception ex) {
			log.error(ex.getMessage());
			throw ex;
		} 
		log.debug("Added Order - " + orderItem.getOrderId());	
		return orderItem.getOrderId();
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

}
