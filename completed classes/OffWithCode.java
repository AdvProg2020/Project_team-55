package Model;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class OffWithCode {
    private static final ArrayList<OffWithCode> allDiscounts = new ArrayList<>();
    ArrayList<User> applyingAccounts = new ArrayList<>();
    private String offCode;
    private LocalDateTime startDate;
    private LocalDateTime stopDate;
    private int offAmount;
    private double maxAmount;
    private int numberOfUsesOfCode;


    public OffWithCode(String offCode, LocalDateTime startDate, LocalDateTime stopDate, int offAmount, double maxAmount, int numberOfUsesOfCode, ArrayList applyingAccounts) {
        this.offCode = offCode;
        this.startDate = startDate;
        this.stopDate = stopDate;
        this.offAmount = offAmount;
        this.maxAmount = maxAmount;
        this.numberOfUsesOfCode = numberOfUsesOfCode;
        this.applyingAccounts.addAll(applyingAccounts);
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

        applyingAccounts.add(user);
    }

    public void removeUserFromDiscount(User user) {
        applyingAccounts.remove(user);
    }

    public ArrayList<User> getApplyingAccounts() {
        return applyingAccounts;
    }

    public void setOffCode(String offCode) {

        this.offCode = offCode;
    }

    public LocalDateTime getStartDate() {
        return startDate;
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

    public void setMaxAmount(double maxAmount) {
        this.maxAmount = maxAmount;
    }

    public void setNumberOfUsesOfCode(int numberOfUsesOfCode) {

        this.numberOfUsesOfCode = numberOfUsesOfCode;
    }

    @Override
    public String toString() {
        return "discount:" +
                "\noff code=" + offCode +
                "\nstart date=" + startDate +
                "\nend date=" + stopDate +
                "\ndiscount percent=" + offAmount +
                "\nnumber of uses of code=" + numberOfUsesOfCode +
                "\napplying accounts=" + applyingAccounts;
    }

    public void editDiscountCode(String field, String newValue) {
        if (field.equalsIgnoreCase("start date")) {
            // TODO: do something about the date
        } else if (field.equalsIgnoreCase("end date")) {

        } else if (field.equalsIgnoreCase("discount percent")) {
            setOffAmount(Integer.parseInt(newValue));
        } else if (field.equalsIgnoreCase("number of uses of code")) {
            setNumberOfUsesOfCode(Integer.parseInt(newValue));
        }
        System.out.println(field + " changed successfully");
    }
}
