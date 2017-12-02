package com.itm.food.controller;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.itm.food.dao.Address;
import com.itm.food.dao.Basket;
import com.itm.food.dao.Item;
import com.itm.food.dao.Payment;
import com.itm.food.dao.Restaurant;
import com.itm.food.model.CouponDB;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXButton.ButtonType;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;

public class BasketController extends BaseController {

	private static final Logger log = Logger.getLogger(BasketController.class);

	@FXML
	private JFXButton btnPlaceOrder;

	@FXML
	private AnchorPane errorPane;

	@FXML
	private Label lblErrorMsg;

	@FXML
	private Label lblItemsTotal;

	@FXML
	private Label lblDeliveryCharge;

	@FXML
	private Label lblCouponsApplied;

	@FXML
	private Label lblTotalBeforeTax;

	@FXML
	private Label lblTaxApplied;

	@FXML
	private Label lblOrderTotal;

	@FXML
	private Hyperlink linkToAddAddress;

	@FXML
	private Hyperlink linkToAddCard;

	@FXML
	private ScrollPane scrollPaneRest;

	@FXML
	private AnchorPane anchorPaneItemList;

	@FXML
	private JFXToggleButton togglePickup;

	@FXML
	private ToggleGroup deliveryMode;

	@FXML
	private JFXToggleButton toggleDelivery;

	@FXML
	private ChoiceBox<Address> addressPicker;

	@FXML
	private ChoiceBox<Payment> paymentPicker;

	@FXML
	private AnchorPane orderErrorPane;

	@FXML
	private Label lblOrderErrorMsg;

	@FXML
	private JFXTextField couponTextBox;

	CouponDB couponDB = new CouponDB();

	public void init() {
		super.init();
		if (null != BaseController.foodBasket.getOrderItems() || BaseController.foodBasket.getOrderItems().isEmpty()) {
			this.errorPane.setVisible(false);
			this.orderErrorPane.setVisible(false);
			JFXToggleButton button = (JFXToggleButton) deliveryMode.getSelectedToggle();
			if (button.getText().equals("Pickup")) {
				addressPicker.setDisable(true);
				linkToAddAddress.setDisable(true);
			} else {
				addressPicker.setDisable(false);
				linkToAddAddress.setDisable(false);
			}
			getCouponValue();
			renderOrderSummary();
			renderBasketItems();
		}

	}

	@FXML
	void handleDelivery(ActionEvent event) {
		addressPicker.setDisable(false);
		BaseController.foodBasket.setDeliveryCharge(3.0);
		lblDeliveryCharge.setText("$" + BaseController.foodBasket.getDeliveryCharge());
		lblDeliveryCharge.setStyle("-fx-font-weight:bold;");
		linkToAddAddress.setDisable(false);
		renderOrderSummary();
	}

	@FXML
	void handlePickup(ActionEvent event) {
		addressPicker.setDisable(true);
		BaseController.foodBasket.setDeliveryCharge(0.0);
		lblDeliveryCharge.setText("$0.00");
		lblDeliveryCharge.setStyle("-fx-font-weight:bold;");
		linkToAddAddress.setDisable(true);
		renderOrderSummary();
	}

	@FXML
	void handlePlaceOrder(ActionEvent event) {
		placeOrder();
	}

	void renderOrderSummary() {
		try {

			BaseController.foodBasket.calculateOrderSummary();
			lblDeliveryCharge.setStyle("-fx-font-weight:bold;");
			lblItemsTotal.setText("$" + BaseController.foodBasket.getItemsTotal());
			lblItemsTotal.setStyle("-fx-font-weight:bold;");
			// lblDeliveryCharge.setText("$" +
			// BaseController.foodBasket.getDeliveryCharge());
			if (BaseController.foodBasket.getCouponsApplied() > 0) {
				lblCouponsApplied.setText("-$" + BaseController.foodBasket.getCouponsApplied());
			} else {
				lblCouponsApplied.setText("$" + BaseController.foodBasket.getCouponsApplied());
			}
			lblCouponsApplied.setStyle("-fx-font-weight:bold;");
			lblTotalBeforeTax.setText("$" + BaseController.foodBasket.getTotalBeforeTax());
			lblTotalBeforeTax.setStyle("-fx-font-weight:bold;");
			lblTaxApplied.setText("$" + BaseController.foodBasket.getTaxApplied());
			lblTaxApplied.setStyle("-fx-font-weight:bold;");
			lblOrderTotal.setText("$" + BaseController.foodBasket.getOrderTotal());
			lblOrderTotal.setStyle("-fx-font-weight:bold;");

			if (null == BaseController.authenticatedCustomer.getAddresses()
					|| BaseController.authenticatedCustomer.getAddresses().size() == 0) {
				BaseController.authenticatedCustomer.setAddresses(
						customerOperation.getCustomerAddress(BaseController.authenticatedCustomer.getCustomerID()));
			}
			linkToAddAddress.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					log.debug("Basket-controller-link to add new address");
					handleAddress();
					return;

				}
			});
			ObservableList<Address> obAddress = null;
			obAddress = FXCollections.observableArrayList(BaseController.authenticatedCustomer.getAddresses());
			addressPicker.setItems(obAddress);
			addressPicker.setValue(BaseController.authenticatedCustomer.getAddresses().get(0));

			if (null == BaseController.authenticatedCustomer.getPayments()
					|| BaseController.authenticatedCustomer.getPayments().size() == 0) {
				BaseController.authenticatedCustomer
						.setPayments(paymentOperation.getCards(BaseController.authenticatedCustomer.getCustomerID()));
			}

			linkToAddCard.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					log.debug("Basket-controller-link to add new card");
					handleCards();
					return;

				}
			});
			if (BaseController.authenticatedCustomer.getPayments().size() > 0) {
				ObservableList<Payment> obPayment = null;
				obPayment = FXCollections.observableArrayList(BaseController.authenticatedCustomer.getPayments());
				paymentPicker.setItems(obPayment);
				paymentPicker.setValue(BaseController.authenticatedCustomer.getPayments().get(0));
			}

		} catch (Exception ex) {
			lblOrderErrorMsg.setText("Unable to load order Summary.");
			orderErrorPane.setVisible(true);
			log.error(ex.getMessage(), ex);
		}
	}

	void renderBasketItems() {
		if (BaseController.foodBasket.getOrderItems().size() > 0) {
			// Clean the anchorPaneRestList before rendering the new search list
			anchorPaneItemList.getChildren().removeAll(anchorPaneItemList.getChildren());
			anchorPaneItemList.setPrefHeight(150.0);
			scrollPaneRest.setVisible(true);
			for (int i = 0; i < BaseController.foodBasket.getOrderItems().size(); i++) {
				renderItem(i);
			}
		} else {
			scrollPaneRest.setVisible(false);
			log.debug("No Items found.");
		}

	}

	void renderItem(int index) {
		try {
			Item item = customerOperation.getItem(BaseController.foodBasket.getOrderItems().get(index).getItemId());
			Restaurant restaurant = customerOperation
					.getRestaurant(BaseController.foodBasket.getOrderItems().get(index).getRestaurantId());
			double paneHeightNeeded = index + 1 * 150;
			double currentPaneHeight = anchorPaneItemList.getPrefHeight();
			if (paneHeightNeeded > currentPaneHeight) {
				anchorPaneItemList.setPrefHeight(currentPaneHeight + 150.0);
			}

			AnchorPane pane = new AnchorPane();
			pane.setLayoutY(index * 150); // TODO INCREMENT THIS 150
			pane.setPrefHeight(150.0);
			pane.setPrefWidth(740.0);
			pane.setStyle("-fx-border-color: lightgrey;");

			ImageView imageView = new ImageView();
			imageView.setFitHeight(150.0);
			imageView.setFitWidth(150.0);
			imageView.setLayoutX(45.0);
			imageView.setLayoutY(25.0);
			imageView.setPickOnBounds(true);
			imageView.setPreserveRatio(false);
			AnchorPane.setLeftAnchor(imageView, 0.0);
			AnchorPane.setTopAnchor(imageView, 0.0);

			/*
			 * String url =
			 * "file:\\" + new File("").getCanonicalFile().getParent().toString() +
			 * File.separatorChar + "FoodHub" + File.separatorChar + "src" +
			 * File.separatorChar + "com" + File.separatorChar + "itm" + File.separatorChar
			 * + "food" + File.separatorChar + "images" + File.separatorChar +
			 * "default-items.png";
			 */

			String url = "file:\\E:\\Projects\\GitHub\\finalproject-2604\\FoodHub\\src\\com\\itm\\food\\images\\default-items.png";
			imageView.setImage(new Image(url));

			pane.getChildren().add(imageView);

			Label lblItemName = new Label();
			lblItemName.setLayoutX(196.0);
			lblItemName.setLayoutY(17.0);
			lblItemName.prefHeight(20.0);
			lblItemName.prefWidth(300.0);
			lblItemName.setText(item.getItemName());
			lblItemName.setWrapText(true);
			lblItemName.setFont(new Font(24.0));
			lblItemName.setTextFill(Color.CRIMSON);
			AnchorPane.setLeftAnchor(lblItemName, 175.0);
			AnchorPane.setTopAnchor(lblItemName, 15.0);
			AnchorPane.setRightAnchor(lblItemName, 200.0);
			pane.getChildren().add(lblItemName);

			Label lblRestName = new Label();
			lblRestName.setLayoutX(196.0);
			lblRestName.setLayoutY(92.0);
			lblRestName.prefHeight(100.0);
			lblRestName.prefWidth(100.0);
			lblRestName.setText("Prepared by: " + restaurant.getRestaurantName() + ", " + restaurant.getCity());
			lblRestName.setWrapText(true);
			lblRestName.setFont(new Font(13.0));
			AnchorPane.setLeftAnchor(lblRestName, 175.0);
			AnchorPane.setBottomAnchor(lblRestName, 20.0);
			AnchorPane.setRightAnchor(lblItemName, 200.0);
			pane.getChildren().add(lblRestName);

			Label lblPrice = new Label();
			lblPrice.setText("$" + item.getItemPrice());
			lblPrice.setFont(new Font(24.0));
			lblPrice.setWrapText(true);
			lblPrice.setLayoutX(606.0);
			lblPrice.setLayoutY(15.0);
			AnchorPane.setRightAnchor(lblPrice, 20.0);
			AnchorPane.setTopAnchor(lblPrice, 15.0);
			pane.getChildren().add(lblPrice);

			JFXTextField txtQuantity = new JFXTextField();
			txtQuantity.setPromptText("Quantity");
			txtQuantity.setText(String.valueOf(BaseController.foodBasket.getOrderItems().get(index).getItemQuantity()));
			txtQuantity.setFont(new Font(15.0));
			txtQuantity.setLayoutX(575.0);
			txtQuantity.setLayoutY(20.0);
			txtQuantity.setPrefHeight(30.0);
			txtQuantity.setPrefWidth(60.0);
			txtQuantity.setLabelFloat(true);
			AnchorPane.setRightAnchor(lblPrice, 10.0);
			AnchorPane.setTopAnchor(lblPrice, 15.0);
			txtQuantity.textProperty().addListener(new ChangeListener<String>() {
				@Override
				public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
					if (StringUtils.isNotBlank(newValue)) {
						log.debug("Quantity changed to new value " + newValue + ". Recalculating the Order Summary");
						BaseController.foodBasket.getOrderItems().get(index)
								.setItemQuantity(Integer.parseInt(newValue));
						renderOrderSummary();
					}
				}
			});
			pane.getChildren().add(txtQuantity);

			JFXButton removeFromBasket = new JFXButton();
			removeFromBasket.setText("Remove from basket");
			removeFromBasket.prefHeight(30.0);
			removeFromBasket.prefWidth(120.0);
			removeFromBasket.setStyle("-fx-background-color: TOMATO;");
			removeFromBasket.setTextFill(Paint.valueOf("WHITE"));
			removeFromBasket.setButtonType(ButtonType.RAISED);
			removeFromBasket.setLayoutX(606.0);
			removeFromBasket.setLayoutY(90.0);
			AnchorPane.setRightAnchor(removeFromBasket, 30.0);
			AnchorPane.setBottomAnchor(removeFromBasket, 20.0);

			removeFromBasket.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					removeItemFromBasket(index);
					renderBasketItems();
					renderOrderSummary();
				}
			});
			pane.getChildren().add(removeFromBasket);

			anchorPaneItemList.getChildren().add(pane);
		} catch (Exception ex) {
			log.error(ex.getMessage());
		}
	}

	private void removeItemFromBasket(int index) {
		int itemToBeRemoved = BaseController.foodBasket.getOrderItems().get(index).getItemId();
		int indexToBeRemoved = -1;
		indexToBeRemoved = isItemPresent(itemToBeRemoved);
		if (indexToBeRemoved != -1) {
			log.debug("Item id: " + BaseController.foodBasket.getOrderItems().get(index).getItemId()
					+ " removed from basket");
			BaseController.foodBasket.getOrderItems().remove(indexToBeRemoved);
			log.debug("Current Items in basket: " + BaseController.foodBasket.getOrderItems().toString());
			navigateToSearchScreenIfNoItemsInBasket();
		}

	}

	private void navigateToSearchScreenIfNoItemsInBasket() {
		if (BaseController.foodBasket.getOrderItems().size() == 0) {
			handleSearch();
		}
	}

	private void placeOrder() {

		try {
			if (!isCardInputValid()) {
				return;
			}
			// Set the payment and address
			int selectedDeliveryMode = -1;
			JFXToggleButton button = (JFXToggleButton) deliveryMode.getSelectedToggle();

			if (StringUtils.isNotBlank(button.getText())) {

				if (button.getText().equals("Pickup")) {
					selectedDeliveryMode = 1;

				} else {
					selectedDeliveryMode = 2;
					BaseController.foodBasket.setAddress(BaseController.authenticatedCustomer.getAddresses()
							.get(addressPicker.getSelectionModel().getSelectedIndex()).getAddrId());
				}
			} else {
				lblOrderErrorMsg.setText("Please choose the delivery option");
			}
			BaseController.foodBasket.setPayment(BaseController.authenticatedCustomer.getPayments()
					.get(paymentPicker.getSelectionModel().getSelectedIndex()).getCardid());
			BaseController.foodBasket.setCustomer(BaseController.authenticatedCustomer.getCustomerID());

			BaseController.foodBasket.setDeliveryMode(selectedDeliveryMode);

			customerOperation.updateOrder(BaseController.foodBasket);

			// Reset the basket
			BaseController.foodBasket = new Basket();
			orderPlacedAlert();
			handleRating();
			handleOrders();

		} catch (Exception ex) {
			lblOrderErrorMsg.setText("Unable to place order. Please try again later.");
			orderErrorPane.setVisible(true);
			log.error(ex.getMessage(), ex);
		}
	}

	public void orderPlacedAlert() {
		Alert orderPlacedMsg = new Alert(Alert.AlertType.INFORMATION);

		orderPlacedMsg.setTitle("Order Placed");
		orderPlacedMsg.setContentText("You have successfully placed your order!" + "\n"
				+ "You can track your order status in the orders section");
		orderPlacedMsg.setHeaderText(null);
		orderPlacedMsg.showAndWait();
	}

	public void getCouponValue() {
		couponTextBox.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> couponText, String oldCoupon, String newCoupon) {
				log.debug("coupon changed to new value " + newCoupon + ". Recalculating the Order Summary");
				if (StringUtils.isNotBlank(newCoupon)) {

					BaseController.foodBasket.setCouponobj(couponDB.getSelectedCoupon(newCoupon));
					renderOrderSummary();

				} else {
					BaseController.foodBasket.setCouponobj(null);
					renderOrderSummary();
				}
			}

		});

	}

	public boolean isCardInputValid() {
		if (null == paymentPicker.getValue() ||  StringUtils.isEmpty(paymentPicker.getValue().toString())) {
			lblOrderErrorMsg.setText("Please choose a card for payment");
			this.orderErrorPane.setVisible(true);
			return false;
		}
		this.orderErrorPane.setVisible(false);
		return true;
	}
}
