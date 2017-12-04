package com.itm.food.controller;

import org.apache.log4j.Logger;

import com.itm.food.controller.admin.AdminLoginController;
import com.itm.food.controller.admin.AdminManager;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.mysql.jdbc.StringUtils;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class LoginController extends BaseController {

	@FXML
	private JFXTextField txtUsername;

	@FXML
	private JFXPasswordField txtPassword;

	@FXML
	private JFXButton btnLogin;

	@FXML
	private Hyperlink linkSignup;

	@FXML
	private AnchorPane errorPane;

	@FXML
	private Label lblErrorMsg;

	@FXML
	private ImageView imgExit;

	@FXML
	private JFXTextField txtAdminUsername;

	@FXML
	private JFXPasswordField txtAdminPassword;

	@FXML
	private JFXButton btnAdminLogin;

	@FXML
	private AnchorPane adminErrorPane;

	@FXML
	private Label lblAdminErrorMsg;

	@FXML
	void handleSignup(MouseEvent event) {
		this.launchScene("Signup Screen", "/com/itm/food/views/Signup.fxml", BaseController.SIGNUP_SCREEN_WIDTH,
				BaseController.SIGNUP_SCREEN_HEIGHT);
	}

	@FXML
	void handleKeyLogin(KeyEvent event) {
		handleLogin();
	}

	@FXML
	void handleMouseLogin(MouseEvent event) {
		handleLogin();
	}

	@FXML
	void handleAdminLogin(ActionEvent event) {
		this.handleAdminLogin();
	}

	AdminLoginController adminLogin = new AdminLoginController();

	private static final Logger log = Logger.getLogger(LoginController.class);

	void handleLogin() {
		try {
			if (StringUtils.isEmptyOrWhitespaceOnly(this.txtUsername.getText())) {
				showErrorMsg("Username is empty");
				return;
			}
			if (StringUtils.isEmptyOrWhitespaceOnly(this.txtPassword.getText())) {
				showErrorMsg("Password is empty");
				return;
			}

			if (validateUser()) {
				this.launchScene("Home Screen", "/com/itm/food/views/Home.fxml", BaseController.MAIN_SCREEN_WIDTH,
						BaseController.MAIN_SCREEN_HEIGHT);
			} else {
				showErrorMsg("Username / Password is not correct");
			}
		} catch (Exception ex) {
			showErrorMsg("Unknown Error.");
		}
	}

	void handleAdminLogin() {
		try {
			if (StringUtils.isEmptyOrWhitespaceOnly(this.txtAdminUsername.getText())) {
				showAdminErrorMsg("Username is empty");
				return;
			}
			if (StringUtils.isEmptyOrWhitespaceOnly(this.txtAdminPassword.getText())) {
				showAdminErrorMsg("Password is empty");
				return;
			}

			String sessionId = adminLogin.authorize(this.txtAdminUsername.getText(), this.txtAdminPassword.getText());

			if (sessionId != null) {
				AdminManager adminManager = new AdminManager();
				AnchorPane root = new AnchorPane();
				Scene scene = new Scene(root, BaseController.ADMIN_SCREEN_WIDTH, BaseController.ADMIN_SCREEN_WIDTH);
				adminManager.appInit(scene);
				adminManager.authenticated(sessionId, adminLogin.getAdmin());
			} else {
				showAdminErrorMsg("Username / Password is not correct");
			}
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			showAdminErrorMsg("Unknown Error.");
		}
	}

	private boolean validateUser() throws Exception {
		boolean isAuthenticated = false;
		int customerId = customerOperation.validateCustomer(this.txtUsername.getText().trim(),
				this.txtPassword.getText().trim());
		isAuthenticated = (0 != customerId);
		if (isAuthenticated) {
			BaseController.authenticatedCustomer = customerOperation.getCustomer(customerId);
		}
		return isAuthenticated;
	}

	private void showErrorMsg(String msg) {
		this.lblErrorMsg.setText(msg);
		this.errorPane.setVisible(true);
	}

	private void showAdminErrorMsg(String msg) {
		this.lblAdminErrorMsg.setText(msg);
		this.adminErrorPane.setVisible(true);
	}

	@Override
	public void init() {
		super.init();
	}

}