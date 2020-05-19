package Model;

import javax.jws.soap.SOAPBinding;
import java.io.*;
import java.util.ArrayList;

public abstract class User implements Serializable {
    protected static ArrayList<User> users = new ArrayList<>();
    protected String userName;
    protected String firstName;
    protected String lastName;
    protected String email;
    protected String phoneNumber;
    protected String password;
    protected float credit;

    public static User getAccountByUserName(String account) {
        for (User searching : users ){
            if(searching.userName.equals(account)) return searching;
        }
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

    public static void saveData() throws IOException {
        File file=new File("users.dat");
        FileOutputStream outputStream=new FileOutputStream(file);
        ObjectOutputStream objectOutputStream=new ObjectOutputStream(outputStream);
        for (User user:users){
            objectOutputStream.writeObject(user);
        }
    }

}
