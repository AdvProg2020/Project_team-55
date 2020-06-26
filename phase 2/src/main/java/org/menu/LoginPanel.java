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
    public LoginPanel(ScrollPane root,Scene previousScene) {
        super(root);
        this.previousScene=previousScene;
    }
    private String olgo = ("(\\w)*");
    private Scene previousScene;
    @Override
    public void init() {

        Text passText=new Text("password");
        passText.relocate(30,70);

        PasswordField passwordField=new PasswordField();
        passwordField.relocate(70,70);
        passwordField.setPromptText("enter your password...");

        Label invalidPass=new Label(" password can only contain a-z A-Z _ 0-9.");
        invalidPass.relocate(70,75);
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
        user.relocate(30,50);
        TextField username=new TextField();
        username.relocate(70,50);
        username.setPromptText("enter username...");

        Label invalidUser=new Label("username can only contain a-z A-Z _ 0-9 .");
        invalidUser.setVisible(false);
        invalidUser.relocate(70,55);

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
        submit.relocate(30,85);
        submit.setOnAction(event -> {
            if (invalidUser.isVisible() || invalidPass.isVisible()){
               return;
            }else if (username.getText().isEmpty() || passwordField.getText().isEmpty()){
                new Alert(Alert.AlertType.ERROR,"please make sure to fill all the fields").show();
            }else if (User.getAccountByUserName(username.getText())==null){
                new Alert(Alert.AlertType.ERROR,"no user with this username is found").show();
            }else if (!User.getAccountByUserName(username.getText()).getPassword().equals(passwordField.getText())){
                new Alert(Alert.AlertType.ERROR,"wrong password");
            }else {
                Alert alert=new Alert(Alert.AlertType.INFORMATION,"Login was successful ♥");
                alert.show();

                User.setActiveUser(User.getAccountByUserName(username.getText()));
                App.getMainStage().setScene(previousScene);
            }
        });



        pane.getChildren().addAll(passText,passwordField,invalidPass,user,username,invalidUser,submit);


            }//inIn
}
