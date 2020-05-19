package Model;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Random;

public class ProductAdditionRequest extends Request {
    String productId;
    float price;
    Category category;
    String explanation;
    HashMap<String, String> specialAttributes;


    public ProductAdditionRequest(Seller sender, String productId, float price, Category category, HashMap<String, String> specialAttributes,String explanation) {
        this.productId = productId;
        this.sender = sender;
        this.price = price;
        this.category = category;
        this.specialAttributes = specialAttributes;
        this.explanation=explanation;
        this.dateTime= LocalDateTime.now();
        Random random = new Random();
        while (getRequestsById(this.id = Integer.toString(random.nextInt())) != null) ;
        allRequests.add(this);
    }

    @Override
    public void acceptRequest() {
        new Product(productId, price, category, specialAttributes,explanation,sender);
        allRequests.remove(this);
    }

    @Override
    public String showRequestDetails() {
        StringBuilder stringBuilder=new StringBuilder();
        stringBuilder.append(id + " " + sender + " has requested to add product " + productId);
        stringBuilder.append("\nproduct price: "+price+"\ncategory: "+category.getName());
        for (String attribute:specialAttributes.keySet()){
            stringBuilder.append('\n'+attribute+": "+specialAttributes.get(attribute));
        }
        stringBuilder.append("\n extra description: "+explanation);
          return stringBuilder.toString();
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
