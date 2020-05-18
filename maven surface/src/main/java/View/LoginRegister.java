package View;

import Controller.LoginRegisterController;
import Model.Buyer;
import Model.Manager;
import Model.Seller;
import Model.User;


import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginRegister {
    private static LoginRegister loginRegister;
    private String input, password, username, email, firsName, factory, lastName, number, credit;
    private LoginRegisterController controller =new LoginRegisterController();
    private Scanner scanner= MainPageView.getScanner();
    private Matcher matcher;

    public void enterLoginRegisterMenu() {
        while (!(input = scanner.nextLine().trim()).equalsIgnoreCase("back")) {
            if (input.startsWith("create account") && (matcher = getGroup("create account (\\S+)\\s(\\S+)", input)).find()) {
                String type=matcher.group(1),name=matcher.group(2);
                if (type.equals("buyer"))
                    registerBuyer(name);
                else if (type.equals("seller")) {
                    registerSeller(name);
                } else if (type.equals("manager")) {
                    registerManager(name);
                }else System.out.println("invalid command");
            }else if (input.startsWith("login")&&(matcher=getGroup("login (\\w+)",input)).find()){
                loginUser(matcher.group(1));
            }else if (input.equalsIgnoreCase("help")){
                System.out.println("create account [buyer/seller/manager] [username]" +
                        "\nlogin [username]\nback\nhelp");
            }
        }
    }

    private User loginUser(String username){
        while (!username.equalsIgnoreCase("back")) {
            if (User.getAccountByUserName(username) == null) {
                System.out.println("user with this username doesn't exist");
                username=scanner.nextLine();
            } else {
                return controller.enterPasswordForLogin(User.getAccountByUserName(username));
            }
        }
        return null;
    }

    public User loginByForce(){
        System.out.println("please enter your username:");
        if ((input=scanner.nextLine().trim()).equalsIgnoreCase("back")) return null;
        return loginUser(input);
    }

    private void registerBuyer(String command) {

        if ((username = controller.enterUsername(command)).equalsIgnoreCase("back")) return;

        if ((password = controller.enterPassword()).equalsIgnoreCase("back")) return;

        if ((email = controller.enterEmail()).equalsIgnoreCase("back")) return;

        System.out.println("Please enter a first name for your account:");
        if ((firsName = scanner.nextLine()).equalsIgnoreCase("back")) return;

        System.out.println("Please enter a last name for your account:");
        if ((lastName = scanner.nextLine()).equalsIgnoreCase("back")) return;

        if ((number = controller.enterPhoneNumber()).equalsIgnoreCase("back")) return;

        if ((credit = controller.enterInitialCredit()).equalsIgnoreCase("back")) return;
        System.out.println("you registered successfully");
        controller.login(new Buyer(username, password, email, firsName, lastName, number, Float.parseFloat(credit)));

    }//buyer

    private void registerSeller(String command) {
        if ((username = controller.enterUsername(command)).equalsIgnoreCase("back")) return;

        if ((password = controller.enterPassword()).equalsIgnoreCase("back")) return;

        if ((email = controller.enterEmail()).equalsIgnoreCase("back")) return;

        System.out.println("Please enter a first name for your account:");
        if ((firsName = scanner.nextLine()).equalsIgnoreCase("back")) return;

        System.out.println("Please enter a last name for your account:");
        if ((lastName = scanner.nextLine()).equalsIgnoreCase("back")) return;

        System.out.println("Please enter your workplace name:");
        if ((factory = scanner.nextLine().trim()).equalsIgnoreCase("back")) return;

        if ((number = controller.enterPhoneNumber()).equalsIgnoreCase("back")) return;

        if ((credit = controller.enterInitialCredit()).equalsIgnoreCase("back")) return;
        System.out.println("you registered successfully");
        controller.login(new Seller(factory, username, password, email, firsName, lastName, number, Float.parseFloat(credit)));
    }

    private void registerManager(String command) {
        if (Manager.getMainManager() == null) {
            if ((username = controller.enterUsername(command)).equalsIgnoreCase("back")) return;

            if ((password = controller.enterPassword()).equalsIgnoreCase("back")) return;

            if ((email = controller.enterEmail()).equalsIgnoreCase("back")) return;

            System.out.println("Please enter a first name for your account:");
            if ((firsName = scanner.nextLine()).equalsIgnoreCase("back")) return;

            System.out.println("Please enter a last name for your account:");
            if ((lastName = scanner.nextLine()).equalsIgnoreCase("back")) return;

            if ((number = controller.enterPhoneNumber()).equalsIgnoreCase("back")) return;
            System.out.println("you registered successfully");
            controller.login(new Manager(username, password, email, firsName, lastName, number));
        } else {
            System.out.println("Only a manager can register another manager.");
        }
    }

    private Matcher getGroup(String commandPattern, String commandText) {
        Pattern pattern = Pattern.compile(commandPattern);
        Matcher matcher = pattern.matcher(commandText);
        return matcher;
    }

    public static LoginRegister getInstance(){
        if (loginRegister==null)return new LoginRegister();
        return loginRegister;
    }
}