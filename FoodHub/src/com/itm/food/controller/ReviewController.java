package com.itm.food.controller;

import com.jfoenix.controls.JFXButton;

import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;

public class ReviewController extends BaseController {

	@FXML
	private RadioButton radioStar1;

	@FXML
	private RadioButton radioStar2;

	@FXML
	private RadioButton radioStar3;

	@FXML
	private JFXButton ratingSubmitBtn;
	
	void init() {
		super.init();
	}
	
	void submitRating() {
		
		radioStar1.setSelected(true);
	}
}