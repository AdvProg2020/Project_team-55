package View;

import Controller.BuyerPageController;
import Model.Buyer;
import Model.OffWithCode;
import Model.Product;
import Model.User;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BuyerView {

    private static BuyerView buyerView;
    private Matcher matcher;
    private BuyerPageController controller =new BuyerPageController();
    private MainPageView mainPage=MainPageView.getInstance();
    private Scanner scanner= MainPageView.getScanner();
    private String input;

    public void enterBuyerPageMenu(User user) {
        while (!(input = scanner.nextLine().trim()).equalsIgnoreCase("back")) {
            if (input.equalsIgnoreCase("view personal info")) {
                editBuyerInfo(user);
            } else if (input.equalsIgnoreCase("view cart")) {
                viewBuyerCart((Buyer) user);
            } else if (input.equalsIgnoreCase("view orders")) {
                viewBuyerOrders((Buyer)user);
            } else if (input.equalsIgnoreCase("view balance")) {
                System.out.println("your credit is " + user.getCredit());
            } else if (input.equalsIgnoreCase("view discount codes")) {
                controller.showUsersDiscounts((Buyer) user);
            } else if (input.equalsIgnoreCase("logout")) {
                mainPage.enterMainPage(null);
            } else if (input.equalsIgnoreCase("help")){
                System.out.println("view personal info\nview cart\nview orders\nview balance\nview discount codes\nlogout\nback\n help");
            }else System.out.println("invalid command");
        }
    }

    public void editBuyerInfo(User user) {
        System.out.println(user.toString());
        while (!(input = scanner.nextLine().trim()).equalsIgnoreCase("back")) {
            if (input.startsWith("edit") && (matcher = getGroup("edit (.+)", input)).find()) {
                controller.editBuyerInfo(user, matcher.group(1));
            } else if (input.equalsIgnoreCase("logout")) {
                mainPage.enterMainPage(null);
            }else if (input.equalsIgnoreCase("help")){
                System.out.println("edit [field]\nlogout\nback\nhelp");
            }
            else System.out.println("invalid command");
        }
    }

    public void viewBuyerCart(Buyer buyer) {
        while (!(input = scanner.nextLine().trim().toLowerCase()).equalsIgnoreCase("back")) {
            if (input.equalsIgnoreCase("show products")) {
                controller.showCartProducts(buyer);
            } else if (input.startsWith("view") && (matcher = getGroup("view (\\d+)", input)).find()) {
                controller.viewUniqueProduct(matcher.group(1), buyer);
            } else if (input.startsWith("increase") && (matcher = getGroup("increase (\\d+)", input)).find()) {
                buyer.increaseProductInCart(Product.getProductById(matcher.group(1)));
            } else if (input.startsWith("decrease") && (matcher = getGroup("decrease (\\d+)", input)).find()) {
                buyer.decreaseProductInCart(Product.getProductById(matcher.group(1)));
            } else if (input.equalsIgnoreCase("show total price")) {
                controller.showTotalCartPrice(buyer);
            } else if (input.equalsIgnoreCase("purchase")) {
                purchase(buyer);
            } else if (input.equalsIgnoreCase("logout")) {
                mainPage.enterMainPage(null);
            } else if (input.equalsIgnoreCase("help")) {
                System.out.println("show products\nview [product id]\nincrease [product id]\ndecrease [product id]" +
                        "\nshow total price\npurchase\nlogout\nback\nhelp");
            } else System.out.println("invalid command");
        }
    }

    public void purchase(Buyer buyer) {
        String address;
        if ((address = controller.receiveAddress()).equalsIgnoreCase("back")) return;
        String phoneNumber;
        if ((phoneNumber = controller.receivePhoneNumber()).equalsIgnoreCase("back")) return;
        String name;
        if ((name = controller.receiveName()).equalsIgnoreCase("back")) return;
        String discountCode;
        if ((discountCode = controller.receiveDiscountCode(buyer)).equalsIgnoreCase("back")) return;
        controller.createReceipt(buyer, address, phoneNumber, name, OffWithCode.getOffByCode(discountCode));
        buyer.getCart().getCartItems().clear();
    }

    public void viewBuyerOrders(Buyer buyer) {
        controller.showBuyHistory(buyer);
        while (!(input = scanner.nextLine().trim().toLowerCase()).equalsIgnoreCase("back")) {
            if (input.startsWith("show order") && (matcher = getGroup("show order (\\d+)", input)).find()) {
                controller.showOrder(buyer,matcher.group(1));
            } else if (input.startsWith("rate") && (matcher = getGroup("rate\\s(\\d+)\\s([1-9])", input)).find()) {
                controller.rateProduct(buyer,matcher.group(1),Integer.parseInt(matcher.group(2)));
            } else if (input.equalsIgnoreCase("logout")) {
                mainPage.enterMainPage(null);
            } else if (input.equalsIgnoreCase("help")) {
                System.out.println("show order [order id]\nrate [product id] [1-5]\nlogout\nback\nhelp");
            } else System.out.println("invalid command");
        }
    }

    private Matcher getGroup(String commandPattern, String commandText) {
        Pattern pattern = Pattern.compile(commandPattern);
        Matcher matcher = pattern.matcher(commandText);
        return matcher;
    }

    public static BuyerView getInstance(){
        if (buyerView==null)return new BuyerView();
        return buyerView;
    }
}
