package com.itm.food.controller.admin;

import java.sql.ResultSet;

import org.apache.log4j.Logger;

import com.itm.food.controller.admin.components.DetailViewTable;
import com.itm.food.controller.admin.components.Dialogs;
import com.itm.food.controller.admin.components.TextfieldControl;
import com.itm.food.dao.Item;
import com.itm.food.dao.Restaurant;

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

public class MenuItemController extends AdminManager {

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
	private StackPane stackPaneMenu;
	@FXML
	private AnchorPane addMenuPane;
	@FXML
	private GridPane addGrid;
	@FXML
	private GridPane updateGrid;
	@FXML
	private GridPane deleteGrid;
	@FXML
	private AnchorPane menuViewPane;
	@FXML
	private AnchorPane menuAddPane;
	@FXML
	private AnchorPane menuUpdatePane;
	@FXML
	private AnchorPane menuDeletePane;
	@FXML
	private MenuButton actionButton;
	@FXML
	private MenuItem addMenuButton;
	@FXML
	private MenuItem updateMenuButton;
	@FXML
	private MenuItem deleteMenuButton;
	// View Page
	@FXML
	private SplitPane splitPaneView;
	@FXML
	private AnchorPane searchPane, resultsPane;
	@FXML
	private ScrollPane resultsScrollPane;
	@FXML
	private TextField restSearch;
	@FXML
	private Button menuSearchButton;
	// Add Page
	@FXML
	private TextField addmenuRest;
	@FXML
	private Label itemTitle;
	@FXML
	private Button addButton, restValButton;
	@FXML
	private TextField itemName, itemDesc, itemPrice;
	// Update Page
	@FXML
	private TextField updtitem, UitemName, UitemDesc, UitemPrice, UrestID;
	@FXML
	private Button UsearchItemButton, updateButton;
	@FXML
	private Label UitemTitle;
	// Delete page
	@FXML
	private TextField deleteitem, DitemName, DitemDesc, DitemPrice, DrestID;
	@FXML
	private Button DsearchItemButton, deleteButton;
	@FXML
	private Label ditemTitle;

	@SuppressWarnings("rawtypes")
	private TableView tableview;

	private static final Logger log = Logger.getLogger(MenuItemController.class);

	Item item = new Item();

	@SuppressWarnings("rawtypes")
	public void initManager(final AdminManager loginManager, String sessionID) {
		logoutButton.setOnAction(e -> loginManager.logout());
		homeButton.setOnAction(e -> loginManager.showMainView(sessionID));
		restaurantButton.setOnAction(e -> loginManager.showRestaurantsView(sessionID));
		menuButton.setOnAction(e -> loginManager.showMenuView(sessionID));
		driverButton.setOnAction(e -> loginManager.showDriversView(sessionID));
		reviewButton.setOnAction(e -> loginManager.showReviewsView(sessionID));
		profileButton.setOnAction(e -> loginManager.showProfile(sessionID));

		// toolbar button navigation calls
		logoutButton.setStyle("-fx-background-color: linear-gradient(lightgray,gray);");
		profileButton.setStyle("-fx-background-color: linear-gradient(lightgray,gray);");
		homeButton.setStyle("-fx-background-color: linear-gradient(lightgray,gray);");
		restaurantButton.setStyle("-fx-background-color: linear-gradient(lightgray,gray);");
		menuButton.setStyle("-fx-background-color: linear-gradient(lightgray,gray);");
		driverButton.setStyle("-fx-background-color: linear-gradient(lightgray,gray);");
		reviewButton.setStyle("-fx-background-color: linear-gradient(lightgray,gray);");

		// CSS for toolbar buttons
		actionButton.setStyle("-fx-background-color: linear-gradient(lightgray,gray);");
		menuSearchButton.setStyle("-fx-background-color: linear-gradient(lightgray,gray);");
		addButton.setStyle("-fx-background-color: linear-gradient(lightgray,gray);");
		restValButton.setStyle("-fx-background-color: linear-gradient(lightgray,gray);");
		UsearchItemButton.setStyle("-fx-background-color: linear-gradient(lightgray,gray);");
		updateButton.setStyle("-fx-background-color: linear-gradient(lightgray,gray);");
		DsearchItemButton.setStyle("-fx-background-color: linear-gradient(lightgray,gray);");
		deleteButton.setStyle("-fx-background-color: linear-gradient(lightgray,gray);");

		menuAddPane.setVisible(false);
		menuUpdatePane.setVisible(false);
		menuDeletePane.setVisible(false);

		log.debug("Menu Clicked");

		tableview = new TableView();

		// Search for Restaurant to display menu items list
		menuSearchButton.setOnAction(e -> {
			if (restSearch.getText().equals("") || restSearch.getText().matches(".*[^0-9].*"))
				new Dialogs().showWarning("Please enter a valid restaurant ID");
			else {
				int searchRestID = Integer.parseInt(restSearch.getText());
				log.debug("Searching for displaying items" + searchRestID);
				try {
					Restaurant restaurant = restaurantDB.find(searchRestID);
					if (restaurant.getRestaurantId() != 0) {
						ResultSet rs = null;
						rs = itemsDB.getAllItems(searchRestID);
						tableview = new DetailViewTable().viewList(rs);
						resultsScrollPane.setContent(tableview);
						tableview.prefHeightProperty().bind(resultsScrollPane.heightProperty());
						tableview.prefWidthProperty().bind(resultsScrollPane.widthProperty());
						rs.close();
					} else {
						new Dialogs().showError(searchRestID,
								"Restauarant Not Found! Please verify restaurant ID entered");
					}

				} catch (Exception ex) {
					ex.getMessage();
				}
			}
		});

		// Add menu Button click
		addMenuButton.setOnAction(ev -> {
			log.debug("Add Restaurant Menu Processing");
			menuViewPane.setVisible(false);
			menuAddPane.toFront();
			menuAddPane.setVisible(true);

			itemTitle.setVisible(false);
			addGrid.setVisible(false);
			addButton.setVisible(false);

			// Search for Restaurant in which to add item
			restValButton.setOnAction(e -> {
				if (addmenuRest.getText().equals("") || addmenuRest.getText().matches(".*[^0-9].*"))
					new Dialogs().showWarning("Please enter a valid restaurant ID");
				else {

					int searchRestID = Integer.parseInt(addmenuRest.getText());
					log.debug("Searching for adding items" + searchRestID);
					try {
						Restaurant restaurant = restaurantDB.find(searchRestID);
						if (restaurant.getRestaurantId() != 0) {
							itemTitle.setVisible(true);
							addGrid.setVisible(true);
							addButton.setVisible(true);
						} else {
							new Dialogs().showError(searchRestID,
									"Restaurant Not Found! Please verify restaurant ID entered Or Add Restaurant.");
						}
					} catch (Exception ex) {
						ex.getMessage();
					}
					final Text actiontarget = new Text();
					addGrid.add(actiontarget, 0, 8);
					actiontarget.setFill(Color.FIREBRICK);

					// add item to menu on add button click
					addButton.setOnAction(event -> {

						if (itemName.getText().equals("") || itemPrice.getText().equals("")) {
							actiontarget.setText("Please enter mandatory details");

						} else if (itemPrice.getText().matches(".*[^0-9.].*")) {
							actiontarget.setText("Please enter valid price value");
						} else {

							item.setRestaurantId(searchRestID);
							item.setItemName(itemName.getText());
							item.setItemDesc(itemDesc.getText());
							item.setItemPrice(Double.parseDouble(itemPrice.getText()));

							try {
								item.setItemId(itemsDB.add(item));
								if (item.getItemId() != 0) {
									new Dialogs().showActionStatus(item.getItemId(), "ID item added successfully!");
									itemName.clear();
									itemDesc.clear();
									itemPrice.clear();
								}
							} catch (Exception e1) {
								log.error(e1.getMessage(), e1);
							}
						}

					}); // add button action ends
				}
			}); // add search button action ends

		});// addmenuItem action ends

		// Update Item details menubutton click
		updateMenuButton.setOnAction(e -> {
			log.debug("Update Item Processing");
			menuViewPane.setVisible(false);
			menuUpdatePane.toFront();
			menuUpdatePane.setVisible(true);

			UitemTitle.setVisible(false);
			updateGrid.setVisible(false);
			updateButton.setVisible(false);

			// Search for item to update details
			UsearchItemButton.setOnAction(ev -> {
				if (updtitem.getText().equals("") || updtitem.getText().matches(".*[^0-9].*"))
					new Dialogs().showWarning("Please enter a valid item ID");
				else {
					int searchItemID = Integer.parseInt(updtitem.getText());
					log.debug("Searching for update" + searchItemID);
					try {
						updateButton.setDisable(false);
						item = itemsDB.find(searchItemID);

						if (item.getItemId() != 0) {
							UitemTitle.setVisible(true);
							updateGrid.setVisible(true);
							updateButton.setVisible(true);

							// enable textfields for edit
							new TextfieldControl().enableTextField(UitemName, item.getItemName());
							new TextfieldControl().enableTextField(UitemDesc, item.getItemDesc());
							new TextfieldControl().enableTextField(UitemPrice, Double.toString(item.getItemPrice()));
							new TextfieldControl().enableTextField(UrestID, Integer.toString(item.getRestaurantId()));
						} else {
							new Dialogs().showError(searchItemID, "Item Not Found! Please verify item ID entered.");
						}
					} catch (Exception e1) {
						log.error(e1.getMessage(), e1);
					}

					// update details in db on button click
					updateButton.setOnAction(eve -> {
						if (new Dialogs().showConfirmation(searchItemID,
								"Are you sure want to update details for item") == true) {

							item.setItemId(searchItemID);
							item.setItemName(new TextfieldControl().get_Text_PromptText(UitemName));
							item.setItemDesc(new TextfieldControl().get_Text_PromptText(UitemDesc));
							item.setItemPrice(
									Double.parseDouble(new TextfieldControl().get_Text_PromptText(UitemPrice)));
							item.setRestaurantId(Integer.parseInt(new TextfieldControl().get_Text_PromptText(UrestID)));

							try {
								itemsDB.update(item);
							} catch (Exception e1) {
								log.error(e1.getMessage(), e1);
							}
							// disable textfields after edit
							new TextfieldControl().disableTextField(UitemName, item.getItemName());
							new TextfieldControl().disableTextField(UitemDesc, item.getItemDesc());
							new TextfieldControl().disableTextField(UitemPrice, Double.toString(item.getItemPrice()));
							new TextfieldControl().disableTextField(UrestID, Integer.toString(item.getRestaurantId()));

							new Dialogs().showActionStatus(searchItemID, "ID Item updated successfully!");
							updateButton.setDisable(true);
							updtitem.clear();

						}
					});// updatebutton action ends
				}
			});// update search button event ends

		});// updatemenu button ends

		// Delete item menu button click
		deleteMenuButton.setOnAction(e -> {
			log.debug("Delete Item Processing");
			menuUpdatePane.setVisible(false);
			menuViewPane.setVisible(false);
			menuDeletePane.toFront();
			menuDeletePane.setVisible(true);

			ditemTitle.setVisible(false);
			deleteGrid.setVisible(false);
			deleteButton.setVisible(false);

			// Search for item to delete
			DsearchItemButton.setOnAction(ev -> {
				if (deleteitem.getText().equals("") || deleteitem.getText().matches(".*[^0-9].*"))
					new Dialogs().showWarning("Please enter a valid item ID");
				else {
					int searchItemID = Integer.parseInt(deleteitem.getText());

					log.debug("Searching for delete" + searchItemID);
					try {
						item = itemsDB.find(searchItemID);
						if (item.getItemId() != 0) {
							ditemTitle.setVisible(true);
							deleteGrid.setVisible(true);
							deleteButton.setVisible(true);

							// disable textfields
							new TextfieldControl().disableTextField(DitemName, item.getItemName());
							new TextfieldControl().disableTextField(DitemDesc, item.getItemDesc());
							new TextfieldControl().disableTextField(DitemPrice, Double.toString(item.getItemPrice()));
							new TextfieldControl().disableTextField(DrestID, Integer.toString(item.getRestaurantId()));

						} else {
							new Dialogs().showError(searchItemID, "ID Item Not Found! Please verify item ID entered.");
						}
					} catch (Exception e1) {
						log.error(e1.getMessage(), e1);
					}

					// delete item on button click
					deleteButton.setOnAction(eve -> {

						if (new Dialogs().showConfirmation(searchItemID, "Are you sure want to delete item") == true) {
							try {
								itemsDB.delete(searchItemID);
								ditemTitle.setVisible(false);
								deleteGrid.setVisible(false);
								deleteButton.setVisible(false);
							} catch (Exception e1) {
								log.error(e1.getMessage(), e1);
							}
							new Dialogs().showActionStatus(searchItemID, "ID item deleted successfully!");
							deleteitem.clear();
						}
					});
				}
			});
		});
	}
}