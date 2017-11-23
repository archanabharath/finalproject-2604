package com.itm.food.dao.operation;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.itm.food.dao.Address;
import com.itm.food.dao.Customer;
import com.itm.food.dao.Restaurant;
import com.itm.food.model.AddressDB;
import com.itm.food.model.CustomerDB;
import com.itm.food.model.RestaurantDB;

public class CustomerOperations implements IUserOperations, ICustomerPreferences {

	private static final Logger log = Logger.getLogger(CustomerOperations.class);
	String tempcustid = null;

	CustomerDB customerDB = new CustomerDB();
	AddressDB addressDB = new AddressDB();
	RestaurantDB restaurantDB = new RestaurantDB();

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
	public void updateUserDetails(Customer updateCustomer) throws ParseException {
		customerDB.update(updateCustomer);
	}

	@Override
	public void updateOrder(int orderId) {
		// TODO Auto-generated method stub

	}

	@Override
	public void displayPromotions(Date startDate, Date endDate) {

	}

	@Override
	public void displayUserProfile(int customerId) {

	}

	@Override
	public void displayOrderDetails(int orderId) {

	}

	@Override
	public void displayOrderHistory(int customerId) {

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
	public ArrayList<Restaurant> searchRestaurants(int zipcode) throws Exception {
		return restaurantDB.searchByZip(zipcode);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.itm.food.dao.operation.IUserOperations#validateCustomer(java.lang.String,
	 * java.lang.String) Authenticate the user information while logging in
	 */
	public String validateCustomer(String username, String password) throws SQLException {
		tempcustid = customerDB.customerLoginCheck(username, password);
		log.debug("CO-custid:" + tempcustid);
		return tempcustid;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.itm.food.dao.operation.IUserOperations#getCustomer(java.lang.String)
	 * Fetch the customer_id for the current session
	 */
	public Customer getCustomer(String customerId) throws Exception {
		return customerDB.find(customerId);
	}

	/*
	 * Fetch the list of addresses of the customer
	 */
	public List<Address> getCustomerAddress(Address getAddress) throws Exception {
		return addressDB.getAddresses(getAddress.getCustId());

	}

	/*
	 * Validate if username already exists in Customer table while the user tries to
	 * register
	 */
	public boolean isUserNamePresent(String username) throws ClassNotFoundException, SQLException {
		return customerDB.validateUsername(username);
	}

	/*
	 * Fetch the customer details from customer table using the customer id This is
	 * used to display the customer's information on profile page
	 */
	public Customer getCustomerProfile(String transferCustId) throws Exception {
		return customerDB.pullCustomerDetails(transferCustId);

	}
}
