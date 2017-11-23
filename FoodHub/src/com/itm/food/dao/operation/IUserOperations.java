package com.itm.food.dao.operation;

import java.text.ParseException;
import java.util.Date;

import com.itm.food.dao.Address;
import com.itm.food.dao.Customer;

public interface IUserOperations {

	String validateCustomer(String username, String password) throws Exception;

	Customer getCustomer(String customerId) throws Exception;

	String addUserDetails(Customer newcustomer) throws Exception;

	String addAddress(Address newAddress) throws Exception;

	void updateOrder(int orderId) throws Exception;

	void displayPromotions(Date startDate, Date endDate) throws Exception;

	void displayUserProfile(int customerId) throws Exception;

	void displayOrderDetails(int orderId) throws Exception;

	void displayOrderHistory(int customerId) throws Exception;

	void displayReviewComments(int orderId) throws Exception;

	void displayPreferences(int customerId) throws Exception;

	void displayRestaurants(int restaurantId) throws Exception;

	void displayMenu(int menuId) throws Exception;

	void Logout() throws Exception;

	void updateUserDetails(Customer updateCustomer) throws ParseException;
}