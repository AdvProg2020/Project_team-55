package Controller;

import Model.*;
import View.LoginRegister;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class JustUniqueProductsController {
    User user;
    Product product;
    Seller seller;
    String id;
    Buyer buyer;
    Comments comments;
    String title;
    String text;
double score;
Score scoreClass;
Category category;
    LoginRegister loginRegister;


    Scanner scannerForJustUniqueProduct;
    String inputForJustUniqueProduct;



    public void showInformationDigestFunction(Product product, User user) {
        System.out.println("product price = "+product.getPrice());
        System.out.println("product name = "+product.getSpecialAttributes().get("name"));
        System.out.println("product brand = "+product.getSpecialAttributes().get("brand"));
    }//showInformationDigestFunction

    ////////////////////////////////////////////////////////////////////////////////////////
    
    public void addToCartFunction(Product product) {
buyer.setShowProductIdArray(product);
    }


    public void showAllInformationAttributesFunction(Product product) {
        System.out.println("Name: "+category.getName());
        System.out.println("Special Attributes : "+category.getSpecialAttributes());
    }


    ////////////////////////////////////////////////////////////////////////////////////////
    public void addCommentsFunction(Product product, User user) {

comments.forComments.keySet().add(product);
comments.forComments.values().add(user);

        System.out.println("Enter your title .");
title=scannerForJustUniqueProduct.nextLine();

        System.out.println("Enter you comment .");
        text=scannerForJustUniqueProduct.nextLine();

        comments.saveComment(user,product,text);

        comments.allComments.keySet().add(user);
        comments.allComments.values().add(text);

        comments.ProductAndComments.keySet().add(product);
        comments.ProductAndComments.values().add(comments);

    }//addCommentsFunction
    ////////////////////////////////////////////////////////////////////////////////////////
    public void showCommentsFunction(Product product) {
        for (Product product1 : comments.ProductAndComments.keySet()) {
           if (product1.equals(product) ){
               System.out.println(comments.ProductAndComments.values());
           }


        }

    }//showCommentsFunction

    ////////////////////////////////////////////////////////////////////////////////////////
    public void addScoreFunction(Product product) {
        System.out.println("Enter score .");
        score=scannerForJustUniqueProduct.nextDouble();
        scoreClass.saveScore(score);

    }//addScoreFunction




    //function
    public void runFunctionForJustUniqueProduct() {


        while (!(inputForJustUniqueProduct = scannerForJustUniqueProduct.nextLine()).equalsIgnoreCase("exit")) {

            if (inputForJustUniqueProduct.trim().equalsIgnoreCase("back")) {
                return;
            }
////////////////////////////////////////////////////////////////////////////////////////

            else if (inputForJustUniqueProduct.trim().equalsIgnoreCase("help")) {
                System.out.println(" In this page you can use these orders: \n");
                System.out.println("________________________________________________________________________________________");
                System.err.println("digest ");
                System.out.println("add to cart ");
                System.out.println("select seller [seller_username] ");
                System.out.println("________________________________________________________________________________________");
                System.err.println("attributes ");
                System.out.println("________________________________________________________________________________________");
                System.err.println("compare [productID] ");
                System.out.println("________________________________________________________________________________________");
                System.err.println("comments ");
                System.out.println("Add comment");
                System.out.println("Title");
                System.out.println("content");
                System.out.println("________________________________________________________________________________________");
            }//help
////////////////////////////////////////////////////////////////////////////////////////////////////////

            else if (inputForJustUniqueProduct.trim().equalsIgnoreCase("digest")) {
                showInformationDigestFunction(product, user);

                if (scannerForJustUniqueProduct.nextLine().trim().equalsIgnoreCase("add to cart")) {
                    if(loginRegister.getUsername()==null){
                        loginRegister.enterLoginMenuForBuyer();
                    }
                    addToCartFunction(product);
                }//add to cart
                

            }//digest
///////////////////////////////////////////////////////////////////////////////////////////////////////////


            else if (inputForJustUniqueProduct.trim().equalsIgnoreCase("attributes")) {
                showAllInformationAttributesFunction(product);
            }//attributes

///////////////////////////////////////////////////////////////////////////////////////////////////////////
            else if (inputForJustUniqueProduct.trim().equalsIgnoreCase("Add comments")) {
                addCommentsFunction(product, user);
            }//comments

/////////////////////////////////////////////////////////////////////////////////////////////////////////////

            else if (inputForJustUniqueProduct.trim().equalsIgnoreCase("show comments")) {
                showCommentsFunction(product);
            }//comments

/////////////////////////////////////////////////////////////////////////////////////////////////////////////

            else if (inputForJustUniqueProduct.trim().equalsIgnoreCase("score")) {
                addScoreFunction(product);
            }






        }//while
        System.err.println("You exit :/  .");

    }


}




















