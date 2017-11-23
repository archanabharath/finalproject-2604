package com.itm.food.controller;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.itm.food.dao.Address;
import com.itm.food.dao.operation.CustomerOperations;
import com.itm.food.util.UniqueKeyGen;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class AddressController extends BaseController {

	private static final Logger log = Logger.getLogger(AddressController.class);

	@FXML
	private JFXTextField uFirstName;

	@FXML
	private JFXTextField uLastName;

	@FXML
	private JFXTextField uAddress1;

	@FXML
	private JFXTextField uAddress2;

	@FXML
	private JFXTextField uCity;

	@FXML
	private JFXTextField uZipCode;

	@FXML
	private JFXTextField uPhone;

	@FXML
	private JFXButton addAddressBtn;

	@FXML
	private AnchorPane errorPane;

	@FXML
	private Label lblErrorMsg;

	String pageAddrId = null;

	/*
	 * This is the method that controls the overall functioning of adding a new
	 * address to the customer table
	 */
	@FXML
	void handleAddNewAddress(ActionEvent event) {

		// validate the input data entered by the user
		if (!isAddressInputValid()) {
			return;
		}

		// insert the address into the address table
		addressInsertToDB(BaseController.authenticatedCustomer.getCustomerID());

		// set an alert message soon after the address is added to the address table
		setSuccessAlert();

		// Reset the input fields after adding the address
		resetInputFields();

	}

	@Override
	void init() {
		super.init();

	}

	/*
	 * place calls to the address table to insert the new address for the customer
	 */
	public void addressInsertToDB(String transferCustId) {

		CustomerOperations addNewAddress = new CustomerOperations();
		Address additionalAddress = new Address();
		additionalAddress.setFname(uFirstName.getText());
		additionalAddress.setLname(uLastName.getText());
		additionalAddress.setAddr1(uAddress1.getText());
		additionalAddress.setAddr2(uAddress2.getText());
		additionalAddress.setCity(uCity.getText());
		additionalAddress.setPincode(Integer.parseInt(uZipCode.getText()));
		additionalAddress.setAddrphoneNo(uPhone.getText());
		additionalAddress.setCustId(transferCustId);
		additionalAddress.setAddrId(UniqueKeyGen.generateUUID());
		try {
			pageAddrId = addNewAddress.addAddress(additionalAddress);
		} catch (Exception e) {
			log.error(e.getMessage());

		}

	}

	/*
	 * Validate every input data the user keys in for correction before inserting
	 * into table
	 */
	public boolean isAddressInputValid() {
		if (StringUtils.isEmpty(uFirstName.getText())) {
			lblErrorMsg.setText("Please enter the First Name");
			this.errorPane.setVisible(true);
			return false;
		}
		if (StringUtils.isEmpty(uLastName.getText())) {
			lblErrorMsg.setText("Please enter the Last Name");
			this.errorPane.setVisible(true);
			return false;
		}
		if (StringUtils.isEmpty(uAddress1.getText())) {
			lblErrorMsg.setText("Please enter Address Line1");
			this.errorPane.setVisible(true);
			return false;
		}
		if (StringUtils.isEmpty(uAddress2.getText())) {
			lblErrorMsg.setText("Please enter Address Line2");
			this.errorPane.setVisible(true);
			return false;
		}
		if (StringUtils.isEmpty(uCity.getText())) {
			lblErrorMsg.setText("Please enter the City");
			this.errorPane.setVisible(true);
			return false;
		}
		if (StringUtils.isEmpty(uZipCode.getText())) {
			lblErrorMsg.setText("Please enter your zip code");
			this.errorPane.setVisible(true);
			return false;
		}
		if (StringUtils.isEmpty(uPhone.getText())) {
			lblErrorMsg.setText("Please enter your Mobile Number");
			this.errorPane.setVisible(true);
			return false;
		}
		this.errorPane.setVisible(false);
		return true;

	}

	/*
	 * Throw a success alert to the customer once the address is added successfully
	 * to the table
	 */
	public void setSuccessAlert() {
		Alert addressAddedMsg = new Alert(Alert.AlertType.INFORMATION);
		addressAddedMsg.setTitle("Address Addition");
		addressAddedMsg.setContentText("You have successfully added your address");
		addressAddedMsg.setHeaderText(null);
		addressAddedMsg.showAndWait();
	}

	/*
	 * reset the input fields on the screen
	 */
	private void resetInputFields() {
		uFirstName.setText(null);
		uLastName.setText(null);
		uAddress1.setText(null);
		uAddress2.setText(null);
		uCity.setText(null);
		uZipCode.setText(null);
		uPhone.setText(null);

	}

}
