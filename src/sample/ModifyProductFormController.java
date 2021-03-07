package sample;
/**
 * Class ModifyProductFormController.java
 */
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.Optional;

/**
 *
 * @author Frank Xander Morales
 */
public class ModifyProductFormController {
    private Inventory inventory = new Inventory();
    private int index = 0;
    private NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();

    @FXML private TextField idTextField;
    @FXML private TextField nameTextField;
    @FXML private TextField invTextField;
    @FXML private TextField priceTextField;
    @FXML private TextField maxTextField;
    @FXML private TextField minTextField;
    @FXML private TextField searchTextField;

    @FXML private Label infoLabel;

    @FXML private TableView<Part> mainPartTableView;
    @FXML private TableColumn<Part, Integer> partsIdColumn;
    @FXML private TableColumn<Part, String> partsNameColumn;
    @FXML private TableColumn<Part, Integer> partsInventoryLevelColumn;
    @FXML private TableColumn<Part, Double> partsPriceColumn;

    @FXML private TableView<Part> selectedPartsTableView;
    @FXML private TableColumn<Part, Integer> selPartsIdColumn;
    @FXML private TableColumn<Part, String> selPartsNameColumn;
    @FXML private TableColumn<Part, Integer> selPartsInventoryLevelColumn;
    @FXML private TableColumn<Part, Double> selPartsPriceColumn;

    /**
     *  This method receives the inventory object from
     *  the main screen.
     *  @param inv is the object from main screen.
     *  @param i the index from main screen
     */
    public void setInventoryAndIndex(Inventory inv, int i){
        inventory = inv;
        index = i;

        idTextField.setText(String.valueOf(inventory.getAllProducts().get(index).getId()));
        nameTextField.setText(inventory.getAllProducts().get(index).getName());
        invTextField.setText(String.valueOf(inventory.getAllProducts().get(index).getStock()));
        priceTextField.setText(String.valueOf(inventory.getAllParts().get(index).getPrice()));
        maxTextField.setText(String.valueOf(inventory.getAllProducts().get(index).getMax()));
        minTextField.setText(String.valueOf(inventory.getAllProducts().get(index).getMin()));

        partsIdColumn.setCellValueFactory(new PropertyValueFactory<Part, Integer>("id"));
        partsNameColumn.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
        partsInventoryLevelColumn.setCellValueFactory(new PropertyValueFactory<Part, Integer>("stock"));
        partsPriceColumn.setCellValueFactory(new PropertyValueFactory<Part, Double>("price"));

        // this formats the price value to a currency format
        partsPriceColumn.setCellFactory(tc -> new TableCell<Part, Double>() {
            @Override
            protected void updateItem(Double price, boolean empty) {
                super.updateItem(price, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText(currencyFormat.format(price));
                }
            }
        });
        mainPartTableView.setItems(inventory.getAllParts());


        // fills selected parts table
        selPartsIdColumn.setCellValueFactory(new PropertyValueFactory<Part, Integer>("id"));
        selPartsNameColumn.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
        selPartsInventoryLevelColumn.setCellValueFactory(new PropertyValueFactory<Part, Integer>("stock"));
        selPartsPriceColumn.setCellValueFactory(new PropertyValueFactory<Part, Double>("price"));

        // this formats the price value to a currency format
        selPartsPriceColumn.setCellFactory(tc -> new TableCell<Part, Double>() {
            @Override
            protected void updateItem(Double price, boolean empty) {
                super.updateItem(price, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText(currencyFormat.format(price));
                }
            }
        });

        selectedPartsTableView.setItems(inventory.getAllProducts().get(index).getAllAssociatedParts());
    }

    /**
     *  This method adds a selected part from
     *  the table with all the parts to the table
     *  with the selected parts.
     *  @param event Is an ActionEvent object.
     */
    public void AddButtonListener(ActionEvent event) {
        // check for selection
        if(mainPartTableView.getSelectionModel().isEmpty()){
            infoLabel.setText("No selection made.");
            return;
        }

        inventory.getAllProducts().get(index).addAssociatedPart(mainPartTableView.getSelectionModel().getSelectedItem());

        selPartsIdColumn.setCellValueFactory(new PropertyValueFactory<Part, Integer>("id"));
        selPartsNameColumn.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
        selPartsInventoryLevelColumn.setCellValueFactory(new PropertyValueFactory<Part, Integer>("stock"));
        selPartsPriceColumn.setCellValueFactory(new PropertyValueFactory<Part, Double>("price"));

        selectedPartsTableView.setItems(inventory.getAllProducts().get(index).getAllAssociatedParts());
    }

    /**
     *  This method switches to the main screen.
     *  @param event Is an ActionEvent object.
     *  @exception IOException Failed to open scene.
     */
    public void cancelButtonListener(ActionEvent event) throws IOException {
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
     *  This method removes a selected part
     *  from the table of selected parts.
     *  @param event Is an ActionEvent object.
     */
    public void removeAssociatedPartListener(ActionEvent event) {
        // check for selection
        if(selectedPartsTableView.getSelectionModel().isEmpty()){
            infoLabel.setText("No selection made.");
            return;
        }

        // confirmation dialog box
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Product");
        alert.setHeaderText("Delete");
        alert.setContentText("Are you you want to delete?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            inventory.getAllProducts().get(index).deleteAssociatedPart(selectedPartsTableView.getSelectionModel().getSelectedItem());

            selPartsIdColumn.setCellValueFactory(new PropertyValueFactory<Part, Integer>("id"));
            selPartsNameColumn.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
            selPartsInventoryLevelColumn.setCellValueFactory(new PropertyValueFactory<Part, Integer>("stock"));
            selPartsPriceColumn.setCellValueFactory(new PropertyValueFactory<Part, Double>("price"));

            selectedPartsTableView.setItems(inventory.getAllProducts().get(index).getAllAssociatedParts());
        } else {
            return;
        }
    }

    /**
     *  This method adds the product info to the Inventory
     *  list and returns to the main screen.
     *  @param event Is an ActionEvent object.
     *  @exception IOException Failed to open scene.
     */
    public void saveButtonListener(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Inventory.fxml"));
        Parent mainScreen = loader.load();

        Scene mainScene = new Scene(mainScreen);

        Controller controller = loader.getController();

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

        // Now that all values have been checked
        // set new values into product
        inventory.getAllProducts().get(index).setName(name);
        inventory.getAllProducts().get(index).setPrice(price);
        inventory.getAllProducts().get(index).setStock(inv);
        inventory.getAllProducts().get(index).setMin(min);
        inventory.getAllProducts().get(index).setMax(max);

        controller.setInventory(inventory);
        controller.initialize();

        // this line gets the Stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(mainScene);
        window.show();
    }

    /**
     *  This method searches the table
     *  @param ae Is an ActionEvent object.
     */
    @FXML public void onEnter(ActionEvent ae){
        if(searchTextField.getText().isEmpty()) {
            mainPartTableView.setItems(inventory.getAllParts());
            infoLabel.setText("");
        } else{
            ObservableList<Part> selParts = FXCollections.observableArrayList();
            try {
                if(inventory.lookupPart(Integer.parseInt(searchTextField.getText())) != null) {
                    selParts.add(inventory.lookupPart(Integer.parseInt(searchTextField.getText())));
                    mainPartTableView.setItems(selParts);
                } else {
                    infoLabel.setText("Sorry, no Part with that ID exists.");
                    mainPartTableView.setItems(null);
                }
            } catch (NumberFormatException e) {
                if(!inventory.lookupPart(searchTextField.getText()).isEmpty()) {
                    selParts = inventory.lookupPart(searchTextField.getText());
                    mainPartTableView.setItems(selParts);
                } else{
                    infoLabel.setText("Sorry, no Part with that name exists.");
                    mainPartTableView.setItems(null);
                }
            }
        }
    }
}
