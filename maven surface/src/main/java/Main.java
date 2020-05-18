import View.LoginRegister;
import View.MainPageView;

import java.io.*;

public class Main {
    public static void main(String[] args) throws StackOverflowError {
        Runtime runtime=Runtime.getRuntime();
        runtime.addShutdownHook(new ExitThread());
        MainPageView mainPage=MainPageView.getInstance();
        mainPage.enterMainPage(null);
    }

    public void instantiate(){

    }
}
