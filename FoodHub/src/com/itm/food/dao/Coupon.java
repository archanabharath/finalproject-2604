package com.itm.food.dao;

public class Coupon extends AbstractDomain {
	String couponId;
	String couponCode;
	String couponDescription;
	int couponType;
	int couponValue;
	String couponValidity;

	/**
	 * @return the couponId
	 */
	public String getCouponId() {
		return couponId;
	}

	/**
	 * @param couponId
	 *            the couponId to set
	 */
	public void setCouponId(String couponId) {
		this.couponId = couponId;
	}

	/**
	 * @return the couponCode
	 */
	public String getCouponCode() {
		return couponCode;
	}

	/**
	 * @param couponCode
	 *            the couponCode to set
	 */
	public void setCouponCode(String couponCode) {
		this.couponCode = couponCode;
	}

	/**
	 * @return the couponDescription
	 */
	public String getCouponDescription() {
		return couponDescription;
	}

	/**
	 * @param couponDescription
	 *            the couponDescription to set
	 */
	public void setCouponDescription(String couponDescription) {
		this.couponDescription = couponDescription;
	}

	/**
	 * @return the couponType
	 */
	public int getCouponType() {
		return couponType;
	}

	/**
	 * @param i
	 *            the couponType to set
	 */
	public void setCouponType(int i) {
		this.couponType = i;
	}

	/**
	 * @return the couponValue
	 */
	public int getCouponValue() {
		return couponValue;
	}

	/**
	 * @param couponValue
	 *            the couponValue to set
	 */
	public void setCouponValue(int couponValue) {
		this.couponValue = couponValue;
	}

	/**
	 * @return the couponValidity
	 */
	public String getCouponValidity() {
		return couponValidity;
	}

	/**
	 * @param couponValidity
	 *            the couponValidity to set
	 */
	public void setCouponValidity(String couponValidity) {
		this.couponValidity = couponValidity;
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(this.getCouponCode());
		builder.append(" - ");
		if (this.getCouponType() == 1) {
			builder.append(this.getCouponValue() + "% OFF");
		} else {
			builder.append("$" + this.getCouponValue() + " OFF");
		}

		return builder.toString();
	}
}
