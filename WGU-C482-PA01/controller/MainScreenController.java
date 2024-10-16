package controller;

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
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * The MainScreenController class provides the logic for the main screen, the main user interface, and
 * the features allow for useability in the inventory system.
 */

public class MainScreenController implements Initializable {

    @FXML
    private TextField partSearch;
    @FXML
    private TableColumn<Part, Integer> partIDCol;
    @FXML
    private TableColumn<Part, String> partNameCol;
    @FXML
    private TableColumn<Part, Integer> partInventoryCol;
    @FXML
    private TableColumn<Part, Double> partPriceCol;
    @FXML
    private TextField productSearch;
    @FXML
    private Button addProductButton;
    @FXML
    private Button modifyProductButton;
    @FXML
    private Button deleteProductButton;
    @FXML
    private TableView<Product> productTable;
    @FXML
    private TableColumn<Product, Integer> productIDCol;
    @FXML
    private TableColumn<Product, String> productNameCol;
    @FXML
    private TableColumn<Product, Integer> productInventoryCol;
    @FXML
    private TableColumn<Product, Double> productPriceCol;
    @FXML
    private TableView<Product> mainProductsTable;
    @FXML
    private TableView<Part> mainPartsTable;
    @FXML
    private TableView<Part> modifyProductTable;
    @FXML
    private TableColumn<Part, Integer> partsTablePartID;
    @FXML
    private TableColumn<Part, String> partsTablePartName;
    @FXML
    private TableColumn<Part, Integer> partsTableInventoryCount;
    @FXML
    private TableColumn<Part, Integer> partsTablePPU;
    @FXML
    private TableColumn<Product, Integer> productsTablePartID;
    @FXML
    private TableColumn<Product, String> productsTablePartName;
    @FXML
    private TableColumn<Product, Integer> productsTableInventoryCount;
    @FXML
    private TableColumn<Product, Integer> productsTablePPU;
    @FXML
    private Button modifyPartButton;
    @FXML
    private Button addPartButton;
    @FXML
    private Button deletePartButton;
    @FXML
    private Button exitMain;
    @FXML
    private TableView<Part> partsTableView;

    Stage stage;

    /**
     * Implement AddPart.fxml. UI for adding a part to inventory.
     *
     * @param event
     */
    @FXML
    void MainAddPartsClick(ActionEvent event) throws IOException {

        Parent addParts = FXMLLoader.load(getClass().getResource("../views/AddPart.fxml"));
        Scene scene = new Scene(addParts);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    /**
     * Loads the ModifyPart controller.
     *
     * @param event
     */
    @FXML
    void MainPartsClick(ActionEvent event) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../views/ModifyPart.fxml"));
            loader.load();

            ModifyPartController MPController = loader.getController();
            MPController.sendPart(mainPartsTable.getSelectionModel().getSelectedIndex(), mainPartsTable.getSelectionModel().getSelectedItem());

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        } catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please select a part first");
            alert.show();
        }
    }

    /**
     * Loads the ModifyProduct controller.
     *
     * @param event
     */
    @FXML
    void MainModifyProductsClick(ActionEvent event) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../views/ModifyProduct.fxml"));
            loader.load();

            ModifyProductController MPController = loader.getController();
            MPController.sendProduct(mainProductsTable.getSelectionModel().getSelectedIndex(), mainProductsTable.getSelectionModel().getSelectedItem());

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        } catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Select a product to modify");
            alert.show();
        }
    }

    /**
     * Loads the AddProduct controller.
     *
     * @param event
     */
    @FXML
    void MainAddProductsClick(ActionEvent event) throws IOException {

        Parent addParts = FXMLLoader.load(getClass().getResource("../views/AddProduct.fxml"));
        Scene scene = new Scene(addParts);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    /**
     * Create and populate the tables.
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        mainPartsTable.setItems(Inventory.getAllParts());
        partIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        mainProductsTable.setItems(Inventory.getAllProducts());
        productIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

    }

    /**
     * Deletes the selected part.
     *
     * @param event
     */
    @FXML
    void mainDeletePartButton(ActionEvent event) {
        Part selectedPart = mainPartsTable.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Are you sure?");
        alert.setContentText("Do you really want to delete this part?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            Inventory.deletePart(selectedPart);
        }
    }

    /**
     * Deletes the selected product while checking it for associated parts. If there are associated
     * parts when deleting, an Error will occur.
     *
     * @param event
     */

    @FXML
    void mainDeleteProductButton(ActionEvent event) {
        Product selectedProduct = mainProductsTable.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Are you sure?");
        alert.setContentText("Do you really want to delete this part?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            Product selectedDeleteProduct = mainProductsTable.getSelectionModel().getSelectedItem();
            if (selectedDeleteProduct.getAllAssociatedParts().size() > 0) {
                Alert cantDelete = new Alert(Alert.AlertType.ERROR);
                cantDelete.setTitle("Error Message!");
                cantDelete.setContentText("Please remove associated parts before you delete the product.");
                cantDelete.showAndWait();
                return;
            }
            Inventory.deleteProduct(selectedProduct);
        }
    }

    /**
     * Search for parts using text or IDin search box by pressing enter.
     * FUTURE ENHANCEMENT: Search functions should not be case-sensitive.
     * This can cause end user confusion when searching for a part/product.
     * Currently, the search functions only return parts or products if you type the used upper/lower case character.
     *
     * @param event
     */
    @FXML
    void mainPartSearch(ActionEvent event) {
        String searchText = partSearch.getText();
        ObservableList<Part> results = Inventory.lookupPart(searchText);
        try {
            while (results.size() == 0) {
                int partID = Integer.parseInt(searchText);
                results.add(Inventory.lookupPart(partID));
            }
            mainPartsTable.setItems(results);
        } catch (NumberFormatException e) {
            Alert noParts = new Alert(Alert.AlertType.ERROR);
            noParts.setTitle("Error Message");
            noParts.setContentText("Part not found");
            noParts.showAndWait();
        }
    }

    /**
     * Search for parts using text or IDin search box by pressing enter.
     *
     * @param event
     */
    @FXML
    void mainProductSearch(ActionEvent event) {
        String searchText = productSearch.getText();
        ObservableList<Product> results = Inventory.lookupProduct(searchText);
        try {
            while (results.size() == 0) {
                int productID = Integer.parseInt(searchText);
                results.add(Inventory.lookupProduct(productID));
            }
            mainProductsTable.setItems(results);
        } catch (NumberFormatException e) {
            Alert noParts = new Alert(Alert.AlertType.ERROR);
            noParts.setTitle("Error Message");
            noParts.setContentText("Product was not found");
            noParts.showAndWait();
        }
    }

    /**
     * Exits the application.
     *
     * @param ExitButton
     */
    public void mainExitButton(ActionEvent ExitButton) {
        Stage stage = (Stage) ((Node) ExitButton.getSource()).getScene().getWindow();
        stage.close();
    }
}
