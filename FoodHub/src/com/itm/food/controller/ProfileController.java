package com.itm.food.controller;

import org.apache.log4j.Logger;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;

public class ProfileController extends BaseController {
	
	private static final Logger log = Logger.getLogger(ProfileController.class);

	@FXML
	void handleMouseUpdateProfile(MouseEvent event) {
		handleUpdateProfile();
	}

	void handleUpdateProfile() {
		log.debug("Updating profile");
	}

	@Override
	void init() {
		super.init();
	}

}
