package com.itm.food.controller.admin;

import java.io.IOException;

import org.apache.log4j.Logger;

import com.itm.food.controller.BaseController;
import com.itm.food.controller.LoginController;
import com.itm.food.controller.ParentController;
import com.itm.food.dao.Admin;
import com.itm.food.model.AdminDB;
import com.itm.food.model.CustomerDB;
import com.itm.food.model.DriverDB;
import com.itm.food.model.ItemsDB;
import com.itm.food.model.OrderDB;
import com.itm.food.model.RestaurantDB;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

/** Manages control flow for logins */
public class AdminManager {

	private static final Logger log = Logger.getLogger(AdminManager.class);

	private Scene scene;
	private String sessionId;
	public Admin admin;

	public AdminDB adminDB = new AdminDB();
	public DriverDB driverDB = new DriverDB();
	public CustomerDB customerDB = new CustomerDB();
	public RestaurantDB restaurantDB = new RestaurantDB();
	public OrderDB orderDB = new OrderDB();
	public ItemsDB itemsDB = new ItemsDB();

	public void appInit(Scene scene) {
		this.scene = scene;
	}

	public void initManager(final AdminManager adminManager, String sessionID) {
	}

	/**
	 * @return the sessionId
	 */
	public String getSessionId() {
		return sessionId;
	}

	/**
	 * @param sessionId
	 *            the sessionId to set
	 */
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	/**
	 * Callback method invoked to notify that a user has been authenticated.
	 * Will show the application Home screen for admin.
	 */
	public void authenticated(String sessionID, Admin admin) {
		this.admin = admin;
		showMainView(sessionID);
	}

	/**
	 * Callback method invoked to notify that a user has logged out of the main
	 * application. Will show the login application screen.
	 */
	public void logout() {
		showLoginScreen();
	}

	// Method to load Login page
	public void showLoginScreen() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/itm/food/views/Parentlogin.fxml"));
			scene = new Scene((Parent) loader.load(), BaseController.PARENT_LOGIN_SCREEN_WIDTH,
					BaseController.PARENT_LOGIN_SCREEN_HEIGHT);
			LoginController controller = loader.<LoginController>getController();
			controller.init();
			scene.getStylesheets().add(getClass().getResource("/com/itm/food/application.css").toExternalForm());
			ParentController.mainStage.setScene(scene);
			ParentController.mainStage.setResizable(false);
			ParentController.mainStage.centerOnScreen();
			ParentController.mainStage.show();
		} catch (IOException ex) {
			log.error(ex.getMessage(), ex);
		} catch (NullPointerException n) {
			log.error(n.getMessage(), n);
		}
	}

	// Method to load Home page
	public void showMainView(String sessionID) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/itm/food/views/admin/mainview.fxml"));
			scene.setRoot((Parent) loader.load());
			MainViewController controller = loader.<MainViewController>getController();
			controller.initSessionID(this, sessionID);
			scene.getStylesheets().add(getClass().getResource("/com/itm/food/application.css").toExternalForm());
			ParentController.mainStage.setScene(scene);
			ParentController.mainStage.setResizable(false);
			ParentController.mainStage.centerOnScreen();
			ParentController.mainStage.show();
		} catch (IOException ex) {
			log.error(ex.getMessage(), ex);
		} catch (NullPointerException n) {
			log.error(n.getMessage(), n);
		}
	}

	// Method to load Restaurants page
	public void showRestaurantsView(String sessionID) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/itm/food/views/admin/restaurants.fxml"));
			scene.setRoot((Parent) loader.load());
			RestaurantsController controller = loader.<RestaurantsController>getController();
			controller.initManager(this, sessionID);

		} catch (IOException ex) {
			log.error(ex.getMessage(), ex);
		} catch (NullPointerException n) {
			log.error(n.getMessage(), n);
		}
	}

	// Method to load Menu page
	public void showMenuView(String sessionID) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/itm/food/views/admin/menu.fxml"));
			scene.setRoot((Parent) loader.load());
			MenuItemController controller = loader.<MenuItemController>getController();
			controller.initManager(this, sessionID);
		} catch (IOException ex) {
			log.error(ex.getMessage(), ex);
		} catch (NullPointerException n) {
			log.error(n.getMessage(), n);
		}
	}

	// Method to load Drivers page
	public void showDriversView(String sessionID) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/itm/food/views/admin/drivers.fxml"));
			scene.setRoot((Parent) loader.load());
			DriverController controller = loader.<DriverController>getController();
			controller.initManager(this, sessionID);
		} catch (IOException ex) {
			log.error(ex.getMessage(), ex);
		} catch (NullPointerException n) {
			log.error(n.getMessage(), n);
		}
	}

	// Method to load Orders page
	public void showReviewsView(String sessionID) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/itm/food/views/admin/orders.fxml"));
			scene.setRoot((Parent) loader.load());
			OrdersController controller = loader.<OrdersController>getController();
			controller.initManager(this, sessionID);
		} catch (IOException ex) {
			log.error(ex.getMessage(), ex);
		} catch (NullPointerException n) {
			log.error(n.getMessage(), n);
		}
	}

	// Method to load Admin Profile page
	public void showProfile(String sessionID) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/itm/food/views/admin/adminProfile.fxml"));
			scene.setRoot((Parent) loader.load());
			AdminProfileController controller = loader.<AdminProfileController>getController();
			controller.initManager(this, sessionID);
		} catch (IOException ex) {
			log.error(ex.getMessage(), ex);
		} catch (NullPointerException n) {
			log.error(n.getMessage(), n);
		}

	}

}
