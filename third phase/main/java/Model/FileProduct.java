package Model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedList;

public class FileProduct extends Product{
    private LinkedList<Integer> fileBytes=new LinkedList<>();
    private String fileName;


    public FileProduct(String productId, float price, Category category, HashMap<String, String> specialAttributes, String explanation, Seller seller, String picture,String entry) throws IOException {
        super(productId, price, category, specialAttributes, explanation, seller, picture);
        this.fileName=entry.substring(entry.lastIndexOf("/"));
        getBytes(entry);
    }

    public void getBytes(String entry) throws IOException {
        InputStream inputStream=new FileInputStream(entry);
        int nextByte;
        while ((nextByte=inputStream.read())!=-1){
            fileBytes.add(nextByte);
        }
    }

    public String getFileName() {
        return fileName;
    }

    public LinkedList<Integer> getFileBytes() {
        return fileBytes;
    }

    public void setFileBytes(LinkedList<Integer> fileBytes) {
        this.fileBytes = fileBytes;
    }
}
