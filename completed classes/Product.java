package Model;

import java.util.ArrayList;
import java.util.HashMap;

public class Product {
    private static final ArrayList<Product> allProducts = new ArrayList<Product>();
    Category category;
    private String explanation;
    private float averageOfScore;
    private final String productId;
    private float price;
    private float views;
    private final ArrayList<String> comments = new ArrayList<>();
    private final HashMap<String, String> specialAttributes = new HashMap<>();
    private final ArrayList<Buyer> listOfBuyers = new ArrayList<>();
    private Boolean existence;
    private Off assignedOff;

    public Product(String productId, float price, Category category, HashMap specialAttributes) {
        this.productId = productId;
        this.price = price;
        this.category = category;
        this.specialAttributes.putAll(specialAttributes);
        this.existence = true;
        allProducts.add(this);
    }

    public static Product getProductById(String id) {
        for (Product product : allProducts) {
            if (product.productId.equalsIgnoreCase(id)) {
                return product;
            }
        }
        return null;
    }

    public static void removeProduct(Product product) {
        allProducts.remove(product);
    }


    public static void compare2Products(Product product) {

    }

    public static ArrayList<Product> getAllProducts() {
        return allProducts;
    }

    public static boolean productWithIdExists(String id) {
        return getProductById(id) != null;
    }

    public static boolean productWithNameExists(String name) {
        for (Product product : allProducts) {
            if (product.specialAttributes.get(0).equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
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

    public void setViews(float views) {
        this.views = views;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public ArrayList<String> getComments() {
        return comments;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public HashMap<String, String> getSpecialAttributes() {
        return specialAttributes;
    }

    public Boolean getExistence() {
        return existence;
    }

    public void setExistence(Boolean existence) {
        this.existence = existence;
    }

    public Off getAssignedOff() {
        return assignedOff;
    }

    public void setAssignedOff(Off assignedOff) {
        this.assignedOff = assignedOff;
    }

    public ArrayList<Buyer> getListOfBuyers() {
        return listOfBuyers;
    }
}
