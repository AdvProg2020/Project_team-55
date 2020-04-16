package Model;

import java.util.ArrayList;

public class Manager extends User{
    private ArrayList<Manager> managers;



    String userName;
    String firstName;
    String lastName;
    String email;
    String phoneNumber;
    String password;
    private ArrayList<Product> arrayProduct=new ArrayList<>();

    public Manager(String userName, String firstName, String lastName, String email, String phoneNumber, String password) {
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
    }

}
