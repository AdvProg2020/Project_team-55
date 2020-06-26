package Model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

enum postStatusSellLog {deliverToTheCustomer, deliverToThePost, preparingTheOrder;}

public class BuyLog {


    private float purchasedMoney;
    private float omittedPrice;
    private int discountPercent;
    private postStatusBuyLog postStatus;
    private static ArrayList<BuyLog> allSales = new ArrayList<>();
    private Buyer Buyer;
    private ArrayList<CartItem> listOfProducts = new ArrayList<>();
    private String address, phone, receiver;
    private String logId;
    private LocalDateTime date;

    public BuyLog(Buyer buyer, ArrayList<CartItem> cartItems, float purchasedMoney, float omittedPrice, int discountPercent,
                  String address, String phone, String receiver) {
        Random random = new Random();
        while (getBuyLogById(this.logId = Integer.toString(random.nextInt(999999) + 1)) != null) ;
        this.Buyer = buyer;
        this.address = address;
        this.phone = phone;
        this.receiver = receiver;
        this.purchasedMoney = purchasedMoney;
        this.omittedPrice = omittedPrice;
        this.discountPercent = discountPercent;
        this.listOfProducts.addAll(cartItems);
        this.date = LocalDateTime.now();
        buyer.setCredit(buyer.getCredit() - purchasedMoney);
        this.postStatus=postStatusBuyLog.preparingTheOrder;
        allSales.add(this);
        buyer.getOrderHistory().add(this);
    }

    public static BuyLog getBuyLogById(String id) {
        for (BuyLog log : allSales) {
            if (log.logId.equalsIgnoreCase(id)) {
                return log;
            }
        }
        return null;
    }

    public ArrayList<CartItem> getListOfProducts() {
        return listOfProducts;
    }

    public String getLogId() {
        return logId;
    }

    public String getDate() {
        return date.format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"));
    }

    public float getPurchasedMoney() {
        return purchasedMoney;
    }

    public float getOmittedPrice() {
        return omittedPrice;
    }

    public static ArrayList<BuyLog> getAllSales() {
        return allSales;
    }

}
