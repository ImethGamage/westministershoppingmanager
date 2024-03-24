
public abstract class Product { // Abstract class representing product

    // Instance variables to store product details
    private String productId;
    private String productName;
    private int numberOfAvailableItems;
    private double price;

    private String category; // Category used for GUI

    // Constructor to initialize product details
    public Product(String productId, String productName, int numberOfAvailableItems, double price, String category) {
        this.productId = productId;
        this.productName = productName;
        this.numberOfAvailableItems = numberOfAvailableItems;
        this.price = price;
        this.category = category;
    }


    public Product() { // Default constructor
    }

    public String getProductId() { // Getter method for retrieving the product ID
        return productId;
    }


    public String getProductName() {  // Getter method for retrieving the product name
        return productName;
    }


    public int getNumberOfAvailableItems() {  // Getter method for retrieving the number of available items
        return numberOfAvailableItems;
    }


    public double getPrice() {  // Getter method for retrieving the product price
        return price;
    }


    public void setProductId(String productId) {
        this.productId = productId;
    }


    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setNumberOfAvailableItems(int numberOfAvailableItems) {
        this.numberOfAvailableItems = numberOfAvailableItems;
    }


    public void setPrice(double price) {
        this.price = price;
    }


    public String getCategory() { // Getter method for retrieving the product category to know product is electronic or clothing for GUI
        return category;
    }


    public void decreaseAvailableItems() {  // Method to decrease the number of available items when adding to shopping cart

        if (numberOfAvailableItems > 0) {
            numberOfAvailableItems--;
        }
    }
}
