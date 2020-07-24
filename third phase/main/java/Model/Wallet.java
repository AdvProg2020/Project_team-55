package Model;

public class Wallet {
    private  int amount;
    private User owner;
    private static int leastAmount;
    private static int freelance;

    public Wallet(int amount,User owner){
        this.amount=amount;
        this.owner=owner;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public User getOwner() {
        return owner;
    }

    public static int getLeastAmount() {
        return leastAmount;
    }

    public static void setLeastAmount(int leastAmount) {
        Wallet.leastAmount = leastAmount;
    }

    public static int getFreelance() {
        return freelance;
    }

    public static void setFreelance(int freelance) {
        Wallet.freelance = freelance;
    }
}
