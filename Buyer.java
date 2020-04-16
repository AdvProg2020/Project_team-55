package Model;

import java.util.ArrayList;
import java.util.HashMap;

public class Buyer  extends User {
    private Cart cart;

    String userName;
    String firstName;
    String lastName;
    String email;
    String phoneNumber;
    String password;
    private ArrayList<Product> arrayProduct=new ArrayList<>();
    private HashMap<Comments , Product> usersCommentsHashmap=new HashMap<>();

    public Buyer(String userName, String firstName, String lastName, String email, String phoneNumber, String password) {
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
    }
}
