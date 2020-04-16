package Model;

import java.util.ArrayList;
import java.util.Date;

public class SellLog {
    private String logId;
    private Date date;
    float purchaseMoney;
    float off;
    ArrayList<Product> boughtProducts=new ArrayList<>();
    User BuyerName;

}
enum postStatusSellLog{deliverToTheCustomer , deliverToThePost , preparingTheOrder }
