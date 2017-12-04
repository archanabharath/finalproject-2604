package com.itm.food.model.db;

public class MySQLQuery {

	/***********************************************************************************************************************************
	 * Customer SQL queries /
	 **************************************************************************************************************************************/
	public static String SQL_CUSTOMER_INSERT = "INSERT INTO ofod_customer(FIRST_NAME, LAST_NAME, DOB, PHONE, "
			+ "EMAIL, USERNAME, PASSWORD) VALUES (?,?,?,?,?,?,?)";

	public static String SQL_CUSTOMER_SELECT = "SELECT * FROM ofod_customer WHERE CUSTOMER_ID=?";

	public static String SQL_CUSTOMER_ADDRESS_INSERT = "INSERT INTO ofod_caddress(CUSTOMER_ID,FIRST_NAME,LAST_NAME,ADDRESS1,"
			+ "ADDRESS2,CITY,ZIPCODE,PHONE) VALUES(?,?,?,?,?,?,?,?)";

	public static String SQL_GET_RESTAURANTS_BY_ZIP = "SELECT RESTAURANT_ID,RESTAURANT_NAME,DESCRIPTION,ADDRESS1,ADDRESS2,CITY,ZIPCODE,RATING,"
			+ "PHONE,EMAIL,LATITUDE,LONGITUDE FROM ofod_restaurant WHERE ZIPCODE IN (?) ORDER BY RATING DESC";

	public static String SQL_VERIFY_CUSTOMER_LOGIN = "SELECT CUSTOMER_ID FROM ofod_customer WHERE USERNAME = ? AND PASSWORD = ?";

	public static String SQL_PAYMENT_INSERT = "INSERT INTO ofod_payment(CUSTOMER_ID,CARD_NO,EXPIRY,CARD_TYPE,NAME_ON_CARD)"
			+ " VALUES (?,?,?,?,?)";

	public static String SQL_GET_ALL_ADDRESSES_OF_CUSTOMER = "SELECT FIRST_NAME,LAST_NAME,ADDRESS1,ADDRESS2,CITY,ZIPCODE,PHONE,ADDRESS_ID,CUSTOMER_ID FROM ofod_caddress"
			+ " WHERE CUSTOMER_ID = ?";

	public static String SQL_GET_ALL_CARDS_OF_CUSTOMER = "SELECT NAME_ON_CARD,CARD_TYPE,EXPIRY,CARD_NO,CARD_ID,CUSTOMER_ID FROM ofod_payment "
			+ "WHERE CUSTOMER_ID = ? ORDER BY NAME_ON_CARD";

	public static String SQL_VALIDATE_USERNAME = "SELECT count(CUSTOMER_ID) FROM ofod_customer WHERE USERNAME = ?";

	public static String SQL_FETCH_CUSTOMER_PROFILE = "SELECT FIRST_NAME,LAST_NAME,EMAIL,USERNAME,PASSWORD,DOB FROM ofod_customer WHERE CUSTOMER_ID = ?";

	public static String SQL_FETCH_ITEMS_LIST = "SELECT I.ITEM_ID,ITEM_NAME,ITEM_DESCRIPTION,ITEM_PRICE,AVG(UR.RATING) AS RATING,RESTAURANT_ID "
			+ "FROM ofod_item I " + "LEFT JOIN ofod_item_rating IR ON IR.ITEM_ID = I.ITEM_ID "
			+ "LEFT JOIN ofod_user_rating UR ON UR.RATING_ID = IR.RATING_ID " + "WHERE RESTAURANT_ID = ? "
			+ "GROUP BY I.ITEM_ID " + "ORDER BY RATING DESC";

	public static String SQL_INSERT_INTO_ORDER = "INSERT INTO ofod_order (CUSTOMER_ID,CARD_ID,ADDRESS_ID,TOTAL_PAYMENT,ORDER_TIME,ORDER_STATUS,DRIVER_ID,"
			+ "DELIVERY_MODE,ORDER_FULFILMENT_TIME,ORDER_RATING,ORDER_FEEDBACK,FEEDBACK_TIME) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";

	public static String SQL_INSERT_INTO_ORDER_TIME = "INSERT INTO ofod_order_item (ORDER_ID,RESTAURANT_ID,COUPON_ID,ITEM_ID,ITEM_QUANTITY) VALUES (?,?,?,?,?)";

	public static String SQL_RETRIEVE_ORDERS_FOR_CUSTOMER = "SELECT ORDER_ID,ADDRESS_ID,TOTAL_PAYMENT,ORDER_TIME,DRIVER_ID,DELIVERY_MODE FROM ofod_order WHERE CUSTOMER_ID = ? ORDER BY ORDER_TIME DESC";

	public static String SQL_RETRIEVE_COMPLETED_ORDERS_FOR_CUSTOMER = "SELECT ORDER_ID,ADDRESS_ID,TOTAL_PAYMENT,ORDER_TIME,DRIVER_ID,DELIVERY_MODE FROM ofod_order WHERE CUSTOMER_ID = ? AND ORDER_STATUS = 4";

	public static String SQL_ITEMS_SELECT = "SELECT ITEM_ID,ITEM_NAME,ITEM_DESCRIPTION,ITEM_PRICE,ITEM_RATING,RESTAURANT_ID FROM ofod_item WHERE ITEM_ID = ?";

	public static String SQL_RESTAURANT_SELECT = "SELECT RESTAURANT_ID,RESTAURANT_NAME,DESCRIPTION,ADDRESS1,ADDRESS2,CITY,ZIPCODE,RATING,"
			+ "PHONE,EMAIL,LATITUDE,LONGITUDE FROM ofod_restaurant WHERE RESTAURANT_ID = ?";

	public static String SQL_ORDER_ITEM_RESTAURANT_DETAILS_FETCH_QUERY = "SELECT R.RESTAURANT_ID,R.RESTAURANT_NAME,"

			+ "I.ITEM_ID,I.ITEM_NAME,I.ITEM_PRICE,O.ORDER_ID,OI.COUPON_ID,OI.ITEM_QUANTITY,C.COUPON_CODE FROM  "
			+ "ofod_order O INNER JOIN ofod_order_item OI ON O.ORDER_ID = OI.ORDER_ID "
			+ "INNER JOIN ofod_restaurant R ON OI.RESTAURANT_ID = R.RESTAURANT_ID "
			+ "INNER JOIN ofod_item I ON OI.ITEM_ID = I.ITEM_ID "
			+ "LEFT JOIN ofod_coupon C ON C.COUPON_ID = OI.COUPON_ID " + " WHERE O.ORDER_ID = ?";

	public static String SQL_ORDER_CUSTOMER_DETAILS_FETCH_QUERY = "SELECT "
			+ "P.CARD_ID,P.CARD_NO,P.NAME_ON_CARD,P.CARD_TYPE,A.ADDRESS_ID,A.CUSTOMER_ID,A.FIRST_NAME,A.LAST_NAME,A.ADDRESS1,A.ADDRESS2,A.CITY,A.ZIPCODE,A.PHONE, "
			+ "O.ORDER_ID,O.TOTAL_PAYMENT,O.ORDER_STATUS,O.ORDER_TIME,O.DELIVERY_MODE,O.ORDER_FULFILMENT_TIME "
			+ "FROM ofod_payment P INNER JOIN ofod_order O ON O.CARD_ID = P.CARD_ID "
			+ "LEFT JOIN ofod_caddress A ON O.ADDRESS_ID = A.ADDRESS_ID WHERE O.CUSTOMER_ID = ? ORDER BY O.ORDER_TIME DESC";

	public static String SQL_ORDER_INSERT = "INSERT INTO ofod_order (CUSTOMER_ID, CARD_ID, ADDRESS_ID, TOTAL_PAYMENT, ORDER_TIME, ORDER_STATUS, "
			+ "DELIVERY_MODE) VALUES( ?, ?, ?, ?, NOW(), ?, ?)";

	public static String SQL_ORDER__ITEM_INSERT = "INSERT INTO ofod_order_item (ORDER_ID, RESTAURANT_ID, COUPON_ID, ITEM_ID, ITEM_QUANTITY) "
			+ "VALUES(?, ?, ?, ?, ?)";

	public static String SQL_FETCH_TOP_3_ITEMS = "SELECT I.ITEM_ID, I.ITEM_NAME, AVG(UR.RATING) AS I_RATING, R.RESTAURANT_ID, "
			+ "R.RESTAURANT_NAME, I.ITEM_DESCRIPTION, I.ITEM_PRICE, R.ZIPCODE, R.CITY " + "FROM ofod_item I "
			+ "INNER JOIN ofod_restaurant R ON I.RESTAURANT_ID = R.RESTAURANT_ID "
			+ "LEFT JOIN ofod_item_rating IR ON IR.ITEM_ID = I.ITEM_ID "
			+ "LEFT JOIN ofod_user_rating UR ON UR.RATING_ID = IR.RATING_ID " + "GROUP BY I.ITEM_ID "
			+ "ORDER BY I_RATING DESC LIMIT 3;";

	public static String SQL_FETCH_TOP_3_RESTAURANTS = "SELECT R.RESTAURANT_ID,RESTAURANT_NAME,DESCRIPTION,ADDRESS1,ADDRESS2,CITY,ZIPCODE,PHONE, EMAIL, AVG(UR.RATING) AS RATING "
			+ "FROM ofod_restaurant R "
			+ "LEFT JOIN  ofod_restaurant_rating RR ON R.RESTAURANT_ID = RR.RESTAURANT_ID "
			+ "LEFT JOIN ofod_user_rating UR ON UR.RATING_ID = RR.RATING_ID " + "GROUP BY R.RESTAURANT_ID "
			+ "ORDER BY RATING DESC LIMIT 3;";

	public static String SQL_FETCH_ALL_COUPONS = "SELECT COUPON_ID,COUPON_CODE,COUPON_DESCRIPTION,COUPON_TYPE,COUPON_VALUE,COUPON_VALIDITY FROM ofod_coupon;";

	public static String SQL_FETCH_SELECTED_COUPON = "SELECT COUPON_ID,COUPON_CODE,COUPON_DESCRIPTION,COUPON_TYPE,COUPON_VALUE,COUPON_VALIDITY FROM ofod_coupon"
			+ " WHERE COUPON_CODE = ? ;";

	public static String SQL_USER_RATING_INSERT = "INSERT INTO ofod_user_rating(CUSTOMER_ID,RATING) VALUES(?,?)";

	public static String SQL_RESTAURANT_RATING_INSERT = "INSERT INTO ofod_restaurant_rating(RATING_ID,RESTAURANT_ID) VALUES(?,?)";

	public static String SQL_ITEM_RATING_INSERT = "INSERT INTO ofod_item_rating(RATING_ID, ITEM_ID) VALUES(?,?)";

	/************************************************************************************************************************************
	 * Admin SQL queries
	 **************************************************************************************************************************************/
	public static String SQL_ADMIN_LOGIN_VALIDATE = "SELECT * from ofod_admin where username = ? and password = ?";

	public static String SQL_ADMIN_UPDATE = "update ofod_admin set admin_id=?,first_name=?,last_name=?,email=?,phone=? where username=?";

	public static String SQL_ADMIN_LOGIN_UPDATE = "update ofod_admin set password=? where username=?";

	public static String SQL_ADMIN_GET_ALL_DRIVER = "SELECT * from ofod_driver;";

	public static String SQL_ADMIN_DRIVER_DELETE = "delete from ofod_driver where driver_id=?";

	public static String SQL_ADMIN_DRIVER_INSERT = "insert into ofod_driver (first_name,last_name,phone,email,vehicle_no,license_no) values (?,?,?,?,?,?)";

	public static String SQL_ADMIN_DRIVER_UPDATE = "update ofod_driver set first_name=?,last_name=?,phone=?,email=?,vehicle_no=?,license_no=? where driver_id=?";

	public static String SQL_ADMIN_DRIVER_FIND = "SELECT * from ofod_driver where driver_id = ?";

	public static String SQL_ADMIN_DRIVER_COUNT = "SELECT count( DISTINCT(driver_id) ) FROM ofod_driver;";

	public static String SQL_ADMIN_ITEM_UPDATE = "update ofod_item set item_name=?,item_description=?,item_price=?,restaurant_id=? where item_id=?";

	public static String SQL_ADMIN_ITEM_INSERT = "insert into ofod_item (item_name,item_description,item_price,restaurant_ID) values (?,?,?,?)";

	public static String SQL_ADMIN_ITEM_DELETE = "delete from ofod_item where item_id=?";

	public static String SQL_ADMIN_ITEM_FETCH = "SELECT item_id,item_name,item_description,item_price,restaurant_ID from ofod_item where restaurant_id=?;";

	public static String SQL_ADMIN_ORDER_COUNT = "SELECT count( DISTINCT(order_id) ) FROM ofod_order;";

	public static String SQL_ADMIN_ORDER_FULLFILMENT_COUNT = "SELECT count( DISTINCT(order_id) ) FROM ofod_order where order_status=4;";

	public static String SQL_ADMIN_ORDER_TODAY_COUNT = "SELECT count( DISTINCT(order_id) ) FROM ofod_order where DATE(order_time)=curdate();";

	public static String SQL_ADMIN_ORDER_EXIST = "SELECT EXISTS(SELECT * FROM ofod_order WHERE order_ID= ?)";

	public static String SQL_ADMIN_ORDER_DETAILS = "SELECT order_item_id,order_id,restaurant_id,item_id,item_quantity from ofod_order_item where order_id = ?";

	public static String SQL_ADMIN_ORDER_LIST = "SELECT order_id,customer_id,total_payment,order_time,order_status,delivery_mode from ofod_order;";

	public static String SQL_ADMIN_ORDER_DETAIL_CUSTOMER = "SELECT order_id,customer_id,total_payment,delivery_mode from ofod_order where customer_id = ?";

	public static String SQL_ADMIN_RESTAURANT_ALL = "SELECT restaurant_id,restaurant_name,description,address1,address2,city,zipcode,email,phone from ofod_restaurant;";

	public static String SQL_ADMIN_RESTAURANT_DELETE = "delete from ofod_restaurant where restaurant_id=?";
	
	public static String SQL_ADMIN_RESTAURANT_INSERT = "insert into ofod_restaurant (restaurant_name,description,address1,address2,city,zipcode,email,phone) values (?,?,?,?,?,?,?,?)";
	
	public static String SQL_ADMIN_RESTAURANT_UPDATE = "update ofod_restaurant set restaurant_name=?,description=?,address1=?,address2=?,city=?,zipcode=?,email=?,phone=? where restaurant_id=?";
	
	public static String SQL_ADMIN_RESTAURANT_COUNT = "SELECT count( DISTINCT(restaurant_id) ) FROM ofod_restaurant;";

	public static String SQL_ADMIN_CUSTOMER_COUNT = "SELECT count( DISTINCT(customer_id) ) FROM ofod_customer;";
	
}
