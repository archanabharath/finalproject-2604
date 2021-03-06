
package com.itm.food.controller;

import org.apache.log4j.Logger;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXMLLoader;

public class ParentController extends Application {

	private static final Logger log = Logger.getLogger(ParentController.class);

	public static Stage mainStage;

	@Override
	public void start(Stage primaryStage) {
		try {
			mainStage = primaryStage;
			AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("../views/Login.fxml"));
			Scene scene = new Scene(root, 900, 500);
			scene.getStylesheets().add(getClass().getResource("../application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.initStyle(StageStyle.UNDECORATED);
			primaryStage.centerOnScreen();
			primaryStage.show();
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}

	/**
	 * Main method to launch the Application - Entry point to application
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
