package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Product class objects and parts.
 */

public class Product {
    public static ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    public Product(int id, String name, double price, int stock, int min, int max) {

        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @param price
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * @param stock
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * @param min
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * @param max
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * @return price
     */
    public double getPrice() {
        return price;
    }

    /**
     * @return stock
     */
    public int getStock() {
        return stock;
    }

    /**
     * @return min
     */
    public int getMin() {
        return min;
    }

    /**
     * @return max
     */
    public int getMax() {
        return max;
    }

    /**
     * Adds part to the associated parts list.
     * @param part
     */
    public void addAssociatedParts(Part part) {
        associatedParts.add(part);
    }

    /**
     * Removes part from the list.
     * @param selectedAssociatedPart
     * @return true
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart) {
        associatedParts.remove(selectedAssociatedPart);
        return true;
    }

    /**
     * Static method that Removes a part from the observable list.
     * @param selectedAssociatedPart
     * @return true
     */
    public static boolean removeAssociatedPart(Part selectedAssociatedPart) {
        associatedParts.remove(selectedAssociatedPart);
        return true;
    }

    /**
     * Returns all associated parts for selected product.
     * @return associatedParts
     */
    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
    }
}




