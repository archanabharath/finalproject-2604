package com.itm.food.controller;

import java.sql.SQLException;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.itm.food.dao.Address;
import com.itm.food.dao.Customer;
import com.itm.food.dao.operation.CustomerOperations;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class SignupController extends BaseController {

	private static final Logger log = Logger.getLogger(SignupController.class);

	@FXML
	private JFXTextField txtFirstname;

	@FXML
	private JFXTextField txtLastname;

	@FXML
	private JFXTextField txtEmail;

	@FXML
	private JFXTextField txtUsername;

	@FXML
	private JFXPasswordField txtPassword;

	@FXML
	private JFXPasswordField txtReEnterPassword;

	@FXML
	private JFXTextField txtAddress1;

	@FXML
	private JFXTextField txtAddress2;

	@FXML
	private JFXTextField txtCity;

	@FXML
	private JFXTextField txtZip;

	@FXML
	private JFXTextField txtMobile;

	@FXML
	private JFXDatePicker txtDob;

	@FXML
	private Label lblErrorMsg;

	@FXML
	private AnchorPane errorPane;

	@FXML
	private JFXButton btnSignup;
	@FXML
	private AnchorPane usernameep;
	@FXML
	private Label lblErrorMsgun;

	@FXML
	void handleKeySignup(KeyEvent event) {
		this.handleSignup();
	}

	@FXML
	void handleMouseSignup(MouseEvent event) {
		this.handleSignup();
	}

	Customer newcustomer = new Customer();
	Address newaddress = new Address();
	CustomerOperations customerOperations = new CustomerOperations();
	String customerId = "";

	void handleSignup() {
		try {
			// validate the user information before registering
			if (!validateCustomerInformation()) {
				return;
			}

			if (!usernamevalidate()) {
				return;
			}
			register();

			Alert a1 = new Alert(Alert.AlertType.INFORMATION);

			a1.setTitle("Registration confirmation");
			a1.setContentText("You have successfully registered!! Please login to continue.");
			a1.setHeaderText(null);
			a1.showAndWait();

			AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("/com/itm/food/views/Login.fxml"));
			Scene scene = new Scene(root, 900, 500);
			scene.getStylesheets().add(getClass().getResource("/com/itm/food/application.css").toExternalForm());
			ParentController.mainStage.setScene(scene);
			ParentController.mainStage.setResizable(false);
			ParentController.mainStage.centerOnScreen();
			ParentController.mainStage.show();

		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}

	/*
	 * assign the customer data retrieved from the user input to the respective
	 * variables using the setters
	 */
	private void setCustomerData() {
		// newcustomer.setCustomerID(UniqueKeyGen.generateUUID());
		newcustomer.setFirstName(this.txtFirstname.getText());
		newcustomer.setLastName(this.txtLastname.getText());
		newcustomer.setDOB(this.txtDob.getValue().toString());
		newcustomer.setUsername(this.txtUsername.getText());
		newcustomer.setPassword(this.txtPassword.getText());
		newcustomer.setEmail(this.txtEmail.getText());
		newcustomer.setPhoneNo(this.txtMobile.getText());
	}

	/*
	 * assign the customer address details retrieved from the user input to the
	 * respective variables using the setters
	 */
	private void setAddressData() {
		// newaddress.setAddrId(UniqueKeyGen.generateUUID());
		newaddress.setFname(this.txtFirstname.getText());
		newaddress.setLname(this.txtLastname.getText());
		newaddress.setAddr1(this.txtAddress1.getText());
		newaddress.setAddr2(this.txtAddress2.getText());
		newaddress.setCity(this.txtCity.getText());
		newaddress.setPincode(Integer.parseInt(this.txtZip.getText()));
		newaddress.setAddrphoneNo(this.txtMobile.getText());
	}

	private void register() {
		try {
			log.debug("Registration start for the member");

			// Set and Save Customer
			setCustomerData();
			int custId = customerOperations.addUserDetails(newcustomer);

			// Set and save Customer address
			setAddressData();

			// insert the customer details into customer and address tables

			newaddress.setCustId(custId);
			customerOperations.addAddress(newaddress);

			log.info("Successfully registered the member: " + newcustomer.getCustomerID());

		} catch (Exception ex) {
			log.error(ex.getMessage());

		}
	}

	@Override
	void init() {

	}

	private boolean validateCustomerInformation() {
		if (StringUtils.isEmpty(txtFirstname.getText())) {
			lblErrorMsg.setText("Please enter first name");
			this.errorPane.setVisible(true);
			return false;
		}
		if (StringUtils.isEmpty(txtLastname.getText())) {
			lblErrorMsg.setText("Please enter last name");
			this.errorPane.setVisible(true);
			return false;
		}
		if (StringUtils.isEmpty(txtEmail.getText())) {
			lblErrorMsg.setText("Please enter email");
			this.errorPane.setVisible(true);
			return false;
		}
		if (StringUtils.isEmpty(txtUsername.getText())) {
			lblErrorMsg.setText("Please enter your username");
			this.errorPane.setVisible(true);
			return false;
		}
		if (StringUtils.isEmpty(txtPassword.getText())) {
			lblErrorMsg.setText("Please enter your password");
			this.errorPane.setVisible(true);
			return false;
		}
		if (StringUtils.isEmpty(txtReEnterPassword.getText())) {
			lblErrorMsg.setText("Please enter your password again");
			this.errorPane.setVisible(true);
			return false;
		}

		if (txtPassword.getText().compareTo(txtReEnterPassword.getText()) != 0) {
			lblErrorMsg.setText("Passwords does not match");
			this.errorPane.setVisible(true);
			return false;

		}

		if (StringUtils.isEmpty(txtAddress1.getText())) {
			lblErrorMsg.setText("Please enter your address line1");
			this.errorPane.setVisible(true);
			return false;
		}

		if (StringUtils.isEmpty(txtAddress2.getText())) {
			lblErrorMsg.setText("Please enter your address line2");
			this.errorPane.setVisible(true);
			return false;
		}

		if (StringUtils.isEmpty(txtCity.getText())) {
			lblErrorMsg.setText("Please enter your city");
			this.errorPane.setVisible(true);
			return false;
		}

		if (StringUtils.isEmpty(txtZip.getText())) {
			lblErrorMsg.setText("Please enter your zip code");
			this.errorPane.setVisible(true);
			return false;
		}

		if (StringUtils.isEmpty(txtMobile.getText())) {
			lblErrorMsg.setText("Please enter your mobile number");
			this.errorPane.setVisible(true);
			return false;
		}

		if (txtDob.getValue() == null) {
			lblErrorMsg.setText("Please enter your DOB");
			this.errorPane.setVisible(true);
			return false;
		}

		this.errorPane.setVisible(false);
		return true;

	}

	private boolean usernamevalidate() throws ClassNotFoundException, SQLException {

		if (txtUsername.getText() != null) {
			if (customerOperations.isUserNamePresent(txtUsername.getText())) {
				lblErrorMsgun.setText("Username already exists");
				this.usernameep.setVisible(true);
				return false;
			}

		}
		this.usernameep.setVisible(false);
		return true;

	}
}
