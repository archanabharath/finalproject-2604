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

	public static String SQL_GET_ALL_ADDRESSES_OF_CUSTOMER = "SELECT FIRST_NAME,LAST_NAME,ADDRESS1,ADDRESS2,CITY,ZIPCODE,PHONE,ADDRESS_ID,CUSTOMER_ID FROM ofod.ofod_caddress"
			+ " WHERE CUSTOMER_ID = ?";

	public static String SQL_GET_ALL_CARDS_OF_CUSTOMER = "SELECT NAME_ON_CARD,CARD_TYPE,EXPIRY,CARD_NO,CARD_ID,CUSTOMER_ID FROM ofod.ofod_payment "
			+ "WHERE CUSTOMER_ID = ? ORDER BY NAME_ON_CARD";

	public static String SQL_VALIDATE_USERNAME = "SELECT count(CUSTOMER_ID) FROM ofod.ofod_customer WHERE USERNAME = ?";

	public static String SQL_FETCH_CUSTOMER_PROFILE = "SELECT FIRST_NAME,LAST_NAME,EMAIL,USERNAME,PASSWORD,DOB FROM ofod.ofod_customer WHERE CUSTOMER_ID = ?";

	public static String SQL_FETCH_ITEMS_LIST = "SELECT ITEM_ID,ITEM_NAME,ITEM_DESCRIPTION,ITEM_PRICE,ITEM_RATING,RESTAURANT_ID FROM ofod.ofod_item WHERE RESTAURANT_ID = ?";
	
	public static String SQL_INSERT_INTO_ORDER = "INSERT INTO ofod.ofod_order (ORDER_ID,CUSTOMER_ID,CARD_ID,ADDRESS_ID,TOTAL_PAYMENT,ORDER_TIME,ORDER_STATUS,DRIVER_ID,"
			+ "DELIVERY_MODE,ORDER_FULFILMENT_TIME,ORDER_RATING,ORDER_FEEDBACK,FEEDBACK_TIME) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
	
	public static String SQL_INSERT_INTO_ORDER_TIME = "INSERT INTO ofod.ofod_order_item (ORDER_ID,RESTAURANT_ID,COUPON_ID,ITEM_ID,ITEM_QUANTITY) VALUES (?,?,?,?,?)";
	
	public static String SQL_RETRIEVE_ORDERS_FOR_CUSTOMER = "SELECT ORDER_ID,ADDRESS_ID,TOTAL_PAYMENT,ORDER_TIME,DRIVER_ID,DELIVERY_MODE FROM ofod.ofod_order WHERE CUSTOMER_ID = ? ORDER BY ORDER_TIME DESC";
	
	public static String SQL_RETRIEVE_COMPLETED_ORDERS_FOR_CUSTOMER = "SELECT ORDER_ID,ADDRESS_ID,TOTAL_PAYMENT,ORDER_TIME,DRIVER_ID,DELIVERY_MODE FROM ofod.ofod_order WHERE CUSTOMER_ID = ? AND ORDER_STATUS = 4";
	
	public static String SQL_ITEMS_SELECT = "SELECT ITEM_ID,ITEM_NAME,ITEM_DESCRIPTION,ITEM_PRICE,ITEM_RATING,RESTAURANT_ID FROM ofod.ofod_item WHERE ITEM_ID = ?";
	
	public static String SQL_RESTAURANT_SELECT = "SELECT RESTAURANT_ID,RESTAURANT_NAME,DESCRIPTION,ADDRESS1,ADDRESS2,CITY,ZIPCODE,RATING,"
			+ "PHONE,EMAIL,LATITUDE,LONGITUDE FROM ofod.ofod_restaurant WHERE RESTAURANT_ID = ?";
	
	public static String SQL_ORDER_CUSTOMER_DETAILS_FETCH_QUERY = "SELECT R.RESTAURANT_ID,R.RESTAURANT_NAME," + 
			"I.ITEM_ID,I.ITEM_NAME,I.ITEM_PRICE,O.ORDER_ID,OI.COUPON_ID,OI.ITEM_QUANTITY,C.COUPON_NAME " + 
			"FROM  " + 
			"ofod.ofod_order O INNER JOIN ofod.ofod_order_item OI ON O.ORDER_ID = OI.ORDER_ID" + 
			"INNER JOIN ofod.ofod_restaurant R ON OI.RESTAURANT_ID = R.RESTAURANT_ID" + 
			"INNER JOIN ofod.ofod_coupon C ON C.COUPON_ID = OI.COUPON_ID WHERE O.CUSTOMER_ID = ?";
	
	public static String SQL_ORDER_ITEM_RESTAURANT_DETAILS_FETCH_QUERY = "SELECT " + 
			"P.CARD_ID,P.CARD_NO," + 
			"A.ADDRESS_ID,A.CUSTOMER_ID,A.FIRST_NAME,A.LAST_NAME,A.ADDRESS1,A.ADDRESS2,A.CITY,A.ZIPCODE,A.PHONE," + 
			"O.ORDER_ID,O.TOTAL_PAYMENT,O.ORDER_STATUS,O.ORDER_TIME,O.DELIVERY_MODE " + 
			"FROM ofod.ofod_payment P INNER JOIN ofod.ofod_order O ON O.CARD_ID = P.CARD_ID" + 
			"INNER JOIN ofod.ofod_caddress A ON O.ADDRESS_ID = A.ADDRESS_ID WHERE O.CUSTOMER_ID = ?";
	
	
	public static String SQL_ORDER_INSERT = "INSERT INTO ofod.ofod_order (ORDER_ID, CUSTOMER_ID, CARD_ID, ADDRESS_ID, TOTAL_PAYMENT, ORDER_TIME, ORDER_STATUS, "
			+ "DELIVERY_MODE) VALUES(?, ?, ?, ?, ?, NOW(), ?, ?)";

	public static String SQL_ORDER__ITEM_INSERT = "INSERT INTO ofod.ofod_order_item (ORDER_ID, RESTAURANT_ID, COUPON_ID, ITEM_ID, ITEM_QUANTITY) "
			+ "VALUES(?, ?, ?, ?, ?)";

}
