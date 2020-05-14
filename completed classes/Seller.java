package Model;

import java.util.ArrayList;

public class Seller extends User {
    private String factory;
    private final ArrayList<Product> arrayProduct = new ArrayList<Product>();
    private final ArrayList<SellLog> sellHistory = new ArrayList<SellLog>();

    public Seller(String factory, String userName, String firstName, String lastName, String email, String phoneNumber, String password) {
        this.factory = factory;
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
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

    @Override
    public void changeInfo(String field, String newValue) {
        if (field.equalsIgnoreCase("first name")) {
            setFirstName(newValue);
        } else if (field.equalsIgnoreCase("last name")) {
            setLastName(newValue);
        } else if (field.equalsIgnoreCase("email")) {
            setEmail(newValue);
        } else if (field.equalsIgnoreCase("phone number")) {
            setPhoneNumber(newValue);
        } else if (field.equals("credit")) {
            setCredit(Float.parseFloat(newValue));
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
                "email: " + email + '\n' +
                "phoneNumber: " + phoneNumber + '\n' +
                "Company: " + factory;
    }
}
