package sample;
/**
 * Class OutSourced.java
 */

/**
 *
 * @author Frank Xander Morales
 */
public class Outsourced extends Part{
    private String companyName;

    /**
     * The Constructor sets the id, name,
     * price, stock, min, max, and companyName.
     * @param id The part ID.
     * @param name The part name.
     * @param price The price of part.
     * @param stock The stock of part.
     * @param min The minimum amount of stock.
     * @param max The maximum amount of stock.
     * @param companyName The company name.
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**
     * This method returns the company name.
     * @return the companyName
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * This method sets the company name.
     * @param companyName the company name to set
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
