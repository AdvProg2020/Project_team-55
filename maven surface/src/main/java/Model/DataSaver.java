package Model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;

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
        FileWriter buyerWriter = new FileWriter("./database/buyers.json");
        FileWriter sellerWriter = new FileWriter("./database/sellers.json");
        FileWriter managerWriter = new FileWriter("./database/managers.json");
        FileWriter mainManager = new FileWriter("./database/mainManager.json");
        for (User user : User.getUsers()) {
            if (user instanceof Buyer)
                buyerWriter.write(gson.toJson(user));
            else if (user instanceof Seller)
                sellerWriter.write(gson.toJson(user));
            else if (user instanceof Manager)
                managerWriter.write(gson.toJson(user));
        }
        mainManager.write(gson.toJson(Manager.getMainManager()));

        buyerWriter.close();
        sellerWriter.close();
        managerWriter.close();
        mainManager.close();
    }

    public void saveOffs() throws IOException {
        FileWriter offWriter=new FileWriter("./database/offs.json");
        for (Off off:Off.getOffArray()){
            offWriter.write(gson.toJson(off));
        }
        offWriter.close();
    }

    public void saveProducts() throws IOException {
        FileWriter productWriter=new FileWriter("./database/products.json");
        for (Product product:Product.getAllProducts()){
            productWriter.write(gson.toJson(product));
        }
        productWriter.close();
    }

    public void saveCategories() throws IOException {
        FileWriter categoryWriter=new FileWriter("./database/categories.json");
        for (Category category:Category.getAllCategories()){
            categoryWriter.write(gson.toJson(category));
        }
        categoryWriter.close();
    }

    public void saveDiscounts() throws IOException{
        FileWriter discountWriter=new FileWriter("./database/discounts.json");
        for (OffWithCode discount: OffWithCode.getAllDiscounts()){
            discountWriter.write(gson.toJson(discount));
        }
        discountWriter.close();
    }
    public void saveRequests() throws IOException{
        FileWriter productAddWriter=new FileWriter("./database/productAdditionRequests.json");
        FileWriter productEditWriter=new FileWriter("./database/productEditRequests.json");
        FileWriter productRemoveWriter=new FileWriter("./database/productRemovalRequests.json");
        FileWriter offAddWriter=new FileWriter("./database/offAdditionRequests.json");
        FileWriter offEditWriter=new FileWriter("./database/offEditRequests.json");
        for (Request request:Request.getAllRequests()){
            if (request instanceof ProductAdditionRequest){
                productAddWriter.write(gson.toJson(request));
            }else if (request instanceof ProductEditRequest){
                productEditWriter.write(gson.toJson(request));
            }else if (request instanceof ProductRemovalRequest){
                productRemoveWriter.write(gson.toJson(request));
            }else if (request instanceof OffAdditionRequest){
                offAddWriter.write(gson.toJson(request));
            }else if (request instanceof OffEditRequest){
                offEditWriter.write(gson.toJson(request));
            }
        }
        productAddWriter.close();
        productEditWriter.close();
        productRemoveWriter.close();
        offAddWriter.close();
        offEditWriter.close();
    }

    public void saveLogs() throws IOException{
        FileWriter sellWriter=new FileWriter("./database/sellLog.json");
        FileWriter buyWriter=new FileWriter("./database/buyLog.json");
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
        FileWriter scoreWriter=new FileWriter("./database/scores.json");
        scoreWriter.write(gson.toJson(Score.getInstance()));
        scoreWriter.close();
    }

}
