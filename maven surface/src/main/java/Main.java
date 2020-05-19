import Model.DataLoader;
import Model.User;
import View.LoginRegister;
import View.MainPageView;

import java.io.*;

public class Main {
    public static void main(String[] args) throws  IOException {
        Runtime runtime=Runtime.getRuntime();
        runtime.addShutdownHook(new ExitThread());
        System.out.println("please wait for a few moments...");
        DataLoader.readData();
        MainPageView mainPage=MainPageView.getInstance();
        mainPage.enterMainPage(null);
    }

    public void instantiate(){

    }
}
