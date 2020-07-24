package org.example;

import Model.DataLoader;
import Model.ExitThread;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class MarketServer {
    Socket socket;
    DataInputStream bankDataInputStream;
    DataOutputStream bankDataOutputStream;
    private MarketServer marketServer;

    public String marketAccount;

    public static void main(String[] args) throws IOException, ClassNotFoundException {
//        Runtime runtime=Runtime.getRuntime();
//        runtime.addShutdownHook(new ExitThread());

        // DataLoader.readData();

        MarketServer marketServer=new MarketServer();
        marketServer.socket =new Socket("127.0.0.1",2222);
        marketServer.bankDataInputStream=new DataInputStream(new BufferedInputStream(marketServer.socket.getInputStream()));
        marketServer.bankDataOutputStream=new DataOutputStream(new BufferedOutputStream(marketServer.socket.getOutputStream()));
        ServerSocket serverSocket=new ServerSocket(1234);
        marketServer.serverHandler(serverSocket);
    }

    public void serverHandler(ServerSocket serverSocket) throws IOException {
        while (true){
            System.out.println("waiting");
            Socket clientSocket=serverSocket.accept();
            System.out.println("connected");

            DataOutputStream dataOutputStream=new DataOutputStream(clientSocket.getOutputStream());
            DataInputStream dataInputStream=new DataInputStream(clientSocket.getInputStream());

            new ClientHandler(this,clientSocket,dataInputStream, dataOutputStream).start();
        }
    }

    public DataOutputStream getBankDataOutputStream() {
        return bankDataOutputStream;
    }

    public DataInputStream getBankDataInputStream() {
        return bankDataInputStream;
    }
}
