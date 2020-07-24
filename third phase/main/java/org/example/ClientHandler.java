package org.example;

import Model.*;

import java.io.*;
import java.net.Socket;

public class ClientHandler extends Thread {
    private Socket socket;
    private MarketServer marketServer;
    private DataOutputStream dataOutputStream;
    private DataInputStream dataInputStream;
    private ObjectOutputStream objectOutputStream;
    private User activeUser;
    private String message;
    private String token;

    public ClientHandler(MarketServer server, Socket socket, DataInputStream dataInputStream, DataOutputStream dataOutputStream) throws IOException {
        this.marketServer = server;
        this.socket = socket;
        this.dataInputStream = dataInputStream;
        this.dataOutputStream = dataOutputStream;

        objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
    }

    @Override
    public void run() {
        while (true) {
            try {
                message = dataInputStream.readUTF();

                if (message.startsWith("login")) {
                    loginUser(message.split("\\s")[1], message.split("\\s")[2]);
                } else if (message.startsWith("signUp")) {
                    message = message.substring(7);
                    signUpUser(message.split(", "));
                } else if (message.startsWith("mainManager")) {
                    message = message.substring(12);
                    submitMainManager(message.split(", "));
                } else if (message.equals("get active user")) {
                    objectOutputStream.writeObject(activeUser);
                    objectOutputStream.flush();
                } else if (message.equals("log out")) {
                    activeUser = null;
                } else if (message.startsWith("submitSupporter")) {
                    submitSupporter(message.substring(16).split(", "));
                } else if (message.startsWith("submitManager")) {
                    submitManager(message.substring(14).split(", "));
                } else if (message.startsWith("withdraw")) {
                    withdraw(message.substring(9));
                } else if (message.startsWith("charge")) {
                    charge(message.substring(7));
                } else if (message.startsWith("pay_by_wallet")) {
                    payByWallet(message.substring(14).split(" "));
                }else if (message.startsWith("pay_by_account")){
                    payByAccount(message.substring(15).split(" "));
                }else if (message.equals("log out")){
                    logout();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    public void loginUser(String username, String password) throws IOException {
        for (User user : User.getUsers()) {
            if (user.getUsername().equals(username)) {
                if (user.getPassword().equals(password)) {
                    activeUser = user;
                    activeUser.setLoggedIn(true);
                    dataOutputStream.writeUTF("succeed");
                    dataOutputStream.flush();
                    marketServer.getBankDataOutputStream().writeUTF("get_token " + activeUser.getUsername() + " " + activeUser.getPassword());
                    marketServer.getBankDataOutputStream().flush();
                    return;
                } else {
                    dataOutputStream.writeUTF("failed");
                    dataOutputStream.flush();
                    return;
                }
            }
        }
        dataOutputStream.writeUTF("failed");
        dataOutputStream.flush();
    }

    public void signUpUser(String[] info) throws IOException {
        if (info[0].equals("buyer")) {
            activeUser = new Buyer(info[1], info[2], info[3], info[4], info[5], info[6], Integer.parseInt(info[7]), info[8]);
        } else {
            activeUser = new Seller(info[9], info[1], info[2], info[3], info[4], info[5], info[6], Integer.parseInt(info[7]), info[8]);
        }
        marketServer.getBankDataOutputStream().writeUTF("create_account " + info[2] + " " + info[3] + " " + info[1] + " " + info[6] + " " + info[6]);
        marketServer.getBankDataOutputStream().flush();
    }

    public void submitMainManager(String[] info) {
        new Manager(info[0], info[1], info[2], info[3], info[4], info[5], info[6]);
        Wallet.setLeastAmount(Integer.parseInt(info[7]));
        Wallet.setFreelance(Integer.parseInt(info[8]));
    }

    public void submitSupporter(String[] info) {
        new Supporter(info[0], info[1], info[2], info[3], info[4], info[5], info[6]);
    }

    public void submitManager(String[] info) {
        new Manager(info[0], info[1], info[2], info[3], info[4], info[5], info[6]);
    }

    public void withdraw(String amount) throws IOException {
        if (activeUser.getCredit() >= Integer.parseInt(amount) + Wallet.getLeastAmount()) {
            marketServer.getBankDataOutputStream().writeUTF("create_receipt " + token + " " + "deposit " + amount + " " + -1 + " " + activeUser.getAccountId());
            marketServer.getBankDataOutputStream().flush();

            String response = marketServer.getBankDataInputStream().readUTF();
            if (response.matches("\\d+")) {
                dataOutputStream.writeUTF("success");
                dataOutputStream.flush();
            } else {
                if (response.equals("token expired") || response.equals("invalid token")) {
                    getToken();
                    withdraw(amount);
                } else {
                    dataOutputStream.writeUTF("fail");
                    dataOutputStream.flush();
                    dataOutputStream.writeUTF(response);
                    dataOutputStream.flush();
                }
            }
        } else {
            dataOutputStream.writeUTF("fail");
            dataOutputStream.flush();
            dataOutputStream.writeUTF("not enough credit");
            dataOutputStream.flush();
        }
    }

    public void charge(String amount) throws IOException {
        marketServer.getBankDataOutputStream().writeUTF("create_receipt " + token + " " + "withdraw " + amount + " " + activeUser.getAccountId() + " " + -1);
        marketServer.getBankDataOutputStream().flush();

        String response = marketServer.getBankDataInputStream().readUTF();
        if (response.equals("\\d+")) {
            dataOutputStream.writeUTF("success");
            dataOutputStream.flush();
        } else {
            if (response.equals("token expired") || response.equals("invalid token")) {
                getToken();
                charge(amount);
            } else {
                dataOutputStream.writeUTF("fail");
                dataOutputStream.flush();
                dataOutputStream.writeUTF(response);
                dataOutputStream.flush();
            }
        }
    }

    public void payByWallet(String[] info) throws IOException {
        if (((Buyer) activeUser).getCart().calculatePrice() > activeUser.getCredit() + Wallet.getLeastAmount()) {
            dataOutputStream.writeUTF("you don't have enough money in your wallet!");
            dataOutputStream.flush();
        } else if (!info[0].isEmpty()) {
            if ((OffWithCode.getOffByCode(info[0]) == null) || (OffWithCode.getOffByCode(info[0]).getApplyingAccounts().get(activeUser) == 0)) {
                dataOutputStream.writeUTF("this discount code is invalid!");
                dataOutputStream.flush();
            } else {
                submitOrderByWallet(info);
            }
        } else {
            submitOrderByWallet(info);
        }
    }

    public void payByAccount(String[] info) throws IOException {
        if (!info[0].isEmpty()){
            if ((OffWithCode.getOffByCode(info[0]) == null) || (OffWithCode.getOffByCode(info[0]).getApplyingAccounts().get(activeUser) == 0)) {
                dataOutputStream.writeUTF("this discount code is invalid!");
                dataOutputStream.flush();
            } else {
                submitOrderByAccount(info);
            }
        }else {
            submitOrderByAccount(info);
        }
    }

    public void submitOrderByWallet(String[] info) throws IOException {
        int max = 0;
        if (!info[0].isEmpty()) {
            OffWithCode offWithCode = OffWithCode.getOffByCode(info[0]);
            max = (int) Math.max(offWithCode.getMaxAmount(), (((Buyer) activeUser).getCart().calculatePrice() * offWithCode.getOffAmount()) / 100);
        }
        activeUser.setCredit(activeUser.getCredit() - (((Buyer) activeUser).getCart().calculatePrice() - max));

        new BuyLog((Buyer) activeUser, ((Buyer) activeUser).getCart().getCartItems(), ((Buyer) activeUser).getCart().calculatePrice() - max, max,
                (info[0].isEmpty()) ? 0 : OffWithCode.getOffByCode(info[0]).getOffAmount(), info[2], info[3], info[1]);

        for (CartItem item : ((Buyer) activeUser).getCart().getCartItems()) {
            item.getItem().getSeller().setCredit((int) (item.getItem().getSeller().getCredit() + (item.getTotalPrice()))-Wallet.getFreelance());
            new SellLog((Buyer) activeUser,item.getItem(),item.getQuantity());

            if (item.getItem() instanceof FileProduct)
                downloadFile((FileProduct) item.getItem());
        }

        dataOutputStream.writeUTF("success");
        dataOutputStream.flush();
    }

    public void submitOrderByAccount(String[] info) throws IOException {
        int max = 0;
        if (!info[0].isEmpty()) {
            OffWithCode offWithCode = OffWithCode.getOffByCode(info[0]);
            max = (int) Math.max(offWithCode.getMaxAmount(), (((Buyer) activeUser).getCart().calculatePrice() * offWithCode.getOffAmount()) / 100);
        }

        new BuyLog((Buyer) activeUser, ((Buyer) activeUser).getCart().getCartItems(), ((Buyer) activeUser).getCart().calculatePrice() - max, max,
                (info[0].isEmpty()) ? 0 : OffWithCode.getOffByCode(info[0]).getOffAmount(), info[2], info[3], info[1]);

        for (CartItem item : ((Buyer) activeUser).getCart().getCartItems()) {
            item.getItem().getSeller().setCredit((int) (item.getItem().getSeller().getCredit() + (item.getTotalPrice()))-Wallet.getFreelance());
            withdraw(""+((int) (item.getItem().getSeller().getCredit() + (item.getTotalPrice()))-Wallet.getFreelance()));
            new SellLog((Buyer) activeUser,item.getItem(),item.getQuantity());

            if (item.getItem() instanceof FileProduct)
                downloadFile((FileProduct) item.getItem());
        }
        dataOutputStream.writeUTF("success");
        dataOutputStream.flush();
    }

    public void downloadFile(FileProduct fileProduct) throws IOException {
        OutputStream outputStream=new FileOutputStream("C:/Downloads/"+fileProduct.getFileName());

        for (int bytes:fileProduct.getFileBytes()){
            outputStream.write(bytes);
        }
    }

    public void logout(){
        activeUser.setLoggedIn(false);
        activeUser=null;
    }

    public void getToken() throws IOException {
        marketServer.getBankDataOutputStream().writeUTF("get_token " + activeUser.getUsername() + " " + activeUser.getPassword());
        marketServer.getBankDataOutputStream().flush();
        token = marketServer.getBankDataInputStream().readUTF();
    }

    public User getActiveUser() {
        return activeUser;
    }

    public void setActiveUser(User activeUser) {
        this.activeUser = activeUser;
    }
}
