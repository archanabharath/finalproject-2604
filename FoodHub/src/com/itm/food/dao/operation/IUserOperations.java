package com.itm.food.dao.operation;

import java.util.Date;
import java.util.List;

import com.itm.food.dao.Address;
import com.itm.food.dao.Basket;
import com.itm.food.dao.Customer;
import com.itm.food.dao.CustomerOrder;
import com.itm.food.dao.Order;

public interface IUserOperations {

	String validateCustomer(String username, String password) throws Exception;

	Customer getCustomer(String customerId) throws Exception;

	String addUserDetails(Customer newcustomer) throws Exception;

	String addAddress(Address newAddress) throws Exception;

	void updateOrder(Basket basket) throws Exception;

	void displayPromotions(Date startDate, Date endDate) throws Exception;

	void displayUserProfile(int customerId) throws Exception;

	List<Order> displayOrderDetails(Order getOrders) throws Exception;

	List<CustomerOrder> displayOrderHistoryOfCustomer(String CustomerId) throws Exception;

	void displayReviewComments(int orderId) throws Exception;

	void displayPreferences(int customerId) throws Exception;

	void displayRestaurants(int restaurantId) throws Exception;

	void displayMenu(int menuId) throws Exception;

	void Logout() throws Exception;

	void updateUserDetails(Customer updateCustomer) throws Exception;
}