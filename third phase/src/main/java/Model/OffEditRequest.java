package Model;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Random;

public class OffEditRequest extends Request {
    private  Off off;
    private  ArrayList<Product> productsArray;
    private  LocalDateTime startDate;
    private LocalDateTime stopDate;
    private int offAmount;

    public OffEditRequest(Off off,Seller sender, ArrayList<Product> productsArray, LocalDateTime startDate, LocalDateTime stopDate, int offAmount) {
        this.off = off;
        this.sender = sender;
        this.productsArray=productsArray;
        this.startDate = startDate;
        this.stopDate = stopDate;
        this.offAmount = offAmount;
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

    public Off getOff() {
        return off;
    }

    public int getOffAmount() {
        return offAmount;
    }

    public String getStartDate() {
        return startDate.format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
    }

    public String getStopDate() {
        return stopDate.format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
    }

    public ArrayList<Product> getProductsArray() {
        return productsArray;
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

}
