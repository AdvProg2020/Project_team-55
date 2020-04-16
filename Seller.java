package Model;

import java.util.ArrayList;

public class Seller extends User {
    private String factory;
    String userName;
    String firstName;
     String lastName;
   String email;
     String phoneNumber;
    String password;
    private ArrayList<Product> arrayProduct=new ArrayList<>();

    public Seller(String factory, String userName, String firstName, String lastName, String email, String phoneNumber, String password) {
        this.factory = factory;
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
    }
}
