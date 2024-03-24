public interface ShoppingManager {

    void addNewProduct(Product product);// Add products

    void deleteProduct(String productIdToDelete); // delete products

    void printListOfProduct(); // print list of product

    void saveFile(); // Save to Text file

    void loadFile(); //Load from text file

}