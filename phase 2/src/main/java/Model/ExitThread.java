package Model;

import java.io.IOException;

public class ExitThread extends Thread{

    @Override
    public void run() {
        System.out.println("please wait for a few moments ...");
        try {
           if (Manager.getMainManager()!=null)
            DataSaver.saveData();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("goodbye");

    }
}
