package View;
//  bad bia page main besaz va onja callesh kon
// be ja hashmap  file bezar  ya map dg
// back  hm nzdi
//boro be safe  mahsool  khat 147

import Controller.LoginRegisterController;
import Model.Buyer;
//import Model.Product;
import Model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class BuyerView {
    private ArrayList<User> user = new ArrayList<>();
    private ArrayList<Buyer> buyers = new ArrayList<>();

    public HashMap<String, String> buyerStringtHashMap = new HashMap<>();


    String buyeruser;
    private static Scanner scannerForBuyerView = new Scanner(System.in);
    String inputForBuyer = scannerForBuyerView.nextLine();

    public void show() {


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


        if (inputForBuyer.equalsIgnoreCase("exit")) {
            System.exit(1);
        }//exit


        if (inputForBuyer.trim().equals("view personal info") || inputForBuyer.endsWith("info")) {
            System.out.println("Please enter your username .");
            buyeruser = scannerForBuyerView.nextLine();
            buyerStringtHashMap.keySet().add(buyeruser);
            for (User searching : User.users) {
                if (searching.equals(buyeruser)) {
                    System.out.println(searching.getFirstName());
                    System.out.println(searching.getLastName());
                    System.out.println(searching.getEmail());
                    System.out.println(searching.getPassword());
                }//if

                if (inputForBuyer.startsWith("edit")) {
                    String[] tike = inputForBuyer.split(" ");
                    String parts = tike[1];


                    if (parts.equalsIgnoreCase("firstname")) {
                        searching.setFirstName(scannerForBuyerView.nextLine());
                    }

                    if (parts.equalsIgnoreCase("lastname")){
                        searching.setLastName(scannerForBuyerView.nextLine());
                    }


                    if (parts.equalsIgnoreCase("password")) {
                        String pass = scannerForBuyerView.nextLine();
                        while (LoginRegisterController.checkPattern(pass) == false) {
                            System.out.println("Please enter a valid password:");
                        }
                        searching.setPassword(pass);
                    }


                    if (parts.equalsIgnoreCase("Number") || parts.equalsIgnoreCase("telephone number")) {
                        String tell = scannerForBuyerView.nextLine();
                        while (LoginRegisterController.checkNumber(tell) == false) {
                            System.out.println("Please enter a valid telephonee number:");
                        }
                        searching.setPhoneNumber(tell);
                    }


                    if (parts.equals("Email")) {
                        String email = scannerForBuyerView.nextLine();
                        while (LoginRegisterController.checkEmail(email) == false) {
                            System.out.println("Please enter a valid email:");
                        }
                        searching.setEmail(email);
                    }

                }//edit
            }//for
        }//view personal info


        if (inputForBuyer.trim().equals("view cart")) {
            System.out.println("At first please enter your username");
            String user = inputForBuyer;
            if (buyerStringtHashMap.containsKey(user)) {
                for (int i = 0; i < buyerStringtHashMap.size(); i++) {
                    System.out.println(buyerStringtHashMap.values());

                    System.out.println("if you want delete a product please write : delete \n" + "Then write ProductId .");
                    System.out.println("If you want to finalize your purchase please write : confirmation .");
                    if (inputForBuyer.equalsIgnoreCase("delete")) {
                        String id = inputForBuyer;
                        buyerStringtHashMap.values().remove(id);
                        System.out.println("You can remove " + id + "successfully.");
                    }

                    if (inputForBuyer.equalsIgnoreCase("confirmation")) {
                        //taeed  kard khob che konm???

                        System.out.println("You could confirmation successfully.");
                    }
                }
            } else {
                System.out.println("This user in invalid \n" + "If you want, try this command again.");
            }


            if (inputForBuyer.equalsIgnoreCase("show products")) {
                for (int i = 0; i < buyerStringtHashMap.size(); i++) {
                    System.out.println(buyerStringtHashMap.values());
                }
            }


            if (inputForBuyer.equalsIgnoreCase("view [productId]")) {
                String[] mahsoolMoredeNazar = inputForBuyer.split(" ");
                String productIdPart = mahsoolMoredeNazar[1];
                //boro be safe  mahsool
            }


            if (inputForBuyer.startsWith("increase")) {
                String parts[] = inputForBuyer.split(" ");
                String productId = parts[1];
                buyerStringtHashMap.values().add(productId);
            }

            if (inputForBuyer.startsWith("decrease")) {
                String parts[] = inputForBuyer.split(" ");
                String productId = parts[1];
                buyerStringtHashMap.values().remove(productId);
            }

            if (inputForBuyer.startsWith("show total price")) {
//az class product gheimat bgir
            }


            if (inputForBuyer.startsWith("purchase")) {
//boro safe   pardakht tabe call kon
            }

            if (inputForBuyer.startsWith("view orders")) {
                //hashmap avaz she


                if (inputForBuyer.startsWith("show orders")) {
                    String parts[] = inputForBuyer.split(" ");
                    String orderId = parts[2];


                }


                if (inputForBuyer.startsWith("rate")) {
                    String parts[] = inputForBuyer.split(" ");
                    String productId = parts[1];
                    String number = parts[2];
                }


            }


            if (inputForBuyer.startsWith("view balance")) {

            }


            if (inputForBuyer.startsWith("view discount codes")) {
                
            }











        }//view cart


    }//show


}
