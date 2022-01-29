package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * This is the Product Class
 */
public class Product {
    private int productID;
    private String productName;
    private double productPrice;
    private int productStock;
    private int productMin;
    private int productMax;
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    public Product(int id, String name, double price, int stock, int min, int max) {
        this.productID = id;
        this.productName = name;
        this.productPrice = price;
        this.productStock = stock;
        this.productMin = min;
        this.productMax = max;
    }


    /**
     * @return the id
     */
    public int getProductID() {
        return productID;
    }

    /**
     * @param id the id to set
     */
    public void setProductID(int id) {
        this.productID = id;
    }

    /**
     * @return the name
     */
    public String getProductName() {
        return productName;
    }

    /**
     * @param name the name to set
     */
    public void setProductName(String name) {
        this.productName = name;
    }

    /**
     * @return the price
     */
    public double getProductPrice() {
        return productPrice;
    }

    /**
     * @param price the price to set
     */
    public void setProductPrice(double price) {
        this.productPrice = price;
    }

    /**
     * @return the stock
     */
    public int getProductStock() {
        return productStock;
    }

    /**
     * @param stock the stock to set
     */
    public void setProductStock(int stock) {
        this.productStock = stock;
    }

    /**
     * @return the min
     */
    public int getProductMin() {
        return productMin;
    }

    /**
     * @param min the min to set
     */
    public void setProductMin(int min) {
        this.productMin = min;
    }

    /**
     * @return the max
     */
    public int getProductMax() {
        return productMax;
    }

    /**
     * @param max the max to set
     */
    public void setProductMax(int max) {
        this.productMax = max;
    }

    /**
     * @param part the part to set
     */
    public void addAssociatedPart(Part part) {
        associatedParts.add(part);
    }

    /**
     * @return the selected associated part
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart) {
        if (associatedParts.contains(selectedAssociatedPart)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * @return the all associated parts
     */
    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
    }


}

