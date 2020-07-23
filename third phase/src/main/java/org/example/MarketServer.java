package org.example;

import Model.DataLoader;
import Model.ExitThread;
import com.google.gson.internal.$Gson$Preconditions;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class MarketServer {
   private Socket socket;
    private DataOutputStream bankOutPutStream;
    private DataInputStream bankInputStream;

    private String marketAccount;

    public static void main(String[] args)  {

//        Runtime runtime=Runtime.getRuntime();
//        runtime.addShutdownHook(new ExitThread());

        //DataLoader.readData();

        MarketServer marketServer=new MarketServer();

        try {
            marketServer.socket =new Socket("127.0.0.1",2222);
            marketServer.bankOutPutStream=new DataOutputStream(new BufferedOutputStream(marketServer.socket.getOutputStream()));
            marketServer.bankInputStream=new DataInputStream(new BufferedInputStream(marketServer.socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        ServerSocket serverSocket= null;
        try {
            serverSocket = new ServerSocket(1234);
            marketServer.serverHandler(serverSocket);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void serverHandler(ServerSocket serverSocket) throws IOException {
        while (true){
            System.out.println("waiting for connection");
            Socket clientSocket=serverSocket.accept();
            System.out.println("connected");

            DataOutputStream dataOutputStream=new DataOutputStream(clientSocket.getOutputStream());
            DataInputStream dataInputStream=new DataInputStream(clientSocket.getInputStream());

            new ClientHandler(this,clientSocket,dataInputStream,dataOutputStream).start();
        }
    }

    public DataInputStream getBankInputStream() {
        return bankInputStream;
    }

    public DataOutputStream getBankOutPutStream() {
        return bankOutPutStream;
    }
}
