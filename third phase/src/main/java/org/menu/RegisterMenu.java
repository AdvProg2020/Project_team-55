package org.menu;

import Model.Manager;
import Model.User;
import com.sun.javafx.iio.gif.GIFImageLoaderFactory;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import org.example.App;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class RegisterMenu extends Menu {
    private File file;
    private Menu previousScene;

    public RegisterMenu(ScrollPane root, Menu previousScene){
        super(root);
        this.previousScene = previousScene;
    }

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

        Text username = new Text("username :");
        form.add(username, 0, 0);

        TextField usernameField = new TextField();
        usernameField.setPromptText("enter username...");
        form.add(usernameField, 1, 0);

        Label takenUsername = new Label("*this username is already taken");
        form.add(takenUsername, 1, 1, 2, 1);
        alerts.add(takenUsername);

        Label invalidUsername = new Label("*username can only contain a-z A-Z _ 0-9");
        form.add(invalidUsername, 1, 1, 2, 1);
        alerts.add(invalidUsername);

        usernameField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                takenUsername.setVisible(User.getAccountByUserName(newValue) != null);
                invalidUsername.setVisible(!newValue.matches("\\w+"));
            }
        });

        Text password = new Text("password : ");
        form.add(password, 0, 2);

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("enter password...");
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
        form.add(fNameField, 1, 6);

        Text lName = new Text("last name : ");
        form.add(lName, 0, 8);

        TextField lNameField = new TextField();
        lNameField.setPromptText("last name...");
        form.add(lNameField, 1, 8);

        Text email = new Text("Email : ");
        form.add(email, 0, 10);

        TextField emailField = new TextField();
        emailField.setPromptText("Email...");
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

        Text credit = new Text("initial credit : ");
        form.add(credit, 0, 14);

        TextField creditField = new TextField();
        creditField.setPromptText("credit...");
        form.add(creditField, 1, 14);

        Button getImage = new Button("upload profile picture");
        form.add(getImage, 0, 16);

        FileChooser imageChooser = new FileChooser();
        getImage.setOnAction(event -> setFile(imageChooser.showOpenDialog(App.getMainStage())));

        Text corporate = new Text("corporate : ");
        corporate.setVisible(false);
        form.add(corporate, 0, 19);

        TextField corporateField = new TextField();
        corporateField.setPromptText("corporate...");
        corporateField.setVisible(false);
        form.add(corporateField, 1, 19);

        Text role = new Text("role : ");
        form.add(role, 0, 17);

        ChoiceBox<String> roles = new ChoiceBox<>();
        ObservableList<String> options = FXCollections.observableArrayList();
        options.addAll("seller", "buyer");
        roles.setItems(options);
        roles.setOnAction(event -> {
            if (roles.getSelectionModel().getSelectedItem().equals("seller")) {
                corporate.setVisible(true);
                corporateField.setVisible(true);
            } else {
                corporate.setVisible(false);
                corporateField.setVisible(false);
            }
        });
        form.add(roles, 1, 17);

        alerts.forEach((alert) -> alert.setVisible(false));

        Label emptyField = new Label("*please make sure no field is left empty");
        emptyField.setVisible(false);
        form.add(emptyField, 1, 21, 2, 1);

        Button submit = new Button("submit");
        form.add(submit, 1, 20);
        submit.setOnAction(event -> {
            emptyField.setVisible(false);
            if ((((usernameField.getText().isEmpty() || passwordField.getText().isEmpty()) || passConfirm.getText().isEmpty()) || creditField.getText().isEmpty() || fNameField.getText().isEmpty())
                    || lNameField.getText().isEmpty() || emailField.getText().isEmpty() || phoneField.getText().isEmpty() || roles.getSelectionModel().isEmpty()) {
                emptyField.setVisible(true);
                return;
            } else if (roles.getSelectionModel().getSelectedItem().equals("seller") && corporateField.getText().isEmpty()) {
                emptyField.setVisible(true);
                return;
            } else {
                for (Label alert : alerts) {
                    if (alert.isVisible())
                        return;
                }
            }

            try {
                if (roles.getSelectionModel().getSelectedItem().equals("buyer")) {
                    App.getDataOutputStream().writeUTF("signUp " + "buyer, " + usernameField.getText() + ", " + fNameField.getText() + ", " + lNameField.getText() + ", " + emailField.getText() +
                            ", " + phoneField.getText() + ", " + passwordField.getText() + ", " + creditField.getText() + ", " + file.toURI().toString());
                    App.getDataOutputStream().flush();
                    App.setSignedIn(true);
                    App.getMainStage().setScene(new BuyerPanel(new ScrollPane()));
                } else {
                    App.getDataOutputStream().writeUTF("signUp " + "seller, " + usernameField.getText() + ", " + fNameField.getText() + ", " + lNameField.getText() + ", " + emailField.getText() +
                            ", " + phoneField.getText() + ", " + passwordField.getText() + ", " + creditField.getText() + ", " + ", " + file.toURI().toString() + ", " + corporateField.getText());
                    App.getDataOutputStream().flush();
                    App.setSignedIn(true);
                    App.getMainStage().setScene(new SellerPanel(new ScrollPane()));
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        Button back = new Button("return");
        form.add(back, 1, 22);
        back.setOnAction(event -> App.getMainStage().setScene(previousScene));
    }

    public Menu submitManager() {
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

                Text username = new Text("username :");
                form.add(username, 0, 0);

                TextField usernameField = new TextField();
                usernameField.setPromptText("enter username...");
                form.add(usernameField, 1, 0);

                Label takenUsername = new Label("*this username is already taken");
                form.add(takenUsername, 1, 1, 2, 1);
                alerts.add(takenUsername);

                Label invalidUsername = new Label("*username can only contain a-z A-Z _ 0-9");
                form.add(invalidUsername, 1, 1, 2, 1);
                alerts.add(invalidUsername);

                usernameField.textProperty().addListener(new ChangeListener<String>() {
                    @Override
                    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                        takenUsername.setVisible(User.getAccountByUserName(newValue) != null);
                        invalidUsername.setVisible(!newValue.matches("\\w+"));
                    }
                });

                Text password = new Text("password : ");
                form.add(password, 0, 2);

                PasswordField passwordField = new PasswordField();
                passwordField.setPromptText("enter password...");
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
                form.add(fNameField, 1, 6);

                Text lName = new Text("last name : ");
                form.add(lName, 0, 8);

                TextField lNameField = new TextField();
                lNameField.setPromptText("last name...");
                form.add(lNameField, 1, 8);

                Text email = new Text("Email : ");
                form.add(email, 0, 10);

                TextField emailField = new TextField();
                emailField.setPromptText("Email...");
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
                form.add(getImage, 0, 14);

                FileChooser imageChooser = new FileChooser();

                getImage.setOnAction(event -> setFile(imageChooser.showOpenDialog(App.getMainStage())));

                form.add(new Text("least amount of money in wallet :"),0,15);

                TextField walletField=new TextField();
                walletField.setPromptText("least amount...");
                walletField.textProperty().addListener(new ChangeListener<String>() {
                    @Override
                    public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                        if (!t1.matches("\\d*"))
                            walletField.setText(s);
                    }
                });
                form.add(walletField,1,15);

                form.add(new Text("freelance: "),0,16);

                TextField freeLanceField=new TextField();
                freeLanceField.setPromptText("freelance...");
                freeLanceField.textProperty().addListener(new ChangeListener<String>() {
                    @Override
                    public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                        if (!t1.matches("\\d*"))
                            freeLanceField.setText(s);
                    }
                });
                form.add(freeLanceField,1,16);

                Label emptyField = new Label("*please make sure no field is left empty");
                emptyField.setVisible(false);
                form.add(emptyField, 1, 21, 2, 1);

                for (Label label : alerts) {
                    label.setVisible(false);
                }

                Button submit = new Button("submit");
                form.add(submit, 1, 20);
                submit.setOnAction(event -> {
                    emptyField.setVisible(false);
                    if ((((usernameField.getText().isEmpty() || passwordField.getText().isEmpty()) || passConfirm.getText().isEmpty()) || fNameField.getText().isEmpty())
                            || lNameField.getText().isEmpty() || emailField.getText().isEmpty() || phoneField.getText().isEmpty() || freeLanceField.getText().isEmpty()
                            || walletField.getText().isEmpty() || file == null) {
                        emptyField.setVisible(true);
                        return;
                    } else {
                        for (Label alert : alerts) {
                            if (alert.isVisible())
                                return;
                        }
                    }
                    try {
                        App.getDataOutputStream().writeUTF("mainManager "+usernameField.getText()+", "+fNameField.getText()+", "+lNameField.getText()+", "+
                                emailField.getText()+", "+phoneField.getText()+", "+passwordField.getText()+", "+file.toURI().toString()+", "+walletField.getText()+", "+freeLanceField.getText());

                        App.getDataOutputStream().flush();
                        App.getMainStage().setScene(new MainPage(new ScrollPane()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

            }
        };
    }

    public void setFile(File input) {
        this.file = input;
    }

}
