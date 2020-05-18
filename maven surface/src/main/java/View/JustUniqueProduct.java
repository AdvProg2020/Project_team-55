package View;

import Controller.JustUniqueProductsController;
import Model.Buyer;
import Model.Product;
import Model.User;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JustUniqueProduct {
    private static JustUniqueProduct uniqueProduct;
    private String command;
    private LoginRegister login=LoginRegister.getInstance();
    private JustUniqueProductsController controller=JustUniqueProductsController.getInstance();
    private Matcher matcher;
    private MainPageView mainPage=MainPageView.getInstance();
    private Scanner scanner= MainPageView.getScanner();

    public void enterUniqueProduct(User user, Product product) {
        product.setViews(product.getViews()+1);

        while (!(command=scanner.nextLine().trim().toLowerCase()).equalsIgnoreCase("back")){
            if (command.equalsIgnoreCase("digest")){
                digestProduct(user,product);
            }else if (command.equalsIgnoreCase("attributes")){
                product.toString();
            }else if (command.startsWith("compare")&&(matcher=getGroup("compare (\\d+)",command)).find()){
                if (Product.productWithIdExists(matcher.group(1))){
                    Product.compare2Products(product,Product.getProductById(matcher.group(1)));
                }else System.out.println("product with this id doesn't exist.");
            }else if (command.equalsIgnoreCase("comments")){
                commentsForProduct(user,product);
            }else if (command.equalsIgnoreCase("help")){
                System.out.println("digest\nattrinutes\ncompare [product id]\ncomments\nback\nhelp");
            }
        }
    }


    public void digestProduct(User user, Product product) {
        System.out.println(product.initialToString());
        while (!(command=scanner.nextLine().trim()).equalsIgnoreCase("back")){
            if (command.equalsIgnoreCase("add to cart")){
                if (user==null) login.loginByForce();
                else if (user instanceof Buyer){
                    ((Buyer) user).addProductToCart(product);
                }else System.out.println("only buyers can add product to their cart.");
            }
        }
    }

    public void commentsForProduct(User user, Product product) {
        controller.showComments(product);
        while (!(command=scanner.nextLine().trim()).equalsIgnoreCase("back")){
            if (command.equalsIgnoreCase("add comment")){
                if (user==null) System.out.println("you must login to add a comment");
                if (user instanceof Buyer){
                    controller.addComments(product,user);
                }else System.out.println("only buyers can comment on a product.");
            }else if (command.equalsIgnoreCase("help")){
                System.out.println("add comment\nback\nhelp");
            }
        }
    }

    private Matcher getGroup(String commandPattern, String commandText) {
        Pattern pattern = Pattern.compile(commandPattern);
        Matcher matcher = pattern.matcher(commandText);
        return matcher;
    }

    public static JustUniqueProduct getInstance(){
        if (uniqueProduct==null)return new JustUniqueProduct();
        return uniqueProduct;
    }

}
