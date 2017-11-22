package com.itm.food.dao.operation;

public interface ICustomerPreferences {
	int prefId = 0000;
	int customerId = 00000;
	int restaurantId = 0000;
	int cuisineId = 00;

	void addPreferences(); // customer bookmarks, admin based on order history and max ratings by loc

	void updatePreferences(int prefId);

	void deletePreferences(int prefId);

}