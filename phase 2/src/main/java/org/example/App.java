package org.example;

import Model.DataLoader;
import Model.ExitThread;
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

import java.io.File;
import java.io.IOException;
import java.util.Optional;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    private static Stage mainStage;

    @Override
    public void start(Stage stage) throws IOException {


        mainStage = stage;
        mainStage.setMaximized(true);
        mainStage.setWidth(1500);
        mainStage.setHeight(900);
        mainStage.setMinHeight(500);
        mainStage.setMinWidth(500);

        String path = "C:\\Users\\ASUS\\Downloads\\Telegram Desktop\\The Korgis – Everybody's Got To Learn Sometimes.mp3";
        Media media = new Media(new File(path).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setAutoPlay(true);

//        Runtime runtime=Runtime.getRuntime();
//        runtime.addShutdownHook(new ExitThread());

        mainStage.setOnCloseRequest(event -> {
            event.consume();
            closeProgram();
        });


        DataLoader.readData();
        mainStage.show();
    }

    public static void main(String[] args) {



        launch();
    }

    public static Stage getMainStage() {
        return mainStage;
    }

    public void closeProgram(){
        Alert exit=new Alert(Alert.AlertType.CONFIRMATION,"are you sure you want to exit?");
        exit.setTitle("exit");

        Optional<ButtonType> result=exit.showAndWait();

        if (result.get()==ButtonType.OK){
            mainStage.close();
        }
    }
}