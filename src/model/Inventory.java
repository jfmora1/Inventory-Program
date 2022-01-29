package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * This is the Inventory class
 */
public class Inventory {

    private static ObservableList<Part> allparts = FXCollections.observableArrayList();
    private static ObservableList<Product> allproducts = FXCollections.observableArrayList();
    private static ObservableList<InHouse> allInHouse = FXCollections.observableArrayList();
    private static ObservableList<OutSourced> allOutSourced = FXCollections.observableArrayList();


    public static void addPart(Part partAdd) {
        if (partAdd != null)
            allparts.add(partAdd);
    }

    public static void updatePart(int index, Part selectedPart) {
        allparts.set(index, selectedPart);

    }

    public static boolean DeletePart(Part deletePart) {
        if (allparts.contains(deletePart)) {
            allparts.remove(deletePart);
            return true;
        } else {

            return false;
        }
    }

    public static Part lookupPart(int partID) {

        Part found = null;

        for (Part foundPart : allparts) {
            if (foundPart.getId() == partID) {
                found = foundPart;
            }
        }
        return found;

    }

    public static ObservableList<Part> lookupPart(String partName) {
        ObservableList<Part> foundParts = FXCollections.observableArrayList();

        for (Part part : allparts) {
            if (part.getName().equals(partName)) {
                foundParts.add(part);
            }
        }
        return foundParts;
    }

    public static ObservableList<InHouse> getAllInHouse() {
        return allInHouse;
    }


    public static ObservableList<Part> getAllparts() {
        return allparts;
    }

    public static void addProduct(Product addProduct) {
        if (addProduct != null) {
            allproducts.add(addProduct);
        }
    }

    public static Product lookupProduct(int productID) {
        Product productFound = null;

        for (Product product : allproducts) {
            if (product.getProductID() == productID) {
                productFound = product;
            }
        }
        return productFound;
    }

    public static ObservableList<Product> lookupProduct(String productName) {
        ObservableList<Product> foundProduct = FXCollections.observableArrayList();

        for (Product product : allproducts) {
            if (product.getProductName().equals(productName)) {
                foundProduct.add(product);
            }
        }
        return foundProduct;
    }

    public static void UpdateProduct(int index, Product selectedProduct) {
        allproducts.set(index, selectedProduct);
    }

    public static boolean DeleteProduct(Product deleteProduct) {
        if (allproducts.contains(deleteProduct)) {
            allproducts.remove(deleteProduct);
            return true;
        } else {

            return false;
        }
    }

    public static ObservableList<Product> getAllproducts() {
        return allproducts;
    }


    static {
        addTestData();
    }

    public static void addTestData() {

        //        Initialized Products and Parts
        Inventory.addProduct(new Product(GenerateProductID(), "Bicycle", 200, 4, 0, 20));
        Inventory.addProduct(new Product(GenerateProductID(), "Motorcycle", 500, 10, 1, 20));
        Inventory.addProduct(new Product(GenerateProductID(), "Product 3", 600, 20, 1, 25));
        Inventory.addPart(new InHouse(GeneratePartId(), "Brakes", 200, 4, 0, 20, 123));
        Inventory.addPart(new InHouse(GeneratePartId(), "Tire", 500, 10, 1, 20, 321));
        Inventory.addPart(new OutSourced(GeneratePartId(), "Part 3", 250, 20, 1, 30, "Abe's Company"));

    }


    public static int PartID;
    public static int ProductID;

    public static int GeneratePartId() {
        return PartID += 2;
    }

    public static int GenerateProductID() {
        return ProductID += 3;
    }


}
