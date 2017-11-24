package com.itm.food.controller;

import java.io.File;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXButton.ButtonType;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
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
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}

	void renderRestaurantHeader() {
		try {
			String url = "file://" + new File("").getCanonicalFile().getParent().toString() + File.separatorChar
					+ "FoodHub/asserts/default-restaurent.png";
			imgRestaurant.setImage(new Image(url));
		} catch (Exception ex) {
			log.error(ex.getMessage());
		}

		lblRestaurantName.setText(BaseController.currentRestaurant.getRestaurantName());
		lblRestAddress.setText(BaseController.currentRestaurant.getAddressPhoneAndEmail());
	}

	void renderItemList() {
		if (StringUtils.isNotEmpty(BaseController.currentRestaurant.getRestaurantId())) {
			// Clean the anchorPaneRestList before rendering the new search list
			anchorPaneItemList.getChildren().removeAll(anchorPaneItemList.getChildren());
			anchorPaneItemList.setPrefHeight(150.0);
			scrollPaneRest.setVisible(true);
			for (int i = 0; i < BaseController.currentRestaurant.getItems().size(); i++) {
				renderItem(i);
			}
		} else {
			scrollPaneRest.setVisible(false);
			log.debug("No Items found.");
		}
	}

	void renderItem(int count) {
		double paneHeightNeeded = count + 1 * 150;
		double currentPaneHeight = anchorPaneItemList.getPrefHeight();
		if (paneHeightNeeded <= currentPaneHeight) {
			anchorPaneItemList.setPrefHeight(currentPaneHeight + 150.0);
		}

		AnchorPane pane = new AnchorPane();
		pane.setLayoutY(count * 150); // TODO INCREMENT THIS 150
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
			String url = "file://" + new File("").getCanonicalFile().getParent().toString() + File.separatorChar
					+ "FoodHub/asserts/default-items.png";
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
		lblItemName.setText(BaseController.currentRestaurant.getItems().get(count).getItemName());
		lblItemName.setWrapText(true);
		lblItemName.setFont(new Font(24.0));
		AnchorPane.setLeftAnchor(lblItemName, 200.0);
		AnchorPane.setTopAnchor(lblItemName, 20.0);
		pane.getChildren().add(lblItemName);

		Label lblItemDesc = new Label();
		lblItemDesc.setLayoutX(200.0);
		lblItemDesc.setLayoutY(70.0);
		lblItemDesc.prefHeight(100.0);
		lblItemDesc.prefWidth(100.0);
		lblItemDesc.setText(BaseController.currentRestaurant.getItems().get(count).getItemDesc());
		lblItemDesc.setWrapText(true);
		lblItemDesc.setFont(new Font(15.0));
		AnchorPane.setLeftAnchor(lblItemDesc, 200.0);
		AnchorPane.setTopAnchor(lblItemDesc, 60.0);
		AnchorPane.setRightAnchor(lblItemDesc, 400.0);
		pane.getChildren().add(lblItemDesc);

		Label lblPrice = new Label();
		lblPrice.setText("$" + BaseController.currentRestaurant.getItems().get(count).getItemPrice());
		lblPrice.setFont(new Font(24.0));
		lblPrice.setWrapText(true);
		lblPrice.setLayoutX(1020.0);
		lblPrice.setLayoutY(70.0);
		AnchorPane.setRightAnchor(lblPrice, 100.0);
		AnchorPane.setTopAnchor(lblPrice, 20.0);
		AnchorPane.setLeftAnchor(lblPrice, 860.0);
		pane.getChildren().add(lblPrice);

		Label lblRating = new Label();
		lblRating.setText(
				"Item Rating: " + BaseController.currentRestaurant.getItems().get(count).getItemOverallRating());
		lblRating.setFont(new Font(15.0));
		lblRating.setWrapText(true);
		lblRating.setLayoutX(900.0);
		lblRating.setLayoutY(70.0);
		AnchorPane.setRightAnchor(lblRating, 100.0);
		AnchorPane.setTopAnchor(lblRating, 60.0);
		AnchorPane.setLeftAnchor(lblRating, 860.0);
		pane.getChildren().add(lblRating);

		JFXButton addToBasket = new JFXButton();
		addToBasket.prefHeight(30.0);
		addToBasket.prefWidth(120.0);
		addToBasket.setTextFill(Paint.valueOf("#4682b4"));
		addToBasket.setButtonType(ButtonType.RAISED);
		addToBasket.setLayoutX(850.0);
		addToBasket.setLayoutY(70.0);
		AnchorPane.setTopAnchor(addToBasket, 100.0);
		AnchorPane.setLeftAnchor(addToBasket, 850.0);
		if (BaseController.itemsInBasket.contains(BaseController.currentRestaurant.getItems().get(count).getItemId())) {
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
					BaseController.itemsInBasket.add(String.valueOf(BaseController.currentRestaurant.getItems().get(count).getItemId()));
					addToBasket.setText("Remove from Basket");
					addToBasket.setStyle("-fx-background-color: TOMATO;");
					addToBasket.setTextFill(Paint.valueOf("WHITE"));
				} else {
					BaseController.itemsInBasket.remove(String.valueOf(BaseController.currentRestaurant.getItems().get(count).getItemId()));
					addToBasket.setText("Add to Basket");
					addToBasket.setStyle("-fx-background-color: GOLD;");
					addToBasket.setTextFill(Paint.valueOf("#4682b4"));
				}
				log.debug("Item id: " + BaseController.currentRestaurant.getItems().get(count).getItemId() + " added to basket");
				log.debug("Current Items in basket: " + BaseController.itemsInBasket.toString());
			}
		});
		pane.getChildren().add(addToBasket);

		anchorPaneItemList.getChildren().add(pane);
	}
}
