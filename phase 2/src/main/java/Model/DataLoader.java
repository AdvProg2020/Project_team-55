package Model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.scene.control.ScrollPane;
import org.example.App;
import org.menu.BuyerPanel;
import org.menu.MainPage;
import org.menu.RegisterMenu;

import javax.jws.soap.SOAPBinding;
import java.io.*;

public class DataLoader {
    private GsonBuilder builder=new GsonBuilder();
    private Gson gson=builder.create();

    public static void readData() throws IOException{
        File dir=new File("mainManager.json");
        if (dir.exists()) {
            DataLoader loader = new DataLoader();
            loader.readUsers();
            loader.readProducts();
            loader.readCategories();
            loader.readOffs();
            loader.readDiscounts();
            loader.readRequests();
            loader.readLogs();
            loader.readScore();
            App.getMainStage().setScene(new MainPage(new ScrollPane()));
        }else {
            App.getMainStage().setScene(new RegisterMenu(new ScrollPane(),null).submitManager());
           // App.getMainStage().setScene(new BuyerPanel(new ScrollPane()));
        }
    }


    private void readUsers() throws IOException {
        BufferedReader buyerReader=new BufferedReader(new FileReader("buyers.json"));
        BufferedReader sellerReader=new BufferedReader(new FileReader("sellers.json"));
        BufferedReader managerReader=new BufferedReader(new FileReader("managers.json"));
        BufferedReader mainManager=new BufferedReader(new FileReader("mainManager.json"));
        Buyer buyer;Seller seller;Manager manager;

        while ((buyer=gson.fromJson(buyerReader,Buyer.class))!=null){
            Buyer.getAllBuyers().add(buyer);
                User.getUsers().add(buyer);
        }
        while ((seller=gson.fromJson(sellerReader,Seller.class))!=null){
            Seller.getAllSellers().add(seller);
            User.getUsers().add(seller);
        }
        while ((manager=gson.fromJson(managerReader,Manager.class))!=null){
            Manager.getSubManagers().add(manager);
            User.getUsers().add(manager);
        }

        Manager.setMainManager(gson.fromJson(mainManager,Manager.class));
        User.getUsers().add(Manager.getMainManager());

        buyerReader.close();
        sellerReader.close();
        managerReader.close();
        mainManager.close();
    }

    private void readProducts() throws IOException{
        BufferedReader productReader=new BufferedReader(new FileReader("products.json"));
        Product product;
        while ((product=gson.fromJson(productReader,Product.class))!=null){
            Product.getAllProducts().add(product);
        }
        productReader.close();
    }

    private void readCategories() throws IOException{
        BufferedReader categoryReader=new BufferedReader(new FileReader("categories.json"));
        Category category;
        while ((category=gson.fromJson(categoryReader,Category.class))!=null){
            Category.getAllCategories().add(category);
        }
        categoryReader.close();
    }

    private void readOffs() throws IOException{
        BufferedReader offReader=new BufferedReader(new FileReader("offs.json"));
        Off off;
        while ((off=gson.fromJson(offReader,Off.class))!=null){
            Off.getOffArray().add(off);
            off.startCountdown();
        }
        offReader.close();
    }

    private void readDiscounts() throws IOException{
        BufferedReader discountReader= new BufferedReader(new FileReader("discounts.json"));
        OffWithCode discount;
        while ((discount=gson.fromJson(discountReader,OffWithCode.class))!=null){
            OffWithCode.getAllDiscounts().add(discount);
            discount.startCountdown();
        }
        discountReader.close();
    }

    private void readRequests() throws IOException{
        BufferedReader productAddReader=new BufferedReader(new FileReader("productAdditionRequests.json"));
        ProductAdditionRequest PARequest;
        while ((PARequest=gson.fromJson(productAddReader,ProductAdditionRequest.class))!=null){
            Request.getAllRequests().add(PARequest);
        }
        productAddReader.close();
        BufferedReader productEditReader=new BufferedReader(new FileReader("productEditRequests.json"));
        ProductEditRequest PERequest;
        while ((PERequest=gson.fromJson(productEditReader,ProductEditRequest.class))!=null){
            Request.getAllRequests().add(PERequest);
        }
        productEditReader.close();
        BufferedReader offAddReader=new BufferedReader(new FileReader("offAdditionRequests.json"));
        OffAdditionRequest OARequest;
        while ((OARequest=gson.fromJson(offAddReader,OffAdditionRequest.class))!=null){
            Request.getAllRequests().add(OARequest);
        }
        offAddReader.close();
        BufferedReader offEditReader=new BufferedReader(new FileReader("offEditRequests.json"));
        OffEditRequest OERequest;
        while ((OERequest=gson.fromJson(offEditReader,OffEditRequest.class))!=null){
            Request.getAllRequests().add(OERequest);
        }
        offEditReader.close();

    }

    private void readLogs() throws IOException{
        BufferedReader sellReader=new BufferedReader(new FileReader("sellLog.json"));
        SellLog sellLog;
        while ((sellLog=gson.fromJson(sellReader,SellLog.class))!=null){
            SellLog.getAllSales().add(sellLog);
        }
        sellReader.close();
        BufferedReader buyReader=new BufferedReader(new FileReader("buyLog.json"));
        BuyLog buyLog;
        while ((buyLog=gson.fromJson(buyReader,BuyLog.class))!=null){
            BuyLog.getAllSales().add(buyLog);
        }
        buyReader.close();
    }

    private void readScore() throws IOException{
        BufferedReader score=new BufferedReader(new FileReader("scores.json"));
        Score.setScore(gson.fromJson(score,Score.class));
        score.close();
    }

}
