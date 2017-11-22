package com.itm.food.dao.operation;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.itm.food.dao.Address;
import com.itm.food.dao.Customer;
import com.itm.food.model.AddressDB;
import com.itm.food.model.CustomerDB;
import com.itm.food.model.RestaurantDB;

public class CustomerOperations implements IUserOperations, ICustomerPreferences {

	private static final Logger log = Logger.getLogger(CustomerOperations.class);
	String tempcustid = null;

	CustomerDB customerDB = new CustomerDB();
	AddressDB addressDB = new AddressDB();

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
	public void updateUserDetails(int userId) {
		// TODO Auto-generated method stub

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

	public ArrayList<String> searchRestaurants(int zipcode) throws Exception {
		RestaurantDB callrestaurant = new RestaurantDB();
		return callrestaurant.searchByZip(zipcode);

	}

	public String validateCustomer(String username, String password) throws SQLException {
		tempcustid = customerDB.customerLoginCheck(username, password);
		log.debug("CO-custid:" + tempcustid);
		return tempcustid;
	}

	public Customer getCustomer(String customerId) throws Exception {
		return customerDB.find(customerId);
	}

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
}
