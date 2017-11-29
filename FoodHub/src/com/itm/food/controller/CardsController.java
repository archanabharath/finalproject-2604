package com.itm.food.controller;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.itm.food.dao.Payment;
import com.itm.food.util.CardUtil;
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
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

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

	@FXML
	private ScrollPane cardscrollpane;

	@FXML
	private AnchorPane cardanchorpane;

	ObservableList<String> months;
	ObservableList<String> years;
	ObservableList<String> typesOfCard;
	int pageCardId = 0;
	ObservableList<Payment> cardsList;

	/**
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

		// Refresh the screen
		handleCards();

	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.itm.food.controller.BaseController#init()
	 */
	@Override
	void init() {
		super.init();
		setPickerData();
		// list the cards already present for the customer
		listAllCards();
		cardscrollpane.setVisible(false);
		// rendering the cards view
		renderCardsList();

	}

	/**
	 * Fetch the input details from screen and add the card to the payment table
	 */
	public void cardInsertToDB(int i) {
		log.info("Addcardcontroller started");
		Payment newCard = new Payment();

		//newCard.setCardid(UniqueKeyGen.generateUUID());
		newCard.setCardNo(Long.parseLong(cardNo.getText()));
		newCard.setNameOnCard(nameOnCard.getText());
		newCard.setCustomerid(i);
		newCard.setCardType(cardtypepicker.getValue());
		newCard.setCardExpDate(monthpicker.getValue() + "/" + yearpicker.getValue());
		pageCardId = paymentOperation.addPaymentInfo(newCard);
		log.info("cardcontroller returned");

	}

	/**
	 * set the dropdown(choicebox) values for month, card and year here in this
	 * method
	 */
	public void setPickerData() {
		// initialize observable arraylists for choice boxes
		months = FXCollections.observableArrayList("--Month--", "01", "02", "03", "04", "05", "06", "07", "08", "09",
				"10", "11", "12");
		years = FXCollections.observableArrayList("--Year--", "2017", "2018", "2019", "2020", "2022", "2023", "2024",
				"2025", "2026", "2027", "2028", "2029", "2030");
		typesOfCard = FXCollections.observableArrayList("--Card-type--", "Credit", "Debit");

		// initialize months choicebox for dropdown
		monthpicker.setItems(months);
		monthpicker.setValue("--Month--");
		monthpicker.setStyle("-fx-font: normal bold 14px");

		// initialize years choicebox for dropdown
		yearpicker.setItems(years);
		yearpicker.setValue("--Year--");
		yearpicker.setStyle("-fx-font: normal bold 14px");

		// set card type picker
		cardtypepicker.setItems(typesOfCard);
		cardtypepicker.setValue("--Card-type--");
		cardtypepicker.setStyle("-fx-font: normal bold 14px");
	}

	/**
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

		if ((monthpicker.getValue().compareTo("--Month--")) == 0) {
			lblErrorMsg.setText("Please pick card expiry month");
			this.errorPane.setVisible(true);
			return false;
		}
		if ((yearpicker.getValue().compareTo("--Year--")) == 0) {
			lblErrorMsg.setText("Please pick card expiry year");
			this.errorPane.setVisible(true);
			return false;
		}
		if ((cardtypepicker.getValue().compareTo("--Card-type--")) == 0) {
			lblErrorMsg.setText("Please pick your card type");
			this.errorPane.setVisible(true);
			return false;
		}
		this.errorPane.setVisible(false);
		return true;

	}

	/**
	 * Reset the input fields once the card is added to the payment table
	 */
	public void resetInputFields() {

		nameOnCard.setText(null);
		cardNo.setText(null);
		monthpicker.setValue("--month--");
		yearpicker.setValue("--year--");
		cardtypepicker.setValue("--card-type--");
	}

	/**
	 * Alert the customer on successfully adding the new card
	 */
	public void cardAddedAlert() {
		Alert cardAddedMsg = new Alert(Alert.AlertType.INFORMATION);

		cardAddedMsg.setTitle("Card Addition");
		cardAddedMsg.setContentText("You have successfully added your card");
		cardAddedMsg.setHeaderText(null);
		cardAddedMsg.showAndWait();
	}

	/**
	 * Get the list of cards from payment table and store it in the cardsList
	 */
	public void listAllCards() {
		try {
			BaseController.authenticatedCustomer
					.setPayments(getAllCards(BaseController.authenticatedCustomer.getCustomerID()));
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}

	/**
	 * place the call to access the payments table to retrieve the list of cards
	 * 
	 * @param i
	 * @return
	 * @throws Exception
	 */
	public List<Payment> getAllCards(int i) throws Exception {
		return paymentOperation.getCards(i);
	}

	/**
	 * render the view of the cards list on screen
	 */
	public void renderCardsList() {
		cardanchorpane.getChildren().removeAll(cardanchorpane.getChildren());
		cardanchorpane.setPrefHeight(150.0);
		cardscrollpane.setPrefWidth(600.0);
		cardscrollpane.setVisible(true);
		for (int i = 0; i < BaseController.authenticatedCustomer.getPayments().size(); i++) {
			renderEachCard(i);
		}

	}

	/**
	 * render the view of individual cards on screen
	 * 
	 * @param cardIndex
	 */
	private void renderEachCard(int cardIndex) {

		double requiredAPaneHeight = cardIndex * 150.0;
		double currentAPaneHeight = cardanchorpane.getPrefHeight();
		if (requiredAPaneHeight <= currentAPaneHeight) {
			cardanchorpane.setPrefHeight(currentAPaneHeight + 150.0);
		}

		// Add a inner pane container

		// design the pane for holding every card
		AnchorPane individualCardPane = new AnchorPane();
		individualCardPane.setLayoutY(cardIndex * 150); // TODO INCREMENT THIS
														// 210.0
		individualCardPane.setPrefHeight(150.0);
		individualCardPane.setPrefWidth(600.0);
		individualCardPane.setStyle("-fx-border-color: teal;");

		// add card information to the inner individual panes

		// adding name on card first
		Label lblNameOnCard = new Label();
		lblNameOnCard.setLayoutX(200.0);
		lblNameOnCard.setLayoutY(20.0);
		lblNameOnCard.prefHeight(30.0);
		lblNameOnCard.prefWidth(600.0);
		lblNameOnCard.setText(BaseController.authenticatedCustomer.getPayments().get(cardIndex).getNameOnCard());
		lblNameOnCard.setTextFill(Color.CRIMSON);
		lblNameOnCard.setWrapText(true);
		lblNameOnCard.setFont(new Font(24.0));
		AnchorPane.setLeftAnchor(lblNameOnCard, 10.0);
		AnchorPane.setTopAnchor(lblNameOnCard, 20.0);
		individualCardPane.getChildren().add(lblNameOnCard);

		// adding card number for display
		Label lblCardNo = new Label();
		lblCardNo.setLayoutX(200.0);
		lblCardNo.setLayoutY(20.0);
		lblCardNo.prefHeight(30.0);
		lblCardNo.prefWidth(600.0);
		lblCardNo.setText("Card No: " + CardUtil.maskCardNo(
				String.valueOf(BaseController.authenticatedCustomer.getPayments().get(cardIndex).getCardNo())));

		lblCardNo.setWrapText(true);
		lblCardNo.setFont(new Font(15.0));
		AnchorPane.setLeftAnchor(lblCardNo, 20.0);
		AnchorPane.setTopAnchor(lblCardNo, 60.0);
		AnchorPane.setRightAnchor(lblCardNo, 300.0);
		individualCardPane.getChildren().add(lblCardNo);

		// adding card type for display
		Label lblCardType = new Label();
		lblCardType.setLayoutX(200.0);
		lblCardType.setLayoutY(20.0);
		lblCardType.prefHeight(30.0);
		lblCardType.prefWidth(600.0);
		lblCardType.setText(
				"Card Type: " + BaseController.authenticatedCustomer.getPayments().get(cardIndex).getCardType());
		lblCardType.setWrapText(true);
		lblCardType.setFont(new Font(15.0));
		AnchorPane.setLeftAnchor(lblCardType, 20.0);
		AnchorPane.setTopAnchor(lblCardType, 90.0);
		AnchorPane.setRightAnchor(lblCardType, 300.0);
		individualCardPane.getChildren().add(lblCardType);

		// adding card expiry for display
		Label lblCardExpiry = new Label();
		lblCardExpiry.setLayoutX(200.0);
		lblCardExpiry.setLayoutY(20.0);
		lblCardExpiry.prefHeight(30.0);
		lblCardExpiry.prefWidth(600.0);
		lblCardExpiry.setText(
				"Card Expiry: " + BaseController.authenticatedCustomer.getPayments().get(cardIndex).getCardExpDate());
		lblCardExpiry.setWrapText(true);
		lblCardExpiry.setFont(new Font(15.0));
		AnchorPane.setLeftAnchor(lblCardExpiry, 20.0);
		AnchorPane.setTopAnchor(lblCardExpiry, 120.0);
		AnchorPane.setRightAnchor(lblCardExpiry, 300.0);
		individualCardPane.getChildren().add(lblCardExpiry);

		cardanchorpane.getChildren().add(individualCardPane);

	}
}