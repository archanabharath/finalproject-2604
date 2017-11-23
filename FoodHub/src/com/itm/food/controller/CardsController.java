package com.itm.food.controller;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.itm.food.dao.Payment;
import com.itm.food.dao.operation.PaymentOperations;
import com.itm.food.util.UniqueKeyGen;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class CardsController extends BaseController {

	private static final Logger log = Logger.getLogger(CardsController.class);

	@FXML
	private JFXTextField nameOnCard;

	@FXML
	private JFXTextField cardNo;

	@FXML
	private JFXButton addCardBtn;

	@FXML
	private ChoiceBox<String> cardtypepicker;

	@FXML
	private ChoiceBox<String> monthpicker;

	@FXML
	private ChoiceBox<String> yearpicker;

	@FXML
	private Label lblErrorMsg;

	@FXML
	private AnchorPane errorPane;

	ObservableList<String> months;
	ObservableList<String> years;
	ObservableList<String> typesOfCard;
	String pageCardId = null;

	/*
	 * This the method which handles the overall functioning of adding new cards of
	 * customers
	 */
	@FXML
	void handleAddNewCard(ActionEvent event) {

		// validate user inputs first
		if (!isCardInputValid()) {
			return;
		}

		// Insert the card details to the payment table
		cardInsertToDB(BaseController.authenticatedCustomer.getCustomerID());

		// alert the user that the card is added
		cardAddedAlert();

		// Reset all input fields
		resetInputFields();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.itm.food.controller.BaseController#init()
	 */
	@Override
	void init() {
		super.init();
		setPickerData();

	}

	/*
	 * Fetch the input details from screen and add the card to the payment table
	 */
	public void cardInsertToDB(String transferCustId) {
		log.info("Addcardcontroller started");
		Payment newCard = new Payment();
		PaymentOperations addNewCard = new PaymentOperations();

		newCard.setCardid(UniqueKeyGen.generateUUID());
		newCard.setCardNo(Long.parseLong(cardNo.getText()));
		newCard.setNameOnCard(nameOnCard.getText());
		newCard.setCustomerid(transferCustId);
		newCard.setCardType(cardtypepicker.getValue());
		newCard.setCardExpDate(monthpicker.getValue() + "/" + yearpicker.getValue());
		pageCardId = addNewCard.addPaymentInfo(newCard);
		log.info("cardcontroller returned");

	}

	/*
	 * set the dropdown(choicebox) values for month, card and year here in this
	 * method
	 */
	public void setPickerData() {
		// initialize observable arraylists for choice boxes
		months = FXCollections.observableArrayList("--month--", "01", "02", "03", "04", "05", "06", "07", "08", "09",
				"10", "11", "12");
		years = FXCollections.observableArrayList("--year--", "2017", "2018", "2019", "2020", "2022", "2023", "2024",
				"2025", "2026", "2027", "2028", "2029", "2030");
		typesOfCard = FXCollections.observableArrayList("--card-type--", "credit", "debit");

		// initialize months choicebox for dropdown
		monthpicker.setItems(months);
		monthpicker.setValue("--month--");
		monthpicker.setStyle("-fx-font: normal bold 14px");

		// initialize years choicebox for dropdown
		yearpicker.setItems(years);
		yearpicker.setValue("--year--");
		yearpicker.setStyle("-fx-font: normal bold 14px");

		// set card type picker
		cardtypepicker.setItems(typesOfCard);
		cardtypepicker.setValue("--card-type--");

		cardtypepicker.setStyle("-fx-font: normal bold 14px");
	}

	/*
	 * validate the user input details and throw error messages on screen
	 * accordingly
	 */
	public boolean isCardInputValid() {

		if (StringUtils.isEmpty(nameOnCard.getText())) {
			lblErrorMsg.setText("Please enter the name on card");
			this.errorPane.setVisible(true);
			return false;
		}
		if (StringUtils.isEmpty(cardNo.getText())) {
			lblErrorMsg.setText("Please enter the card number");
			this.errorPane.setVisible(true);
			return false;
		}
		if (cardNo.getLength() != 16) {
			lblErrorMsg.setText("Card number must be a 16 digit number");
			this.errorPane.setVisible(true);
			return false;
		}

		if ((monthpicker.getValue().compareTo("--month--")) == 0) {
			lblErrorMsg.setText("Please pick card expiry month");
			this.errorPane.setVisible(true);
			return false;
		}
		if ((yearpicker.getValue().compareTo("--year--")) == 0) {
			lblErrorMsg.setText("Please pick card expiry year");
			this.errorPane.setVisible(true);
			return false;
		}
		if ((cardtypepicker.getValue().compareTo("--card-type--")) == 0) {
			lblErrorMsg.setText("Please pick your card type");
			this.errorPane.setVisible(true);
			return false;
		}
		this.errorPane.setVisible(false);
		return true;

	}

	/*
	 * Reset the input fields once the card is added to the payment table
	 */
	public void resetInputFields() {

		nameOnCard.setText(null);
		cardNo.setText(null);
		monthpicker.setValue("--month--");
		yearpicker.setValue("--year--");
		cardtypepicker.setValue("--card-type--");
	}

	public void cardAddedAlert() {
		Alert cardAddedMsg = new Alert(Alert.AlertType.INFORMATION);

		cardAddedMsg.setTitle("Card Addition");
		cardAddedMsg.setContentText("You have successfully added your card");
		cardAddedMsg.setHeaderText(null);
		cardAddedMsg.showAndWait();
	}

}