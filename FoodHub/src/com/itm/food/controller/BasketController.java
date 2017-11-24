package com.itm.food.controller;

import com.jfoenix.controls.JFXButton;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class BasketController extends BaseController {

	@FXML
	private JFXButton btnPlaceOrder;

	@FXML
	private AnchorPane errorPane;

	@FXML
	private Label lblErrorMsg;

	@FXML
	private Label lblItemsTotal;

	@FXML
	private Label lblDeliveryCharge;

	@FXML
	private Label lblCouponsApplied;

	@FXML
	private Label lblTotalBeforeTax;

	@FXML
	private Label lblTaxApplied;

	@FXML
	private Label lblOrderTotal;

	public void init() {
		super.init();
		if (null != BaseController.foodBasket.getOrderItems() || BaseController.foodBasket.getOrderItems().isEmpty())
			renderOrderSummary();
	}

	@FXML
	void handlePlaceOrder(ActionEvent event) {

	}

	void renderOrderSummary() {
		BaseController.foodBasket.calculateOrderSummary();
		lblItemsTotal.setText("$" + BaseController.foodBasket.getItemsTotal());
		lblDeliveryCharge.setText("$" + BaseController.foodBasket.getDeliveryCharge());
		lblCouponsApplied.setText("$" + BaseController.foodBasket.getCouponsApplied());
		lblTotalBeforeTax.setText("$" + BaseController.foodBasket.getTotalBeforeTax());
		lblTaxApplied.setText("$" + BaseController.foodBasket.getTaxApplied());
		lblOrderTotal.setText("$" + BaseController.foodBasket.getOrderTotal());
	}

}
