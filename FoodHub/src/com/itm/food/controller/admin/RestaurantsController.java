package com.itm.food.controller.admin;

import java.sql.ResultSet;

import org.apache.log4j.Logger;

import com.itm.food.controller.admin.components.DetailViewTable;
import com.itm.food.controller.admin.components.Dialogs;
import com.itm.food.controller.admin.components.TextfieldControl;
import com.itm.food.dao.Restaurant;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class RestaurantsController extends AdminManager {

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
	private StackPane stackPaneRest;
	@FXML
	private ScrollPane scrollPane;
	@FXML
	private ScrollPane restViewScrollPane;
	@FXML
	private AnchorPane addRestPane;
	@FXML
	private GridPane addGrid;
	@FXML
	private GridPane updateGrid;
	@FXML
	private GridPane deleteGrid;
	@FXML
	private AnchorPane restViewPane;
	@FXML
	private AnchorPane restTablePane;
	@FXML
	private AnchorPane restAddPane;
	@FXML
	private AnchorPane restUpdatePane;
	@FXML
	private AnchorPane restDeletePane;
	@FXML
	private MenuButton actionButton;
	@FXML
	private MenuItem addRestButton;
	@FXML
	private MenuItem updateRestButton;
	@FXML
	private MenuItem deleteRestButton;

	// Add restaurant controls
	@FXML
	private Button addButton;
	@FXML
	private TextField restName, restDesc, addr1, addr2, city, zipCode, phone, email;
	// Update restaurant controls
	@FXML
	private TextField updtrest, UrestName, UrestDesc, Uaddr1, Uaddr2, Ucity, UzipCode, Uphone, Uemail;
	@FXML
	private Button updtSearchButton, updateButton;
	@FXML
	private Label UresultTitle;
	// Delete restaurant controls
	@FXML
	private TextField deleterest, DrestName, DrestDesc, Daddr1, Daddr2, Dcity, DzipCode, Dphone, Demail;
	@FXML
	private Button deleteSearchButton, deleteButton;
	@FXML
	private Label DresultTitle;

	@SuppressWarnings("rawtypes")
	private TableView tableview;

	private static final Logger log = Logger.getLogger(RestaurantsController.class);

	Restaurant rest = new Restaurant();

	public void initManager(final AdminManager loginManager, String sessionID) {

		// toolbar button navigation calls
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

		// CSS for Restaurant buttons
		addButton.setStyle("-fx-background-color: linear-gradient(lightgray,gray);");
		updtSearchButton.setStyle("-fx-background-color: linear-gradient(lightgray,gray);");
		updateButton.setStyle("-fx-background-color: linear-gradient(lightgray,gray);");
		actionButton.setStyle("-fx-background-color: linear-gradient(lightgray,gray);");
		deleteSearchButton.setStyle("-fx-background-color: linear-gradient(lightgray,gray);");
		deleteButton.setStyle("-fx-background-color: linear-gradient(lightgray,gray);");

		restAddPane.setVisible(false);
		restUpdatePane.setVisible(false);
		restDeletePane.setVisible(false);

		log.debug("Menu Clicked");

		// View restaurant list
		ResultSet rs = null;
		try {
			rs = restaurantDB.getAllRestaurants();
			tableview = new DetailViewTable().viewList(rs);
			scrollPane.setContent(tableview);
			tableview.prefHeightProperty().bind(scrollPane.heightProperty());
			tableview.prefWidthProperty().bind(scrollPane.widthProperty());
			rs.close();
		} catch (Exception e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

		// Add Restaurant menu button action
		addRestButton.setOnAction(ev -> {

			log.debug("Add Restaurant Menu Processing");
			restViewPane.setVisible(false);
			restAddPane.toFront();
			restAddPane.setVisible(true);

			final Text actiontarget = new Text();
			addGrid.add(actiontarget, 0, 8);
			actiontarget.setFill(Color.FIREBRICK);
			addButton.setOnAction(event -> {

				// restaurant details validation
				if (restName.getText().equals("") || addr1.getText().equals("") || city.getText().equals("")
						|| zipCode.getText().equals("") || email.getText().equals("") || phone.getText().equals("")) {
					actiontarget.setText("Please enter mandatory details");

				} else if (phone.getText().matches(".*[^0-9].*") || zipCode.getText().matches(".*[^0-9].*")) {
					actiontarget.setText("Please enter valid phone number and zipcode in numbers");
				} else if ((phone.getText().length()) > 10 || (zipCode.getText().length()) > 10) {
					actiontarget.setText("Phone number and zipcode should have less than or equal to 10 digits");
				}

				else {
					rest.setRestaurantName(restName.getText());
					rest.setRestaurantDescription(restDesc.getText());
					rest.setAddress1(addr1.getText());
					rest.setAddress2(addr2.getText());
					rest.setCity(city.getText());
					if (!zipCode.getText().equalsIgnoreCase(""))
						rest.setZipcode(Integer.parseInt(zipCode.getText()));
					else
						rest.setZipcode(0);
					rest.setEmail(email.getText());
					rest.setPhone(phone.getText());

					// insert restaurant details in db
					try {
						rest.setRestaurantId(restaurantDB.add(rest));
						if (rest.getRestaurantId() != 0) {
							new Dialogs().showActionStatus(rest.getRestaurantId(), "ID Restaurant added successfully!");
							restName.clear();
							restDesc.clear();
							addr1.clear();
							addr2.clear();
							city.clear();
							zipCode.clear();
							email.clear();
							phone.clear();
							actiontarget.setText("");
						}
					} catch (Exception e1) {
						log.error(e1.getMessage(), e1);
					}
				}
			}); // add button action ends

		});// addmenuItem action ends

		// Update Restaurant menu button action
		updateRestButton.setOnAction(e -> {
			log.debug("Update Restaurant Menu Processing");
			restViewPane.setVisible(false);
			restUpdatePane.toFront();
			restUpdatePane.setVisible(true);

			UresultTitle.setVisible(false);
			updateGrid.setVisible(false);
			updateButton.setVisible(false);

			// Search by restaurant ID
			updtSearchButton.setOnAction(ev -> {
				if (updtrest.getText().equals("") || updtrest.getText().matches(".*[^0-9].*"))
					new Dialogs().showWarning("Please enter a valid restaurant ID");
				else {
					int searchRestID = Integer.parseInt(updtrest.getText());
					log.debug("Searching for update" + searchRestID);

					try {
						rest = restaurantDB.find(searchRestID);
						if (rest.getRestaurantId() != 0) {
							updateButton.setDisable(false);
							UresultTitle.setVisible(true);
							updateGrid.setVisible(true);
							updateButton.setVisible(true);

							// enable textfields for edit
							new TextfieldControl().enableTextField(UrestName, rest.getRestaurantName());
							new TextfieldControl().enableTextField(UrestDesc, rest.getRestaurantDescription());
							new TextfieldControl().enableTextField(Uaddr1, rest.getAddress1());
							new TextfieldControl().enableTextField(Uaddr2, rest.getAddress2());
							new TextfieldControl().enableTextField(Ucity, rest.getCity());
							new TextfieldControl().enableTextField(UzipCode, Integer.toString(rest.getZipcode()));
							new TextfieldControl().enableTextField(Uemail, rest.getEmail());
							new TextfieldControl().enableTextField(Uphone, rest.getPhone());
						} else {
							new Dialogs().showError(searchRestID,
									"ID Restaurant Not Found! Please verify restaurant ID entered.");
						}
					} catch (Exception e1) {
						log.error(e1.getMessage(), e1);
					}

					// update button action
					updateButton.setOnAction(eve -> {
						if (new Dialogs().showConfirmation(searchRestID,
								"Are you sure want to update details for restaurant") == true) {
							try {
								rest.setRestaurantId(searchRestID);
								rest.setRestaurantName(new TextfieldControl().get_Text_PromptText(UrestName));
								rest.setRestaurantDescription(new TextfieldControl().get_Text_PromptText(UrestDesc));
								rest.setAddress1(new TextfieldControl().get_Text_PromptText(Uaddr1));
								rest.setAddress2(new TextfieldControl().get_Text_PromptText(Uaddr2));
								rest.setCity(new TextfieldControl().get_Text_PromptText(Ucity));
								rest.setZipcode(Integer.valueOf(new TextfieldControl().get_Text_PromptText(UzipCode)));
								rest.setEmail(new TextfieldControl().get_Text_PromptText(Uemail));
								rest.setPhone(new TextfieldControl().get_Text_PromptText(Uphone));
							} catch (NumberFormatException n) {
								log.error(n.getMessage(), n);
							}
							try {
								restaurantDB.update(rest);
							} catch (Exception e1) {
								log.error(e1.getMessage(), e1);
							}

							// disable textfields after edit
							new TextfieldControl().disableTextField(UrestName, rest.getRestaurantName());
							new TextfieldControl().disableTextField(UrestDesc, rest.getRestaurantDescription());
							new TextfieldControl().disableTextField(Uaddr1, rest.getAddress1());
							new TextfieldControl().disableTextField(Uaddr2, rest.getAddress2());
							new TextfieldControl().disableTextField(Ucity, rest.getCity());
							new TextfieldControl().disableTextField(UzipCode, Integer.toString(rest.getZipcode()));
							new TextfieldControl().disableTextField(Uemail, rest.getEmail());
							new TextfieldControl().disableTextField(Uphone, rest.getPhone());

							new Dialogs().showActionStatus(searchRestID, "ID Restaurant updated successfully!");
							updateButton.setDisable(true);
							updtrest.clear();

						}
					});// updatebutton action ends
				}
			});// update search button event ends

		});// updatemenu button ends

		// Delete restaurant menu button action
		deleteRestButton.setOnAction(e -> {
			log.debug("Delete Restaurant Menu Processing");
			restUpdatePane.setVisible(false);
			restViewPane.setVisible(false);
			restDeletePane.toFront();
			restDeletePane.setVisible(true);

			DresultTitle.setVisible(false);
			deleteGrid.setVisible(false);
			deleteButton.setVisible(false);

			// Search by restaurant ID for deletion
			deleteSearchButton.setOnAction(ev -> {
				if (deleterest.getText().equals("") || deleterest.getText().matches(".*[^0-9].*"))
					new Dialogs().showWarning("Please enter a valid restaurant ID");
				else {
					int searchRestID = Integer.parseInt(deleterest.getText());

					log.debug("Searching for delete" + searchRestID);
					try {
						rest = restaurantDB.find(searchRestID);
						if (rest.getRestaurantId() != 0) {

							DresultTitle.setVisible(true);
							deleteGrid.setVisible(true);
							deleteButton.setVisible(true);

							new TextfieldControl().disableTextField(DrestName, rest.getRestaurantName());
							new TextfieldControl().disableTextField(DrestDesc, rest.getRestaurantDescription());
							new TextfieldControl().disableTextField(Daddr1, rest.getAddress1());
							new TextfieldControl().disableTextField(Daddr2, rest.getAddress2());
							new TextfieldControl().disableTextField(Dcity, rest.getCity());
							new TextfieldControl().disableTextField(DzipCode, Integer.toString(rest.getZipcode()));
							new TextfieldControl().disableTextField(Demail, rest.getEmail());
							new TextfieldControl().disableTextField(Dphone, rest.getPhone());
						} else {
							new Dialogs().showError(searchRestID,
									"ID Restaurant Not Found! Please verify restaurant ID entered.");
						}
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.getMessage();
					}

					// delete button action
					deleteButton.setOnAction(eve -> {

						if (new Dialogs().showConfirmation(searchRestID,
								"Are you sure want to delete the restaurant? Menu associated with the restaurant will also be deleted.") == true) {
							try {
								restaurantDB.delete(searchRestID);
								DresultTitle.setVisible(false);
								deleteGrid.setVisible(false);
								deleteButton.setVisible(false);
							} catch (Exception e1) {
								// TODO Auto-generated catch block
								e1.getMessage();
							}
							new Dialogs().showActionStatus(searchRestID, "ID Restaurant deleted successfully!");
							deleterest.clear();
						}
					});// delete button action ends
				}
			});// delete search button action ends
		});// deletRestButton action ends
	}

}