package Model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;

enum postStatusSellLog {deliverToTheCustomer, deliverToThePost, preparingTheOrder;}

public class SellLog {

    private float purchasedMoney;
    private int offPercent;
    private static ArrayList<SellLog> allSales = new ArrayList<>();
    private Buyer buyer;
    private Product boughtProduct;
    private int number;
    private String logId;
    private postStatusSellLog postStatus;
    private LocalDateTime date;

    public SellLog(Buyer buyer,Product boughtProduct,int number){
        Random random=new Random();
        while (getSellLogById(this.logId=Integer.toString(random.nextInt(999999)+1))!=null);
        this.buyer=buyer;
        this.boughtProduct=boughtProduct;
        this.number=number;
        if (!boughtProduct.getListOfBuyers().contains(buyer))boughtProduct.getListOfBuyers().add(buyer);
        if (boughtProduct.getAssignedOff()==null){
            this.purchasedMoney=boughtProduct.getPrice()*number;
            this.offPercent=0;
        }else {
            this.purchasedMoney=boughtProduct.getPriceAfterOff()*number;
            this.offPercent=boughtProduct.getAssignedOff().getOffAmount();
        }
        boughtProduct.getSeller().setCredit((int) (boughtProduct.getSeller().getCredit()+purchasedMoney));
        this.date=LocalDateTime.now();
        boughtProduct.getSeller().getSellHistory().add(this);
        this.postStatus=postStatusSellLog.preparingTheOrder;
        allSales.add(this);
    }

    public static ArrayList<SellLog> getAllSales() {
        return allSales;
    }

    public static SellLog getSellLogById(String id){
        for (SellLog log:allSales){
            if (log.logId.equalsIgnoreCase(id)){
                return log;
            }
        }
        return null;
    }

    public String getLogId() {
        return logId;
    }

    public String getDate() {
        return date.format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"));
    }

    public Product getBoughtProduct() {
        return boughtProduct;
    }

    public int getNumber() {
        return number;
    }

    public float getPurchasedMoney() {
        return purchasedMoney;
    }

    public int getOffPercent() {
        return offPercent;
    }

    public Buyer getBuyer() {
        return buyer;
    }

    @Override
    public String toString() {
        return logId+ " product "+boughtProduct.getProductId()+" by quantity "+number+
                "sold at "+date.format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"));
    }
}
