
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;
import java.io.*;


public class westminsterShoppingManager implements ShoppingManager {


    private ArrayList<Product> shoppingList; // Main List to store the products


    westminsterShoppingManager() {
        this.shoppingList = new ArrayList<>();
    }


    public void displayMenu() { // Display the menu options
        System.out.print("1) Add a new product\n");
        System.out.print("2) Delete a product\n");
        System.out.print("3) Print the list of the products\n");
        System.out.print("4) Save in a file\n");
        System.out.print("5) Load from the File\n");
        System.out.print("6) Open the GUI\n");
    }

    // Getter method to retrieve the shoppingList
    public ArrayList<Product> getShoppingList() {
        return shoppingList;
    }


    @Override
    public void addNewProduct(Product product) {

        // Check if the shoppingList is null
        if (this.shoppingList == null) {
            this.shoppingList = new ArrayList<>();
        }


        if (shoppingList.size() >= 50) {// Check if the maximum limit of products 50 is reached
            System.out.println("The system has reached the maximum limit of 50 products. Cannot add more products.");
            return;
        }

        // Add the product to the shoppingList
        shoppingList.add(product);
        System.out.println("\nProduct Successfully added to the list"); // Print message successfully added product
    }


    @Override
    public void deleteProduct(String productIdToDelete) { // Method to delete a product from the shoppingList using product ID

        // Delete the product with the specified ID
        boolean removed = shoppingList.removeIf(product -> product.getProductId().equals(productIdToDelete));

        // Check if any product was removed
        if (removed) {
            System.out.println("Product with ID " + productIdToDelete + " has been deleted."); // If any item removed display that product Id and message
        } else {
            System.out.println("Product with ID " + productIdToDelete + " not found."); // If item not found display message
        }
    }

    // Method to print the list of product in the shoppingList
    @Override
    public void printListOfProduct() {

        // Sort the shoppingList based on Product Name
        Collections.sort(shoppingList, Comparator.comparing(Product::getProductName));

        // Check if shoppingList is empty before printing
        if (shoppingList.isEmpty()) {
            System.out.println("No products to print. Shopping list is empty.");
            return;
        }

        // Print details of each product in the sorted productList
        for (Product product : shoppingList) {
            if (product instanceof Electronics) {
                Electronics electronicsProduct = (Electronics) product;
                System.out.println("\nElectronic Product " + "\nProduct Name:" +
                        electronicsProduct.getProductName() + "\nProduct Id: " + electronicsProduct.getProductId() +
                        " \nPrice: $" + electronicsProduct.getPrice() +
                        "\nBrand: " + electronicsProduct.getBrand() + "\nNumber of Available product " + electronicsProduct.getNumberOfAvailableItems() +
                        "\nWarranty: " + electronicsProduct.getWarrentyPeriod() + " months");
            } else if (product instanceof Clothing) {
                Clothing clothingProduct = (Clothing) product;
                System.out.println("\nClothing Product " + "\nProduct Name:" +
                        clothingProduct.getProductName() + "\nProduct Id:" + clothingProduct.getProductId() +
                        " \nPrice: $" + clothingProduct.getPrice() +
                        "\nSize: " + clothingProduct.getSize() +
                        "\nColor: " + clothingProduct.getColor());
            }
        }
    }


    // Method to save the products in the shoppingList to a Text file
    @Override
    public void saveFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("products.txt"))) {

            // Check if shoppingList is empty before saving
            if (shoppingList.isEmpty()) {
                System.out.println("No products to save. Shopping list is empty.");
                return;
            }


            // Iterate through the shoppingList and write product details to the file
            for (Product product : shoppingList) {
                if (product instanceof Electronics) {
                    Electronics electronicProduct = (Electronics) product;
                    writer.write("Product Type - Electronic\n");
                    writer.write("Product ID - " + product.getProductId() + "\n");
                    writer.write("Product Name - " + product.getProductName() + "\n");
                    writer.write("Products Available - " + product.getNumberOfAvailableItems() + "\n");
                    writer.write("Product Price - " + product.getPrice() + "\n");
                    writer.write("Product Brand - " + electronicProduct.getBrand() + "\n");
                    writer.write("Product warranty period - " + electronicProduct.getWarrentyPeriod() + "\n");
                } else if (product instanceof Clothing) {
                    Clothing clothingProduct = (Clothing) product;
                    writer.write("Product Type - Clothing\n");
                    writer.write("Product ID - " + product.getProductId() + "\n");
                    writer.write("Product Name - " + product.getProductName() + "\n");
                    writer.write("Products Available - " + product.getNumberOfAvailableItems() + "\n");
                    writer.write("Product Price - " + product.getPrice() + "\n");
                    writer.write("Product Size - " + clothingProduct.getSize() + "\n");
                    writer.write("Product colour - " + clothingProduct.getColor() + "\n");
                }
                writer.write("\n");
            }
            System.out.println("All products saved to the file successfully.");
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    // Method to load products from a file into the shoppingList
    @Override
    public void loadFile() {
        try (Scanner readFile = new Scanner(new File("products.txt"))) {

            // Clear the shoppingList before loading
            shoppingList.clear();

            // Set the delimiter for reading product blocks from the file
            readFile.useDelimiter("\\n\\n");

            // Check if the file is empty before loading
            if (!readFile.hasNext()) {
                System.out.println("No products to load. File is empty.");
                return;
            }

            // Iterate through the product in the file
            while (readFile.hasNext()) {
                String productBlock = readFile.next();

                String productType = extractValue(productBlock, "Product Type");
                String productId = extractValue(productBlock, "Product ID");
                String productName = extractValue(productBlock, "Product Name");

                int availableItems;
                try {
                    availableItems = Integer.parseInt(extractValue(productBlock, "Products Available"));
                } catch (NumberFormatException e) {
                    System.out.println("Invalid Products Available value. Setting to 0.");
                    availableItems = 0;
                }

                double price;
                try {
                    price = Double.parseDouble(extractValue(productBlock, "Product Price"));
                } catch (NumberFormatException e) {
                    System.out.println("Invalid Product Price value.");
                    price = 0.0;
                }

                Product product;
                if (productType.equals("Electronic")) {
                    String brand = extractValue(productBlock, "Product Brand");

                    int warrantyPeriod;
                    try {
                        warrantyPeriod = Integer.parseInt(extractValue(productBlock, "Product warranty period"));
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid Product warranty period value. Setting to 0.");
                        warrantyPeriod = 0;
                    }
                    product = new Electronics(productId, productName, availableItems, price, productType, warrantyPeriod, brand);
                } else {
                    String size = extractValue(productBlock, "Product Size");
                    String color = extractValue(productBlock, "Product colour");
                    product = new Clothing(productId, productName, availableItems, price, productType, size, color);
                }

                addNewProduct(product);// Add the new product to the shoppingList
            }
            System.out.println("Successfully loaded products from the file.");
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        }
    }


    private String extractValue(String block, String key) {
        String[] lines = block.split("\\n");
        for (String line : lines) {
            if (line.startsWith(key)) {
                return line.substring(line.indexOf("-") + 1).trim();
            }
        }
        return "";
    }

    // Method to check if a product ID already exists in the shoppingList
    public boolean isProductIdExists(String productId) {

        // Check if shoppingList is null
        if (shoppingList == null) {
            return false;
        }

        // Iterate over shoppingList only if it's not null
        for (Product product : shoppingList) {
            if (product.getProductId().equals(productId)) {
                return true; // Product ID already exists
            }
        }
        // If no match is found, return false
        return false; // Product ID does not exist
    }

    // Method to filter products in the shoppingList based on product type
    public ArrayList<Product> filterProductsByType(String productType) {
        ArrayList<Product> filteredProducts = new ArrayList<>();


        for (Product product : shoppingList) {// Iterate through the shoppingList and add products matching the specified type
            if (productType.equals("All") || (productType.equals("Electronics") && product instanceof Electronics) || (productType.equals("Clothes") && product instanceof Clothing)) {
                filteredProducts.add(product);// Filter product add to the list
            }
        }
        return filteredProducts;
    }

    // Method to retrieve a product from the shoppingList using its ID
    public Product getProductById(String productId) {


        for (Product product : shoppingList) {// Iterate through the shoppingList to find the product with the specified ID
            if (product.getProductId().equals(productId)) {
                return product;// return specific product
            }
        }
        return null; // Product not found
    }
}




