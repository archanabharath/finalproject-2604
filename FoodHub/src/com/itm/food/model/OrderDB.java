package com.itm.food.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;

import com.itm.food.dao.AbstractDomain;
import com.itm.food.dao.CustomerOrder;
import com.itm.food.dao.Order;
import com.itm.food.dao.OrderItem;
import com.itm.food.model.db.MySQLQuery;

public class OrderDB extends AbstractDB implements IDBAccess {

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

	public List<Order> getOrderHistoryOfCustomer(Order getOrders) throws SQLException {
		List<Order> ordersList = null;
		Order order = new Order();
		try {// ORDER_ID,ADDRESS_ID,TOTAL_PAYMENT,ORDER_TIME,DRIVER_ID,DELIVERY_MODE
			PreparedStatement preparestatement = this.getDBConnection()
					.prepareStatement(MySQLQuery.SQL_RETRIEVE_COMPLETED_ORDERS_FOR_CUSTOMER);
			preparestatement.setString(1, getOrders.getCustomerId());
			ResultSet rsorders;
			rsorders = preparestatement.executeQuery();
			while (rsorders.next()) {
				order.setOrderId(rsorders.getString(1));
				order.setAddressId(rsorders.getString(2));
				order.setTotalPayment(rsorders.getDouble(3));
				order.setOrderTime(rsorders.getTimestamp(4).toString());
				order.setDriverId(rsorders.getString(5));
				order.setDeliveryMode(rsorders.getInt(6));

				ordersList.add(order);
			}

		} catch (ClassNotFoundException e) {
			log.error(e.getMessage());
		} catch (SQLException e) {
			log.error(e.getMessage());

		} finally {
			this.closeConnection();
		}
		return ordersList;

	}

	/**
	 * Fetch the customer, address and payment details of all orders placed by a
	 * customer
	 * 
	 * @param customerId
	 * @return
	 * @throws SQLException
	 */
	public List<CustomerOrder> getListOfOrdersPlacedByCustomer(String customerId) throws SQLException {
		CustomerOrder orderCustomerInfo = new CustomerOrder();
		List<CustomerOrder> customerDetailsInOrderList = null;
		try {// P.CARD_ID,P.CARD_NO,A.ADDRESS_ID,A.CUSTOMER_ID,A.FIRST_NAME,A.LAST_NAME,A.ADDRESS1,
				// A.ADDRESS2,A.CITY,A.ZIPCODE,A.PHONE,O.ORDER_ID,O.TOTAL_PAYMENT,O.ORDER_STATUS,O.ORDER_TIME,O.DELIVERY_MODE
			PreparedStatement preparestatement = this.getDBConnection()
					.prepareStatement(MySQLQuery.SQL_ORDER_CUSTOMER_DETAILS_FETCH_QUERY);
			preparestatement.setString(1, customerId);
			ResultSet rsCustomerOrder;
			rsCustomerOrder = preparestatement.executeQuery();
			while (rsCustomerOrder.next()) {
				orderCustomerInfo.setCardId(rsCustomerOrder.getString(1));
				orderCustomerInfo.setCardNo(rsCustomerOrder.getInt(2));
				orderCustomerInfo.setAddressId(rsCustomerOrder.getString(3));
				orderCustomerInfo.setCustomerId(rsCustomerOrder.getString(4));
				orderCustomerInfo.setCustomerName(rsCustomerOrder.getString(5) + " " + rsCustomerOrder.getString(6));
				orderCustomerInfo.setAddress1(rsCustomerOrder.getString(7));
				orderCustomerInfo.setAddress2(rsCustomerOrder.getString(8));
				orderCustomerInfo.setCity(rsCustomerOrder.getString(9));
				orderCustomerInfo.setZipcode(rsCustomerOrder.getInt(10));
				orderCustomerInfo.setPhoneNo(rsCustomerOrder.getString(11));
				orderCustomerInfo.setOrderId(rsCustomerOrder.getString(12));
				orderCustomerInfo.setOrderTotal(rsCustomerOrder.getDouble(13));
				orderCustomerInfo.setOrderStatus(rsCustomerOrder.getInt(14));
				orderCustomerInfo.setOrderTime(rsCustomerOrder.getTimestamp(15).toString());
				orderCustomerInfo.setDeliveryMode(rsCustomerOrder.getInt(16));
				customerDetailsInOrderList.add(orderCustomerInfo);
			}

		} catch (ClassNotFoundException e) {
			log.error(e.getMessage());
		} catch (SQLException e) {
			log.error(e.getMessage());

		} finally {
			this.closeConnection();
		}
		return customerDetailsInOrderList;

	}

	/**
	 * Fetch the item and restaurant details of all orders placed by a customer
	 * 
	 * @param CustomerId
	 * @return
	 */
	public List<OrderItem> getOrderItemRestaurantDetails(String CustomerId) {
		OrderItem orderitem = new OrderItem();
		List<OrderItem> orderItemsList = null;
		try {// R.RESTAURANT_ID,R.RESTAURANT_NAME,
				// I.ITEM_ID,I.ITEM_NAME,I.ITEM_PRICE,O.ORDER_ID,OI.COUPON_ID,OI.ITEM_QUANTITY,C.COUPON_NAME
			PreparedStatement preparestatement = this.getDBConnection()
					.prepareStatement(MySQLQuery.SQL_ORDER_ITEM_RESTAURANT_DETAILS_FETCH_QUERY);
			preparestatement.setString(1, CustomerId);
			ResultSet rsOrderItems;
			rsOrderItems = preparestatement.executeQuery();
			while (rsOrderItems.next()) {
				orderitem.setRestaurantId(rsOrderItems.getString(1));
				orderitem.setRestaurantName(rsOrderItems.getString(2));
				orderitem.setItemId(rsOrderItems.getString(3));
				orderitem.setItemName(rsOrderItems.getString(4));
				orderitem.setItemPrice(rsOrderItems.getDouble(5));
				orderitem.setOrderId(rsOrderItems.getString(6));
				orderitem.setCouponId(rsOrderItems.getString(7));
				orderitem.setItemQuantity(rsOrderItems.getInt(8));
				orderitem.setCouponName(rsOrderItems.getString(9));

				orderItemsList.add(orderitem);
			}
		} catch (ClassNotFoundException e) {
			log.error(e.getMessage());
		} catch (SQLException e) {
			log.error(e.getMessage());
		}

		return orderItemsList;

	}
}
