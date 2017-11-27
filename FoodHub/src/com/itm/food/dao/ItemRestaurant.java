package com.itm.food.dao;

import java.util.List;

public class ItemRestaurant {
	List<Item> top3ItemsList;
	List<Restaurant> top3ItemsByRestaurants;
	/**
	 * @return the top3ItemsList
	 */
	public List<Item> getTop3ItemsList() {
		return top3ItemsList;
	}
	/**
	 * @param top3ItemsList the top3ItemsList to set
	 */
	public void setTop3ItemsList(List<Item> top3ItemsList) {
		this.top3ItemsList = top3ItemsList;
	}
	/**
	 * @return the top3ItemsByRestaurants
	 */
	public List<Restaurant> getTop3ItemsByRestaurants() {
		return top3ItemsByRestaurants;
	}
	/**
	 * @param top3ItemsByRestaurants the top3ItemsByRestaurants to set
	 */
	public void setTop3ItemsByRestaurants(List<Restaurant> top3ItemsByRestaurants) {
		this.top3ItemsByRestaurants = top3ItemsByRestaurants;
	}

}
