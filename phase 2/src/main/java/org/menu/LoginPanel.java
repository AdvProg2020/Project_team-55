package org.menu;

import Model.User;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import org.example.App;

import java.security.acl.Acl;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginPanel extends Menu{
    public LoginPanel(ScrollPane root,Menu previousScene) {
        super(root);
        this.previousScene=previousScene;
    }
    private String olgo = ("(\\w)*");
    private Menu previousScene;
    @Override
    public void init() {

        GridPane form=new GridPane();

        Text passText=new Text("password");
        form.add(passText,0,2);

        PasswordField passwordField=new PasswordField();
        form.add(passwordField,1,2);
        passwordField.setPromptText("enter your password...");

        Label invalidPass=new Label(" password can only contain a-z A-Z _ 0-9.");
        form.add(invalidPass,1,3);
        invalidPass.setVisible(false);

        passwordField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                Pattern pattern = Pattern.compile(olgo);
                Matcher matcher = pattern.matcher(newValue);

                if (!matcher.matches()) {
                    invalidPass.setVisible(true);

                }else {
                   invalidPass.setVisible(false);
                }
            }
        } );


        Text user=new Text("username");
        form.add(user,0,0);
        TextField username=new TextField();
        form.add(username,1,0);
        username.setPromptText("enter username...");

        Label invalidUser=new Label("username can only contain a-z A-Z _ 0-9 .");
        invalidUser.setVisible(false);
        form.add(invalidUser,1,1);

        username.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                Pattern pattern1 = Pattern.compile(olgo);
                Matcher matcher1 = pattern1.matcher(newValue);

                if (!matcher1.matches()) {
                    invalidUser.setVisible(true);
                } else {invalidUser.setVisible(false);}
            }
        });

        Button submit=new Button("login");
        form.add(submit,0,4);
        submit.setOnAction(event -> {
            if (invalidUser.isVisible() || invalidPass.isVisible()){
               return;
            }else if (username.getText().isEmpty() || passwordField.getText().isEmpty()){
                new Alert(Alert.AlertType.ERROR,"please make sure to fill all the fields").show();
            }else if (User.getAccountByUserName(username.getText())==null){
                new Alert(Alert.AlertType.ERROR,"no user with this username is found").show();
            }else if (!User.getAccountByUserName(username.getText()).getPassword().equals(passwordField.getText())){
                new Alert(Alert.AlertType.ERROR,"wrong password").show();
            }else {
                Alert alert=new Alert(Alert.AlertType.INFORMATION,"Login was successful ♥");
                alert.show();

                User.setActiveUser(User.getAccountByUserName(username.getText()));
                previousScene.manifestNavbar();
                App.getMainStage().setScene(previousScene);
            }
        });



        pane.getChildren().addAll(form);


            }//inIn
}
