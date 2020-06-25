package org.menu;

import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginPanel extends Menu{
    public LoginPanel(ScrollPane root) {
        super(root);
    }
    String olgo = ("(\\w)+");
    int counter1 = 0;
    int counter2 = 0;
    int counter3 = 0;
    int counter4 = 0;
    @Override
    public void init() {

        Text passText=new Text("password");
        passText.relocate(30,70);

        PasswordField passwordField=new PasswordField();
        passwordField.relocate(70,70);
        passwordField.setPromptText("enter your password...");
        String getPass=passwordField.getText();
        passwordField.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                Pattern pattern = Pattern.compile(olgo);
                Matcher matcher = pattern.matcher(getPass);

                if (!matcher.matches()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, " password can only contain a-z A-Z _ 0-9.");
                    alert.show();

                }else {
                    counter1++;

                }

            }
        });


        Text user=new Text("username");
        user.relocate(30,50);
        TextField username=new TextField();
        username.relocate(70,50);
        username.setPromptText("enter username...");


        String user_String=username.getText();

        Pattern pattern1 = Pattern.compile(olgo);
        Matcher matcher1 = pattern1.matcher(user_String);

            if (!matcher1.matches()) {
                Alert alert = new Alert(Alert.AlertType.ERROR, " username can only contain a-z A-Z _ 0-9 .");
                alert.show();

            } else {counter2++;}


            if (counter1 == 1 && counter2 == 1) {
                Alert alert=new Alert(Alert.AlertType.INFORMATION,"Login was successful ♥");

                alert.show();
            }

        pane.getChildren().addAll(passText,passwordField,user,username);


            }//inIn
}
