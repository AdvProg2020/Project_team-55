package Controller;

import Model.*;
import View.JustUniqueProduct;
import View.MainPageView;

import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class BuyerPageController {
    private String command;
    private Scanner scanner=MainPageView.getScanner();
    private JustUniqueProduct uniqueProduct = JustUniqueProduct.getInstance();

    public void editBuyerInfo(User buyer, String field) {
        if (field.equalsIgnoreCase("username")) {
            System.out.println("you cannot change your username");
        } else if (field.equalsIgnoreCase("password")) {
            setNewPassword(buyer);
        } else {
            System.out.print("enter new " + field + " value: ");
            if (!(command = scanner.nextLine().trim()).equalsIgnoreCase("back")) {
                buyer.changeInfo(field, command);
            }
        }
    }

    private void setNewPassword(User user) {
        System.out.print("enter your old password:");
        String newValue = scanner.nextLine().trim();
        while (!newValue.equals(user.getPassword())) {
            if (newValue.equals("back")) {
                return;
            }
            System.out.println("wrong password!");
            System.out.print("enter your old password:");
            newValue = scanner.nextLine().trim();
        }
        System.out.print("enter your new password:");
        while (!passwordIsValid(newValue = scanner.nextLine().trim())) {
            if (newValue.equalsIgnoreCase("back")) {
                return;
            }
        }
        user.setPassword(newValue);
        System.out.println("password changed successfully!");
    }

    private boolean passwordIsValid(String password) {
        if (!password.matches("\\w+")) {
            System.out.print("you can only use english letters,numbers and underline in your password." +
                    "\nplease choose another password:");
            return false;
        } else return !password.equalsIgnoreCase("back");
    }

    public void showCartProducts(Buyer buyer) {
        for (Product product : buyer.getCart().getCartItems().keySet()) {
            System.out.println(product.getProductId() + " " + product.getSpecialAttributes().get("name"));
            System.out.println("amount: " + buyer.getCart().getCartItems().get(product));
        }
    }

    public void viewUniqueProduct(String id, Buyer buyer) {
        if (buyer.getCart().getCartItems().containsKey(Product.getProductById(id))) {
            uniqueProduct.enterUniqueProduct(buyer, Product.getProductById(id));
        } else {
            System.out.println("your cart doesn't contain product with this id");
        }
    }

    public void showTotalCartPrice(Buyer buyer) {
        if (buyer.getCart().getCartItems().isEmpty()) {
            System.out.println("your cart is currently empty.");
        } else {
            System.out.println("your cart's total price is: " + buyer.getCart().calculatePrice());
        }
    }

    public String receiveAddress() {
        System.out.print("enter your province:");
        String province = scanner.nextLine().trim();
        if (province.equalsIgnoreCase("back")) return province;
        System.out.print("enter your city:");
        String city = scanner.nextLine().trim();
        if (city.equalsIgnoreCase("back")) return city;
        System.out.print("enter your exact address:");
        String address = scanner.nextLine().trim();
        if (address.equalsIgnoreCase("back")) return address;
        return province + ", " + city + ", " + address;
    }

    public String receivePhoneNumber() {
        System.out.print("enter your phone number:");
        String phone;
        while (!(phone = scanner.next().trim()).matches("(\\+98|0)?9\\d{9}")) {
            if (phone.equalsIgnoreCase("back")) return phone;
            System.out.print("invalid phone number. try again:");
        }
        return phone;
    }

    public String receiveName() {
        System.out.print("enter receiver first name:");
        String fName;
        if ((fName = scanner.nextLine().trim()).equalsIgnoreCase("back")) return fName;
        System.out.print("enter receiver last name:");
        String lName;
        if ((lName = scanner.nextLine().trim()).equalsIgnoreCase("back")) return lName;
        return lName + "," + fName;
    }

    public String receiveDiscountCode(Buyer buyer) {
        OffWithCode.updateDiscounts();
        System.out.println("would you like to use a discount code?[yes/no]");
        if ((command = scanner.nextLine()).equalsIgnoreCase("back")) return command;
        else if (command.equalsIgnoreCase("no")) return command;
        else if (command.equalsIgnoreCase("yes")) {
            System.out.print("enter your discount code:");
            while ((command = scanner.nextLine().trim()).equalsIgnoreCase("back")) {
                if (OffWithCode.getOffByCode(command) != null &&
                        OffWithCode.getOffByCode(command).getApplyingAccounts().containsKey(buyer)) {
                    OffWithCode discount = OffWithCode.getOffByCode(command);
                    if (discount.getApplyingAccounts().get(buyer) == 0) {
                        System.out.print("you cannot use this code anymore. try another code:");
                    } else {
                        return discount.getOffCode();
                    }
                } else {
                    System.out.print("invalid discount code. try again:");
                }
            }
        } else {
            System.out.println("invalid command");
            return receiveDiscountCode(buyer);
        }
        return command;
    }

    public void createReceipt(Buyer buyer, String address, String phone, String name, OffWithCode discount) {
        float totalPrice = buyer.getCart().calculatePrice();
        float omittedPrice = 0;
        if (discount != null) {
            omittedPrice = Math.min(discount.getOffAmount() * totalPrice / 100, discount.getMaxAmount());
        }
        new BuyLog(buyer, buyer.getCart().getCartItems(), totalPrice - omittedPrice, omittedPrice,
                discount.getOffAmount(), address, phone, name);

        for (Product product : buyer.getCart().getCartItems().keySet()) {
            new SellLog(buyer, product, buyer.getCart().getCartItems().get(product));
        }
    }

    public void showBuyHistory(Buyer buyer) {
        for (BuyLog log : buyer.getOrderHistory()) {
            System.out.println(log.getLogId() + "\tdate: " + log.getDate() + "\ttotal price: " + log.getPurchasedMoney());
        }
    }

    public void showOrder(Buyer buyer, String id) {
        if ((BuyLog.getBuyLogById(id) != null) && buyer.getOrderHistory().contains(BuyLog.getBuyLogById(id))){
            System.out.println(BuyLog.getBuyLogById(id).toString());
        }else {
            System.out.println("order with this id isn't in your history.");
        }
    }

    public void rateProduct(Buyer buyer,String id,int rate){
        for (BuyLog log:buyer.getOrderHistory()){
            if (Product.productWithNameExists(id)&&log.getListOfProducts().containsKey(Product.getProductById(id))){
                if (rate<=5){
                    Score score=Score.getInstance();
                    score.rateProduct(Product.getProductById(id),rate);
                    System.out.println("product rated successfully");
                    return;
                }else {
                    System.out.println("product rate should be 1-5.");
                    return;
                }
            }
        }
        System.out.println("product with this id doesn't exist");;
    }

    public void showUsersDiscounts(Buyer buyer) {
        OffWithCode.updateDiscounts();
        for (OffWithCode discount : OffWithCode.getAllDiscounts()) {
            if (discount.getApplyingAccounts().containsKey(buyer)) {
                if (discount.isActive())
                    System.out.print(discount.getOffAmount() + "% discount with code " + discount.getOffCode() + " till " +
                            discount.getStopDate().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")));
            }
        }
    }
}
