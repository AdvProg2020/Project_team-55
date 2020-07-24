package Model;

import java.util.LinkedList;

public class Supporter extends User{
    private static LinkedList<Supporter> allSupporters=new LinkedList<>();

    public Supporter(String userName, String firstName, String lastName, String email, String phoneNumber, String password, String profile) {
        super(userName, firstName, lastName, email, phoneNumber, password, profile);
        allSupporters.add(this);
    }

    @Override
    public void changeInfo(String field, String newValue) {

    }

    public static LinkedList<Supporter> getAllSupporters() {
        return allSupporters;
    }


}
