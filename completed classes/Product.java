package Model;

import sun.dc.pr.PRError;

import java.util.ArrayList;
import java.util.HashMap;

public class Product {
    private String explanation;
    private float averageOfScore;
    private String productId;
    private float price;
    private float views;
    private ArrayList<String> comments = new ArrayList<>();
    Category category;
    private HashMap<String, String> specialAttributes = new HashMap<>();
    private ArrayList<Buyer> listOfBuyers = new ArrayList<>();
    private Boolean existence;
    private Off assignedOff;
    private static ArrayList<Product> allProducts = new ArrayList<Product>();

    public Product(String productId, float price, Category category, HashMap specialAttributes) {
        this.productId = productId;
        this.price = price;
        this.category = category;
        this.specialAttributes.putAll(specialAttributes);
        this.existence=true;
    }

    public static Product getProductById(String id) {

        return null;
    }

    public static void removeProduct(Product product) {
        allProducts.remove(product);
    }


    public static void compare2Products(Product product) {

    }

    public void setAssignedOff(Off assignedOff) {
        this.assignedOff = assignedOff;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void setViews(float views) {
        this.views = views;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setExistence(Boolean existence) {
        this.existence = existence;
    }

    public String getExplanation() {
        return explanation;
    }

    public float getAverageOfScore() {
        return averageOfScore;
    }

    public String getProductId() {
        return productId;
    }

    public float getViews() {
        return views;
    }

    public float getPrice() {
        return price;
    }

    public ArrayList<String> getComments() {
        return comments;
    }

    public Category getCategory() {
        return category;
    }

    public HashMap<String, String> getSpecialAttributes() {
        return specialAttributes;
    }

    public Boolean getExistence() {
        return existence;
    }

    public Off getAssignedOff() {
        return assignedOff;
    }

    public static ArrayList<Product> getAllProducts() {
        return allProducts;
    }

    public ArrayList<Buyer> getListOfBuyers() {
        return listOfBuyers;
    }

    public static boolean productWithIdExists(String id) {
        return getProductById(id) != null;
    }

    public static boolean productWithNameExists(String name){
        for (Product product:allProducts){
            if (product.specialAttributes.get(0).equalsIgnoreCase(name)){
                return true;
            }
        }
        return false;
    }
}
