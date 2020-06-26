package org.menu;

import Model.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.SubScene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTreeTableCell;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import org.example.App;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Random;

public class ManagerPanel extends Menu {

    SubScene discountSub, userSub;
    private File file;

    public ManagerPanel(ScrollPane root) {
        super(root);
    }

    public void init() {
        getStylesheets().add("userpanel.css");
        getCommandList();
        getInfoGrid();
    }

    public void getCommandList() {
        VBox commands = new VBox();
        commands.setPrefSize(200, 600);
        commands.setLayoutY(100);

        AnchorPane.setBottomAnchor(commands, 0.0);

        ImageView profile = new ImageView(User.getActiveUser().getProfile());
        profile.resize(300, 300);
        profile.setFitWidth(300);
        profile.setFitHeight(300);

        Button edit = new Button("edit profile");
        edit.getStyleClass().add("command");
        edit.setOnAction(event -> App.getMainStage().setScene(editProfile()));

        Button manageUsers = new Button("manage users");
        manageUsers.setPrefSize(200, 50);
        manageUsers.setOnAction(event -> {
            App.getMainStage().setScene(manageUsers());
        });
        manageUsers.getStyleClass().add("command");

        Button manageProducts = new Button("manage all products");
        manageProducts.setPrefSize(200, 50);
        manageProducts.getStyleClass().add("command");

        Button manageCategories = new Button("manage categories");
        manageCategories.setPrefSize(200, 50);
        manageCategories.getStyleClass().add("command");
        manageCategories.setOnAction(event -> App.getMainStage().setScene(manageCategories()));

        Button createDiscount = new Button("create discount code");
        createDiscount.setPrefSize(200, 50);
        createDiscount.getStyleClass().add("command");
        createDiscount.setOnAction(event -> {
            App.getMainStage().setScene(createDiscount());
        });

        Button viewDiscounts = new Button("view discount codes");
        viewDiscounts.setPrefSize(200, 50);
        viewDiscounts.getStyleClass().add("command");
        viewDiscounts.setOnAction(event -> App.getMainStage().setScene(manageDiscounts()));

        Button manageRequests = new Button("manageRequests");
        manageRequests.setPrefSize(200, 50);
        manageRequests.getStyleClass().add("command");
        manageRequests.setOnAction(event -> App.getMainStage().setScene(manageRequests()));

        commands.getChildren().addAll(profile, edit, manageUsers, manageCategories, manageProducts,
                createDiscount, viewDiscounts, manageRequests);

        pane.getChildren().add(commands);
    }

    public void getInfoGrid() {
        GridPane info = new GridPane();
        info.setPrefSize(800, 300);
        info.setHgap(300);
        info.setVgap(40);
        info.setLayoutX(400);
        info.setLayoutY(200);
        info.setAlignment(Pos.CENTER);

        info.add(new Text("manager: "+User.getActiveUser().getUsername()),0,0);

        Text firstName = new Text("first name:");
        firstName.getStyleClass().add("info-grid");
        info.add(firstName, 0, 1);

        Text firstNameValue = new Text(User.getActiveUser().getFirstName());
        firstNameValue.getStyleClass().add("info-grid");
        info.add(firstNameValue, 1, 1);

        Text lastName = new Text("last name:");
        lastName.getStyleClass().add("info-grid");
        info.add(lastName, 0, 2);

        Text lastNameValue = new Text(User.getActiveUser().getLastName());
        lastNameValue.getStyleClass().add("info-grid");
        info.add(lastNameValue, 1, 2);

        Text phone = new Text("phone number:");
        phone.getStyleClass().add("info-grid");
        info.add(phone, 0, 3);

        Text phoneValue = new Text(User.getActiveUser().getPhoneNumber());
        phoneValue.getStyleClass().add("info-grid");
        info.add(phoneValue, 1, 3);

        Text email = new Text("Email:");
        email.getStyleClass().add("info-grid");
        info.add(email, 0, 4);

        Text emailValue = new Text(User.getActiveUser().getEmail());
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
                passwordField.setText(User.getActiveUser().getPassword());
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
                passConfirm.setText(User.getActiveUser().getPassword());
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
                fNameField.setText(User.getActiveUser().getFirstName());
                form.add(fNameField, 1, 6);

                Text lName = new Text("last name : ");
                form.add(lName, 0, 8);

                TextField lNameField = new TextField();
                lNameField.setPromptText("last name...");
                lNameField.setText(User.getActiveUser().getLastName());
                form.add(lNameField, 1, 8);

                Text email = new Text("Email : ");
                form.add(email, 0, 10);

                TextField emailField = new TextField();
                emailField.setPromptText("Email...");
                emailField.setText(User.getActiveUser().getEmail());
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
                phoneField.setText(User.getActiveUser().getPhoneNumber());
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
                    User.getActiveUser().setPassword(passwordField.getText());
                    User.getActiveUser().setFirstName(fNameField.getText());
                    User.getActiveUser().setLastName(lNameField.getText());
                    User.getActiveUser().setEmail(emailField.getText());
                    User.getActiveUser().setPhoneNumber(phoneField.getText());
                    if (file != null)
                        User.getActiveUser().setProfile(file.toURI().toString());
                    new Alert(Alert.AlertType.INFORMATION, "profile successfully edited").show();
                    App.getMainStage().setScene(new ManagerPanel(new ScrollPane()));
                });

                Button back = new Button("return");
                form.add(back, 1, 22);
                back.setOnAction(event -> App.getMainStage().setScene(new ManagerPanel(new ScrollPane())));

                App.getMainStage().getScene().getStylesheets().add("userpanel.css");
                pane.getStyleClass().add("pane");
            }
        };
    }


    public Menu manageUsers() {
        return new Menu(new ScrollPane()) {
            @Override
            public void init() {
                HBox list = new HBox();

                TableView<User> userList = new TableView<>();

                TableColumn<User, String> name = new TableColumn<>("username");
                name.setPrefWidth(70);
                name.setCellValueFactory(new PropertyValueFactory<>("username"));

                TableColumn<User, String> fNameColumn=new TableColumn<>("first name");
                fNameColumn.setPrefWidth(70);
                fNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));

                TableColumn<User,String> lNameColumn=new TableColumn<>("last name");
                lNameColumn.setPrefWidth(70);
                lNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));

                TableColumn<User,String> emailColumn=new TableColumn<>("email");
                emailColumn.setPrefWidth(150);
                emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));

                TableColumn<User, String> role = new TableColumn<>("role");
                role.setCellValueFactory(new PropertyValueFactory<>("role"));

                ObservableList<User> users = FXCollections.observableArrayList();
                users.addAll(User.getUsers());
                userList.setItems(users);
                userList.getColumns().addAll(name, fNameColumn,lNameColumn,emailColumn,role);

                Button cancel = new Button("return");
                cancel.setOnAction(event -> {
                    App.getMainStage().setScene(new ManagerPanel(new ScrollPane()));
                });
                AnchorPane.setBottomAnchor(cancel, 0.0);
                pane.getChildren().add(cancel);

                Button delete = new Button("delete selected");
                delete.setOnAction(event -> {
                    if (userList.getSelectionModel().getSelectedItem() != null) {
                        User.getUsers().remove(userList.getSelectionModel().getSelectedItem());
                        new Alert(Alert.AlertType.INFORMATION, "user successfully deleted").show();
                        userList.setItems(FXCollections.observableArrayList(User.getUsers()));
                    } else {
                        new Alert(Alert.AlertType.ERROR, "no user is selected!");
                    }

                });

                Button createManager=new Button("create manager");
                createManager.setOnAction(event -> App.getMainStage().setScene(createManager()));

                list.getChildren().addAll(cancel,delete,createManager,userList);

                pane.getChildren().add(list);
            }
        };
    }

    public Menu createManager(){
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
                            || lNameField.getText().isEmpty() || emailField.getText().isEmpty() || phoneField.getText().isEmpty() || file == null) {
                        emptyField.setVisible(true);
                        return;
                    } else {
                        for (Label alert : alerts) {
                            if (alert.isVisible())
                                return;
                        }
                    }
                    new Manager(usernameField.getText(), fNameField.getText(), lNameField.getText(), emailField.getText(), phoneField.getText(),
                            passwordField.getText(), file.toURI().toString());
                    new Alert(Alert.AlertType.INFORMATION,"manager successfully added");
                    App.getMainStage().setScene(manageUsers());
                });

                Button back = new Button("return");
                form.add(back, 1, 22);
                back.setOnAction(event -> App.getMainStage().setScene(manageUsers()));
            }
        };
    }


    public Menu manageCategories() {
        return new Menu(new ScrollPane()) {
            @Override
            public void init() {
                HBox categories = new HBox();
                categories.setFillHeight(true);
                AnchorPane.setLeftAnchor(categories, 300.0);

                TreeTableView<Category> categoryTree = new TreeTableView<>();
                categories.getChildren().add(categoryTree);

                setTreeTableItems(categoryTree);

                TreeTableColumn<Category, String> name = new TreeTableColumn<>("category");
                name.setCellValueFactory(new TreeItemPropertyValueFactory<>("name"));
                name.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());
                categoryTree.getColumns().add(name);


                HBox buttons = new HBox();
                buttons.setSpacing(10);

                Button back = new Button("return");
                back.setOnAction(event -> App.getMainStage().setScene(new ManagerPanel(new ScrollPane())));

                Button delete = new Button("delete");
                delete.setOnAction(event -> {
                    if (categoryTree.getSelectionModel().getSelectedItem() != null)
                        deleteCategory(categoryTree.getSelectionModel().getSelectedItem().getValue());
                    else
                        new Alert(Alert.AlertType.ERROR, "no category is selected");
                });

                Button add = new Button("add");
                add.setOnAction(event -> App.getMainStage().setScene(getNewCategory()));

                Button edit = new Button("edit");
                edit.setOnAction(event ->
                        App.getMainStage().setScene(editCategory(categoryTree.getSelectionModel().getSelectedItem().getValue())));

                buttons.getChildren().addAll(back, delete, add, edit);

                pane.getChildren().addAll(new HBox(buttons,categoryTree));


            }
        };
    }


    public void setTreeTableItems(TreeTableView<Category> tree) {
        TreeItem<Category> root = new TreeItem<>(new Category("categories", null, new ArrayList<String>()));
        Category.deleteCategory(root.getValue());
        tree.setRoot(root);
        tree.setShowRoot(false);

        TreeItem<Category> parent;

        for (Category category : Category.getAllCategories()) {
            if (category.getParentCategory() == null) {
                parent = new TreeItem(category);
                root.getChildren().add(parent);
                for (Category sub : category.getSubCategories()) {
                    parent.getChildren().add(new TreeItem<>(sub));
                }
            }
        }
    }


    public void deleteCategory(Category category) {

        Category.deleteCategory(category);
        new Alert(Alert.AlertType.INFORMATION, "category successfully deleted");
        App.getMainStage().setScene(manageCategories());
    }

    public Menu editCategory(Category category) {
        return new Menu(new ScrollPane()) {
            @Override
            public void init() {
                GridPane grid = new GridPane();


                Text name = new Text("name:");
                grid.add(name, 0, 0);

                TextField nameField = new TextField();
                nameField.setText(category.getName());
                grid.add(nameField, 1, 0);

                Text parent = new Text("choose parent category:");
                grid.add(parent, 0, 1);

                ChoiceBox<String> parentChoice = new ChoiceBox<>();

                ObservableList<String> parentCategories = FXCollections.observableArrayList();
                for (Category category : Category.getAllCategories()) {
                    if (category.getParentCategory() == null)
                        parentCategories.add(category.getName());
                }
                parentChoice.getItems().add("none");
                parentChoice.getItems().addAll(parentCategories);
                parentChoice.getSelectionModel().select(category.getName());
                grid.add(parentChoice, 1, 1);

                Text specialAttributes = new Text("special attributes:");
                grid.add(specialAttributes, 0, 2);

                Text selectedAttributes = new Text("name\nbrand");
                grid.add(selectedAttributes, 1, 2);

                VBox attributeBox = new VBox();
                grid.add(attributeBox, 1, 3);

                for (String attribute : category.getSpecialAttributes()) {
                    TextField att = new TextField();
                    att.setText(attribute);
                    attributeBox.getChildren().add(att);
                }

                Button more = new Button("+");
                more.setOnAction(event -> {
                    attributeBox.getChildren().add(new TextField());
                });

                grid.add(more, 1, 4);

                Button back = new Button("return");
                back.setOnAction(event -> App.getMainStage().setScene(manageCategories()));
                grid.add(back, 0, 5);

                Button add = new Button("submit");

                Label blankField = new Label("*please make sure no fields are blank.");
                blankField.setVisible(false);
                grid.add(blankField, 1, 6);
                add.setOnAction(event -> {
                    blankField.setVisible(false);
                    if (nameField.getText().isEmpty() || Category.getCategoryByName(nameField.getText()) != null)
                        blankField.setVisible(true);
                    else if (parentChoice.getSelectionModel().isEmpty())
                        blankField.setVisible(true);
                    else if (((TextField) attributeBox.getChildren().get(0)).getText().isEmpty())
                        blankField.setVisible(true);
                    else {
                        category.setName(nameField.getText());

                        ArrayList<String> specialAttribute = new ArrayList<>();
                        for (int i = 0; i < attributeBox.getChildren().size(); i++) {
                            specialAttribute.add(((TextField) attributeBox.getChildren().get(i)).getText());
                        }

                        category.setSpecialAttributes(specialAttribute);

                        new Alert(Alert.AlertType.INFORMATION, "category successfully edited");
                        App.getMainStage().setScene(manageCategories());
                    }
                });
                grid.add(add, 1, 5);

                pane.getChildren().add(grid);
            }
        };
    }

    public Menu getNewCategory() {
        return new Menu(new ScrollPane()) {
            @Override
            public void init() {
                GridPane grid = new GridPane();
                grid.setHgap(40);
                grid.setVgap(40);


                Text name = new Text("name:");
                grid.add(name, 0, 0);

                TextField nameField = new TextField();
                grid.add(nameField, 1, 0);

                Text parent = new Text("choose parent category:");
                grid.add(parent, 0, 1);

                ChoiceBox<String> parentChoice = new ChoiceBox<>();

                ObservableList<String> parentCategories = FXCollections.observableArrayList();
                for (Category category : Category.getAllCategories()) {
                    if (category.getParentCategory() == null)
                        parentCategories.add(category.getName());
                }
                parentChoice.getItems().add("none");
                parentChoice.getItems().addAll(parentCategories);
                grid.add(parentChoice, 1, 1);

                Text specialAttributes = new Text("special attributes:");
                grid.add(specialAttributes, 0, 2);

                Text selectedAttributes = new Text("name\nbrand");
                grid.add(selectedAttributes, 1, 2);

                VBox attributeBox = new VBox();
                grid.add(attributeBox, 1, 3);

                attributeBox.getChildren().add(new TextField());
                Button more = new Button("+");
                more.setOnAction(event -> {
                    attributeBox.getChildren().add(new TextField());
                });

                grid.add(more, 1, 4);

                Button back = new Button("return");
                back.setOnAction(event -> App.getMainStage().setScene(manageCategories()));
                grid.add(back, 0, 5);

                Button add = new Button("submit");

                Label blankField = new Label("*please make sure no fields are blank.");
                blankField.setVisible(false);
                grid.add(blankField, 1, 6);
                add.setOnAction(event -> {
                    blankField.setVisible(false);
                    if (nameField.getText().isEmpty() || Category.getCategoryByName(nameField.getText()) != null)
                        blankField.setVisible(true);
                    else if (parentChoice.getSelectionModel().isEmpty())
                        blankField.setVisible(true);
                    else if (((TextField) attributeBox.getChildren().get(0)).getText().isEmpty())
                        blankField.setVisible(true);
                    else {
                        addCategory(nameField.getText(), Category.getCategoryByName(parentChoice.getSelectionModel().getSelectedItem().toString()), attributeBox);
                        new Alert(Alert.AlertType.INFORMATION, "category successfully added");
                        App.getMainStage().setScene(manageCategories());
                    }
                });
                grid.add(add, 1, 5);

                pane.getChildren().add(grid);
            }
        };
    }


    public void addCategory(String name, Category parent, VBox attributes) {
        ArrayList<String> specialAttribute = new ArrayList<>();
        for (int i = 0; i < attributes.getChildren().size(); i++) {
            specialAttribute.add(((TextField) attributes.getChildren().get(i)).getText());
        }
        new Category(name, parent, specialAttribute);
    }


    public Menu createDiscount() {

        return new Menu(new ScrollPane()) {
            @Override
            public void init() {

                GridPane form = new GridPane();
                form.setHgap(200);
                form.setVgap(20);
                form.setPrefWidth(900);
                AnchorPane.setRightAnchor(form, 100.0);
                AnchorPane.setLeftAnchor(form, 100.0);
                AnchorPane.setTopAnchor(form, 100.0);

                String discountCode;
                while (OffWithCode.getOffByCode(discountCode = Integer.toString(new Random().nextInt(899999) + 100000)) != null)
                    ;

                Text code = new Text("generated code:");
                form.add(code, 0, 0);

                Text codeValue = new Text();
                codeValue.setText(discountCode);
                form.add(codeValue, 1, 0);

                Text percent = new Text("discount percentage:");
                form.add(percent, 0, 1);

                TextField percentField = new TextField();
                form.add(percentField, 1, 1);

                percentField.textProperty().addListener(new ChangeListener<String>() {
                    @Override
                    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                        if (!newValue.matches("\\d{0,2}")) {
                            percentField.setText(oldValue);
                        }
                    }
                });

                Text max = new Text("maximum amount:");
                form.add(max, 0, 2);

                TextField maxField = new TextField();
                form.add(maxField, 1, 2);

                maxField.textProperty().addListener(new ChangeListener<String>() {
                    @Override
                    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                        if (!newValue.matches("\\d*"))
                            maxField.setText(oldValue);
                    }
                });

                Text usageLimit = new Text("usage limit:");
                form.add(usageLimit, 0, 3);

                TextField usageField = new TextField();
                form.add(usageField, 1, 3);

                usageField.textProperty().addListener(new ChangeListener<String>() {
                    @Override
                    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                        if (!newValue.matches("\\d*"))
                            usageField.setText(oldValue);
                    }
                });

                Text startDate = new Text("start date:");
                form.add(startDate, 0, 4);

                DatePicker startDatePick = new DatePicker();
                form.add(startDatePick, 1, 4);
                startDatePick.setDayCellFactory(d ->
                        new DateCell() {
                            @Override
                            public void updateItem(LocalDate item, boolean empty) {
                                super.updateItem(item, empty);
                                setDisable(item.isBefore(LocalDate.now()));
                            }
                        });

                Text endDate = new Text("end date:");
                form.add(endDate, 0, 5);

                DatePicker endDatePick = new DatePicker();
                form.add(endDatePick, 1, 5);
                endDatePick.setDayCellFactory(d ->
                        new DateCell() {
                            @Override
                            public void updateItem(LocalDate item, boolean empty) {
                                super.updateItem(item, empty);
                                if (startDatePick.getValue() != null)
                                    setDisable(item.isBefore(startDatePick.getValue()) || item.isEqual(startDatePick.getValue()));
                            }
                        });

                Text includingUsers = new Text("choose including buyers:");
                form.add(includingUsers, 0, 6);

                ObservableList<Buyer> observableList = FXCollections.observableArrayList();
                observableList.addAll(Buyer.getAllBuyers());

                VBox userChoices = new VBox();

                ChoiceBox<Buyer> userList = new ChoiceBox();
                userList.setItems(observableList);
                form.add(userChoices, 1, 6);
                userChoices.getChildren().add(userList);

                Button more = new Button("+");
                more.setOnAction(event -> {
                    ChoiceBox<Buyer> newChoice = new ChoiceBox<>();
                    newChoice.setItems(observableList);
                    userChoices.getChildren().add(newChoice);
                });
                form.add(more, 1, 7);


                Button cancel = new Button("cancel");
                form.add(cancel, 1, 10);
                cancel.setOnAction(event -> App.getMainStage().setScene(new ManagerPanel(new ScrollPane())));

                Label alert = new Label("*please make sure all fields are filled.");
                alert.setVisible(false);
                form.add(alert, 1, 9);

                Button ok = new Button("submit");
                String finalDiscountCode = discountCode;
                ok.setOnAction(event -> {
                    alert.setVisible(false);
                    if (percentField.getText().isEmpty()) {
                        alert.setVisible(true);
                    } else if (maxField.getText().isEmpty()) {
                        alert.setVisible(true);
                    } else if (usageField.getText().isEmpty()) {
                        alert.setVisible(true);
                    } else if (startDatePick.getValue() == null) {
                        alert.setVisible(true);
                    } else if (endDatePick.getValue() == null) {
                        alert.setVisible(true);
                    } else if (((ChoiceBox<Buyer>) userChoices.getChildren().get(0)).getSelectionModel() == null) {
                        alert.setVisible(true);
                    } else {
                        ArrayList<Buyer> buyers = new ArrayList<>();
                        for (int i = 0; i < userChoices.getChildren().size(); i++) {
                            buyers.add(((ChoiceBox<Buyer>) userChoices.getChildren().get(i)).getSelectionModel().getSelectedItem());
                        }
                        new OffWithCode(codeValue.getText(), startDatePick.getValue().atStartOfDay(), endDatePick.getValue().atStartOfDay(),
                                Integer.parseInt(percentField.getText()), Float.parseFloat(maxField.getText()), Integer.parseInt(usageField.getText()), buyers);

                        new Alert(Alert.AlertType.INFORMATION, "discount successfully added");
                        App.getMainStage().setScene(new ManagerPanel(new ScrollPane()));
                    }
                });
                form.add(ok, 1, 8);


                pane.getChildren().add(form);
                stagePane.setContent(pane);
            }
        };
    }

    public Menu manageDiscounts() {
        return new Menu(new ScrollPane()) {
            @Override
            public void init() {
                TableView<OffWithCode> discounts = new TableView<>();

                ObservableList<OffWithCode> codes = FXCollections.observableArrayList();
                codes.addAll(OffWithCode.getAllDiscounts());

                discounts.setItems(codes);

                TableColumn<OffWithCode, String> code = new TableColumn<>("code");
                code.setCellValueFactory(new PropertyValueFactory<>("offCode"));

                TableColumn<OffWithCode, String> startTime = new TableColumn<>("start date");
                startTime.setCellValueFactory(new PropertyValueFactory<>("formattedStartDate"));

                TableColumn<OffWithCode, String> stopTime = new TableColumn<>("stop date");
                stopTime.setCellValueFactory(new PropertyValueFactory<>("formattedStopDate"));

                AnchorPane.setLeftAnchor(discounts, 300.0);

                discounts.getColumns().addAll(code, startTime, stopTime);

                HBox buttons = new HBox();
                buttons.setSpacing(10);

                Button back = new Button("return");
                back.setOnAction(event -> App.getMainStage().setScene(new ManagerPanel(new ScrollPane())));

                Button delete = new Button("delete");
                delete.setOnAction(event -> deleteDiscount(discounts.getSelectionModel().getSelectedItem()));

                Button edit = new Button("edit");
                edit.setOnAction(event -> App.getMainStage().setScene(editDiscount(discounts.getSelectionModel().getSelectedItem())));

                buttons.getChildren().addAll(back, delete, edit);

                pane.getChildren().addAll(buttons, discounts);
            }
        };
    }

    public Menu editDiscount(OffWithCode discount) {
        return new Menu(new ScrollPane()) {
            @Override
            public void init() {
                GridPane form = new GridPane();
                form.setHgap(200);
                form.setVgap(20);
                form.setPrefWidth(900);
                AnchorPane.setRightAnchor(form, 100.0);
                AnchorPane.setLeftAnchor(form, 100.0);
                AnchorPane.setTopAnchor(form, 100.0);

                String discountCode;
                while (OffWithCode.getOffByCode(discountCode = Integer.toString(new Random().nextInt(999999) + 1)) != null)
                    ;

                Text code = new Text("generated code:");
                form.add(code, 0, 0);

                Text codeValue = new Text();
                codeValue.setText(discountCode);
                form.add(codeValue, 1, 0);

                Text percent = new Text("discount percentage:");
                form.add(percent, 0, 1);

                TextField percentField = new TextField();
                percentField.setText(Integer.toString(discount.getOffAmount()));
                form.add(percentField, 1, 1);

                percentField.textProperty().addListener(new ChangeListener<String>() {
                    @Override
                    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                        if (!newValue.matches("\\d{0,2}")) {
                            percentField.setText(oldValue);
                        }
                    }
                });

                Text max = new Text("maximum amount:");
                form.add(max, 0, 2);

                TextField maxField = new TextField();
                maxField.setText(Float.toString(discount.getMaxAmount()));
                form.add(maxField, 1, 2);

                maxField.textProperty().addListener(new ChangeListener<String>() {
                    @Override
                    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                        if (!newValue.matches("\\d*"))
                            maxField.setText(oldValue);
                    }
                });

                Text usageLimit = new Text("usage limit:");
                form.add(usageLimit, 0, 3);

                TextField usageField = new TextField();
                usageField.setText(Integer.toString(discount.getNumberOfUsesOfCode()));
                form.add(usageField, 1, 3);

                usageField.textProperty().addListener(new ChangeListener<String>() {
                    @Override
                    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                        if (!newValue.matches("\\d*"))
                            usageField.setText(oldValue);
                    }
                });

                Text startDate = new Text("start date:");
                form.add(startDate, 0, 4);

                DatePicker startDatePick = new DatePicker();
                startDatePick.setValue(discount.getStartDate().toLocalDate());
                form.add(startDatePick, 1, 4);
                startDatePick.setDayCellFactory(d ->
                        new DateCell() {
                            @Override
                            public void updateItem(LocalDate item, boolean empty) {
                                super.updateItem(item, empty);
                                setDisable(item.isBefore(LocalDate.now()));
                            }
                        });

                Text endDate = new Text("end date:");
                form.add(endDate, 0, 5);

                DatePicker endDatePick = new DatePicker();
                endDatePick.setValue(discount.getStopDate().toLocalDate());
                form.add(endDatePick, 1, 5);
                endDatePick.setDayCellFactory(d ->
                        new DateCell() {
                            @Override
                            public void updateItem(LocalDate item, boolean empty) {
                                super.updateItem(item, empty);
                                if (startDatePick.getValue() != null)
                                    setDisable(item.isBefore(startDatePick.getValue()) || item.isEqual(startDatePick.getValue()));
                            }
                        });

                Text includingUsers = new Text("choose including buyers:");
                form.add(includingUsers, 0, 6);

                ObservableList<Buyer> observableList = FXCollections.observableArrayList();
                observableList.addAll(Buyer.getAllBuyers());

                VBox userChoices = new VBox();

                for (Buyer buyer : discount.getApplyingAccounts().keySet()) {
                    ChoiceBox<Buyer> userList = new ChoiceBox();
                    userList.setItems(observableList);
                    userList.setValue(buyer);
                    userChoices.getChildren().add(userList);
                }

                form.add(userChoices, 1, 6);


                Button more = new Button("+");
                more.setOnAction(event -> {
                    ChoiceBox<Buyer> newChoice = new ChoiceBox<>();
                    newChoice.setItems(observableList);
                    userChoices.getChildren().add(newChoice);
                });
                form.add(more, 1, 7);


                Button cancel = new Button("cancel");
                form.add(cancel, 1, 10);
                cancel.setOnAction(event -> App.getMainStage().setScene(new ManagerPanel(new ScrollPane())));

                Label alert = new Label("*please make sure all fields are filled.");
                alert.setVisible(false);
                form.add(alert, 1, 9);

                Button ok = new Button("submit");
                String finalDiscountCode = discountCode;
                ok.setOnAction(event -> {
                    alert.setVisible(false);
                    if (percentField.getText().isEmpty()) {
                        alert.setVisible(true);
                    } else if (maxField.getText().isEmpty()) {
                        alert.setVisible(true);
                    } else if (usageField.getText().isEmpty()) {
                        alert.setVisible(true);
                    } else if (startDatePick.getValue() == null) {
                        alert.setVisible(true);
                    } else if (endDatePick.getValue() == null) {
                        alert.setVisible(true);
                    } else if (((ChoiceBox<Buyer>) userChoices.getChildren().get(0)).getSelectionModel() == null) {
                        alert.setVisible(true);
                    } else {
                        ArrayList<Buyer> buyers = new ArrayList<>();
                        for (int i = 0; i < userChoices.getChildren().size(); i++) {
                            buyers.add(((ChoiceBox<Buyer>) userChoices.getChildren().get(i)).getSelectionModel().getSelectedItem());
                        }
                        discount.setOffAmount(Integer.parseInt(percentField.getText()));
                        discount.setMaxAmount(Float.parseFloat(maxField.getText()));
                        discount.setNumberOfUsesOfCode(Integer.parseInt(usageField.getText()));
                        discount.setStartDate(startDatePick.getValue().atStartOfDay());
                        discount.setStopDate(endDatePick.getValue().atStartOfDay());

                        ArrayList<Buyer> chosenBuyers = new ArrayList<>();

                        for (Node choice : userChoices.getChildren()) {
                            chosenBuyers.add(((ChoiceBox<Buyer>) choice).getSelectionModel().getSelectedItem());
                        }
                        discount.setApplyingAccounts(chosenBuyers);

                        new Alert(Alert.AlertType.INFORMATION, "discount successfully edited");
                        App.getMainStage().setScene(manageDiscounts());
                    }
                });
                form.add(ok, 1, 8);


                pane.getChildren().add(form);
                stagePane.setContent(pane);
            }
        };
    }

    public void deleteDiscount(OffWithCode discount) {
        OffWithCode.deleteDiscount(discount);
        App.getMainStage().setScene(manageDiscounts());
    }

    public Menu manageRequests() {
        return new Menu(new ScrollPane()) {
            @Override
            public void init() {
                TableView<Request> requestTable = new TableView<>();
                requestTable.setItems(FXCollections.observableArrayList(Request.getAllRequests()));
                pane.getChildren().add(requestTable);

                AnchorPane.setTopAnchor(requestTable, 100.0);
                AnchorPane.setLeftAnchor(requestTable, 300.0);

                TableColumn<Request, String> typeColumn = new TableColumn<>("type");
                typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));

                TableColumn<Request, String> dateColumn = new TableColumn<>("send time");
                dateColumn.setCellValueFactory(new PropertyValueFactory<>("dateTime"));

                requestTable.getColumns().addAll(typeColumn, dateColumn);

                Button back = new Button("return");
                back.setOnAction(event -> App.getMainStage().setScene(new ManagerPanel(new ScrollPane())));

                Button view=new Button("view");
                view.setOnAction(event -> {
                    if(requestTable.getSelectionModel().getSelectedItem()!=null){
                        App.getMainStage().setScene(new RequestPanel(new ScrollPane(),requestTable.getSelectionModel().getSelectedItem()).productAddition());
                    }else {
                        new Alert(Alert.AlertType.ERROR,"no request chosen").show();
                    }
                });

                pane.getChildren().add(new VBox(back,view));

            }
        };
    }

    public void setFile(File file) {
        this.file = file;
    }

}
