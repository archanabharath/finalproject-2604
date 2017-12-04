package com.itm.food.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.itm.food.dao.AbstractDomain;
import com.itm.food.dao.Address;
import com.itm.food.model.db.MySQLQuery;

public class AddressDB extends AbstractDB implements IDBAccess {

	private static final Logger log = Logger.getLogger(AddressDB.class);

	@Override
	public <T extends AbstractDomain> int add(T object) throws Exception {
		Address custaddrobj = (Address) object;
		log.debug("Adding Address");
		try {
			PreparedStatement preparedStatement = this.getDBConnection()
					.prepareStatement(MySQLQuery.SQL_CUSTOMER_ADDRESS_INSERT);
			//preparedStatement.setString(1, custaddrobj.getAddrId()); // ADDRESS_ID
			preparedStatement.setInt(1, custaddrobj.getCustId()); // CUSTOMER_ID
			preparedStatement.setString(2, custaddrobj.getFname());// FIRST_NAME
			preparedStatement.setString(3, custaddrobj.getLname());// LAST_NAME
			preparedStatement.setString(4, custaddrobj.getAddr1()); // ADDRESS1
			preparedStatement.setString(5, custaddrobj.getAddr2()); // ADDRESS2
			preparedStatement.setString(6, custaddrobj.getCity()); // CITY
			preparedStatement.setInt(7, custaddrobj.getPincode()); // ZIPCODE
			preparedStatement.setString(8, custaddrobj.getAddrphoneNo()); // PHONE

			preparedStatement.execute();
			preparedStatement.close();

		} catch (Exception ex) {
			log.error(ex.getMessage());
			throw ex;
		} 
		log.debug("Added Address - " + custaddrobj.getAddrId());
		return custaddrobj.getAddrId();
	}

	@Override
	public <T extends AbstractDomain> void update(T object) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	// get all addresses for a customer from address table
	public <T extends AbstractDomain> T find(int id) throws Exception {
		return null;

	}

	@Override
	public <T extends AbstractDomain> void delete(T object) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(int id) throws Exception {
		// TODO Auto-generated method stub
	}

	public List<Address> getAddresses(int i) throws ClassNotFoundException {
		log.debug("Getting addresses for the customer");
		List<Address> addressList = new ArrayList<Address>();
		ResultSet rsAllAddresses;
		try {
			PreparedStatement preparestatement = this.getDBConnection()
					.prepareStatement(MySQLQuery.SQL_GET_ALL_ADDRESSES_OF_CUSTOMER);
			preparestatement.setInt(1, i);
			rsAllAddresses = preparestatement.executeQuery();
			while (rsAllAddresses.next()) {
				// FIRST_NAME,LAST_NAME,ADDRESS1,ADDRESS2,CITY,ZIPCODE,PHONE
				Address address = new Address();
				address.setFname(rsAllAddresses.getString(1));
				address.setLname(rsAllAddresses.getString(2));
				address.setAddr1(rsAllAddresses.getString(3));
				address.setAddr2(rsAllAddresses.getString(4));
				address.setCity(rsAllAddresses.getString(5));
				address.setPincode(rsAllAddresses.getInt(6));
				address.setAddrphoneNo(rsAllAddresses.getString(7));
				address.setAddrId(rsAllAddresses.getInt(8));
				address.setCustId(rsAllAddresses.getInt(9));
				addressList.add(address);
			}
		} catch (SQLException e) {
			log.error(e.getMessage());

		}
		return addressList;
	}

}
