package com.itm.food.controller.admin;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/** Controls the main application screen */
public class MainViewController extends AdminManager {

	// Toolbar buttons for navigation
	@FXML
	private Button logoutButton;
	@FXML
	private Button profileButton;
	@FXML
	private Button homeButton;
	@FXML
	private Button restaurantButton;
	@FXML
	private Button menuButton;
	@FXML
	private Button driverButton;
	@FXML
	private Button reviewButton;

	// Labels to display dynamic statistics to admin dashboard
	@FXML
	private Label customers, restaurants, drivers, orders, orderFulfilled, orderToday;

	public void initSessionID(final AdminManager loginManager, String sessionID) {

		logoutButton.setOnAction(e -> loginManager.logout());
		homeButton.setOnAction(e -> loginManager.showMainView(sessionID));
		restaurantButton.setOnAction(e -> loginManager.showRestaurantsView(sessionID));
		menuButton.setOnAction(e -> loginManager.showMenuView(sessionID));
		driverButton.setOnAction(e -> loginManager.showDriversView(sessionID));
		reviewButton.setOnAction(e -> loginManager.showReviewsView(sessionID));
		profileButton.setOnAction(e -> loginManager.showProfile(sessionID));

		// CSS for Toolbar buttons
		logoutButton.setStyle("-fx-background-color: linear-gradient(lightgray,gray);");
		profileButton.setStyle("-fx-background-color: linear-gradient(lightgray,gray);");
		homeButton.setStyle("-fx-background-color: linear-gradient(lightgray,gray);");
		restaurantButton.setStyle("-fx-background-color: linear-gradient(lightgray,gray);");
		menuButton.setStyle("-fx-background-color: linear-gradient(lightgray,gray);");
		driverButton.setStyle("-fx-background-color: linear-gradient(lightgray,gray);");
		reviewButton.setStyle("-fx-background-color: linear-gradient(lightgray,gray);");

		// retrieving current statistics from DB and displaying it on labels in
		// dashboard
		try {
			customers.setText(Integer.toString(customerDB.getCustomerCount()));
			restaurants.setText(Integer.toString(restaurantDB.getRestaurantCount()));
			drivers.setText(Integer.toString(driverDB.getDriverCount()));
			orders.setText(Integer.toString(orderDB.getOrderCount()));
			orderFulfilled.setText(Integer.toString(orderDB.getFullFilledOrderCount()));
			orderToday.setText(Integer.toString(orderDB.getTodayOrderCount()));
		} catch (Exception e) {
			e.getMessage();
		}
	}

}