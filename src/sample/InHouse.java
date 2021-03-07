package sample;
/**
 * Class InHouse.java
 */

/**
 *
 * @author Frank Xander Morales
 */
public class InHouse extends Part{
    private int machineId;

    /**
     * The Constructor sets the ID, name,
     * price, stock, min, max, and ID of the machine.
     * @param id The part ID.
     * @param name The part name.
     * @param price The price of part.
     * @param stock The stock of part.
     * @param min The minimum amount of stock.
     * @param max The maximum amount of stock.
     * @param machineId The ID of the machine.
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    /**
     * This method gets the machine ID.
     * @return the machineId
     */
    public int getMachineId() {
        return machineId;
    }

    /**
     * This method sets the machine ID.
     * @param machineId the machineId to set
     */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }
}
