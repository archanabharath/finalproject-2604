package com.itm.food.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;

public class SearchController extends BaseController {

	@FXML
	private ScrollPane scrollPaneRest;

	@FXML
	private AnchorPane anchorPaneRestList;

	@Override
	void init() {
		super.init();
		for (int i = 0; i < 10; i++) {
			renderSearchList(i);
		}
	}

	void renderSearchList(int count) {

		double paneHeightNeeded = count + 1 * 210;
		double currentPaneHeight = anchorPaneRestList.getPrefHeight();
		if (paneHeightNeeded <= currentPaneHeight) {
			anchorPaneRestList.setPrefHeight(currentPaneHeight + 210.0);
		}

		// <AnchorPane prefHeight="200.0" prefWidth="1180.0"
		// style="-fx-border-color: lightgrey;">
		// Add a inner pane container
		AnchorPane pane = new AnchorPane();
		pane.setLayoutY(count * 210); // TODO INCREMENT THIS 210.0
		pane.setPrefHeight(200.0);
		pane.setPrefWidth(1180.0);
		pane.setStyle("-fx-border-color: lightgrey;");

		// <ImageView fitHeight="150.0" fitWidth="200.0" layoutX="47.0"
		// layoutY="25.0" pickOnBounds="true" preserveRatio="true"
		// AnchorPane.leftAnchor="10.0" AnchorPane.topAnchor="25.0">
		// Add a image view for restaurant
		ImageView imageView = new ImageView();
		imageView.setFitHeight(150.0);
		imageView.setFitWidth(200.0);
		imageView.setLayoutX(45.0);
		imageView.setLayoutY(25.0);
		imageView.setPickOnBounds(true);
		imageView.setPreserveRatio(false);
		AnchorPane.setLeftAnchor(imageView, 10.0);
		AnchorPane.setTopAnchor(imageView, 25.0);
		// System.out.println(getClass().getResource("../../../../asserts/icons8-restaurant-96.png").toString());
		//imageView.setImage(new Image("http://mikecann.co.uk/wp-content/uploads/2009/12/javafx_logo_color_1.jpg"));
		pane.getChildren().add(imageView);

		// <Label layoutX="202.0" layoutY="22.0" prefHeight="30.0"
		// prefWidth="900.0" text="[ResutantName]" wrapText="true"
		// AnchorPane.leftAnchor="200.0" AnchorPane.topAnchor="20.0">
		// <font>
		// <Font size="24.0" />
		// </font>
		// </Label>
		Label lblRestName = new Label();
		lblRestName.setLayoutX(200.0);
		lblRestName.setLayoutY(20.0);
		lblRestName.prefHeight(30.0);
		lblRestName.prefWidth(900.0);
		lblRestName.setText("[ResutantName]");
		lblRestName.setWrapText(true);
		lblRestName.setFont(new Font(24.0));
		AnchorPane.setLeftAnchor(lblRestName, 200.0);
		AnchorPane.setTopAnchor(lblRestName, 20.0);
		pane.getChildren().add(lblRestName);

		// <Label layoutX="210.0" layoutY="70.0" prefHeight="20.0"
		// prefWidth="615.0" text="[ResturantDesc]" wrapText="true"
		// AnchorPane.leftAnchor="200.0" AnchorPane.topAnchor="60.0">
		// <font>
		// <Font size="15.0" />
		// </font>
		// </Label>
		Label lblRestDesc = new Label();
		lblRestDesc.setLayoutX(210.0);
		lblRestDesc.setLayoutY(70.0);
		lblRestDesc.prefHeight(20.0);
		lblRestDesc.prefWidth(615.0);
		lblRestDesc.setText("[ResutantDesc]");
		lblRestDesc.setWrapText(true);
		lblRestDesc.setFont(new Font(15.0));
		AnchorPane.setLeftAnchor(lblRestDesc, 200.0);
		AnchorPane.setTopAnchor(lblRestDesc, 60.0);
		pane.getChildren().add(lblRestDesc);

		// <Label layoutX="896.0" layoutY="156.0" text="[0 Miles]"
		// AnchorPane.bottomAnchor="20.0" AnchorPane.rightAnchor="200.0">
		// <font>
		// <Font size="18.0" />
		// </font>
		// </Label>
		Label lblRestDistance = new Label();
		lblRestDistance.setLayoutX(896.0);
		lblRestDistance.setLayoutY(156.0);
		lblRestDistance.setText("[0 Miles]");
		lblRestDistance.setFont(new Font(18.0));
		AnchorPane.setRightAnchor(lblRestDistance, 200.0);
		AnchorPane.setBottomAnchor(lblRestDistance, 20.0);
		pane.getChildren().add(lblRestDistance);

		// <Label layoutX="1021.0" layoutY="156.0" text="[0 Mins]"
		// AnchorPane.bottomAnchor="20.0" AnchorPane.rightAnchor="75.0">
		// <font>
		// <Font size="18.0" />
		// </font>
		// </Label>
		Label lblRestTravelTime = new Label();
		lblRestTravelTime.setLayoutX(1021.0);
		lblRestTravelTime.setLayoutY(156.0);
		lblRestTravelTime.setText("[0 Mins]");
		lblRestTravelTime.setFont(new Font(18.0));
		AnchorPane.setRightAnchor(lblRestTravelTime, 75.0);
		AnchorPane.setBottomAnchor(lblRestTravelTime, 20.0);
		pane.getChildren().add(lblRestTravelTime);

		anchorPaneRestList.getChildren().add(pane);

		// for(int i=1; i<=5; i++) {
		// ScrollPane vbox = new ScrollPane();
		// vbox.setPrefSize(200, 100);
		// Label lbl1 = new Label();
		// lbl1.setText("TEST");
		// vbox.setContent(lbl1);
		//
		// AnchorPane.setTopAnchor(vbox, 10.0 * i * 2); // obviously provide
		// your own constraints
		// searchResultsPane.getChildren().add(vbox);

	}

}
