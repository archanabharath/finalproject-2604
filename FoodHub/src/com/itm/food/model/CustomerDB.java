package com.itm.food.model;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.log4j.Logger;

import com.itm.food.dao.AbstractDomain;
import com.itm.food.dao.Customer;
import com.itm.food.model.db.MySQLQuery;
import com.itm.food.util.PasswordUtil;
import com.mysql.jdbc.Statement;

public class CustomerDB extends AbstractDB implements IDBAccess {

	private static final Logger log = Logger.getLogger(CustomerDB.class);

	@Override
	public <T extends AbstractDomain> int add(T object) throws Exception {
		Customer customerObj = (Customer) object;
		log.debug("Fetched Customer");
		try {
			PreparedStatement preparedStatement = this.getDBConnection()
					.prepareStatement(MySQLQuery.SQL_CUSTOMER_INSERT, Statement.RETURN_GENERATED_KEYS);
			// preparedStatement.setString(1, customerObj.getCustomerID()); //
			// CUSTOMER_ID
			preparedStatement.setString(1, customerObj.getFirstName()); // FIRST_NAME
			preparedStatement.setString(2, customerObj.getLastName());// LAST_NAME
			preparedStatement.setDate(3,
					new Date(new SimpleDateFormat("yyyy-MM-dd").parse(customerObj.getDOB()).getTime()));// BIRTH_DATE
			preparedStatement.setString(4, customerObj.getPhoneNo()); // PHONE
			preparedStatement.setString(5, customerObj.getEmail()); // EMAIL
			preparedStatement.setString(6, customerObj.getUsername()); // USERNAME
			preparedStatement.setString(7, customerObj.getEncryptedPassword()); // PASSWORD
			preparedStatement.executeUpdate();
			ResultSet rsReturnKey = preparedStatement.getGeneratedKeys();
			while (rsReturnKey.next()) {
				customerObj.setCustomerID(rsReturnKey.getInt(1));
			}

			preparedStatement.close();

		} catch (Exception ex) {
			log.error(ex.getMessage());
			throw ex;
		}
		log.debug("Added Customer - " + customerObj.getCustomerID());
		return customerObj.getCustomerID();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.itm.food.model.IDBAccess#update(com.itm.food.dao.AbstractDomain)
	 * update customer details
	 */
	@Override
	public <T extends AbstractDomain> void update(T object) throws ParseException {
		Customer updateCustDetails = (Customer) object;
		String updateProfileQuery = null;
		log.debug("Customer");

		updateProfileQuery = "UPDATE ofod.ofod_customer SET FIRST_NAME = '" + updateCustDetails.getFirstName() + "',"
				+ "LAST_NAME = '" + updateCustDetails.getLastName() + "'," + "EMAIL = '" + updateCustDetails.getEmail()
				+ "'," + "USERNAME = '" + updateCustDetails.getUsername() + "'," + "DOB = '"
				+ (new Date(new SimpleDateFormat("yyyy-MM-dd").parse(updateCustDetails.getDOB()).getTime())) + "',"
				+ "PASSWORD = '" + updateCustDetails.getEncryptedPassword() + "' WHERE CUSTOMER_ID = '"
				+ updateCustDetails.getCustomerID() + "';";

		try {
			PreparedStatement preparestatement = this.getDBConnection().prepareStatement(updateProfileQuery);

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
	public <T extends AbstractDomain> T find(int i) throws Exception {
		Customer customer = new Customer();
		try {
			PreparedStatement preparedstatement = this.getDBConnection()
					.prepareStatement(MySQLQuery.SQL_CUSTOMER_SELECT);
			preparedstatement.setInt(1, i);
			ResultSet rs;
			rs = preparedstatement.executeQuery();
			if (rs.first()) {
				customer.setCustomerID(rs.getInt(1));
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

		}
		return (T) customer;
	}

	@Override
	public <T extends AbstractDomain> void delete(T object) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub

	}

	/*
	 * validate if the entered login credentials are valid for the customer
	 */
	public int customerLoginCheck(String username, String password) throws SQLException {

		int custId = 0;

		try {
			PreparedStatement preparedstatement = this.getDBConnection()
					.prepareStatement(MySQLQuery.SQL_VERIFY_CUSTOMER_LOGIN);
			preparedstatement.setString(1, username);
			preparedstatement.setString(2, PasswordUtil.encryptPassword(password));
			ResultSet rs;
			rs = preparedstatement.executeQuery();
			if (rs.first()) {
				custId = rs.getInt(1);
			} else {
				log.error("Username not found");
			}
			preparedstatement.close();
		} catch (Exception e) {
			log.error(e.getMessage());

		}
		return custId;
	}

	/*
	 * validate if the username entered by the user already exists or not
	 */
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
		}
		return usernameFound;

	}

	/*
	 * Retrieve customer information from customer table to print it on profile
	 * page
	 */

	public Customer pullCustomerDetails(int i) throws Exception {
		Customer userProfile = new Customer();
		try {
			PreparedStatement preparestatement = this.getDBConnection()
					.prepareStatement(MySQLQuery.SQL_FETCH_CUSTOMER_PROFILE);
			preparestatement.setInt(1, i);
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
			throw e;
		} catch (SQLException e) {
			log.error(e.getMessage());
			throw e;
		}
		return userProfile;
	}

	public int getCustomerCount() throws Exception {
		ResultSet rs = null;
		int count = 0;
		try {
			PreparedStatement statement = this.getDBConnection().prepareStatement(MySQLQuery.SQL_ADMIN_CUSTOMER_COUNT);
			rs = statement.executeQuery();
			if (rs.next())
				count = rs.getInt(1);
			log.debug(" count retrieved from customer");
		} catch (SQLException s) {
			log.error(s.getMessage(), s);
			throw s;
		} finally {
			rs.close();
		}
		return count;
	}

}
