package Controller;

import Model.Buyer;
import Model.Manager;
import Model.Seller;
import Model.User;
import View.*;

import java.util.Scanner;

public class MainPageController {
    private Scanner scanner = MainPageView.getScanner();
    private LoginRegister loginRegister;
    private String command;
    private ManagerPageView managerPage;
    private SellerPageView sellerPage;
    private BuyerView buyerPage;

    public User enterUserPanel(User user) {
        if (user == null) {
            loginRegister=LoginRegister.getInstance();
            if ((user = loginRegister.loginByForce()) == null) {
                return null;
            }
        }
        if (user instanceof Manager) {
            managerPage=ManagerPageView.getInstance();
            managerPage.enterManagerPageMenu(user);
        } else if (user instanceof Seller) {
            sellerPage=SellerPageView.getInstance();
            sellerPage.enterSellerPage((Seller) user);
        } else if (user instanceof Buyer) {
            buyerPage=BuyerView.getInstance();
            buyerPage.enterBuyerPageMenu(user);
        }
        return user;
    }
}
