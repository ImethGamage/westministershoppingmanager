
import java.util.ArrayList;


public class ShoppingCart {// Class to represent a shopping cart details


    private ArrayList<Product> products; // ArrayList to store the products that user add to the shopping cart


    public ShoppingCart() {
        this.products = new ArrayList<>();
    }


    public void addProduct(Product product) {  // Method to add a product to the shopping cart

        products.add(product);
    }

    public double calculateTotalCost() {
        // Method to calculate the total cost of all products in the shopping cart
        double totalCost = 0.0;

        for (Product product : products) {// Iterate through each product in the cart and calculate their prices
            totalCost += product.getPrice();
        }
        return totalCost;
    }


    public double calculateDiscountedPrice() {// Method to calculate the discounted price
        double totalDiscount = 0.0;

        // Track quantities for a specific category
        int electronicQuantity = 0;
        int clothingQuantity = 0;

        // Calculate quantities for each category
        for (Product product : products) {
            if ("Electronics".equalsIgnoreCase(product.getCategory())) {
                electronicQuantity++;
            } else if ("Clothing".equalsIgnoreCase(product.getCategory())) {
                clothingQuantity++;
            }
        }

        // Check if the user is eligible for a 20% discount for the same category
        if (electronicQuantity >= 3 || clothingQuantity >= 3) {
            totalDiscount = calculateTotalCost() * 0.20; // 20% discount
        }

        return totalDiscount;
    }

    // Getter method to retrieve the list of products in the shopping cart
    public ArrayList<Product> getProducts() {
        return products;
    }
}
