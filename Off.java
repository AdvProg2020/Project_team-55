package Model;

import java.util.ArrayList;
import java.util.Date;

public class Off {
    private String offId;
    private ArrayList<Product> productsArray =new ArrayList<>();
    private static ArrayList <Off> offArray =new ArrayList<>();
    private Date startDate;
    private Date stopDate;
    private int offAmount;

    public Off(String offId, ArrayList<Product> productsArray, Date startDate, Date stopDate, int offAmount) {
        this.offId = offId;
        this.productsArray = productsArray;
        this.startDate = startDate;
        this.stopDate = stopDate;
        this.offAmount = offAmount;
    }


    public static Off offFunction(String id){
        return null;
    }

    public static Off getOffById(String id){
        return null;
    }




}
