package Model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Random;

public class ProductAdditionRequest extends Request {
    private String productId;
    private float price;
    private Category category;
    private String explanation;
    private Image picture;
    private HashMap<String, String> specialAttributes;


    public ProductAdditionRequest(Seller sender, String productId, float price, Category category, HashMap<String, String> specialAttributes, String explanation, Image picture) {
        this.productId = productId;
        this.sender = sender;
        this.price = price;
        this.category = category;
        this.specialAttributes = specialAttributes;
        this.explanation=explanation;
        this.dateTime= LocalDateTime.now();
        this.picture=picture;
        Random random = new Random();
        while (getRequestsById(this.id = Integer.toString(random.nextInt())) != null) ;
        allRequests.add(this);
    }

    @Override
    public void acceptRequest() {
        new Product(productId, price, category, specialAttributes,explanation,sender,picture);
        allRequests.remove(this);
    }

    public String getName(){
        return specialAttributes.get("name");
    }

    public Category getCategory() {
        return category;
    }

    public float getPrice() {
        return price;
    }

    public String getBrand(){
        return specialAttributes.get("brand");
    }

    public String getExplanation() {
        return explanation;
    }

    public HashMap<String, String> getSpecialAttributes() {
        return specialAttributes;
    }

    public ImageView getPicture() {
        return new ImageView(picture);
    }

    @Override
    public void declineRequest() {
        allRequests.remove(this);
    }

    @Override
    public String toString() {
        return id + " " + sender + " has requested to add product " + productId;
    }
}
