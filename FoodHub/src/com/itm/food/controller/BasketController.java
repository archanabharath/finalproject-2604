package com.itm.food.controller;

import java.io.File;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.itm.food.dao.Address;
import com.itm.food.dao.Basket;
import com.itm.food.dao.Item;
import com.itm.food.dao.Payment;
import com.itm.food.dao.Restaurant;
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
import javafx.scene.control.ChoiceBox;
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

	public void init() {
		super.init();
		if (null != BaseController.foodBasket.getOrderItems() || BaseController.foodBasket.getOrderItems().isEmpty()) {
			this.errorPane.setVisible(false);
			this.orderErrorPane.setVisible(false);
			renderOrderSummary();
			renderBasketItems();
		}

	}

    @FXML
    void handleDelivery(ActionEvent event) {
    	addressPicker.setDisable(false);
    }
    
    @FXML
    void handlePickup(ActionEvent event) {
    	addressPicker.setDisable(true);
    }
	
	@FXML
	void handlePlaceOrder(ActionEvent event) {
		placeOrder();
	}

	void renderOrderSummary() {
		try {
			BaseController.foodBasket.calculateOrderSummary();
			lblItemsTotal.setText("$" + BaseController.foodBasket.getItemsTotal());
			lblDeliveryCharge.setText("$" + BaseController.foodBasket.getDeliveryCharge());
			lblCouponsApplied.setText("$" + BaseController.foodBasket.getCouponsApplied());
			lblTotalBeforeTax.setText("$" + BaseController.foodBasket.getTotalBeforeTax());
			lblTaxApplied.setText("$" + BaseController.foodBasket.getTaxApplied());
			lblOrderTotal.setText("$" + BaseController.foodBasket.getOrderTotal());

			if (null == BaseController.authenticatedCustomer.getAddresses()
					|| BaseController.authenticatedCustomer.getAddresses().size() == 0) {
				BaseController.authenticatedCustomer.setAddresses(
						customerOperation.getCustomerAddress(BaseController.authenticatedCustomer.getCustomerID()));
			}
			ObservableList<Address> obAddress = null;
			obAddress = FXCollections.observableArrayList(BaseController.authenticatedCustomer.getAddresses());
			addressPicker.setItems(obAddress);
			addressPicker.setValue(BaseController.authenticatedCustomer.getAddresses().get(0));

			if (null == BaseController.authenticatedCustomer.getPayments()
					|| BaseController.authenticatedCustomer.getPayments().size() == 0) {
				BaseController.authenticatedCustomer
						.setPayments(paymentOperation.getCards(BaseController.authenticatedCustomer.getCustomerID()));
			}
			ObservableList<Payment> obPayment = null;
			obPayment = FXCollections.observableArrayList(BaseController.authenticatedCustomer.getPayments());
			paymentPicker.setItems(obPayment);
			paymentPicker.setValue(BaseController.authenticatedCustomer.getPayments().get(0));

		} catch (Exception ex) {
			lblOrderErrorMsg.setText("Unable to load order Summary.");
			orderErrorPane.setVisible(true);
			log.error(ex.getMessage());
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

			String url = "file://" + new File("").getCanonicalFile().getParent().toString() + File.separatorChar
					+ "FoodHub/asserts/default-items.png";
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

			JFXButton addToBasket = new JFXButton();
			addToBasket.setText("Remove from basket");
			addToBasket.prefHeight(30.0);
			addToBasket.prefWidth(120.0);
			addToBasket.setStyle("-fx-background-color: TOMATO;");
			addToBasket.setTextFill(Paint.valueOf("WHITE"));
			addToBasket.setButtonType(ButtonType.RAISED);
			addToBasket.setLayoutX(606.0);
			addToBasket.setLayoutY(90.0);
			AnchorPane.setRightAnchor(addToBasket, 30.0);
			AnchorPane.setBottomAnchor(addToBasket, 20.0);

			addToBasket.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					removeItemFromBasket(index);
					renderBasketItems();
				}
			});
			pane.getChildren().add(addToBasket);

			anchorPaneItemList.getChildren().add(pane);
		} catch (Exception ex) {
			log.error(ex.getMessage());
		}
	}

	private void removeItemFromBasket(int index) {
		String itemToBeRemoved = BaseController.foodBasket.getOrderItems().get(index).getItemId();
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
			// Set the payment and address
			int selectedDeliveryMode = -1;
			JFXToggleButton button = (JFXToggleButton) deliveryMode.getSelectedToggle();
			if (button.getText().equals("Pickup")) {
				selectedDeliveryMode = 1;
			} else {
				selectedDeliveryMode = 2;
				BaseController.foodBasket.setAddress(BaseController.authenticatedCustomer.getAddresses()
						.get(addressPicker.getSelectionModel().getSelectedIndex()).getAddrId());
			}
			BaseController.foodBasket.setPayment(BaseController.authenticatedCustomer.getPayments()
					.get(paymentPicker.getSelectionModel().getSelectedIndex()).getCardid());
			BaseController.foodBasket.setCustomer(BaseController.authenticatedCustomer.getCustomerID());

			BaseController.foodBasket.setDeliveryMode(selectedDeliveryMode);

			customerOperation.updateOrder(BaseController.foodBasket);

			
			// Reset the basket
			BaseController.foodBasket = new Basket();

			handleOrders();

		} catch (Exception ex) {
			lblOrderErrorMsg.setText("Unable to place order. Please try again later.");
			orderErrorPane.setVisible(true);
			log.error(ex.getMessage(), ex);
		}
	}

}
