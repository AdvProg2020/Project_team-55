package Model;

import javafx.beans.property.FloatProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.CheckBox;
import javafx.scene.image.Image;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;

public abstract class User implements Serializable {
    protected static LinkedList<User> users = new LinkedList<>();
    protected String username;
    protected String firstName;
    protected String lastName;
    protected String email;
    protected String phoneNumber;
    protected String password;
    protected Wallet credit;
    protected String profile;
    protected String accountId;
    protected boolean loggedIn;

    public User(String userName, String firstName, String lastName, String email, String phoneNumber, String password,String profile){
        this.username=userName;
        this.firstName=firstName;
        this.lastName=lastName;
        this.email=email;
        this.phoneNumber=phoneNumber;
        this.password=password;
        this.profile=profile;
        users.add(this);
    }

    public static User getAccountByUserName(String account) {
        for (User searching : users ){
            if(searching.username.equals(account)) return searching;
        }
        return null;
    }

    public static void removeUser(User user) {
        users.remove(user);
    }

    public static LinkedList<User> getUsers() {
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

    public void setCredit(int credit) {
        this.credit.setAmount(credit);
    }

    public String getUsername() {
        return username;
    }

    public abstract void changeInfo(String field, String newValue);

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public int getCredit() {
        return credit.getAmount();
    }

    public Image getProfile() {
        return new Image(profile);
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getRole(){
        return getClass().getSimpleName();
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }
}
