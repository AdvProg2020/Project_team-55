package Model;

import java.util.ArrayList;

public class Manager extends User {
    private ArrayList<Manager> managers;

    public Manager(String userName, String firstName, String lastName, String email, String phoneNumber, String password) {
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
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
