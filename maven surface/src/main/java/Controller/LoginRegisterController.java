package Controller;

import Model.Buyer;
import Model.Manager;
import Model.Seller;
import Model.User;
import View.BuyerView;
import View.MainPageView;
import View.ManagerPageView;
import View.SellerPageView;


import java.util.Scanner;

public class LoginRegisterController {
    private String command;
    private Scanner scanner= MainPageView.getScanner();
    private ManagerPageView managerPage;
    private SellerPageView sellerPage;
    private BuyerView buyerPage;

    public  boolean passwordIsValid(String string) {
        if (string.matches("\\w+")){
            if (string.equalsIgnoreCase("back"))return false;
            return true;
        }
        System.out.println("invalid password format");
        return false;
    }

    private boolean usernameIsValid(String input){
        if (!input.matches("\\w+")){
            System.out.println("invalid username format");
            return false;
        }
        if (User.getAccountByUserName(input)!=null){
            System.out.println("user with this username already exists");
            return false;
        }
        if (input.equalsIgnoreCase("back")){
            return false;
        }
        return true;
    }

    public boolean checkNumber(String phoneNumber) {
        if (phoneNumber.matches("(\\+98|0)?9\\d{9}"))return true;
        return false;
    }


    public  boolean checkEmail(String email) {
        if (email.matches("[a-zA-Z0-9_\\-\\.]+@(gmail|@yahoo)\\.com")) {
            return true;
        }
        return false;
    }

    public String enterUsername(String username){
        command=username;
        while (!usernameIsValid(command)){
            if (username.equalsIgnoreCase("back"))return command;
            System.out.print("Please enter a valid username:");
            command=scanner.nextLine().trim();
        }
        return command;
    }

    public String enterPassword(){
        System.out.print("Please enter a password for your account: ");
        while (!passwordIsValid(command=scanner.nextLine().trim())){
            if (command.equalsIgnoreCase("back"))return command;
            System.out.print("please enter a valid username: ");
        }
        return command;
    }

    public String enterEmail(){
        System.out.println("Please enter a email for your account");
        while (!checkEmail(command=scanner.nextLine().trim())) {
            if (command.equalsIgnoreCase("back"))return command;
            System.out.print("Please enter a valid email:");
        }
        return command;
    }

    public String enterPhoneNumber(){
        System.out.println("Please enter a phone number for your account");
        while (!checkNumber(command=scanner.nextLine().trim())){
            if (command.equalsIgnoreCase("back"))return command;
            System.out.print("Please enter a valid phone number:");
        }
        return command;
    }

    public String enterInitialCredit(){
        System.out.println("enter your initial credit");
        try {
            Float.parseFloat(command=scanner.nextLine().trim());
            return command;
        }catch (NumberFormatException e){
            if (command.equalsIgnoreCase("back"))return command;
            System.out.println("invalid number format.");
            return enterInitialCredit();
        }
    }

    public User enterPasswordForLogin(User user){
        System.out.println("please enter your password");
        while (!(command=scanner.nextLine().trim()).equalsIgnoreCase("back")){
            if (command.matches("\\w+")){
                if (user.getPassword().equals(command)){
                    return user;
                }else {
                    System.out.println("incorrect password. please try again.");
                }
            }else {
                System.out.println("invalid password format please try again.");
            }
        }
        return null;
    }

    public void login(User user){
        if (user instanceof Manager){
            managerPage=ManagerPageView.getInstance();
            managerPage.enterManagerPageMenu(user);
        }else if (user instanceof Seller){
            sellerPage=SellerPageView.getInstance();
            sellerPage.enterSellerPage((Seller)user);
        }else if (user instanceof Buyer)
            buyerPage=BuyerView.getInstance();
            buyerPage.enterBuyerPageMenu(user);
    }
}
