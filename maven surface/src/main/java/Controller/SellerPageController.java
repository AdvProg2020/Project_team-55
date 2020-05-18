package Controller;

import Model.*;
import View.JustUniqueProduct;
import View.MainPageView;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class SellerPageController {

    private static SellerPageController controller;
    private Scanner scanner= MainPageView.getScanner();
    private String command;
    private JustUniqueProduct uniqueProduct=JustUniqueProduct.getInstance();

    public boolean sellerHasProductWithId(Seller seller, String id) {
        return seller.getArrayProduct().contains(Product.getProductById(id));
    }

    public void showCategories() {
        for (Category category : Category.getAllCategories()) {
            if (category.getParentCategory() == null) {
                System.out.println(category.getName() + ":");
                for (Category subs : category.getSubCategories()) {
                    System.out.println('\t' + subs.getName());
                }
            }
        }
    }

    public void showPersonalInfo(User user) {
        user.toString();
    }

    public void editSellerInfo(User seller, String field) {
        if (field.equalsIgnoreCase("username")) {
            System.out.println("you cannot change your username");
        } else if (field.equalsIgnoreCase("password")) {
            setNewPassword(seller);
        } else {
            System.out.print("enter new " + field + " value: ");
            if (!(command = scanner.nextLine().trim()).equalsIgnoreCase("back")) {
                seller.changeInfo(field, command);
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

    public void showSellerProducts(Seller seller){
        for (Product product:seller.getArrayProduct()){
            System.out.println(product.getProductId()+" "+product.getSpecialAttributes().get("name")+"\nprice: "+product.getPrice());
        }
    }

    public void showProductBuyers(Seller seller, String id) {
        if (sellerHasProductWithId(seller, id))
            System.out.println(Product.getProductById(id).getListOfBuyers().stream().map(Buyer::getUserName));
        else {
            System.out.println("you don't own any product with this id.");
        }
    }

    public void goToProductPage(User user, String id) {
        if (sellerHasProductWithId((Seller) user, id)) {
            uniqueProduct.enterUniqueProduct(user, Product.getProductById(id));
        } else {
            System.out.println("you don't own any product with this id.");
        }
    }

    public void editProductName(Seller seller, Product product) {
        System.out.print("enter new name for your product:");
        while (!(command = scanner.nextLine().trim()).equalsIgnoreCase("back")) {
            if (!Product.productWithNameExists(command)) {
                ProductEditRequest request = new ProductEditRequest(product, seller);
                request.changeRequestedChanges("name", command);
                System.out.println("your request to change product " + product.getProductId() + "'s name has been sent to the manager.");
                break;
            } else {
                System.out.print("product with this name already exists! try another name: ");
            }
        }
    }

    public void editProductPrice(Seller seller, Product product) {
        System.out.print("enter new price for your product");
        while (!(command = scanner.nextLine().trim()).equalsIgnoreCase("back")) {
            try {
                if (Float.parseFloat(command) == product.getPrice()) {
                    System.out.print("the price you've entered is the same as before, please enter a new price: ");
                } else {
                    new ProductEditRequest(product, seller).changePrice(Float.parseFloat(command));
                    System.out.println("your request to change product " + product.getProductId() + "'s price has been sent to the manager.");
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.print("invalid format");
            }
        }
    }

    public void editProductBrand(Seller seller, Product product) {
        System.out.print("enter new brand for your product: ");
        while (!(command = scanner.nextLine().trim().toLowerCase()).equalsIgnoreCase("back")) {
            if (command.equalsIgnoreCase(product.getSpecialAttributes().get("brand"))) {
                System.out.print("the brand you've entered is the same as before. please enter a new brand: ");
            } else {
                new ProductEditRequest(product, seller).changeRequestedChanges("brand", command);
                System.out.println("your request to change product " + product.getProductId() + "'s brand has been sent to the manager.");
                break;
            }
        }
    }

    public void editProductCategory(Seller seller, Product product) {
        System.out.print("enter new category for your product: ");
        while (!(command = scanner.nextLine().trim().toLowerCase()).equalsIgnoreCase("back")) {
            if (Category.categoryWithNameExists(command)) {
                if (Category.getCategoryByName(command).equals(product.getCategory())) {
                    System.out.print("the category you've chosen is the same as before please enter another category:");
                } else {
                    System.out.println("now you should change product attributes according to category.");
                    HashMap<String,String> newAttributes=enterProductAttributes(Category.getCategoryByName(command).getSpecialAttributes());
                    if (newAttributes==null)return;
                    new ProductEditRequest(product, seller).changeCategory(Category.getCategoryByName(command),newAttributes);
                    System.out.println("your request to change product " + product.getProductId() + "'s category has been sent to the manager.");
                    break;
                }
            } else {
                System.out.print("category with this name doesn't exist. please choose another category: ");
            }
        }
    }

    public void editProductSpecialAttributes(Seller seller, Product product) {
        for (String attribute : product.getSpecialAttributes().keySet()) {
            System.out.println(attribute + ": " + product.getSpecialAttributes().get(attribute));
        }
        System.out.print("enter the name of the attribute you want to change it's value: ");
        while (!product.getSpecialAttributes().containsKey(command = scanner.nextLine().trim().toLowerCase())) {
            if (command.equalsIgnoreCase("back")) {
                return;
            }
            System.out.print("product doesn't include such key. please enter another key: ");
        }
        new ProductEditRequest(product, seller).changeRequestedChanges(command, scanner.nextLine().trim());
        System.out.println("product " + product.getProductId() + "'s edit request has been sent to the manager");
    }

    public void editProductExistence(Seller seller, Product product) {
        System.out.print("enter whether your product available [yes/no]");
        while (!(command = scanner.nextLine().trim()).equalsIgnoreCase("yes|no|back")) {
            System.out.print("invalid command. please enter another command");
        }
        if (command.equalsIgnoreCase("yes")) {
            if (product.getExistence().equals(true)) {
                System.out.println("your product is already submitted as available. there's no need to send an edit request");
            } else {
                new ProductEditRequest(product, seller).changeExistence(true);
                System.out.println("product " + product.getProductId() + "'s edit request has been sent to the manager");
            }
        } else if (command.equalsIgnoreCase("no")) {
            if (product.getExistence().equals(false)) {
                System.out.println("your product is already submitted as unavailable. there's no need to send an edit request");
            } else {
                new ProductEditRequest(product, seller).changeExistence(false);
                System.out.println("product " + product.getProductId() + "'s edit request has been sent to the manager");
            }
        }
    }

    public void editProductExplanation(Seller seller, Product product) {
        System.out.println(product.getExplanation());
        System.out.println("enter new description for your product:");
        if (!(command = scanner.nextLine().trim()).equalsIgnoreCase("back")) {
            new ProductEditRequest(product, seller).changeExplanation(command);
            System.out.println("product " + product.getProductId() + "'s edit request has been sent to the manager");
        }
    }

    public String enterProductCategory() {
        for (Category category : Category.getAllCategories()) {
            if (category.getParentCategory() == null) {
                System.out.println(category.getName() + ":");
                for (Category subs : category.getSubCategories()) {
                    System.out.println('\t' + subs.getName());
                }
            }
        }
        System.out.print("enter the desired category for your product:");
        command = scanner.nextLine().trim();
        if (command.equalsIgnoreCase("back")) return "back";
        if (Category.categoryWithNameExists(command)) return command;
        else {
            System.out.println("category with this name doesn't exist.");
            return enterProductCategory();

        }
    }

    public HashMap<String, String> enterProductAttributes(ArrayList<String> categoryAttributes) {
        HashMap<String, String> productAttributes = new HashMap<>();
        for (String attribute : categoryAttributes) {
            System.out.print("enter the " + attribute + " of your product:");
            if ((command = scanner.nextLine().trim()).equalsIgnoreCase("back")) return null;
            else productAttributes.put(attribute, command);
        }
        return productAttributes;
    }

    public String enterProductPrice() {
        System.out.print("enter the price of your product:");
        command = scanner.nextLine().trim();
        if (command.equalsIgnoreCase("back")) return command;
        try {
            Float.parseFloat(command);
            return command;
        } catch (NumberFormatException e) {
            System.out.println("invalid format");
            return enterProductPrice();
        }
    }

    public String enterProductExplanation() {
        System.out.println("enter extra description for your product: ");
        return scanner.nextLine();
    }

    public void removeProduct(Seller seller, String id) {
        if (sellerHasProductWithId(seller, id)) {
            new ProductRemovalRequest(seller, Product.getProductById(id));
            System.out.println("your request to remove product " + id + " has been sent to the manager");
        } else {
            System.out.println("you don't own any product with this id.");
        }
    }

    public void showSellerOffs(Seller seller){
        for (Off off:seller.getSellerOffs()){
            System.out.println(off.getOffId()+" "+off.getOffAmount()+"%off from "+
                    off.getStartDate().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"))+" to "+
                    off.getStopDate().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")));
        }
    }

    public void viewOff(String id) {
        if (Off.getOffById(id) != null) {
            System.out.println(Off.getOffById(id));
            System.out.println(Off.getOffById(id).toString());
        } else {
            System.out.println("off with this id doesn't exist");
        }
    }

    public void editOffPercent(Seller seller, Off off) {
        System.out.print("enter new percent for your off:");
        while (!(command = scanner.nextLine().trim()).equalsIgnoreCase("back")) {
            try {
                if (Integer.parseInt(command) < 100) {
                    if (Integer.parseInt(command) != off.getOffAmount()) {
                        new OffEditRequest(seller, off).changeAmount(Integer.parseInt(command));
                        System.out.println("your request to change off " + off.getOffId() + "'s percent has been sent to the manager.");
                        break;
                    } else {
                        System.out.print("the percent you've entered is the same as before. please enter a new amount: ");
                    }
                } else {
                    System.out.print("off percent should be lower than 100. enter another number: ");
                }
            } catch (NumberFormatException e) {
                System.out.print("invalid number format. try again: ");
            }
        }
    }

    public void editOffStartDate(Seller seller, Off off) {
        System.out.print("enter new start date for your off (enter in format yyyy/MM/dd HH:mm:ss):");
        while (!(command = scanner.nextLine().trim()).equalsIgnoreCase("back")) {
            if (startDateIsValid(command, off.getStartDate())) {
                new OffEditRequest(seller, off).changeStartDate(LocalDateTime.parse(command, DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")));
                System.out.println("your request to change off " + off.getOffId() + "'s start date had been sent to the manager.");
                break;
            }
        }
    }

    private boolean startDateIsValid(String input, LocalDateTime oldStartDate) {
        try {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime dateTime = LocalDateTime.parse(input, dateTimeFormatter);
            if (dateTime.isBefore(LocalDateTime.now())) {
                System.out.println("you should choose a date after the current time.");
                return false;
            } else if (dateTime.isEqual(oldStartDate)) {
                System.out.println("the date you've entered is the same as before please enter a new date.");
                return false;
            }
            return true;
        } catch (DateTimeParseException e) {
            System.out.println("invalid format");
            return false;
        }


    }

    public void editOffEndDate(Seller seller, Off off) {
        System.out.print("enter new end date for your off (enter in format yyyy/MM/dd HH:mm:ss):");
        while (!(command = scanner.nextLine().trim()).equalsIgnoreCase("back")) {
            if (endDateIsValid(command, off.getStartDate(), off.getStopDate())) {
                new OffEditRequest(seller, off).changeEndDate(LocalDateTime.parse(command, DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")));
                System.out.println("your request to change off " + off.getOffId() + "'s end date had been sent to the manager.");
                break;
            }
        }
    }

    private boolean endDateIsValid(String input, LocalDateTime startDate, LocalDateTime oldEndDate) {
        try {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime dateTime = LocalDateTime.parse(input, dateTimeFormatter);
            if (dateTime.isBefore(startDate)) {
                System.out.println("you should choose a date after the start date.");
                return false;
            } else if (dateTime.isEqual(oldEndDate)) {
                System.out.println("the date you've entered is the same as before please enter a new date.");
                return false;
            }
            return true;
        } catch (DateTimeParseException e) {
            System.out.println("invalid format");
            return false;
        }
    }

    public void addProductToOff(Seller seller, Off off, String id) {
        if (sellerHasProductWithId(seller, id)) {
            if (off.getProductsArray().contains(Product.getProductById(id))) {
                System.out.println("this off already includes this product.");
            } else {
                new OffEditRequest(seller, off).addProduct(Product.getProductById(id));
                System.out.println("you request to add product " + id + " to off " + off.getOffId() + " has been sent to the manager.");
            }
        } else {
            System.out.println("you don't own any product with this id.");
        }
    }

    public void removeProductFromOff(Seller seller, Off off, String id) {
        if (sellerHasProductWithId(seller, id)) {
            if (off.getProductsArray().contains(Product.getProductById(id))) {
                new OffEditRequest(seller, off).removeProduct(Product.getProductById(id));
                System.out.println("you request to remove product " + id + " from off " + off.getOffId() + " has been sent to the manager.");
            } else {
                System.out.println("this off doesn't include this product.");
            }
        } else {
            System.out.println("you don't own any product with this id.");
        }
    }

    public String enterOffPercent() {
        System.out.print("enter the percentage of your off: ");
        String percent = scanner.nextLine().trim();
        if (percent.equalsIgnoreCase("back")) return percent;
        try {
            if (Integer.parseInt(percent) >= 100) {
                System.out.println("off percent should be lower than 100%");
                return enterOffPercent();
            }
            return percent;
        } catch (NumberFormatException e) {

            System.out.println("invalid input.");
            return enterOffPercent();

        }
    }

    public String enterOffStartDate() {
        System.out.print("enter the start date of your off in format [yyyy/MM/dd HH:mm:ss]:");
        String startDate = scanner.nextLine().trim();
        if (startDate.equalsIgnoreCase("back")) return startDate;
        if (startDateIsValid(startDate)) {
            return startDate;
        }
        return enterOffStartDate();

    }

    private boolean startDateIsValid(String input) {
        try {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime dateTime = LocalDateTime.parse(input, dateTimeFormatter);
            if (dateTime.isBefore(LocalDateTime.now())) {
                System.out.println("you should choose a date after the current time.");
                return false;
            }
            return true;
        } catch (DateTimeParseException e) {
            System.out.println("invalid format");
            return false;
        }


    }

    public String enterOffEndDate(LocalDateTime startDate) {
        System.out.print("enter end date for your off in format [yyyy/MM/dd HH:mm:dd]:");
        String endDate = scanner.nextLine().trim();
        if (endDate.equalsIgnoreCase("back")) return endDate;
        if (endDateIsValid(endDate, startDate)) {
            return endDate;
        }
        return enterOffEndDate(startDate);
    }

    private boolean endDateIsValid(String input, LocalDateTime startDate) {
        try {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime dateTime = LocalDateTime.parse(input, dateTimeFormatter);
            if (dateTime.isBefore(startDate)) {
                System.out.println("you should choose a date after the start date.");
                return false;
            }
            return true;
        } catch (DateTimeParseException e) {
            System.out.println("invalid format");
            return false;
        }
    }

    public ArrayList<Product> enterOffProducts(Seller seller) {
        ArrayList<Product> products = new ArrayList<>();
        for (Product product : Product.getAllProducts()) {
            System.out.println(product.getProductId() + " " + product.getSpecialAttributes().get("name"));
        }
        System.out.println("enter the id of the products you want to be included in off (enter end to finalize):");
        while (!(command = scanner.nextLine().trim()).equalsIgnoreCase("end")) {
            if (command.equalsIgnoreCase("back")) return null;
            if (sellerHasProductWithId(seller, command)) {
                if (products.contains(Product.getProductById(command))) {
                    System.out.println("you've chosen this product before.");
                } else {
                    products.add(Product.getProductById(command));
                    System.out.println("product with id " + command + " successfully added to off");
                }
            } else System.out.println("you don't own any product with this id.");
        }
        return products;
    }

    public static SellerPageController getInstance(){
        if (controller==null)return new SellerPageController();
        return controller;
    }
}
