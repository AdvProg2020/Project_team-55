package org.menu;

import Model.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Popup;
import org.example.App;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class BuyerPanel extends Menu {
    File file;
    User user;

    public BuyerPanel(ScrollPane root) {
        super(root);
    }

    @Override
    public void init() {
        try {
            App.getDataOutputStream().writeUTF("get active user");
            App.getDataOutputStream().flush();
            user=(User)App.getObjectInputStream().readObject();
        } catch (IOException|ClassNotFoundException e) {
            e.printStackTrace();
        }


        VBox commands = new VBox();

        AnchorPane.setBottomAnchor(commands, 300.0);

        ImageView profile = new ImageView(user.getProfile());
        profile.resize(300, 300);
        profile.setFitHeight(300);
        profile.setFitHeight(300);

        Button edit = new Button("edit profile");
        edit.getStyleClass().add("command");
        edit.setOnAction(event -> {

                App.getMainStage().setScene(editProfile());

        });

        Button cart = new Button("cart");
        cart.getStyleClass().add("command");
        cart.setOnAction(event -> {

                App.getMainStage().setScene(viewCart());

        });

        Button history = new Button("order history");
        history.getStyleClass().add("command");
        history.setOnAction(event -> {

                App.getMainStage().setScene(viewOrderHistory());

        });

        Button discounts = new Button("view discount codes");
        discounts.getStyleClass().add("command");
        discounts.setOnAction(event -> {

                App.getMainStage().setScene(showDiscountCodes());
        });

        Button balance = new Button("view balance");
        balance.getStyleClass().add("command");
        balance.setOnAction(event -> {

                App.getMainStage().setScene(balance());

        });

        Button viewSupporters=new Button("view supporters");
        viewSupporters.getStyleClass().add("command");
        viewSupporters.setOnAction(event ->{

                App.getMainStage().setScene(viewSupporters());

        });

        commands.getChildren().addAll(profile, edit, cart, history, discounts, balance,viewSupporters);
        commands.setSpacing(10);

        pane.getChildren().add(commands);

        GridPane info = new GridPane();
        info.setPrefSize(800, 300);
        info.setHgap(300);
        info.setVgap(40);
        info.setLayoutX(400);
        info.setLayoutY(200);
        info.setAlignment(Pos.CENTER);

        info.add(new Text("buyer: "+user.getUsername()),0,0);

        Text firstName = new Text("first name:");
        firstName.getStyleClass().add("info-grid");
        info.add(firstName, 0, 1);

        Text firstNameValue = new Text(user.getFirstName());
        firstNameValue.getStyleClass().add("info-grid");
        info.add(firstNameValue, 1, 1);

        Text lastName = new Text("last name:");
        lastName.getStyleClass().add("info-grid");
        info.add(lastName, 0, 2);

        Text lastNameValue = new Text(user.getLastName());
        lastNameValue.getStyleClass().add("info-grid");
        info.add(lastNameValue, 1, 2);

        Text phone = new Text("phone number:");
        phone.getStyleClass().add("info-grid");
        info.add(phone, 0, 3);

        Text phoneValue = new Text(user.getPhoneNumber());
        phoneValue.getStyleClass().add("info-grid");
        info.add(phoneValue, 1, 3);

        Text email = new Text("Email:");
        email.getStyleClass().add("info-grid");
        info.add(email, 0, 4);

        Text emailValue = new Text(user.getEmail());
        emailValue.getStyleClass().add("info-grid");
        info.add(emailValue, 1, 4);

        info.setMinSize(200, 200);

        AnchorPane.setLeftAnchor(info, 350.0);
        AnchorPane.setRightAnchor(info, 350.0);


        pane.getChildren().add(info);
    }

    public Menu editProfile() {
        return new Menu(new ScrollPane()) {
            @Override
            public void init() {
                GridPane form = new GridPane();
                pane.getChildren().add(form);
                form.setVgap(25);
                form.setHgap(100);

                AnchorPane.setLeftAnchor(form, 300.0);
                AnchorPane.setTopAnchor(form, 100.0);
                AnchorPane.setBottomAnchor(form, 100.0);

                ArrayList<Label> alerts = new ArrayList<>();


                Text password = new Text("password : ");
                form.add(password, 0, 2);

                PasswordField passwordField = new PasswordField();
                passwordField.setPromptText("enter password...");
                passwordField.setText(user.getPassword());
                form.add(passwordField, 1, 2);

                Label invalidPass = new Label("*password can only contain a-z A-Z _ 0-9");
                form.add(invalidPass, 1, 3, 2, 1);
                alerts.add(invalidPass);

                passwordField.textProperty().addListener(new ChangeListener<String>() {
                    @Override
                    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                        invalidPass.setVisible(!newValue.matches("\\w+"));
                    }
                });

                Text confirm = new Text("confirm password : ");
                form.add(confirm, 0, 4);

                PasswordField passConfirm = new PasswordField();
                passConfirm.setPromptText("confirm password...");
                passConfirm.setText(user.getPassword());
                form.add(passConfirm, 1, 4);

                Label notMatchingPass = new Label("*password and confirmation don't match");
                form.add(notMatchingPass, 1, 5, 2, 1);
                alerts.add(notMatchingPass);

                passConfirm.textProperty().addListener(new ChangeListener<String>() {
                    @Override
                    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                        notMatchingPass.setVisible(!newValue.equals(passwordField.getText()));
                    }
                });

                Text fName = new Text("first name : ");
                form.add(fName, 0, 6);

                TextField fNameField = new TextField();
                fNameField.setPromptText("first name...");
                fNameField.setText(user.getFirstName());
                form.add(fNameField, 1, 6);

                Text lName = new Text("last name : ");
                form.add(lName, 0, 8);

                TextField lNameField = new TextField();
                lNameField.setPromptText("last name...");
                lNameField.setText(user.getLastName());
                form.add(lNameField, 1, 8);

                Text email = new Text("Email : ");
                form.add(email, 0, 10);

                TextField emailField = new TextField();
                emailField.setPromptText("Email...");
                emailField.setText(user.getEmail());
                form.add(emailField, 1, 10);

                Label invalidEmail = new Label("*please enter a valid email");
                form.add(invalidEmail, 1, 11, 2, 1);
                alerts.add(invalidEmail);

                emailField.textProperty().addListener(new ChangeListener<String>() {
                    @Override
                    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                        invalidEmail.setVisible(!newValue.matches("\\w+@\\w+\\.\\w+"));
                    }
                });

                Text phone = new Text("phone number : ");
                form.add(phone, 0, 12);

                TextField phoneField = new TextField();
                phoneField.setPromptText("phone number...");
                phoneField.setText(user.getPhoneNumber());
                form.add(phoneField, 1, 12);

                Label invalidPhone = new Label("*please enter a valid phone number");
                form.add(invalidPhone, 1, 13, 2, 1);
                alerts.add(invalidPhone);

                phoneField.textProperty().addListener(new ChangeListener<String>() {
                    @Override
                    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                        if (!newValue.matches("\\+?\\d*"))
                            phoneField.setText(oldValue);
                        invalidPhone.setVisible(!newValue.matches("(\\+98|0)9\\d{9}"));
                    }
                });

                Button getImage = new Button("upload profile picture");
                form.add(getImage, 0, 16);

                FileChooser imageChooser = new FileChooser();

                getImage.setOnAction(event -> setFile(imageChooser.showOpenDialog(App.getMainStage())));


                alerts.forEach((alert) -> alert.setVisible(false));

                Label emptyField = new Label("*please make sure no field is left empty");
                emptyField.setVisible(false);
                form.add(emptyField, 1, 21, 2, 1);

                Button submit = new Button("submit");
                form.add(submit, 1, 20);
                submit.setOnAction(event -> {
                    emptyField.setVisible(false);
                    if ((((passwordField.getText().isEmpty()) || passConfirm.getText().isEmpty()) || fNameField.getText().isEmpty())
                            || lNameField.getText().isEmpty() || emailField.getText().isEmpty() || phoneField.getText().isEmpty()) {
                        emptyField.setVisible(true);
                        return;
                    } else {
                        for (Label alert : alerts) {
                            if (alert.isVisible())
                                return;
                        }
                    }
                    user.setPassword(passwordField.getText());
                    user.setFirstName(fNameField.getText());
                    user.setLastName(lNameField.getText());
                    user.setEmail(emailField.getText());
                    user.setPhoneNumber(phoneField.getText());
                    if (file != null) {
                        user.setProfile(file.toURI().toString());
                    }
                    new Alert(Alert.AlertType.INFORMATION, "profile successfully changed").show();
                });

                Button back = new Button("return");
                form.add(back, 1, 22);
                back.setOnAction(event -> {

                        App.getMainStage().setScene(new SellerPanel(new ScrollPane()));

                });

            }
        };
    }

    public Menu viewCart() {
        return new Menu(new ScrollPane()) {
            @Override
            public void init() {
                TableView<CartItem> cartItemTableView = new TableView<>();
                AnchorPane.setLeftAnchor(cartItemTableView, 400.0);

                cartItemTableView.setItems(FXCollections.observableArrayList(((Buyer)user).getCart().getCartItems()));

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
                    cartItemTableView.setItems(FXCollections.observableArrayList(((Buyer)user).getCart().getCartItems()));
                });

                Button decrease = new Button("decrease");
                decrease.setOnAction(event -> {
                    cartItemTableView.getSelectionModel().getSelectedItem().decreaseItem();
                    cartItemTableView.setItems(FXCollections.observableArrayList(((Buyer)user).getCart().getCartItems()));
                });

                Button delete = new Button("remove");
                delete.setOnAction(event -> {
                    ((Buyer) user).getCart().getCartItems().remove(cartItemTableView.getSelectionModel().getSelectedItem());
                    cartItemTableView.setItems(FXCollections.observableArrayList(((Buyer)user).getCart().getCartItems()));
                });

                Button back = new Button("return");
                back.setOnAction(event -> {

                        App.getMainStage().setScene(new BuyerPanel(new ScrollPane()));

                });

                Button purchase = new Button("purchase");
                purchase.setOnAction(event -> {
                        App.getMainStage().setScene(purchase());

                });

                buttons.getChildren().addAll(increase, decrease, delete, back, purchase);
                buttons.setSpacing(10);

                pane.getChildren().add(buttons);


            }
        };
    }

    public Menu purchase() {
        return new Menu(new ScrollPane()) {
            @Override
            public void init() {
                GridPane form = new GridPane();
                AnchorPane.setLeftAnchor(form, 300.0);
                AnchorPane.setTopAnchor(form, 100.0);

                form.add(new Text("first name : "), 0, 0);

                TextField firstName = new TextField();
                firstName.setPromptText("first name...");
                form.add(firstName, 1, 0);

                form.add(new Text("last name : "), 0, 1);

                TextField lastName = new TextField();
                lastName.setPromptText("last name...");
                form.add(lastName, 1, 1);

                form.add(new Text("phone number : "), 0, 2);

                TextField phoneNumber = new TextField();
                phoneNumber.setPromptText("phone number...");
                form.add(phoneNumber, 1, 2);

                form.add(new Text("address : "), 0, 3);

                TextArea address = new TextArea();
                address.setPromptText("address...");
                form.add(address, 1, 3);

                form.add(new Text("discount code (optional) : "), 0, 4);

                TextField discount = new TextField();
                discount.setPromptText("discount code...");
                form.add(discount, 1, 4);

                Button back = new Button("return");
                back.setOnAction(event -> {
                        App.getMainStage().setScene(viewCart());

                });

                Alert emptyField = new Alert(Alert.AlertType.ERROR, "please make sure all fields are filled.");
                Alert invalidCode = new Alert(Alert.AlertType.ERROR, "discount code you've entered is invalid");
                Alert unusableCode = new Alert(Alert.AlertType.ERROR, "you cannot use this code anymore");

                Button purchase = new Button("purchase");
                purchase.setOnAction(event -> {
                    Popup purchaseMethod=new Popup();

                    Button account=new Button("bank account");
                    account.setOnAction(actionEvent -> {

                            purchaseMethod.hide();
                            payByAccount(discount.getText(),lastName.getText()+","+firstName.getText(),address.getText(),phoneNumber.getText());

                    });

                    Button wallet=new Button("wallet");
                    wallet.setOnAction(actionEvent -> {
                        purchaseMethod.hide();
                            payByWallet(discount.getText(),lastName.getText()+","+firstName.getText(),address.getText(),phoneNumber.getText());

                    });

                    purchaseMethod.getContent().addAll(new Text("which method do you choose to pay with?"),account,wallet);
                    purchaseMethod.show(App.getMainStage());
                });
                form.add(purchase, 0, 5);
                form.add(back,1,5);

                pane.getChildren().add(form);
            }
        };
    }

    public void payByAccount(String discount,String name,String address,String phoneNumber)  {


        String response= null;
        try {
            App.getDataOutputStream().writeUTF("pay_by_account "+discount+" "+name+" "+address+" "+phoneNumber);
            App.getDataOutputStream().flush();
            response = App.getDataInputStream().readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (response.equals("success")){
            new Alert(Alert.AlertType.INFORMATION,"order submitted successfully").show();
        }else {
            new Alert(Alert.AlertType.ERROR,response).show();
        }
    }

    public void payByWallet(String discount,String name,String address,String phoneNumber){


        String response= null;
        try {
            App.getDataOutputStream().writeUTF("pay_by_wallet "+discount+" "+name+" "+address+" "+phoneNumber);
            App.getDataOutputStream().flush();
            response = App.getDataInputStream().readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (response.equals("success")){
            new Alert(Alert.AlertType.INFORMATION,"order submitted successfully").show();
        }else {
            new Alert(Alert.AlertType.ERROR,response).show();
        }
    }


    public Menu showDiscountCodes()  {
        return new Menu(new ScrollPane()) {
            @Override
            public void init() {
                TableView<OffWithCode> discountTable = new TableView<>();
                AnchorPane.setLeftAnchor(discountTable, 300.0);

                ObservableList<OffWithCode> discounts = FXCollections.observableArrayList();

                ArrayList<OffWithCode> discountArray=new ArrayList<>();
                for (OffWithCode offWithCode:OffWithCode.getAllDiscounts()){
                    //if (offWithCode.getApplyingAccounts().containsKey(User.getActiveUser()) && offWithCode.isActive()){
                        discountArray.add(offWithCode);
                    //}
                }
                discounts.addAll(discountArray);

                TableColumn<OffWithCode, String> codeColumn = new TableColumn<>("code");
                codeColumn.setCellValueFactory(new PropertyValueFactory<>("offCode"));

                TableColumn<OffWithCode, Integer> usageColumn = new TableColumn<>("remained usages");
                usageColumn.setCellValueFactory(new PropertyValueFactory<>("remainedUsages"));

                TableColumn<OffWithCode, String> dateColumn = new TableColumn<>("end date");
                dateColumn.setCellValueFactory(new PropertyValueFactory<>("formattedStopDate"));

                discountTable.getColumns().addAll(codeColumn, usageColumn, dateColumn);
                pane.getChildren().add(discountTable);

                Button back = new Button("return");
                back.setOnAction(event -> {

                        App.getMainStage().setScene(new BuyerPanel(new ScrollPane()));

                });
                pane.getChildren().add(back);
            }
        };
    }

    public Menu viewOrderHistory(){
        return new Menu(new ScrollPane()) {
            @Override
            public void init() {
                VBox content = new VBox();
                content.setSpacing(5);

                Button back = new Button("return");
                back.setOnAction(event -> {
                        App.getMainStage().setScene(new BuyerPanel(new ScrollPane()));

                });

                TableView<BuyLog> orderTable = new TableView<>();
                orderTable.setItems(FXCollections.observableArrayList(((Buyer) user).getOrderHistory()));

                TableColumn<BuyLog, String> dateColumn = new TableColumn<>("date");
                dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));

                TableColumn<BuyLog, String> idColumn = new TableColumn<>("id");
                idColumn.setCellValueFactory(new PropertyValueFactory<>("logId"));

                TableColumn<BuyLog, Float> moneyColumn = new TableColumn<>("purchased money");
                moneyColumn.setCellValueFactory(new PropertyValueFactory<>("purchasedMoney"));

                orderTable.getColumns().addAll(dateColumn, idColumn, moneyColumn);

                orderTable.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {

                            App.getMainStage().setScene(specificOrder(orderTable.getSelectionModel().getSelectedItem()));

                    }
                });

                content.getChildren().addAll(back, orderTable);
                pane.getChildren().add(content);
            }
        };
    }

    public Menu specificOrder(BuyLog order) {
        return new Menu(new ScrollPane()) {
            @Override
            public void init() {
                VBox content = new VBox();
                content.setSpacing(10);

                Button back = new Button("return");
                back.setOnAction(event -> {

                        App.getMainStage().setScene(viewOrderHistory());

                });

                content.getChildren().add(new Text("state: "+order.getPostStatus()));

                TableView<CartItem> cartTable = new TableView<>();
                cartTable.setItems(FXCollections.observableArrayList(order.getListOfProducts()));

                TableColumn<CartItem, Product> productColumn = new TableColumn<>("product");
                productColumn.setCellValueFactory(new PropertyValueFactory<>("item"));

                TableColumn<CartItem, Integer> numberColumn = new TableColumn<>("quantity");
                numberColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));

                TableColumn<CartItem, Float> priceColumn = new TableColumn<>("price per each");
                priceColumn.setCellValueFactory(new PropertyValueFactory<>("pricePerEach"));

                cartTable.getColumns().addAll(productColumn, numberColumn, priceColumn);

                content.getChildren().addAll(back, cartTable);
                content.getChildren().add(new Text("total price: " + (order.getPurchasedMoney() + order.getOmittedPrice())));
                content.getChildren().add(new Text("omitted price: " + order.getOmittedPrice()));
                content.getChildren().add(new Text("purchased price: " + order.getPurchasedMoney()));

                pane.getChildren().add(content);
            }
        };
    }

    public Menu balance() {
        return new Menu(new ScrollPane()) {
            @Override
            public void init() {
                Text credit=new Text("credit:   "+((Buyer)user).getCredit());

                TextField chargeField=new TextField();
                chargeField.setPromptText("charge amount...");

                Button charge=new Button("charge");
                charge.setOnAction(actionEvent -> {
                    try {
                        App.getDataOutputStream().writeUTF("charge "+chargeField.getText());
                        App.getDataOutputStream().flush();
                        String response=App.getDataInputStream().readUTF();
                        if (response.equals("fail")){
                            new Alert(Alert.AlertType.ERROR,App.getDataInputStream().readUTF()).show();
                        }else {
                            new Alert(Alert.AlertType.INFORMATION,"wallet charged Successfully").show();
                            App.getMainStage().setScene(balance());
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

                TextField withdrawField=new TextField();
                withdrawField.setPromptText("withdraw amount...");

                Button withdraw=new Button("withdraw");
                withdraw.setOnAction(actionEvent -> {
                    try {
                        App.getDataOutputStream().writeUTF("withdraw "+withdrawField.getText());
                        App.getDataOutputStream().flush();
                        String response=App.getDataInputStream().readUTF();
                        if (response.equals("fail")){
                            new Alert(Alert.AlertType.ERROR,App.getDataInputStream().readUTF()).show();
                        }else {
                            new Alert(Alert.AlertType.INFORMATION,"amount withdrawn successfully").show();
                            App.getMainStage().setScene(balance());
                        }
                    } catch (IOException  e) {
                        e.printStackTrace();
                    }
                });

                pane.getChildren().add(new VBox(credit,new HBox(chargeField,charge),new HBox(withdrawField,withdraw)));
            }
        };
    }

    public Menu viewSupporters() {
        return new Menu(new ScrollPane()) {
            @Override
            public void init() {
                TableView<Supporter> supporterTable=new TableView<>();
                supporterTable.setItems(FXCollections.observableArrayList(Supporter.getAllSupporters()));

                TableColumn<Supporter,String> nameColumn=new TableColumn<>("username");
                nameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));

                TableColumn<Supporter,Boolean> logColumn=new TableColumn<>("has logged in");
                logColumn.setCellValueFactory(new PropertyValueFactory<>("loggedIn"));

                supporterTable.getColumns().addAll(nameColumn,logColumn);

                Button back=new Button("return");
                back.setOnAction(actionEvent -> {

                        App.getMainStage().setScene(new BuyerPanel(new ScrollPane()));

                });

                pane.getChildren().add(new VBox(supporterTable,back));
            }
        };
    }

    public void setFile(File file) {
        this.file = file;
    }
}
