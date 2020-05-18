package Model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Random;

public class OffEditRequest extends Request {
    private final Off off;
    private final ArrayList<Product> productsArray = new ArrayList<>();
    private final LocalDateTime startDate;
    private LocalDateTime stopDate;
    private int offAmount;

    public OffEditRequest(Seller sender, Off off) {
        this.off = off;
        this.sender = sender;
        this.productsArray.addAll(off.getProductsArray());
        this.startDate = off.getStartDate();
        this.stopDate = off.getStopDate();
        this.offAmount = off.getOffAmount();
        this.dateTime= LocalDateTime.now();
        Random random = new Random();
        while (getRequestsById(this.id = Integer.toString(random.nextInt())) != null) ;
        allRequests.add(this);
    }

    public void changeAmount(int amount) {
        offAmount = amount;
    }

    public void changeStartDate(LocalDateTime newStartDate) {
        stopDate = newStartDate;
    }

    public void changeEndDate(LocalDateTime newEndDate) {
        stopDate = newEndDate;
    }

    public void addProduct(Product product) {
        productsArray.add(product);
    }


    public void removeProduct(Product product) {
        productsArray.remove(product);
    }

    @Override
    public void acceptRequest() {
        off.setOffAmount(offAmount);
        off.setStartDate(startDate);
        off.setStopDate(stopDate);
        off.setProductsArray(productsArray);
        allRequests.remove(this);
    }

    @Override
    public void declineRequest() {
        allRequests.remove(this);
    }

    @Override
    public String toString() {
        return id + " " + sender + " has requested to change info on off ";
    }

    @Override
    public String showRequestDetails() {
        StringBuilder stringBuilder=new StringBuilder();
        stringBuilder.append("before change:");
        stringBuilder.append("\npercent: "+off.getOffAmount()+"\nstartDay: "+off.getStartDate().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"))+
                "\nend date: "+off.getStopDate().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"))+"\nincluding products:\n");
        for (Product product:off.getProductsArray()){
            stringBuilder.append(product.getSpecialAttributes().get("name")+", ");
        }
        stringBuilder.deleteCharAt(stringBuilder.lastIndexOf(","));
        stringBuilder.append("\n\nafter change: ");
        stringBuilder.append("\npercent: "+offAmount+"\nstartDay: "+startDate.format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"))+
                "\nend date: "+stopDate.format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"))+"\nincluding products: ");
        for (Product product: productsArray){
            stringBuilder.append(product.getSpecialAttributes().get("name")+", ");
        }
        stringBuilder.deleteCharAt(stringBuilder.lastIndexOf(","));
        return stringBuilder.toString();
    }
}
