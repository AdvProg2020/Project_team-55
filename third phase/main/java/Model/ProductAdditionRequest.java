package Model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Random;

public class ProductAdditionRequest extends Request {
    private String productId;
    private float price;
    private Category category;
    private String explanation;
    private String picture;
    private HashMap<String, String> specialAttributes;
    private boolean isFile;
    private String entry;


    public ProductAdditionRequest(Seller sender, String productId, float price, Category category, HashMap<String, String> specialAttributes, String explanation, String picture,boolean isFile) {
        this.productId = productId;
        this.sender = sender;
        this.price = price;
        this.category = category;
        this.specialAttributes = specialAttributes;
        this.explanation=explanation;
        this.dateTime= LocalDateTime.now();
        this.picture=picture;
        this.isFile=isFile;
        Random random = new Random();
        while (getRequestsById(this.id = Integer.toString(random.nextInt())) != null) ;
        allRequests.add(this);
    }

    public ProductAdditionRequest(Seller sender, String productId, float price, Category category, HashMap<String, String> specialAttributes, String explanation, String picture,String entry,boolean isFile) {
        this.productId = productId;
        this.sender = sender;
        this.price = price;
        this.category = category;
        this.specialAttributes = specialAttributes;
        this.explanation=explanation;
        this.dateTime= LocalDateTime.now();
        this.picture=picture;
        this.isFile=isFile;
        this.entry=entry;
        Random random = new Random();
        while (getRequestsById(this.id = Integer.toString(random.nextInt())) != null) ;
        allRequests.add(this);
    }

    @Override
    public void acceptRequest() throws IOException {
        if (!isFile) {
            new Product(productId, price, category, specialAttributes, explanation, sender, picture);
        }else {
            new FileProduct(productId,price,category,specialAttributes,explanation,sender,picture,entry);
        }
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
        ImageView imageView=new ImageView(new Image(picture));
        imageView.resize(100,100);
        imageView.setFitWidth(100);
        imageView.setFitHeight(100);
        return imageView;
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
