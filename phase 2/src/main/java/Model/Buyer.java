package Model;

import java.util.ArrayList;
import java.util.LinkedList;

public class Buyer extends User {
    private Cart cart;
    private ArrayList<BuyLog> orderHistory=new ArrayList<BuyLog>();
    private static final LinkedList<Buyer> allBuyers=new LinkedList<>();

    public Buyer(String userName, String firstName, String lastName, String email, String phoneNumber, String password, float credit) {
        super(userName,firstName,lastName,email,phoneNumber,password);
        this.credit = credit;
        allBuyers.add(this);
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public void addProductToCart(Product product) {
        if (product == null) {
            System.out.println("product with this id doesn't exist.");
            return;
        }
        if (cart.getCartItems().containsKey(product)) {
            System.out.println("this product already exists in your cart. it's amount will be increased.");
            increaseProductInCart(product);
            return;
        }
        cart.getCartItems().put(product, 1);
        System.out.println("product added to your cart successfully");
    }

    public void increaseProductInCart(Product product) {
        if (product == null) {
            System.out.println("your cart doesn't contain product with this id.");
            return;
        }
        if (cart.getCartItems().containsKey(product)) {
            int num = cart.getCartItems().get(product);
            cart.getCartItems().put(product, ++num);
            System.out.println("product increased in your cart by one.");
        } else {
            System.out.println("your cart doesn't contain product with this id.");
        }
    }

    public void decreaseProductInCart(Product product) {
        if (product == null) {
            System.out.println("your cart doesn't contain product with his id");
            return;
        }
        if (cart.getCartItems().containsKey(product)) {
            if (cart.getCartItems().get(product) == 1) {
                cart.getCartItems().remove(product);
                System.out.println("product removed from your cart");
            } else {
                int num = cart.getCartItems().get(product);
                cart.getCartItems().put(product, --num);
                System.out.println("product decreased in your cart by one.");
            }
        } else {
            System.out.println("your cart doesn't contain product with this id.");
        }
    }

    public ArrayList<BuyLog> getOrderHistory() {
        return orderHistory;
    }

    public static LinkedList<Buyer> getAllBuyers() {
        return allBuyers;
    }

    @Override
    public void changeInfo(String field, String newValue) {
        if (field.equalsIgnoreCase("first name")) {
            setFirstName(newValue);
        } else if (field.equalsIgnoreCase("last name")) {
            setLastName(newValue);
        } else if (field.equalsIgnoreCase("email")) {
            if (newValue.matches("[a-zA-Z0-9_\\-\\.]+@(gmail|@yahoo)\\.com")) setEmail(newValue);
            else {
                System.out.println("invalid email format.");
                return;
            }
        } else if (field.equalsIgnoreCase("phone number")) {
            setPhoneNumber(newValue);
        } else if (field.equals("credit")) {
            try {
                setCredit(Float.parseFloat(newValue));
            } catch (NumberFormatException e) {
                System.out.println("invalid number format");
                return;
            }
        } else {
            System.out.println("invalid field");
            return;
        }
        System.out.println(field + " changed successfully!");
    }

    @Override
    public String toString() {
        return "role: Buyer" +
                "userName: " + username + '\n' +
                "firstName: " + firstName + '\n' +
                "lastName: " + lastName + '\n' +
                "credit" + credit + '\n' +
                "email: " + email + '\n' +
                "phoneNumber: " + phoneNumber;
    }
}
