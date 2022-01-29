package main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;


/**
 * This is the Main Class.
 * This starts the application on the Home Screen, which is an Inventory Program.
 * <p>
 * Future Enhancement: Adding log in features per user would extend the functionality to track changes and control features.
 */
public class Main extends Application {

    /**
     * The Main Method.
     * This launches the application
     * The JavaDoc is located in the Javadoc folder "Java Projects / Javadoc"
     */
    public static void main(String[] args) {

        launch(args); // When program starts, calls launch, launch goes into Application, set it all up and call start.
    }

    /**
     * The Start Method.
     * This method creates and loads the first scene from the FXML
     */
    @Override
    public void start(Stage mainStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
        mainStage.setTitle("Home Screen");
        mainStage.setScene(new Scene(root, 800, 400));
        mainStage.show();
    }
}
