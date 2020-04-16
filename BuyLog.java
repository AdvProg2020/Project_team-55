package Model;

import java.util.ArrayList;
import java.util.Date;

public class BuyLog {
    private String logId;
    private Date date;
float purchaseMoney;
float off;
ArrayList<Product> boughtProducts=new ArrayList<>();
User sellerName;

}
enum postStatusBuyLog{deliverToTheCustomer , deliverToThePost , preparingTheOrder }
