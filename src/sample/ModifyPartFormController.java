package sample;

/**
 * Class ModifyPartFormController.java
 */
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;

/**
 *
 * @author Frank Xander Morales
 */
public class ModifyPartFormController {

    private Inventory inventory = new Inventory();
    private int index = 0;

    //@FXML private ToggleGroup toggleGroup = new ToggleGroup();
    @FXML private RadioButton inHouse;
    @FXML private RadioButton outsourced;

    @FXML private TextField idTextField;
    @FXML private TextField nameTextField;
    @FXML private TextField invTextField;
    @FXML private TextField priceTextField;
    @FXML private TextField maxTextField;
    @FXML private TextField minTextField;
    @FXML private TextField machineIdOrCompanyNameTextField;

    @FXML private Label machineIDOrCompanyNameLabel;
    @FXML private Label infoLabel;

    /**
     *  This method receives the inventory object from
     *  the main screen.
     *  @param inv is the object from main screen.
     *  @param i index of the Part in which to modify.
     */
    public void setInventoryAndIndex(Inventory inv, int i){
        inventory = inv;
        index = i;

        if(inventory.getAllParts().get(index) instanceof InHouse){
            inHouse.setSelected(true);
            InHouse p = (InHouse) inventory.getAllParts().get(index);
            machineIdOrCompanyNameTextField.setText(String.valueOf(p.getMachineId()));
            machineIDOrCompanyNameLabel.setText("Machine Id");
        } else {
            outsourced.setSelected(true);
            Outsourced p = (Outsourced) inventory.getAllParts().get(index);
            machineIdOrCompanyNameTextField.setText(p.getCompanyName());
            machineIDOrCompanyNameLabel.setText("Company Name");
        }

        idTextField.setText(String.valueOf(inventory.getAllParts().get(index).getId()));
        nameTextField.setText(inventory.getAllParts().get(index).getName());
        invTextField.setText(String.valueOf(inventory.getAllParts().get(index).getStock()));
        priceTextField.setText(String.valueOf(inventory.getAllParts().get(index).getPrice()));
        maxTextField.setText(String.valueOf(inventory.getAllParts().get(index).getMax()));
        minTextField.setText(String.valueOf(inventory.getAllParts().get(index).getMin()));
    }

    /**
     *  This method adds the part info to the Inventory
     *  list and returns to the main screen.
     *  @param event Is an ActionEvent object.
     *  @exception IOException Failed to open scene.
     */
    public void saveButtonPushed(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Inventory.fxml"));
        Parent mainScreen = loader.load();

        Scene mainScene = new Scene(mainScreen);

        Controller controller = loader.getController();

        // sets the inventory ID
        int id = index+1;

        // gets the name from nameTextField and verifies it is not empty
        if(nameTextField.getText().isEmpty()){
            infoLabel.setText("Error! Please enter a name.");
            return;
        }
        String name = nameTextField.getText();

        // declares necessary variables
        double price = 0;
        int inv, max, min = 0;

        // verifies validity of integers
        try {
            inv = Integer.parseInt(invTextField.getText());
            price = Double.parseDouble(priceTextField.getText());
            max = Integer.parseInt(maxTextField.getText());
            min = Integer.parseInt(minTextField.getText());
        } catch(NumberFormatException e) {
            infoLabel.setText("Error! Please check your Inv/Price/Max/Min values.");
            return;
        }
        //price =

        // checks to make sure max is bigger than min
        if(max < min) {
            infoLabel.setText("Error! Max value must equal or be more than Min value.");
            return;
        }

        // checks to make sure Inv value is between max and min
        if(inv < min || inv > max ) {
            infoLabel.setText("Error! Inv value must be between Max and Min.");
            return;
        }

        // creates either inHouse Part or Outsourced Part depending on radiobutton
        if (inHouse.isSelected()) {
            int machineID = 0;

            // validates the value is an integer
            try {
                machineID = Integer.parseInt(machineIdOrCompanyNameTextField.getText());
            } catch (NumberFormatException e) {
                infoLabel.setText("Machine ID must be a number.");
                return;
            }
            InHouse p = new InHouse(id, name, price, inv, min, max, machineID);
            inventory.getAllParts().set(index, p);
        } else {
            Outsourced p = new Outsourced(id, name, price, inv, min, max, machineIdOrCompanyNameTextField.getText());
            inventory.getAllParts().set(index, p);
        }

        controller.setInventory(inventory);
        controller.initialize();

        // this line gets the Stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(mainScene);
        window.show();
    }

    /**
     *  This method switches to the main screen.
     *  @param event Is an ActionEvent object.
     *  @exception IOException Failed to open scene.
     */
    public void cancelButtonPushed(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Inventory.fxml"));
        Parent mainScreen = loader.load();

        Scene mainScene = new Scene(mainScreen);

        Controller controller = loader.getController();
        controller.setInventory(inventory);
        controller.initialize();

        // this line gets the Stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(mainScene);
        window.show();
    }

    /**
     *  This method changes the last label according
     *  to the radio button selected.
     */
    public void inHouseListener(){
        if(inHouse.isSelected()){
            machineIDOrCompanyNameLabel.setText("Machine Id");
        }
    }

    /**
     *  This method changes the last label according
     *  to the radio button selected.
     */
    public void outsourcedListener(){
        if(outsourced.isSelected()){
            machineIDOrCompanyNameLabel.setText("Company Name");
        }
    }
}
