package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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
 * Controller class for the Add Product scene
 */
public class Controller_AddProduct implements Initializable {


    public TextField AddProduct_SearchField;
    public TextField AddProduct_ID;
    public TextField AddProduct_Name;
    public TextField AddProduct_Inv;
    public TextField AddProduct_Price;
    public TextField AddProduct_Max;
    public TextField AddProduct_Min;
    public TableView<Part> AddProduct_TopTable;
    public TableColumn AddProduct_IDcol1;
    public TableColumn AddProduct_NameCol1;
    public TableColumn AddProduct_InvCol1;
    public TableColumn AddProduct_PriceCol1;
    public Button AddProduct_AddButton;
    public Button AddProduct_RemoveButt;
    public Button OnProductSaveButton;
    public Button ProductCancelButton;
    public TableView<Part> AddProduct_BottomTable;
    public TableColumn AddProduct_IDCol2;
    public TableColumn AddProduct_NameCol2;
    public TableColumn AddProduct_InvCol2;
    public TableColumn AddProduct_PriceCol2;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        AddProduct_TopTable.setItems(Inventory.getAllparts());

        AddProduct_IDcol1.setCellValueFactory(new PropertyValueFactory<>("id"));
        AddProduct_NameCol1.setCellValueFactory(new PropertyValueFactory<>("name"));
        AddProduct_InvCol1.setCellValueFactory(new PropertyValueFactory<>("stock"));
        AddProduct_PriceCol1.setCellValueFactory(new PropertyValueFactory<>("price"));

        AddProduct_IDCol2.setCellValueFactory(new PropertyValueFactory<>("id"));
        AddProduct_NameCol2.setCellValueFactory(new PropertyValueFactory<>("name"));
        AddProduct_InvCol2.setCellValueFactory(new PropertyValueFactory<>("stock"));
        AddProduct_PriceCol2.setCellValueFactory(new PropertyValueFactory<>("price"));

        FilteredList<Part> filteredData = new FilteredList<>(Inventory.getAllparts(), p -> true);
        AddProduct_SearchField.textProperty().addListener((observableValue, oldValue, newValue) -> {
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
                } else {
                    return false; // does not match
                }
            });
            if (filteredData.size() == 0) {
                AlertMessages.emptySearchError();
            }
        });
        //wrap filtered list in sorted list
        SortedList<Part> sortedData = new SortedList<>(filteredData);
        //Bind sortedList comparator to tableview  comparator
        sortedData.comparatorProperty().bind(AddProduct_TopTable.comparatorProperty());
        //add sorted/ filtered data to table
        AddProduct_TopTable.setItems(sortedData);

    }


    private ObservableList<Part> invParts = FXCollections.observableArrayList();
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();


    public void OnProductSaveButton(ActionEvent actionEvent) throws IOException {
        try {
            int inv = Integer.parseInt(AddProduct_Inv.getText());
            int max = Integer.parseInt(AddProduct_Max.getText());
            int min = Integer.parseInt(AddProduct_Min.getText());

            if (AddProduct_Name.getText().isBlank()) {
                AlertMessages.productError(1);
                return;
            }

            if (min >= max) {
                AlertMessages.productError(3);
                return;
            }

            if (inv >= max || inv <= min) {
                AlertMessages.productError(4);
                return;
            }


            Product product = new Product(Inventory.GenerateProductID(), AddProduct_Name.getText(), Double.parseDouble(AddProduct_Price.getText()), Integer.parseInt(AddProduct_Inv.getText()),
                    Integer.parseInt(AddProduct_Min.getText()), Integer.parseInt(AddProduct_Max.getText()));
            Inventory.addProduct(product);
            for (Part part : associatedParts) {
                product.addAssociatedPart(part);
            }

            System.out.println("Add Product save button clicked");
            Parent root = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setTitle("Home Screen");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            AlertMessages.productError(2);
        }
    }

    public void OnProductCancelButton(ActionEvent actionEvent) throws IOException {
        System.out.println("Product Modify cancel button clicked");
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Home Screen");
        stage.setScene(scene);
        stage.show();
    }

    public void OnAddProduct_AddButton(ActionEvent actionEvent) {
        System.out.println("Product  Add button clicked");
        Part Selected = AddProduct_TopTable.getSelectionModel().getSelectedItem();

        if (Selected == null) {
            return;
        }
        associatedParts.add(Selected);
        AddProduct_BottomTable.setItems(associatedParts);
        invParts.remove(Selected);
    }

    public void OnAddProduct_RemoveButt(ActionEvent actionEvent) {
        System.out.println("Product  Remove button clicked");
        Part Selected = AddProduct_BottomTable.getSelectionModel().getSelectedItem();

        if (Selected == null) {
            return;
        }
        if (AlertMessages.removeConfirmation() == true) {
            associatedParts.remove(Selected);
            invParts.add(Selected);
        }
    }
}

