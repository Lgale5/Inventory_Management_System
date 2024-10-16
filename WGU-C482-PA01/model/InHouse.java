package model;

/**
 * InHouse Class is used for InHouse parts.
 */
public class InHouse extends Part {
    private int machineID;

    public InHouse(int id, String name, double price, int inStock, int min, int max, int machineID) {
        super(id, name, price, inStock, min, max);
        this.machineID = machineID;
    }

    public void setMachineID(int machineID) {
        this.machineID = machineID;
    }

    public int getMachineID() {

        return machineID;
    }

}
