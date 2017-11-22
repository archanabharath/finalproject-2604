package com.itm.food.controller;

import org.apache.log4j.Logger;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;

public class AddressController extends BaseController {

	private static final Logger log = Logger.getLogger(AddressController.class);

	@FXML
	void handleMouseAddAddress(MouseEvent event) {
		handleAddAddress();
	}

	void handleAddAddress() {
		log.debug("Adding address");
	}

	@Override
	void init() {
		super.init();
	}

}
