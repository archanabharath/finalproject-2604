package com.itm.food.controller.admin;

import java.sql.ResultSet;

import org.apache.log4j.Logger;

import com.itm.food.controller.admin.components.DetailViewTable;
import com.itm.food.controller.admin.components.Dialogs;
import com.itm.food.dao.Customer;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class OrdersController extends AdminManager {
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
	@FXML
	private MenuButton actionButton;

	@FXML
	private MenuItem orderIDButton, custIDButton;
	@FXML
	private ScrollPane allOrdersView, orderIdView, custIdView;
	@FXML
	private Button orderButton, custButton;
	@FXML
	private TextField orderID, custID;
	@FXML
	private AnchorPane orderListViewPane, orderIdViewPane, custIdViewPane;

	private static final Logger log = Logger.getLogger(OrdersController.class);

	@SuppressWarnings("rawtypes")
	private TableView tableview;

	@SuppressWarnings("rawtypes")
	public void initManager(final AdminManager loginManager, String sessionID) {

		// toolbar button navigation calls
		logoutButton.setOnAction(e -> loginManager.logout());
		homeButton.setOnAction(e -> loginManager.showMainView(sessionID));
		restaurantButton.setOnAction(e -> loginManager.showRestaurantsView(sessionID));
		menuButton.setOnAction(e -> loginManager.showMenuView(sessionID));
		driverButton.setOnAction(e -> loginManager.showDriversView(sessionID));
		reviewButton.setOnAction(e -> loginManager.showReviewsView(sessionID));
		profileButton.setOnAction(e -> loginManager.showProfile(sessionID));

		// CSS for toolbar buttons
		logoutButton.setStyle("-fx-background-color: linear-gradient(lightgray,gray);");
		profileButton.setStyle("-fx-background-color: linear-gradient(lightgray,gray);");
		homeButton.setStyle("-fx-background-color: linear-gradient(lightgray,gray);");
		restaurantButton.setStyle("-fx-background-color: linear-gradient(lightgray,gray);");
		menuButton.setStyle("-fx-background-color: linear-gradient(lightgray,gray);");
		driverButton.setStyle("-fx-background-color: linear-gradient(lightgray,gray);");
		reviewButton.setStyle("-fx-background-color: linear-gradient(lightgray,gray);");

		orderIdViewPane.setVisible(false);
		custIdViewPane.setVisible(false);
		orderListViewPane.setVisible(true);
		tableview = new TableView();

		// Order list view
		ResultSet rs = null;
		try {
			rs = orderDB.getOrderList();
			tableview = new DetailViewTable().viewList(rs);
			allOrdersView.setContent(tableview);
			tableview.prefHeightProperty().bind(allOrdersView.heightProperty());
			tableview.prefWidthProperty().bind(allOrdersView.widthProperty());
			rs.close();
		} catch (Exception e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

		// Order id details view by order id
		orderIDButton.setOnAction(e -> {

			log.debug("Add Restaurant Menu Processing");
			orderListViewPane.setVisible(false);
			orderIdViewPane.toFront();
			orderIdViewPane.setVisible(true);
			orderIdView.setVisible(false);

			// Order ID search on button click
			orderButton.setOnAction(ev -> {
				if (orderID.getText().equals("") || orderID.getText().matches(".*[^0-9].*"))
					new Dialogs().showWarning("Please enter a valid order ID");
				else {
					int orderid = Integer.parseInt(orderID.getText());
					log.debug("Searching for order" + orderid);
					ResultSet rs1;
					try {
						rs1 = orderDB.validateOrder(orderid);
						if (rs1.next()) {
							if (rs1.getInt(1) != 0) {
								orderIdView.setVisible(true);
								ResultSet rs2 = null;
								rs2 = orderDB.getOrderDetails(orderid);
								tableview = new DetailViewTable().viewList(rs2);
								orderIdView.setContent(tableview);
								tableview.prefHeightProperty().bind(orderIdView.heightProperty());
								tableview.prefWidthProperty().bind(orderIdView.widthProperty());
								rs2.close();
								rs1.close();
							} else
								new Dialogs().showError(orderid, "ID Order Not Found! Please verify order ID.");
						}
						rs1.close();
					} catch (Exception ex) {
						ex.getMessage();
					}
				}
			});
		});

		// Order list view by customer ID
		custIDButton.setOnAction(e -> {

			orderListViewPane.setVisible(false);
			orderIdViewPane.setVisible(false);
			custIdViewPane.setVisible(true);
			custIdView.setVisible(false);

			// search for Customer ID
			custButton.setOnAction(ev -> {
				if (custID.getText().equals(""))
					new Dialogs().showWarning("Please enter a valid ID");
				else {
					int custid = Integer.parseInt(custID.getText());
					log.debug("Searching for customer" + custID);
					try {
						Customer customer = customerDB.find(custid);

						if (customer.getCustomerID() != 0) {
							custIdView.setVisible(true);
							ResultSet rs2 = null;
							rs2 = orderDB.getOrderDetailsofCustomer(custid);
							tableview = new DetailViewTable().viewList(rs2);
							custIdView.setContent(tableview);
							tableview.prefHeightProperty().bind(custIdView.heightProperty());
							tableview.prefWidthProperty().bind(custIdView.widthProperty());
							rs2.close();
						} else {
							new Dialogs().showError(custid,
									"ID Customer Not Found! Please verify customer ID entered.");
						}
					} catch (Exception ex) {
						log.error(ex.getMessage(), ex);
					}
				}
			});
		});
	}

}