package com.itm.food.controller;

import com.jfoenix.controls.JFXTextField;

import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;

public class BasketController extends BaseController {

	@FXML
	private ScrollPane spContent;

	public void init() {
		super.init();
    	for(int i=0; i<5 ; i++) {
        	AnchorPane ap = new AnchorPane();
        	ap.setPrefHeight(200);
        	ap.setPrefWidth(500);
        	
        	JFXTextField jtf = new JFXTextField();
        	jtf.setPrefHeight(100);
        	jtf.setPrefWidth(300);
        	ap.setUserData(jtf);
    	}
    }

}
