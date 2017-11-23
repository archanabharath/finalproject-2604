package com.itm.food.controller;

import java.io.File;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;

public class SearchController extends BaseController {

	private static final Logger log = Logger.getLogger(SearchController.class);

	@FXML
	private ScrollPane scrollPaneRest;

	@FXML
	private AnchorPane anchorPaneRestList;

	@FXML
	private JFXButton btnSearch;

	@FXML
	private JFXTextField txtSearch;

	@Override
	void init() {
		super.init();
		scrollPaneRest.setVisible(false);

	}

	@FXML
	void handleSearch(ActionEvent event) {
		try {
			int zipcode = 0;
			if (StringUtils.isBlank(txtSearch.getText())) {
				// Get location using GeoLocator
			} else {
				zipcode = Integer.parseInt(txtSearch.getText());
			}
			// Load restaurant result
			loadRestaurantSearchResult(zipcode);

			if (BaseController.preferredRestaurents.size() > 0) {
				// Clean the anchorPaneRestList before rendering the new search
				// list
				anchorPaneRestList.getChildren().removeAll(anchorPaneRestList.getChildren());
				scrollPaneRest.setVisible(true);
				for (int i = 0; i < BaseController.preferredRestaurents.size(); i++) {
					renderSearchList(i);
				}
			} else {
				scrollPaneRest.setVisible(false);
				log.debug("No resutaurant found.");
			}
		} catch (Exception ex) {
			log.error(ex.getMessage());
		}
	}

	void loadRestaurantSearchResult(int zipcode) throws Exception {
		BaseController.preferredRestaurents.clear();
		BaseController.preferredRestaurents.addAll(customerOperation.searchRestaurants(zipcode));
	}

	void renderSearchList(int count) {
		double paneHeightNeeded = count + 1 * 210;
		double currentPaneHeight = anchorPaneRestList.getPrefHeight();
		if (paneHeightNeeded <= currentPaneHeight) {
			anchorPaneRestList.setPrefHeight(currentPaneHeight + 210.0);
		}

		// <AnchorPane prefHeight="200.0" prefWidth="1180.0"
		// style="-fx-border-color: lightgrey;">
		// Add a inner pane container
		AnchorPane pane = new AnchorPane();
		pane.setLayoutY(count * 210); // TODO INCREMENT THIS 210.0
		pane.setPrefHeight(200.0);
		pane.setPrefWidth(1180.0);
		pane.setStyle("-fx-border-color: lightgrey;");

		// <ImageView fitHeight="150.0" fitWidth="200.0" layoutX="47.0"
		// layoutY="25.0" pickOnBounds="true" preserveRatio="true"
		// AnchorPane.leftAnchor="10.0" AnchorPane.topAnchor="25.0">
		// Add a image view for restaurant
		ImageView imageView = new ImageView();
		imageView.setFitHeight(150.0);
		imageView.setFitWidth(200.0);
		imageView.setLayoutX(45.0);
		imageView.setLayoutY(25.0);
		imageView.setPickOnBounds(true);
		imageView.setPreserveRatio(false);
		AnchorPane.setLeftAnchor(imageView, 10.0);
		AnchorPane.setTopAnchor(imageView, 25.0);
		try {
			String url = "file://" + new File("").getCanonicalFile().getParent().toString() + File.separatorChar
					+ "FoodHub/asserts/default-restaurent.png";
			imageView.setImage(new Image(url));
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		pane.getChildren().add(imageView);

		// <Label layoutX="202.0" layoutY="22.0" prefHeight="30.0"
		// prefWidth="900.0" text="[ResutantName]" wrapText="true"
		// AnchorPane.leftAnchor="200.0" AnchorPane.topAnchor="20.0">
		// <font>
		// <Font size="24.0" />
		// </font>
		// </Label>
		Label lblRestName = new Label();
		lblRestName.setLayoutX(200.0);
		lblRestName.setLayoutY(20.0);
		lblRestName.prefHeight(30.0);
		lblRestName.prefWidth(900.0);
		lblRestName.setText(BaseController.preferredRestaurents.get(count).getRestaurantName());
		lblRestName.setWrapText(true);
		lblRestName.setFont(new Font(24.0));
		AnchorPane.setLeftAnchor(lblRestName, 200.0);
		AnchorPane.setTopAnchor(lblRestName, 20.0);
		pane.getChildren().add(lblRestName);

		// <Label layoutX="210.0" layoutY="70.0" prefHeight="20.0"
		// prefWidth="615.0" text="[ResturantDesc]" wrapText="true"
		// AnchorPane.leftAnchor="200.0" AnchorPane.topAnchor="60.0">
		// <font>
		// <Font size="15.0" />
		// </font>
		// </Label>
		Label lblRestDesc = new Label();
		lblRestDesc.setLayoutX(210.0);
		lblRestDesc.setLayoutY(70.0);
		lblRestDesc.prefHeight(100.0);
		lblRestDesc.prefWidth(100.0);
		lblRestDesc.setText(BaseController.preferredRestaurents.get(count).getRestaurantDescription());
		lblRestDesc.setWrapText(true);
		lblRestDesc.setFont(new Font(15.0));
		AnchorPane.setLeftAnchor(lblRestDesc, 200.0);
		AnchorPane.setTopAnchor(lblRestDesc, 60.0);
		AnchorPane.setRightAnchor(lblRestDesc, 400.0);
		pane.getChildren().add(lblRestDesc);

		// <Label layoutX="896.0" layoutY="156.0" text="[0 Miles]"
		// AnchorPane.bottomAnchor="20.0" AnchorPane.rightAnchor="200.0">
		// <font>
		// <Font size="18.0" />
		// </font>
		// </Label>
		Label lblRestDistance = new Label();
		lblRestDistance.setLayoutX(896.0);
		lblRestDistance.setLayoutY(156.0);
		lblRestDistance.setText("[0 Miles]");
		lblRestDistance.setFont(new Font(18.0));
		AnchorPane.setRightAnchor(lblRestDistance, 200.0);
		AnchorPane.setBottomAnchor(lblRestDistance, 20.0);
		pane.getChildren().add(lblRestDistance);

		// <Label layoutX="1021.0" layoutY="156.0" text="[0 Mins]"
		// AnchorPane.bottomAnchor="20.0" AnchorPane.rightAnchor="75.0">
		// <font>
		// <Font size="18.0" />
		// </font>
		// </Label>
		Label lblRestTravelTime = new Label();
		lblRestTravelTime.setLayoutX(1021.0);
		lblRestTravelTime.setLayoutY(156.0);
		lblRestTravelTime.setText("[0 Mins]");
		lblRestTravelTime.setFont(new Font(18.0));
		AnchorPane.setRightAnchor(lblRestTravelTime, 75.0);
		AnchorPane.setBottomAnchor(lblRestTravelTime, 20.0);
		pane.getChildren().add(lblRestTravelTime);

		anchorPaneRestList.getChildren().add(pane);
	}

}
