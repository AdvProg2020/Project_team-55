package View;


import Controller.LoginRegisterController;
import Model.Buyer;
import Model.Manager;
import Model.Seller;
import Model.User;

import java.util.ArrayList;
import java.util.Scanner;

public class LoginRegister extends User {

    private ArrayList<Buyer> buyers = new ArrayList<>();
    private ArrayList<Seller> sellers = new ArrayList<>();
    private ArrayList<Manager> managers = new ArrayList<>();



  private   String  password,username, email, firsName, factory, lastName, number;
  private String manager=null;
    Scanner scanner;
    String input = new String();




    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getFirsName() {
        return firsName;
    }

    public String getFactory() {
        return factory;
    }

    public String getLastName() {
        return lastName;
    }

    public String getNumber() {
        return number;
    }


    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFirsName(String firsName) {
        this.firsName = firsName;
    }

    public void setFactory(String factory) {
        this.factory = factory;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setNumber(String number) {
        this.number = number;
    }




    public void enterLoginMenuForBuyer() {

        System.out.println("Welcome to this part dear");
        input = scanner.nextLine();

        if (input.startsWith("Create account")) {
            String[] words = input.split(" ");


            if (words[2].equals("buyer")) {

                username = words[3];
                while (LoginRegisterController.checkPattern(username) == false) {
                    System.out.println("Please enter a valid username:");
                    username = scanner.nextLine();
                }

                System.out.println("Please enter a password for your account");
                password = scanner.nextLine();
                while (LoginRegisterController.checkPattern(password) == false) {
                    System.out.println("Please enter a valid password:");
                    password = scanner.nextLine();
                }

                System.out.println("Please enter a email for your account");
                email = scanner.nextLine();
                while (LoginRegisterController.checkEmail(email) == false) {
                    System.out.println("Please enter a valid email:");
                    email = scanner.nextLine();
                }

                System.out.println("Please enter a firstname for your account:");
                firsName = scanner.nextLine();

                System.out.println("Please enter a lastname for your account:");
                lastName = scanner.nextLine();


                System.out.println("Please enter a phone number for your account");
                number = scanner.nextLine();
                while (LoginRegisterController.checkNumber(number) == false) {
                    System.out.println("Please enter a valid number");
                    number = scanner.nextLine();
                }

                buyers.add(new Buyer(username, password, email, firsName, lastName, number));

            }//buyer


            else if (words[2].equals("seller")) {


                username = words[3];
                while (LoginRegisterController.checkPattern(username) == false) {
                    System.out.println("Please enter a valid username:");
                    username = scanner.nextLine();
                }

                System.out.println("Please enter a password for your account");
                password = scanner.nextLine();
                while (LoginRegisterController.checkPattern(password) == false) {
                    System.out.println("Please enter a valid password:");
                    password = scanner.nextLine();
                }

                System.out.println("Please enter a email for your account");
                email = scanner.nextLine();
                while (LoginRegisterController.checkEmail(email) == false) {
                    System.out.println("Please enter a valid email:");
                    email = scanner.nextLine();
                }

                System.out.println("Please enter a factory/firm name for your products");
                factory = scanner.nextLine();

                System.out.println("Please enter a firstname for your account:");
                firsName = scanner.nextLine();

                System.out.println("Please enter a lastname for your account:");
                lastName = scanner.nextLine();


                System.out.println("Please enter a phone number for your account");
                number = scanner.nextLine();
                while (LoginRegisterController.checkNumber(number) == false) {
                    System.out.println("Please enter a valid number");
                    number = scanner.nextLine();
                }
                sellers.add(new Seller(username, password, email, factory, firsName, lastName, number));


            }//seller


            else if (words[2].equals("manager")) {



                if (words[2].equals("manager")) {

                    if (manager == null) {
                        manager = scanner.nextLine();
                        System.out.println("You are the main manager");

                        username = words[3];
                        while (LoginRegisterController.checkPattern(username) == false) {
                            System.out.println("Please enter a valid username:");
                            username = scanner.nextLine();
                        }

                        System.out.println("Please enter a password for your account");
                        password = scanner.nextLine();
                        while (LoginRegisterController.checkPattern(password) == false) {
                            System.out.println("Please enter a valid password:");
                            password = scanner.nextLine();
                        }

                        System.out.println("Please enter a email for your account");
                        email = scanner.nextLine();
                        while (LoginRegisterController.checkEmail(email) == false) {
                            System.out.println("Please enter a valid email:");
                            email = scanner.nextLine();
                        }

                        System.out.println("Please enter a firstname for your account:");
                        firsName = scanner.nextLine();

                        System.out.println("Please enter a lastname for your account:");
                        lastName = scanner.nextLine();


                        System.out.println("Please enter a phone number for your account");
                        number = scanner.nextLine();
                        while (LoginRegisterController.checkNumber(number) == false) {
                            System.out.println("Please enter a valid number");
                            number = scanner.nextLine();
                        }
                        managers.add(new Manager(username, password, email, firsName, lastName, number));

                        User.users.add(this);

                    }//manager

                }else{
                    System.out.println("Only a manager can register another manager.");
                }
                }

        }//create account



        if (input.startsWith("login") ) {
            String[] wordss = input.split(" ");
            username = wordss[1];
            System.out.println("Please enter your password");
            password = scanner.nextLine();

           if( password.equals(User.getAccountByUserName(username).getPassword() )     ){
               System.out.println(" Mission accomplished! ");
           }else {
               System.out.println(" Your password is not correct. ");
               System.out.println(" Try this command again. " );
           }

        }

    }




}

