package com.itm.food.dao;

import java.util.ArrayList;

public class Item {
	String itemId;
	String itemName;
	String itemDesc;
	double itemPrice;
	double itemOverallRating;
	ArrayList<UserReview> itemReview[];
	
	void addItems() {}
	void updateItem(int menuId,int itemId) {}
	void deleteItem(int itemId,int menuId) {}
}