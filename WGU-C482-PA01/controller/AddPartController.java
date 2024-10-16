package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;

import java.io.IOException;

/**
 * The AddPartController Class adds to or modifies the parts inventory.
 */
public class AddPartController {

    @FXML
    private Label machineOrCompany;

    @FXML
    private RadioButton inHouseRadio;
    @FXML
    private RadioButton outsourcedRadio;
    @FXML
    private RadioButton addPartOutsourced;
    @FXML
    private Button addPartCancelButton;
    @FXML
    private Button addPartSaveButton;
    @FXML
    private TextField addPartName;
    @FXML
    private TextField addPartInv;
    @FXML
    private TextField addPartPrice;
    @FXML
    private TextField addPartMax;
    @FXML
    private TextField addPartMin;
    @FXML
    private TextField addPartMachineID;

    /**
     * On In-House radio press set the machineOrCompany field to Machine ID.
     * @param event
     */
    @FXML
    void onActionAddPartInHouse(ActionEvent event) {

        machineOrCompany.setText("Machine ID");
    }

    /**
     * On Outsourced radio press set the machineOrCompany field to Company Name.
     * @param event
     */
    @FXML
    void onActionAddPartOutsourced(ActionEvent event) {

        machineOrCompany.setText("Company Name");
    }


    /**
     * On save button press, save the part.
     * RUNTIME ERROR: Cause: java.lang.NumberFormatException. Input string: "hello".
     * I corrected the error by taking the contents of the method and wrap it inside the try
     * and catch statements and told it to catch the NumberFormatException.
     * @param event
     */
    @FXML
    void addPartSaveButton(ActionEvent event) throws IOException {
        try {

            // Create random number between 0.0 - 1.0 and * 100 to create a unique number
            int autoID = (int) (Math.random() * 100);

            int machineID = 0;
            String companyName;

            String name = addPartName.getText();
            int partInv = Integer.parseInt(addPartInv.getText());
            double partPrice = Double.parseDouble(addPartPrice.getText());
            int min = Integer.parseInt(addPartMin.getText());
            int max = Integer.parseInt(addPartMax.getText());

            //Min must be less than max. Alert if out of bounds
            if (max < min) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Max must be greater than the Min.");
                alert.showAndWait();
                return;
            }
            //Inventory must be between the min and max values. Alert if out of bounds
            else if ((partInv < min) || (max < partInv)) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Inventory must be within the Min and Max.");
                alert.showAndWait();
                return;
            }

            // Print to console
            System.out.println(outsourcedRadio.isSelected() + " Outsourced");
            System.out.println(inHouseRadio.isSelected() + " In-house");
            if (outsourcedRadio.isSelected()) {
                companyName = addPartMachineID.getText();
                Outsourced addPart = new Outsourced(autoID, name, partPrice, partInv, min, max, companyName);
                Inventory.addPart(addPart);
            }
            if (inHouseRadio.isSelected()) {
                machineID = Integer.parseInt(addPartMachineID.getText());
                InHouse addPart = new InHouse(autoID, name, partPrice, partInv, min, max, machineID);
                Inventory.addPart(addPart);
            }
            // Stage
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Object scene = FXMLLoader.load(getClass().getResource("../views/MainScreen.fxml"));
            stage.setScene(new Scene((Parent) scene));
            stage.show();

        } catch (NumberFormatException w) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Input Error!");
            alert.setContentText("Incorrect value entered");
            alert.showAndWait();
            return;
        }
    }

    /**
     * On cancel button press go to the main screen.
     * @param event
     */

    @FXML
    public void addPartCancelAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../views/MainScreen.fxml"));
        Scene scene = new Scene(root);
        Stage MainScreenReturn = (Stage) ((Node) event.getSource()).getScene().getWindow();
        MainScreenReturn.setScene(scene);
        MainScreenReturn.show();

    }


}
