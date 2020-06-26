package Model;


import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Random;

public class ProductEditRequest extends Request {
    private Product product;
    private HashMap<String, String> requestedChange = new HashMap<String, String>();
    private float price;
    private Category category;
    private boolean existence;
    private String explanation;
    private Image picture;

    public ProductEditRequest(Seller sender, Product product, float price, Category category, HashMap<String, String> specialAttributes, String explanation, Image picture) {
        this.product=product;
        this.sender = sender;
        this.price = price;
        this.category = category;
        this.requestedChange = specialAttributes;
        this.explanation=explanation;
        this.dateTime= LocalDateTime.now();
        this.picture=picture;
        Random random = new Random();
        while (getRequestsById(this.id = Integer.toString(random.nextInt())) != null) ;
        allRequests.add(this);
    }



    @Override
    public void acceptRequest() {
        product.setCategory(category);
        product.setExistence(existence);
        product.setPrice(price);
        product.setExplanation(explanation);
        product.getSpecialAttributes().putAll(requestedChange);
        product.setPicture(picture);
        allRequests.remove(this);

    }


    @Override
    public void declineRequest() {
        allRequests.remove(this);
    }

    public Product getProduct() {
        return product;
    }

    public ImageView getPicture() {
        return new ImageView(picture);
    }

    public HashMap<String, String> getRequestedChange() {
        return requestedChange;
    }

    public float getPrice() {
        return price;
    }

    public Category getCategory() {
        return category;
    }

    public boolean isExistence() {
        return existence;
    }

    public String getExplanation() {
        return explanation;
    }

    @Override
    public String toString() {
        return id + " " + sender + " has requested to change info on product " + product.getProductId();
    }
}
