package org.menu;

import Model.*;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.example.App;

import javax.jws.soap.SOAPBinding;
import java.util.ArrayList;

public class UniqueProductMenu extends Menu{


    public UniqueProductMenu(ScrollPane root, Product product,Scene previousPage) {
        super(root,product,previousPage);

    }

    @Override
    public void init() {
        VBox content=new VBox();

        TabPane productsTabPane=new TabPane();

        Tab mainProduct=new Tab(product.getProductId());
        mainProduct.setClosable(false);
        mainProduct.setContent(content);

        productsTabPane.getTabs().add(mainProduct);

        Button back=new Button("return");
        back.setOnAction(event -> App.getMainStage().setScene(previousPage));
        content.getChildren().add(back);

        GridPane header=new GridPane();
        header.add(product.getPicture(),0,0);
        header.add(new Text(product.getName()),0,1);
        header.add(new Text("category: "+product.getCategory().getName()),0,2);



        header.add(new Text(product.getBrand()),1,0);
        header.add(new Text("price: "+product.getPrice()),1,1);
        header.add(new Text("score: "+product.getAverageScore()),1,2);

        ChoiceBox<Integer> scoreOptions=new ChoiceBox<>();
        ArrayList<Integer> options=new ArrayList<>();
        options.add(1);
        options.add(2);
        options.add(3);
        options.add(4);
        options.add(5);
        scoreOptions.setItems(FXCollections.observableArrayList(options));

        Button score=new Button("give score");
        score.setDisable(User.getActiveUser()==null || !(User.getActiveUser() instanceof Buyer) || !product.getListOfBuyers().contains(User.getActiveUser()));
        score.setOnAction(event -> {
            if (!score.isDisabled() && scoreOptions.getSelectionModel().getSelectedItem()!=null){
                Score.getInstance().rateProduct(product,scoreOptions.getSelectionModel().getSelectedItem());
            }
        });

        HBox scoreBox=new HBox();
        scoreBox.getChildren().addAll(scoreOptions,score);
        header.add(scoreBox,0,3);

        Button cart=new Button("add to cart");
        if (!(User.getActiveUser() instanceof Buyer))
            cart.setDisable(true);
        else
            cart.setOnAction(event -> ((Buyer)User.getActiveUser()).addProductToCart(product));
        header.add(cart,0,4);

        Button compare=new Button("compare");
        compare.setOnAction(event ->{
            Tab sideProducts=new Tab();
            sideProducts.setClosable(true);
            sideProducts.setContent(new ProductMenu(new ScrollPane()).getRoot());
            productsTabPane.getTabs().add(sideProducts);
        });
        header.add(compare,1,4);



        content.getChildren().add(header);
        content.getChildren().add(new Text(product.getExplanation()));

        ListView<String> info=new ListView<>();

        for (String attribute:product.getSpecialAttributes().keySet()){
            info.getItems().add(attribute+": "+product.getSpecialAttributes().get(attribute));
        }

        content.getChildren().add(info);

        VBox comment=new VBox();

        TextArea commentArea=new TextArea();
        commentArea.setPromptText("comment...");
        commentArea.setDisable(User.getActiveUser()==null || !(User.getActiveUser() instanceof Buyer));

        Button send=new Button("send comment");
        send.setDisable(User.getActiveUser()==null ||!(User.getActiveUser() instanceof  Buyer));
        send.setOnAction(event -> {
            if (!send.isDisabled()){
                if (!commentArea.getText().isEmpty()){
                    new Comment(User.getActiveUser(),commentArea.getText(),product);
                    App.getMainStage().setScene(new UniqueProductMenu(new ScrollPane(),product,previousPage));
                }
            }
        });

        comment.getChildren().addAll(commentArea,send);

        content.getChildren().add(comment);

        TableView<Comment> commentTable=new TableView<>();
        commentTable.setItems(FXCollections.observableArrayList(product.getComments()));

        TableColumn<Comment, String> userColumn=new TableColumn<>("sender");
        userColumn.setCellValueFactory(new PropertyValueFactory<>("commenter"));

        TableColumn<Comment,String> messageColumn=new TableColumn<>("comment");
        messageColumn.setCellValueFactory(new PropertyValueFactory<>("message"));

        commentTable.getColumns().addAll(userColumn,messageColumn);

        content.getChildren().add(commentTable);



        pane.getChildren().add(productsTabPane);

    }
}
