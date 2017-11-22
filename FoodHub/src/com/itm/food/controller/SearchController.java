package com.itm.food.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;

public class SearchController extends BaseController {

	@FXML
	AnchorPane searchResultsPane;

	@Override
	void init() {
		super.init();
		for(int i=1; i<=5; i++) {
		ScrollPane vbox = new ScrollPane();
		vbox.setPrefSize(200, 100);
		Label lbl1 = new Label();
		lbl1.setText("TEST");
		vbox.setContent(lbl1);
		
	    AnchorPane.setTopAnchor(vbox, 10.0 * i * 2); // obviously provide your own constraints
	    searchResultsPane.getChildren().add(vbox);
		}

	}

}
