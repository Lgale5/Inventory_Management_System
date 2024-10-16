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
 * The ModifyProductController Class.
 */

public class ModifyProductController implements Initializable {

    private ObservableList<Part> associatedPartsList = FXCollections.observableArrayList();

    @FXML
    private TableView<Product> mainProductsTable;
    @FXML
    private TextField modifyProductSearch;
    @FXML
    private Button modifyProductSearchButton;
    @FXML
    private Button modifyProductCancelButton;
    @FXML
    private Button modifyProductSaveButton;
    @FXML
    private Button removeAssociatedPartButton;
    @FXML
    private Button modifyProductmodifyButton;
    @FXML
    private TextField addProductMachineID;
    @FXML
    private TextField modifyProductID;
    @FXML
    private TextField modifyProductName;
    @FXML
    private TextField modifyProductInventory;
    @FXML
    private TextField modifyProductPrice;
    @FXML
    private TextField modifyProductMax;
    @FXML
    private TextField modifyProductMin;
    @FXML
    private TableView<Part> modifyProductTable;
    @FXML
    private TableColumn<?, ?> modifyProductPartIDCol;
    @FXML
    private TableColumn<?, ?> modifyPartNameCol;
    @FXML
    private TableColumn<?, ?> modifyProductInventoryCol;
    @FXML
    private TableColumn<?, ?> modifyProductPriceCol;
    @FXML
    private TableView<Part> associatedProductTable;
    @FXML
    private TableColumn<?, ?> associatedProductIDCol;
    @FXML
    private TableColumn<?, ?> associatedPartNameCol;
    @FXML
    private TableColumn<?, ?> associatedInventoryCol;
    @FXML
    private TableColumn<?, ?> associatedPriceCol;
    private int currentIndex = 0;

    /**
     * ModifyPartController class.
     *
     * @param event
     */

    /**
     * Receive the information from the main screen.
     *
     * @param selectedIndex
     * @param product
     */

    @FXML
    public void sendProduct(int selectedIndex, Product product) {

        currentIndex = selectedIndex;
        modifyProductID.setText(String.valueOf(product.getId()));
        modifyProductName.setText(String.valueOf(product.getName()));
        modifyProductInventory.setText(String.valueOf(product.getStock()));
        modifyProductPrice.setText(String.valueOf(product.getPrice()));
        modifyProductMax.setText(String.valueOf(product.getMax()));
        modifyProductMin.setText(String.valueOf(product.getMin()));

        for (Part part : product.getAllAssociatedParts()) {
            associatedPartsList.add(part);
        }
    }

    /**
     * Create and fill the tables.
     *
     * @param url
     * @param resourceBundle
     */
    public void initialize(URL url, ResourceBundle resourceBundle) {

        modifyProductTable.setItems(Inventory.getAllParts());
        modifyProductPartIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        modifyPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        modifyProductInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        modifyProductPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        //add parts to associated table below

        associatedProductTable.setItems(associatedPartsList);
        associatedProductIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

    }

    /**
     * Add the part that the user has selected to the associated parts table.
     *
     * @param event
     */
    @FXML
    void addPartProductModify(ActionEvent event) {
        Part selectedPart = modifyProductTable.getSelectionModel().getSelectedItem();

        if (selectedPart == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Input Error!");
            alert.setContentText("Please select a part from list");
            alert.showAndWait();
            return;
        } else if (!associatedPartsList.contains(selectedPart)) {
            associatedPartsList.add(selectedPart);
            associatedProductTable.setItems(associatedPartsList);
        }
    }

    /**
     * Remove the part that the user has selected from the associated parts table.
     *
     * @param event
     */
    @FXML
    void removeAssociatedPartsButton(ActionEvent event) {
        Part selectedPart = associatedProductTable.getSelectionModel().getSelectedItem();

        if (selectedPart == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Input Error");
            alert.setContentText("Select part from list");
            alert.showAndWait();
            return;
        } else if (associatedPartsList.contains(selectedPart)) {
            Product.removeAssociatedPart(selectedPart);
            associatedPartsList.remove(selectedPart);
            associatedProductTable.setItems(associatedPartsList);
        }
    }

    /**
     * Save the product event.
     *
     * @param event`
     */

    @FXML
    void productSavePush(ActionEvent event) throws IOException {
        try {
            int id = Integer.parseInt(modifyProductID.getText());
            String name = modifyProductName.getText();
            int stock = Integer.parseInt(modifyProductInventory.getText());
            double price = Double.parseDouble(modifyProductPrice.getText());
            int max = Integer.parseInt(modifyProductMax.getText());
            int min = Integer.parseInt(modifyProductMin.getText());

            if (stock > max || stock < min) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Inventory must be within min and max.");
                alert.showAndWait();
                return;
            } else if (min >= max) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Max must be greater than min");
                alert.showAndWait();
                return;
            }
            Product updatedProduct = new Product(id, name, price, stock, min, max);
            if (updatedProduct != associatedPartsList) {
                Inventory.updateProduct(currentIndex, updatedProduct);
            }


            for (Part part : associatedPartsList) {
                if (part != associatedPartsList)
                    updatedProduct.addAssociatedParts(part);
            }

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
     * Search for parts to add to the associated parts table
     * using text or ID in search box by pressing enter.
     *
     * @param event
     */
    @FXML
    void modifyProductPartSearch(ActionEvent event) {
        String searchText = modifyProductSearch.getText();
        ObservableList<Part> results = Inventory.lookupPart(searchText);
        try {
            while (results.size() == 0) {
                int partID = Integer.parseInt(searchText);
                results.add(Inventory.lookupPart(partID));
            }
            modifyProductTable.setItems(results);
        } catch (NumberFormatException e) {
            Alert noParts = new Alert(Alert.AlertType.ERROR);
            noParts.setTitle("Error Message!");
            noParts.setContentText("Part was not found");
            noParts.showAndWait();
        }
    }
    @FXML
    public void modifyProductCancel(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../views/MainScreen.fxml"));
        Scene scene = new Scene(root);
        Stage MainScreenReturn = (Stage) ((Node) event.getSource()).getScene().getWindow();
        MainScreenReturn.setScene(scene);
        MainScreenReturn.show();
    }
}
