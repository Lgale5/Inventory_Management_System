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
import model.Part;

import java.io.IOException;

/**
 * The ModifyPartController class.
 */

public class ModifyPartController {

    // label for swapping between machine id and outsourced
    @FXML
    private RadioButton ModifyInHouseRadio;
    @FXML
    private RadioButton ModifyOutsourcedRadio;
    @FXML
    private Label machineID;
    @FXML
    private Label companyName;
    @FXML
    private TextField modifyPartID;
    @FXML
    private TextField modifyPartName;
    @FXML
    private TextField modifyPartInventory;
    @FXML
    private TextField modifyPartPrice;
    @FXML
    private TextField modifyPartMax;
    @FXML
    private TextField modifyPartMin;
    @FXML
    private TextField addPartMachineID;
    @FXML
    private Button modifyPartCancelButton;

    private int currentIndex = 0;

    /**
     * Receives the information from the main screen.
     *
     * @param part
     * @param selectedIndex
     */
    public void sendPart(int selectedIndex, Part part) {

        currentIndex = selectedIndex;

        if (part instanceof InHouse) {
            ModifyInHouseRadio.setSelected(true);
            addPartMachineID.setText(String.valueOf(((InHouse) part).getMachineID()));
        } else {
            ModifyOutsourcedRadio.setSelected(true);
            addPartMachineID.setText(((Outsourced) part).getCompanyName());
        }

        //Logic for setting label for Machine ID or Company Name based on selected radio
        if (ModifyInHouseRadio.isSelected()) {
            machineID.setVisible(true);
            companyName.setVisible(false);
        }
        else {
            machineID.setVisible(false);
            companyName.setVisible(true);
        }

        modifyPartID.setText(String.valueOf(part.getId()));
        modifyPartName.setText(String.valueOf(part.getName()));
        modifyPartInventory.setText(String.valueOf(part.getStock()));
        modifyPartPrice.setText(String.valueOf(part.getPrice()));
        modifyPartMax.setText(String.valueOf(part.getMax()));
        modifyPartMin.setText(String.valueOf(part.getMin()));
    }

    /**
     * Change part to in house.
     *
     * @param event
     */

    @FXML
    public void onActionAddPartInHouse(ActionEvent event) {

        machineID.setText("Machine ID");
        machineID.setVisible(true);
        companyName.setVisible(false);
    }

    /**
     * Change part to Outsourced.
     *
     * @param event
     */
    @FXML
    public void onActionAddPartOutsourced(ActionEvent event) {

        machineID.setText("Company Name");
        machineID.setVisible(false);
        companyName.setVisible(true);
    }


    /**
     * Modify Parts Save Button to save the modified part.
     *
     * @param event
     */
    @FXML
    void modifyPartSaveButton(ActionEvent event) throws IOException {
        try {
            int partID = Integer.parseInt(modifyPartID.getText());
            String name = modifyPartName.getText();
            int inStock = Integer.parseInt(modifyPartInventory.getText());
            double price = Double.parseDouble(modifyPartPrice.getText());
            int min = Integer.parseInt(modifyPartMin.getText());
            int max = Integer.parseInt(modifyPartMax.getText());
            int machineID;

            String companyName;

            //Min should be less than max.
            if (max < min) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Max must be greater than min.");
                alert.showAndWait();
                return;
            }
            //Inventory should be between the min and max values.
            else if (inStock < min || max < inStock) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Inventory must be within min and max.");
                alert.showAndWait();
                return;
            }

            // Print to console for troubleshooting.
            System.out.println(ModifyOutsourcedRadio.isSelected() + " Outsourced");
            System.out.println(ModifyInHouseRadio.isSelected() + " In-house");
            if (ModifyInHouseRadio.isSelected()) {
                machineID = Integer.parseInt(addPartMachineID.getText());
                InHouse updatedPart = new InHouse(partID, name, price, inStock, min, max, machineID);
                Inventory.updatePart(currentIndex, updatedPart);
            }
            if (ModifyOutsourcedRadio.isSelected()) {
                companyName = addPartMachineID.getText();
                Outsourced updatedPart = new Outsourced(partID, name, price, inStock, min, max, companyName);
                Inventory.updatePart(currentIndex, updatedPart);
            }
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Object scene = FXMLLoader.load(getClass().getResource("../views/MainScreen.fxml"));
            stage.setScene(new Scene((Parent) scene));
            stage.show();

        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Input Error!");
            alert.setContentText("Incorrect value entered");
            alert.showAndWait();
            return;
        }
    }

    /**
     * Cancel button returns user to the main screen.
     *
     * @param event
     */

    @FXML
    public void ModifyPartCancelAction(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("../views/MainScreen.fxml"));
        Scene scene = new Scene(root);
        Stage MainScreenReturn = (Stage) ((Node) event.getSource()).getScene().getWindow();
        MainScreenReturn.setScene(scene);
        MainScreenReturn.show();
    }

}

