package org.menu;

import Model.*;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.example.App;

import java.io.IOException;

public class RequestPanel extends Menu{

    private Request request;

    public RequestPanel(ScrollPane root,Request request){
        super(root);
        this.request=request;

        Scene scene=null;

        if (request instanceof ProductAdditionRequest) {
            System.out.println("product addition");
            scene=productAddition();
        } else if (request instanceof ProductEditRequest) {
            System.out.println("roduct edit");
            scene=productEdit();
        } else if (request instanceof OffAdditionRequest) {
            scene=offAddition();
        } else if (request instanceof OffEditRequest) {
            scene=offEdit();
        } else if (request instanceof SellerAdditionRequest) {
            scene=sellerAddition();
        }

        App.getMainStage().setScene(scene);
    }

    @Override
    public void init() {

    }

    public Menu productAddition(){
        return new Menu(new ScrollPane()) {
            @Override
            public void init() {

                ProductAdditionRequest product= (ProductAdditionRequest) request;

                VBox content=new VBox();
                content.setSpacing(5);

                Button back=new Button("return");
                back.setOnAction(event -> {

                        App.getMainStage().setScene(new ManagerPanel(new ScrollPane()));

                });

                Button accept=new Button("accept request");
                accept.setOnAction(event -> {
                    try {
                        product.acceptRequest();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    new Alert(Alert.AlertType.INFORMATION,"request accepted").show();

                        App.getMainStage().setScene(new ManagerPanel(new ScrollPane()).manageRequests());

                });
                Button decline=new Button("decline request");
                decline.setOnAction(event -> {
                    product.declineRequest();
                    new Alert(Alert.AlertType.INFORMATION,"request rejected").show();

                        App.getMainStage().setScene(new ManagerPanel(new ScrollPane()).manageRequests());

                });

                content.getChildren().addAll(back,accept,decline);

                GridPane header=new GridPane();
                header.add(product.getPicture(),0,0);
                header.add(new Text(product.getName()),0,1);
                header.add(new Text("category: "+product.getCategory().getName()),0,2);

                header.add(new Text(product.getBrand()),1,0);
                header.add(new Text("price: "+product.getPrice()),1,1);

                content.getChildren().add(header);
                content.getChildren().add(new Text(product.getExplanation()));

                ListView<String> info=new ListView<>();

                for (String attribute:product.getSpecialAttributes().keySet()){
                    info.getItems().add(attribute+": "+product.getSpecialAttributes().get(attribute));
                }

                pane.getChildren().add(content);

                content.getChildren().add(info);

                content.getChildren().add(new Text("hello"));

                pane.getChildren().add(new Text("how are you"));



            }
        };
    }

    public Menu productEdit() {
        return new Menu(new ScrollPane()) {
            @Override
            public void init() {
                ProductEditRequest editRequest=(ProductEditRequest) request;
                Product product=editRequest.getProduct();

                Tab before=new Tab("before edit");

                VBox content=new VBox();
                before.setContent(content);

                Button back=new Button("return");
                back.setOnAction(event -> {

                        App.getMainStage().setScene(new ManagerPanel(new ScrollPane()).manageRequests());

                });
                Button accept=new Button("accept request");
                accept.setOnAction(event -> {
                    editRequest.acceptRequest();

                        App.getMainStage().setScene(new ManagerPanel(new ScrollPane()).manageRequests());

                });
                Button decline=new Button("decline request");
                decline.setOnAction(event -> {
                    editRequest.declineRequest();

                        App.getMainStage().setScene(new ManagerPanel(new ScrollPane()).manageRequests());

                });

                content.getChildren().addAll(back,accept,decline);
                

                GridPane header=new GridPane();
                header.add(product.getPicture(),0,0);
                header.add(new Text(product.getName()),0,1);
                header.add(new Text("category: "+product.getCategory().getName()),0,2);


                header.add(new Text(product.getBrand()),1,0);
                header.add(new Text("price: "+product.getPrice()),1,1);
                header.add(new Text("score: "+product.getAverageScore()),1,2);

                content.getChildren().add(header);
                content.getChildren().add(new Text(product.getExplanation()));

                ListView<String> info=new ListView<>();

                for (String attribute:product.getSpecialAttributes().keySet()){
                    info.getItems().add(attribute+": "+product.getSpecialAttributes().get(attribute));
                }

                content.getChildren().add(info);

                Tab after=new Tab("after edit");

                VBox content2=new VBox();
                before.setContent(content2);


                GridPane header2=new GridPane();
                header2.add(editRequest.getPicture(),0,0);
                header2.add(new Text(editRequest.getRequestedChange().get("name")),0,1);
                header2.add(new Text("category: "+editRequest.getCategory().getName()),0,2);


                header2.add(new Text(editRequest.getRequestedChange().get("brand")),1,0);
                header2.add(new Text("price: "+editRequest.getPrice()),1,1);
                header2.add(new Text("score: "+product.getAverageScore()),1,2);

                content2.getChildren().add(header2);
                content2.getChildren().add(new Text(editRequest.getExplanation()));

                ListView<String> info2=new ListView<>();

                for (String attribute:product.getSpecialAttributes().keySet()){
                    info2.getItems().add(attribute+": "+editRequest.getRequestedChange().get(attribute));
                }

                content2.getChildren().add(info2);

                TabPane compare=new TabPane();
                compare.getTabs().addAll(before,after);

                pane.getChildren().add(compare);

            }
        };
    }

    public Menu offAddition() {
        return new Menu(new ScrollPane()) {
            @Override
            public void init() {
                OffAdditionRequest additionRequest=(OffAdditionRequest)request;

                VBox content=new VBox();

                Button back=new Button("return");
                back.setOnAction(event -> {

                        App.getMainStage().setScene(new ManagerPanel(new ScrollPane()).manageRequests());

                });
                Button accept=new Button("accept request");
                accept.setOnAction(event -> {
                    additionRequest.acceptRequest();

                        App.getMainStage().setScene(new ManagerPanel(new ScrollPane()).manageRequests());

                });
                Button decline=new Button("decline request");
                decline.setOnAction(event -> {
                    additionRequest.declineRequest();

                        App.getMainStage().setScene(new ManagerPanel(new ScrollPane()).manageRequests());

                });

                content.getChildren().addAll(back,accept,decline);

                GridPane gridPane=new GridPane();
                content.getChildren().add(gridPane);

                gridPane.add(new Text("id : "),0,0);
                gridPane.add(new Text(additionRequest.getOffId()),1,0);
                gridPane.add(new Text("percent : "),0,1);
                gridPane.add(new Text(""+additionRequest.getOffAmount()),1,1);
                gridPane.add(new Text("start date : "),0,2);
                gridPane.add(new Text(additionRequest.getStartDate()),1,2);
                gridPane.add(new Text("stop date : "),0,3);
                gridPane.add(new Text(additionRequest.getStopDate()),1,3);
                gridPane.add(new Text("product list : "),0,4);

                ListView<Product> productListView=new ListView<>();
                productListView.setItems(FXCollections.observableArrayList(additionRequest.getProductsArray()));
                gridPane.add(productListView,1,4);

                pane.getChildren().add(content);
            }
        };
    }

    public Menu offEdit()  {
        return new Menu(new ScrollPane()) {
            @Override
            public void init() {
                OffEditRequest editRequest=(OffEditRequest) request;

                Off off=editRequest.getOff();

                VBox content=new VBox();

                Button back=new Button("return");
                back.setOnAction(event -> {

                        App.getMainStage().setScene(new ManagerPanel(new ScrollPane()).manageRequests());

                });
                Button accept=new Button("accept request");
                accept.setOnAction(event -> {
                    editRequest.acceptRequest();
                        App.getMainStage().setScene(new ManagerPanel(new ScrollPane()).manageRequests());

                });
                Button decline=new Button("decline request");
                decline.setOnAction(event ->{
                    editRequest.declineRequest();

                        App.getMainStage().setScene(new ManagerPanel(new ScrollPane()).manageRequests());

                });

                content.getChildren().addAll(back,accept,decline);

                Tab before=new Tab("before edit");

                GridPane beforeGrid=new GridPane();
                before.setContent(beforeGrid);


                beforeGrid.add(new Text("id : "),0,0);
                beforeGrid.add(new Text(off.getOffId()),1,0);
                beforeGrid.add(new Text("percent : "),0,1);
                beforeGrid.add(new Text(""+editRequest.getOffAmount()),1,1);
                beforeGrid.add(new Text("start date : "),0,2);
                beforeGrid.add(new Text(off.getFormattedStartDate()),1,2);
                beforeGrid.add(new Text("stop date : "),0,3);
                beforeGrid.add(new Text(off.getFormattedStopDate()),1,3);
                beforeGrid.add(new Text("product list : "),0,4);

                ListView<Product> productListView=new ListView<>();
                productListView.setItems(FXCollections.observableArrayList(off.getProductsArray()));
                beforeGrid.add(productListView,1,4);

                Tab after=new Tab("after edit");

                GridPane afterGrid=new GridPane();
                after.setContent(afterGrid);


                afterGrid.add(new Text("id : "),0,0);
                afterGrid.add(new Text(off.getOffId()),1,0);
                afterGrid.add(new Text("percent : "),0,1);
                afterGrid.add(new Text(""+editRequest.getOffAmount()),1,1);
                afterGrid.add(new Text("start date : "),0,2);
                afterGrid.add(new Text(editRequest.getStartDate()),1,2);
                afterGrid.add(new Text("stop date : "),0,3);
                afterGrid.add(new Text(editRequest.getStopDate()),1,3);
                afterGrid.add(new Text("product list : "),0,4);

                ListView<Product> afterProductListView=new ListView<>();
                afterProductListView.setItems(FXCollections.observableArrayList(editRequest.getProductsArray()));
                afterGrid.add(afterProductListView,1,4);

                TabPane compare=new TabPane();
                compare.getTabs().addAll(before,after);

                content.getChildren().add(compare);

                pane.getChildren().addAll(content);

            }
        };
    }

    public Menu sellerAddition() {
        return new Menu(new ScrollPane()) {
            @Override
            public void init() {
                SellerAdditionRequest additionRequest=(SellerAdditionRequest) request;

                GridPane form=new GridPane();

                form.add(new Text("username : "),0,0);
                form.add(new Text(additionRequest.getUsername()),1,0);
                form.add(new Text("first name : "),0,1);
                form.add(new Text(additionRequest.getFirstName()),1,1);
                form.add(new Text("last name : "),0,2);
                form.add(new Text(additionRequest.getLastName()),1,2);
            }
        };
    }
}
