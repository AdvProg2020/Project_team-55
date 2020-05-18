package View;

import Controller.OffsController;
import Model.User;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OffView {

    private static OffView offView;
    private Scanner scanner= MainPageView.getScanner();
    private String command;
    private Matcher matcher;
    private OffsController offController=OffsController.getInstance();
    private LoginRegister loginRegister=LoginRegister.getInstance();

    public void offMenu(User user) {
        offController.showOffs();
        while (!(command = scanner.nextLine().trim()).equals("back")) {
            if (command.matches("show product (\\d+)") && (matcher = getGroup("show product (\\d+)", command)).find()) {
                offController.goToProduct(user, matcher.group(1));
            } else if (command.equalsIgnoreCase("filtering")) {
                enterFilterMenu(user);
            } else if (command.equalsIgnoreCase("sorting")) {
                enterSortingMenu(user);
            } else if (command.equalsIgnoreCase("logout")) {
                if (user == null) System.out.println("you must first log in");
                else {
                    user = null;
                    System.out.println("you logged out successfully");
                }
            } else if (command.equalsIgnoreCase("login")) {
                if (user != null) System.out.println("you have already logged in.");
                else user=loginRegister.loginByForce();
            } else if (command.equalsIgnoreCase("help")) {
                System.out.println("show product [product id]\nfiltering\nsorting");
                if (user == null) System.out.println("login\nregister");
                else System.out.println("logout");
                System.out.println("back\nhelp");
            }else {
                System.out.println("invalid command");
            }
        }
    }

    private void enterSortingMenu(User user) {
        while (!(command = scanner.nextLine().trim().toLowerCase()).equals("back")) {
            if (command.equals("show available sorts")) {
                System.out.println("views\naverage score\ncreation date");
            } else if (command.startsWith("sort") && (matcher = getGroup("sort (.+)", command)).find()) {
                offController.setSortBy(matcher.group(1));
            } else if (command.equalsIgnoreCase("current sort")) {
                System.out.println(offController.getSortBy());
            } else if (command.equalsIgnoreCase("disable sort")) {
                offController.disableSort();
            } else if (command.equalsIgnoreCase("help")) {
                System.out.println("show available sorts\nsort [sort element]\ncurrent sort\ndisable sort\nback\nhelp");
            }else {
                System.out.println("invalid command");
            }
        }
    }

    private void enterFilterMenu(User user) {
        while (!(command = scanner.nextLine().trim().toLowerCase()).equals("back")) {
            if (command.equals("show available filters")) {
                offController.showAvailableFilters();
            } else if (command.equals("current filters")) {
                offController.showCurrentFilters();
            } else if (command.startsWith("filter") && (matcher = getGroup("filter (.+):(.+)", command)).find()) {
                offController.addFilter(matcher.group(1), matcher.group(2));
            } else if (command.startsWith("disable filter") && (matcher = getGroup("disable filter ([^:]+:[^:]+|price)$", command)).find()) {
                offController.removeFilter(matcher.group(1));
            } else if (command.equalsIgnoreCase("help")) {
                System.out.println("show available filters\ncurrent filters\nfilter ([attribute]:[attribute value] or price:[min price]-[max price])" +
                        "disable filter ([attribute]:[attribute value] or price)\nback\nhelp");
            }else System.out.println("invalid command");
        }
    }

    private Matcher getGroup(String commandPattern, String commandText) {
        Pattern pattern = Pattern.compile(commandPattern);
        Matcher matcher = pattern.matcher(commandText);
        return matcher;
    }

    public static OffView getInstance(){
        if (offView==null)return new OffView();
        return offView;
    }
}
