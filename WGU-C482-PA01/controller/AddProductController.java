package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Inventory;
import model.Part;
import model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


/**
 * The AddProductController Class adds to or modifies the product inventory.
 */

public class AddProductController implements Initializable {

    private ObservableList<Part> associatedPartsList = FXCollections.observableArrayList();

    @FXML
    private TextField addProductSearch;
    @FXML
    private Button addProductSearchButton;
    @FXML
    private Button addProductCancelButton;
    @FXML
    private Button addProductSaveButton;
    @FXML
    private Button removeAssociatedPartButton;
    @FXML
    private Button addProductAddButton;
    @FXML
    private Button removeAssocPartButton;
    @FXML
    private TextField addProductID;
    @FXML
    private TextField addProductName;
    @FXML
    private TextField addProductInventory;
    @FXML
    private TextField addProductPrice;
    @FXML
    private TextField addProductMax;
    @FXML
    private TextField addProductMin;
    @FXML
    private TableView<Part> associatedProductTable;
    @FXML
    private TableColumn<?, ?> associatedProductIDCol;
    @FXML
    private TableColumn<?, ?> associatedPartNameCol;
    @FXML
    private TableColumn<?, ?> associatedInventoryCol;
    @FXML
    private TableColumn<?, ?> associatedCostCol;
    @FXML
    private TableView<Part> addProductTable;
    @FXML
    private TableColumn<?, ?> addProductPartIDCol;
    @FXML
    private TableColumn<?, ?> addPartNameCol;
    @FXML
    private TableColumn<?, ?> addProductInventoryCol;
    @FXML
    private TableColumn<?, ?> addProductCostCol;

    /**
     * Save new product.
     *
     * @param event
     */
    @FXML
    void productSavePushed(ActionEvent event) throws IOException {
        try {

            // Create random number between 0.0 - 1.0 and * 10000 to create a unique number
            int uniqueID = (int) (Math.random() * 10000);

            // Get input
            String name = addProductName.getText();
            int stock = Integer.parseInt(addProductInventory.getText());
            double price = Double.parseDouble(addProductPrice.getText());
            int max = Integer.parseInt(addProductMax.getText());
            int min = Integer.parseInt(addProductMin.getText());

            // Set Requirements for the inventory
            if (min > stock || stock > max) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Inventory requirements: Inventory must be within min and max.");
                alert.showAndWait();
            } else if (min >= max) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Inventory requirements: maximum must be greater than minimum");
                alert.showAndWait();
            }

            Product product = new Product(uniqueID, name, price, stock, min, max);

            for (Part part : associatedPartsList) {
                if (part != associatedPartsList)
                    product.addAssociatedParts(part);
            }

            Inventory.getAllProducts().add(product);

            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Object scene = FXMLLoader.load(getClass().getResource("../views/MainScreen.fxml"));
            stage.setScene(new Scene((Parent) scene));
            stage.show();

        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Input Error!");
            alert.setContentText("Incorrect value entered");
            alert.showAndWait();
            return;
        }
    }

    /**
     * On Cancel button press, go to the main screen.
     *
     * @param event
     */
    @FXML
    public void addProductCancel(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../views/MainScreen.fxml"));
        Scene scene = new Scene(root);
        Stage MainScreenReturn = (Stage) ((Node) event.getSource()).getScene().getWindow();
        MainScreenReturn.setScene(scene);
        MainScreenReturn.show();
    }

    /**
     * Add the part to the observable list using abstract part class.
     *
     * @param event
     */

    @FXML
    void addProductAdd(ActionEvent event) {
        Part selectedPart = addProductTable.getSelectionModel().getSelectedItem();

        if (selectedPart == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Input Error!");
            alert.setContentText("Select a part from the list above.");
            alert.showAndWait();
            return;
        } else if (!associatedPartsList.contains(selectedPart)) {
            associatedPartsList.add(selectedPart);
            associatedProductTable.setItems(associatedPartsList);
        }
    }

    /**
     * Remove the part from the observable list using abstract part class.
     *
     * @param event
     */
    @FXML
    void removeAssocPartButton(ActionEvent event) {
        Part selectedPart = associatedProductTable.getSelectionModel().getSelectedItem();

        if (selectedPart == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Input Error!");
            alert.setContentText("Select a part from the list above.");
            alert.showAndWait();
            return;
        } else if (associatedPartsList.contains(selectedPart)) {
            associatedPartsList.remove(selectedPart);
            associatedProductTable.setItems(associatedPartsList);
        }
    }

    /**
     * Initialize and populate the table with products and associated parts.
     *
     * @param resourceBundle
     * @param url
     */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        addProductTable.setItems(Inventory.getAllParts());
        addProductPartIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        addPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        addProductInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        addProductCostCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        //add parts to associated table (bottom)
        associatedProductTable.setItems(associatedPartsList);
        associatedProductIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedCostCol.setCellValueFactory(new PropertyValueFactory<>("price"));

    }

    /**
     * Implement search function.
     *
     * @param event
     */
    @FXML
    void addProductPartSearch(ActionEvent event) {
        String searchText = addProductSearch.getText();
        ObservableList<Part> results = Inventory.lookupPart(searchText);
        try {
            while (results.size() == 0) {
                int partID = Integer.parseInt(searchText);
                results.add(Inventory.lookupPart(partID));
            }
            addProductTable.setItems(results);
        } catch (NumberFormatException e) {
            Alert noParts = new Alert(Alert.AlertType.ERROR);
            noParts.setTitle("Error Message!");
            noParts.setContentText("Part was not found.");
            noParts.showAndWait();
        }
    }

}
