package Model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

public class Product {
    protected static ArrayList<Product> allProducts = new ArrayList<Product>();
    protected Seller seller;
    protected String name;
    protected Category category;
    protected String explanation;
    protected float averageScore;
    protected String productId;
    protected float price;
    protected int views;
    protected ArrayList<Comment> comments = new ArrayList<>();
    protected HashMap<String, String> specialAttributes = new HashMap<>();
    protected ArrayList<Buyer> listOfBuyers = new ArrayList<>();
    protected Boolean existence;
    protected Off assignedOff;
    protected float priceAfterOff;
    protected LocalDateTime creationDate;
    protected String picture;

    public Product(String productId, float price, Category category, HashMap<String, String> specialAttributes, String explanation, Seller seller, String picture) {
        this.name = specialAttributes.get("name");
        this.productId = productId;
        this.price = price;
        this.category = category;
        this.specialAttributes.putAll(specialAttributes);
        this.existence = true;
        this.creationDate = LocalDateTime.now();
        this.explanation = explanation;
        this.seller = seller;
        this.picture = picture;
//        this.picture.resize(100, 100);
//        this.picture.setFitHeight(100);
//        this.picture.setFitWidth(100);
        this.seller.getArrayProduct().add(this);
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

    public String getBrand() {
        return specialAttributes.get("brand");
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

    public ArrayList<Comment> getComments() {
        return comments;
    }

    public Category getCategory() {
        return category;
    }

    public String getName() {
        return name;
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
        this.setPriceAfterOff();
    }

    public ImageView getPicture() {
        ImageView image=new ImageView(new Image(picture));
        image.resize(100,100);
        image.setFitWidth(100);
        image.setFitHeight(100);
        return image;
    }

    public void setPicture(String picture) {
        this.picture=picture;
    }

    public String getPicturePath(){
        return picture;
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

    public String getRemainedTime() {
        return this.assignedOff.getRemainedTime();
    }

    public int getOffPercent() {
        return this.assignedOff.getOffAmount();
    }

    public static void compare2Products(Product product1, Product product2) {
        System.out.println("product " + product1.getProductId());
        System.out.println(product1.toString());
        System.out.println("\n\nproduct " + product2.getProductId());
        System.out.println(product2.toString());
    }

    public String initialToString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(productId + " " + specialAttributes.get("name") + "\nBrand: " + specialAttributes.get("brand"));
        stringBuilder.append("\ncategory: " + category.getName());
        stringBuilder.append("\n price: " + price);
        if (assignedOff != null)
            stringBuilder.append("\tprice after " + assignedOff.getOffAmount() + "% off: " + priceAfterOff);
        if (existence) stringBuilder.append("\nstatus: available");
        else stringBuilder.append("\nstatus: unavailable");
        stringBuilder.append("\naverage score: " + averageScore);
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
