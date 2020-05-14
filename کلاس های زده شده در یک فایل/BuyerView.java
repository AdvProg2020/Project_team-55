package View;

//boro be safe  mahsool   va   gheimat  bgir  va purchace hesab kon

import Controller.LoginRegisterController;
import Model.Buyer;
import Model.Product;
import Model.User;

import java.util.ArrayList;
import java.util.Scanner;

public class BuyerView {
    private ArrayList<User> user = new ArrayList<>();

User  buyerUser;
Buyer buyer;

    private static Scanner scannerForBuyerView = new Scanner(System.in);
    String inputForBuyer = scannerForBuyerView.nextLine();

    public void show() {

        while (  !(scannerForBuyerView.nextLine().equalsIgnoreCase("exit")) ) {

            if (inputForBuyer.trim().equalsIgnoreCase("help")) {
                System.out.println(" In this page you can use these orders: \n");
                System.err.println("view personal info");  //ghermez
                System.out.println("edit [field]");
                System.out.println("________________________________________________________________________________________");
                System.err.println("view cart");
                System.out.println("show products\n" + "view [productId]\n" + " increase [productId]\n" + "decrease [productId] \n" + "⇒ show total price\n" + "purchase ");
                System.out.println("________________________________________________________________________________________");
                System.err.println("view orders ");
                System.out.println("show order [orderId] \n" + "rate [productId] [1-5] ");
                System.out.println("________________________________________________________________________________________");
                System.err.println("view balance");
                System.out.println("________________________________________________________________________________________");
                System.err.println("view discount codes");

            }//help
/////////////////////////////////////////////////////////////////////////////////////////////
            else if (inputForBuyer.equalsIgnoreCase("back")) {
                return;
            }
///////////////////////////////////////////////////////////////////////////////////////////////
            else if (inputForBuyer.trim().equalsIgnoreCase("view personal info")) {
                System.out.println("Please enter your username .");
                buyerUser = User.getAccountByUserName(scannerForBuyerView.nextLine());
                for (User searching : User.users) {
                    if (searching.equals(buyerUser)) {
                        System.out.println(searching.getFirstName());
                        System.out.println(searching.getLastName());
                        System.out.println(searching.getEmail());
                        System.out.println(searching.getPassword());
                    }//if
                    if (scannerForBuyerView.nextLine().startsWith("edit")) {
                        String[] tike = inputForBuyer.split(" ");
                        String parts = tike[1];


                        if (parts.trim().equalsIgnoreCase("first name")) {
                            searching.setFirstName(scannerForBuyerView.nextLine());
                        } else if (parts.trim().equalsIgnoreCase("last name")) {
                            searching.setLastName(scannerForBuyerView.nextLine());
                        } else if (parts.equalsIgnoreCase("password")) {
                            String pass = scannerForBuyerView.nextLine();
                            while (LoginRegisterController.checkPattern(pass) == false) {
                                System.out.println("Please enter a valid password:");
                            }
                            searching.setPassword(pass);
                        } else if (parts.trim().equalsIgnoreCase("Number") || parts.equalsIgnoreCase("telephone number")) {
                            String tell = scannerForBuyerView.nextLine();
                            while (LoginRegisterController.checkNumber(tell) == false) {
                                System.out.println("Please enter a valid telephone number:");
                            }
                            searching.setPhoneNumber(tell);
                        } else if (parts.trim().equalsIgnoreCase("Email")) {
                            String email = scannerForBuyerView.nextLine();
                            while (LoginRegisterController.checkEmail(email) == false) {
                                System.out.println("Please enter a valid email:");
                            }
                            searching.setEmail(email);
                        }
                    }//edit
                }//for
            }//view personal info

////////////////////////////////////////////////////////////////////////////////////////////////////
            else if (inputForBuyer.endsWith("cart")) {//view cart
                for (User searching : User.users) {
                    for(Product saerching : buyer.getShowProductIdArray()){
                    System.out.println(buyer.showProductIdArray.toString());}
                }

                System.out.println("if you want delete a product please write : delete \n" + "Then write ProductId .");
                System.out.println("If you want to finalize your purchase please write : confirmation .");
                if (scannerForBuyerView.nextLine().trim().equalsIgnoreCase("delete")) {
                    String id = inputForBuyer;
                   buyer.showProductIdArray.remove(id);
                    System.out.println("You can remove " + id + "successfully.");
                } else if (scannerForBuyerView.nextLine().equalsIgnoreCase("confirmation")) {
                    //taeed  kard boro be  safe pardakht
                    //
                    System.out.println("You could confirmation successfully.");
                } else if (inputForBuyer.trim().equalsIgnoreCase("show products")) {
                    for (Product searching : buyer.getShowProductIdArray()) {
                        System.out.println(buyer.showProductIdArray.toString());
                    }
                } else if (inputForBuyer.startsWith("view")) {//view productId
                    String[] mahsoolMoredeNazar = inputForBuyer.split(" ");
                    String productIdPart = mahsoolMoredeNazar[2];
                    //boro be safe  mahsool

                } else if (inputForBuyer.startsWith("increase")) {
                    String parts[] = inputForBuyer.split(" ");
                    Product productId = Product.getProductByName(parts[1]);
                    //
                  buyer.showProductIdArray.add(productId);
                } else if (inputForBuyer.startsWith("decrease")) {
                    String parts[] = inputForBuyer.split(" ");
                    String productId = parts[1];
                    buyer.showProductIdArray.remove(productId);
                } else if (inputForBuyer.trim().equalsIgnoreCase("show total price")) {
//az class product gheimat bgir
                } else if (inputForBuyer.startsWith("purchase")) {
//boro safe   pardakht tabe call kon
                }


            }//view  cart


/////////////////////////////////////////////////////////////////////////////////////////////////////
            if (inputForBuyer.endsWith("orders")) {//view orders
                for (Product searching : buyer.getShowProductIdArray()) {
                    System.out.println(buyer.showProductIdArray.toString());
                }


                if (inputForBuyer.trim().equalsIgnoreCase("show orders")) {
                    String parts[] = inputForBuyer.split(" ");
                    String orderId = parts[2];


                } else if (inputForBuyer.startsWith("rate")) {
                    String parts[] = inputForBuyer.split(" ");
                    String productId = parts[1];
                    String number = parts[2];
                }
            }//view order
 /////////////////////////////////////////////////////////////////////////////////////////////
                else if (inputForBuyer.endsWith("balance")) {//view balance

                }

  /////////////////////////////////////////////////////////////////////////////////////////////
                else if (inputForBuyer.endsWith("codes")) {

                }


        }//while
        System.err.println("You exit :/  .");

    }//show
}//public class  view

