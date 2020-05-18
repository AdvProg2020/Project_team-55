package Model;

import java.sql.Statement;
import java.util.ArrayList;

public class Manager extends User {
    private static Manager mainManager;
    private ArrayList<Manager> subManagers;

    public Manager(String userName, String firstName, String lastName, String email, String phoneNumber, String password) {
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        if (mainManager==null)mainManager=this;
        else subManagers.add(this);
    }

    public static Manager getMainManager() {
        return mainManager;
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
            setCredit(Float.parseFloat(newValue));
        } else {
            System.out.println("invalid field");
            return;
        }
        System.out.println(field + " changed successfully!");
    }

    @Override
    public String toString() {
        return "role: Manager" +
                "userName: " + userName + '\n' +
                "firstName: " + firstName + '\n' +
                "lastName: " + lastName + '\n' +
                "email: " + email + '\n' +
                "phoneNumber: " + phoneNumber;
    }
}
