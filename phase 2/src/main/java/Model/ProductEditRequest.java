package Model;


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


    public ProductEditRequest(Product product, Seller sender) {
        this.product = product;
        this.sender = sender;
        this.price = product.getPrice();
        this.price = product.getPrice();
        this.category = product.getCategory();
        this.existence = product.getExistence();
        this.explanation = product.getExplanation();
        this.requestedChange = product.getSpecialAttributes();
        this.dateTime = LocalDateTime.now();
        Random random = new Random();
        while (getRequestsById(this.id = Integer.toString(random.nextInt())) != null) ;
        allRequests.add(this);
    }

    public void changePrice(float price) {
        this.price = price;
    }

    public void changeCategory(Category category, HashMap<String, String> newAttributes) {
        this.category = category;
        this.requestedChange.clear();
        this.requestedChange.putAll(newAttributes);
    }

    public void changeExistence(boolean existence) {
        this.existence = existence;
    }

    public void changeRequestedChanges(String field, String value) {
        requestedChange.put(field, value);
    }

    public void changeExplanation(String explanation) {
        this.explanation = explanation;
    }

    @Override
    public void acceptRequest() {
        product.setCategory(category);
        product.setExistence(existence);
        product.setPrice(price);
        product.setExplanation(explanation);
        product.getSpecialAttributes().putAll(requestedChange);
        allRequests.remove(this);

    }

    @Override
    public String showRequestDetails() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("before change:\n");
        stringBuilder.append("category: "+product.getCategory().getName() + "\nprice: " + product.getPrice());
        stringBuilder.append("\nstate: ");
        if (product.getExistence())stringBuilder.append("available");
        else stringBuilder.append("unavailable");
        stringBuilder.append('\n'+product.getExplanation());
        for (String attribute:product.getSpecialAttributes().keySet()){
            stringBuilder.append('\n'+attribute+": "+product.getSpecialAttributes().get(attribute));
        }
        stringBuilder.append("after change:\n");
        stringBuilder.append("category: "+category.getName() + "\nprice: " + price);
        stringBuilder.append("\nstate: ");
        if (existence)stringBuilder.append("available");
        else stringBuilder.append("unavailable");
        stringBuilder.append('\n'+explanation);
        for (String attribute:requestedChange.keySet()){
            stringBuilder.append('\n'+attribute+": "+requestedChange.get(attribute));
        }
        return stringBuilder.toString();
    }

    @Override
    public void declineRequest() {
        allRequests.remove(this);
    }

    @Override
    public String toString() {
        return id + " " + sender + " has requested to change info on product " + product.getProductId();
    }
}
