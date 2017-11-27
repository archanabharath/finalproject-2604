package com.itm.food.controller;

import java.util.ArrayList;
import java.util.List;

import com.itm.food.dao.ItemRestaurant;
import com.itm.food.dao.Restaurant;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class HomeController extends BaseController {

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
	void handleAddToCart1(MouseEvent event) {

	}

	@FXML
	void handleAddToCart2(MouseEvent event) {

	}

	@FXML
	void handleAddToCart3(MouseEvent event) {

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

	@Override
	void init() {
		super.init();
		getTop3RestaurantsByRating();
		getTop3ItemsByRating();

	}

	public void getTop3ItemsByRating() {
		ItemRestaurant top3ItemsList = new ItemRestaurant();
		top3ItemsList = customerOperation.getTop3Items();
		lblitem1.setText(top3ItemsList.getTop3ItemsList().get(0).getItemName() + "\n" + "Prepared By: "
				+ top3ItemsList.getTop3ItemsByRestaurants().get(0).getRestaurantName());
		lblitem2.setText(top3ItemsList.getTop3ItemsList().get(1).getItemName() + "\n" + "Prepared By:"
				+ top3ItemsList.getTop3ItemsByRestaurants().get(1).getRestaurantName());
		lblitem3.setText(top3ItemsList.getTop3ItemsList().get(2).getItemName() + "\n" + "Prepared By:"
				+ top3ItemsList.getTop3ItemsByRestaurants().get(2).getRestaurantName());

	}

	public void getTop3RestaurantsByRating() {

		top3RestaurantsByRating = customerOperation.getTopRestaurants();
		lblrestaurant1.setText(top3RestaurantsByRating.get(0).getRestaurantName());
		lblrestaurant2.setText(top3RestaurantsByRating.get(1).getRestaurantName());
		lblrestaurant3.setText(top3RestaurantsByRating.get(2).getRestaurantName());

	}

	public void handleViewMenu(int i) {
		BaseController.currentRestaurant = top3RestaurantsByRating.get(i);
		handleItem();

	}

}
