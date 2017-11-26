package com.itm.food.controller;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.itm.food.dao.Basket;
import com.itm.food.dao.Customer;
import com.itm.food.dao.Restaurant;
import com.itm.food.dao.operation.CustomerOperations;
import com.itm.food.dao.operation.PaymentOperations;
import com.itm.food.model.db.MySQLDBConnector;
import com.jfoenix.controls.JFXButton;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public abstract class BaseController {

	private static final Logger log = Logger.getLogger(BaseController.class);

	public static final int MAIN_SCREEN_WIDTH = 1500;
	public static final int MAIN_SCREEN_HEIGHT = 1000;

	public static final int LOGIN_SCREEN_WIDTH = 900;
	public static final int LOGIN_SCREEN_HEIGHT = 500;

	public static final int SIGNUP_SCREEN_WIDTH = 900;
	public static final int SIGNUP_SCREEN_HEIGHT = 900;

	// Operations
	CustomerOperations customerOperation = new CustomerOperations();
	PaymentOperations paymentOperation = new PaymentOperations();

	// Data access objects for logged in member
	public static Customer authenticatedCustomer = new Customer();
	public static ArrayList<Restaurant> preferredRestaurants = new ArrayList<Restaurant>();
	public static Restaurant currentRestaurant = new Restaurant();
	public static Basket foodBasket = new Basket();

	@FXML
	private ImageView imgExit;

	@FXML
	private Label txtFullname;

	@FXML
	protected JFXButton btnBasket;

	@FXML
	void handleApplicationClose(MouseEvent event) {
		try {
			MySQLDBConnector connector = new MySQLDBConnector();
			connector.closeConnection();
		} catch (Exception ex) {
			log.error("Unable to close DB connection:" + ex.getMessage());
		}
		log.debug("Closing app");
		System.exit(0);
	}

	@FXML
	void handeKeyCards(KeyEvent event) {
		handleCards();
	}

	@FXML
	void handleKeyAddress(KeyEvent event) {
		handleAddress();
	}

	@FXML
	void handleKeyBasket(KeyEvent event) {
		handleBasket();
	}

	@FXML
	void handleKeyHome(KeyEvent event) {
		handleHome();
	}

	@FXML
	void handleKeyOrders(KeyEvent event) {
		handleOrders();
	}

	@FXML
	void handleKeyProfile(KeyEvent event) {
		handleProfile();
	}

	@FXML
	void handleKeySearch(KeyEvent event) {
		handleSearch();
	}

	@FXML
	void handleKeySignout(KeyEvent event) {
		handleSignout();
	}

	@FXML
	void handleMouseAddress(MouseEvent event) {
		handleAddress();
	}

	@FXML
	void handleMouseBasket(MouseEvent event) {
		handleBasket();
	}

	@FXML
	void handleMouseCards(MouseEvent event) {
		handleCards();
	}

	@FXML
	void handleMouseHome(MouseEvent event) {
		handleHome();
	}

	@FXML
	void handleMouseOrders(MouseEvent event) {
		handleOrders();
	}

	@FXML
	void handleMouseProfile(MouseEvent event) {
		handleProfile();
	}

	@FXML
	void handleMouseSearch(MouseEvent event) {
		handleSearch();
	}

	@FXML
	void handleMouseSignout(MouseEvent event) {
		handleSignout();
	}
	
	void appInit() {
		try {
		MySQLDBConnector connector = new MySQLDBConnector();
		connector.getConnection();
		} catch (Exception e) {
			log.error(e.getMessage());
			// Add a pop up message for DB connectivity 
		}

	}

	/**
	 * Base init() method. Implement all the logic is common for main screen
	 */
	void init() {
		try {
			if (null != this.txtFullname) {
				this.txtFullname.setText(BaseController.authenticatedCustomer.getFirstName() + " "
						+ BaseController.authenticatedCustomer.getLastName());
			}
			if (null != this.btnBasket && (null == BaseController.foodBasket.getOrderItems()
					|| BaseController.foodBasket.getOrderItems().size() == 0)) {
				btnBasket.setDisable(true);
			} else {
				btnBasket.setDisable(false);
			}
		} catch (Exception ex) {
			log.error(ex.getMessage());
		}

	}

	void handleHome() {
		this.launchScene("Home Screen", "../views/Home.fxml", BaseController.MAIN_SCREEN_WIDTH,
				BaseController.MAIN_SCREEN_HEIGHT);
	}

	void handleSearch() {
		this.launchScene("Search Screen", "../views/Search.fxml", BaseController.MAIN_SCREEN_WIDTH,
				BaseController.MAIN_SCREEN_HEIGHT);
	}

	void handleOrders() {
		this.launchScene("Orders Screen", "../views/Orders.fxml", BaseController.MAIN_SCREEN_WIDTH,
				BaseController.MAIN_SCREEN_HEIGHT);
	}

	void handleBasket() {
		this.launchScene("Basket Screen", "../views/Basket.fxml", BaseController.MAIN_SCREEN_WIDTH,
				BaseController.MAIN_SCREEN_HEIGHT);
	}

	void handleAddress() {
		this.launchScene("Address Screen", "../views/Address.fxml", BaseController.MAIN_SCREEN_WIDTH,
				BaseController.MAIN_SCREEN_HEIGHT);
	}

	void handleCards() {
		this.launchScene("Cards Screen", "../views/Cards.fxml", BaseController.MAIN_SCREEN_WIDTH,
				BaseController.MAIN_SCREEN_HEIGHT);
	}

	void handleProfile() {
		this.launchScene("Profile Screen", "../views/Profile.fxml", BaseController.MAIN_SCREEN_WIDTH,
				BaseController.MAIN_SCREEN_HEIGHT);
	}

	void handleSignout() {
		cleanupSession();
		this.launchScene("Login Screen", "../views/Login.fxml", BaseController.LOGIN_SCREEN_WIDTH,
				BaseController.LOGIN_SCREEN_HEIGHT);
		log.debug("User successfully logged out.");
	}

	/**
	 * Clean up the data model used during user login.
	 */
	void cleanupSession() {
		log.debug("Session cleaned.");
		BaseController.authenticatedCustomer = new Customer();
		BaseController.preferredRestaurants = new ArrayList<Restaurant>();
		BaseController.currentRestaurant = null;
		BaseController.foodBasket = new Basket();
	}

	/**
	 * Method to launch the scene in the primary stage
	 * 
	 * @param sceneName
	 * @param resource
	 * @param width
	 * @param height
	 */
	public void launchScene(String sceneName, String resource, int width, int height) {
		try {
			log.debug(sceneName);
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource(resource));
			AnchorPane root = loader.load();
			BaseController controller = loader.getController();
			controller.init();
			Scene scene = new Scene(root, width, height);
			scene.getStylesheets().add(getClass().getResource("../application.css").toExternalForm());
			ParentController.mainStage.setScene(scene);
			ParentController.mainStage.setResizable(false);
			ParentController.mainStage.centerOnScreen();
			ParentController.mainStage.show();
		} catch (Exception e) {
			log.error("Error in launching scene: " + e.getMessage());
		}
	}

	public int isItemPresent(String itemsId) {
		for (int i = 0; i < BaseController.foodBasket.getOrderItems().size(); i++) {
			if (BaseController.foodBasket.getOrderItems().get(i).getItemId().equals(itemsId)) {
				return i;
			}
		}
		return -1;
	}

}
