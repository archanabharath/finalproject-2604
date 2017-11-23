package com.itm.food.model;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.itm.food.dao.AbstractDomain;
import com.itm.food.dao.Customer;
import com.itm.food.model.db.MySQLQuery;
import com.itm.food.util.PasswordUtil;

public class CustomerDB extends AbstractDB implements IDBAccess {

	private static final Logger log = Logger.getLogger(CustomerDB.class);

	@Override
	public <T extends AbstractDomain> String add(T object) throws Exception {
		Customer customerObj = (Customer) object;
		log.debug("Fetched Customer");
		try {
			PreparedStatement preparedStatement = this.getDBConnection()
					.prepareStatement(MySQLQuery.SQL_CUSTOMER_INSERT);
			preparedStatement.setString(1, customerObj.getCustomerID()); // CUSTOMER_ID
			preparedStatement.setString(2, customerObj.getFirstName()); // FIRST_NAME
			preparedStatement.setString(3, customerObj.getLastName());// LAST_NAME
			preparedStatement.setDate(4,
					new Date(new SimpleDateFormat("yyyy-MM-dd").parse(customerObj.getDOB()).getTime()));// BIRTH_DATE
			preparedStatement.setString(5, customerObj.getPhoneNo()); // PHONE
			preparedStatement.setString(6, customerObj.getEmail()); // EMAIL
			preparedStatement.setString(7, customerObj.getUsername()); // USERNAME
			preparedStatement.setString(8, customerObj.getEncryptedPassword()); // PASSWORD

			preparedStatement.execute();
			preparedStatement.close();

		} catch (Exception ex) {
			log.error(ex.getMessage());
			throw ex;
		} finally {
			this.closeConnection();
		}
		log.debug("Added Customer - " + customerObj.getCustomerID());
		return customerObj.getCustomerID();
	}

	@Override
	public <T extends AbstractDomain> void update(T object) {
		Customer updateCustDetails = (Customer) object;
		String updateProfileQuery = null;
		log.debug("Customer");
		if (StringUtils.isNotEmpty(updateCustDetails.getFirstName())) {
			updateProfileQuery = "UPDATE ofod.ofod_customer SET FIRST_NAME = '" + updateCustDetails.getFirstName()
					+ "' WHERE CUSTOMER_ID = ? ;";
			if (StringUtils.isNotEmpty(updateCustDetails.getLastName())) {
				updateProfileQuery = "UPDATE ofod.ofod_customer SET FIRST_NAME = '" + updateCustDetails.getFirstName()
						+ "'," + "LAST_NAME = '" + updateCustDetails.getLastName() + "' WHERE CUSTOMER_ID = ? ;";
				if (StringUtils.isNotEmpty(updateCustDetails.getEmail())) {

					updateProfileQuery = "UPDATE ofod.ofod_customer SET FIRST_NAME = '"
							+ updateCustDetails.getFirstName() + "'," + "LAST_NAME = '"
							+ updateCustDetails.getLastName() + "'," + "EMAIL = '" + updateCustDetails.getEmail()
							+ "' WHERE CUSTOMER_ID = ? ;";
					if (StringUtils.isNotEmpty(updateCustDetails.getPassword())) {
						updateProfileQuery = "UPDATE ofod.ofod_customer SET FIRST_NAME = '"
								+ updateCustDetails.getFirstName() + "'," + "LAST_NAME = '"
								+ updateCustDetails.getLastName() + "'," + "EMAIL = '" + updateCustDetails.getEmail()
								+ "'," + "PASSWORD = '" + updateCustDetails.getPassword() + "' WHERE CUSTOMER_ID = ? ;";

					} // firstname, lastname,email,password input not empty
				} // firstname,lastname,email input not empty

			} // first name and last name input not empty

		} // first name input alone not empty

		try {
			PreparedStatement preparestatement = this.getDBConnection().prepareStatement(updateProfileQuery);
			preparestatement.setString(1, updateCustDetails.getCustomerID());
			preparestatement.executeUpdate();
			log.debug("Customer details updated successfully");
		} catch (ClassNotFoundException e) {
			log.error(e.getMessage());
		} catch (SQLException e) {
			log.error(e.getSQLState());
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends AbstractDomain> T find(String id) throws Exception {
		Customer customer = new Customer();
		try {
			PreparedStatement preparedstatement = this.getDBConnection()
					.prepareStatement(MySQLQuery.SQL_CUSTOMER_SELECT);
			preparedstatement.setString(1, id);
			ResultSet rs;
			rs = preparedstatement.executeQuery();
			if (rs.first()) {
				customer.setCustomerID(rs.getString(1));
				customer.setFirstName(rs.getString(2));
				customer.setLastName(rs.getString(3));
				customer.setDOB(rs.getString(4));
				customer.setPhoneNo(rs.getString(5));
				customer.setEmail(rs.getString(6));
				customer.setUsername(rs.getString(7));
				customer.setPassword(rs.getString(8));
				log.debug("Customer found Successfully.");
			} else {
				log.error("Customer not found");
			}
			preparedstatement.close();
		} catch (Exception e) {
			log.error(e.getMessage());

		} finally {
			this.closeConnection();
		}
		return (T) customer;
	}

	@Override
	public <T extends AbstractDomain> void delete(T object) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(String id) {
		// TODO Auto-generated method stub

	}

	public String customerLoginCheck(String username, String password) throws SQLException {

		String custId = null;

		try {
			PreparedStatement preparedstatement = this.getDBConnection()
					.prepareStatement(MySQLQuery.SQL_VERIFY_CUSTOMER_LOGIN);
			preparedstatement.setString(1, username);
			preparedstatement.setString(2, PasswordUtil.encryptPassword(password));
			ResultSet rs;
			rs = preparedstatement.executeQuery();
			if (rs.first()) {
				custId = rs.getString(1);
			} else {
				log.error("Username not found");
			}
			preparedstatement.close();
		} catch (Exception e) {
			log.error(e.getMessage());

		} finally {
			this.closeConnection();
		}

		return custId;
	}

	public boolean validateUsername(String userName) throws SQLException, ClassNotFoundException {
		boolean usernameFound = true;

		try {
			PreparedStatement preparestatement = this.getDBConnection()
					.prepareStatement(MySQLQuery.SQL_VALIDATE_USERNAME);
			preparestatement.setString(1, userName);
			ResultSet usernamers;
			usernamers = preparestatement.executeQuery();
			while (usernamers.next()) {
				if (usernamers.getInt(1) == 0) {
					usernameFound = false;
				}
			}
			preparestatement.close();
		} catch (SQLException e) {
			log.error(e.getMessage());
		} finally {
			this.closeConnection();
		}

		return usernameFound;

	}

	/*
	 * Retrieve customer information from customer table to print it on profile page
	 */

	public Customer pullCustomerDetails(String transferCustId) throws SQLException {

		Customer userProfile = new Customer();
		try {
			PreparedStatement preparestatement = this.getDBConnection()
					.prepareStatement(MySQLQuery.SQL_FETCH_CUSTOMER_PROFILE);
			preparestatement.setString(1, transferCustId);
			ResultSet customerProfilers;
			customerProfilers = preparestatement.executeQuery();
			while (customerProfilers.next()) {
				userProfile.setFirstName(customerProfilers.getString(1));
				userProfile.setLastName(customerProfilers.getString(2));
				userProfile.setEmail(customerProfilers.getString(3));
				userProfile.setUsername(customerProfilers.getString(4));
				userProfile.setEncryptedPassword(customerProfilers.getString(5));
				userProfile.setDOB(customerProfilers.getDate(6).toString());

			}
		} catch (ClassNotFoundException e) {
			log.error(e.getMessage());

		} catch (SQLException e) {
			log.error(e.getMessage());

		} finally {
			this.closeConnection();

		}
		return userProfile;

	}

}
