package org.menu;

import Model.Seller;
import Model.User;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import org.example.App;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class SupporterPanel extends Menu{
    User user;
    File file;

    public SupporterPanel(ScrollPane root) {
        super(root);
    }

    @Override
    public void init() {

        try {
            App.getDataOutputStream().writeUTF("get active user");
            App.getDataOutputStream().flush();
            user=(User) App.getObjectInputStream().readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        VBox commands = new VBox();
        AnchorPane.setTopAnchor(commands, 100.0);
        AnchorPane.setBottomAnchor(commands, 500.0);
        commands.setSpacing(15);

        ImageView profile = new ImageView(user.getProfile());
        profile.resize(300, 300);
        profile.setFitWidth(300);
        profile.setFitHeight(300);

        Button editInfo=new Button("edit profile");
        editInfo.setOnAction(actionEvent -> {

                App.getMainStage().setScene(editProfile());

        });

        Button viewChats=new Button("chats");


        commands.getChildren().addAll(profile,editInfo,viewChats);
        pane.getChildren().add(commands);

        GridPane info = new GridPane();
        info.setPrefSize(800, 300);
        info.setHgap(300);
        info.setVgap(40);
        info.setLayoutX(400);
        info.setLayoutY(200);
        info.setAlignment(Pos.CENTER);

        info.add(new Text("seller: "+user.getUsername()),0,0);

        Text firstName = new Text("first name:");
        firstName.getStyleClass().add("info-grid");
        info.add(firstName, 0, 1);

        Text firstNameValue = new Text(( user).getFirstName());
        firstNameValue.getStyleClass().add("info-grid");
        info.add(firstNameValue, 1, 1);

        Text lastName = new Text("last name:");
        lastName.getStyleClass().add("info-grid");
        info.add(lastName, 0, 2);

        Text lastNameValue = new Text(( user).getLastName());
        lastNameValue.getStyleClass().add("info-grid");
        info.add(lastNameValue, 1, 2);

        Text phone = new Text("phone number:");
        phone.getStyleClass().add("info-grid");
        info.add(phone, 0, 3);

        Text phoneValue = new Text(( user).getPhoneNumber());
        phoneValue.getStyleClass().add("info-grid");
        info.add(phoneValue, 1, 3);

        Text email = new Text("Email:");
        email.getStyleClass().add("info-grid");
        info.add(email, 0, 4);

        Text emailValue = new Text((user).getEmail());
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
                File file = null;

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
                    if (file != null)
                        user.setProfile(file.toURI().toString());
                    new Alert(Alert.AlertType.INFORMATION, "profile successfully edited").show();

                        App.getMainStage().setScene(new ManagerPanel(new ScrollPane()));

                });

                Button back = new Button("return");
                form.add(back, 1, 22);
                back.setOnAction(event -> {

                        App.getMainStage().setScene(new ManagerPanel(new ScrollPane()));

                });

                App.getMainStage().getScene().getStylesheets().add("userpanel.css");
                pane.getStyleClass().add("pane");
            }
        };
    }

    public void setFile(File file) {
        this.file = file;
    }
}
