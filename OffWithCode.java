package Model;

import java.util.ArrayList;
import java.util.Date;

public class OffWithCode {
    private String offCode;
    private Date startDate;
    private Date stopDate;
private int offAmount;
private int numberOfUsersOfCode;
ArrayList<User> applyingAccounts=new ArrayList<>();


    public OffWithCode(String offCode, Date startDate, Date stopDate, int offAmount, int numberOfUsersOfCode) {
        this.offCode = offCode;
        this.startDate = startDate;
        this.stopDate = stopDate;
        this.offAmount = offAmount;
        this.numberOfUsersOfCode = numberOfUsersOfCode;
    }

    public void  addAccountToDiscount(User user){

}

public void exitAccount(User user){

}


public static User offWithCode (String id){
    return null;
}


    public static User getOffByCode (String id){
        return null;
    }


    public static User giveOffCodeToSb(User user){
        return null;
    }

}
