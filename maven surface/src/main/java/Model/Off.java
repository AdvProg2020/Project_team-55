package Model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;

public class Off {
    private static ArrayList<Off> offArray = new ArrayList<>();
    private String offId;
    private Seller seller;
    private ArrayList<Product> productsArray = new ArrayList<>();
    private LocalDateTime startDate;
    private LocalDateTime stopDate;
    private int offAmount;
    private boolean active;

    public Off(String offId, ArrayList<Product> productsArray, LocalDateTime startDate, LocalDateTime stopDate, int offAmount,Seller seller) {
        this.offId = offId;
        this.productsArray = productsArray;
        this.startDate = startDate;
        this.stopDate = stopDate;
        this.offAmount = offAmount;
        this.seller=seller;
        seller.getSellerOffs().add(this);
        active = false;
        offArray.add(this);
        for (Product product:productsArray){
            product.setAssignedOff(this);
        }
    }

    public static Off getOffById(String id) {
        for (Off off : offArray) {
            if (off.getOffId().equals(id)) {
                return off;
            }
        }
        return null;
    }

    public static ArrayList<Off> getOffArray() {
        return offArray;
    }

    public static void updateOffs() {
        Iterator iterator = offArray.iterator();
        while (iterator.hasNext()) {
            Off off = (Off) iterator.next();
            if (off.startDate.isBefore(LocalDateTime.now()) && off.stopDate.isAfter(LocalDateTime.now())) {
                off.active = true;
                off.getProductsArray().forEach(Product::setPriceAfterOff);
            }
            if (off.stopDate.isBefore(LocalDateTime.now())) {
                for (Product product : off.productsArray) {
                    product.setAssignedOff(null);
                    offArray.remove(off);
                }
            }

        }
    }

    public ArrayList<Product> getProductsArray() {
        return productsArray;
    }

    public String getOffId() {
        return offId;
    }

    public int getOffAmount() {
        return offAmount;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getStopDate() {
        return stopDate;
    }

    public boolean isActive() {
        return active;
    }

    public void setProductsArray(ArrayList<Product> productsArray) {
        this.productsArray = productsArray;
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

    @Override
    public String toString() {
        StringBuilder stringBuilder=new StringBuilder();
        stringBuilder.append(offAmount + "\\% off" + "\nfrom " + startDate + " to " + startDate + "\nincluding products:");
        for (Product product:this.productsArray){
            stringBuilder.append(product.getProductId()+" "+product.getSpecialAttributes().get("name"));
        }
        return stringBuilder.toString();
    }
}
