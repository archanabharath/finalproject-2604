package com.itm.food.controller;

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
	    
	    @FXML
	    void handleProfileUpdate(ActionEvent event) {
	    	if(!isUpdateDataValid()) {
	    		return;
	    	}
	    	updateUserProfile(BaseController.authenticatedCustomer.getCustomerID());
	    	
	    	setSuccessAlert();
	    	resetInputFields();

	    }
	@FXML
	void handleMouseUpdateProfile(MouseEvent event) {
		handleUpdateProfile();
	}

	void handleUpdateProfile() {
		log.debug("Updating profile");
	}

	@Override
	void init() {
		super.init();
	}

	public void updateUserProfile(String transferCustid) {
		Customer updateCustomer = new Customer();
		updateCustomer.setCustomerID(transferCustid);
		updateCustomer.setFirstName(pFirstName.getText());
		updateCustomer.setLastName(pLastName.getText());
		updateCustomer.setUsername(pUserName.getText());
		updateCustomer.setPassword(pPassword.getText());
		CustomerOperations addUpdates = new CustomerOperations();
		addUpdates.updateUserDetails(updateCustomer);
		
	}
	public boolean isUpdateDataValid() {
		if (pPassword.getText().compareTo(pRePassword.getText()) != 0) {
			lblErrorMsg.setText("Passwords does not match");
			this.errorPane.setVisible(true);
			return false;

		}
		this.errorPane.setVisible(false);
		return true;
		
	}
	
	public void setSuccessAlert() {
		Alert addressAddedMsg = new Alert(Alert.AlertType.INFORMATION);
		addressAddedMsg.setTitle("Customer details update");
		addressAddedMsg.setContentText("You have successfully updated your details");
		addressAddedMsg.setHeaderText(null);
		addressAddedMsg.showAndWait();
	}
	
	private void resetInputFields() {
		pFirstName.setText(null);
		pLastName.setText(null);
		pUserName.setText(null);
		pPassword.setText(null);
		pRePassword.setText(null);
		pDOB.setValue(null);
	}
	
}
