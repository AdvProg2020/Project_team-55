package org.example;

import Model.DataLoader;
import Model.DataSaver;
import Model.ExitThread;
import Model.Manager;
import com.google.gson.internal.$Gson$Preconditions;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ScrollPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import org.menu.MainPage;
import org.menu.SellerPanel;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.Socket;
import java.util.Optional;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    private static Stage mainStage;
    private static boolean signedIn;

    private static Socket socket;
    private static DataInputStream dataInputStream;
    private static DataOutputStream dataOutputStream;
    private static ObjectInputStream objectInputStream;

    public static void main(String[] args) {
//        try {
//            socket=new Socket("192.168.1.5",1234);
//            dataInputStream=new DataInputStream(new BufferedInputStream(socket.getInputStream()));
//            dataOutputStream=new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
//            objectInputStream=new ObjectInputStream(socket.getInputStream());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }


        launch();
    }


    @Override
    public void start(Stage stage){

        mainStage = stage;
        mainStage.setMaximized(true);
        mainStage.setWidth(1500);
        mainStage.setHeight(900);
        mainStage.setMinHeight(500);
        mainStage.setMinWidth(500);

            mainStage.setScene(new MainPage(new ScrollPane()));

//        String path = "E:\\java projects\\graphic phase\\phase 2\\src\\main\\resources\\Pachelbel - Canon in D (Best Piano Version).mp3";
//        Media media = new Media(new File(path).toURI().toString());
//        MediaPlayer mediaPlayer = new MediaPlayer(media);
//        mediaPlayer.setAutoPlay(true);

        mainStage.setOnCloseRequest(event -> {
            event.consume();
            closeProgram();
        });


        mainStage.show();
    }



    public static Stage getMainStage() {
        return mainStage;
    }

    public static DataOutputStream getDataOutputStream() {
        return dataOutputStream;
    }

    public static DataInputStream getDataInputStream() {
        return dataInputStream;
    }

    public static ObjectInputStream getObjectInputStream() {
        return objectInputStream;
    }

    public static boolean isSignedIn() {
        return signedIn;
    }

    public static void setSignedIn(boolean signedIn) {
        App.signedIn = signedIn;
    }

    public void closeProgram(){
        Alert exit=new Alert(Alert.AlertType.CONFIRMATION,"are you sure you want to exit?");
        exit.setTitle("exit");

        Optional<ButtonType> result=exit.showAndWait();

        if (result.get()==ButtonType.OK){
            mainStage.close();
//            try {
//                if (Manager.getMainManager() != null) {
//                    DataSaver.saveData();
//                }
//            }catch (IOException e){
//                e.printStackTrace();
//            }
        }
    }
}