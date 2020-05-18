package Controller;

import Model.Buyer;
import Model.Manager;
import Model.Seller;
import Model.User;
import View.*;

import java.util.Scanner;

public class MainPageController {
    private static MainPageController controller;
    private Scanner scanner = MainPageView.getScanner();
    private LoginRegister loginRegister = LoginRegister.getInstance();
    private String command;
    private ManagerPageView managerPage=ManagerPageView.getInstance();
    private SellerPageView sellerPage=SellerPageView.getInstance();
    private BuyerView buyerPage=BuyerView.getInstance();

    public User enterUserPanel(User user) {
        if (user == null) {
            if ((user = loginRegister.loginByForce()) == null) {
                return null;
            }
        }
        if (user instanceof Manager) {
            managerPage.enterManagerPageMenu(user);
        } else if (user instanceof Seller) {
            sellerPage.enterSellerPage((Seller) user);
        } else if (user instanceof Buyer) {
            buyerPage.enterBuyerPageMenu(user);
        }
        return user;
    }

    public static MainPageController getInstance(){
        if (controller==null)return new MainPageController();
        return controller;
    }
}
