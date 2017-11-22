package com.itm.food.util;

import java.util.UUID;

public class UniqueKeyGen {
	
	public static String generateUUID() {
		UUID uuid = UUID.randomUUID();
		return uuid.toString();
		
	}

}
