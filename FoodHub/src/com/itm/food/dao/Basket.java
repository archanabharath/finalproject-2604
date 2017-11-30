
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
	private int payment;
	private int address;
	private int customer;
	private double itemtotalminuscoupon;
	private Coupon couponobj;
	private ArrayList<Coupon> allCoupons;

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
	public int getPayment() {
		return payment;
	}

	/**
	 * @param payment
	 *            the payment to set
	 */
	public void setPayment(int payment) {
		this.payment = payment;
	}

	/**
	 * @return the address
	 */
	public int getAddress() {
		return address;
	}

	/**
	 * @param address
	 *            the address to set
	 */
	public void setAddress(int address) {
		this.address = address;
	}

	/**
	 * @return the customer
	 */
	public int getCustomer() {
		return customer;
	}

	/**
	 * @param customer
	 *            the customer to set
	 */
	public void setCustomer(int customer) {
		this.customer = customer;
	}

	/**
	 * @return the itemtotalminuscoupon
	 */
	public double getItemtotalminuscoupon() {
		return itemtotalminuscoupon;
	}

	/**
	 * @param itemtotalminuscoupon
	 *            the itemtotalminuscoupon to set
	 */
	public void setItemtotalminuscoupon(double itemtotalminuscoupon) {
		this.itemtotalminuscoupon = itemtotalminuscoupon;
	}

	/**
	 * @return the couponobj
	 */
	public Coupon getCouponobj() {
		return couponobj;
	}

	/**
	 * @param couponobj
	 *            the couponobj to set
	 */
	public void setCouponobj(Coupon couponobj) {
		this.couponobj = couponobj;
	}

	/**
	 * @return the allCoupons
	 */
	public ArrayList<Coupon> getAllCoupons() {
		return allCoupons;
	}

	/**
	 * @param allCoupons
	 *            the allCoupons to set
	 */
	public void setAllCoupons(ArrayList<Coupon> allCoupons) {
		this.allCoupons = allCoupons;
	}

	/**
	 * calculate the order total amounts deducting the coupon amount and adding
	 * delivery charges and tax amount
	 */
	public void calculateOrderSummary() {
		// Calculate item total first
		double tmpItemsTotal = 0.0;

		for (OrderItem orderItem : this.getOrderItems()) {
			tmpItemsTotal += Math.abs((orderItem.getItemPrice() * orderItem.getItemQuantity()));
		}
		this.setItemsTotal(tmpItemsTotal);

		// Calculate Delivery charge
		// this.setDeliveryCharge(3.0);

		// Calculate Coupon applied
		// this.setCouponsApplied(0.0);
		if (this.getCouponobj() != null) {
			if (this.getCouponobj().getCouponType() == 1) {
				System.out.println("coupon-type-1" + this.getCouponobj().getCouponValue());
				this.setCouponsApplied(this.itemsTotal * (this.getCouponobj().getCouponValue()) / 100.0);
				System.out.println("coupon-applied-pct:" + this.getCouponsApplied());
			} else if (this.getCouponobj().getCouponType() == 2) {
				System.out.println("coupon-type-2" + this.getCouponobj().getCouponValue());
				this.setCouponsApplied(this.getCouponobj().getCouponValue());
			}
		} else {
			this.setCouponsApplied(0.0);
		}

		if (this.getItemsTotal() <= this.getCouponsApplied()) {
			// this.setItemsTotal(0);
			this.setItemtotalminuscoupon(0);

		} else {
			this.setItemtotalminuscoupon(this.getItemsTotal() - this.getCouponsApplied());
		}
		// Calculate Total before Tax
		this.setTotalBeforeTax(
				Precision.round(Math.abs((this.getItemtotalminuscoupon() + this.getDeliveryCharge())), 2));

		// Calculate tax
		this.setTaxApplied(Precision.round(Math.abs((this.getItemtotalminuscoupon() * Basket.TAX_AMOUNT) / 100), 2));

		// Calculate total order amount
		this.setOrderTotal(Precision.round(Math.abs(this.getTotalBeforeTax() + this.getTaxApplied()), 2));

	}

}
