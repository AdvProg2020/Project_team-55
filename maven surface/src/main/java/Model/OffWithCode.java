package Model;

import javax.jws.soap.SOAPBinding;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

public class OffWithCode {
    private static ArrayList<OffWithCode> allDiscounts = new ArrayList<>();
    private HashMap<Buyer, Integer> applyingAccounts = new HashMap<>();
    private String offCode;
    private LocalDateTime startDate;
    private LocalDateTime stopDate;
    private int offAmount;
    private float maxAmount;
    private int numberOfUsesOfCode;
    private boolean active = false;


    public OffWithCode(String offCode, LocalDateTime startDate, LocalDateTime stopDate, int offAmount, float maxAmount, int numberOfUsesOfCode, ArrayList<Buyer> applyingAccounts) {
        this.offCode = offCode;
        this.startDate = startDate;
        this.stopDate = stopDate;
        this.offAmount = offAmount;
        this.maxAmount = maxAmount;
        this.numberOfUsesOfCode = numberOfUsesOfCode;
        for (Buyer buyer : applyingAccounts) {
            this.applyingAccounts.put(buyer, numberOfUsesOfCode);
        }
    }


    public static OffWithCode getOffByCode(String id) {
        for (OffWithCode discount : allDiscounts) {
            if (discount.offCode.equals(id)) {
                return discount;
            }
        }
        return null;
    }

    public static ArrayList<OffWithCode> getAllDiscounts() {
        return allDiscounts;
    }

    public static void deleteDiscount(OffWithCode discount) {

        allDiscounts.remove(discount);
    }

    public void addUserToDiscount(User user) {

        applyingAccounts.put((Buyer) user, numberOfUsesOfCode);
    }

    public void removeUserFromDiscount(User user) {
        applyingAccounts.remove(user);
    }

    public HashMap<Buyer, Integer> getApplyingAccounts() {
        return applyingAccounts;
    }

    public void setOffCode(String offCode) {

        this.offCode = offCode;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getStopDate() {
        return stopDate;
    }

    public int getOffAmount() {
        return offAmount;
    }

    public String getOffCode() {
        return offCode;
    }

    public float getMaxAmount() {
        return maxAmount;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setStartDate(LocalDateTime startDate) {

        this.startDate = startDate;
    }

    public void setStopDate(LocalDateTime stopDate) {

        this.stopDate = stopDate;
    }

    public void setOffAmount(int offAmount) {

        this.offAmount = offAmount;
    }

    public void setMaxAmount(float maxAmount) {
        this.maxAmount = maxAmount;
    }

    public void setNumberOfUsesOfCode(int numberOfUsesOfCode) {
        this.numberOfUsesOfCode = numberOfUsesOfCode;
        for (Buyer buyer:applyingAccounts.keySet()){
            int remain=applyingAccounts.get(buyer)+numberOfUsesOfCode;
            applyingAccounts.put(buyer,remain);
        }
    }

    public void useByUser(Buyer buyer){
        this.applyingAccounts.put(buyer,applyingAccounts.get(buyer)-1);
    }

    public static void updateDiscounts() {
        for (OffWithCode discount : allDiscounts) {
            discount.active = false;
            if (discount.startDate.isBefore(LocalDateTime.now()) && discount.stopDate.isAfter(LocalDateTime.now()))
                discount.active = true;
        }
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("discount:" +
                "\noff code=" + offCode +
                "\nstart date=" + startDate +
                "\nend date=" + stopDate +
                "\ndiscount percent=" + offAmount +
                "\nnumber of uses of code=" + numberOfUsesOfCode +
                "\napplying accounts=");
        for (Buyer buyer:applyingAccounts.keySet()){
            stringBuilder.append('\n'+buyer.userName);
        }
        return stringBuilder.toString();
    }

}
