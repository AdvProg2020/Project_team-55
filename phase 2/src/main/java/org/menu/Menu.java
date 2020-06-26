package org.menu;

import Model.Buyer;
import Model.Manager;
import Model.Seller;
import Model.User;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import org.example.App;

import java.util.Optional;

public abstract class Menu extends Scene {
    protected ScrollPane stagePane;
    protected AnchorPane pane;
    protected Label label = new Label();
    protected HBox navbar=new HBox();

    public Menu(ScrollPane root) {
        super(root);
        pane = new AnchorPane();
        this.stagePane = root;
        manifestNavbar();
        init();
        stagePane.setFitToWidth(true);
        stagePane.setFitToHeight(true);
        stagePane.setContent(pane);
        pane.getChildren().add(navbar);
        getStylesheets().add("userPanel.css");
    }

    public abstract void init();

    public void manifestNavbar(){

        Button login=new Button("login");
        login.setOnAction(event -> App.getMainStage().setScene(new LoginPanel(new ScrollPane(),App.getMainStage().getScene())));

        Button signUp=new Button("sign up");
        signUp.setOnAction(event -> App.getMainStage().setScene(new RegisterMenu(new ScrollPane())));

        Button logout=new Button("log out");
        logout.setOnAction(event -> {
            Alert alert=new Alert(Alert.AlertType.CONFIRMATION,"are you sure you want to log out?");
            Optional<ButtonType> result=alert.showAndWait();

            if (result.get()==ButtonType.OK){
                User.setActiveUser(null);
                App.getMainStage().setScene(new ManagerPanel(new ScrollPane()));
            }
        });

        Button userPanel=new Button("user panel");
        userPanel.setOnAction(event -> {
            if (User.getActiveUser() instanceof Buyer){
                App.getMainStage().setScene(new BuyerPanel(new ScrollPane()));
            }else if (User.getActiveUser() instanceof Seller){
                App.getMainStage().setScene(new SellerPanel(new ScrollPane()));
            }else if (User.getActiveUser() instanceof Manager){
                App.getMainStage().setScene(new ManagerPanel(new ScrollPane()));
            }
                }
        );

        Button product=new Button("product page");
        product.setOnAction(event -> App.getMainStage().setScene(new ProductMenu(new ScrollPane())));

        Button off=new Button("off page");
        off.setOnAction(event -> App.getMainStage().setScene(new OffMenu(new ScrollPane())));

        if (User.getActiveUser()==null){
            navbar.getChildren().addAll(signUp,login);
        }else {
            navbar.getChildren().addAll(userPanel,logout);
        }

        navbar.getChildren().addAll(product,off);
        navbar.getStyleClass().add("navbar");
        navbar.setPrefWidth(App.getMainStage().getWidth());
        navbar.setSpacing(7);
    }
}
