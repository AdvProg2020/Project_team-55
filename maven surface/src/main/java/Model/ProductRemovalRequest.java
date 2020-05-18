package Model;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.function.Predicate;

public class ProductRemovalRequest extends Request{
    private Product product;

    public ProductRemovalRequest(Seller sender, Product product){
        this.sender=sender;
        this.product=product;
        this.dateTime= LocalDateTime.now();
        Random random = new Random();
        while (getRequestsById(this.id = Integer.toString(random.nextInt())) != null) ;
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
