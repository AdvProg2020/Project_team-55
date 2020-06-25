package org.menu;

import Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.example.App;

public class BuyerPanel extends Menu {
    public BuyerPanel(ScrollPane root) {
        super(root);
    }

    @Override
    public void init() {
        VBox commands = new VBox();

        AnchorPane.setBottomAnchor(commands, 300.0);

        Button cart = new Button("cart");
        cart.setOnAction(event -> App.getMainStage().setScene(viewCart()));

        Button history = new Button("order history");
        Button discounts = new Button("view discount codes");
        discounts.setOnAction(event -> App.getMainStage().setScene(showDiscountCodes()));

        commands.getChildren().addAll(cart, history, discounts);
        commands.setSpacing(10);

        pane.getChildren().add(commands);

        GridPane info = new GridPane();
        info.setPrefSize(800, 300);
        info.setHgap(300);
        info.setVgap(40);
        info.setLayoutX(400);
        info.setLayoutY(200);
        info.setAlignment(Pos.CENTER);

        Text firstName = new Text("first name:");
        firstName.getStyleClass().add("info-grid");
        info.add(firstName, 0, 0);

        Text firstNameValue = new Text("logged in firstName");
        //todo: get login panel
        firstNameValue.getStyleClass().add("info-grid");
        info.add(firstNameValue, 1, 0);

        Text lastName = new Text("last name:");
        lastName.getStyleClass().add("info-grid");
        info.add(lastName, 0, 1);

        Text lastNameValue = new Text("logged in last name");
        lastNameValue.getStyleClass().add("info-grid");
        info.add(lastNameValue, 1, 1);

        Text phone = new Text("phone number:");
        phone.getStyleClass().add("info-grid");
        info.add(phone, 0, 2);

        Text phoneValue = new Text("logged in phone number");
        phoneValue.getStyleClass().add("info-grid");
        info.add(phoneValue, 1, 2);

        Text email = new Text("Email:");
        email.getStyleClass().add("info-grid");
        info.add(email, 0, 3);

        Text emailValue = new Text("logged in email");
        emailValue.getStyleClass().add("info-grid");
        info.add(emailValue, 1, 3);

        info.setMinSize(200, 200);

        AnchorPane.setLeftAnchor(info, 350.0);
        AnchorPane.setRightAnchor(info, 350.0);


        pane.getChildren().add(info);
    }

    public Menu viewCart() {
        return new Menu(new ScrollPane()) {
            @Override
            public void init() {
                TableView<CartItem> cartItemTableView = new TableView<>();
                AnchorPane.setLeftAnchor(cartItemTableView, 300.0);

                ObservableList<CartItem> cartItemObservableList = FXCollections.observableArrayList();
                cartItemObservableList.addAll(((Buyer) User.getActiveUser()).getCart().getCartItems());
                cartItemTableView.setItems(cartItemObservableList);

                TableColumn<CartItem, Product> itemColumn = new TableColumn<>("product");
                itemColumn.setCellValueFactory(new PropertyValueFactory<>("item"));

                TableColumn<CartItem, Integer> quantityColumn = new TableColumn<>("quantity");
                quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));

                TableColumn<CartItem, Float> eachPriceColumn = new TableColumn<>("price per each");
                eachPriceColumn.setCellValueFactory(new PropertyValueFactory<>("pricePerEach"));

                TableColumn<CartItem, Float> totalPriceColumn = new TableColumn<>("total price");
                totalPriceColumn.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));

                cartItemTableView.getColumns().addAll(itemColumn, quantityColumn, eachPriceColumn, totalPriceColumn);
                pane.getChildren().add(cartItemTableView);

                HBox buttons = new HBox();

                Button increase = new Button("increase");
                increase.setOnAction(event -> {
                    cartItemTableView.getSelectionModel().getSelectedItem().increaseItem();
                    cartItemObservableList.clear();
                    cartItemObservableList.addAll(((Buyer) User.getActiveUser()).getCart().getCartItems());
                    cartItemTableView.setItems(cartItemObservableList);
                });

                Button decrease = new Button("decrease");
                decrease.setOnAction(event -> {
                    cartItemTableView.getSelectionModel().getSelectedItem().decreaseItem();
                    cartItemObservableList.clear();
                    cartItemObservableList.addAll(((Buyer) User.getActiveUser()).getCart().getCartItems());
                    cartItemTableView.setItems(cartItemObservableList);
                });

                Button delete = new Button("remove");
                delete.setOnAction(event -> {
                    ((Buyer) User.getActiveUser()).getCart().getCartItems().remove(cartItemTableView.getSelectionModel().getSelectedItem());
                    cartItemObservableList.clear();
                    cartItemObservableList.addAll(((Buyer) User.getActiveUser()).getCart().getCartItems());
                    cartItemTableView.setItems(cartItemObservableList);
                });

                Button back = new Button("return");
                back.setOnAction(event -> App.getMainStage().setScene(new BuyerPanel(new ScrollPane())));

                Button purchase = new Button("purchase");
                purchase.setOnAction(event -> App.getMainStage().setScene(purchase()));

                buttons.getChildren().addAll(increase, decrease, delete, back, purchase);
                buttons.setSpacing(10);

                pane.getChildren().add(buttons);


            }
        };
    }

    public Menu purchase(){
        return new Menu(new ScrollPane()) {
            @Override
            public void init() {
                GridPane form=new GridPane();
                AnchorPane.setLeftAnchor(form,300.0);
                AnchorPane.setTopAnchor(form,100.0);

                form.add(new Text("first name : "),0,0);

                TextField firstName=new TextField();
                firstName.setPromptText("first name...");
                form.add(firstName,1,0);

                form.add(new Text("last name : "),0,1);

                TextField lastName=new TextField();
                lastName.setPromptText("last name...");
                form.add(lastName,1,1);

                form.add(new Text("phone number : "),0,2);

                TextField phoneNumber=new TextField();
                phoneNumber.setPromptText("phone number...");
                form.add(phoneNumber,1,2);

                form.add(new Text("address : "),0,3);

                TextArea address=new TextArea();
                address.setPromptText("address...");
                form.add(address,1,3);

                form.add(new Text("discount code (optional) : "),0,4);

                TextField discount=new TextField();
                discount.setPromptText("discount code...");
                form.add(discount,1,4);

                Button back=new Button("return");
                back.setOnAction(event -> App.getMainStage().setScene(viewCart()));

                Alert emptyField=new Alert(Alert.AlertType.ERROR,"please make sure all fields are filled.");
                Alert invalidCode=new Alert(Alert.AlertType.ERROR,"discount code you've entered is invalid");
                Alert unusableCode=new Alert(Alert.AlertType.ERROR,"you cannot use this code anymore");

                Button purchase=new Button("purchase");
                purchase.setOnAction(event -> {
                    if (firstName.getText().isEmpty()||lastName.getText().isEmpty())
                        emptyField.show();

                    if (!discount.getText().isEmpty()){

                        if((OffWithCode.getOffByCode(discount.getText())!=null) &&
                                OffWithCode.getOffByCode(discount.getText()).getApplyingAccounts().containsKey((User.getActiveUser()))){

                            if (OffWithCode.getOffByCode(discount.getText()).getApplyingAccounts().get((User.getActiveUser()))!=0){

                                createLog(firstName.getText()+" "+lastName.getText(),phoneNumber.getText(),address.getText(),discount.getText());

                            }else {
                                unusableCode.show();
                            }
                        }else{
                            invalidCode.show();
                        }
                    }
                });
                form.add(purchase,0,5);
            }
        };
    }

    public void createLog(String receiver,String phone,String address,String discount){

        for (CartItem item:((Buyer)User.getActiveUser()).getCart().getCartItems()){
            new SellLog(((Buyer)User.getActiveUser()),item.getItem(),item.getQuantity());
        }

        float omittedPrice;

        if (discount.isEmpty()){
            omittedPrice=0;
        }else {
            omittedPrice=Math.min(OffWithCode.getOffByCode(discount).getMaxAmount(),
                    OffWithCode.getOffByCode(discount).getOffAmount()*((Buyer)User.getActiveUser()).getCart().calculatePrice()/100);
        }

        new BuyLog(((Buyer)User.getActiveUser()),((Buyer)User.getActiveUser()).getCart().getCartItems(),((Buyer)User.getActiveUser()).getCart().calculatePrice()-omittedPrice,
                omittedPrice,(OffWithCode.getOffByCode(discount)==null)? 0:OffWithCode.getOffByCode(discount).getOffAmount(),
                address,phone,receiver);
    }

    public Menu showDiscountCodes(){
        return new Menu(new ScrollPane()) {
            @Override
            public void init() {
                TableView<OffWithCode> discountTable=new TableView<>();
                AnchorPane.setLeftAnchor(discountTable,300.0);

                ObservableList<OffWithCode> discounts=FXCollections.observableArrayList();
                discounts.addAll((OffWithCode) OffWithCode.getAllDiscounts().stream().filter(discount->
                        discount.getApplyingAccounts().containsKey(User.getActiveUser())&&
                                discount.isActive()));

                TableColumn<OffWithCode,String> codeColumn=new TableColumn<>("code");
                codeColumn.setCellValueFactory(new PropertyValueFactory<>("offCode"));

                TableColumn<OffWithCode,Integer> usageColumn=new TableColumn<>("remained usages");
                usageColumn.setCellValueFactory(new PropertyValueFactory<>("remainedUsages"));

                TableColumn<OffWithCode,String> dateColumn=new TableColumn<>("end date");
                dateColumn.setCellValueFactory(new PropertyValueFactory<>("formattedStopDate"));

                discountTable.getColumns().addAll(codeColumn,usageColumn,dateColumn);
                pane.getChildren().add(discountTable);

                Button back=new Button("return");
                back.setOnAction(event -> App.getMainStage().setScene(new BuyerPanel(new ScrollPane())));
                pane.getChildren().add(back);
            }
        };
    }

    public Menu showOrderHistory(){
        return new Menu(new ScrollPane()) {
            @Override
            public void init() {
                TableView<BuyLog> orderTable=new TableView<>();
                orderTable.setItems(FXCollections.observableArrayList(((Buyer)User.getActiveUser()).getOrderHistory()));

                TableColumn<BuyLog,String> idColumn=new TableColumn<>("id");
                idColumn.setCellValueFactory(new PropertyValueFactory<>("logId"));

                TableColumn<BuyLog,String> dateColumn=new TableColumn<>("order time");
                dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));

                TableColumn<BuyLog,Float> moneyColumn=new TableColumn<>("purchased money");
                moneyColumn.setCellValueFactory(new PropertyValueFactory<>("purchasedMoney"));

                orderTable.getColumns().addAll(idColumn,dateColumn,moneyColumn);

                pane.getChildren().add(orderTable);
            }
        };
    }
}
