package com.itm.food.controller;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.itm.food.dao.Restaurant;
import com.itm.food.util.DistanceMatrix;
import com.itm.food.util.GeoLocator;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
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
	
	@FXML
	private AnchorPane noRestaurantPane;

	@Override
	void init() {
		super.init();
		scrollPaneRest.setVisible(false);
		if(BaseController.preferredRestaurants.size() > 0) {
			renderRestaurantList();
		}
		
	}

	@FXML
	void handleSearch(ActionEvent event) {
		try {
			GeoLocator geoLocator; 
			if (StringUtils.isBlank(txtSearch.getText())) {
				// Get location using GeoLocator
				geoLocator = new GeoLocator();
				geoLocator.getCurrentGeoCordinates();
			} else {
				geoLocator = new GeoLocator(txtSearch.getText());
				geoLocator.lookupGeocoding();
			}
			
			List<Integer> nearByzipcodes = geoLocator.getNearByZipCodes();
			
			// Load restaurant result
			loadRestaurantSearchResult(nearByzipcodes);

			// Retrieve the Distance and time for the restaurant from customer
			// place.
			Map<String, DistanceMatrix> distanceTimeMap = new HashMap<String, DistanceMatrix>();
			for (Restaurant restaurant : BaseController.preferredRestaurants) {
				DistanceMatrix dMatrix = new DistanceMatrix();
				dMatrix.setDestLat(String.valueOf(restaurant.getLatitude()));
				dMatrix.setDestLng(String.valueOf(restaurant.getLongitude()));
				distanceTimeMap.put(restaurant.getRestaurantId(), dMatrix);
			}

			geoLocator.getDistanceMatrix(distanceTimeMap);

			// Set the Distance & TimeToTravel to Restaurant object.
			for (Restaurant restaurant : BaseController.preferredRestaurants) {
				restaurant.setDistance(distanceTimeMap.get(restaurant.getRestaurantId()).getMiles());
				restaurant.setTimeToTravel(distanceTimeMap.get(restaurant.getRestaurantId()).getDuration());
			}
			renderRestaurantList();
			
		} catch (Exception ex) {
			log.error(ex.getMessage());
		}
	}

	void loadRestaurantSearchResult(List<Integer> zipcodes) throws Exception {
		BaseController.preferredRestaurants.clear();
		BaseController.preferredRestaurants.addAll(customerOperation.searchRestaurants(zipcodes));
		if (null == BaseController.preferredRestaurants
				|| BaseController.preferredRestaurants.size() == 0) {
			noRestaurantPane.setVisible(true);
			scrollPaneRest.setVisible(false);
		} else {
			noRestaurantPane.setVisible(false);
			scrollPaneRest.setVisible(true);
		}
	}
	
	void renderRestaurantList() {
		if (BaseController.preferredRestaurants.size() > 0) {
			// Clean the anchorPaneRestList before rendering the new search
			// list
			anchorPaneRestList.getChildren().removeAll(anchorPaneRestList.getChildren());
			anchorPaneRestList.setPrefHeight(210.0);
			scrollPaneRest.setVisible(true);

			for (int i = 0; i < BaseController.preferredRestaurants.size(); i++) {
				renderRestaurant(i);
			}
		} else {
			scrollPaneRest.setVisible(false);
			log.debug("No restaurant found.");
		}
	}

	void renderRestaurant(int count) {
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
		imageView.setFitWidth(180.0);
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
			log.error(ex.getMessage());
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
		lblRestName.setText(BaseController.preferredRestaurants.get(count).getRestaurantName());
		lblRestName.setWrapText(true);
		lblRestName.setTextFill(Color.CRIMSON);
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
		lblRestDesc.setLayoutX(200.0);
		lblRestDesc.setLayoutY(70.0);
		lblRestDesc.prefHeight(100.0);
		lblRestDesc.prefWidth(100.0);
		lblRestDesc.setText(BaseController.preferredRestaurants.get(count).getRestaurantDescription());
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
		ImageView imageDistance = new ImageView();
		imageDistance.setFitHeight(25.0);
		imageDistance.setFitWidth(25.0);
		imageDistance.setPickOnBounds(true);
		imageDistance.setPreserveRatio(false);
		AnchorPane.setRightAnchor(imageDistance, 275.0);
		AnchorPane.setBottomAnchor(imageDistance, 20.0);
		try {
			String url = "file://" + new File("").getCanonicalFile().getParent().toString() + File.separatorChar
					+ "FoodHub/asserts/icons8-geo-fence-24.png";
			imageDistance.setImage(new Image(url));
		} catch (Exception ex) {
			log.error(ex.getMessage());
		}
		pane.getChildren().add(imageDistance);
		
		Label lblRestDistance = new Label();
		lblRestDistance.setLayoutX(896.0);
		lblRestDistance.setLayoutY(156.0);
		lblRestDistance.setText(BaseController.preferredRestaurants.get(count).getDistance());
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
		ImageView imageTimer = new ImageView();
		imageTimer.setFitHeight(25.0);
		imageTimer.setFitWidth(25.0);
		imageTimer.setPickOnBounds(true);
		imageTimer.setPreserveRatio(false);
		AnchorPane.setRightAnchor(imageTimer, 145.0);
		AnchorPane.setBottomAnchor(imageTimer, 20.0);
		try {
			String url = "file://" + new File("").getCanonicalFile().getParent().toString() + File.separatorChar
					+ "FoodHub/asserts/icons8-timer-24.png";
			imageTimer.setImage(new Image(url));
		} catch (Exception ex) {
			log.error(ex.getMessage());
		}
		pane.getChildren().add(imageTimer);
		
		Label lblRestTravelTime = new Label();
		lblRestTravelTime.setLayoutX(1021.0);
		lblRestTravelTime.setLayoutY(156.0);
		lblRestTravelTime.setText(BaseController.preferredRestaurants.get(count).getTimeToTravel());
		lblRestTravelTime.setFont(new Font(18.0));
		AnchorPane.setRightAnchor(lblRestTravelTime, 75.0);
		AnchorPane.setBottomAnchor(lblRestTravelTime, 20.0);
		pane.getChildren().add(lblRestTravelTime);

		// <Label text="[Address]" AnchorPane.bottomAnchor="20.0"
		// AnchorPane.leftAnchor="200.0">
		// <font>
		// <Font size="18.0" />
		// </font>
		// </Label>
		Label lblAddress = new Label();
		lblAddress.setText(BaseController.preferredRestaurants.get(count).getAddressPhoneAndEmail());
		lblAddress.setFont(new Font(18.0));
		lblAddress.setWrapText(true);
		lblAddress.setLayoutX(200.0);
		lblAddress.setLayoutY(70.0);
		AnchorPane.setLeftAnchor(lblAddress, 200.0);
		AnchorPane.setBottomAnchor(lblAddress, 20.0);
		pane.getChildren().add(lblAddress);

		// <Hyperlink onAction="#handleViewMenu" text="View Menu"
		// AnchorPane.rightAnchor="100.0" AnchorPane.topAnchor="20.0">
		// <font>
		// <Font size="18.0" />
		// </font>
		// </Hyperlink>
		Hyperlink viewMenuLink = new Hyperlink();
		viewMenuLink.setText("View Menu");
		viewMenuLink.setFont(new Font(18.0));
		viewMenuLink.setLayoutX(1020.0);
		viewMenuLink.setLayoutY(70.0);
		AnchorPane.setRightAnchor(viewMenuLink, 100.0);
		AnchorPane.setTopAnchor(viewMenuLink, 20.0);
		AnchorPane.setLeftAnchor(viewMenuLink, 950.0);
		viewMenuLink.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				BaseController.currentRestaurant = BaseController.preferredRestaurants.get(count);
				log.debug(BaseController.currentRestaurant.getRestaurantId());
				handleItem();
				
			}
		});

		pane.getChildren().add(viewMenuLink);

		Label lblRating = new Label();
		lblRating.setText("Rating: " + BaseController.preferredRestaurants.get(count).getRating());
		lblRating.setFont(new Font(15.0));
		lblRating.setWrapText(true);
		lblRating.setLayoutX(900.0);
		lblRating.setLayoutY(70.0);
		AnchorPane.setRightAnchor(lblRating, 100.0);
		AnchorPane.setTopAnchor(lblRating, 80.0);
		AnchorPane.setLeftAnchor(lblRating, 970.0);
		pane.getChildren().add(lblRating);

		anchorPaneRestList.getChildren().add(pane);
	}

}
