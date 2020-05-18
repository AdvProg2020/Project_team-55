import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

public class ExitThread extends Thread{

    @Override
    public void run() {
        try {
            FileWriter fileWriter=new FileWriter("buyer.txt");
            fileWriter.write("hello");
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
