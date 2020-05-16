package Model;

import java.util.function.Predicate;

public class ProductRemovalRequest extends Request{
    private Product product;

    public ProductRemovalRequest(Seller sender, Product product){
        this.sender=sender;
        this.product=product;
        allRequests.add(this);
    }

    @Override
    public void acceptRequest() {
        Product.removeProduct(product);
        allRequests.remove(this);
    }

    @Override
    public String showRequestDetails() {
        return "seller "+sender.getUserName()+" has requested to remove product "+product.getProductId();
    }

    @Override
    public void declineRequest() {
        allRequests.remove(this);
    }
}
