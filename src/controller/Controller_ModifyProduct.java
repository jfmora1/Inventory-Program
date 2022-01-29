package controller;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
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
 * Controller class for Modify Product Scene
 */
public class Controller_ModifyProduct implements Initializable {
    public TextField ModifyProduct_SearchField;
    public TextField ModifyProduct_ID;
    public TextField ModifyProduct_Name;
    public TextField ModifyProduct_Inv;
    public TextField ModifyProduct_Price;
    public TextField ModifyProduct_Max;
    public TextField ModifyProduct_Min;
    public TableView ModifyProduct_Table1;
    public TableColumn ModifyProduct_PartIDCol1;
    public TableColumn ModifyProduct_NameCol1;
    public TableColumn ModifyProduct_InvCol1;
    public TableColumn ModifyProduct_PriceCol1;
    public Button ModifyProduct_AddButton;
    public Button ModifyProduct_RemoveButton;
    public Button OnProductSaveButton;
    public Button ProductCancelButton;
    public TableView ModifyProduct_Table2;
    public TableColumn ModifyProduct_IDcol2;
    public TableColumn ModifyProduct_Namecol2;
    public TableColumn ModifyProduct_InvCol2;
    public TableColumn ModifyProduct_PriceCol2;
    private Product selectedProduct;
    private ObservableList<Part> associatedParts;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    selectedProduct = MainScreen.getSelectedProduct();
    associatedParts = selectedProduct.getAllAssociatedParts();

        ModifyProduct_ID.setText(String.valueOf(selectedProduct.getProductID()));
        ModifyProduct_Name.setText(selectedProduct.getProductName());
        ModifyProduct_Inv.setText(String.valueOf(selectedProduct.getProductStock()));
        ModifyProduct_Min.setText(String.valueOf(selectedProduct.getProductMin()));
        ModifyProduct_Max.setText(String.valueOf(selectedProduct.getProductMax()));
        ModifyProduct_Price.setText(String.valueOf(selectedProduct.getProductPrice()));

        ModifyProduct_Table1.setItems(Inventory.getAllparts());
        ModifyProduct_Table2.setItems(associatedParts);

        ModifyProduct_PartIDCol1.setCellValueFactory(new PropertyValueFactory<>("id"));
        ModifyProduct_NameCol1.setCellValueFactory(new PropertyValueFactory<>("name"));
        ModifyProduct_InvCol1.setCellValueFactory(new PropertyValueFactory<>("stock"));
        ModifyProduct_PriceCol1.setCellValueFactory(new PropertyValueFactory<>("price"));

        ModifyProduct_IDcol2.setCellValueFactory(new PropertyValueFactory<>("id"));
        ModifyProduct_Namecol2.setCellValueFactory(new PropertyValueFactory<>("name"));
        ModifyProduct_InvCol2.setCellValueFactory(new PropertyValueFactory<>("stock"));
        ModifyProduct_PriceCol2.setCellValueFactory(new PropertyValueFactory<>("price"));

        FilteredList<Part> filteredData = new FilteredList<>(Inventory.getAllparts(), p -> true);
        ModifyProduct_SearchField.textProperty().addListener((observableValue, oldValue, newValue) -> {
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
            if(filteredData.size() == 0){
                AlertMessages.emptySearchError();
                System.out.println("EMPTY");
            }
        });

        //wrap filtered list in sorted list
        SortedList<Part> sortedData = new SortedList<>(filteredData);
        //Bind sortedList comparator to tableview  comparator
        sortedData.comparatorProperty().bind(ModifyProduct_Table1.comparatorProperty());
        //add sorted/ filtered data to table
        ModifyProduct_Table1.setItems(sortedData);

    }

    public void OnProductSaveButton(ActionEvent actionEvent)throws IOException {

        try {
            int inv = Integer.parseInt(ModifyProduct_Inv.getText());
            int max = Integer.parseInt(ModifyProduct_Max.getText());
            int min = Integer.parseInt(ModifyProduct_Min.getText());

            if (ModifyProduct_Name.getText().isBlank()) {
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

            Product product = new Product(selectedProduct.getProductID(), ModifyProduct_Name.getText(), Double.parseDouble(ModifyProduct_Price.getText()),
                    Integer.parseInt(ModifyProduct_Inv.getText()), Integer.parseInt(ModifyProduct_Min.getText()), Integer.parseInt(ModifyProduct_Max.getText()));
            Inventory.addProduct(product);
            for (Part part : associatedParts) {
                product.addAssociatedPart(part);
            }
            Inventory.DeleteProduct(selectedProduct);


            System.out.println("Modify Product save button clicked");
            Parent root = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setTitle("Home Screen");
            stage.setScene(scene);
            stage.show();
        }catch(Exception e){
            AlertMessages.productError(2);
        }
    }

    public void OnProductCancelButton(ActionEvent actionEvent)throws IOException {
        System.out.println("Modify Product Cancel button clicked");
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
        Stage stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Home Screen");
        stage.setScene(scene);
        stage.show();
    }

    public void OnModifyProduct_AddButton(ActionEvent actionEvent) {
        System.out.println("Modify Product Add button clicked");
        Part Selected = (Part) ModifyProduct_Table1.getSelectionModel().getSelectedItem();

        if (Selected == null) {
            return;
        }
        associatedParts.add(Selected);
        ModifyProduct_Table2.setItems(associatedParts);
    }

    public void OnModifyProduct_RemoveButton(ActionEvent actionEvent) {
        System.out.println("Modify Product Remove button clicked");
        Part Selected = (Part) ModifyProduct_Table2.getSelectionModel().getSelectedItem();

        if (Selected == null) {
            return;
        }
        if(AlertMessages.removeConfirmation() == true) {
            associatedParts.remove(Selected);
        }

    }
}
