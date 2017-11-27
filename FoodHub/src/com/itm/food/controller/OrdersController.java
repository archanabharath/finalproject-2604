package com.itm.food.controller;

<<<<<<< HEAD
import java.text.SimpleDateFormat;
=======
import java.sql.SQLException;
import java.util.List;
>>>>>>> refs/remotes/origin/feature1

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.itm.food.dao.CustomerOrder;
<<<<<<< HEAD
import com.itm.food.dao.OrderItem;
import com.itm.food.util.CardUtil;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXProgressBar;
=======
>>>>>>> refs/remotes/origin/feature1

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.AnchorPane;
<<<<<<< HEAD
=======
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
>>>>>>> refs/remotes/origin/feature1
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class OrdersController extends BaseController {

	private static final Logger log = Logger.getLogger(OrdersController.class);
	@FXML
	private ScrollPane ordersScrollpane;

	@FXML
	private AnchorPane ordersAnchorPane;

	CustomerOrder ordercustomer = new CustomerOrder();
	List<CustomerOrder> custOrderList ;

	@Override
	void init() {
		super.init();
		loadOrdersList();
		renderOrdersList();
	}

<<<<<<< HEAD
	private void loadOrdersList() {
=======
	public void getOrderCustomerDetails(String orderCustid) {

	}

	/**
	 * Retrieve the list of orders with customer,address,payment,items and
	 * restaurant details that were placed by the customer
	 * 
	 * @param orderCustId
	 */
	public void retrieveOrdersForCustomer(String orderCustId) {

		
>>>>>>> refs/remotes/origin/feature1
		try {
<<<<<<< HEAD
			BaseController.customerOrders = customerOperation
					.displayOrderHistoryOfCustomer(BaseController.authenticatedCustomer.getCustomerID());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
=======
			customerOperation.displayOrderHistoryOfCustomer(orderCustId);
			customerOperation.displayItemsRestaurantsOfAnOrder(orderCustId);

		} catch (SQLException e) {
			log.error(e.getMessage());
>>>>>>> refs/remotes/origin/feature1
		}
	}

	/**
	 * render the view of list of orders
	 * 
	 * @throws Exception
	 */
	private void renderOrdersList() {
		try {
			// Remove all children if any.
			ordersAnchorPane.getChildren().removeAll(ordersAnchorPane.getChildren());
			ordersAnchorPane.setPrefHeight(250.0);
			ordersScrollpane.setVisible(true);

			for (int i = 0; i < BaseController.customerOrders.size(); i++) {
				renderOrder(i);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	private void renderOrder(int index) throws Exception {
		double paneHeightNeeded = index + 1 * 250;
		double currentPaneHeight = ordersAnchorPane.getPrefHeight();
		if (paneHeightNeeded <= currentPaneHeight) {
			ordersAnchorPane.setPrefHeight(currentPaneHeight + 250.0);
		}

		SimpleDateFormat sdtFormat = new SimpleDateFormat("MM-dd-yyyy hh:mm a");
		SimpleDateFormat sdtParse = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.m");
		log.debug(BaseController.customerOrders.get(index).getOrderData().getOrderTime());
		AnchorPane pane = new AnchorPane();
		pane.setLayoutY(index * 250); // INCREMENT THIS 250.0
		pane.setPrefHeight(250.0);
		pane.setPrefWidth(1180.0);
		pane.setStyle("-fx-border-color: lightgrey;");

		// <JFXProgressBar layoutX="68.0" layoutY="30.0" prefHeight="10.0"
		// progress="0.3" AnchorPane.leftAnchor="50.0"
		// AnchorPane.rightAnchor="50.0" AnchorPane.topAnchor="15.0">
		// <effect>
		// <DropShadow />
		// </effect>
		// </JFXProgressBar>
		JFXProgressBar progressBar = new JFXProgressBar();
		progressBar.setLayoutX(68.0);
		progressBar.setLayoutY(30.0);
		progressBar.prefHeight(20.0);
		progressBar.prefWidth(1000.0);
		AnchorPane.setLeftAnchor(progressBar, 50.0);
		AnchorPane.setTopAnchor(progressBar, 15.0);
		AnchorPane.setRightAnchor(progressBar, 15.0);
		AnchorPane.setBottomAnchor(progressBar, 225.0);
		progressBar.setEffect(new DropShadow());
		double progress = 0.0;
		switch (BaseController.customerOrders.get(index).getOrderData().getOrderStatus()) {
		case 1:
			progress = 0.30;
			break;
		case 2:
		case 3:
			progress = 0.66;
			break;
		case 4:
			progress = 1;
			break;
		default:
			progress = 0;
			break;
		}
		progressBar.setProgress(progress);
		pane.getChildren().add(progressBar);

		// <Label layoutX="20.0" layoutY="17.0" prefWidth="150.0" text="Order
		// Placed" textFill="DARKSLATEGRAY" wrapText="true"
		// AnchorPane.leftAnchor="20.0" AnchorPane.rightAnchor="1000.0"
		// AnchorPane.topAnchor="30.0">
		// <font>
		// <Font size="18.0" />
		// </font>
		// </Label>
		Label lblOrderPlaced = new Label();
		lblOrderPlaced.setLayoutX(20.0);
		lblOrderPlaced.setLayoutY(17.0);
		lblOrderPlaced.prefHeight(20.0);
		lblOrderPlaced.prefWidth(150.0);

		lblOrderPlaced.setText("Order Placed \r\n"
				+ (StringUtils.isEmpty(BaseController.customerOrders.get(index).getOrderData().getOrderTime()) ? ""
						: sdtFormat.format(sdtParse
								.parse(BaseController.customerOrders.get(index).getOrderData().getOrderTime()))));
		lblOrderPlaced.setWrapText(true);
		lblOrderPlaced.setTextFill(Color.DARKSLATEGRAY);
		lblOrderPlaced.setFont(new Font(15.0));
		AnchorPane.setLeftAnchor(lblOrderPlaced, 20.0);
		AnchorPane.setTopAnchor(lblOrderPlaced, 30.0);
		AnchorPane.setRightAnchor(lblOrderPlaced, 1000.0);
		pane.getChildren().add(lblOrderPlaced);

		// <Label layoutX="469.0" layoutY="38.0" text="Preparing"
		// textFill="DARKSLATEGRAY" AnchorPane.leftAnchor="325.0"
		// AnchorPane.rightAnchor="725.0" AnchorPane.topAnchor="30.0">
		// <font>
		// <Font size="18.0" />
		// </font>
		// </Label>
		Label lblPreparing = new Label();
		lblPreparing.setLayoutX(469.0);
		lblPreparing.setLayoutY(38.0);
		lblPreparing.prefHeight(20.0);
		lblPreparing.prefWidth(150.0);
		lblPreparing.setText("Preparing");
		lblPreparing.setWrapText(true);
		lblPreparing.setTextFill(Color.DARKSLATEGRAY);
		lblPreparing.setFont(new Font(15.0));
		AnchorPane.setLeftAnchor(lblPreparing, 325.0);
		AnchorPane.setTopAnchor(lblPreparing, 30.0);
		AnchorPane.setRightAnchor(lblPreparing, 725.0);
		pane.getChildren().add(lblPreparing);

		// <Label layoutX="459.0" layoutY="28.0" text="[DeliveryMode]"
		// textFill="DARKSLATEGRAY" AnchorPane.leftAnchor="700.0"
		// AnchorPane.rightAnchor="300.0" AnchorPane.topAnchor="30.0">
		// <font>
		// <Font size="18.0" />
		// </font>
		// </Label>
		Label lblDeliveryMode = new Label();
		lblDeliveryMode.setLayoutX(459.0);
		lblDeliveryMode.setLayoutY(28.0);
		lblDeliveryMode.prefHeight(20.0);
		lblDeliveryMode.prefWidth(150.0);
		if (BaseController.customerOrders.get(index).getOrderData().getDeliveryMode() == 1) {
			lblDeliveryMode.setText("Ready for Pickup");
		} else {
			lblDeliveryMode.setText("Out for Delivery");
		}
		lblDeliveryMode.setWrapText(true);
		lblDeliveryMode.setTextFill(Color.DARKSLATEGRAY);
		lblDeliveryMode.setFont(new Font(15.0));
		AnchorPane.setLeftAnchor(lblDeliveryMode, 700.0);
		AnchorPane.setTopAnchor(lblDeliveryMode, 30.0);
		AnchorPane.setRightAnchor(lblDeliveryMode, 300.0);
		pane.getChildren().add(lblDeliveryMode);

		// <Label layoutX="1035.0" layoutY="41.0" text="Completed"
		// textFill="DARKSLATEGRAY" wrapText="true"
		// AnchorPane.leftAnchor="1060.0" AnchorPane.rightAnchor="20.0"
		// AnchorPane.topAnchor="30.0">
		// <font>
		// <Font size="18.0" />
		// </font>
		// </Label>
		Label lblCompleted = new Label();
		lblCompleted.setLayoutX(1035.0);
		lblCompleted.setLayoutY(41.0);
		lblCompleted.prefHeight(20.0);
		lblCompleted.prefWidth(150.0);
		lblCompleted.setText("Completed \r\n"
				+ (StringUtils.isEmpty(BaseController.customerOrders.get(index).getOrderData().getOrderFulfilment())
						? ""
						: sdtFormat.format(sdtParse
								.parse(BaseController.customerOrders.get(index).getOrderData().getOrderFulfilment()))));
		lblCompleted.setWrapText(true);
		lblCompleted.setTextFill(Color.DARKSLATEGRAY);
		lblCompleted.setFont(new Font(15.0));
		AnchorPane.setLeftAnchor(lblCompleted, 1060.0);
		AnchorPane.setTopAnchor(lblCompleted, 30.0);
		AnchorPane.setRightAnchor(lblCompleted, 30.0);
		pane.getChildren().add(lblCompleted);

		// <Label layoutX="1001.0" layoutY="88.0" text="Amount Paid"
		// textFill="CRIMSON" AnchorPane.rightAnchor="60.0"
		// AnchorPane.topAnchor="80.0">
		// <font>
		// <Font size="24.0" />
		// </font>
		// </Label>
		Label lblAmountPaid = new Label();
		lblAmountPaid.setLayoutX(1001.0);
		lblAmountPaid.setLayoutY(88.0);
		lblAmountPaid.prefHeight(30.0);
		lblAmountPaid.prefWidth(150.0);
		lblAmountPaid.setText("Amount Paid");
		lblAmountPaid.setWrapText(true);
		lblAmountPaid.setTextFill(Color.CRIMSON);
		lblAmountPaid.setFont(new Font(24.0));
		AnchorPane.setLeftAnchor(lblAmountPaid, 975.0);
		AnchorPane.setTopAnchor(lblAmountPaid, 100.0);
		AnchorPane.setRightAnchor(lblAmountPaid, 40.0);
		pane.getChildren().add(lblAmountPaid);

		// <Label layoutX="981.0" layoutY="129.0" prefHeight="30.0"
		// prefWidth="150.0" text="\$0.00" textFill="CRIMSON"
		// AnchorPane.leftAnchor="1020.0" AnchorPane.rightAnchor="90.0"
		// AnchorPane.topAnchor="130.0">
		// <font>
		// <Font size="24.0" />
		// </font>
		// </Label>
		Label lblPaid = new Label();
		lblPaid.setLayoutX(981.0);
		lblPaid.setLayoutY(129.0);
		lblPaid.prefHeight(30.0);
		lblPaid.prefWidth(150.0);
		lblPaid.setText("$" + BaseController.customerOrders.get(index).getOrderData().getTotalPayment());
		lblPaid.setWrapText(true);
		lblPaid.setTextFill(Color.CRIMSON);
		lblPaid.setFont(new Font(24.0));
		AnchorPane.setLeftAnchor(lblPaid, 1020.0);
		AnchorPane.setTopAnchor(lblPaid, 150.0);
		AnchorPane.setRightAnchor(lblPaid, 60.0);
		pane.getChildren().add(lblPaid);

		// <JFXListView layoutX="50.0" layoutY="70.0" prefHeight="160.0"
		// prefWidth="900.0" showTooltip="true" AnchorPane.bottomAnchor="15.0"
		// AnchorPane.leftAnchor="50.0" AnchorPane.rightAnchor="230.0"
		// AnchorPane.topAnchor="70.0" />
		JFXListView<OrderItem> orderItemlistView = new JFXListView<OrderItem>();
		orderItemlistView.setLayoutX(50.0);
		orderItemlistView.setLayoutY(70.0);
		orderItemlistView.prefHeight(160.0);
		orderItemlistView.prefWidth(500.0);
		orderItemlistView.setShowTooltip(true);
		AnchorPane.setLeftAnchor(orderItemlistView, 50.0);
		AnchorPane.setTopAnchor(orderItemlistView, 100.0);
		AnchorPane.setRightAnchor(orderItemlistView, 500.0);
		AnchorPane.setBottomAnchor(orderItemlistView, 15.0);
		pane.getChildren().add(orderItemlistView);

		ObservableList<OrderItem> observableList = FXCollections
				.observableArrayList(BaseController.customerOrders.get(index).getOrderItemRestaurantList());
		orderItemlistView.setItems(observableList);

		if (BaseController.customerOrders.get(index).getOrderData().getDeliveryMode() == 2) {
			Label lblAddress = new Label();
			lblAddress.setLayoutX(706.0);
			lblAddress.setLayoutY(87.0);
			lblAddress.prefHeight(100.0);
			lblAddress.prefWidth(150.0);
			StringBuilder addressBuilder = new StringBuilder();
			addressBuilder.append(BaseController.customerOrders.get(index).getAddressData().getFname() + " "
					+ BaseController.customerOrders.get(index).getAddressData().getLname());
			addressBuilder.append("\r\n");
			addressBuilder.append(BaseController.customerOrders.get(index).getAddressData().getAddr1());
			addressBuilder.append("\r\n");
			addressBuilder.append(BaseController.customerOrders.get(index).getAddressData().getAddr2());
			addressBuilder.append("\r\n");
			addressBuilder.append(BaseController.customerOrders.get(index).getAddressData().getCity() + ", "
					+ BaseController.customerOrders.get(index).getAddressData().getPincode());
			lblAddress.setText(addressBuilder.toString());
			lblAddress.setWrapText(true);
			lblAddress.setTextFill(Color.STEELBLUE);
			lblAddress.setFont(new Font(13.0));
			AnchorPane.setLeftAnchor(lblAddress, 700.0);
			AnchorPane.setTopAnchor(lblAddress, 100.0);
			AnchorPane.setRightAnchor(lblAddress, 300.0);
			pane.getChildren().add(lblAddress);
		}

		Label lblCard = new Label();
		lblCard.setLayoutX(706.0);
		lblCard.setLayoutY(199.0);
		lblCard.prefHeight(30.0);
		lblCard.prefWidth(300.0);
		StringBuilder builder = new StringBuilder();
		builder.append("Paid through:");
		builder.append("\r\n");
		builder.append(BaseController.customerOrders.get(index).getPaymentData().getNameOnCard());
		builder.append("\r\n");
		builder.append(CardUtil
				.maskCardNo(String.valueOf(BaseController.customerOrders.get(index).getPaymentData().getCardNo())));
		lblCard.setText(builder.toString());
		lblCard.setWrapText(true);
		lblCard.setTextFill(Color.STEELBLUE);
		lblCard.setFont(new Font(13.0));
		AnchorPane.setLeftAnchor(lblCard, 700.0);
		AnchorPane.setBottomAnchor(lblCard, 15.0);
		AnchorPane.setRightAnchor(lblCard, 20.0);
		pane.getChildren().add(lblCard);

		ordersAnchorPane.getChildren().add(pane);

	}

}
