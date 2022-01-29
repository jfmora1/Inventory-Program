package controller;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

/**
 * This is the Alert class for error's in controllers.
 */
public class AlertMessages {

    /**
     * DeleteConfirmation Method.
     * Alerts user to confirm to delete a part
     */
    public static boolean deleteConfirmation() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Parts");
        alert.setHeaderText("Delete");
        alert.setContentText("Do you want to delete this part?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Remove Confirmation Method.
     * Alerts user with a pop up window if they want to confirm removal of part in associated parts
     */
    public static boolean removeConfirmation() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Associated Parts");
        alert.setHeaderText("Remove");
        alert.setContentText("Do you want to remove this part?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Empty search error method.
     * Alerts that the search field was unable to locate part
     */
    public static void emptySearchError() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Parts");
        alert.setHeaderText("ERROR");
        alert.setContentText("Parts not found!");
        alert.showAndWait();
    }

    /**
     * Parts Associated Error method.
     * Alerts user that program is unable to delete a product with an associated part attached
     */
    public static void PartAssociatedError() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Products");
        alert.setHeaderText("ERROR");
        alert.setContentText("Can't delete product with part associated.");
        alert.showAndWait();
    }

    /**
     * Part Error method.
     * Alerts for Adding/Modify parts pane
     */
    public static void partError(int num) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Parts");
        alert.setHeaderText("ERROR ADDING PART");
        switch (num) {
            case 1: {
                alert.setContentText("Missing Part Name");
                break;
            }
            case 2: {
                alert.setContentText("MISSING USER INPUT AND/OR INVALID FORMAT");
                break;
            }
            case 3: {
                alert.setContentText("Min should be less than max!");
                break;
            }
            case 4: {
                alert.setContentText("Inventory must be between min and max values");
                break;
            }
            case 5: {
                alert.setContentText("Missing company name/machine ID");
            }
        }
        alert.showAndWait();

    }

    /**
     * Product Error Method.
     * Alerts for Products add/modify pane
     */
    public static void productError(int num) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Products");
        alert.setHeaderText("ERROR ADDING PRODUCTS");
        switch (num) {
            case 1: {
                alert.setContentText("Missing Product Name");
                break;
            }
            case 2: {
                alert.setContentText("MISSING USER INPUT AND/OR INVALID FORMAT");
                break;
            }
            case 3: {
                alert.setContentText("Min should be less than max!");
                break;
            }
            case 4: {
                alert.setContentText("Inventory must be between min and max values");
                break;
            }
        }
        alert.showAndWait();

    }


}
