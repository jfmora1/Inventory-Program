package controller;

import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
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
import java.util.function.Predicate;

/**
 * Controller class for the Main screen Inventory Program
 */
public class MainScreen implements Initializable {
    public TableView<Part> PartTable;
    public TableColumn PartInventCol;
    public TableColumn PartPriceCol;
    public TableView<Product> ProductTable;
    public TableColumn ProductIDCol;
    public TableColumn ProductNameCol;
    public TableColumn ProductInvenCol;
    public TableColumn ProductPriceCol;
    public Button ModifyA;
    public Button DeleteA;
    public Button AddA;
    public Button DeleteB;
    public Button ModifyB;
    public Button AddB;
    public Button ExitA;
    public TableColumn PartIDCol;
    public TableColumn PartNameCol;
    public TextField SearchPart;
    public TextField SearchProduct;
    private static Part selectedPart;
    private static Product selectedProduct;

    public static Part getSelectedPart() {
        return selectedPart;
    }

    public static Product getSelectedProduct() {
        return selectedProduct;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        //Setting Inventory into Tables
        PartTable.setItems(Inventory.getAllparts());
        ProductTable.setItems(Inventory.getAllproducts());

        // Initializes the columns
        PartIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        PartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        PartInventCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        PartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        ProductIDCol.setCellValueFactory(new PropertyValueFactory<>("productID"));
        ProductNameCol.setCellValueFactory(new PropertyValueFactory<>("productName"));
        ProductInvenCol.setCellValueFactory(new PropertyValueFactory<>("productStock"));
        ProductPriceCol.setCellValueFactory(new PropertyValueFactory<>("productPrice"));

        //setup for searchPart
        //initially displays all data ( wrapping observable list in a filtered list)
        FilteredList<Part> filteredData = new FilteredList<>(Inventory.getAllparts(), p -> true);
        SearchPart.textProperty().addListener((observableValue, oldValue, newValue) -> {
            filteredData.setPredicate((Predicate<? super Part>) Parts -> {

                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();
                String StringID = String.valueOf(Parts.getId());


                if (StringID.contains(newValue)) {
                    return true; // filter matches name
                } else if (Parts.getName().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else
                    return false; // does not match
            });
            if (filteredData.size() == 0) {
                AlertMessages.emptySearchError();
            }
        });

        //wrap filtered list in sorted list
        SortedList<Part> sortedData = new SortedList<>(filteredData);
        //Bind sortedList comparator to tableview  comparator
        sortedData.comparatorProperty().bind(PartTable.comparatorProperty());
        //add sorted/ filtered data to table
        PartTable.setItems(sortedData);


        //setup for searchProduct
        //initially displays all data ( wrapping observable list in a filtered list)
        FilteredList<Product> filteredData2 = new FilteredList<>(Inventory.getAllproducts(), p -> true);

        SearchProduct.textProperty().addListener((observableValue, oldValue, newValue) -> {
            filteredData2.setPredicate((Predicate<? super Product>) Products -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();
                String StringProductID = String.valueOf(Products.getProductID());


                if (StringProductID.contains(newValue)) {
                    return true; // filter matches name
                } else if (Products.getProductName().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else
                    return false; // does not match
            });
            if (filteredData2.size() == 0) {
                AlertMessages.emptySearchError();
            }
        });
        //wrap filtered list in sorted list
        SortedList<Product> sortedData2 = new SortedList<>(filteredData2);
        //Bind sortedList comparator to tableview  comparator
        sortedData2.comparatorProperty().bind(ProductTable.comparatorProperty());
        //add sorted/ filtered data to table
        ProductTable.setItems(sortedData2);

    }

    public void OnModifyA(ActionEvent actionEvent) throws IOException {

        selectedPart = PartTable.getSelectionModel().getSelectedItem();

        if (selectedPart == null) {
            return;
        }

        System.out.println("On ModifyA Clicked");
        Parent root = FXMLLoader.load(getClass().getResource("/view/ModifyPart.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 600, 600);
        stage.setTitle("Modify Part");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * This method deletes the part selected/highlighted.
     * Runtime Error: A runtime error displayed if there was no part selected and delete action was invoked.
     * Error was resolved by if selected part was null it returned without invoking action
     */
    public void OnDeleteA(ActionEvent actionEvent) {
        System.out.println("On DeleteA Clicked");

        Part SP = PartTable.getSelectionModel().getSelectedItem();

        if (SP == null)
            return;
        if (AlertMessages.deleteConfirmation() == true) {
            Inventory.getAllparts().remove(SP);
        }
    }

    public void OnAddA(ActionEvent actionEvent) throws IOException {
        System.out.println("On AddA Clicked");
        Parent root = FXMLLoader.load(getClass().getResource("/view/AddPart.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 600, 600);
        stage.setTitle("Add Part");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * This method deletes the part selected/highlighted.
     * Runtime Error: A runtime error displayed if there was no part selected and delete action was invoked.
     * Error was resolved by if selected part was null it returned without invoking action
     */
    public void OnDeleteB(ActionEvent actionEvent) {
        System.out.println("On DeleteB Clicked");

        Product SP = ProductTable.getSelectionModel().getSelectedItem();

        if (SP == null)
            return;

        if (SP.getAllAssociatedParts().size() > 0) {
            AlertMessages.PartAssociatedError();
            return;
        }

        if (AlertMessages.deleteConfirmation() == true) {
            Inventory.getAllproducts().remove(SP);
        }
    }

    public void OnModifyB(ActionEvent actionEvent) throws IOException {
        selectedProduct = ProductTable.getSelectionModel().getSelectedItem();

        if (selectedProduct == null) {
            return;
        }

        System.out.println("On ModifyB Clicked");
        Parent root = FXMLLoader.load(getClass().getResource("/view/ModifyProduct.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 900, 600);
        stage.setTitle("Modify Product");
        stage.setScene(scene);
        stage.show();
    }

    public void OnAddB(ActionEvent actionEvent) throws IOException {
        System.out.println("On AddB Clicked");
        Parent root = FXMLLoader.load(getClass().getResource("/view/AddProduct.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 900, 600);
        stage.setTitle("Add Product");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Exit Method.
     * This method invokes exit button action to exit the program.
     */
    public void OnExitA(ActionEvent actionEvent) {
        System.out.println("On ExitA Clicked");
        System.exit(0);
    }
}
