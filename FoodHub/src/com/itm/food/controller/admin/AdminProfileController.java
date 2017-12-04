package com.itm.food.controller.admin;

import org.apache.log4j.Logger;

import com.itm.food.controller.admin.components.Dialogs;
import com.itm.food.controller.admin.components.TextfieldControl;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class AdminProfileController extends AdminManager {

	// Toolbar buttons for navigation
	@FXML
	private Button logoutButton;
	@FXML
	private Button profileButton;
	@FXML
	private Button homeButton;
	@FXML
	private Button restaurantButton;
	@FXML
	private Button menuButton;
	@FXML
	private Button driverButton;
	@FXML
	private Button reviewButton;

	// Pane to Hold Profile Edit pane and Password edit pane
	@FXML
	private StackPane stackPaneProfile;

	// Profile Edit pane, textfields and buttons
	@FXML
	private AnchorPane profileViewPane;
	@FXML
	public TextField adminid;
	@FXML
	private TextField firstname;
	@FXML
	private TextField lastname;
	@FXML
	private TextField email;
	@FXML
	private TextField phone;
	@FXML
	private ToggleButton editButton;
	@FXML
	private Button editPasswordButton;

	// Password Edit pane, textfields and buttons
	@FXML
	private AnchorPane changePasswordPane;
	@FXML
	private GridPane passwordGrid;
	@FXML
	private PasswordField currPwd;
	@FXML
	private PasswordField newPwd;
	@FXML
	private PasswordField confirmPwd;
	@FXML
	private Button savePwdButton;

	private static final Logger log = Logger.getLogger(AdminProfileController.class);
	
	@Override
	public void initManager(final AdminManager loginManager, String sessionID) {

		logoutButton.setOnAction(e -> loginManager.logout());
		homeButton.setOnAction(e -> loginManager.showMainView(sessionID));
		restaurantButton.setOnAction(e -> loginManager.showRestaurantsView(sessionID));
		menuButton.setOnAction(e -> loginManager.showMenuView(sessionID));
		driverButton.setOnAction(e -> loginManager.showDriversView(sessionID));
		reviewButton.setOnAction(e -> loginManager.showReviewsView(sessionID));
		profileButton.setOnAction(e -> loginManager.showProfile(sessionID));

		// CSS for Toolbar buttons
		logoutButton.setStyle("-fx-background-color: linear-gradient(lightgray,gray);");
		profileButton.setStyle("-fx-background-color: linear-gradient(lightgray,gray);");
		homeButton.setStyle("-fx-background-color: linear-gradient(lightgray,gray);");
		restaurantButton.setStyle("-fx-background-color: linear-gradient(lightgray,gray);");
		menuButton.setStyle("-fx-background-color: linear-gradient(lightgray,gray);");
		driverButton.setStyle("-fx-background-color: linear-gradient(lightgray,gray);");
		reviewButton.setStyle("-fx-background-color: linear-gradient(lightgray,gray);");

		// CSS for Profile pane buttons
		editButton.setStyle("-fx-background-color: linear-gradient(lightgray,gray);");
		editPasswordButton.setStyle("-fx-background-color: linear-gradient(lightgray,gray);");
		savePwdButton.setStyle("-fx-background-color: linear-gradient(lightgray,gray);");
		changePasswordPane.setVisible(false);

		new TextfieldControl().disableTextField(adminid, loginManager.admin.getAdminID());
		new TextfieldControl().disableTextField(firstname, loginManager.admin.getFirstName());
		new TextfieldControl().disableTextField(lastname, loginManager.admin.getLastName());
		new TextfieldControl().disableTextField(email, loginManager.admin.getEmail());
		new TextfieldControl().disableTextField(phone, loginManager.admin.getPhone());

		// action on Edit button click
		editButton.setOnAction(event -> {
			// action if toggle button edit is selected
			if (editButton.isSelected()) {
				editButton.setText("Save");
				new TextfieldControl().enableTextField(adminid, loginManager.admin.getAdminID());
				new TextfieldControl().enableTextField(firstname, loginManager.admin.getFirstName());
				new TextfieldControl().enableTextField(lastname, loginManager.admin.getLastName());
				new TextfieldControl().enableTextField(email, loginManager.admin.getEmail());
				new TextfieldControl().enableTextField(phone, loginManager.admin.getPhone());

			} else {
				editButton.setText("Edit");
				loginManager.admin.setFirstName(new TextfieldControl().get_Text_PromptText(firstname));
				loginManager.admin.setAdminID(new TextfieldControl().get_Text_PromptText(adminid));
				loginManager.admin.setLastName(new TextfieldControl().get_Text_PromptText(lastname));
				loginManager.admin.setEmail(new TextfieldControl().get_Text_PromptText(email));
				loginManager.admin.setPhone(new TextfieldControl().get_Text_PromptText(phone));
				try {

					log.debug("Updating admin details in db");

					adminDB.updateAdminDetails(loginManager.admin.getUsername(), loginManager.admin.getAdminID(),
							loginManager.admin.getFirstName(), loginManager.admin.getLastName(),
							loginManager.admin.getEmail(), loginManager.admin.getPhone());
					new Dialogs().showActionStatus(1, "Admin profile has been updated");

				} catch (Exception s) {
					s.getMessage();
				}
			}

		});

		// action on Change Password button click
		editPasswordButton.setOnAction(event -> {

			profileViewPane.setVisible(false);
			changePasswordPane.toFront();
			changePasswordPane.setVisible(true);

			// placeholder to display validation message to user
			final Text actiontarget = new Text();
			passwordGrid.add(actiontarget, 1, 3);
			actiontarget.setFill(Color.FIREBRICK);
			// action on save password button click
			savePwdButton.setOnAction(e -> {

				// password validations for updation
				if (currPwd.getText().equals("") || newPwd.getText().equals("") || confirmPwd.getText().equals("")) {
					actiontarget.setText("Please fill in all fields");

				} else {

					if (loginManager.admin.getPassword().equals(currPwd.getText())) {
						if (newPwd.getText().compareTo(confirmPwd.getText()) == 0) {
							loginManager.admin.setPassword(newPwd.getText());
							try {
								adminDB.updateAdminPassword(loginManager.admin.getUsername(),
										loginManager.admin.getPassword());
							} catch (Exception ex) {
								ex.getMessage();
							}
							new Dialogs().showActionStatus(1, "Admin password has been changed");

						} else
							actiontarget.setText("New password and confirm password should match");

					} else
						actiontarget.setText("Current password entered is incorrect");

				}

			});// end of save password button action

		});// end of change password button action

	}

}
