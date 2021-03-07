package sample;

/**
 * Class Controller.java
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
public class Controller {

    private Inventory inventory = new Inventory();
    private NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();

    // This code is to set up the Parts table
    @FXML private TableView<Part> partsTableView;
    @FXML private TableColumn<Part, Integer> partsIdColumn;
    @FXML private TableColumn<Part, String> partsNameColumn;
    @FXML private TableColumn<Part, Integer> partsInventoryLevelColumn;
    @FXML private TableColumn<Part, Double> partsPriceColumn;

    // This code is to set up the Products table
    @FXML private TableView<Product> productsTableView;
    @FXML private TableColumn<Product, Integer> productsIdColumn;
    @FXML private TableColumn<Product, String> productsNameColumn;
    @FXML private TableColumn<Product, Integer> productsInventoryLevelColumn;
    @FXML private TableColumn<Product, Double> productsPriceColumn;

    @FXML private Label errorLabel;
    @FXML private TextField searchPartsTextField;
    @FXML private TextField searchProductsTextField;

    /**
     *  This method performs any necessary
     *  initialization.
     */
    public void initialize(){
        // set up the columns for the Parts table, and load data into table
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

        partsTableView.setItems(inventory.getAllParts());


        // set up the columns for the Products table, and load data into table
        productsIdColumn.setCellValueFactory(new PropertyValueFactory<Product, Integer>("id"));
        productsNameColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
        productsInventoryLevelColumn.setCellValueFactory(new PropertyValueFactory<Product, Integer>("stock"));
        productsPriceColumn.setCellValueFactory(new PropertyValueFactory<Product, Double>("price"));

        // this formats the price value to a currency format
        productsPriceColumn.setCellFactory(tc -> new TableCell<Product, Double>() {
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

        productsTableView.setItems(inventory.getAllProducts());
    }


    /**
     *  This method opens the Add Parts Pane when
     *  add button is pressed.
     *  @param event Is an ActionEvent object.
     *  @exception IOException Failed to open scene.
     */
    public void AddPartsButtonPressed(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("AddPartForm.fxml"));
        Parent addPartScreen = loader.load();

        Scene addPartScene = new Scene(addPartScreen);

        // access controller and call setInventory method
        AddPartFormController controller = loader.getController();
        controller.setInventory(inventory);

        // this line gets the Stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(addPartScene);
        window.show();
    }

    /**
     *  This method receives an inventory object from
     *  another window.
     *  @param inventory inventory object.
     */
    public void setInventory(Inventory inventory){ this.inventory = inventory; }

    /**
     *  This method opens the Modify Parts Pane when
     *  the button is pressed.
     *  @param event Is an ActionEvent object.
     *  @exception IOException Failed to open scene.
     */
    public void ModifyPartsButtonPressed(ActionEvent event) throws IOException {
        // check for selection
        if(partsTableView.getSelectionModel().isEmpty()){
            errorLabel.setText("No selection made.");
            return;
        }

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("ModifyPartForm.fxml"));
        Parent screen = loader.load();

        // method to send inventory and index
        ModifyPartFormController c = loader.getController();
        int index = inventory.getAllParts().indexOf(partsTableView.getSelectionModel().getSelectedItem());
        c.setInventoryAndIndex(inventory, index);

        Scene scene = new Scene(screen);

        // this line gets the Stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(scene);
        window.show();
    }

    /**
     *  This method deletes a selected part when the delete
     *  button is pressed.
     */
    public void DeletePartsButtonPressed() {
        // check for selection
        if(partsTableView.getSelectionModel().isEmpty()){
            errorLabel.setText("No selection made.");
            return;
        }

        // confirmation dialog box
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Parts");
        alert.setHeaderText("Delete");
        alert.setContentText("Are you you want to delete?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            inventory.deletePart(partsTableView.getSelectionModel().getSelectedItem());
            partsTableView.setItems(inventory.getAllParts());
        } else {
            return;
        }
    }

    /**
     *  This method opens the Add Products Pane when
     *  add button is pressed.
     *  @param event Is an ActionEvent object.
     *  @exception IOException Failed to open scene.
     */
    public void AddProductsButtonPressed(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("AddProductForm.fxml"));
        Parent screen = loader.load();

        // access controller and call setInventory method
        AddProductFormController controller = loader.getController();
        controller.setInventory(inventory);

        Scene scene = new Scene(screen);

        // this line gets the Stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(scene);
        window.show();
    }

    /**
     *  This method opens the Modify Products Pane when
     *  the button is pressed.
     *  @param event Is an ActionEvent object.
     *  @exception IOException Failed to open scene.
     */
    public void ModifyProductsButtonPressed(ActionEvent event) throws IOException {
        // check for selection
        if(productsTableView.getSelectionModel().isEmpty()){
            errorLabel.setText("No selection made.");
            return;
        }

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("ModifyProductForm.fxml"));
        Parent screen = loader.load();

        // method to send inventory and index
        ModifyProductFormController c = loader.getController();
        int index = inventory.getAllProducts().indexOf(productsTableView.getSelectionModel().getSelectedItem());
        c.setInventoryAndIndex(inventory, index);

        Scene scene = new Scene(screen);

        // this line gets the Stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(scene);
        window.show();
    }

    /**
     *  This method deletes a selected product when the delete
     *  button is pressed.
     */
    public void DeleteProductsButtonPressed() {
        // check for selection
        if(productsTableView.getSelectionModel().isEmpty()){
            errorLabel.setText("No selection made.");
            return;
        }

        // check if Product to be deleted has associated parts
        if(!productsTableView.getSelectionModel().getSelectedItem().getAllAssociatedParts().isEmpty()){
            errorLabel.setText("Product to be deleted has associated parts. Please remove associated parts first.");
            return;
        }

        // confirmation dialog box
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Product");
        alert.setHeaderText("Delete");
        alert.setContentText("Are you you want to delete?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            inventory.deleteProduct(productsTableView.getSelectionModel().getSelectedItem());
            productsTableView.setItems(inventory.getAllProducts());
        } else {
            return;
        }
    }

    /**
     *  This method exits the program when
     *  the exit button is pressed.
     */
    @FXML private Button exitButton;
    public void ExitButtonPressed() {
        // get a handle to the stage
        Stage stage = (Stage) exitButton.getScene().getWindow();
        // do what you have to do
        stage.close();
    }

    /**
     *  This method searches the Parts and Products tables
     *  @param ae Is an ActionEvent object.
     */
    @FXML public void onEnter(ActionEvent ae){
        partsTableView.setItems(inventory.getAllParts());
        productsTableView.setItems(inventory.getAllProducts());
        if(searchPartsTextField.getText().isEmpty() && searchProductsTextField.getText().isEmpty()){
            errorLabel.setText("");
        }

        if(searchPartsTextField.getText().isEmpty()) {
            partsTableView.setItems(inventory.getAllParts());
        } else{
            ObservableList<Part> selParts = FXCollections.observableArrayList();
            try {
                if(inventory.lookupPart(Integer.parseInt(searchPartsTextField.getText())) != null) {
                    selParts.add(inventory.lookupPart(Integer.parseInt(searchPartsTextField.getText())));
                    partsTableView.setItems(selParts);
                } else {
                    errorLabel.setText("Sorry, no Part with that ID exists.");
                    partsTableView.setItems(null);
                }
            } catch (NumberFormatException e) {
                if(!inventory.lookupPart(searchPartsTextField.getText()).isEmpty()) {
                    selParts = inventory.lookupPart(searchPartsTextField.getText());
                    partsTableView.setItems(selParts);
                } else{
                    errorLabel.setText("Sorry, no Part with that name exists.");
                    partsTableView.setItems(null);
                }
            }
        }

        if(searchProductsTextField.getText().isEmpty()) {
            productsTableView.setItems(inventory.getAllProducts());
        } else {
            ObservableList<Product> selProducts = FXCollections.observableArrayList();
            try {
                if(inventory.lookupProduct(Integer.parseInt(searchProductsTextField.getText())) != null) {
                    selProducts.add(inventory.lookupProduct(Integer.parseInt(searchProductsTextField.getText())));
                    productsTableView.setItems(selProducts);
                } else {
                    errorLabel.setText("Sorry, no Product with that ID exists.");
                    productsTableView.setItems(null);
                }
            } catch (NumberFormatException e) {
                if(!inventory.lookupProduct(searchProductsTextField.getText()).isEmpty()) {
                    selProducts = inventory.lookupProduct(searchProductsTextField.getText());
                    productsTableView.setItems(selProducts);
                } else{
                    errorLabel.setText("Sorry, no Product with that name exists.");
                    productsTableView.setItems(null);
                }
            }
        }
    }
}