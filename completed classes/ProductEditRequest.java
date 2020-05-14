package Model;


import java.util.HashMap;

public class ProductEditRequest extends Request {
    private final Product product;
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
    }

    public void changePrice(float price) {
        this.price = price;
    }

    public void changeCategory(Category category) {
        this.category = category;
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
