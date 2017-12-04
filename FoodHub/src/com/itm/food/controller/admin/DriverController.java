package com.itm.food.controller.admin;

import java.sql.ResultSet;

import org.apache.log4j.Logger;

import com.itm.food.controller.admin.components.DetailViewTable;
import com.itm.food.controller.admin.components.Dialogs;
import com.itm.food.controller.admin.components.TextfieldControl;
import com.itm.food.dao.Drivers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class DriverController extends AdminManager {

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

	@FXML
	private StackPane stackPaneDriver;
	@FXML
	private SplitPane splitPaneView;
	@FXML
	private AnchorPane resultsPane;
	@FXML
	private ScrollPane resultsScrollPane;

	@FXML
	private GridPane addGrid;
	@FXML
	private GridPane updateGrid;
	@FXML
	private GridPane deleteGrid;
	@FXML
	private AnchorPane driverViewPane;
	@FXML
	private AnchorPane driverTablePane;
	@FXML
	private AnchorPane driverAddPane;
	@FXML
	private AnchorPane driverUpdatePane;
	@FXML
	private AnchorPane driverDeletePane;
	@FXML
	private MenuButton actionButton;
	@FXML
	private MenuItem addDriverButton;
	@FXML
	private MenuItem updateDriverButton;
	@FXML
	private MenuItem deleteDriverButton;

	private static final Logger log = Logger.getLogger(DriverController.class);

	// Add driver controls
	@FXML
	private Button addButton;
	@FXML
	private TextField driverFirstName, driverLastName, driverPhone, driverEmail, vehicleNo, licenseNo;
	@FXML
	private TextField updtdriver, UdriverFirstName, UdriverLastName, UdriverPhone, UdriverEmail, UvehicleNo, UlicenseNo;
	// Update driver controls
	@FXML
	private Button updtSearchButton, updateButton;
	@FXML
	private Label UresultTitle;
	// Delete driver controls
	@FXML
	private TextField deletedriver, DdriverFirstName, DdriverLastName, DdriverPhone, DdriverEmail, DvehicleNo,
			DlicenseNo;
	@FXML
	private Button deleteSearchButton, deleteButton;
	@FXML
	private Label DresultTitle;

	@SuppressWarnings("rawtypes")
	private TableView tableview;

	Drivers driver = new Drivers();

	@SuppressWarnings("rawtypes")
	public void initManager(final AdminManager loginManager, String sessionID) {
		// Toolbar navigation actions
		logoutButton.setOnAction(e -> loginManager.logout());
		homeButton.setOnAction(e -> loginManager.showMainView(sessionID));
		restaurantButton.setOnAction(e -> loginManager.showRestaurantsView(sessionID));
		menuButton.setOnAction(e -> loginManager.showMenuView(sessionID));
		driverButton.setOnAction(e -> loginManager.showDriversView(sessionID));
		reviewButton.setOnAction(e -> loginManager.showReviewsView(sessionID));
		profileButton.setOnAction(e -> loginManager.showProfile(sessionID));

		// CSS for toolbar buttons
		logoutButton.setStyle("-fx-background-color: linear-gradient(lightgray,gray);");
		profileButton.setStyle("-fx-background-color: linear-gradient(lightgray,gray);");
		homeButton.setStyle("-fx-background-color: linear-gradient(lightgray,gray);");
		restaurantButton.setStyle("-fx-background-color: linear-gradient(lightgray,gray);");
		menuButton.setStyle("-fx-background-color: linear-gradient(lightgray,gray);");
		driverButton.setStyle("-fx-background-color: linear-gradient(lightgray,gray);");
		reviewButton.setStyle("-fx-background-color: linear-gradient(lightgray,gray);");

		// CSS for Driver buttons
		actionButton.setStyle("-fx-background-color: linear-gradient(lightgray,gray);");
		addButton.setStyle("-fx-background-color: linear-gradient(lightgray,gray);");
		updtSearchButton.setStyle("-fx-background-color: linear-gradient(lightgray,gray);");
		updateButton.setStyle("-fx-background-color: linear-gradient(lightgray,gray);");
		deleteSearchButton.setStyle("-fx-background-color: linear-gradient(lightgray,gray);");
		deleteButton.setStyle("-fx-background-color: linear-gradient(lightgray,gray);");

		driverAddPane.setVisible(false);
		driverUpdatePane.setVisible(false);
		driverDeletePane.setVisible(false);

		log.debug("Menu Clicked");

		// View Driver list
		tableview = new TableView();
		ResultSet rs = null;
		try {
			rs = driverDB.getAllDrivers();
			tableview = new DetailViewTable().viewList(rs);
			resultsScrollPane.setContent(tableview);
			tableview.prefHeightProperty().bind(resultsScrollPane.heightProperty());
			tableview.prefWidthProperty().bind(resultsScrollPane.widthProperty());
			rs.close();
		} catch (Exception e2) {
			log.error(e2.getMessage(), e2);
		}

		// Add driver
		final Text actiontarget = new Text();
		addGrid.add(actiontarget, 0, 8);
		actiontarget.setFill(Color.FIREBRICK);

		// Add driver menu button action
		addDriverButton.setOnAction(ev -> {
			log.debug("Add Driver Menu Processing");
			driverViewPane.setVisible(false);
			driverAddPane.toFront();
			driverAddPane.setVisible(true);

			// add button action
			addButton.setOnAction(event -> {

				// driver details validation
				if (driverFirstName.getText().equals("") || driverLastName.getText().equals("")
						|| driverEmail.getText().equals("") || driverPhone.getText().equals("")
						|| vehicleNo.getText().equals("") || licenseNo.getText().equals("")) {
					actiontarget.setText("Please enter mandatory details");

				} else if (driverPhone.getText().matches(".*[^0-9].*")) {
					actiontarget.setText("Please enter valid phone number");
				} else if ((driverPhone.getText().length()) > 10) {
					actiontarget.setText("Phone number have less than or equal to 10 digits");
				} else {

					driver.setFirstName(driverFirstName.getText());
					driver.setLastName(driverLastName.getText());
					driver.setEmail(driverEmail.getText());
					driver.setPhoneNo(driverPhone.getText());
					driver.setVehicleNo(vehicleNo.getText());
					driver.setLicenseNo(licenseNo.getText());

					try {
						driver.setDriverId(driverDB.add(driver));
						if (driver.getDriverId() != 0) {
							new Dialogs().showActionStatus(driver.getDriverId(), "ID Driver added successfully!");
							driverFirstName.clear();
							driverLastName.clear();
							driverEmail.clear();
							driverPhone.clear();
							vehicleNo.clear();
							licenseNo.clear();
							actiontarget.setText("");
						}
					} catch (Exception ex) {
						log.error(ex.getMessage(), ex);
					}
				}
			}); // add button action ends

		});// addmenuItem action ends

		// Update driver Menu button action
		updateDriverButton.setOnAction(e -> {
			log.debug("Update Driver Menu Processing");
			driverViewPane.setVisible(false);
			driverUpdatePane.toFront();
			driverUpdatePane.setVisible(true);

			UresultTitle.setVisible(false);
			updateGrid.setVisible(false);
			updateButton.setVisible(false);

			// Search for driver ID for update on button click
			updtSearchButton.setOnAction(ev -> {
				if (updtdriver.getText().equals("") || updtdriver.getText().matches(".*[^0-9].*"))
					new Dialogs().showWarning("Please enter a valid driver ID");
				else {
					updateButton.setDisable(false);
					int searchdriverID = Integer.parseInt(updtdriver.getText());
					log.debug("Searching for update" + searchdriverID);

					try {
						driver = driverDB.find(searchdriverID);
						if (driver.getDriverId() != 0) {
							UresultTitle.setVisible(true);
							updateGrid.setVisible(true);
							updateButton.setVisible(true);

							// enable textfields for edit
							new TextfieldControl().enableTextField(UdriverFirstName, driver.getFirstName());
							new TextfieldControl().enableTextField(UdriverLastName, driver.getLastName());
							new TextfieldControl().enableTextField(UdriverPhone, driver.getPhoneNo());
							new TextfieldControl().enableTextField(UdriverEmail, driver.getEmail());
							new TextfieldControl().enableTextField(UvehicleNo, driver.getVehicleNo());
							new TextfieldControl().enableTextField(UlicenseNo, driver.getLicenseNo());
						} else {
							new Dialogs().showError(searchdriverID,
									"Driver ID Not Found! Please verify driver ID entered.");
						}
					} catch (Exception ex) {
						log.error(ex.getMessage(), ex);
					}

					// Update button action
					updateButton.setOnAction(eve -> {
						if (new Dialogs().showConfirmation(searchdriverID,
								"Are you sure want to update details for driver") == true) {

							driver.setDriverId(searchdriverID);
							driver.setFirstName(new TextfieldControl().get_Text_PromptText(UdriverFirstName));
							driver.setLastName(new TextfieldControl().get_Text_PromptText(UdriverLastName));
							driver.setPhoneNo(new TextfieldControl().get_Text_PromptText(UdriverPhone));
							driver.setEmail(new TextfieldControl().get_Text_PromptText(UdriverEmail));
							driver.setVehicleNo(new TextfieldControl().get_Text_PromptText(UvehicleNo));
							driver.setLicenseNo(new TextfieldControl().get_Text_PromptText(UlicenseNo));

							try {
								driverDB.update(driver);
								// disable textfields after updation
								new TextfieldControl().disableTextField(UdriverFirstName, driver.getFirstName());
								new TextfieldControl().disableTextField(UdriverLastName, driver.getLastName());
								new TextfieldControl().disableTextField(UdriverPhone, driver.getPhoneNo());
								new TextfieldControl().disableTextField(UdriverEmail, driver.getEmail());
								new TextfieldControl().disableTextField(UvehicleNo, driver.getVehicleNo());
								new TextfieldControl().disableTextField(UlicenseNo, driver.getLicenseNo());

								new Dialogs().showActionStatus(searchdriverID, "Driver details updated successfully!");
								updateButton.setDisable(true);
								updtdriver.clear();
							} catch (Exception e1) {
								log.error(e1.getMessage(), e1);
							}

						}
					});// updatebutton action ends
				}
			});// update search button event ends

		});// updatemenu button ends

		// delete driver menubutton action
		deleteDriverButton.setOnAction(e -> {
			log.debug("Delete Driver Menu Processing");
			driverUpdatePane.setVisible(false);
			driverViewPane.setVisible(false);
			driverDeletePane.toFront();
			driverDeletePane.setVisible(true);

			DresultTitle.setVisible(false);
			deleteGrid.setVisible(false);
			deleteButton.setVisible(false);

			// Search by driver ID for deletion
			deleteSearchButton.setOnAction(ev -> {
				if (deletedriver.getText().equals("") || deletedriver.getText().matches(".*[^0-9].*"))
					new Dialogs().showWarning("Please enter a valid Driver ID");
				else {
					int searchdriverID = Integer.parseInt(deletedriver.getText());

					log.debug("Searching for delete" + searchdriverID);

					try {
						driver = driverDB.find(searchdriverID);
						if (driver.getDriverId() != 0) {
							DresultTitle.setVisible(true);
							deleteGrid.setVisible(true);
							deleteButton.setVisible(true);

							new TextfieldControl().disableTextField(DdriverFirstName, driver.getFirstName());
							new TextfieldControl().disableTextField(DdriverLastName, driver.getLastName());
							new TextfieldControl().disableTextField(DdriverPhone, driver.getPhoneNo());
							new TextfieldControl().disableTextField(DdriverEmail, driver.getEmail());
							new TextfieldControl().disableTextField(DvehicleNo, driver.getVehicleNo());
							new TextfieldControl().disableTextField(DlicenseNo, driver.getLicenseNo());
						} else {
							new Dialogs().showError(searchdriverID,
									"Driver ID Not Found! Please verify driver ID entered.");
						}
					} catch (Exception ex) {
						log.error(ex.getMessage(), ex);
					}

					// delete button action
					deleteButton.setOnAction(eve -> {

						if (new Dialogs().showConfirmation(searchdriverID,
								"Are you sure want to delete driver details") == true) {
							try {
								driverDB.delete(searchdriverID);
								DresultTitle.setVisible(false);
								deleteGrid.setVisible(false);
								deleteButton.setVisible(false);
							} catch (Exception e1) {
								log.error(e1.getMessage(), e1);
							}
							new Dialogs().showActionStatus(searchdriverID, "deleted successfully!");
							deletedriver.clear();
						}

					});
				}
			});
		});
	}

}