package com.itm.food.dao;

import java.util.List;

public class Order extends AbstractDomain {

	int orderId;
	int customerId;
	int cardId;
	int addressId;
	double totalPayment;
	String orderTime;
	int orderStatus;
	String driverId;
	int deliveryMode;
	String orderFulfilmentTime;
	int orderRating;
	String orderFeedback;
	String feedbackTime;
	List<OrderItem> orderedItems;

	/**
	 * @return the orderId
	 */
	public int getOrderId() {
		return orderId;
	}

	/**
	 * @param orderId
	 *            the orderId to set
	 */
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	/**
	 * @return the customerId
	 */
	public int getCustomerId() {
		return customerId;
	}

	/**
	 * @param customerId
	 *            the customerId to set
	 */
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	/**
	 * @return the cardId
	 */
	public int getCardId() {
		return cardId;
	}

	/**
	 * @param cardId
	 *            the cardId to set
	 */
	public void setCardId(int cardId) {
		this.cardId = cardId;
	}

	/**
	 * @return the addressId
	 */
	public int getAddressId() {
		return addressId;
	}

	/**
	 * @param addressId
	 *            the addressId to set
	 */
	public void setAddressId(int addressId) {
		this.addressId = addressId;
	}

	/**
	 * @return the totalPayment
	 */
	public double getTotalPayment() {
		return totalPayment;
	}

	/**
	 * @param totalPayment
	 *            the totalPayment to set
	 */
	public void setTotalPayment(double totalPayment) {
		this.totalPayment = totalPayment;
	}

	/**
	 * @return the orderTime
	 */
	public String getOrderTime() {
		return orderTime;
	}

	/**
	 * @param orderTime
	 *            the orderTime to set
	 */
	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}

	/**
	 * @return the orderStatus
	 */
	public int getOrderStatus() {
		return orderStatus;
	}

	/**
	 * @param orderStatus
	 *            the orderStatus to set
	 */
	public void setOrderStatus(int orderStatus) {
		this.orderStatus = orderStatus;
	}

	/**
	 * @return the driverId
	 */
	public String getDriverId() {
		return driverId;
	}

	/**
	 * @param driverId
	 *            the driverId to set
	 */
	public void setDriverId(String driverId) {
		this.driverId = driverId;
	}

	/**
	 * @return the deliveryMode
	 */
	public int getDeliveryMode() {
		return deliveryMode;
	}

	/**
	 * @param deliveryMode
	 *            the deliveryMode to set
	 */
	public void setDeliveryMode(int deliveryMode) {
		this.deliveryMode = deliveryMode;
	}

	/**
	 * @return the orderFulfilment
	 */
	public String getOrderFulfilment() {
		return orderFulfilmentTime;
	}

	/**
	 * @param orderFulfilment
	 *            the orderFulfilment to set
	 */
	public void setOrderFulfilment(String orderFulfilment) {
		this.orderFulfilmentTime = orderFulfilment;
	}

	/**
	 * @return the orderRating
	 */
	public int getOrderRating() {
		return orderRating;
	}

	/**
	 * @param orderRating
	 *            the orderRating to set
	 */
	public void setOrderRating(int orderRating) {
		this.orderRating = orderRating;
	}

	/**
	 * @return the orderFeedback
	 */
	public String getOrderFeedback() {
		return orderFeedback;
	}

	/**
	 * @param orderFeedback
	 *            the orderFeedback to set
	 */
	public void setOrderFeedback(String orderFeedback) {
		this.orderFeedback = orderFeedback;
	}

	/**
	 * @return the feedbackTime
	 */
	public String getFeedbackTime() {
		return feedbackTime;
	}

	/**
	 * @param feedbackTime
	 *            the feedbackTime to set
	 */
	public void setFeedbackTime(String feedbackTime) {
		this.feedbackTime = feedbackTime;
	}

	/**
	 * @return the orderedItems
	 */
	public List<OrderItem> getOrderedItems() {
		return orderedItems;
	}

	/**
	 * @param orderedItems
	 *            the orderedItems to set
	 */
	public void setOrderedItems(List<OrderItem> orderedItems) {
		this.orderedItems = orderedItems;
	}

}
