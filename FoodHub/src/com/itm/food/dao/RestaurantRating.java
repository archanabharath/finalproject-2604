package com.itm.food.dao;

public class RestaurantRating extends UserRating {

	private int restaurantId;

	/**
	 * @return the restaurantId
	 */
	public int getRestaurantId() {
		return restaurantId;
	}

	/**
	 * @param restaurantId
	 *            the restaurantId to set
	 */
	public void setRestaurantId(int restaurantId) {
		this.restaurantId = restaurantId;
	}

}
