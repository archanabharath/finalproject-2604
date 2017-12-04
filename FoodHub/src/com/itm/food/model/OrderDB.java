package com.itm.food.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.itm.food.dao.AbstractDomain;
import com.itm.food.dao.Address;
import com.itm.food.dao.CustomerOrder;
import com.itm.food.dao.Order;
import com.itm.food.dao.OrderItem;
import com.itm.food.dao.Payment;
import com.itm.food.model.db.MySQLQuery;
import com.mysql.jdbc.Statement;

public class OrderDB extends AbstractDB implements IDBAccess {

	private static final Logger log = Logger.getLogger(OrderDB.class);

	@Override
	public <T extends AbstractDomain> int add(T object) throws Exception {
		Order order = (Order) object;
		log.debug("Adding Order");
		try {
			PreparedStatement preparedStatement = this.getDBConnection().prepareStatement(MySQLQuery.SQL_ORDER_INSERT,
					Statement.RETURN_GENERATED_KEYS);
			// preparedStatement.setString(1, order.getOrderId());
			preparedStatement.setInt(1, order.getCustomerId());
			preparedStatement.setInt(2, order.getCardId());
			preparedStatement.setInt(3, order.getAddressId());
			preparedStatement.setDouble(4, order.getTotalPayment());
			preparedStatement.setInt(5, order.getOrderStatus());
			preparedStatement.setInt(6, order.getDeliveryMode());

			preparedStatement.executeUpdate();
			ResultSet rsReturnOrderKey = preparedStatement.getGeneratedKeys();
			while (rsReturnOrderKey.next()) {
				order.setOrderId(rsReturnOrderKey.getInt(1));
			}
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
	public <T extends AbstractDomain> T find(int id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends AbstractDomain> void delete(T object) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(int id) throws Exception {
		// TODO Auto-generated method stub

	}

	public List<Order> getOrderHistoryOfCustomer(Order getOrders) throws SQLException {
		List<Order> ordersList = new ArrayList<Order>();
		Order order = new Order();
		try {// ORDER_ID,ADDRESS_ID,TOTAL_PAYMENT,ORDER_TIME,DRIVER_ID,DELIVERY_MODE
			PreparedStatement preparestatement = this.getDBConnection()
					.prepareStatement(MySQLQuery.SQL_RETRIEVE_COMPLETED_ORDERS_FOR_CUSTOMER);
			preparestatement.setInt(1, getOrders.getCustomerId());
			ResultSet rsorders;
			rsorders = preparestatement.executeQuery();
			while (rsorders.next()) {
				order.setOrderId(rsorders.getInt(1));
				order.setAddressId(rsorders.getInt(2));
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
	public List<CustomerOrder> getListOfOrdersPlacedByCustomer(int customerId) throws SQLException {
		List<CustomerOrder> customerOrderList = new ArrayList<CustomerOrder>();

		try {
			// P.CARD_ID,P.CARD_NO,A.ADDRESS_ID,A.CUSTOMER_ID,A.FIRST_NAME,A.LAST_NAME,A.ADDRESS1,
			// A.ADDRESS2,A.CITY,A.ZIPCODE,A.PHONE,O.ORDER_ID,O.TOTAL_PAYMENT,O.ORDER_STATUS,O.ORDER_TIME,O.DELIVERY_MODE
			// O.ORDER_FULFILMENT_TIME
			PreparedStatement preparestatement = this.getDBConnection()
					.prepareStatement(MySQLQuery.SQL_ORDER_CUSTOMER_DETAILS_FETCH_QUERY);
			preparestatement.setInt(1, customerId);
			ResultSet rsCustomerOrder;
			rsCustomerOrder = preparestatement.executeQuery();
			while (rsCustomerOrder.next()) {
				CustomerOrder orderCustomerInfo = new CustomerOrder();
				Address addressdata = new Address();
				Order orderdata = new Order();
				Payment paymentdata = new Payment();

				paymentdata.setCardid(rsCustomerOrder.getInt(1));
				paymentdata.setCardNo(rsCustomerOrder.getLong(2));
				paymentdata.setNameOnCard(rsCustomerOrder.getString(3));
				paymentdata.setCardType(rsCustomerOrder.getString(4));

				addressdata.setAddrId(rsCustomerOrder.getInt(5));
				addressdata.setCustId(rsCustomerOrder.getInt(6));
				addressdata.setFname(rsCustomerOrder.getString(7));
				addressdata.setLname(rsCustomerOrder.getString(8));
				addressdata.setAddr1(rsCustomerOrder.getString(9));
				addressdata.setAddr2(rsCustomerOrder.getString(10));
				addressdata.setCity(rsCustomerOrder.getString(11));
				addressdata.setPincode(rsCustomerOrder.getInt(12));
				addressdata.setAddrphoneNo(rsCustomerOrder.getString(13));

				orderdata.setOrderId(rsCustomerOrder.getInt(14));
				orderdata.setTotalPayment(rsCustomerOrder.getDouble(15));
				orderdata.setOrderStatus(rsCustomerOrder.getInt(16));
				orderdata.setOrderTime(rsCustomerOrder.getTimestamp(17).toString());
				orderdata.setDeliveryMode(rsCustomerOrder.getInt(18));
				orderdata.setOrderFulfilment(null != rsCustomerOrder.getTimestamp(19)
						? rsCustomerOrder.getTimestamp(19).toString() : StringUtils.EMPTY);

				orderCustomerInfo.setPaymentData(paymentdata);
				orderCustomerInfo.setAddressData(addressdata);
				orderCustomerInfo.setOrderData(orderdata);

				customerOrderList.add(orderCustomerInfo);

			}

		} catch (ClassNotFoundException e) {
			log.error(e.getMessage());
		} catch (SQLException e) {
			log.error(e.getMessage());

		}
		return customerOrderList;

	}

	/**
	 * Fetch the item and restaurant details of all orders placed by a customer
	 * 
	 * @param CustomerId
	 * @return
	 */
	public List<OrderItem> getOrderItemRestaurantDetails(int i) {
		List<OrderItem> orderItemsList = new ArrayList<OrderItem>();
		try {// R.RESTAURANT_ID,R.RESTAURANT_NAME,
				// I.ITEM_ID,I.ITEM_NAME,I.ITEM_PRICE,O.ORDER_ID,OI.COUPON_ID,OI.ITEM_QUANTITY,C.COUPON_NAME
			PreparedStatement preparestatement = this.getDBConnection()
					.prepareStatement(MySQLQuery.SQL_ORDER_ITEM_RESTAURANT_DETAILS_FETCH_QUERY);
			preparestatement.setInt(1, i);
			ResultSet rsOrderItems;
			rsOrderItems = preparestatement.executeQuery();
			while (rsOrderItems.next()) {
				OrderItem orderitem = new OrderItem();
				orderitem.setRestaurantId(rsOrderItems.getInt(1));
				orderitem.setRestaurantName(rsOrderItems.getString(2));
				orderitem.setItemId(rsOrderItems.getInt(3));
				orderitem.setItemName(rsOrderItems.getString(4));
				orderitem.setItemPrice(rsOrderItems.getDouble(5));
				orderitem.setOrderId(rsOrderItems.getInt(6));
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

	public int getOrderCount() throws Exception {
		ResultSet rs = null;
		int count = 0;
		try {
			PreparedStatement statement = this.getDBConnection().prepareStatement(MySQLQuery.SQL_ADMIN_ORDER_COUNT);
			rs = statement.executeQuery();
			log.debug(" count retrieved from customer");
			if (rs.next()) {
				count = rs.getInt(1);
			}
			statement.close();
		} catch (SQLException s) {
			log.error(s.getMessage(), s);
			throw s;
		} finally {
			rs.close();
		}
		return count;

	}

	public int getFullFilledOrderCount() throws Exception {
		ResultSet rs = null;
		int count = 0;
		try {
			PreparedStatement statement = this.getDBConnection()
					.prepareStatement(MySQLQuery.SQL_ADMIN_ORDER_FULLFILMENT_COUNT);
			rs = statement.executeQuery();
			log.debug(" count retrieved from customer");
			if (rs.next())
				count = rs.getInt(1);
		} catch (SQLException s) {
			log.error(s.getMessage());
			throw s;
		} finally {
			rs.close();
		}
		return count;
	}

	public int getTodayOrderCount() throws Exception {
		ResultSet rs = null;
		int count = 0;
		try {
			PreparedStatement statement = this.getDBConnection()
					.prepareStatement(MySQLQuery.SQL_ADMIN_ORDER_TODAY_COUNT);
			rs = statement.executeQuery();
			log.debug(" count retrieved from customer");
			if (rs.next())
				count = rs.getInt(1);
		} catch (SQLException s) {
			log.error(s.getMessage(), s);
			throw s;
		} finally {
			rs.close();
		}
		return count;
	}

	public ResultSet validateOrder(int orderID) throws Exception {
		ResultSet rs = null;
		try {
			PreparedStatement statement = this.getDBConnection().prepareStatement(MySQLQuery.SQL_ADMIN_ORDER_EXIST);
			statement.setInt(1, orderID);
			rs = statement.executeQuery();
			log.debug(orderID + " restaurant exists");
		} catch (SQLException s) {
			log.error(s.getMessage(), s);
			throw s;
		}
		return rs;
	}

	public ResultSet getOrderDetails(int orderID) throws Exception {
		ResultSet rs = null;
		try {
			PreparedStatement statement = this.getDBConnection().prepareStatement(MySQLQuery.SQL_ADMIN_ORDER_DETAILS);
			statement.setInt(1, orderID);
			rs = statement.executeQuery();
			log.debug("Data selected from table for order " + orderID);
		} catch (SQLException s) {
			log.error(s.getMessage(), s);
			throw s;
		}
		return rs;
	}

	public ResultSet getOrderList() throws Exception {
		ResultSet rs = null;
		try {
			// order by order_time desc
			PreparedStatement statement = this.getDBConnection().prepareStatement(MySQLQuery.SQL_ADMIN_ORDER_LIST);
			rs = statement.executeQuery();
			log.debug("Data selected from table order");
		} catch (SQLException s) {
			log.error(s.getMessage(), s);
			throw s;
		}
		return rs;

	}

	public ResultSet getOrderDetailsofCustomer(int custID) throws Exception {
		ResultSet rs = null;
		try {
			PreparedStatement statement = this.getDBConnection()
					.prepareStatement(MySQLQuery.SQL_ADMIN_ORDER_DETAIL_CUSTOMER);
			statement.setInt(1, custID);
			rs = statement.executeQuery();
			log.debug("Data selected from table for order " + custID);
		} catch (SQLException s) {
			log.error(s.getMessage(), s);
			throw s;
		}
		return rs;
	}

}
