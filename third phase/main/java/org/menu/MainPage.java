package org.menu;

import Model.Buyer;
import Model.Manager;
import Model.Seller;
import Model.User;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import org.example.App;

import java.io.IOException;

public class MainPage extends Menu{
    public MainPage(ScrollPane root) {
        super(root);
    }

    @Override
    public void init()  {

        User user= null;
        try {
            App.getDataOutputStream().writeUTF("get active user");
            App.getDataOutputStream().flush();
            user = (User) App.getObjectInputStream().readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


        VBox navbar=new VBox();
        pane.getChildren().add(navbar);

        AnchorPane.setTopAnchor(navbar,200.0);
        AnchorPane.setLeftAnchor(navbar,200.0);


        Button login=new Button("login");
        login.setFont(Font.font("CHILLER", FontWeight.NORMAL, FontPosture.REGULAR,22));
        login.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                    App.getMainStage().setScene(new LoginPanel(new ScrollPane(),(Menu)App.getMainStage().getScene()));
            }
        });

        Button register=new Button("register");
        register.setFont(Font.font("CHILLER",FontWeight.NORMAL,FontPosture.REGULAR,22));
        register.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                RegisterMenu registerMenu= null;

                    registerMenu = new RegisterMenu(new ScrollPane(), (Menu) App.getMainStage().getScene());

                App.getMainStage().setScene(registerMenu);
            }
        });

        Button products=new Button("products");
        products.setFont(Font.font("CHILLER",FontWeight.NORMAL,FontPosture.REGULAR,22));
        products.setOnAction(event -> {

                App.getMainStage().setScene(new ProductMenu(new ScrollPane()));

        });

        Button offs=new Button("offs");
        offs.setFont(Font.font("CHILLER",FontWeight.NORMAL,FontPosture.REGULAR,22));
        offs.setOnAction(event -> {

                App.getMainStage().setScene(new OffMenu(new ScrollPane()));

        });

        Button panel=new Button("userPanel");
        panel.setFont(Font.font("CHILLER",FontWeight.NORMAL,FontPosture.REGULAR,22));
        User finalUser = user;
        panel.setOnAction(event -> {
            if (finalUser instanceof Buyer){
                    App.getMainStage().setScene(new BuyerPanel(new ScrollPane()));

            }else if (finalUser instanceof Seller){
                
                    App.getMainStage().setScene(new SellerPanel(new ScrollPane()));

            }else if (finalUser instanceof Manager){

                    App.getMainStage().setScene(new ManagerPanel(new ScrollPane()));

            }
        });

        if (!App.isSignedIn()){
            navbar.getChildren().addAll(login,register);
        }else {
            navbar.getChildren().add(panel);
        }


        navbar.getChildren().addAll(products,offs);
        navbar.setSpacing(10);
    }
}
