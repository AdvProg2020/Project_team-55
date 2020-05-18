package View;


import Controller.MainPageController;
import Model.User;

import java.util.Scanner;

public class MainPageView {
    private static MainPageView mainPage;
    private LoginRegister loginRegister;
    private ProductPageView productPage;
    private OffView offPage;
    private MainPageController controller=new MainPageController();
    public static Scanner mainScanner=getScanner();
    private String command;

    public void enterMainPage(User user){
        System.out.println("welcome");
        while (!(command=mainScanner.nextLine().trim()).equalsIgnoreCase("exit")){
            if (command.equalsIgnoreCase("login/register page")){
                loginRegister=LoginRegister.getInstance();
                if (user==null)loginRegister.enterLoginRegisterMenu();
                else System.out.println("you are already logged in");
            }else if (command.equalsIgnoreCase("user panel")){
                user=controller.enterUserPanel(user);
            }else if (command.equalsIgnoreCase("products")){
                productPage=ProductPageView.getInstance();
                productPage.enterProductPage(user);
            }else if (command.equalsIgnoreCase("offs")){
                offPage=OffView.getInstance();
                offPage.offMenu(user);
            }else if (command.equalsIgnoreCase("logout")){
                if (user==null) System.out.println("you are already logged out");
                user=null;
            }else if (command.equalsIgnoreCase("help")){
                System.out.println("login/register page\nuser panel\nproducts\noffs\nlogout\nexit\nhelp");
            }else System.out.println("invalid command");
        }
        System.exit(0);

    }

    public static MainPageView getInstance(){
        if (mainPage==null)return new MainPageView();
        return mainPage;
    }

    public static Scanner getScanner(){
        if (mainScanner==null)return new Scanner(System.in);
        return mainScanner;
    }

}
