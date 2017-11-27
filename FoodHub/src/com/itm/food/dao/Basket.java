
package com.itm.food.dao;

import java.util.ArrayList;

import org.apache.commons.math3.util.Precision;

public class Basket {

	private double itemsTotal;
	private double deliveryCharge;
	private double couponsApplied;
	private double totalBeforeTax;
	private double taxApplied;
	private double orderTotal;
	private ArrayList<OrderItem> orderItems;
	private int deliveryMode;
	private String payment;
	private String address;
	private String customer;

	public static double TAX_AMOUNT = 7.0;

	/**
	 * @return the itemsTotal
	 */
	public double getItemsTotal() {
		return itemsTotal;
	}

	/**
	 * @param itemsTotal
	 *            the itemsTotal to set
	 */
	public void setItemsTotal(double itemsTotal) {
		this.itemsTotal = itemsTotal;
	}

	/**
	 * @return the deliveryCharge
	 */
	public double getDeliveryCharge() {
		return deliveryCharge;
	}

	/**
	 * @param deliveryCharge
	 *            the deliveryCharge to set
	 */
	public void setDeliveryCharge(double deliveryCharge) {
		this.deliveryCharge = deliveryCharge;
	}

	/**
	 * @return the couponsApplied
	 */
	public double getCouponsApplied() {
		return couponsApplied;
	}

	/**
	 * @param couponsApplied
	 *            the couponsApplied to set
	 */
	public void setCouponsApplied(double couponsApplied) {
		this.couponsApplied = couponsApplied;
	}

	/**
	 * @return the totalBeforeTax
	 */
	public double getTotalBeforeTax() {
		return totalBeforeTax;
	}

	/**
	 * @param totalBeforeTax
	 *            the totalBeforeTax to set
	 */
	public void setTotalBeforeTax(double totalBeforeTax) {
		this.totalBeforeTax = totalBeforeTax;
	}

	/**
	 * @return the taxApplied
	 */
	public double getTaxApplied() {
		return taxApplied;
	}

	/**
	 * @param taxApplied
	 *            the taxApplied to set
	 */
	public void setTaxApplied(double taxApplied) {
		this.taxApplied = taxApplied;
	}

	/**
	 * @return the orderTotal
	 */
	public double getOrderTotal() {
		return orderTotal;
	}

	/**
	 * @param orderTotal
	 *            the orderTotal to set
	 */
	public void setOrderTotal(double orderTotal) {
		this.orderTotal = orderTotal;
	}

	/**
	 * @return the orderItems
	 */
	public ArrayList<OrderItem> getOrderItems() {
		return orderItems;
	}

	/**
	 * @param orderItems
	 *            the orderItems to set
	 */
	public void setOrderItems(ArrayList<OrderItem> orderItems) {
		this.orderItems = orderItems;
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
	 * @return the payment
	 */
	public String getPayment() {
		return payment;
	}

	/**
	 * @param payment
	 *            the payment to set
	 */
	public void setPayment(String payment) {
		this.payment = payment;
	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address
	 *            the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return the customer
	 */
	public String getCustomer() {
		return customer;
	}

	/**
	 * @param customer
	 *            the customer to set
	 */
	public void setCustomer(String customer) {
		this.customer = customer;
	}

	public void calculateOrderSummary() {
		// Calculate item total first
		double tmpItemsTotal = 0.0;
		for (OrderItem orderItem : this.getOrderItems()) {
			tmpItemsTotal += Math.abs((orderItem.getItemPrice() * orderItem.getItemQuantity()));
		}
		this.setItemsTotal(tmpItemsTotal);

		// Calculate Delivery charge
		this.setDeliveryCharge(3.0);

		// Calculate Coupon applied
		this.setCouponsApplied(0.0);

		// Calculate Total before Tax
		this.setTotalBeforeTax(Precision
				.round(Math.abs((this.getItemsTotal() + this.getDeliveryCharge()) - this.getCouponsApplied()), 2));

		// Calculate tax
		this.setTaxApplied(Precision.round(Math.abs((this.getItemsTotal() * Basket.TAX_AMOUNT) / 100), 2));

		// Calculate total order amount
		this.setOrderTotal(Precision.round(Math.abs(this.getTotalBeforeTax() + this.getTaxApplied()), 2));
	}

}
