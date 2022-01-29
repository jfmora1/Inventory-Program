package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.OutSourced;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * This is the Controller Class for the add part scene.
 */
public class Controller_AddPart implements Initializable {

    public RadioButton radio_button1;
    public RadioButton radio_button2;
    public TextField AddID_part;
    public TextField AddName_part;
    public TextField AddInv_part;
    public TextField AddPrice_part;
    public TextField AddMax_part;
    public TextField AddMacID_part;
    public TextField AddMin_part;
    public Button save_button;
    public Button cancel_button;
    public Label MachineID;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void Onradio_button1(ActionEvent actionEvent) {
        MachineID.setText("Machine ID");

    }

    public void Onradio_button2(ActionEvent actionEvent) {
        MachineID.setText("Company Name");
    }

    public void onsave_button(ActionEvent actionEvent) throws IOException {
        try {
            int inv = Integer.parseInt(AddInv_part.getText());
            int max = Integer.parseInt(AddMax_part.getText());
            int min = Integer.parseInt(AddMin_part.getText());

            if (AddName_part.getText().isBlank()) {
                AlertMessages.partError(1);
                return;
            }
            if (AddMacID_part.getText().isBlank()) {
                AlertMessages.partError(5);
                return;
            } else {
                if (min >= max) {
                    AlertMessages.partError(3);
                    return;
                }

                if (inv >= max || inv <= min) {
                    AlertMessages.partError(4);
                    return;
                }


                // adds to the array
                else if (radio_button1.isSelected()) {
                    try {
                        Inventory.addPart(new InHouse(Inventory.GeneratePartId(), AddName_part.getText(), Double.parseDouble(AddPrice_part.getText()), Integer.parseInt(AddInv_part.getText()),
                                Integer.parseInt(AddMin_part.getText()), Integer.parseInt(AddMax_part.getText()), Integer.parseInt(AddMacID_part.getText())));
                    } catch (Exception e) {
                        AlertMessages.partError(2);
                        return;
                    }
                } else if (radio_button2.isSelected()) {
                    Inventory.addPart(new OutSourced(Inventory.GeneratePartId(), AddName_part.getText(), Double.parseDouble(AddPrice_part.getText()), Integer.parseInt(AddInv_part.getText()),
                            Integer.parseInt(AddMin_part.getText()), Integer.parseInt(AddMax_part.getText()), AddMacID_part.getText()));

                }


                System.out.println("Part save button clicked");
                Parent root = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
                Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setTitle("Home Screen");
                stage.setScene(scene);
                stage.show();
            }
        } catch (Exception e) {
            AlertMessages.partError(2);
        }
    }


    public void oncancel_button(ActionEvent actionEvent) throws IOException {
        System.out.println("Part cancel button clicked");
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Home Screen");
        stage.setScene(scene);
        stage.show();
    }


}
