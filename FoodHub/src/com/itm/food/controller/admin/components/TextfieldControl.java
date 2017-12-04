package com.itm.food.controller.admin.components;

import javafx.beans.binding.Bindings;
import javafx.scene.control.TextField;

public class TextfieldControl {
	
	//method to disable textfields invoked for display only
	public void disableTextField(TextField textField, String promptText) {
		textField.setText(promptText);
		textField.styleProperty().bind(
                Bindings.when(
                		textField.editableProperty())
                            .then((String) null)
                            .otherwise("-fx-background-color: transparent;")
        );
 
		textField.setEditable(false);
 
		textField.setOnAction(event -> textField.setEditable(false));
		
		
	}
	
	//method to enable textfields on edit
	public void enableTextField(TextField textField,String promptText) {
			
		 textField.setEditable(true);
		 textField.setText(promptText);
	 }
	
	//method to get previous text in text field if updated and updated text otherwise on updation
	public String get_Text_PromptText(TextField textfield)
	 {
		 
		String name = null;
		
 		if (textfield.getText().equals("")) {
			
			name = textfield.getPromptText();
			
		}
		else
			name = textfield.getText();
	 
 		return name;
	 }
}
