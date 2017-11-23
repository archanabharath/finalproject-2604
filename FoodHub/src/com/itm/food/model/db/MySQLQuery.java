package com.itm.food.model.db;

public class MySQLQuery {

	public static String SQL_CUSTOMER_INSERT = "INSERT INTO ofod.ofod_customer(CUSTOMER_ID, FIRST_NAME, LAST_NAME, DOB, PHONE, "
			+ "EMAIL, USERNAME, PASSWORD) VALUES (?,?,?,?,?,?,?,?)";

	public static String SQL_CUSTOMER_SELECT = "SELECT * FROM ofod.ofod_customer WHERE CUSTOMER_ID=?";

	public static String SQL_CUSTOMER_ADDRESS_INSERT = "INSERT INTO ofod.ofod_caddress(ADDRESS_ID,CUSTOMER_ID,FIRST_NAME,LAST_NAME,ADDRESS1,"
			+ "ADDRESS2,CITY,ZIPCODE,PHONE) VALUES(?,?,?,?,?,?,?,?,?)";

	public static String SQL_GET_RESTAURANTS_BY_ZIP = "SELECT RESTAURANT_ID,RESTAURANT_NAME,DESCRIPTION,ADDRESS1,ADDRESS2,CITY,ZIPCODE,RATING,"
			+ "PHONE,EMAIL,LATITUDE,LONGITUDE FROM ofod.ofod_restaurant WHERE ZIPCODE IN (?) ORDER BY RATING DESC";

	public static String SQL_VERIFY_CUSTOMER_LOGIN = "SELECT CUSTOMER_ID FROM ofod.ofod_customer WHERE USERNAME = ? AND PASSWORD = ?";

	public static String SQL_PAYMENT_INSERT = "INSERT INTO ofod.ofod_payment(CUSTOMER_ID,CARD_ID,CARD_NO,EXPIRY,CARD_TYPE,NAME_ON_CARD)"
			+ " VALUES (?,?,?,?,?,?)";

	public static String SQL_GET_ALL_ADDRESSES_OF_CUSTOMER = "SELECT FIRST_NAME,LAST_NAME,ADDRESS1,ADDRESS2,CITY,ZIPCODE,PHONE FROM ofod.ofod_caddress"
			+ " WHERE CUSTOMER_ID = ?";

	public static String SQL_GET_ALL_CARDS_OF_CUSTOMER = "SELECT NAME_ON_CARD,CARD_TYPE,EXPIRY,CARD_NO FROM ofod.ofod_payment "
			+ "WHERE CUSTOMER_ID = ?";

	public static String SQL_VALIDATE_USERNAME = "SELECT count(CUSTOMER_ID) FROM ofod.ofod_customer WHERE USERNAME = ?";

}
