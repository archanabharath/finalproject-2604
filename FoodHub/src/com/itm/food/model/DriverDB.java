package com.itm.food.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.itm.food.dao.AbstractDomain;
import com.itm.food.dao.Drivers;
import com.itm.food.model.db.MySQLQuery;
import com.mysql.jdbc.Statement;

public class DriverDB extends AbstractDB implements IDBAccess {

	private static final Logger log = Logger.getLogger(DriverDB.class);

	@Override
	public <T extends AbstractDomain> int add(T object) throws Exception {
		Drivers driver = (Drivers) object;
		int id = 0;
		ResultSet rs = null;
		try {
			PreparedStatement statement = this.getDBConnection().prepareStatement(MySQLQuery.SQL_ADMIN_DRIVER_INSERT,
					Statement.RETURN_GENERATED_KEYS);

			statement.setString(1, driver.getFirstName());
			statement.setString(2, driver.getLastName());
			statement.setString(3, driver.getPhoneNo());
			statement.setString(4, driver.getEmail());
			statement.setString(5, driver.getVehicleNo());
			statement.setString(6, driver.getLicenseNo());

			statement.executeUpdate();
			rs = statement.getGeneratedKeys();
			if (rs.next()) {
				id = rs.getInt(1);
			}
			log.debug("Data inserted for driver");
			statement.close();
		} catch (SQLException s) {
			log.error(s.getMessage());
			throw s;
		}
		return id;
	}

	@Override
	public <T extends AbstractDomain> void update(T object) throws Exception {
		try {
			Drivers driver = (Drivers) object;
			PreparedStatement statement = this.getDBConnection().prepareStatement(MySQLQuery.SQL_ADMIN_DRIVER_UPDATE);
			statement.setString(1, driver.getFirstName());
			statement.setString(2, driver.getLastName());
			statement.setString(3, driver.getPhoneNo());
			statement.setString(4, driver.getEmail());
			statement.setString(5, driver.getVehicleNo());
			statement.setString(6, driver.getLicenseNo());
			statement.setInt(7, driver.getDriverId());

			statement.executeUpdate();
			statement.close();
			log.debug("Data updated for driver " + driver.getDriverId());

		} catch (Exception s) {
			s.getMessage();

		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends AbstractDomain> T find(int id) throws Exception {
		ResultSet rs = null;
		Drivers driver = new Drivers();
		try {
			PreparedStatement statement = this.getDBConnection().prepareStatement(MySQLQuery.SQL_ADMIN_DRIVER_FIND);
			statement.setInt(1, id);

			rs = statement.executeQuery();
			if (rs.next()) {
				driver.setDriverId(rs.getInt(1));
				driver.setFirstName(rs.getString(2));
				driver.setLastName(rs.getString(3));
				driver.setPhoneNo(rs.getString(4));
				driver.setEmail(rs.getString(5));
				driver.setVehicleNo(rs.getString(6));
				driver.setLicenseNo(rs.getString(7));
			}
			log.debug("Data selected from table for driver " + id);

		} catch (SQLException s) {
			log.error(s.getMessage(), s);
			throw s;
		}
		return (T) driver;

	}

	@Override
	public <T extends AbstractDomain> void delete(T object) throws Exception {
		Drivers driver = (Drivers) object;
		try {
			PreparedStatement statement = this.getDBConnection().prepareStatement(MySQLQuery.SQL_ADMIN_DRIVER_DELETE);
			statement.setInt(1, driver.getDriverId());
			statement.executeUpdate();
			statement.close();
			log.debug("Data deleted for driver " + driver.getDriverId());
		} catch (Exception s) {
			log.error(s.getMessage(), s);
			throw s;
		}
	}

	@Override
	public void delete(int id) throws Exception {
		try {
			PreparedStatement statement = this.getDBConnection().prepareStatement(MySQLQuery.SQL_ADMIN_DRIVER_DELETE);
			statement.setInt(1, id);
			statement.executeUpdate();
			statement.close();
			log.debug("Data deleted for driver " + id);
		} catch (Exception s) {
			log.error(s.getMessage(), s);
			throw s;
		}
	}

	public ResultSet getAllDrivers() throws Exception {
		ResultSet rs = null;
		try {
			PreparedStatement statement = this.getDBConnection().prepareStatement(MySQLQuery.SQL_ADMIN_GET_ALL_DRIVER);
			rs = statement.executeQuery();
			log.debug("Data selected from driver table");
		} catch (SQLException s) {
			log.error(s.getMessage(), s);
			throw s;
		}
		return rs;
	}

	public int getDriverCount() throws Exception {
		ResultSet rs = null;
		int count = 0;
		try {
			PreparedStatement statement = this.getDBConnection().prepareStatement(MySQLQuery.SQL_ADMIN_DRIVER_COUNT);
			rs = statement.executeQuery();
			log.debug("Count retrieved for driver");
			if (rs.next()) {
				count = rs.getInt(1);
			}
			rs.close();
		} catch (SQLException s) {
			log.debug(s.getMessage(), s);
			throw s;
		} finally {
			rs.close();
		}
		return count;

	}

}
