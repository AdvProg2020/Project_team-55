package Model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

public class Product {
    private static ArrayList<Product> allProducts = new ArrayList<Product>();
    private Seller seller;
    Category category;
    private String explanation;
    private float averageScore;
    private String productId;
    private float price;
    private int views;
    private ArrayList<Comments> comments = new ArrayList<>();
    private HashMap<String, String> specialAttributes = new HashMap<>();
    private ArrayList<Buyer> listOfBuyers = new ArrayList<>();
    private Boolean existence;
    private Off assignedOff;
    private float priceAfterOff;
    private LocalDateTime creationDate;

    public Product(String productId, float price, Category category, HashMap specialAttributes, String explanation, Seller seller) {
        this.productId = productId;
        this.price = price;
        this.category = category;
        this.specialAttributes.putAll(specialAttributes);
        this.existence = true;
        this.creationDate = LocalDateTime.now();
        this.explanation = explanation;
        this.seller = seller;
        seller.getArrayProduct().add(this);
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

    public float getAverageScore() {
        return averageScore;
    }

    public String getProductId() {
        return productId;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Seller getSeller() {
        return seller;
    }

    public void setPriceAfterOff() {
        this.priceAfterOff = 100 - assignedOff.getOffAmount() * price / 100;
    }

    public float getPriceAfterOff() {
        return priceAfterOff;
    }

    public ArrayList<Comments> getComments() {
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

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setAverageScore(float averageScore) {
        this.averageScore = averageScore;
    }

    public static void compare2Products(Product product1,Product product2) {
        System.out.println("product "+product1.getProductId());
        System.out.println(product1.toString());
        System.out.println("\n\nproduct "+product2.getProductId());
        System.out.println(product2.toString());
    }

    public String initialToString(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(productId + " " + specialAttributes.get("name") + "\nBrand: " + specialAttributes.get("brand"));
        stringBuilder.append("\ncategory: " + category.getName());
        stringBuilder.append("\n price: " + price);
        if (assignedOff != null)
            stringBuilder.append("\tprice after " + assignedOff.getOffAmount() + "% off: " + priceAfterOff);
        if (existence) stringBuilder.append("\nstatus: available");
        else stringBuilder.append("\nstatus: unavailable");
        stringBuilder.append("\naverage score: "+averageScore);
        stringBuilder.append('\n' + explanation);
        return stringBuilder.toString();
    }
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append('\n' + explanation);
        for (String attribute : specialAttributes.keySet()) {
            stringBuilder.append('\n' + attribute + ": " + specialAttributes.get(attribute));
        }
        return stringBuilder.toString();
    }
}
