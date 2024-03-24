public class Electronics extends Product{// Class representing a Electronic product extending from Product class


    private int warrentyPeriod;

    private String brand;

    public Electronics(String productId, String productName, int numberOfAvailableItems, double price,String category,int warrantyPeriod,String brand) {
        super(productId, productName, numberOfAvailableItems, price,category);
        this.brand = brand;
        this.warrentyPeriod = warrantyPeriod;
    }

    public Electronics() {

    }


    public int getWarrentyPeriod() {
        return warrentyPeriod;
    } // Getter method for retrieving the warrenty of the electronic product

    public String getBrand() {
        return brand;
    } // Getter method for retrieving the brand of the electronic
    public void setWarrentyPeriod(int warrentyPeriod) {
        this.warrentyPeriod = warrentyPeriod;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }


}

