package Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Score {
    private static Score score;
    private HashMap<Product,ArrayList<Integer>> productRates=new HashMap<>();

    public static Score getInstance(){
        if (score==null){
            return score=new Score();
        }else
            return score;
    }

    public void rateProduct(Product product,int rate){
        if (!productRates.containsKey(product)) {
            productRates.put(product, new ArrayList<Integer>());
        }
        productRates.get(product).add(rate);
        updateRates();
    }

    private void updateRates(){
        int sum=0;
        for (Product product:productRates.keySet()){
            for (int rate:productRates.get(product)){
                sum+=rate;
            }
            product.setAverageScore(sum/productRates.get(product).size());
        }
    }
}
