package Model;

import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.LinkedList;

public class Buyer extends User {
    private Cart cart;
    private ArrayList<BuyLog> orderHistory = new ArrayList<BuyLog>();
    private static final LinkedList<Buyer> allBuyers = new LinkedList<>();

    public Buyer(String userName, String firstName, String lastName, String email, String phoneNumber, String password, float credit, String profile) {
        super(userName, firstName, lastName, email, phoneNumber, password,profile);
        this.credit = credit;
        this.cart=new Cart(this);
        allBuyers.add(this);
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public void addProductToCart(Product product) {
        cart.getCartItems().add(new CartItem(product));
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
