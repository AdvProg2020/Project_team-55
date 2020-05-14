package Model;

import java.util.ArrayList;


public class Buyer  extends User {

    String userName;
    String firstName;
    String lastName;
    String email;
    String phoneNumber;
    String password;

    public  ArrayList<Product> showProductIdArray = new ArrayList<>();

    public void setShowProductIdArray(ArrayList<Product> showProductIdArray) {
        this.showProductIdArray = showProductIdArray;
    }

    public ArrayList<Product> getShowProductIdArray() {
        return showProductIdArray;
    }

    public Buyer(String userName , String password, String email, String firstName, String lastName, String phoneNumber) {
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;

        users.add(this);
    }

    @Override
    public String toString() {
        return
                "showProductIdArray=" + showProductIdArray ;
    }

}
