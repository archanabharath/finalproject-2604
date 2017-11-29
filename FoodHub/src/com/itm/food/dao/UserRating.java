package com.itm.food.dao;

public abstract class UserRating extends AbstractDomain {

	private int ratingId;
	private int customerId;
	private double rating;

	/**
	 * @return the ratingId
	 */
	public int getRatingId() {
		return ratingId;
	}

	/**
	 * @param ratingId
	 *            the ratingId to set
	 */
	public void setRatingId(int ratingId) {
		this.ratingId = ratingId;
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
	 * @return the rating
	 */
	public double getRating() {
		return rating;
	}

	/**
	 * @param rating
	 *            the rating to set
	 */
	public void setRating(double rating) {
		this.rating = rating;
	}

}
