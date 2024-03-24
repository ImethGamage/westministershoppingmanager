
public class Clothing extends Product { // Class representing a clothing product extending from Product class


    private String size;
    private String color;


    public Clothing(String productId, String productName, int numberOfAvailableItems, double price, String category, String size, String color) {
        // Call the constructor of the parent class (Product) to initialize common details
        super(productId, productName, numberOfAvailableItems, price, category);


        this.color = color;
        this.size = size;
    }

    public Clothing() {

        super();
    }


    public String getSize() { // Getter method for retrieving the size of the clothing
        return size;
    }


    public String getColor() { // Getter method for retrieving the color of the clothing
        return color;
    }


    public void setSize(String size) {
        this.size = size;
    }


    public void setColor(String color) {
        this.color = color;
    }
}


