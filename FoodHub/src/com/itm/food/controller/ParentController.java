
package com.itm.food.controller;

import org.apache.log4j.Logger;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ParentController extends Application {

	private static final Logger log = Logger.getLogger(ParentController.class);

	public static Stage mainStage;

	@Override
	public void start(Stage primaryStage) {
		try {
			mainStage = primaryStage;
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/com/itm/food/views/Login.fxml"));
			AnchorPane root = loader.load();
			BaseController controller = loader.getController();
			controller.appInit();
			Scene scene = new Scene(root, 900, 500);
			scene.getStylesheets().add(getClass().getResource("/com/itm/food/application.css").toExternalForm());
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