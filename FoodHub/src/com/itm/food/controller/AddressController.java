package com.itm.food.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.itm.food.dao.Address;
import com.itm.food.dao.operation.CustomerOperations;
import com.itm.food.util.UniqueKeyGen;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

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

	@FXML
	private JFXListView<Address> addresslistview;

	@FXML
	private ScrollPane scrollpane;

	@FXML
	private AnchorPane addressanchor;

	String pageAddrId = null;

	ObservableList<Address> obAddress = null;
	ArrayList<Address> addressList = null;

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
		handleAddress();

	}

	@Override
	void init() {
		super.init();
		listAddressesOfCustomers(); // Retrieving the list of addresses of the customer
		scrollpane.setVisible(false);
		renderAddressList(); // rendering the addresses in a scroll pane for better viewability

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

	/**
	 * storing the retrieved addresses in the List of address dao
	 */
	public void listAddressesOfCustomers() {

		try {

			BaseController.authenticatedCustomer
					.setAddresses(getAllAddresses(BaseController.authenticatedCustomer.getCustomerID()));

		} catch (Exception e) {
			log.error(e.getMessage());
		}

	}

	/**
	 * getting all addresses from the address table for the given customer id
	 * 
	 * @param pagesCustId
	 * @return
	 * @throws Exception
	 */
	public List<Address> getAllAddresses(String pagesCustId) throws Exception {

		Address getAddress = new Address();
		getAddress.setCustId(pagesCustId);
		CustomerOperations getCustAddresses = new CustomerOperations();

		return getCustAddresses.getCustomerAddress(getAddress);

	}

	/**
	 * setting up the outer anchor pane in scroll pane
	 */

	public void renderAddressList() {
		addressanchor.getChildren().removeAll(addressanchor.getChildren());
		addressanchor.setPrefHeight(200.0);
		scrollpane.setVisible(true);
		for (int i = 0; i < BaseController.authenticatedCustomer.getAddresses().size(); i++) {
			renderEachAddress(i);
		}

	}

	/**
	 * formatting and displaying every address in separate anchor panes
	 * 
	 * @param addressIndex
	 */

	public void renderEachAddress(int addressIndex) {

		double requiredAPaneHeight = addressIndex * 200.0;
		double currentAPaneHeight = addressanchor.getPrefHeight();
		if (requiredAPaneHeight <= currentAPaneHeight) {
			addressanchor.setPrefHeight(currentAPaneHeight + 200.0);
		}

		// Add a inner pane container

		// design the pane for holding every address
		AnchorPane individualPane = new AnchorPane();
		individualPane.setLayoutY(addressIndex * 200); // TODO INCREMENT THIS 210.0
		individualPane.setPrefHeight(200.0);
		individualPane.setPrefWidth(624.0);
		individualPane.setStyle("-fx-border-color: teal;");

		// add address information to the inner individual panes

		// adding customer name first
		Label lblCustName = new Label();
		lblCustName.setLayoutX(200.0);
		lblCustName.setLayoutY(20.0);
		lblCustName.prefHeight(30.0);
		lblCustName.prefWidth(600.0);
		lblCustName.setText(BaseController.authenticatedCustomer.getAddresses().get(addressIndex).getFname() + " "
				+ BaseController.authenticatedCustomer.getAddresses().get(addressIndex).getLname());
		lblCustName.setWrapText(true);
		lblCustName.setFont(new Font(24.0));
		lblCustName.setTextFill(Color.CRIMSON);
		AnchorPane.setLeftAnchor(lblCustName, 10.0);
		AnchorPane.setTopAnchor(lblCustName, 20.0);
		individualPane.getChildren().add(lblCustName);

		// adding address line 1
		Label lbladdress1 = new Label();
		lbladdress1.setLayoutX(200.0);
		lbladdress1.setLayoutY(20.0);
		lbladdress1.prefHeight(30.0);
		lbladdress1.prefWidth(600.0);
		lbladdress1.setText(BaseController.authenticatedCustomer.getAddresses().get(addressIndex).getAddr1());
		lbladdress1.setWrapText(true);
		lbladdress1.setFont(new Font(15.0));
		AnchorPane.setLeftAnchor(lbladdress1, 20.0);
		AnchorPane.setTopAnchor(lbladdress1, 70.0);
		AnchorPane.setRightAnchor(lbladdress1, 400.0);
		individualPane.getChildren().add(lbladdress1);

		// adding address line 2
		Label lbladdress2 = new Label();
		lbladdress2.setLayoutX(200.0);
		lbladdress2.setLayoutY(20.0);
		lbladdress2.prefHeight(30.0);
		lbladdress2.prefWidth(600.0);
		lbladdress2.setText(BaseController.authenticatedCustomer.getAddresses().get(addressIndex).getAddr2());
		lbladdress2.setWrapText(true);
		lbladdress2.setFont(new Font(15.0));
		AnchorPane.setLeftAnchor(lbladdress2, 20.0);
		AnchorPane.setTopAnchor(lbladdress2, 90.0);
		AnchorPane.setRightAnchor(lbladdress2, 400.0);
		individualPane.getChildren().add(lbladdress2);

		// adding city and zip
		Label lblcity = new Label();
		lblcity.setLayoutX(200.0);
		lblcity.setLayoutY(20.0);
		lblcity.prefHeight(30.0);
		lblcity.prefWidth(600.0);
		lblcity.setText(BaseController.authenticatedCustomer.getAddresses().get(addressIndex).getCity() + ",IL "
				+ BaseController.authenticatedCustomer.getAddresses().get(addressIndex).getPincode());
		lblcity.setWrapText(true);
		lblcity.setFont(new Font(15.0));
		AnchorPane.setLeftAnchor(lblcity, 20.0);
		AnchorPane.setTopAnchor(lblcity, 110.0);
		AnchorPane.setRightAnchor(lblcity, 400.0);
		individualPane.getChildren().add(lblcity);

		// adding address line 2
		Label lblcontact = new Label();
		lblcontact.setLayoutX(200.0);
		lblcontact.setLayoutY(20.0);
		lblcontact.prefHeight(30.0);
		lblcontact.prefWidth(600.0);
		lblcontact.setText(
				"Phone: " + BaseController.authenticatedCustomer.getAddresses().get(addressIndex).getAddrphoneNo());
		lblcontact.setWrapText(true);
		lblcontact.setFont(new Font(15.0));
		AnchorPane.setLeftAnchor(lblcontact, 20.0);
		AnchorPane.setTopAnchor(lblcontact, 130.0);
		AnchorPane.setRightAnchor(lblcontact, 400.0);
		individualPane.getChildren().add(lblcontact);

		addressanchor.getChildren().add(individualPane);

	}
}
