import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Comparator;


public class GUI {
    private JFrame frame; // Main frame of the GUI
    private JComboBox<String> productTypeComboBox; // Dropdown menu for selecting product types
    private JTable productTable;  // Table for displaying products details
    private JTextArea productDetailsTopTextArea; // Top text area for displaying detailed product information
    private JTextArea productDetailsBottomTextArea; // Bottom text area for bottom area  product details
    private JButton addToCartButton; // Button to add selected product to the shopping cart
    private DefaultTableModel tableModel;  // Table model for the product table
    private String selectedProductType = "All"; // Default selected product type
    private westminsterShoppingManager west; // Instance of Westminster Shopping Manager
    private ShoppingCart shoppingCart; // Shopping cart for managing selected products

    // Constructor to initialize the GUI
    public GUI(westminsterShoppingManager west) {
        this.west = west;
        this.shoppingCart = new ShoppingCart();
        initializeGUI();
        initializeTableCellRenderer();
    }


    private void initializeGUI() { // Method to create the GUI and layout
        frame = new JFrame("Westminster Shopping Center"); // Create the main frame of the GUI

        String[] productTypes = {"All", "Electronics", "Clothes"};
        productTypeComboBox = new JComboBox<>(productTypes); // Create a ComboBox with product types

        // Product Table
        tableModel = new DefaultTableModel(new Object[]{"Product ID", "Product Name", "Category", "Price($)", "Information"}, 0);
        productTable = new JTable(tableModel); // Create a table for displaying product details
        productTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane tableScrollPane = new JScrollPane(productTable); // Create a scroll pane for the table

        // Add a sorter to the table model to enable alphabetical sorting
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModel);
        productTable.setRowSorter(sorter);

        // Update the Comparator to sort based on the second column (Product Name)
        Comparator<String> alphabetComparator = Comparator.naturalOrder();
        sorter.setComparator(1, alphabetComparator);

        // Product Details Top TextArea
        productDetailsTopTextArea = new JTextArea(); // Create a top text area for detailed product information
        productDetailsTopTextArea.setEditable(false);
        productDetailsTopTextArea.setFont(new Font("SansSerif", Font.BOLD, 16)); // Set the font style

        // Product Details Bottom TextArea
        productDetailsBottomTextArea = new JTextArea(); // Create a bottom text area for bottom product details
        productDetailsBottomTextArea.setEditable(false); // Set the text area as not editable

        addToCartButton = new JButton("Add to Cart"); // Create the "Add to Cart" button
        addToCartButton.setEnabled(false);

        JButton viewShoppingCartButton = new JButton("Shopping Cart"); // Create the "View Shopping Cart" button

        JPanel controlPanel = new JPanel(); // Create a panel for control elements
        controlPanel.add(productTypeComboBox); // Add the product type ComboBox
        controlPanel.add(viewShoppingCartButton); // Add the "View Shopping Cart" button

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addToCartButton); // Add the "Add to Cart" button

        JPanel southPanel = new JPanel(new BorderLayout()); // Create a panel for the bottom area
        JLabel headingLabel = new JLabel("Product Details"); // Create a label for the heading
        headingLabel.setFont(new Font("SansSerif", Font.BOLD, 20)); // Set font style for the heading in the below text area
        southPanel.add(headingLabel, BorderLayout.NORTH); // Add the heading label to the top of the bottom area
        southPanel.add(productDetailsTopTextArea, BorderLayout.CENTER);
        southPanel.add(buttonPanel, BorderLayout.SOUTH);

        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, tableScrollPane, southPanel); // Create a split pane for the main layout
        splitPane.setDividerLocation(400); // Set the initial divider location

        productDetailsTopTextArea.setPreferredSize(new Dimension(800, 200)); // Set preferred size for the top text area

        frame.setLayout(new BorderLayout());
        frame.add(controlPanel, BorderLayout.NORTH);
        frame.add(splitPane, BorderLayout.CENTER);

        frame.setSize(800, 700); // Set the size of the frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Set default close operation
        frame.setVisible(true);

        addProductTypeComboBoxListener(); // Add a listener for the product type ComboBox

        productTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                updateProductDetails(); // Add a listener for changes in the selected row in the product table
            }
        });

        addToCartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addToShoppingCart(); // Add an action listener for the "Add to Cart" button
            }
        });

        viewShoppingCartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewShoppingCart(); // Add an action listener for the "View Shopping Cart" button
            }
        });
    }


    private void addProductTypeComboBoxListener() { // Method to add a listener for the product type combo box
        productTypeComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedProductType = productTypeComboBox.getSelectedItem().toString(); // Get the selected product type
                ArrayList<Product> filteredProducts = west.filterProductsByType(selectedProductType); // Filter products based on the selected type
                populateProductTable(filteredProducts); // Update the product table with filtered products
            }
        });
    }


    private void updateProductDetails() {  // Method to update the product details displayed when a product is selected
        int selectedRow = productTable.getSelectedRow();  // Get the index of the selected row in the product table
        if (selectedRow >= 0) {  // Check if a valid row is selected
            String productId = tableModel.getValueAt(selectedRow, 0).toString();  // Get the product ID from the selected row
            String productName = tableModel.getValueAt(selectedRow, 1).toString();  // Get the product name from the selected row
            double price = Double.parseDouble(tableModel.getValueAt(selectedRow, 3).toString());  // Get the price from the selected row
            String category = tableModel.getValueAt(selectedRow, 2).toString();  // Get the category from the selected row

            String details;  // String to hold the detailed product information

            Product selectedProduct = west.getProductById(productId);  // Retrieve the product object based on the product ID

            if (selectedProduct != null) {  // Check if the product is found
                if ("Clothing".equalsIgnoreCase(category)) {  // Check if the category is Clothing
                    Clothing clothingProduct = (Clothing) selectedProduct;
                    String size = clothingProduct.getSize();
                    String color = clothingProduct.getColor();
                    int availableItems = clothingProduct.getNumberOfAvailableItems();  // Get the number of available items

                    details = String.format("Product ID: %s\nCategory: %s\nProduct Name: %s\nSize: %s\nColor: %s\nPrice: $%.2f\nItems Available: %d",
                            productId, category, productName, size, color, price, availableItems);

                } else if ("Electronics".equalsIgnoreCase(category)) {  // Check if the category is Electronic
                    Electronics electronicsProduct = (Electronics) selectedProduct;
                    int warrantyPeriod = electronicsProduct.getWarrentyPeriod();
                    String brand = electronicsProduct.getBrand();
                    int availableItems = electronicsProduct.getNumberOfAvailableItems();

                    details = String.format("Product ID: %s\nCategory: %s\nProduct Name: %s\nWarranty Period: %d weeks\nBrand: %s\nPrice: $%.2f\nItems Available: %d",
                            productId, category, productName, warrantyPeriod, brand, price, availableItems);
                } else {
                    details = "Unknown Category";  // If the category is neither Clothing nor Electronic display  it as an unknown category
                }

                // Set the detailed product information in the top text area
                productDetailsTopTextArea.setText(details);

                // Enable or disable the addToCartButton based on the availability of items
                addToCartButton.setEnabled(selectedProduct.getNumberOfAvailableItems() > 0);

                // Set the detailed product information in the bottom text area
                productDetailsBottomTextArea.setText(details);

                // Refresh the frame to reflect the changes
                frame.revalidate();
                frame.repaint();
            }
        }
    }


    private void addToShoppingCart() {  // Method to add a selected product to the shopping cart
        int selectedRow = productTable.getSelectedRow(); // Get the index of the selected row in the product table
        if (selectedRow >= 0) {  // Check if a valid row is selected
            String productId = tableModel.getValueAt(selectedRow, 0).toString();  // Get the product ID from the selected row

            Product selectedProduct = null;  // Create a variable to hold the selected product
            for (Product product : west.getShoppingList()) {  // Iterate through the products in the shopping list
                if (product.getProductId().equals(productId)) {  // Check if the product ID matches the selected product ID
                    selectedProduct = product;  // Assign the selected product
                    break;  // Exit the loop once the selected product is found
                }
            }

            if (selectedProduct != null && selectedProduct.getNumberOfAvailableItems() > 0) {
                selectedProduct.decreaseAvailableItems();  // Decrease the available items for the selected product
                shoppingCart.addProduct(selectedProduct);  // Add the selected product to the shopping cart
                updateProductDetails();  // Update the product details display in the GUI
            } else {
                // Display a warning message if the selected product is out of stock
                JOptionPane.showMessageDialog(frame, "Sorry, this product is out of stock.", "Out of Stock", JOptionPane.WARNING_MESSAGE);
            }
        }
    }


    //
    private void viewShoppingCart() { //Method to view the contents of the shopping cart
        ArrayList<Product> shoppingCartProducts = shoppingCart.getProducts();

        // Create a table model for shopping cart details
        DefaultTableModel cartTableModel = new DefaultTableModel();
        cartTableModel.addColumn("Product"); //Create product column
        cartTableModel.addColumn("Quantity"); //Create Quantity column
        cartTableModel.addColumn("Price"); //Create Price column

        // Create table model with shopping cart details
        for (Product product : shoppingCartProducts) {
            cartTableModel.addRow(new Object[]{
                    product.getProductName(),
                    1,
                    product.getPrice()
            });
        }


        JTable cartTable = new JTable(cartTableModel);  // Create a JTable with the table model
        JScrollPane cartTableScrollPane = new JScrollPane(cartTable);
        cartTable.setPreferredScrollableViewportSize(new Dimension(400, 150)); // Create for Set preferred size for the table

        // Calculate total cost and discounts
        double totalPrice = shoppingCart.calculateTotalCost();
        double discount20Percent = shoppingCart.calculateDiscountedPrice();


        JTextArea totalTextArea = new JTextArea();// Create a text area to display total, discounts, and total cost
        totalTextArea.setEditable(false);
        totalTextArea.append("Total: $" + totalPrice + "\n\n");
        totalTextArea.append("20% Discount: -$" + discount20Percent + "\n\n");
        totalTextArea.append("Total Cost: $" + (totalPrice - discount20Percent));
        totalTextArea.setFont(new Font("SansSerif", Font.BOLD, 12));


        JPanel cartPanel = new JPanel(new BorderLayout()); // Create a panel to hold the table and total details
        cartPanel.add(cartTableScrollPane, BorderLayout.CENTER);
        cartPanel.add(totalTextArea, BorderLayout.SOUTH);


        JOptionPane.showMessageDialog(frame, cartPanel, "Shopping Cart", JOptionPane.INFORMATION_MESSAGE); // Show the cart panel
    }


    private void initializeTableCellRenderer() {

        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer() { // Create a custom cell renderer for the productTable
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                // Call the superclass method to get the default rendering component
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                // Determine the correct column index for available items using product type
                int availableItemsColumnIndex;
                String category = tableModel.getValueAt(row, 2).toString();

                if ("Clothing".equalsIgnoreCase(category) || "Electronics".equalsIgnoreCase(category)) {
                    availableItemsColumnIndex = 4;
                } else {
                    availableItemsColumnIndex = -1; // Invalid category handle
                }

                // Check if the available items are less than or equal to 3 and set the row background color to red
                if (availableItemsColumnIndex != -1) {
                    int availableItems = Integer.parseInt(tableModel.getValueAt(row, availableItemsColumnIndex).toString().replaceAll("[^0-9]", ""));
                    c.setBackground(availableItems <= 3 ? Color.RED : table.getBackground());
                } else {
                    c.setBackground(table.getBackground());
                }

                return c;
            }
        };

        // Apply the custom renderer to each column in the productTable
        for (int i = 0; i < tableModel.getColumnCount(); i++) {
            productTable.getColumnModel().getColumn(i).setCellRenderer(renderer);
        }
    }



    public void populateProductTable(ArrayList<Product> productList) {  // Method to create the product table
        tableModel.setRowCount(0);  // Clear the existing rows in the table model
        for (Product product : productList) {  // Iterate through the list of products
            Object[] rowData;

            if (product instanceof Clothing) {  // Check if the product is an instance of Clothing
                Clothing clothingProduct = (Clothing) product;
                rowData = new Object[]{  // Create an array with product details
                        product.getProductId(),
                        product.getProductName(),
                        product.getCategory(),
                        product.getPrice(),
                        "Color: " + clothingProduct.getColor() + ", Available Items = " + clothingProduct.getNumberOfAvailableItems() + ", Size: " + clothingProduct.getSize()
                };
            } else if (product instanceof Electronics) {  // Check if the product is an instance of Electronics
                Electronics electronicsProduct = (Electronics) product;
                rowData = new Object[]{  // Create an array with product details
                        product.getProductId(),
                        product.getProductName(),
                        product.getCategory(),
                        product.getPrice(),
                        "Brand: " + electronicsProduct.getBrand() + ", Available Items = " + electronicsProduct.getNumberOfAvailableItems()
                };
            } else {
                rowData = new Object[]{};  // If the product type is unknown, create an empty array
            }

            tableModel.addRow(rowData);  // Add the created row data to the table model
        }
    }
}