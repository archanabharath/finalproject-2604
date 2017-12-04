package com.itm.food.controller.admin.components;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;

public class Dialogs {
	
	//Method to request Confirmation on Action from user
	public boolean showConfirmation(int ID,String message) {
		 
	      Alert alert = new Alert(AlertType.CONFIRMATION);
	      alert.setTitle("Confirm Action");
	      alert.setContentText(message+" "+ID+"?");
	 
	      Optional<ButtonType> option = alert.showAndWait();
	      if (option.get() == ButtonType.OK) {
	         return true;
	      } else 
	    	  return false;
	   }
	 
	//method to show action status to user
	 public void showActionStatus(int ID,String message) {
		 
	        Alert alert = new Alert(AlertType.INFORMATION);
	        alert.setTitle("Message to User");
	        alert.setContentText(ID+" "+message);
	        alert.showAndWait();
	    }
	
	 //method to show warning to user
	 public void showWarning(String message) {
	        
		    Alert alert = new Alert(AlertType.WARNING);
	        alert.setTitle("Message to User");
	        alert.setContentText(message);
	        alert.showAndWait();
	    }
	 
	 //method to show error to user
	 public void showError(int ID,String message) { 
	 
	 Alert alert = new Alert(AlertType.ERROR);
	 alert.setTitle("Message to User");
	 alert.setContentText(ID+" "+message);
	 alert.showAndWait();
	 }
}
