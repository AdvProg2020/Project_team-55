package org.menu;

import Model.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.example.App;

import javax.swing.text.html.ImageView;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UniqueAuction extends Menu{

    private ArrayList<String> messageHistory=new ArrayList<>();


    public UniqueAuction(ScrollPane root, Object object, Scene previousScene) {
        super(root, object, previousScene);
    }

    @Override
    public void init()  {
        HBox border=new HBox();

        Auction auction= (Auction) object;
        Product product=auction.getProduct();


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

        VBox content=new VBox();



        Button back=new Button("return");
        back.setOnAction(event -> App.getMainStage().setScene(previousPage));
        content.getChildren().add(back);

        GridPane header=new GridPane();
        header.add(product.getPicture(),0,0);
        header.add(new Text(product.getName()),0,1);
        header.add(new Text("category: "+ product.getCategory().getName()),0,2);



        header.add(new Text(product.getBrand()),1,0);
        header.add(new Text("score: "+ product.getAverageScore()),1,2);


        content.getChildren().add(header);
        content.getChildren().add(new Text(product.getExplanation()));

        ListView<String> info=new ListView<>();

        for (String attribute: product.getSpecialAttributes().keySet()){
            info.getItems().add(attribute+": "+ product.getSpecialAttributes().get(attribute));
        }

        content.getChildren().add(info);

        VBox comment=new VBox();

        content.getChildren().add(comment);

        TableView<Comment> commentTable=new TableView<>();
        commentTable.setItems(FXCollections.observableArrayList(product.getComments()));

        TableColumn<Comment, String> userColumn=new TableColumn<>("sender");
        userColumn.setCellValueFactory(new PropertyValueFactory<>("commenter"));

        TableColumn<Comment,String> messageColumn=new TableColumn<>("comment");
        messageColumn.setCellValueFactory(new PropertyValueFactory<>("messages"));

        commentTable.getColumns().addAll(userColumn,messageColumn);

        content.getChildren().add(commentTable);

        content.getChildren().add(new Text("highest submited offer: "+auction.getHighestProposal()));

        TextField offer=new TextField();
        offer.setPromptText("proposal...");
        if (auction.getProposals().containsKey(user)){
            offer.setText(""+auction.getProposals().get(user));
        }
        offer.textProperty().addListener((observableValue, s, t1) -> {
            if (!t1.matches("\\d*"))
                offer.setText(s);
        });

        Button submit=new Button("submit offer");
        User finalUser = user;
        submit.setOnAction(actionEvent -> {
            if (!offer.getText().isEmpty()){
                auction.getProposals().put((Buyer) finalUser, Integer.parseInt(offer.getText()));
                new Alert(Alert.AlertType.INFORMATION,"your offer was successfully submitted").show();
            }else {
                new Alert(Alert.AlertType.ERROR,"no offer is entered").show();
            }
        });

        content.getChildren().addAll(offer,submit);

        TableView<Supporter> supporterTable=new TableView<>();
        supporterTable.setItems(FXCollections.observableArrayList(Supporter.getAllSupporters()));

        TableColumn<Supporter,String> usernameColumn=new TableColumn<>("supporter");
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));

        TableColumn<Supporter,Boolean> logColumn=new TableColumn<>("logged in");
        logColumn.setCellValueFactory(new PropertyValueFactory<>("loggedIn"));

        supporterTable.getColumns().addAll(usernameColumn,logColumn);
        content.getChildren().add(supporterTable);
        //todo: chatroom

        VBox chatContent=new VBox();

        ListView<String> messageList=new ListView<>();
        messageList.setItems(FXCollections.observableArrayList(messageHistory));

        TextField messageBox=new TextField();
        Button send=new Button("send");
        User finalUser1 = user;
        send.setOnAction(actionEvent -> {
            if (!messageBox.getText().isBlank()){
                messageHistory.add(finalUser1.getUsername()+": "+messageBox.getText());
                messageList.setItems(FXCollections.observableArrayList(messageHistory));
            }
        });

        chatContent.getChildren().addAll(messageList,new HBox(messageBox,send));


        border.getChildren().addAll(chatContent,content);

        pane.getChildren().add(border);
    }


}
