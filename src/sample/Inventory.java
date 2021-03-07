package sample;

/**
 * Class Inventory.java
 */
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Frank Xander Morales
 */
public class Inventory {
    private ObservableList<Part> allParts = FXCollections.observableArrayList();
    private ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /**
     * This method adds a new part to the
     * ObservableList allParts
     * @param newPart the part to add
     */
    public void addPart(Part newPart) {
        allParts.add(newPart);
    }

    /**
     * This method adds a new part to the
     * ObservableList allProducts.
     * @param newProduct the product to add
     */
    public void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

    /**
     * This method looks up a part using
     * the part ID.
     * @param partId the partId to lookup
     * @return Part
     */
    public Part lookupPart(int partId) {
        for(Part p : allParts){
            if(p.getId() == partId) { return p; }
        }
        return null;
    }

    /**
     * This method looks up a product using
     * the product ID.
     * @param productId the productId to lookup
     * @return Product
     */
    public Product lookupProduct(int productId) {
        for(Product p : allProducts){
            if(p.getId() == productId) { return p; }
        }
        return null;
    }

    /**
     * This method looks up all the parts in the
     * allParts list and returns a List with all
     * matching Parts.
     * @param partName the part name to lookup
     * @return tempPartList list to return
     */
    public ObservableList<Part> lookupPart(String partName) {
        ObservableList<Part> tempPartList = FXCollections.observableArrayList();

        for(Part p : allParts){
            if(p.getName().contains(partName))
                tempPartList.add(p);
        }

        return tempPartList;
    }

    /**
     * This method looks up a product using
     * the product name.
     * @param productName the product name to lookup
     * @return tempProductList list to return
     */
    public ObservableList<Product> lookupProduct(String productName) {
        ObservableList<Product> tempProductList = FXCollections.observableArrayList();

        for(Product p : allProducts){
            if(p.getName().contains(productName))
                tempProductList.add(p);
        }

        return tempProductList;
    }

    /**
     * This method is to update a part using
     * the index.
     * @param index the index to lookup
     * @param selectedPart the part to lookup
     */
    public void updatePart(int index, Part selectedPart) {
        allParts.remove(index);
        allParts.add(selectedPart);
    }

    /**
     * This method is to update a product using
     * an index.
     * @param index the index to lookup
     * @param selectedProduct the part to lookup
     */
    public void updateProduct(int index, Product selectedProduct) {
        allProducts.remove(index);
        allProducts.add(selectedProduct);
    }

    /**
     * This method deletes a part from the
     * allParts list.
     * @param selectedPart the part to delete
     * @return boolean
     */
    public boolean deletePart(Part selectedPart) {
        if(allParts.contains(selectedPart)){
            allParts.remove(selectedPart);
            for(Part p : allParts){
                p.setId(allParts.indexOf(p) + 1);
            }
            return true;
        }
        else
            return false;
    }

    /**
     * This method delets a product from the
     * allProducts list.
     * @param selectedProduct the product to delete
     * @return boolean
     */
    public boolean deleteProduct(Product selectedProduct) {
        if(allProducts.contains(selectedProduct)){
            allProducts.remove(selectedProduct);
            for(Product p : allProducts){
                p.setId(allProducts.indexOf(p) + 1001);
            }
            return true;
        }
        else
            return false;
    }

    /**
     * This method returns the ObservableList
     * for the parts.
     * @return ObservableList
     */
    public ObservableList<Part> getAllParts() {
        return allParts;
    }

    /**
     * This method returns the ObservableList
     * for the products.
     * @return ObservableList
     */
    public ObservableList<Product> getAllProducts() {
        return allProducts;
    }
}
