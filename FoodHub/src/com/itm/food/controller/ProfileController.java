package com.itm.food.controller;

import java.time.LocalDate;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.itm.food.dao.Customer;
import com.itm.food.dao.operation.CustomerOperations;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class ProfileController extends BaseController {

	private static final Logger log = Logger.getLogger(ProfileController.class);

	
	@FXML
	private Label txtFullname;

	@FXML
	private JFXTextField pFirstName;

	@FXML
	private JFXTextField pLastName;

	@FXML
	private JFXTextField pEmail;

	@FXML
	private JFXTextField pUserName;

	@FXML
	private JFXPasswordField pPassword;

	@FXML
	private JFXPasswordField pRePassword;

	@FXML
	private JFXDatePicker pDOB;

	@FXML
	private JFXButton pUpdateBtn;

	@FXML
	private AnchorPane errorPane;

	@FXML
	private Label lblErrorMsg;

	Customer updateCustomer = new Customer();
	CustomerOperations addUpdates = new CustomerOperations();

	@FXML
	void handleProfileUpdate(ActionEvent event) {
		
		log.info("entering profile controller");
		try {
			if (!isUpdateDataValid()) {
				return;
			}
			updateUserProfile(BaseController.authenticatedCustomer.getCustomerID());

			setSuccessAlert();
			loadUserProfile(BaseController.authenticatedCustomer.getCustomerID());// load the updated data to the
																					// profile window

			BaseController.authenticatedCustomer = customerOperation
					.getCustomer(BaseController.authenticatedCustomer.getCustomerID()); // load the updated user
																						// information to the base page
			handleProfile();// to update the user name on top right corner of profile page
		} catch (Exception e) {
			log.error(e.getMessage());

		}

	}

	/*
	 * invoking the profile page from base controller on clicking profile section
	 */
	@FXML
	void handleMouseUpdateProfile(MouseEvent event) {
		handleUpdateProfile();
	}

	void handleUpdateProfile() {
		log.debug("Updating profile");
	}

	@Override
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.itm.food.controller.BaseController#init()
	 * 
	 * populate the user details on profile page
	 */
	void init() {
		super.init();
		loadUserProfile(BaseController.authenticatedCustomer.getCustomerID());
	}

	/*
	 * update the new details of the customer in the table
	 */
	public void updateUserProfile(int i) {

		updateCustomer.setCustomerID(i);
		updateCustomer.setFirstName(pFirstName.getText());
		updateCustomer.setLastName(pLastName.getText());
		updateCustomer.setEmail(pEmail.getText());
		updateCustomer.setUsername(pUserName.getText());
		updateCustomer.setPassword(pPassword.getText());
		updateCustomer.setDOB(pDOB.getValue().toString());
		try {
			addUpdates.updateUserDetails(updateCustomer);
		} catch (Exception e) {
			log.error(e.getMessage());
		}

	}

	/*
	 * validate the new password that the user sets for his account
	 */
	public boolean isUpdateDataValid() {

		if (StringUtils.isNotEmpty(pRePassword.getText())) {

			if (pPassword.getText().compareTo(pRePassword.getText()) != 0) {
				lblErrorMsg.setText("Passwords does not match");
				this.errorPane.setVisible(true);
				return false;

			}
		}
		this.errorPane.setVisible(false);
		return true;

	}

	/*
	 * alert the user that the user details are updated successfully
	 */
	public void setSuccessAlert() {
		Alert addressAddedMsg = new Alert(Alert.AlertType.INFORMATION);
		addressAddedMsg.setTitle("Customer details update");
		addressAddedMsg.setContentText("You have successfully updated your details");
		addressAddedMsg.setHeaderText(null);
		addressAddedMsg.showAndWait();
	}

	/*
	 * Load the updated user profile data to the profile page
	 */
	public void loadUserProfile(int i) {
		updateCustomer.setCustomerID(i);
		try {
			updateCustomer = addUpdates.getCustomerProfile(i);
			pFirstName.setText(updateCustomer.getFirstName());
			pLastName.setText(updateCustomer.getLastName());
			pEmail.setText(updateCustomer.getEmail());
			pUserName.setText(updateCustomer.getUsername());
			pPassword.setText(updateCustomer.getPassword());
			pDOB.setValue(LocalDate.parse(updateCustomer.getDOB()));

		} catch (Exception e) {
			log.error(e.getMessage());
		}

	}
}
