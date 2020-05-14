package Model;

import java.util.ArrayList;
import java.util.Date;



public class BuyLog {
    private String logId;
    private Date date=new Date();
float purchaseMoney;
Buyer buyer;
int counter=0;
float off;
ArrayList<Product> boughtProducts=new ArrayList<>();
User user;
public void printBuyLog(){
    System.out.println("You can read some information about buy log class. \n");

      System.out.println("Date: "+date.toString());
      counter++;
    System.out.println("Log ID: "+ counter);
    System.out.println("Amount paid: " +purchaseMoney  );
    System.out.println(" The amount of discount applied: "+ off   );
    System.out.println("  List of purchased products: "+ buyer.showProductIdArray.toString()  );
    System.out.println("Buyer's name: "+ buyer.getFirstName() +" "+ buyer.lastName );
    System.out.println("Delivery status: ");/////////////////////////////////////////////////////////////


}



}
enum postStatusBuyLog{deliverToTheCustomer , deliverToThePost , preparingTheOrder }
