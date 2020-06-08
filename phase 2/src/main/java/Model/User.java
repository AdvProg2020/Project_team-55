package Model;

import javafx.beans.property.FloatProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.CheckBox;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;

public abstract class User implements Serializable {
    protected static LinkedList<User> users = new LinkedList<>();
    private static User activeUser;
    protected String username;
    protected String firstName;
    protected String lastName;
    protected String email;
    protected String phoneNumber;
    protected String password;
    protected float credit;
    protected CheckBox select;

    public User(String userName, String firstName, String lastName, String email, String phoneNumber, String password){
        this.username=userName;
        this.firstName=firstName;
        this.lastName=lastName;
        this.email=email;
        this.phoneNumber=phoneNumber;
        this.password=password;
        select=new CheckBox();
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

    public void setCredit(float credit) {
        this.credit = credit;
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

    public float getCredit() {
        return credit;
    }

    public CheckBox getSelect() {
        return select;
    }

    public void setSelect(CheckBox select) {
        this.select = select;
    }

    public static User getActiveUser() {
        return activeUser;
    }

    public static void setActiveUser(User activeUser) {
        User.activeUser = activeUser;
    }
}
