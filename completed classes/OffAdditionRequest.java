package Model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class OffAdditionRequest extends Request {
    private final String offId;
    private ArrayList<Product> productsArray = new ArrayList<>();
    private final LocalDateTime startDate;
    private final LocalDateTime stopDate;
    private final int offAmount;

    public OffAdditionRequest(Seller sender, String offId, ArrayList<Product> productsArray, LocalDateTime startDate, LocalDateTime stopDate, int offAmount) {
        this.offId = offId;
        this.sender = sender;
        this.productsArray = productsArray;
        this.startDate = startDate;
        this.stopDate = stopDate;
        this.offAmount = offAmount;
        allRequests.add(this);
    }

    @Override
    public void acceptRequest() {
        new Off(offId, productsArray, startDate, stopDate, offAmount);
        allRequests.remove(this);
    }


    @Override
    public void declineRequest() {
        allRequests.remove(this);
    }

    @Override
    public String toString() {
        return id + " " + sender + " has requested to add off " + offId;
    }

    @Override
    public String showRequestDetails() {
       StringBuilder stringBuilder=new StringBuilder();
       stringBuilder.append(toString());
       stringBuilder.append("\noff percent: "+offAmount);
       stringBuilder.append("\nstart date: "+startDate.format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")));
       stringBuilder.append("\nend date: "+stopDate.format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")));
       stringBuilder.append("\nlist of products:");
       for (Product product:productsArray){
           stringBuilder.append('\n'+product.getProductId()+" "+product.getSpecialAttributes().get("name"));
       }
       return stringBuilder.toString();
    }
}
