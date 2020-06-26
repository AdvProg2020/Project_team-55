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
import org.example.App;
import sun.swing.BakedArrayList;

public class MainPage extends Menu{
    public MainPage(ScrollPane root) {
        super(root);
    }

    @Override
    public void init() {
        App.getMainStage().getScene().getStylesheets().add("userpanel.css");
        VBox navbar=new VBox();
        pane.getChildren().add(navbar);
        pane.getStyleClass().add("pane");

        AnchorPane.setTopAnchor(navbar,200.0);
        AnchorPane.setLeftAnchor(navbar,200.0);


        Button login=new Button("login");
        login.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                App.getMainStage().setScene(new LoginPanel(new ScrollPane(),App.getMainStage().getScene()));
            }
        });

        Button register=new Button("register");
        register.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                RegisterMenu registerMenu=new RegisterMenu(new ScrollPane());
                registerMenu.setPreviousScene(App.getMainStage().getScene());
                App.getMainStage().setScene(registerMenu);
            }
        });

        Button products=new Button("products");
        products.setOnAction(event -> App.getMainStage().setScene(new ProductMenu(new ScrollPane())));

        Button offs=new Button("offs");
        offs.setOnAction(event -> App.getMainStage().setScene(new OffMenu(new ScrollPane())));

        Button panel=new Button("userPanel");
        panel.setOnAction(event -> {
            if (User.getActiveUser() instanceof Buyer){
                App.getMainStage().setScene(new BuyerPanel(new ScrollPane()));
            }else if (User.getActiveUser() instanceof Seller){
                App.getMainStage().setScene(new SellerPanel(new ScrollPane()));
            }else if (User.getActiveUser() instanceof Manager){
                App.getMainStage().setScene(new ManagerPanel(new ScrollPane()));
            }
        });

        if (User.getActiveUser()==null){
            navbar.getChildren().addAll(login,register);
        }else {
            navbar.getChildren().add(panel);
        }


        navbar.getChildren().addAll(products,offs);
        navbar.setSpacing(10);
    }
}
