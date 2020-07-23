package Model;

import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import org.menu.UniqueAuction;

import java.io.DataInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Auction {
    private String id;
    private Product product;
    private HashMap<Buyer,Integer> proposals=new HashMap<>();
    private LocalDateTime startDate;
    private LocalDateTime stopDate;
    private static ArrayList<Auction> allAuctions=new ArrayList<>();
    private UniqueAuction auctionMenu;

    public Auction(Product product,LocalDateTime stopDate) throws IOException, ClassNotFoundException {
        Random random=new Random();
        while (getAuctionById(this.id=Integer.toString(random.nextInt(899999)+100000))!=null);
        this.product=product;
        this.startDate=LocalDateTime.now();
        this.stopDate=stopDate;
        this.auctionMenu=new UniqueAuction(new ScrollPane(),this,null);
        allAuctions.add(this);

        startCountDown();
    }

    public Product getProduct() {
        return product;
    }

    public LocalDateTime getStopDate() {
        return stopDate;
    }

    public String getFormattedStopDate(){
        return stopDate.format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public String getFormattedStartDate(){
        return startDate.format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
    }

    public static ArrayList<Auction> getAllAuctions() {
        return allAuctions;
    }

    public int getHighestProposal(){
     int highest=proposals.get(0);
     for (int proposal:proposals.values()){
         if (proposal>highest){
             highest=proposal;
         }
     }
     return highest;
    }

    public HashMap<Buyer, Integer> getProposals() {
        return proposals;
    }

    public String getId() {
        return id;
    }

    public static Auction getAuctionById(String id){
        for (Auction auction:allAuctions){
            if (id.equals(auction.id)){
                return auction;
            }
        }
        return null;
    }

    public void startCountDown(){
        Auction auction=this;
        new Thread(){

            @Override
            public void run() {
                while (true){
                    if (LocalDateTime.now().isAfter(auction.stopDate)){
                        allAuctions.remove(auction);
                        break;
                    }
                }
            }

        }.start();
    }

    public UniqueAuction getAuctionMenu(Scene previousScene) {
        this.auctionMenu.setPreviousPage(previousScene);
        return auctionMenu;
    }
}
