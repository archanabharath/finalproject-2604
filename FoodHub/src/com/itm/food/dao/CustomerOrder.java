package com.itm.food.dao;

import java.util.List;

public class CustomerOrder {

	Order orderData;
	Address addressData;
	Payment paymentData;
	List<OrderItem> orderItemRestaurantList;
	

	/**
	 * @return the orderData
	 */
	public Order getOrderData() {
		return orderData;
	}

	/**
	 * @param orderData
	 *            the orderData to set
	 */
	public void setOrderData(Order orderData) {
		this.orderData = orderData;
	}

	/**
	 * @return the addressData
	 */
	public Address getAddressData() {
		return addressData;
	}

	/**
	 * @param addressData
	 *            the addressData to set
	 */
	public void setAddressData(Address addressData) {
		this.addressData = addressData;
	}

	/**
	 * @return the paymentData
	 */
	public Payment getPaymentData() {
		return paymentData;
	}

	/**
	 * @param paymentData
	 *            the paymentData to set
	 */
	public void setPaymentData(Payment paymentData) {
		this.paymentData = paymentData;
	}

	/**
	 * @return the orderItemRestaurantList
	 */
	public List<OrderItem> getOrderItemRestaurantList() {
		return orderItemRestaurantList;
	}

	/**
	 * @param orderItemRestaurantList
	 *            the orderItemRestaurantList to set
	 */
	public void setOrderItemRestaurantList(List<OrderItem> orderItemRestaurantList) {
		this.orderItemRestaurantList = orderItemRestaurantList;
	}

}