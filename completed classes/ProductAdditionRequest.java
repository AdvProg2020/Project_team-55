package Model;

import java.util.HashMap;
import java.util.Random;

public class ProductAdditionRequest extends Request {
    String productId;
    float price;
    Category category;
    String explanation;
    HashMap<String, String> specialAttributes;
    Random random = new Random();

    public ProductAdditionRequest(Seller sender, String productId, float price, Category category, HashMap<String, String> specialAttributes,String explanation) {
        this.productId = productId;
        this.sender = sender;
        this.price = price;
        this.category = category;
        this.specialAttributes = specialAttributes;
        this.explanation=explanation;
        while (getRequestsById(id = Integer.toString(random.nextInt())) != null) ;
    }

    @Override
    public void acceptRequest() {
        new Product(productId, price, category, specialAttributes,explanation);
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
