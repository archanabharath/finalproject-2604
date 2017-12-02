package com.itm.food.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.controlsfx.control.Rating;

import com.itm.food.dao.ItemRestaurant;
import com.itm.food.dao.OrderItem;
import com.itm.food.dao.Restaurant;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class HomeController extends BaseController {

	private static final Logger log = Logger.getLogger(HomeController.class);

	@FXML
	private AnchorPane anchorrestaurant1;

	@FXML
	private ImageView imgviewrestaurant1;

	@FXML
	private Label lblrestaurant1;

	@FXML
	private AnchorPane anchorrestaurant2;

	@FXML
	private ImageView imgviewrestaurant2;

	@FXML
	private Label lblrestaurant2;

	@FXML
	private AnchorPane anchorrestaurant3;

	@FXML
	private ImageView imgviewrestaurant3;

	@FXML
	private Label lblrestaurant3;

	@FXML
	private AnchorPane anchoritem1;

	@FXML
	private ImageView imgviewitem1;

	@FXML
	private Label lblitem1;

	@FXML
	private AnchorPane anchoritem2;

	@FXML
	private ImageView imgviewitem2;

	@FXML
	private Label lblitem2;

	@FXML
	private AnchorPane anchoritem3;

	@FXML
	private ImageView imgviewitem3;

	@FXML
	private Label lblitem3;

	@FXML
	private Rating irating1;

	@FXML
	private Rating irating2;

	@FXML
	private Rating irating3;

	@FXML
	private Rating rrating1;

	@FXML
	private Rating rrating2;

	@FXML
	private Rating rrating3;

	@FXML
	void handleAddToCart1(MouseEvent event) {
		log.debug(event.getClickCount());
		log.debug(event.getSource().toString());
		handleAddToCart(0);
	}

	@FXML
	void handleAddToCart2(MouseEvent event) {
		handleAddToCart(1);
	}

	@FXML
	void handleAddToCart3(MouseEvent event) {
		handleAddToCart(2);
	}

	@FXML
	void handleViewMenu1(MouseEvent event) {
		handleViewMenu(0);

	}

	@FXML
	void handleViewMenu2(MouseEvent event) {
		handleViewMenu(1);

	}

	@FXML
	void handleViewMenu3(MouseEvent event) {
		handleViewMenu(2);

	}

	List<Restaurant> top3RestaurantsByRating = new ArrayList<Restaurant>();
	ItemRestaurant top3ItemsToOrder = new ItemRestaurant();

	@Override
	void init() {
		super.init();
		getTop3RestaurantsByRating();
		getTop3ItemsByRating();

	}

	public void getTop3ItemsByRating() {
		top3ItemsToOrder = customerOperation.getTop3Items();
		lblitem1.setText(top3ItemsToOrder.getTop3ItemsList().get(0).getItemName() + "\n" + "Prepared By: "
				+ top3ItemsToOrder.getTop3ItemsByRestaurants().get(0).getRestaurantName() + " "
				+ top3ItemsToOrder.getTop3ItemsByRestaurants().get(0).getCity() + "-"
				+ top3ItemsToOrder.getTop3ItemsByRestaurants().get(0).getZipcode());
		irating1.setRating(top3ItemsToOrder.getTop3ItemsList().get(0).getItemOverallRating());
		lblitem2.setText(top3ItemsToOrder.getTop3ItemsList().get(1).getItemName() + "\n" + "Prepared By:"
				+ top3ItemsToOrder.getTop3ItemsByRestaurants().get(1).getRestaurantName() + "\n "
				+ top3ItemsToOrder.getTop3ItemsByRestaurants().get(1).getCity() + "-"
				+ top3ItemsToOrder.getTop3ItemsByRestaurants().get(1).getZipcode());
		irating2.setRating(top3ItemsToOrder.getTop3ItemsList().get(1).getItemOverallRating());
		lblitem3.setText(top3ItemsToOrder.getTop3ItemsList().get(2).getItemName() + "\n" + "Prepared By:"
				+ top3ItemsToOrder.getTop3ItemsByRestaurants().get(2).getRestaurantName() + "\n "
				+ top3ItemsToOrder.getTop3ItemsByRestaurants().get(2).getCity() + "-"
				+ top3ItemsToOrder.getTop3ItemsByRestaurants().get(2).getZipcode());
		irating3.setRating(top3ItemsToOrder.getTop3ItemsList().get(2).getItemOverallRating());

	}

	public void getTop3RestaurantsByRating() {
		top3RestaurantsByRating = customerOperation.getTopRestaurants();
		lblrestaurant1.setText(top3RestaurantsByRating.get(0).getRestaurantName() + "\n "
				+ top3RestaurantsByRating.get(0).getCity() + "-" + top3RestaurantsByRating.get(0).getZipcode());
		rrating1.setRating(top3RestaurantsByRating.get(0).getRating());
		lblrestaurant2.setText(top3RestaurantsByRating.get(1).getRestaurantName() + "\n "
				+ top3RestaurantsByRating.get(1).getCity() + "-" + top3RestaurantsByRating.get(1).getZipcode());
		rrating2.setRating(top3RestaurantsByRating.get(1).getRating());
		lblrestaurant3.setText(top3RestaurantsByRating.get(2).getRestaurantName() + "\n"
				+ top3RestaurantsByRating.get(2).getCity() + "-" + top3RestaurantsByRating.get(2).getZipcode());
		rrating3.setRating(top3RestaurantsByRating.get(2).getRating());

	}

	public void handleViewMenu(int i) {
		BaseController.currentRestaurant = top3RestaurantsByRating.get(i);
		handleItem();
	}

	public void handleAddToCart(int j) {
		log.debug("handleaddtocart: " + j);
		OrderItem item = new OrderItem();
		item.setRestaurantId(top3ItemsToOrder.getTop3ItemsByRestaurants().get(j).getRestaurantId());
		item.setItemId(top3ItemsToOrder.getTop3ItemsList().get(j).getItemId());
		item.setItemPrice(top3ItemsToOrder.getTop3ItemsList().get(j).getItemPrice());
		item.setItemQuantity(1);

		if (null == BaseController.foodBasket.getOrderItems()) {
			ArrayList<OrderItem> orderItemList = new ArrayList<OrderItem>();
			orderItemList.add(item);
			BaseController.foodBasket.setOrderItems(orderItemList);
		} else {
			BaseController.foodBasket.getOrderItems().add(item);
		}

		handleBasket();

	}

}
