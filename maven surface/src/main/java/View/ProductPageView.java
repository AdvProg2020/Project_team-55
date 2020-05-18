package View;

import Controller.ProductPageController;
import Model.User;

import javax.swing.*;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProductPageView {
    private static ProductPageView productPage;
    private String command;
    private MainPageView mainPage=MainPageView.getInstance();
    private Scanner scanner= MainPageView.getScanner();
    private Matcher matcher;
    LoginRegister loginRegister=LoginRegister.getInstance();
    private ProductPageController controller=ProductPageController.getInstance();

    public void enterProductPage(User user) {
        while (!(command=scanner.nextLine().trim().toLowerCase()).equalsIgnoreCase("back")){
            if (command.equalsIgnoreCase("show products")){
                controller.showProducts();
            }else if(command.matches("show product (\\d+)")&&(matcher=getGroup("show product (\\d+)",command)).find()){
                controller.goToProduct(user,matcher.group(1));
            }else if (command.equalsIgnoreCase("vew categories")){
                controller.showCategories();
            }else if (command.equalsIgnoreCase("filtering")){
                enterFilterMenu(user);
            }else if (command.equalsIgnoreCase("sorting")){
                enterSortingMenu(user);
            }else if (command.equalsIgnoreCase("login")){
                if (user==null)user=loginRegister.loginByForce();
                else System.out.println("you have already logged in");
            }else if (command.equalsIgnoreCase("logout")){
                if (user==null) System.out.println("");
            }else if (command.equalsIgnoreCase("help")){
                System.out.println("show categories\nshow products\nshow product [product id]\nfiltering\nsorting");
                if (user==null) System.out.println("login");
                else System.out.println("logout");
                System.out.println("back\nhelp");
            }else System.out.println("invalid command");
        }
    }

    public void enterFilterMenu(User user) {
        while (!(command = scanner.nextLine().trim().toLowerCase()).equals("back")) {
            if (command.equals("show available filters")) {
                controller.showAvailableFilters();
            } else if (command.equals("current filters")) {
                controller.showCurrentFilters();
            } else if (command.startsWith("filter") && (matcher = getGroup("filter (.+):(.+)", command)).find()) {
                controller.addFilter(matcher.group(1), matcher.group(2));
            } else if (command.startsWith("disable filter") && (matcher = getGroup("disable filter ([^:]+:[^:]+|price)$", command)).find()) {
                controller.removeFilter(matcher.group(1));
            } else if (command.equalsIgnoreCase("help")) {
                System.out.println("show available filters\ncurrent filters\nfilter ([attribute]:[attribute value] or price:[min price]-[max price])" +
                        "disable filter ([attribute]:[attribute value] or price)\nback\nhelp");
            }
        }
    }

    public void enterSortingMenu(User user) {
        while (!(command = scanner.nextLine().trim().toLowerCase()).equals("back")) {
            if (command.equals("show available sorts")) {
                System.out.println("views\naverage score\ncreation date");
            } else if (command.startsWith("sort") && (matcher = getGroup("sort (.+)", command)).find()) {
                controller.setSortBy(matcher.group(1));
            } else if (command.equalsIgnoreCase("current sort")) {
                System.out.println(controller.getSortBy());
            } else if (command.equalsIgnoreCase("disable sort")) {
                controller.disableSort();
            } else if (command.equalsIgnoreCase("help")) {
                System.out.println("show available sorts\nsort [sort element]\ncurrent sort\ndisable sort\nback\nhelp");
            }
        }
    }

    private Matcher getGroup(String commandPattern, String commandText) {
        Pattern pattern = Pattern.compile(commandPattern);
        Matcher matcher = pattern.matcher(commandText);
        return matcher;
    }

    public static ProductPageView getInstance(){
        if (productPage==null)return new ProductPageView();
        return productPage;
    }

}
