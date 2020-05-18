package Controller;

import Model.*;
import View.MainPageView;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ManagerPageController {
    private static ManagerPageController controller;
    private Scanner scanner = MainPageView.getScanner();
    String command;
    Matcher matcher;


    private boolean userWithNameExists(String name) {
        return User.getAccountByUserName(name) != null;
    }

    private boolean discountWithCodeExists(String code) {
        return OffWithCode.getOffByCode(code) != null;
    }

    public void showPersonalInfo(User user) {
        user.toString();
    }

    public void changeInfo(User user, String field) {
        String newValue;
        if (!field.equalsIgnoreCase("username")) {
            if (!field.equalsIgnoreCase("password")) {
                System.out.print("enter your new " + field + ":");
                newValue = scanner.nextLine().trim();
                user.changeInfo(field, newValue);
            } else {
                setNewPassword(user);
            }
        } else {
            System.out.println("you cannot change your username");
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

    public void showMembers() {
        for (User user : User.getUsers()) {
            System.out.println(user.getUserName() + " " + user.getClass().getSimpleName());
        }
    }

    public void showUser(String user) {

        System.out.println(User.getAccountByUserName(user).toString());
    }

    public void deleteMember(String user) {
        if (User.getAccountByUserName(user) != null) {
            User.removeUser(User.getAccountByUserName(user));
        } else {
            System.out.println("user with this username doesn't exist!");
        }
    }

    public void addSubManager() {
        System.out.print("enter managers username:");
        String username;
        while (!usernameIsValid(username = scanner.nextLine().trim())) {
            if (username.equalsIgnoreCase("back")) {
                return;
            }
        }
        System.out.print("enter password:");
        String password;
        while (!passwordIsValid(password = scanner.nextLine().trim())) {
            if (password.equalsIgnoreCase("back")) {
                return;
            }
        }

        System.out.print("enter first name:");
        String firstName;
        if ((firstName = scanner.nextLine().trim()).equalsIgnoreCase("back")) {
            return;
        }

        System.out.print("enter last name:");
        String lastName;
        if ((lastName = scanner.nextLine().trim()).equalsIgnoreCase("back")) {
            return;
        }

        System.out.print("enter phone number:");
        String phoneNumber;
        while (!phoneNumberIsValid(phoneNumber = scanner.nextLine().trim())) {
            if (phoneNumber.equalsIgnoreCase("back")) {
                return;
            }
        }

        System.out.print("enter email:");
        String email;
        while (!emailIsValid(email = scanner.nextLine().trim())) {
            if (email.equalsIgnoreCase("back")) {
                return;
            }
        }

        new Manager(username, firstName, lastName, email, phoneNumber, password);
        System.out.println("manager " + username + " successfully created.");
    }

    private boolean usernameIsValid(String username) {
        if (!username.matches("\\w+")) {
            System.out.print("you can only use english letters,numbers and underline in your username." +
                    "\nplease choose another username:");
            return false;
        } else if (userWithNameExists(username)) {
            System.out.print("user with this name already exists." +
                    "\nplease choose another username:");
            return false;
        } else return !username.equalsIgnoreCase("back");
    }

    private boolean passwordIsValid(String password) {
        if (!password.matches("\\w+")) {
            System.out.print("you can only use english letters,numbers and underline in your password." +
                    "\nplease choose another password:");
            return false;
        } else return !password.equalsIgnoreCase("back");
    }

    private boolean emailIsValid(String email) {
        if (email.equalsIgnoreCase("back")) {
            return false;
        } else if (!email.matches("\\w+@[a-z]+\\.[a-z]+")) {
            System.out.print("email is invalid.\nrewrite your email:");
            return false;
        }
        return true;
    }

    private boolean phoneNumberIsValid(String phoneNumber) {
        if (phoneNumber.equalsIgnoreCase("back")) {
            return false;
        } else if (!phoneNumber.matches("[+98|0]9\\d{9}")) {
            System.out.print("phone number should only include numbers." +
                    "\n rewrite your phone number");
            return false;
        }
        return true;
    }

    public void removeProductsByManager(String id) {
        if (Product.getProductById(id) != null) {
            Product.removeProduct(Product.getProductById(id));
        } else {
            System.out.println("product with this id doesn't exist!");
        }

    }

    public void showAllDiscountCodes() {
        OffWithCode.getAllDiscounts().stream().map(OffWithCode::toString).forEach(System.out::println);

    }

    public void createDiscount() {
        System.out.print("enter the new discount code:");
        String code;
        Random random = new Random();
        while (discountWithCodeExists(code = Integer.toString(random.nextInt(999999) + 1))) ;
        System.out.print("enter percentage of the discount:");
        String percent;
        while (true) {
            if ((percent = scanner.nextLine().trim()).equalsIgnoreCase("back")) {
                return;
            }
            try {

                if (Integer.parseInt(percent) >= 100) {
                    System.out.print("discount percent must be lower than 100." +
                            "please reenter discount percent:");
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("invalid format");
            }
        }
        System.out.print("enter maximum amount for discount:");
        String max;
        while (true) {
            if ((max = scanner.nextLine().trim()).equalsIgnoreCase("back")) {
                return;
            }
            try {
                Double.parseDouble(max);
                break;
            } catch (NumberFormatException e) {
                System.out.println("invalid format!");
            }
        }
        System.out.print("enter the number of times code can be used per order:");
        String usesPerOrder;
        while (true) {
            if ((usesPerOrder = scanner.nextLine().trim()).equalsIgnoreCase("back")) {
                return;
            }
            try {
                Integer.parseInt(usesPerOrder);
                break;
            } catch (NumberFormatException e) {
                System.out.println("invalid format");
            }
        }
        System.out.print("enter the start date of the discount in format yyyy/MM/dd HH:mm:ss : ");
        String startDate;
        while (!startDateIsValid(startDate = scanner.nextLine().trim())) {
            if (startDate.equalsIgnoreCase("back"))
                return;
            System.out.print("enter the start date of the discount in format yyyy/MM/dd HH:mm:ss : ");
        }

        System.out.print("enter the end date of the discount in format yyyy/MM/dd HH:mm:ss : ");
        String endDate;
        while (!endDateIsValid(endDate = scanner.nextLine().trim(), LocalDateTime.parse(startDate, DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")))) {
            if (endDate.equalsIgnoreCase("back"))
                return;
            System.out.print("enter the end date of the discount in format yyyy/MM/dd HH:mm:ss : ");
        }
        System.out.println("enter the accounts this discount is applied to (enter [end] to finalize): ");
        ArrayList<Buyer> applyingAccounts = new ArrayList<>();
        String account;
        while (!(account = scanner.nextLine().trim()).equalsIgnoreCase("end")) {
            if (account.equalsIgnoreCase("back")) {
                return;
            }
            if (userWithNameExists(account)) {
                if (User.getAccountByUserName(account) instanceof Buyer) {
                    applyingAccounts.add((Buyer) User.getAccountByUserName(account));
                    System.out.println("account added");
                } else System.out.println("you can only add buyers.");
            } else {
                System.out.println("account with this username doesn't exist.");
            }
        }
        new OffWithCode(code, LocalDateTime.parse(startDate, DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")), LocalDateTime.parse(endDate, DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"))
                , Integer.parseInt(percent), Float.parseFloat(max), Integer.parseInt(usesPerOrder), applyingAccounts);
        System.out.println("new discount successfully created!");
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
            if (input.equalsIgnoreCase("back")) {
                return false;
            }
            System.out.println("invalid format");
            return false;
        }


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
            if (input.equalsIgnoreCase("back")) {
                return false;
            }
            System.out.println("invalid format");
            return false;
        }
    }

    public void showDiscountCode(String code) {
        if (discountWithCodeExists(code)) {
            System.out.println(OffWithCode.getOffByCode(code).toString());
        } else {
            System.out.println("discount with this code doesn't exist!");
        }
    }

    public void editDiscountAmount(OffWithCode discount) {
        System.out.print("set discount's new percentage:");
        if (!(command = scanner.nextLine().trim()).equalsIgnoreCase("back")) {
            discount.setOffAmount(Integer.parseInt(command));
            System.out.println("discount percent changed successfully");
        }
    }

    public void editDiscountMaxAmount(OffWithCode discount) {
        System.out.print("enter discount's new maximum amount:");
        if (!(command = scanner.nextLine().trim()).equalsIgnoreCase("back")) {
            discount.setMaxAmount(Float.parseFloat(command));
            System.out.println("discount maximum amount changed successfully");
        }
    }

    public void editDiscountApplyingUsers(OffWithCode discount) {
        System.out.println("choose a user to add or remove from discount");
        for (User user : discount.getApplyingAccounts().keySet()) {
            System.out.println(user.getUserName());
        }
        while ((command = scanner.nextLine().trim()).equalsIgnoreCase("back")) {
            if (command.matches("add (\\w+)") || (matcher = getGroup("add \\w+)", command)).find()) {
                command = matcher.group(1);
                if (discount.getApplyingAccounts().containsKey(command)) {
                    System.out.println("discount already applies to this user");
                } else {
                    if (userWithNameExists(command)) {
                        if (User.getAccountByUserName(command) instanceof Buyer) {
                            discount.addUserToDiscount(User.getAccountByUserName(command));
                            System.out.println(command + "added to discount successfully");
                        } else {
                            System.out.println("you can only add buyers.");
                        }
                    } else {
                        System.out.println("user with this username doesn't exist");
                    }
                }
            } else if (command.matches("remove (\\w+)") || (matcher = getGroup("remove \\w+)", command)).find()) {
                command = matcher.group(1);
                if (!discount.getApplyingAccounts().containsKey(command)) {
                    System.out.println("discount doesn't apply to this user");
                } else {
                    if (userWithNameExists(command)) {
                        discount.removeUserFromDiscount(User.getAccountByUserName(command));
                        System.out.println(command + " removed successfully");
                    } else {
                        System.out.println("user whit this username doesn't exist");
                    }
                }
            } else if (command.equalsIgnoreCase("help")) {
                System.out.println("add [username]\n" +
                        "remove [username]\n" +
                        "back\n" +
                        "help");
            } else {
                System.out.println("invalid command");
            }
        }
    }

    public void editDiscountStartDate(OffWithCode discount) {
        System.out.print("enter new start date: ");
        String newStartDate;
        while (!startDateIsValid(newStartDate = scanner.nextLine().trim())) {
            if (newStartDate.equalsIgnoreCase("back"))
                return;
            System.out.print("enter new start date: ");
        }
        discount.setStartDate(LocalDateTime.parse(newStartDate, DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")));
        System.out.println("start date changed successfully");
    }

    public void editDiscountEndDate(OffWithCode discount) {
        System.out.print("enter new end date: ");
        String newEndDate;

        while (!endDateIsValid((newEndDate = scanner.nextLine().trim()), discount.getStartDate())) {
            if (newEndDate.equalsIgnoreCase("back"))
                return;
            System.out.print("enter new end date: ");
        }
        discount.setStopDate(LocalDateTime.parse(newEndDate, DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")));
    }

    public void removeDiscountCode(String code) {
        if (OffWithCode.getOffByCode(code) != null) {
            OffWithCode.deleteDiscount(OffWithCode.getOffByCode(code));
        } else {
            System.out.println("discount with this code doesn't exist!");
        }

    }

    public void showRequests() {
        for (Request request : Request.getAllRequests()) {
            System.out.println(request.toString());
        }
    }

    public void showRequestDetail(String id) {
        if (Request.getRequestsById(id) != null) {
            Request.getRequestsById(id).showDetails();
        } else {
            System.out.println("request with this id doesn't exist");
        }
    }

    public void acceptRequest(String id) {
        try {
            Request.getRequestsById(id).acceptRequest();
        } catch (NullPointerException e) {
            System.out.println("request with this id doesn't exist");
        }
    }

    public void declineRequest(String id) {
        try {
            Request.getRequestsById(id).declineRequest();
        } catch (NullPointerException e) {
            System.out.println("request with this id doesn't exist");
        }
    }

    public void showCategories() {
        for (Category category : Category.getAllCategories()) {
            System.out.print(category.getName() + "{");
            for (Category subCategory : category.getSubCategories()) {
                System.out.print('\n' + subCategory.getName());
            }
            System.out.println("}");
        }
    }

    public void showCategories(Category parentCategory, Category childCategory) {
        System.out.println("no parent category");
        for (Category category : Category.getAllCategories()) {
            if (!category.equals(parentCategory) || !category.equals(childCategory)) {
                System.out.println(category.getName());
            }
        }
    }

    public void editCategoryName(Category category) {
        System.out.print("write new name:");
        while ((Category.categoryWithNameExists(command = scanner.nextLine().trim()))) {
            if (command.equalsIgnoreCase("back")) {
                return;
            }
            System.out.print("category with this name already exists.\nplease choose another name:");
        }
        System.out.println("category " + category.getName() + " changed to " + command + "\\.");
        category.setName(command);


    }

    public void editCategoryParent(Category category) {
        System.out.println("select new parent category or [no parent category]");
        showCategories(category.getParentCategory(), category);
        while (!(command = scanner.nextLine().trim()).equalsIgnoreCase("back")) {
            if (command.equalsIgnoreCase("no parent category")) {
                category.setParentCategory(null);
                System.out.println(category + "'s parent category is removed.");
                return;
            } else if (Category.categoryWithNameExists(command)) {
                if (command.equalsIgnoreCase(category.getName())) {
                    System.out.println("you can't choose a category as it's own parent");
                } else {
                    if (Category.getCategoryByName(command).getParentCategory() == null) {
                        category.setParentCategory(Category.getCategoryByName(command));
                        System.out.println(category + "'s parent category changed to " + command);
                        return;
                    } else System.out.println("this category is a subcategory itself.");
                }
            }
        }
    }

    public void editCategoryAttributes(Category category) {
        System.out.println("enter an attribute you want to add or remove:");
        for (String attribute : category.getSpecialAttributes()) {
            System.out.println(attribute);
        }
        while (!(command = scanner.nextLine().trim()).equalsIgnoreCase("back")) {
            if (command.matches("add ([\\S+ \\s*]+)") || (matcher = getGroup("add ([\\S+ \\s*]+)", command)).find()) {
                command = matcher.group(1);
                if (!category.getSpecialAttributes().contains(command)) {
                    category.addAttribute(command);
                    System.out.println("attribute " + command + " successfully added to the category!");
                } else {
                    System.out.println("this category already contains this attributes");
                }
            } else if (command.matches("remove ([\\S+ \\s*]+)") || (matcher = getGroup("remove ([\\S+ \\s*]+)", command)).find()) {
                command = matcher.group(1);
                if (category.getSpecialAttributes().contains(command)) {
                    category.removeAttribute(command);
                    System.out.println("attribute " + command + " successfully removed from the category!");

                } else {
                    System.out.println("this category doesn't contain this attribute.");
                }
            } else if (command.equalsIgnoreCase("help")) {
                System.out.println("add [attribute]\n" +
                        "remove [attribute]\n" +
                        "back\n" +
                        "help");
            } else {
                System.out.println("invalid command");
            }
        }
    }

    public void addCategory(String name) {
        if (!Category.categoryWithNameExists(name)) {
            System.out.print("would you like this category to be a sub-category of any category?[yes/no]");
            while (!(command = scanner.nextLine().trim()).equalsIgnoreCase("yes|no")) {
                if (command.equals("back")) {
                    return;
                }
                System.out.println("invalid command");
            }
            if (command.equalsIgnoreCase("yes")) {
                System.out.println("choose the parent category");
                showCategories();
                command = scanner.nextLine().trim();
                while (!Category.categoryWithNameExists(command) && (Category.getCategoryByName(command).getParentCategory() != null)) {
                    if (command.equalsIgnoreCase("back")) {
                        return;
                    }
                    if (Category.categoryWithNameExists(command))
                        System.out.println("this category is already a subcategory. please choose another category");
                    else System.out.println("this category doesn't exist! choose another parent category");
                    command = scanner.nextLine().trim();
                }
                getCategoryAttributes(name, Category.getCategoryByName(command));
            } else {
                getCategoryAttributes(name, null);
            }
        } else {
            System.out.println("category with this name already exists!");
        }


    }

    private void getCategoryAttributes(String name, Category parentCategory) {
        System.out.println("write the special attributes of your category" +
                "(write <end> to finish):");
        ArrayList<String> attributes = new ArrayList<>();
        while (!(command = scanner.nextLine().trim()).equalsIgnoreCase("back|end")) {
            if (command.equalsIgnoreCase("back")) {
                return;
            }
            attributes.add(command);
        }
        new Category(name, parentCategory, attributes);
        System.out.println("category " + name + " is successfully created");
    }


    public void removeCategory(String name) {
        if (Category.getCategoryByName(name) != null) {
            Category.deleteCategory(Category.getCategoryByName(name));
        } else {
            System.out.println("this category doesn't exist");

        }

    }

    private Matcher getGroup(String commandPattern, String commandText) {
        Pattern pattern = Pattern.compile(commandPattern);
        Matcher matcher = pattern.matcher(commandText);
        return matcher;
    }

    public static ManagerPageController getInstance(){
        if (controller==null){
            return new ManagerPageController();
        }
        return controller;
    }
}
