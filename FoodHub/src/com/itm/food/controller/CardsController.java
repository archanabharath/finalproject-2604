package com.itm.food.controller;

import org.apache.log4j.Logger;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;

public class CardsController extends BaseController {
	
	private static final Logger log = Logger.getLogger(CardsController.class);

	@FXML
	void handleMouseAddPayment(MouseEvent event) {
		handleAddPayment();
	}

	void handleAddPayment() {
		log.debug("Adding Payment");
	}

	@Override
	void init() {
		super.init();
		// TODO Auto-generated method stub
	}

}
