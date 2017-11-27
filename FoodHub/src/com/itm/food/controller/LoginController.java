<<<<<<< HEAD
package com.itm.food.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.mysql.jdbc.StringUtils;

import javafx.fxml.FXML;
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
		
		if(validateUser()) {
		this.launchScene("Home Screen", "/com/itm/food/views/Home.fxml", BaseController.MAIN_SCREEN_WIDTH,
				BaseController.MAIN_SCREEN_HEIGHT);
		} else {
			showErrorMsg("Username / Password is not correct");
		}
		} catch (Exception ex) {
			showErrorMsg("Unknown Error.");
		}
	}
	
	private boolean validateUser() throws Exception {
		boolean isAuthenticated = false;
		String customerId = customerOperation.validateCustomer(this.txtUsername.getText().trim(), this.txtPassword.getText().trim());
		isAuthenticated = (null != customerId);
		if(isAuthenticated) {
		BaseController.authenticatedCustomer = customerOperation.getCustomer(customerId);
		} 
		return isAuthenticated;
	}
	
	private void showErrorMsg(String msg) {
		this.lblErrorMsg.setText(msg);
		this.errorPane.setVisible(true);
	}

	@Override
	void init() {
		super.init();
	}

}
=======
package com.itm.food.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.mysql.jdbc.StringUtils;

import javafx.fxml.FXML;
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
		
		if(validateUser()) {
		this.launchScene("Home Screen", "/com/itm/food/views/Home.fxml", BaseController.MAIN_SCREEN_WIDTH,
				BaseController.MAIN_SCREEN_HEIGHT);
		} else {
			showErrorMsg("Username / Password is not correct");
		}
		} catch (Exception ex) {
			showErrorMsg("Unknown Error.");
		}
	}
	
	private boolean validateUser() throws Exception {
		boolean isAuthenticated = false;
		String customerId = customerOperation.validateCustomer(this.txtUsername.getText().trim(), this.txtPassword.getText().trim());
		isAuthenticated = (null != customerId);
		if(isAuthenticated) {
		BaseController.authenticatedCustomer = customerOperation.getCustomer(customerId);
		} 
		return isAuthenticated;
	}
	
	private void showErrorMsg(String msg) {
		this.lblErrorMsg.setText(msg);
		this.errorPane.setVisible(true);
	}

	@Override
	void init() {
		super.init();
	}

}
>>>>>>> refs/remotes/origin/feature1
