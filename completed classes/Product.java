package Model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

public class Product {
    private static final ArrayList<Product> allProducts = new ArrayList<Product>();
    Category category;
    private String explanation;
    private float averageScore;
    private final String productId;
    private float price;
    private float views;
    private final ArrayList<String> comments = new ArrayList<>();
    private final HashMap<String, String> specialAttributes = new HashMap<>();
    private final ArrayList<Buyer> listOfBuyers = new ArrayList<>();
    private Boolean existence;
    private Off assignedOff;
    private float priceAfterOff;
    private LocalDateTime creationDate;

    public Product(String productId, float price, Category category, HashMap specialAttributes,String explanation) {
        this.productId = productId;
        this.price = price;
        this.category = category;
        this.specialAttributes.putAll(specialAttributes);
        this.existence = true;
        this.creationDate=LocalDateTime.now();
        this.explanation=explanation;
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

    public float getAverageScore() {
        return averageScore;
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

    public void setPriceAfterOff() {
        this.priceAfterOff = 100 - assignedOff.getOffAmount() * price / 100;
    }

    public float getPriceAfterOff() {
        return priceAfterOff;
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

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setAverageScore(float averageScore) {
        this.averageScore = averageScore;
    }

   @Override
    public String toString() {
        StringBuilder stringBuilder=new StringBuilder();
        stringBuilder.append(productId+" "+specialAttributes.get("name")+"\nBrand: "+specialAttributes.get("brand"));
        stringBuilder.append("\ncategory: "+category.getName());
        stringBuilder.append("\n price: "+price);
        if (assignedOff!=null)stringBuilder.append("\tprice after "+assignedOff.getOffAmount()+"% off: "+priceAfterOff);
        if (existence)stringBuilder.append("\nstatus: available");
        else stringBuilder.append("\nstatus: unavailable");
        stringBuilder.append('\n'+explanation);
        for (String attribute:specialAttributes.keySet()){
            stringBuilder.append('\n'+attribute+": "+specialAttributes.get(attribute));
        }
        return stringBuilder.toString();
    }
}
