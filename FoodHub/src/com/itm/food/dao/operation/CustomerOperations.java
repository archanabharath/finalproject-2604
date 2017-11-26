package com.itm.food.dao.operation;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.itm.food.dao.Address;
import com.itm.food.dao.Basket;
import com.itm.food.dao.Customer;
import com.itm.food.dao.CustomerOrder;
import com.itm.food.dao.Item;
import com.itm.food.dao.Order;
import com.itm.food.dao.OrderItem;
import com.itm.food.dao.OrderStatus;
import com.itm.food.dao.Restaurant;
import com.itm.food.model.AddressDB;
import com.itm.food.model.CustomerDB;
import com.itm.food.model.ItemsDB;
import com.itm.food.model.OrderDB;
import com.itm.food.model.OrderItemDB;
import com.itm.food.model.RestaurantDB;
import com.itm.food.util.UniqueKeyGen;

public class CustomerOperations implements IUserOperations, ICustomerPreferences {

	private static final Logger log = Logger.getLogger(CustomerOperations.class);
	String tempcustid = null;

	CustomerDB customerDB = new CustomerDB();
	AddressDB addressDB = new AddressDB();
	RestaurantDB restaurantDB = new RestaurantDB();
	ItemsDB itemsDB = new ItemsDB();
	OrderDB orderDB = new OrderDB();
	OrderItemDB orderItemDB = new OrderItemDB();

	@Override
	public String addUserDetails(Customer newcustomer) throws Exception {
		String custId = "";
		try {
			log.debug("addUserDetails");
			custId = customerDB.add(newcustomer);
		} catch (Exception e) {
			log.error("ERROR: " + e.getMessage());
			throw e;
		}
		return custId;
	}

	public String addAddress(Address newaddress) throws Exception {
		String addressId = "";
		try {
			log.debug("addAddress");
			addressId = new AddressDB().add(newaddress);
		} catch (Exception e) {
			log.error("ERROR: " + e.getMessage());
			throw e;
		}
		return addressId;
	}

	@Override
	public void updateUserDetails(Customer updateCustomer) throws Exception {
		customerDB.update(updateCustomer);
	}

	@Override
	public void updateOrder(Basket basket) throws Exception {
		try {

			// Transaction Begins - Set auto commit to false. which allow us to
			// rollback in case of failures
			orderDB.setAutoCommit(false);

			Order order = new Order();
			order.setOrderId(UniqueKeyGen.generateUUID());
			order.setCustomerId(basket.getCustomer());
			order.setCardId(basket.getPayment());
			order.setAddressId(basket.getAddress());
			order.setTotalPayment(basket.getOrderTotal());
			order.setOrderStatus(OrderStatus.IN_PROGRESS.getId());
			order.setDeliveryMode(basket.getDeliveryMode());

			String orderid = orderDB.add(order);

			for (OrderItem items : basket.getOrderItems()) {
				items.setOrderId(orderid);
				orderItemDB.add(items);
			}

			// Commit the transaction
			orderDB.commit();

			// -- Transaction end

		} catch (Exception e) {
			orderDB.rollback();
			log.error(e.getMessage());
			throw e;
			// nothing saved.
		} finally {
			try {
				orderDB.setAutoCommit(true);
			} catch (ClassNotFoundException | SQLException e) {
				log.error(e.getMessage());
			}
		}

	}

	@Override
	public void displayPromotions(Date startDate, Date endDate) {

	}

	@Override
	public void displayUserProfile(int customerId) {

	}

	/**
	 * retrieve the current orders that are in progress and yet to be completed
	 */
	@Override
	public List<Order> displayOrderDetails(Order getOrders) {
		return null;

	}

	/**
	 * retrieve the list of orders along with customer, address and payment details placed by a customer in the past
	 * 
	 * @throws SQLException
	 */
	@Override
	public List<CustomerOrder> displayOrderHistoryOfCustomer(String customerId) throws SQLException {
		return orderDB.getListOfOrdersPlacedByCustomer(customerId);

	}

	/**
	 * retrieve the item and restaurant details of all orders placed by the customer
	 * 
	 * @param customerId
	 * @return
	 */
	public List<OrderItem> displayItemsRestaurantsOfAnOrder(String customerId) {
		return orderDB.getOrderItemRestaurantDetails(customerId);

	}

	@Override
	public void displayReviewComments(int orderId) {

	}

	@Override
	public void displayPreferences(int customerId) {

	}

	@Override
	public void displayRestaurants(int restaurantId) {

	}

	@Override
	public void displayMenu(int menuId) {

	}

	@Override
	public void Logout() {

	}

	@Override
	public void addPreferences() {

	}

	@Override
	public void updatePreferences(int prefId) {

	}

	@Override
	public void deletePreferences(int prefId) {

	}

	void updateAddress(int addrId) {
	}

	void deleteAddress(int addrId) {
	}

	/*
	 * Fetch the list of restaurants within the zip code that is searched for
	 */
	public ArrayList<Restaurant> searchRestaurants(List<Integer> zipcodes) throws Exception {
		return restaurantDB.searchByZip(zipcodes);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.itm.food.dao.operation.IUserOperations#validateCustomer(java.lang.
	 * String, java.lang.String) Authenticate the user information while logging
	 * in
	 */
	public String validateCustomer(String username, String password) throws SQLException {
		tempcustid = customerDB.customerLoginCheck(username, password);
		log.debug("CO-custid:" + tempcustid);
		return tempcustid;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.itm.food.dao.operation.IUserOperations#getCustomer(java.lang.String)
	 * Fetch the customer_id for the current session
	 */
	public Customer getCustomer(String customerId) throws Exception {
		return customerDB.find(customerId);
	}

	/**
	 * Fetch the list of addresses of the customer
	 * 
	 * @param getAddress
	 * @return
	 * @throws Exception
	 */
	public List<Address> getCustomerAddress(String customerId) throws Exception {
		return addressDB.getAddresses(customerId);

	}

	/**
	 * Validate if username already exists in Customer table while the user
	 * tries to register
	 * 
	 * @param username
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public boolean isUserNamePresent(String username) throws ClassNotFoundException, SQLException {
		return customerDB.validateUsername(username);
	}

	/**
	 * Fetch the customer details from customer table using the customer id This
	 * is used to display the customer's information on profile page
	 * 
	 * @param transferCustId
	 * @return
	 * @throws Exception
	 */
	public Customer getCustomerProfile(String transferCustId) throws Exception {
		return customerDB.pullCustomerDetails(transferCustId);
	}

	/**
	 * 
	 * @param restaurantId
	 * @return
	 * @throws Exception
	 */
	public List<Item> getItemsByRestaurant(String restaurantId) throws Exception {
		return itemsDB.getItemsByRestaurantId(restaurantId);
	}

	/**
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Item getItem(String id) throws Exception {
		return itemsDB.find(id);
	}

	public Restaurant getRestaurant(String id) throws Exception {
		return restaurantDB.find(id);
	}
}
