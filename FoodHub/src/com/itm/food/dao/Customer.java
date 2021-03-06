package com.itm.food.dao;

import java.util.List;

import org.apache.log4j.Logger;

import com.itm.food.util.PasswordUtil;

public class Customer extends AbstractDomain {

	private static final Logger log = Logger.getLogger(Customer.class);

	String customerID;
	String firstName;
	String lastName;
	String DOB;
	String username;
	String password;
	String encryptedPassword;
	String phoneNo;
	String email;
	List<Address> addresses;
	List<Payment> payments;

	/**
	 * @return the customerID
	 */
	public String getCustomerID() {
		return customerID;
	}

	/**
	 * @param string
	 *            the customerID to set
	 */
	public void setCustomerID(String uuid) {
		this.customerID = uuid;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName
	 *            the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;

	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName
	 *            the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the dOB
	 */
	public String getDOB() {
		return DOB;
	}

	/**
	 * @param dOB
	 *            the dOB to set
	 */
	public void setDOB(String dOB) {
		DOB = dOB;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username
	 *            the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the encryptedPassword
	 */
	public String getEncryptedPassword() {
		return encryptedPassword;
	}

	/**
	 * @param encryptedPassword
	 *            the encryptedPassword to set
	 */
	public void setEncryptedPassword(String encryptedPassword) {
		this.encryptedPassword = encryptedPassword;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		try {
			return PasswordUtil.decryptPassword(getEncryptedPassword());
		} catch (Exception ex) {
			log.error(ex.getMessage());
		}
		return getEncryptedPassword();
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		try {
			this.encryptedPassword = PasswordUtil.encryptPassword(password);
		} catch (Exception ex) {
			log.error(ex.getMessage());
			this.encryptedPassword = password;
		}
	}

	/**
	 * @return the phoneNo
	 */
	public String getPhoneNo() {
		return phoneNo;
	}

	/**
	 * @param phoneNo
	 *            the phoneNo to set
	 */
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the addresses
	 */
	public List<Address> getAddresses() {
		return addresses;
	}

	/**
	 * @param addresses
	 *            the addresses to set
	 */
	public void setAddresses(List<Address> addresses) {
		this.addresses = addresses;
	}

	/**
	 * @return the payments
	 */
	public List<Payment> getPayments() {
		return payments;
	}

	/**
	 * @param payments
	 *            the payments to set
	 */
	public void setPayments(List<Payment> payments) {
		this.payments = payments;
	}

}
