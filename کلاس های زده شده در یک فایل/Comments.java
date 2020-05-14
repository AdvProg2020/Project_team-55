package Model;

import View.BuyerView;

import java.util.ArrayList;
import java.util.HashMap;

public class Comments {
    Buyer buyer;
    private User user;
    private Product products;
    private String messages;
    private boolean didTheBuyerBuyThatProduct;
    HashMap<User, String> allComments = new HashMap();

    public void saveComment(User user, String productId, String comment) {

        if (buyer.showProductIdArray.contains(productId)) {
            didTheBuyerBuyThatProduct = true;
            allComments.put(user, comment);
        } else {
            System.out.println("You did not buy this product :/  .");
        }


    }//saveComment


}

enum comments {WaitingForApproval, accepted, Unconfirmed}