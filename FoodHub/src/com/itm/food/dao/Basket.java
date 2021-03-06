package com.itm.food.dao;

import java.util.ArrayList;

public class Basket {

	private double itemsTotal;
	private double deliveryCharge;
	private double couponsApplied;
	private double totalBeforeTax;
	private double taxApplied;
	private double orderTotal;
	private ArrayList<OrderItem> orderItems;

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
		this.setTotalBeforeTax(Math.abs((this.getItemsTotal() + this.getDeliveryCharge()) - this.getCouponsApplied()));

		// Calculate tax
		this.setTaxApplied(Math.abs((this.getItemsTotal() * Basket.TAX_AMOUNT) / 100));

		// Calculate total order amount
		this.setOrderTotal(Math.abs(this.getTotalBeforeTax() + this.getTaxApplied()));
	}

}
