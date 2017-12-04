package com.itm.food.controller.admin;

import org.apache.log4j.Logger;

import com.itm.food.dao.Admin;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

/** Controls the login screen */
public class AdminLoginController extends AdminManager {

	private static final Logger log = Logger.getLogger(AdminLoginController.class);

	@FXML
	private TextField user;
	@FXML
	private PasswordField password;
	@FXML
	private Button loginButton;
	@FXML
	GridPane loginGrid = new GridPane();

	// create an instance of Admin
	Admin admin = new Admin();

	/**
	 * @return the admin
	 */
	public Admin getAdmin() {
		return admin;
	}

	/**
	 * @param admin
	 *            the admin to set
	 */
	public void setAdmin(Admin admin) {
		this.admin = admin;
	}

	private static int sessionID = 0;

	public void initManager(final AdminManager loginManager) {
	}

	/**
	 * Check authorization credentials.
	 * 
	 * If accepted, return a sessionID for the authorized session otherwise,
	 * return null.
	 */
	public String authorize(String username, String password) {
		String loginStatus = null;

		try {
			admin = adminDB.getAdminDetails(username, password);
			if (null != admin) {
				loginStatus = generateSessionID();
			} else {
				loginStatus = null;
			}
		} catch (Exception s) {
			log.error(s.getMessage(), s);
		}
		return loginStatus;
	}

	// method to generate Session ID
	private String generateSessionID() {
		sessionID++;
		return "Login - session " + sessionID;
	}

}
