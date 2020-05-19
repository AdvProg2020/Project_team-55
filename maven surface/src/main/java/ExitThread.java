import Model.DataSaver;
import Model.User;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

public class ExitThread extends Thread{

    @Override
    public void run() {
        System.out.println("please wait for a few moments ...");
        try {
            DataSaver.saveData();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("goodbye");

    }
}
