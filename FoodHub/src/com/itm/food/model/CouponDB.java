package com.itm.food.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.itm.food.dao.Coupon;
import com.itm.food.model.db.MySQLQuery;

public class CouponDB extends AbstractDB {

	private static final Logger log = Logger.getLogger(CouponDB.class);

	public ArrayList<Coupon> getAllCoupons() {
		// COUPON_ID,COUPON_CODE,COUPON_DESCRIPTION,COUPON_TYPE,COUPON_VALUE,COUPON_VALIDITY
		ArrayList<Coupon> allCoupons = new ArrayList<Coupon>();
		try {
			PreparedStatement preparestatement = this.getDBConnection()
					.prepareStatement(MySQLQuery.SQL_FETCH_ALL_COUPONS);
			ResultSet rsCoupons;
			rsCoupons = preparestatement.executeQuery();
			while (rsCoupons.next()) {
				Coupon coupon = new Coupon();
				coupon.setCouponId(rsCoupons.getString(1));
				coupon.setCouponCode(rsCoupons.getString(2));
				coupon.setCouponDescription(rsCoupons.getString(3));
				coupon.setCouponType(rsCoupons.getInt(4));
				coupon.setCouponValue(rsCoupons.getInt(5));
				coupon.setCouponValidity(rsCoupons.getDate(6).toString());

				allCoupons.add(coupon);
			}
		} catch (ClassNotFoundException e) {
			log.error(e.getMessage());
		} catch (SQLException e) {
			log.error(e.getMessage());
		}
		return allCoupons;

	}

	public Coupon getSelectedCoupon(String couponCode) {

		Coupon selectCoupon = new Coupon();
		try {// COUPON_ID,COUPON_CODE,COUPON_DESCRIPTION,COUPON_TYPE,COUPON_VALUE,COUPON_VALIDITY
			PreparedStatement preparestatement = this.getDBConnection()
					.prepareStatement(MySQLQuery.SQL_FETCH_SELECTED_COUPON);
			preparestatement.setString(1, couponCode);
			ResultSet rsSelectedCoupons;
			rsSelectedCoupons = preparestatement.executeQuery();
			while (rsSelectedCoupons.next()) {
				selectCoupon.setCouponId(rsSelectedCoupons.getString(1));
				selectCoupon.setCouponCode(rsSelectedCoupons.getString(2));
				selectCoupon.setCouponDescription(rsSelectedCoupons.getString(3));
				selectCoupon.setCouponType(rsSelectedCoupons.getInt(4));
				selectCoupon.setCouponValue(rsSelectedCoupons.getInt(5));
				selectCoupon.setCouponValidity(rsSelectedCoupons.getDate(6).toString());

			}
		} catch (ClassNotFoundException e) {
			log.error(e.getMessage());
		} catch (SQLException e) {
			log.error(e.getMessage());
		}

		return selectCoupon;

	}

}
