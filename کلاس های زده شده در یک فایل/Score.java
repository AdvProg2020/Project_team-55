package Model;

import View.BuyerView;

import java.util.ArrayList;

public class Score {
    private User user;
    private ArrayList<Integer>allScores=new ArrayList<>();
    double finalScore;
    double average;
    private Product products;


    public void saveScore( Double score) {
        for(int i=0 ; i<allScores.size() ; i++){
            finalScore = finalScore + (allScores.get(i));
        }
        average=finalScore/allScores.size();
        System.out.println("The average score for this product is =" +  average );
    }



}