package main;

/**
 * @author Lucas Gale
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;

/**
 * The main class creates an application for inventory management and adds sample data.
 */
public class Main extends Application {

    /**
     * The start method initializes MainScreen.fxml.
     *
     * @param stage
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("../views/MainScreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 850, 350);
        stage.setTitle("Inventory Management");
        stage.setScene(scene);
        stage.show();
    }

    // JAVADOC FILE IS LOCATED IN A SEPARATE FILE CALLED JAVADOC-WGU-C482-PA

    /**
     * The main method loads the following data and launches the application.
     *
     * @param args
     */
    public static void main(String[] args) {

        Part brakes = new InHouse(1, "Brakes", 15.00, 10, 1, 20, 1);
        Inventory.addPart(brakes);

        Part wheel = new InHouse(2, "Wheel", 15.00, 32, 1, 20, 2);
        Inventory.addPart(wheel);

        Part seat = new InHouse(3, "Seat", 58.00, 10, 0, 100, 3);
        Inventory.addPart(seat);

        Product GiantBike = new Product(5000, "Giant Bike", 350.00, 2, 0, 10);
        Inventory.addProduct(GiantBike);

        Product Tricycle = new Product(5001, "Tricycle", 150.00, 5, 0, 50);
        Inventory.addProduct(Tricycle);

        Part spoke = new Outsourced(4, "Frame", 100.00, 10, 0, 80, "CycleMania");
        Inventory.addPart(spoke);

        launch();
    }

}