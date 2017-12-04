package com.itm.food.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.itm.food.dao.AbstractDomain;
import com.itm.food.dao.Admin;
import com.itm.food.model.db.MySQLQuery;

public class AdminDB extends AbstractDB implements IDBAccess {

	private static final Logger log = Logger.getLogger(AdminDB.class);

	@Override
	public <T extends AbstractDomain> int add(T object) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public <T extends AbstractDomain> void update(T object) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public <T extends AbstractDomain> T find(int id) throws Exception {
		// TODO Auto-generated method stub
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

	public Admin getAdminDetails(String user, String password) throws Exception {
		ResultSet res = null;
		Admin admin = null;
		try {
			PreparedStatement statement = this.getDBConnection().prepareStatement(MySQLQuery.SQL_ADMIN_LOGIN_VALIDATE);
			statement.setString(1, user);
			statement.setString(2, password);
			res = statement.executeQuery();
			
			if(res.next()) {
				admin = new Admin();
				admin.setAdminID(res.getString("admin_id"));
				admin.setFirstName(res.getString("first_name"));
				admin.setLastName(res.getString("last_name"));
				admin.setEmail(res.getString("email"));
				admin.setPhone(res.getString("phone"));
				admin.setUsername(res.getString("username"));
				admin.setPassword(res.getString("password"));
				}
			
		} catch (SQLException s) {
			log.error(s.getMessage(), s);
		}
		return admin;
	}

	public void updateAdminDetails(String user, String adminid, String firstname, String lastname, String email,
			String phone) throws Exception {
		try {
			PreparedStatement statement = this.getDBConnection().prepareStatement(MySQLQuery.SQL_ADMIN_UPDATE);
			statement.setString(1, adminid);
			statement.setString(2, firstname);
			statement.setString(3, lastname);
			statement.setString(4, email);
			statement.setString(5, phone);
			statement.setString(6, user);
			statement.executeUpdate();
			log.debug("Data updated for user " + user);

		} catch (Exception s) {
			log.error(s.getMessage(), s);

		}
	}

	public void updateAdminPassword(String user, String newPwd) throws Exception {
		try {
			PreparedStatement statement = this.getDBConnection().prepareStatement(MySQLQuery.SQL_ADMIN_LOGIN_UPDATE);
			statement.setString(1, newPwd);
			statement.setString(2, user);
			statement.executeUpdate();
			log.debug("Password updated for user " + user);

		} catch (Exception s) {
			log.error(s.getMessage(), s);
		}
	}

}
