package com.itm.food.dao;

public class UserReview extends AbstractDomain {
	String customerID;
	String custComments;
	double rating;
	String commentTimestamp;

	/**
	 * @return the customerID
	 */
	public String getCustomerID() {
		return customerID;
	}

	/**
	 * @param customerID
	 *            the customerID to set
	 */
	public void setCustomerID(String customerID) {
		this.customerID = customerID;
	}

	/**
	 * @return the custComments
	 */
	public String getCustComments() {
		return custComments;
	}

	/**
	 * @param custComments
	 *            the custComments to set
	 */
	public void setCustComments(String custComments) {
		this.custComments = custComments;
	}

	/**
	 * @return the rating
	 */
	public double getRating() {
		return rating;
	}

	/**
	 * @param rating
	 *            the rating to set
	 */
	public void setRating(double rating) {
		this.rating = rating;
	}

	/**
	 * @return the commentTimestamp
	 */
	public String getCommentTimestamp() {
		return commentTimestamp;
	}

	/**
	 * @param commentTimestamp
	 *            the commentTimestamp to set
	 */
	public void setCommentTimestamp(String commentTimestamp) {
		this.commentTimestamp = commentTimestamp;
	}

	public void addReviewComments() {

	}

	public void updateReviewComments() {

	}

	public void deleteReviewComments() {

	}
}