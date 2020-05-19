package View;

import Controller.SellerPageController;
import Model.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SellerPageView {
    private static SellerPageView sellerPage;
    private String command;
    private SellerPageController controller = new SellerPageController();
    private MainPageView mainPage = MainPageView.getInstance();
    private Scanner scanner = MainPageView.getScanner();
    private ProductPageView productPage = ProductPageView.getInstance();
    private OffView offPage = OffView.getInstance();
    private Matcher matcher;

    public void enterSellerPage(Seller user) {
        System.out.println("seller user panel");
        while (!(command = scanner.nextLine().trim()).equalsIgnoreCase("back")) {
            if (command.equalsIgnoreCase("view personal info")) {
                editSellerInfo(user);
            } else if (command.equalsIgnoreCase("view company information")) {
                System.out.println(user.getFactory());
            } else if (command.equalsIgnoreCase("view sales history")) {
                for (SellLog sell:user.getSellHistory()){
                    System.out.println(sell.toString());
                }
            } else if (command.equalsIgnoreCase("manage products")) {
                manageSellerProductMenu(user);
            } else if (command.equalsIgnoreCase("add product")) {
                addProduct(user);
            } else if (command.startsWith("remove product") && (matcher = getGroup("remove product (\\d+)", command)).find()) {
                controller.removeProduct(user, matcher.group(1));
            } else if (command.equalsIgnoreCase("show categories")) {
                controller.showCategories();
            } else if (command.equalsIgnoreCase("view offs")) {
                viewSellerOffs(user);
            } else if (command.equalsIgnoreCase("view balance")) {
                System.out.println("your credit is " + user.getCredit());
            } else if (command.equalsIgnoreCase("products")) {
                productPage.enterProductPage(user);
            } else if (command.equalsIgnoreCase("offs")) {
                offPage.offMenu(user);
            } else if (command.equalsIgnoreCase("logout")) {
                mainPage.enterMainPage(null);
            }else if (command.equalsIgnoreCase("exit")){
                System.exit(1);
            } else if (command.equalsIgnoreCase("help")) {
                System.out.println("view personal info\n" +
                        "view company information\n" +
                        "view sales history\n" +
                        "manage products\n" +
                        "add product\n" +
                        "remove product [product id]\n" +
                        "show categories\n" +
                        "view offs\n" +
                        "view balance\n" +
                        "products\n" +
                        "offs\n" +
                        "logout\n" +
                        "back\n" +
                        "exit\n"+
                        "help");
            } else System.out.println("invalid command");
        }mainPage.enterMainPage(user);
    }

    public void editSellerInfo(User user) {
        controller.showPersonalInfo(user);
        System.out.println(user.toString());
        while (!(command = scanner.nextLine().trim()).equalsIgnoreCase("back")) {
            if (command.startsWith("edit") && (matcher = getGroup("edit (.+)", command)).find()) {
                controller.editSellerInfo(user, matcher.group(1));
            } else if (command.equalsIgnoreCase("logout")) {
                mainPage.enterMainPage(null);
            } else if (command.equalsIgnoreCase("help")) {
                System.out.println("edit [field]\nlogout\nback\nhelp");
            } else System.out.println("invalid command");
        }

    }

    public void manageSellerProductMenu(User user) {
        controller.showSellerProducts((Seller) user);
        while (!(command = scanner.nextLine().trim().toLowerCase()).equalsIgnoreCase("back")) {
            if (command.startsWith("view buyers") && (matcher = getGroup("view buyer (\\d+)", command)).find()) {
                controller.showProductBuyers((Seller) user, matcher.group(1));
            } else if (command.startsWith("view") && (matcher = getGroup("view (\\d+)", command)).find()) {
                controller.goToProductPage(user, matcher.group(1));
            } else if (command.startsWith("edit") && (matcher = getGroup("edit (\\d+)", command)).find()) {
                editProduct((Seller) user, matcher.group(1));
            } else if (command.toLowerCase().startsWith("sort by") && (matcher = getGroup("sort by (\\S+)", command.toLowerCase())).find()) {
                controller.sortSellerProducts((Seller) user, matcher.group(1));
            } else if (command.equalsIgnoreCase("logout")) {
                mainPage.enterMainPage(null);
            } else if (command.equalsIgnoreCase("help")) {
                System.out.println("view buyers [product id]\nview [product id]\nedit [product id]\nsort by[view/score/price]" +
                        "\nlogout\nback\nhelp");
            } else System.out.println("invalid command");
        }
    }

    private void editProduct(Seller seller, String id) {
        if (controller.sellerHasProductWithId(seller, id)) {
            System.out.print("which field do you want to edit?");
            while (!(command = scanner.nextLine().trim()).equalsIgnoreCase("back")) {
                if (command.equalsIgnoreCase("name")) {
                    controller.editProductName(seller, Product.getProductById(id));
                } else if (command.equalsIgnoreCase("price")) {
                    controller.editProductPrice(seller, Product.getProductById(id));
                } else if (command.equalsIgnoreCase("brand")) {
                    controller.editProductBrand(seller, Product.getProductById(id));
                } else if (command.equalsIgnoreCase("category")) {
                    controller.editProductCategory(seller, Product.getProductById(id));
                } else if (command.equalsIgnoreCase("special attributes")) {
                    controller.editProductSpecialAttributes(seller, Product.getProductById(id));
                } else if (command.equalsIgnoreCase("availability")) {
                    controller.editProductExistence(seller, Product.getProductById(id));
                } else if (command.equalsIgnoreCase("description")) {
                    controller.editProductExplanation(seller, Product.getProductById(id));
                } else if (command.equalsIgnoreCase("help")) {
                    System.out.println("name\nprice\nbrand\ncategory\nspecial attributes\n" +
                            "availability\ndescription\nlog out\nback\nhelp");
                } else {
                    System.out.println("invalid field");
                }
            }
        } else {
            System.out.println("product with this id doesn't exist");
        }
    }

    private void addProduct(Seller seller) {
        Random random = new Random();
        String id;
        Category category;
        float price;
        HashMap<String, String> attributes = new HashMap<>();
        while (Product.productWithIdExists(id = Integer.toString(random.nextInt(999999) + 1))) ;
        if ((command = controller.enterProductCategory()).equalsIgnoreCase("back")) return;
        else category = Category.getCategoryByName(command);
        try {
            attributes.putAll(controller.enterProductAttributes(category.getSpecialAttributes()));
        } catch (NullPointerException e) {
            return;
        }
        if ((command = controller.enterProductPrice()).equalsIgnoreCase("back")) return;
        else price = Float.parseFloat(command);
        String explanation;
        if ((explanation = controller.enterProductExplanation()).equalsIgnoreCase("back")) return;
        new ProductAdditionRequest(seller, id, price, category, attributes, explanation);
        System.out.println("your request to add product with id " + id + " has been sent to the manager");
    }

    public void viewSellerOffs(User user) {
        controller.showSellerOffs((Seller) user);
        while (!(command = scanner.nextLine().trim().toLowerCase()).equalsIgnoreCase("back")) {
            if (command.startsWith("view") && (matcher = getGroup("view (\\d+)", command)).find()) {
                controller.viewOff(matcher.group(1));
            } else if (command.startsWith("edit") && (matcher = getGroup("edit (\\d+)", command)).find()) {
                editOff((Seller) user, matcher.group(1));
            } else if (command.equalsIgnoreCase("add off")) {
                addOff((Seller) user);
            } else if (command.toLowerCase().startsWith("sort by") && (matcher = getGroup("sort by (.+)", command.toLowerCase())).find()) {
                controller.sortSellerOffs((Seller) user, matcher.group(1));
            } else if (command.equalsIgnoreCase("logout")) {
                mainPage.enterMainPage(null);
            } else if (command.equalsIgnoreCase("help")) {
                System.out.println("view [off id]\nedit [off id]\nadd off\nsort by [percent/id/start date]\nlogout\nback\nhelp");
            } else {
                System.out.println("invalid command");
            }
        }
    }

    private void editOff(Seller seller, String id) {
        if (Off.getOffById(id) != null && seller.getSellerOffs().contains(Off.getOffById(id))) {
            System.out.print("which field do you want to edit: ");
            while (!(command = scanner.nextLine().trim()).equalsIgnoreCase("back")) {
                if (command.equalsIgnoreCase("percent")) {
                    controller.editOffPercent(seller, Off.getOffById(id));
                } else if (command.equalsIgnoreCase("start date")) {
                    controller.editOffStartDate(seller, Off.getOffById(id));
                } else if (command.equalsIgnoreCase("end date")) {
                    controller.editOffEndDate(seller, Off.getOffById(id));
                } else if (command.equalsIgnoreCase("including products")) {
                    editProductsOfOff(seller, Off.getOffById(id));
                } else if (command.equalsIgnoreCase("help")) {
                    System.out.println("percent\nstart date\nend date\nincluding product\nback\nhelp");
                } else System.out.println("invalid command");
            }
        } else {
            System.out.println("off with this id doesn't exist");
        }
    }

    private void editProductsOfOff(Seller seller, Off off) {
        for (Product product : Product.getAllProducts()) {
            System.out.println(product.getProductId() + " " + product.getSpecialAttributes().get(0));
        }
        System.out.print("select a product to add or remove from off:");
        while (!(command = scanner.nextLine().trim()).equalsIgnoreCase("back")) {
            if (command.matches("add (\\d+)")) {
                controller.addProductToOff(seller, off, getGroup("add (\\d+)", command).group(1));
            } else if (command.matches("remove (\\d+)")) {
                controller.removeProductFromOff(seller, off, getGroup("remove (\\d+)", command).group(1));
            } else if (command.equalsIgnoreCase("help")) {
                System.out.println("add [product id]\nremove [product id]\nback\nhelp");
            } else System.out.println("invalid command");
        }
    }

    private void addOff(Seller seller) {
        Random random = new Random();
        String id;
        int percent;
        LocalDateTime startDate, endDate;
        ArrayList<Product> includingProducts = new ArrayList<>();

        while (Off.getOffById(id = Integer.toString(random.nextInt(999999) + 1)) != null) ;
        if ((command = controller.enterOffPercent()).equalsIgnoreCase("back")) return;
        else percent = Integer.parseInt(command);
        if ((command = controller.enterOffStartDate()).equalsIgnoreCase("back")) return;
        else startDate = LocalDateTime.parse(command, DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"));
        if ((command = controller.enterOffEndDate(startDate)).equalsIgnoreCase("back")) return;
        else endDate = LocalDateTime.parse(command, DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"));
        try {
            includingProducts.addAll(controller.enterOffProducts(seller));
        } catch (NullPointerException e) {
            return;
        }
        new OffAdditionRequest(seller, id, includingProducts, startDate, endDate, percent);
        System.out.println("your request to create off with id " + id + " has been sent to the manager.");
    }

    private Matcher getGroup(String commandPattern, String commandText) {
        Pattern pattern = Pattern.compile(commandPattern);
        Matcher matcher = pattern.matcher(commandText);
        return matcher;
    }

    public static SellerPageView getInstance() {
        if (sellerPage == null) return sellerPage=new SellerPageView();
        return sellerPage;
    }
}
