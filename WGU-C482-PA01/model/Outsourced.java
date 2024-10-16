package model;

/**
 * Outsourced Class is used for Outsourced parts.
 */
public class Outsourced extends Part {
    private String companyName;

    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**
     * getter for Company name.
     */
    public String getCompanyName() {

        return companyName;
    }

    /**
     *
     * setter for Company Name
     */
    public void setCompanyName(String companyName) {

        this.companyName = companyName;
    }
}