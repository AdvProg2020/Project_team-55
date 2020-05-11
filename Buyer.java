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
  //  private HashMap<Product , Integer> boughtProductHashmap=new HashMap<>();
    //private HashMap<Comments , Product> usersCommentsHashmap=new HashMap<>();

    public Buyer(String userName ,String password, String email, String firstName, String lastName, String phoneNumber) {
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;

        users.add(this);
    }

//tabe baraye  hashmap


}
