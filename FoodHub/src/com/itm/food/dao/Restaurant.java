package com.itm.food.dao;

import java.util.ArrayList;


public class Restaurant {
	/**
	 * @return the restaurantId
	 */
	public String getRestaurantId() {
		return restaurantId;
	}
	/**
	 * @param restaurantId the restaurantId to set
	 */
	public void setRestaurantId(String restaurantId) {
		this.restaurantId = restaurantId;
	}
	/**
	 * @return the restaurantName
	 */
	public String getRestaurantName() {
		return restaurantName;
	}
	/**
	 * @param restaurantName the restaurantName to set
	 */
	public void setRestaurantName(String restaurantName) {
		this.restaurantName = restaurantName;
	}
	/**
	 * @return the restaurantDescription
	 */
	public String getRestaurantDescription() {
		return restaurantDescription;
	}
	/**
	 * @param restaurantDescription the restaurantDescription to set
	 */
	public void setRestaurantDescription(String restaurantDescription) {
		this.restaurantDescription = restaurantDescription;
	}
	/**
	 * @return the address1
	 */
	public String getAddress1() {
		return address1;
	}
	/**
	 * @param address1 the address1 to set
	 */
	public void setAddress1(String address1) {
		this.address1 = address1;
	}
	/**
	 * @return the address2
	 */
	public String getAddress2() {
		return address2;
	}
	/**
	 * @param address2 the address2 to set
	 */
	public void setAddress2(String address2) {
		this.address2 = address2;
	}
	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}
	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}
	/**
	 * @return the zipcode
	 */
	public int getZipcode() {
		return zipcode;
	}
	/**
	 * @param zipcode the zipcode to set
	 */
	public void setZipcode(int zipcode) {
		this.zipcode = zipcode;
	}
	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}
	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @return the rating
	 */
	public int getRating() {
		return rating;
	}
	/**
	 * @param rating the rating to set
	 */
	public void setRating(int rating) {
		this.rating = rating;
	}
	/**
	 * @return the feedback
	 */
	public String getFeedback() {
		return feedback;
	}
	/**
	 * @param feedback the feedback to set
	 */
	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}
	/**
	 * @return the latitude
	 */
	public double getLatitude() {
		return latitude;
	}
	/**
	 * @param latitude the latitude to set
	 */
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	/**
	 * @return the longitude
	 */
	public double getLongitude() {
		return longitude;
	}
	/**
	 * @param longitude the longitude to set
	 */
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	/**
	 * @return the cuisineId
	 */
	public String getCuisineId() {
		return cuisineId;
	}
	/**
	 * @param cuisineId the cuisineId to set
	 */
	public void setCuisineId(String cuisineId) {
		this.cuisineId = cuisineId;
	}
	/**
	 * @return the menu
	 */
	public ArrayList<Menu>[] getMenu() {
		return menu;
	}
	/**
	 * @param menu the menu to set
	 */
	public void setMenu(ArrayList<Menu>[] menu) {
		this.menu = menu;
	}
	/**
	 * @return the restaurantOverallRating
	 */
	public double getRestaurantOverallRating() {
		return restaurantOverallRating;
	}
	/**
	 * @param restaurantOverallRating the restaurantOverallRating to set
	 */
	public void setRestaurantOverallRating(double restaurantOverallRating) {
		this.restaurantOverallRating = restaurantOverallRating;
	}
	/**
	 * @return the restaurantReview
	 */
	public ArrayList<UserReview>[] getRestaurantReview() {
		return restaurantReview;
	}
	/**
	 * @param restaurantReview the restaurantReview to set
	 */
	public void setRestaurantReview(ArrayList<UserReview>[] restaurantReview) {
		this.restaurantReview = restaurantReview;
	}
	String restaurantId;
	String restaurantName;
	String restaurantDescription;
	String address1;
	String address2;
	String city;
	int zipcode;
	String phone;
	String email;
	int rating;
	String feedback;
	double latitude;
	double longitude;
	String cuisineId;
	ArrayList<Menu> menu[];
	double restaurantOverallRating;
	ArrayList<UserReview> restaurantReview[];
		
}