package com.itm.food.util;

public class ImageUtil {

	public static String getImagePath() {
		String imagePath = "";
		if (System.getProperty("os.name").toLowerCase().contains("win")) {
			imagePath = "file:\\C:\\FoodHub\\images\\";
		} else {
			imagePath = "file:/Users/Shared/FoodHub/images/";
		}
		return imagePath;
	}

}
