package sample;

/**
 * Class Product.java
 */
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Frank Xander Morales
 */
public class Product {

    private ObservableList<Part> associatedParts;
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    /**
     * The Constructor sets the id, name,
     * price, stock, min, and max.
     * @param id The product ID.
     * @param name The product name.
     * @param price The price of product.
     * @param stock The stock of product.
     * @param min The minimum amount of stock.
     * @param max The maximum amount of stock.
     */
    public Product(int id, String name, double price, int stock, int min, int max){
        associatedParts = FXCollections.observableArrayList();
        this.id = id;
        this.name = name;
        this. price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the price
     */
    public double getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * @return the stock
     */
    public int getStock() {
        return stock;
    }

    /**
     * @param stock the stock to set
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * @return the min
     */
    public int getMin() {
        return min;
    }

    /**
     * @param min the min to set
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * @return the max
     */
    public int getMax() {
        return max;
    }

    /**
     * @param max the max to set
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     * This method adds a selected part to the
     * associatedParts List.
     * @param part the part to add
     */
    public void addAssociatedPart(Part part) { associatedParts.add(part); }

    /**
     * This method deletes a selected part from
     * the associatedParts List.
     * @param part the part to delete
     * @return boolean
     */
    public boolean deleteAssociatedPart(Part part) {
        if(associatedParts.contains(part)){
            associatedParts.remove(part);
            return true;
        } else
            return false;
    }

    /**
     * This method returns the List containing all
     * the associated parts in the associatedParts list.
     * @return ObservableList
     */
    public ObservableList<Part> getAllAssociatedParts() { return associatedParts; }
}
