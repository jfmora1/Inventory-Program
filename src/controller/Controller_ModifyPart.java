package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.OutSourced;
import model.Part;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller class for the Modify Part scene
 */
public class Controller_ModifyPart implements Initializable {
    public RadioButton radio_button1;
    public RadioButton radio_button2;
    public TextField ModifyID_part;
    public TextField ModifyName_part;
    public TextField ModifyInv_part;
    public TextField ModifyPrice_part;
    public TextField ModifyMax_part;
    public TextField ModifyMacID_part;
    public TextField ModifyMin_part;
    public Button save_button;
    public Button cancel_button;
    public Label MachineID;
    private Part selectedPart;

    public void Onradio_button1(ActionEvent actionEvent) {
        MachineID.setText("Machine ID");
    }

    public void Onradio_button2(ActionEvent actionEvent) {
        MachineID.setText("Company Name");
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        selectedPart = MainScreen.getSelectedPart();

        if (selectedPart instanceof InHouse) {
            radio_button1.setSelected(true);
            MachineID.setText("Machine ID");
            ModifyMacID_part.setText(String.valueOf(((InHouse) selectedPart).getMachineID()));
        }

        if (selectedPart instanceof OutSourced) {
            radio_button2.setSelected(true);
            MachineID.setText("Company Name");
            ModifyMacID_part.setText(String.valueOf(((OutSourced) selectedPart).getCompanyName()));
        }

        ModifyID_part.setText(String.valueOf(selectedPart.getId()));
        ModifyName_part.setText(selectedPart.getName());
        ModifyInv_part.setText(String.valueOf(selectedPart.getStock()));
        ModifyMin_part.setText(String.valueOf(selectedPart.getMin()));
        ModifyMax_part.setText(String.valueOf(selectedPart.getMax()));
        ModifyPrice_part.setText(String.valueOf(selectedPart.getPrice()));
    }

    public void Onsavebutton_m(ActionEvent actionEvent) throws IOException {
        try {
            boolean partAdded = false;
            int inv = Integer.parseInt(ModifyInv_part.getText());
            int max = Integer.parseInt(ModifyMax_part.getText());
            int min = Integer.parseInt(ModifyMin_part.getText());

            if (ModifyName_part.getText().isBlank()) {
                AlertMessages.partError(1);
                return;
            }

            if (ModifyMacID_part.getText().isBlank()) {
                AlertMessages.partError(5);
                return;
            }

            if (min >= max) {
                AlertMessages.partError(3);
                return;
            }

            if (inv >= max || inv <= min) {
                AlertMessages.partError(4);
                return;
            }


            if (radio_button1.isSelected()) {
                int machineID = Integer.parseInt(ModifyMacID_part.getText());
                Inventory.addPart(new InHouse(selectedPart.getId(), ModifyName_part.getText(), Double.parseDouble(ModifyPrice_part.getText()),
                        Integer.parseInt(ModifyInv_part.getText()), Integer.parseInt(ModifyMin_part.getText()), Integer.parseInt(ModifyMax_part.getText()), machineID));
                partAdded = true;
            }
            if (radio_button2.isSelected()) {
                String companyName = ModifyMacID_part.getText();
                Inventory.addPart(new OutSourced(selectedPart.getId(), ModifyName_part.getText(), Double.parseDouble(ModifyPrice_part.getText()),
                        Integer.parseInt(ModifyInv_part.getText()), Integer.parseInt(ModifyMin_part.getText()), Integer.parseInt(ModifyMax_part.getText()), companyName));
                partAdded = true;
            }
            if (partAdded) {
                Inventory.DeletePart(selectedPart);
            }

            System.out.println("Part_M save button clicked");
            Parent root = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setTitle("Home Screen");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            AlertMessages.partError(2);
        }
    }

    public void OnCancelButton_M(ActionEvent actionEvent) throws IOException {
        System.out.println("Part_M cancel button clicked");
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Home Screen");
        stage.setScene(scene);
        stage.show();
    }
}
