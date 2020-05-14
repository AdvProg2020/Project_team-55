package View;

import Controller.ManagerPageController;
import Model.Category;
import Model.OffWithCode;
import Model.User;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ManagerPageView {
    ManagerPageController controller;
    ProductPageView productPage;
    String command;
    Scanner scanner;
    Matcher matcher;

    public void enterManagerPageMenu(User user) {
        while (!(command = scanner.nextLine()).trim().equalsIgnoreCase("back")) {
            if ("view personal info".equalsIgnoreCase(command)) {
                viewPersonalInfoManager(user);
            } else if ("manage users".equalsIgnoreCase(command)) {
                manageUsersForManager();
            } else if ("manage all products".equalsIgnoreCase(command)) {
                manageProductByManager();
            } else if ("create discount code".equalsIgnoreCase(command)) {
                controller.createDiscount();
            } else if ("view discount code".equalsIgnoreCase(command)) {
                viewDiscountCodeByManager(user);
            } else if ("manage requests".equalsIgnoreCase(command)) {
                manageRequestByManager(user);
            } else if ("manage categories".equalsIgnoreCase(command)) {
                manageCategoriesByManager();
            } else if ("product".equalsIgnoreCase(command)) {
                productPage.enterProductPage(user);
            } else if ("help".equalsIgnoreCase(command)) {
                System.out.println("view personal info\n" +
                        "manage users\n" +
                        "manage all products\n" +
                        "create discount code\n" +
                        "view discount code\n" +
                        "manage requests\n" +
                        "manage categories\n" +
                        "product\n" +
                        "log out\n" +
                        "back\n" +
                        "help");
            } else {
                System.out.println("invalid command");
            }
        }
    }

    public void viewPersonalInfoManager(User user) {
        controller.showPersonalInfo(user);
        while (!(command = scanner.nextLine().trim()).equalsIgnoreCase("back")) {
            if (command.matches("edit (.+)") || (matcher = getGroup("edit (.+)", command)).find()) {
                controller.changeInfo(user, matcher.group(1));
            } else if (command.equalsIgnoreCase("help")) {
                System.out.println("edit [field]\n" +
                        "logout\n" +
                        "back\n" +
                        "help");
            } else {
                System.out.println("invalid command");
            }
        }

    }

    public void manageUsersForManager() {
        controller.showMembers();
        while (!(command = scanner.nextLine().trim()).equalsIgnoreCase("back")) {
            if (command.matches("view (\\w+)") ||
                    (matcher = getGroup("view (\\w+)", command)).find()) {
                controller.showUser(matcher.group(1));
            } else if (command.matches("delete user (\\w+)") ||
                    (matcher = getGroup("view (\\w+)", command)).find()) {
                controller.deleteMember(matcher.group(1));
            } else if (command.equals("create manager profile")) {
                controller.addSubManager();
            } else if (command.equalsIgnoreCase("help")) {
                System.out.println("view [username]\n" +
                        "delete user [username]" +
                        "create manager profile\n" +
                        "sort by [username/last name/first name]\n" +
                        "log out\n" +
                        "back\n" +
                        "help");
            } else {
                System.out.println("invalid command");
            }
        }
    }

    public void manageProductByManager() {
        while (!(command = scanner.nextLine().trim()).equalsIgnoreCase("back")) {
            if (command.matches("remove (\\d+)") ||
                    (matcher = getGroup("remove (\\d+)", command)).find()) {
                controller.removeProductsByManager(matcher.group(1));
            } else if (command.equalsIgnoreCase("help")) {
                System.out.println("remove [product id]\n" +
                        "sort by [name/id/brand]\n" +
                        "log out\n" +
                        "back\n" +
                        "help");
            } else {
                System.out.println("invalid command");
            }
        }
    }


    public void viewDiscountCodeByManager(User user) {
        controller.showAllDiscountCodes();
        while (!(command = scanner.nextLine().trim()).equalsIgnoreCase("back")) {
            if (command.matches("view discount code (\\w+)") ||
                    (matcher = getGroup("view discount code (\\w+)", command)).find()) {
                controller.showDiscountCode(matcher.group(1));
            } else if (command.matches("edit discount code (\\w+)") ||
                    (matcher = getGroup("edit discount code (\\w+)", command)).find()) {
                editDiscountCode(matcher.group(1));
            } else if (command.matches("remove discount code (\\w+)") ||
                    (matcher = getGroup("remove discount code (\\w+)", command)).find()) {
                controller.removeDiscountCode(matcher.group(1));
            } else if (command.equalsIgnoreCase("help")) {
                System.out.println("view discount code [discount code]\n" +
                        "edit discount code [discount code]\n" +
                        "remove discount code [discount code]\n" +
                        "sort by [code/percent/start date/finish date]\n" +
                        "log out\n" +
                        "back\n" +
                        "help");
            } else {
                System.out.println("invalid command");
            }
        }
    }

    private void editDiscountCode(String discount) {
        if (OffWithCode.getOffByCode(discount) != null) {
            System.out.println("which field do you want to edit?");
            System.out.println(discount);
            while (!(command = scanner.nextLine().trim()).equalsIgnoreCase("back")) {
                if (command.equalsIgnoreCase("amount")) {
                    controller.editDiscountAmount(OffWithCode.getOffByCode(discount));
                } else if (command.equalsIgnoreCase("code")) {
                    controller.editDiscountCode(OffWithCode.getOffByCode(discount));
                } else if (command.equalsIgnoreCase("applying users")) {
                    controller.editDiscountApplyingUsers(OffWithCode.getOffByCode(discount));
                } else if (command.equalsIgnoreCase("maximum amount")) {
                    controller.editDiscountMaxAmount(OffWithCode.getOffByCode(discount));
                } else if (command.equalsIgnoreCase("start date")) {
                    controller.editDiscountStartDate(OffWithCode.getOffByCode(discount));
                } else if (command.equalsIgnoreCase("end date")) {
                    controller.editDiscountEndDate(OffWithCode.getOffByCode(discount));
                } else if (command.equalsIgnoreCase("help")) {
                    System.out.println("fields you can edit are:\n" +
                            "amount, code, applying users, maximum amount, start date, end date\n" +
                            "other commands:\n" +
                            "back\n" +
                            "help");
                }
            }
        } else {
            System.out.println("discount with this code doesn't exist");
        }
    }

    public void manageRequestByManager(User user) {
        controller.showRequests();
        while (!(command = scanner.nextLine().trim()).equalsIgnoreCase("back")) {
            if (command.matches("details (\\d+)") ||
                    (matcher = getGroup("details (\\d+)", command)).find()) {
                controller.showRequestDetail(matcher.group(1));
            } else if (command.matches("accpet (\\d+)") ||
                    (matcher = getGroup("accept (\\d+)", command)).find()) {
                controller.showRequestDetail(matcher.group(1));
            } else if (command.equalsIgnoreCase("decline (\\d+") ||
                    (matcher = getGroup("decline (\\d+)", command)).find()) {
                controller.showRequestDetail(matcher.group(1));
            } else if (command.equalsIgnoreCase("help")) {
                System.out.println("details [request id]\n" +
                        "accept [request id]\n" +
                        "decline [request id]\n" +
                        "log out\n" +
                        "back\n" +
                        "help");
            } else {
                System.out.println("invalid command");
            }
        }
    }

    public void manageCategoriesByManager() {
        controller.showCategories();
        while (!(command = scanner.nextLine().trim()).equalsIgnoreCase("back")) {
            if (command.matches("edit ([\\S \\s]+)") ||
                    (matcher = getGroup("edit (.+)", command)).find()) {
                editCategory(matcher.group(1));
            } else if (command.matches("add ([\\S \\s]+)") ||
                    (matcher = getGroup("add (.+)", command)).find()) {
                controller.addCategory(matcher.group(1));
            } else if (command.matches("remove ([\\S \\s]+)") ||
                    (matcher = getGroup("remove (.+)", command)).find()) {
                controller.removeCategory(matcher.group(1));
            } else if (command.equalsIgnoreCase("help")) {
                System.out.println("edit [category name]\n" +
                        "add [category name]\n" +
                        "remove [category name]\n" +
                        "log out\n" +
                        "back\n" +
                        "help");
            } else {
                System.out.println("invalid command");
            }
        }

    }

    private void editCategory(String name) {
        if (Category.categoryWithNameExists(name)) {
            System.out.println("you are editing category" + name);
            while (!(command = scanner.nextLine().trim()).equalsIgnoreCase("back")) {
                if (command.equalsIgnoreCase("change name")) {
                    controller.editCategoryName(Category.getCategoryByName(name));
                } else if (command.equalsIgnoreCase("change parent category")) {
                    controller.editCategoryParent(Category.getCategoryByName(name));
                } else if (command.equalsIgnoreCase("change special attributes")) {
                    controller.editCategoryAttributes(Category.getCategoryByName(name));
                } else if (command.equalsIgnoreCase("help")) {
                    System.out.println("change name\n" +
                            "change parent category\n" +
                            "change special attributes\n" +
                            "log out\n" +
                            "back\n" +
                            "help\n");
                } else {
                    System.out.println("invalid command");
                }
            }
        } else {
            System.out.println("category doesn't exist");
        }
    }

    private Matcher getGroup(String commandPattern, String commandText) {
        Pattern pattern = Pattern.compile(commandPattern);
        Matcher matcher = pattern.matcher(commandText);
        return matcher;
    }
}
