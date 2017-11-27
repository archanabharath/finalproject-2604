package com.itm.food.dao;

import java.util.List;

public class Order extends AbstractDomain {

	String orderId;
	String customerId;
	String cardId;
	String addressId;
	double totalPayment;
	String orderTime;
	int orderStatus;
	String driverId;
	int deliveryMode;
	String orderFulfilment;
	String orderRating;
	String orderFeedback;
	String feedbackTime;
	List<OrderItem> orderedItems;

	/**
	 * @return the orderId
	 */
	public String getOrderId() {
		return orderId;
	}

	/**
	 * @param orderId
	 *            the orderId to set
	 */
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	/**
	 * @return the customerId
	 */
	public String getCustomerId() {
		return customerId;
	}

	/**
	 * @param customerId
	 *            the customerId to set
	 */
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	/**
	 * @return the cardId
	 */
	public String getCardId() {
		return cardId;
	}

	/**
	 * @param cardId
	 *            the cardId to set
	 */
	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	/**
	 * @return the addressId
	 */
	public String getAddressId() {
		return addressId;
	}

	/**
	 * @param addressId
	 *            the addressId to set
	 */
	public void setAddressId(String addressId) {
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
		return orderFulfilment;
	}

	/**
	 * @param orderFulfilment
	 *            the orderFulfilment to set
	 */
	public void setOrderFulfilment(String orderFulfilment) {
		this.orderFulfilment = orderFulfilment;
	}

	/**
	 * @return the orderRating
	 */
	public String getOrderRating() {
		return orderRating;
	}

	/**
	 * @param orderRating
	 *            the orderRating to set
	 */
	public void setOrderRating(String orderRating) {
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
