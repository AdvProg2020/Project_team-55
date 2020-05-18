package Controller;

import Model.Comments;
import Model.Product;
import Model.Seller;
import Model.User;
import View.BuyerView;
import View.MainPageView;
import View.ManagerPageView;
import View.SellerPageView;

import java.util.Scanner;

public class JustUniqueProductsController {
    private static JustUniqueProductsController controller;
    private String command;
    private Scanner scanner= MainPageView.getScanner();

    public void showComments(Product product) {
        System.out.println("average score:"+product.getAverageScore());
        for (Comments comment:product.getComments()){
            System.out.println(comment.getCommenter()+":");
            System.out.println('\t'+comment.getTitle());
            System.out.println(comment.getMessages());
        }
    }

    public void addComments(Product product, User user) {
        System.out.print("enter title of your comment:");
        String title=scanner.nextLine().trim();
        if (title.equalsIgnoreCase("back"))return;
        System.out.print("enter your comment:");
        String message=scanner.nextLine().trim();
        if (message.equalsIgnoreCase("back"))return;
        new Comments(user,message,title,product);
    }

    public static JustUniqueProductsController getInstance(){
        if (controller==null)return new JustUniqueProductsController();
        return controller;
    }

}
