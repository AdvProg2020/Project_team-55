package Model;

import java.util.ArrayList;

public abstract class User {
    protected static ArrayList<User> users = new ArrayList<>();
    protected String userName;
    protected String firstName;
    protected String lastName;
    protected String email;
    protected String phoneNumber;
    protected String password;
    private float credit;
    private ArrayList<Product> userLog;

    public static User getAccountByUserName(String account) {
        return null;
    }

    public static void removeUser(User user) {
        users.remove(user);
    }

    public static ArrayList<User> getUsers() {
        return users;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setCredit(float credit) {
        this.credit = credit;
    }

    public String getUserName() {
        return userName;
    }

    public abstract void changeInfo(String field, String newValue);
}
