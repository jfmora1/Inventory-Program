package model;

/**
 * InHouse Class that extends Part class
 */
public class InHouse extends Part {

    private int machineID;

    public InHouse(int id, String name, double price, int stock, int min, int max, int machineID) {

        super(id, name, price, stock, min, max);
        this.machineID = machineID;
    }

    public int getMachineID() {
        return machineID;
    }

    public void setMachineID(int id) {
        machineID = id;
    }
}
