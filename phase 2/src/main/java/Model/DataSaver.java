package Model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.omg.CORBA.MARSHAL;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class DataSaver {
    private GsonBuilder builder = new GsonBuilder();
    private Gson gson = builder.create();

    public static void saveData() throws IOException {
        DataSaver saver=new DataSaver();

        saver.saveUsers();
        saver.saveProducts();
        saver.saveCategories();
        saver.saveOffs();
        saver.saveDiscounts();
        saver.saveRequests();
        saver.saveLogs();
        saver.saveScores();
    }

    public void saveUsers() throws IOException{
        FileWriter buyerWriter = new FileWriter("buyers.json");
        FileWriter sellerWriter = new FileWriter("sellers.json");
        FileWriter managerWriter = new FileWriter("managers.json");
        FileWriter mainManager = new FileWriter("mainManager.json");

        for (int i=0;i<=0;i++){
            mainManager.write(gson.toJson(Manager.getMainManager()));
        }

        for (Buyer buyer:Buyer.getAllBuyers())
            buyerWriter.write(gson.toJson(buyer));

        for (Seller seller:Seller.getAllSellers())
            sellerWriter.write(gson.toJson(seller));

        for (Manager manager: Manager.getSubManagers())
            managerWriter.write(gson.toJson(manager));



        buyerWriter.close();
        sellerWriter.close();
        managerWriter.close();
        mainManager.close();
    }

    public void saveOffs() throws IOException {
        FileWriter offWriter=new FileWriter("offs.json");
        for (Off off:Off.getOffArray()){
            offWriter.write(gson.toJson(off));
        }
        offWriter.close();
    }

    public void saveProducts() throws IOException {
        FileWriter productWriter=new FileWriter("products.json");
        for (Product product:Product.getAllProducts()){
            productWriter.write(gson.toJson(product));
        }
        productWriter.close();
    }

    public void saveCategories() throws IOException {
        FileWriter categoryWriter=new FileWriter("categories.json");
        for (Category category:Category.getAllCategories()){
            categoryWriter.write(gson.toJson(category));
        }
        categoryWriter.close();
    }

    public void saveDiscounts() throws IOException{
        FileWriter discountWriter=new FileWriter("discounts.json");
        for (OffWithCode discount: OffWithCode.getAllDiscounts()){
            discountWriter.write(gson.toJson(discount));
        }
        discountWriter.close();
    }
    public void saveRequests() throws IOException{
        FileWriter productAddWriter=new FileWriter("productAdditionRequests.json");
        FileWriter productEditWriter=new FileWriter("productEditRequests.json");
        FileWriter offAddWriter=new FileWriter("offAdditionRequests.json");
        FileWriter offEditWriter=new FileWriter("offEditRequests.json");
        for (Request request:Request.getAllRequests()){
            if (request instanceof ProductAdditionRequest){
                productAddWriter.write(gson.toJson(request));
            }else if (request instanceof ProductEditRequest){
                productEditWriter.write(gson.toJson(request));
            }else if (request instanceof OffAdditionRequest){
                offAddWriter.write(gson.toJson(request));
            }else if (request instanceof OffEditRequest){
                offEditWriter.write(gson.toJson(request));
            }
        }
        productAddWriter.close();
        productEditWriter.close();
        offAddWriter.close();
        offEditWriter.close();
    }

    public void saveLogs() throws IOException{
        FileWriter sellWriter=new FileWriter("sellLog.json");
        FileWriter buyWriter=new FileWriter("buyLog.json");
        for (SellLog log:SellLog.getAllSales()){
            sellWriter.write(gson.toJson(log));
        }
        for (BuyLog log:BuyLog.getAllSales()){
            buyWriter.write(gson.toJson(log));
        }
        sellWriter.close();
        buyWriter.close();
    }

    public void saveScores() throws IOException{
        FileWriter scoreWriter=new FileWriter("scores.json");
        scoreWriter.write(gson.toJson(Score.getInstance()));
        scoreWriter.close();
    }

}
