package Model;

import java.util.ArrayList;
import java.util.HashMap;

public class Product {
    private String explanation;
    private float averageOfScore;
    private String productId;
    private String name;
    private String brand;
    private float price;
    private ArrayList<String> comments = new ArrayList<>();
    Category category;
    private HashMap<String, String> specialAttributes = new HashMap<>();
    private Boolean existence;
    private Off assignedOff;

    public Product(String productId, String name, String brand, float price, Category category) {
        this.productId = productId;
        this.name = name;
        this.brand = brand;
        this.price = price;
        this.category = category;
    }

    public static Product getProductByName(String id) {

        return null;
    }


    public static void compare2Products(Product product) {

    }

}
