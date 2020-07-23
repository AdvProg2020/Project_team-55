package Model;

import java.time.LocalDateTime;

public class SellerAdditionRequest extends Request{

    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String password;
    private String factory;
    private float credit;

    public SellerAdditionRequest(String factory, String userName, String firstName, String lastName, String email, String phoneNumber, String password, float credit){
        this.username=userName;
        this.firstName=firstName;
        this.lastName=lastName;
        this.email=email;
        this.password=password;
        this.phoneNumber=phoneNumber;
        this.factory=factory;
        this.credit=credit;

        this.sender=null;
        this.dateTime= LocalDateTime.now();
        allRequests.add(this);
    }

    public String getUsername() {
        return username;
    }

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

    public String getFactory() {
        return factory;
    }

    public float getCredit() {
        return credit;
    }

    @Override
    public void acceptRequest() {
        //new Seller(factory,username,firstName,lastName,email,phoneNumber,password,credit);
        allRequests.remove(this);
    }

    @Override
    public void declineRequest() {
        allRequests.remove(this);
    }
}
