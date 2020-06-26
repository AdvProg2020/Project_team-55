package Model;

import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.LinkedList;

public class Manager extends User {
    private static Manager mainManager;
    private static LinkedList<Manager> subManagers=new LinkedList<>();

    public Manager(String userName, String firstName, String lastName, String email, String phoneNumber, String password, String profile) {
        super(userName,firstName,lastName,email,phoneNumber,password,profile);
        if (mainManager==null)mainManager=this;
        else subManagers.add(this);
    }

    public static Manager getMainManager() {
        return mainManager;
    }

    public static void setMainManager(Manager mainManager) {
        Manager.mainManager = mainManager;
    }

    public static LinkedList<Manager> getSubManagers() {
        return subManagers;
    }

    @Override
    public void changeInfo(String field, String newValue) {
        if (field.equalsIgnoreCase("first name")) {
            setFirstName(newValue);
        } else if (field.equalsIgnoreCase("last name")) {
            setLastName(newValue);
        } else if (field.equalsIgnoreCase("email")) {
            if (newValue.toString().matches("[a-zA-Z0-9_\\-\\.]+@(gmail|@yahoo)\\.com")) setEmail(newValue);
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
                "userName: " + username + '\n' +
                "firstName: " + firstName + '\n' +
                "lastName: " + lastName + '\n' +
                "email: " + email + '\n' +
                "phoneNumber: " + phoneNumber;
    }
}
