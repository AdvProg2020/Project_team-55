package Model;

import java.util.ArrayList;

public class Seller extends User {
    private String factory;
    private  ArrayList<Product> arrayProduct = new ArrayList<Product>();
    private ArrayList<Off> sellerOffs = new ArrayList<>();
    private ArrayList<SellLog> sellHistory = new ArrayList<SellLog>();

    public Seller(String factory, String userName, String firstName, String lastName, String email, String phoneNumber, String password, float credit) {
        this.factory = factory;
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.credit = credit;
        users.add(this);
    }

    public String getFactory() {
        return factory;
    }

    public void setFactory(String factory) {
        this.factory = factory;
    }

    public ArrayList<Product> getArrayProduct() {
        return arrayProduct;
    }

    public ArrayList<SellLog> getSellHistory() {
        return sellHistory;
    }

    public ArrayList<Off> getSellerOffs() {
        return sellerOffs;
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
            }catch (NumberFormatException e){
                System.out.println("invalid number format");
                return;
            }
        } else if (field.equalsIgnoreCase("company")) {
            setFactory(newValue);
        } else {
            System.out.println("invalid field");
            return;
        }
        System.out.println(field + " changed successfully!");
    }

    @Override
    public String toString() {
        return "role: Seller" +
                "userName: " + userName + '\n' +
                "firstName: " + firstName + '\n' +
                "lastName: " + lastName + '\n' +
                "credit" + credit + '\n' +
                "email: " + email + '\n' +
                "phoneNumber: " + phoneNumber + '\n' +
                "Company: " + factory;
    }
}
