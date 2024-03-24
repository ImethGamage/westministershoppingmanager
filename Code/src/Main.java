import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        westminsterShoppingManager west = new westminsterShoppingManager(); // Create a shopping manager object
        Scanner input = new Scanner(System.in); // Create a scanner for user input

        west.displayMenu(); // Call the  Display the main menu method

        while (true) {
            try {
                System.out.print("\nEnter your choice: ");
                String userChoice = input.next();
                input.nextLine();//the newline character

                switch (userChoice) {
                    case "1":
                        System.out.print("Enter product Id = ");
                        String productId = input.next();

                        if (west.isProductIdExists(productId)) { // Check if the product ID already exists
                            System.out.println("Product with ID " + productId + " already exists. Please choose a different ID.");
                            continue; // Go back to the beginning of the loop
                        }

                        System.out.print("Enter product Name = ");
                        String productName = input.next();
                        System.out.print("Enter number of available products = ");
                        int numberOfAvailableItems = input.nextInt();
                        System.out.print("Enter product price = ");
                        double price = input.nextDouble();
                        System.out.print("Enter product type (Electronics/Clothing) = ");
                        String category = input.next();

                        if (category.equals("Electronics")) { // Adding Electronics product
                            System.out.print("Enter the warranty period (weeks) = ");
                            int warrantyPeriod = input.nextInt();
                            System.out.print("Enter the brand name = ");
                            String brand = input.next();

                            Electronics electronic = new Electronics(productId, productName, numberOfAvailableItems, price, category, warrantyPeriod, brand);
                            west.addNewProduct(electronic);

                        } else if (category.equals("Clothing")) { // Adding Clothing product
                            String size;
                            while (true) {
                                System.out.print("Enter the Size (Small, Medium, Large): ");
                                size = input.next().toLowerCase();
                                if (size.equals("small") || size.equals("medium") || size.equals("large")) {
                                    break;
                                } else {
                                    System.out.println("Invalid size. Please enter Small, Medium, or Large.");
                                }
                            }
                            System.out.print("Enter the Color = ");
                            String color = input.next();

                            Clothing clothing = new Clothing(productId, productName, numberOfAvailableItems, price, category, size, color);
                            west.addNewProduct(clothing);

                        } else {
                            System.out.println("Invalid option");
                            continue;
                        }
                        break;

                    case "2":
                        // Get user input for the product ID to delete
                        System.out.print("Enter product ID to delete: ");
                        String productIdToDelete = input.next();
                        west.deleteProduct(productIdToDelete);
                        break;

                    case "3":
                        west.printListOfProduct();  // Call Printing the list of products method
                        break;

                    case "4":
                        west.saveFile(); //Call the save file method
                        break;

                    case "5":
                        west.loadFile();  //call the load file method
                        break;

                    case "6":

                        GUI shoppingGUI = new GUI(west);  // Create  object from the GUI

                        // Assuming there's a method in your GUI to populate the product table
                        ArrayList<Product> productList = west.getShoppingList();

                        if (productList != null) {
                            shoppingGUI.populateProductTable(productList);
                        } else {
                            System.out.println("Product list is null. Check your initialization logic.");
                        }
                        break;

                    default:
                        System.out.println("Invalid choice. Please enter a valid menu option."); // Handling invalid menu choice3
                }

            } catch (Exception e) {
                System.out.println("Invalid input. Please try again.");
                input.nextLine(); // Consume the invalid input to avoid an infinite loop
            }

            System.out.println();
        }
    }
}
