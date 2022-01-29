package model;

/**
 * This is the OutSourced class
 */
public class OutSourced extends Part {
    private String companyName;

    public OutSourced(int id, String name, double price, int stock, int min, int max, String company_name) {
        super(id, name, price, stock, min, max);
        setCompanyName(company_name);
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String name) {
        companyName = name;

    }

}
