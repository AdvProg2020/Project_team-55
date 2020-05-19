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
    private HashMap<Product, Integer> listOfProducts = new HashMap<>();
    private String address, phone, receiver;
    private String logId;
    private LocalDateTime date;

    public BuyLog(Buyer buyer, HashMap<Product, Integer> products, float purchasedMoney, float omittedPrice, int discountPercent,
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
        this.listOfProducts.putAll(products);
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

    public HashMap<Product, Integer> getListOfProducts() {
        return listOfProducts;
    }

    public String getLogId() {
        return logId;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public float getPurchasedMoney() {
        return purchasedMoney;
    }

    public static ArrayList<BuyLog> getAllSales() {
        return allSales;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder=new StringBuilder();
        stringBuilder.append("order id: "+logId+
                "\torder date: "+date.format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")));
        stringBuilder.append("\nreceiver name: "+receiver+
                "\naddress: "+address+"\nphone number: "+phone);
        stringBuilder.append("\n+-----------------+------------+----------------+");
        stringBuilder.append("\n|product          |quantity    |price per each  |");
        stringBuilder.append("\n+-----------------+------------+----------------+");
        for (Product product:listOfProducts.keySet()){
            stringBuilder.append(String.format("\n| %-15s | %-9d  | %-14f |%n",
                    product.getSpecialAttributes().get("name"), listOfProducts.get(product), product.getPrice()));
        }
        stringBuilder.append("\ntotal price with offs: "+purchasedMoney+omittedPrice);
        stringBuilder.append("\ndecreased price with "+discountPercent+"% discount: "+omittedPrice);
        stringBuilder.append("\npurchase price: "+purchasedMoney);
        stringBuilder.append("\n+-----------------+------------+----------------+");
        return stringBuilder.toString();
    }
}
