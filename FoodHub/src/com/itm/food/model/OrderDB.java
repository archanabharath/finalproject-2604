package com.itm.food.model;

import java.sql.PreparedStatement;

import org.apache.log4j.Logger;

import com.itm.food.dao.AbstractDomain;
import com.itm.food.dao.Order;
import com.itm.food.model.db.MySQLQuery;

public class OrderDB extends AbstractDB implements IDBAccess{
	
	private static final Logger log = Logger.getLogger(OrderDB.class);

	@Override
	public <T extends AbstractDomain> String add(T object) throws Exception {
		Order order = (Order) object;
		log.debug("Adding Order");
		try {
			PreparedStatement preparedStatement = this.getDBConnection()
					.prepareStatement(MySQLQuery.SQL_ORDER_INSERT);
			preparedStatement.setString(1, order.getOrderId());
			preparedStatement.setString(2, order.getCustomerId());
			preparedStatement.setString(3, order.getCardId());
			preparedStatement.setString(4, order.getAddressId());
			preparedStatement.setDouble(5, order.getTotalPayment()); 
			preparedStatement.setInt(6, order.getOrderStatus());
			preparedStatement.setInt(7, order.getDeliveryMode());

			preparedStatement.execute();
			preparedStatement.close();

		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			throw ex;
		} 
		log.debug("Added Order - " + order.getOrderId());
		return order.getOrderId();
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
