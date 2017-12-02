
package com.itm.food.controller;

import java.util.ArrayList;

import org.apache.commons.math3.util.Precision;
import org.apache.log4j.Logger;
import org.controlsfx.control.Rating;

import com.itm.food.dao.ItemRating;
import com.itm.food.dao.OrderItem;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXButton.ButtonType;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;

public class ItemsController extends BaseController {

	private static final Logger log = Logger.getLogger(ItemsController.class);

	@FXML
	private AnchorPane anchorPaneRestHeader;

	@FXML
	private ImageView imgRestaurant;

	@FXML
	private Label lblRestaurantName;

	@FXML
	private Label lblRestAddress;

	@FXML
	private ScrollPane scrollPaneRest;

	@FXML
	private AnchorPane anchorPaneItemList;

	@FXML
	private AnchorPane noMenuPane;

	@Override
	void init() {
		super.init();
		loadItemsList();
		renderRestaurantHeader();
		renderItemList();
	}

	void loadItemsList() {
		try {
			BaseController.currentRestaurant.setItems(
					customerOperation.getItemsByRestaurant(BaseController.currentRestaurant.getRestaurantId()));
			if (null == BaseController.foodBasket.getOrderItems()) {
				BaseController.foodBasket.setOrderItems(new ArrayList<OrderItem>());
			}
			if (null == BaseController.currentRestaurant.getItems()
					|| BaseController.currentRestaurant.getItems().size() == 0) {
				noMenuPane.setVisible(true);
				scrollPaneRest.setVisible(false);
			} else {
				noMenuPane.setVisible(false);
				scrollPaneRest.setVisible(true);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}

	void renderRestaurantHeader() {
		try {
	/*		String url = "file:\\" + new File("").getCanonicalFile().getParent().toString() + File.separatorChar
					+ "FoodHub" + File.separatorChar + "src" + File.separatorChar + "com" + File.separatorChar + "itm"
					+ File.separatorChar + "food" + File.separatorChar + "images" + File.separatorChar
					+ "default-restaurent.png";*/
			String url = "file:\\E:\\Projects\\GitHub\\finalproject-2604\\FoodHub\\src\\com\\itm\\food\\images\\default-restaurant.jpg";
			imgRestaurant.setImage(new Image(url));
		} catch (Exception ex) {
			log.error(ex.getMessage());
		}

		lblRestaurantName.setText(BaseController.currentRestaurant.getRestaurantName());
		lblRestaurantName.setTextFill(Color.CRIMSON);
		lblRestAddress.setText(BaseController.currentRestaurant.getAddressPhoneAndEmail());
	}

	void renderItemList() {
		if ((BaseController.currentRestaurant.getRestaurantId() != 0)) {
			// Clean the anchorPaneRestList before rendering the new search list
			anchorPaneItemList.getChildren().removeAll(anchorPaneItemList.getChildren());
			anchorPaneItemList.setPrefHeight(150.0);
			for (int i = 0; i < BaseController.currentRestaurant.getItems().size(); i++) {
				renderItem(i);
			}
		}
	}

	void renderItem(int index) {
		double paneHeightNeeded = index + 1 * 150;
		double currentPaneHeight = anchorPaneItemList.getPrefHeight();
		if (paneHeightNeeded <= currentPaneHeight) {
			anchorPaneItemList.setPrefHeight(currentPaneHeight + 150.0);
		}

		AnchorPane pane = new AnchorPane();
		pane.setLayoutY(index * 150); // TODO INCREMENT THIS 150
		pane.setPrefHeight(150.0);
		pane.setPrefWidth(1180.0);
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
		try {
/*			String url = "file:\\" + new File("").getCanonicalFile().getParent().toString() + File.separatorChar
					+ "FoodHub" + File.separatorChar + "src" + File.separatorChar + "com" + File.separatorChar + "itm"
					+ File.separatorChar + "food" + File.separatorChar + "images" + File.separatorChar
					+ "default-items.png";*/
			String url = "file:\\E:\\Projects\\GitHub\\finalproject-2604\\FoodHub\\src\\com\\itm\\food\\images\\default-items.png";
			imageView.setImage(new Image(url));
		} catch (Exception ex) {
			log.error(ex.getMessage());
		}
		pane.getChildren().add(imageView);

		Label lblItemName = new Label();
		lblItemName.setLayoutX(200.0);
		lblItemName.setLayoutY(20.0);
		lblItemName.prefHeight(30.0);
		lblItemName.prefWidth(700.0);
		lblItemName.setText(BaseController.currentRestaurant.getItems().get(index).getItemName());
		lblItemName.setWrapText(true);
		lblItemName.setFont(new Font(24.0));
		lblItemName.setTextFill(Color.CRIMSON);
		AnchorPane.setLeftAnchor(lblItemName, 200.0);
		AnchorPane.setTopAnchor(lblItemName, 20.0);
		pane.getChildren().add(lblItemName);

		Label lblItemDesc = new Label();
		lblItemDesc.setLayoutX(200.0);
		lblItemDesc.setLayoutY(70.0);
		lblItemDesc.prefHeight(100.0);
		lblItemDesc.prefWidth(100.0);
		lblItemDesc.setText(BaseController.currentRestaurant.getItems().get(index).getItemDesc());
		lblItemDesc.setWrapText(true);
		lblItemDesc.setFont(new Font(15.0));
		lblItemDesc.setStyle("-fx-font-style: italic");
		AnchorPane.setLeftAnchor(lblItemDesc, 200.0);
		AnchorPane.setTopAnchor(lblItemDesc, 60.0);
		AnchorPane.setRightAnchor(lblItemDesc, 400.0);
		pane.getChildren().add(lblItemDesc);

		Label lblPrice = new Label();
		lblPrice.setText("$" + BaseController.currentRestaurant.getItems().get(index).getItemPrice());
		lblPrice.setFont(new Font(24.0));
		lblPrice.setWrapText(true);
		lblPrice.setLayoutX(1020.0);
		lblPrice.setLayoutY(70.0);
		AnchorPane.setRightAnchor(lblPrice, 100.0);
		AnchorPane.setTopAnchor(lblPrice, 20.0);
		AnchorPane.setLeftAnchor(lblPrice, 860.0);
		pane.getChildren().add(lblPrice);

		Rating rating = new Rating();
		rating.setRating(BaseController.currentRestaurant.getItems().get(index).getItemOverallRating());
		rating.setPartialRating(true);
		rating.setLayoutX(900.0);
		rating.setLayoutY(70.0);
		AnchorPane.setRightAnchor(rating, 100.0);
		AnchorPane.setTopAnchor(rating, 60.0);
		AnchorPane.setLeftAnchor(rating, 860.0);
		rating.ratingProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				ItemRating itemRating = new ItemRating();
				itemRating.setCustomerId(BaseController.authenticatedCustomer.getCustomerID());
				itemRating.setRating(Precision.round(newValue.doubleValue(), 2));
				itemRating.setItemId(BaseController.currentRestaurant.getItems().get(index).getItemId());
				try {
					customerOperation.addItemRating(itemRating);
				} catch (Exception e) {
					log.error(e.getMessage());
				}
				log.debug("Rating:" + Precision.round(newValue.doubleValue(), 2));
			}
		});

		pane.getChildren().add(rating);

		JFXButton addToBasket = new JFXButton();
		addToBasket.prefHeight(30.0);
		addToBasket.prefWidth(120.0);
		addToBasket.setTextFill(Paint.valueOf("#4682b4"));
		addToBasket.setButtonType(ButtonType.RAISED);
		addToBasket.setLayoutX(850.0);
		addToBasket.setLayoutY(70.0);
		AnchorPane.setTopAnchor(addToBasket, 100.0);
		AnchorPane.setLeftAnchor(addToBasket, 850.0);
		if (isItemPresent(BaseController.currentRestaurant.getItems().get(index).getItemId()) != -1) {
			addToBasket.setText("Remove from Basket");
			addToBasket.setStyle("-fx-background-color: TOMATO;");
			addToBasket.setTextFill(Paint.valueOf("WHITE"));
		} else {
			addToBasket.setText("Add to Basket");
			addToBasket.setStyle("-fx-background-color: GOLD;");
			addToBasket.setTextFill(Paint.valueOf("#4682b4"));
		}

		addToBasket.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (addToBasket.getText().equals("Add to Basket")) {
					addItemToBasket(index);
					addToBasket.setText("Remove from Basket");
					addToBasket.setStyle("-fx-background-color: TOMATO;");
					addToBasket.setTextFill(Paint.valueOf("WHITE"));
				} else {
					removeItemFromBasket(index);
					addToBasket.setText("Add to Basket");
					addToBasket.setStyle("-fx-background-color: GOLD;");
					addToBasket.setTextFill(Paint.valueOf("#4682b4"));
				}
			}
		});
		pane.getChildren().add(addToBasket);

		anchorPaneItemList.getChildren().add(pane);
	}

	private void addItemToBasket(int index) {
		OrderItem itemsToBasket = new OrderItem();
		itemsToBasket.setRestaurantId(BaseController.currentRestaurant.getRestaurantId());
		itemsToBasket.setItemId(BaseController.currentRestaurant.getItems().get(index).getItemId());
		itemsToBasket.setItemPrice(BaseController.currentRestaurant.getItems().get(index).getItemPrice());
		itemsToBasket.setItemQuantity(1);
		BaseController.foodBasket.getOrderItems().add(itemsToBasket);
		enableOrDisableBasketButtonIfNeeded();

	}

	private void removeItemFromBasket(int index) {
		int itemToBeRemoved = BaseController.currentRestaurant.getItems().get(index).getItemId();
		int indexToBeRemoved = -1;
		indexToBeRemoved = isItemPresent(itemToBeRemoved);
		if (indexToBeRemoved != -1) {
			BaseController.foodBasket.getOrderItems().remove(indexToBeRemoved);
		}
		enableOrDisableBasketButtonIfNeeded();

	}

	private void enableOrDisableBasketButtonIfNeeded() {
		if (null != BaseController.foodBasket.getOrderItems() && BaseController.foodBasket.getOrderItems().size() > 0) {
			btnBasket.setDisable(false);
		} else {
			btnBasket.setDisable(true);
		}
	}
}
