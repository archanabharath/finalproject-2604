package com.itm.food.controller;

import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;

import com.itm.food.dao.CustomerOrder;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class OrdersController extends BaseController {

	private static final Logger log = Logger.getLogger(OrdersController.class);
	@FXML
	private ScrollPane ordersscrollpane;

	@FXML
	private AnchorPane ordersAnchorPane;

	CustomerOrder ordercustomer = new CustomerOrder();
	List<CustomerOrder> custOrderList ;

	@Override
	void init() {
		super.init();
		// retrieveOrdersForCustomer(BaseController.authenticatedCustomer.getCustomerID());
		renderOrdersList();
	}

	public void getOrderCustomerDetails(String orderCustid) {

	}

	/**
	 * Retrieve the list of orders with customer,address,payment,items and
	 * restaurant details that were placed by the customer
	 * 
	 * @param orderCustId
	 */
	public void retrieveOrdersForCustomer(String orderCustId) {

		
		try {
			customerOperation.displayOrderHistoryOfCustomer(orderCustId);
			customerOperation.displayItemsRestaurantsOfAnOrder(orderCustId);

		} catch (SQLException e) {
			log.error(e.getMessage());
		}

	}

	/**
	 * render the view of list of orders
	 */
	public void renderOrdersList() {
		// double itemsPaneHeight =
		// ((ordercustomer.getOrderItemRestaurantDetails().size())* 50.0)+100.0;
		double itemsPaneHeight = ((5) * 50.0) + 100.0;
		ordersAnchorPane.getChildren().removeAll(ordersAnchorPane.getChildren());
		ordersAnchorPane.setPrefHeight(itemsPaneHeight);
		ordersscrollpane.setPrefWidth(1200.0);
		ordersscrollpane.setVisible(true);
		// for (int i = 0; i < ordercustomer.getOrderCustomerDetails().size(); i++) {
		for (int i = 0; i < 3; i++) {
			HBox ordercustomerhbox = new HBox();
			VBox ordercustomervbox = new VBox();
			renderEachOrder(i, itemsPaneHeight, ordercustomerhbox,ordercustomervbox);
		}

	}

	/**
	 * render the view of every order within the list
	 * 
	 * @param orderIndex
	 * @param itemsPaneHeight
	 * @param ordercustomerhbox
	 * @param ordercustomervbox 
	 */
	private void renderEachOrder(int orderIndex, double itemsPaneHeight, HBox ordercustomerhbox, VBox ordercustomervbox) {

		double requiredAPaneHeight = orderIndex * itemsPaneHeight;
		double currentAPaneHeight = ordersAnchorPane.getPrefHeight();
		if (requiredAPaneHeight <= currentAPaneHeight) {
			ordersAnchorPane.setPrefHeight(currentAPaneHeight + itemsPaneHeight);
		}

		// Add a inner pane container

		// design the pane for holding every card
		AnchorPane individualOrderPane = new AnchorPane();
		individualOrderPane.setLayoutY(orderIndex * itemsPaneHeight); // TODO INCREMENT THIS 150.0
		individualOrderPane.setPrefHeight(itemsPaneHeight);
		individualOrderPane.setPrefWidth(1200.0);
		individualOrderPane.setStyle("-fx-border-color: teal;");

		// add order information to the inner individual panes

		ordercustomerhbox.setLayoutY(orderIndex * 100);
		ordercustomerhbox.setPrefHeight(100);
		ordercustomerhbox.setPrefWidth(1200.0);
		ordercustomerhbox.setSpacing(50.0);

		

		// add orderid
		Label lblOrderId = new Label();
		lblOrderId.setLayoutX(200.0);
		lblOrderId.setLayoutY(20.0);
		lblOrderId.prefHeight(30.0);
		lblOrderId.prefWidth(1200.0);
		// lblOrderId.setText(ordercustomer.getOrderCustomerDetails().get(orderIndex).getOrderId());
		lblOrderId.setText("Order Id: 12345111111");
		lblOrderId.setAlignment(Pos.BASELINE_LEFT);
		lblOrderId.setTextFill(Color.CRIMSON);
		lblOrderId.setWrapText(true);
		lblOrderId.setFont(new Font(24.0));
		AnchorPane.setLeftAnchor(lblOrderId, 10.0);
		AnchorPane.setTopAnchor(lblOrderId, 20.0);
		individualOrderPane.getChildren().add(lblOrderId);

		// add order placed date and time

		Label lblOrderTime = new Label();
		lblOrderTime.setLayoutX(200.0);
		lblOrderTime.setLayoutY(20.0);
		lblOrderTime.prefHeight(30.0);
		lblOrderTime.prefWidth(1200.0);
		// lblOrderTime.setText(ordercustomer.getOrderCustomerDetails().get(orderIndex).getOrderTime());
		lblOrderTime.setText("Order Placed on: 11/25/2017");
		lblOrderTime.setAlignment(Pos.BASELINE_LEFT);
		lblOrderTime.setTextFill(Color.CRIMSON);
		lblOrderTime.setWrapText(true);
		lblOrderTime.setFont(new Font(24.0));
		AnchorPane.setRightAnchor(lblOrderTime, 10.0);
		AnchorPane.setTopAnchor(lblOrderTime, 30.0);
		individualOrderPane.getChildren().add(lblOrderTime);

		ordercustomerhbox.setStyle("-fx-border-color: red;");
		ordercustomerhbox.setMaxHeight(150.0);
		
		ordercustomervbox.setStyle("-fx-border-color: green;");

		//ordercustomerhbox.getChildren().addAll(lblOrderId, lblOrderTime);
		// adding the items and restaurants details
		// for(int j = 0; j < ordercustomer.getOrderItemRestaurantDetails().size(); j
		// ++) {

		for (int j = 0; j < 5; j++) {
			AnchorPane itemsPane = new AnchorPane();
			itemsPane = displayItemsAndRestaurantsOfOrders(j, itemsPane);
			individualOrderPane.getChildren().add(itemsPane);

		}

		//ordercustomervbox.getChildren().addAll(ordercustomerhbox, individualOrderPane);
		ordersAnchorPane.getChildren().addAll(individualOrderPane);

	}

	public AnchorPane displayItemsAndRestaurantsOfOrders(int itemIndex, AnchorPane itemsPane) {

		itemsPane.setLayoutY(itemIndex * 50.0); // TODO INCREMENT THIS 100.0
		itemsPane.setPrefHeight(50.0);
		itemsPane.setPrefWidth(200.0);
		// itemsPane.setStyle("-fx-border-color: blue;");

		// adding items name to the order details
		Label lblItemName = new Label();
		lblItemName.setLayoutX(200.0);
		lblItemName.setLayoutY(20.0);
		lblItemName.prefHeight(10.0);
		lblItemName.prefWidth(200.0);
		// lblItemName.setText(ordercustomer.getOrderItemRestaurantDetails().get(itemIndex).getItemName());
		lblItemName.setText("item " + itemIndex);

		lblItemName.setAlignment(Pos.BASELINE_LEFT);
		lblItemName.setTextFill(Color.CRIMSON);
		lblItemName.setWrapText(true);
		lblItemName.setFont(new Font(14.0));
		AnchorPane.setLeftAnchor(lblItemName, 10.0);
		AnchorPane.setTopAnchor(lblItemName, 60.0);
		itemsPane.getChildren().add(lblItemName);

		// add order placed date and time

		Label lblItemQuantity = new Label();
		lblItemQuantity.setLayoutX(200.0);
		lblItemQuantity.setLayoutY(20.0);
		lblItemQuantity.prefHeight(10.0);
		lblItemQuantity.prefWidth(200.0);
		// lblItemQuantity.setText(String.valueOf(ordercustomer.getOrderItemRestaurantDetails().get(itemIndex).getItemQuantity()));
		lblItemQuantity.setText(String.valueOf(+itemIndex));
		lblItemQuantity.setAlignment(Pos.BASELINE_LEFT);
		lblItemQuantity.setTextFill(Color.CRIMSON);
		lblItemQuantity.setWrapText(true);
		lblItemQuantity.setFont(new Font(14.0));
		AnchorPane.setLeftAnchor(lblItemQuantity, 60.0);
		AnchorPane.setTopAnchor(lblItemQuantity, 60.0);
		itemsPane.getChildren().add(lblItemQuantity);

		Label lblRestaurantName = new Label();
		lblRestaurantName.setLayoutX(200.0);
		lblRestaurantName.setLayoutY(20.0);
		lblRestaurantName.prefHeight(10.0);
		lblRestaurantName.prefWidth(200.0);
		// lblRestaurantName.setText(ordercustomer.getOrderItemRestaurantDetails().get(itemIndex).getRestaurantName());
		lblRestaurantName.setText("Restaurant name");
		lblRestaurantName.setAlignment(Pos.BASELINE_LEFT);
		lblRestaurantName.setTextFill(Color.BLUEVIOLET);
		lblRestaurantName.setWrapText(true);
		lblRestaurantName.setFont(new Font(14.0));
		AnchorPane.setLeftAnchor(lblRestaurantName, 110.0);
		AnchorPane.setTopAnchor(lblRestaurantName, 60.0);
		itemsPane.getChildren().add(lblRestaurantName);

		return itemsPane;

	}

}
