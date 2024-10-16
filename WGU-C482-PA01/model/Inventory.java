package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

/**
 * Inventory class stores the parts and products in observable lists.
 */
public class Inventory {

    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /**
     * Adds part to the allParts list.
     * @param newPart
     */
    public static void addPart(Part newPart) {

        allParts.add(newPart);
    }

    /**
     * Adds new product to the allProducts list.
     * @param newProduct
     */
    public static void addProduct(Product newProduct) {

        allProducts.add(newProduct);
    }

    /**
     * loops all parts and return part if found.
     * @param partID
     * @return part if found and null if no item is found.
     */
    public static Part lookupPart(int partID) {
        for (Part part : Inventory.getAllParts()) {
            while (part.getId() == partID) {
                return part;
            }
        }
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error!");
        alert.setHeaderText("No Item Found");
        alert.show();
        return null;
    }

    /**
     * Iterate through
     * @param productID
     * @return product if found and null if no item has been found.
     */
    public static Product lookupProduct(int productID) {
        for (Product product : Inventory.getAllProducts()) {
            while (product.getId() == productID)
                return product;
        }
        return null;
    }

    /**
     * Creates a list of filtered parts and returns the part if found using loop.
     * @param partName
     * @return filtered parts
     */
    public static ObservableList<Part> lookupPart(String partName) {
        ObservableList<Part> PartName = FXCollections.observableArrayList();

        for (Part part : allParts) {
            if (part.getName().contains(partName)) {
                PartName.add(part);
            }
        }
        return PartName;
    }

    /**
     * Creates a list of filtered product and returns the part if found.
     * @param productName
     * @return filteredProduct
     */
    public static ObservableList<Product> lookupProduct(String productName) {
        ObservableList<Product> ProductName = FXCollections.observableArrayList();

        for (Product product : allProducts) {
            if (product.getName().contains(productName)) {
                ProductName.add(product);
            }
        }
        return ProductName;
    }

    /**
     * Update the part with a list.
     * @param index
     * @param selectedPart
     */
    public static void updatePart(int index, Part selectedPart) {

        allParts.set(index, selectedPart);
    }

    /**
     * Update the product within a list.
     * @param index
     * @param selectedProduct
     */

    public static void updateProduct(int index, Product selectedProduct) {

        allProducts.set(index, selectedProduct);
    }


    /**
     * Delete a part within a list.
     * @param selectedPart
     */

    public static boolean deletePart(Part selectedPart) {
        if (allParts.contains(selectedPart)) {
            allParts.remove(selectedPart);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Delete a product within a list.
     * @param selectedProduct
     */
    public static boolean deleteProduct(Product selectedProduct) {
        if (allProducts.contains(selectedProduct)) {
            allProducts.remove(selectedProduct);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Returns the products in the all products list.
     * @return allProducts observablelist
     */
    public static ObservableList<Product> getAllProducts() {

        return allProducts;
    }

    /**
     * Returns the parts in the all parts list.
     * @return allParts observablelist
     */
    public static ObservableList<Part> getAllParts() {

        return allParts;
    }
}