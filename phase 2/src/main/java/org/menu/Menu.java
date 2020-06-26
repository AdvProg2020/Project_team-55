package org.menu;

import Model.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import org.example.App;

import java.util.Optional;

public abstract class Menu extends Scene {
    protected ScrollPane stagePane;
    protected AnchorPane pane;
    protected VBox wholeContent=new VBox();
    protected Label label = new Label();
    protected HBox navbar=new HBox();

    protected Product product;
    protected Scene previousPage;

    public Menu(ScrollPane root) {
        super(root);
        pane = new AnchorPane();
        this.stagePane = root;
        manifestNavbar();
        init();
        stagePane.setFitToWidth(true);
        stagePane.setFitToHeight(true);
        stagePane.setContent(wholeContent);
        wholeContent.setSpacing(5);
        wholeContent.getChildren().addAll(navbar,pane);
        getStylesheets().add("userPanel.css");
        wholeContent.getStyleClass().add("pane");
    }

    public Menu(ScrollPane root, Product product,Scene previousScene) {
        super(root);
        pane = new AnchorPane();
        this.stagePane = root;
        manifestNavbar();
        this.product=product;
        this.previousPage=previousScene;
        init();
        stagePane.setFitToWidth(true);
        stagePane.setFitToHeight(true);
        stagePane.setContent(wholeContent);
        wholeContent.setSpacing(5);
        wholeContent.getChildren().addAll(navbar,pane);
        getStylesheets().add("userPanel.css");
        wholeContent.getStyleClass().add("pane");
    }

    public abstract void init();

    public void manifestNavbar(){

        navbar.getChildren().clear();
        navbar.setMinHeight(50);
        navbar.getChildren();

        Button mainPage=new Button("main page");
        mainPage.setFont(Font.font("CHILLER",FontWeight.NORMAL,FontPosture.REGULAR,22));
        mainPage.setOnAction(event -> App.getMainStage().setScene(new MainPage(new ScrollPane())));

        Button login=new Button("login");
        login.setFont(Font.font("CHILLER", FontWeight.BOLD, FontPosture.REGULAR,22));
        login.setOnAction(event -> App.getMainStage().setScene(new LoginPanel(new ScrollPane(),(Menu)App.getMainStage().getScene())));

        Button signUp=new Button("sign up");
        signUp.setFont(Font.font("CHILLER",FontWeight.BOLD,FontPosture.REGULAR,22));
        signUp.setOnAction(event -> App.getMainStage().setScene(new RegisterMenu(new ScrollPane(), (Menu) App.getMainStage().getScene())));

        Button logout=new Button("log out");
        logout.setFont(Font.font("CHILLER",FontWeight.NORMAL,FontPosture.REGULAR,22));
        logout.setOnAction(event -> {
            Alert alert=new Alert(Alert.AlertType.CONFIRMATION,"are you sure you want to log out?");
            Optional<ButtonType> result=alert.showAndWait();

            if (result.get()==ButtonType.OK){
                User.setActiveUser(null);
                App.getMainStage().setScene(new MainPage(new ScrollPane()));
            }
        });

        Button userPanel=new Button("user panel");
        userPanel.setFont(Font.font("CHILLER",FontWeight.NORMAL,FontPosture.REGULAR,22));
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
        product.setFont(Font.font("CHILLER",FontWeight.NORMAL,FontPosture.REGULAR,22));
        product.setOnAction(event -> App.getMainStage().setScene(new ProductMenu(new ScrollPane())));

        Button off=new Button("off page");
        off.setFont(Font.font("CHILLER",FontWeight.NORMAL,FontPosture.REGULAR,22));
        off.setOnAction(event -> App.getMainStage().setScene(new OffMenu(new ScrollPane())));

        navbar.getChildren().add(mainPage);

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
