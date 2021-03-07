package sample;

/**
 * A feature I would add in an future update would be to
 * connect this application to an actual database that would
 * permanently save the information instead of just erasing
 * it all when the user exits out.
 * Class Main.java
 */
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 *
 * @author Frank Xander Morales
 */
public class Main extends Application {

    /**
     * The start method starts the program.
     * @param primaryStage the Stage to start
     * @exception Exception Failed get fxml resource.
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent parent = FXMLLoader.load(getClass().getResource("Inventory.fxml"));
        primaryStage.setScene(new Scene(parent));
        primaryStage.show();
    }


    /**
     * The main method.
     * @param args the standard main method argument.
     */
    public static void main(String[] args) {
        launch(args);
    }
}