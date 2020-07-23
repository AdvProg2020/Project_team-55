package org.menu;

import Model.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
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
import javafx.util.Callback;
import org.example.App;

import java.io.File;
import java.io.IOException;
import java.io.PipedReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class SellerPanel extends Menu {
    User user;
    private File file;
    private File entryFile;

    public SellerPanel(ScrollPane root){
        super(root);
    }

    @Override
    public void init()  {

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

        Button editProfile = new Button("edit profile");
        editProfile.getStyleClass().add("command");
        editProfile.setOnAction(event -> {

                App.getMainStage().setScene(editProfile());

        });

        Button company = new Button("view company information");
        company.getStyleClass().add("command");
        company.setOnAction(event -> {
            new Alert(Alert.AlertType.INFORMATION, "you work for " + ((Seller) user).getFactory()).show();
        });

        Button balance = new Button("show balance");
        balance.getStyleClass().add("command");
        balance.setOnAction(event -> {

                balance();

        });

        Button sale = new Button("view sell history");
        sale.getStyleClass().add("command");
        sale.setOnAction(event -> {

                App.getMainStage().setScene(viewSells());

        });

        Button manageProducts = new Button("manage products");
        manageProducts.getStyleClass().add("command");
        manageProducts.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                    App.getMainStage().setScene(manageProducts());

            }
        });

        Button categories = new Button("show categories");
        categories.getStyleClass().add("command");
        categories.setOnAction(event -> {

                App.getMainStage().setScene(viewCategories());

        });

        Button offs = new Button("view offs");
        offs.getStyleClass().add("command");
        offs.setOnAction(event -> {

                App.getMainStage().setScene(manageOffs());

        });

        Button auctions=new Button("manage auctions");
        auctions.getStyleClass().add("command");
        auctions.setOnAction(actionEvent -> {

                App.getMainStage().setScene(manageAuctions());

        });

        commands.getChildren().addAll(profile, editProfile, company, balance, sale, manageProducts, categories, offs,auctions);
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

        Text firstNameValue = new Text(((Seller) user).getFirstName());
        firstNameValue.getStyleClass().add("info-grid");
        info.add(firstNameValue, 1, 1);

        Text lastName = new Text("last name:");
        lastName.getStyleClass().add("info-grid");
        info.add(lastName, 0, 2);

        Text lastNameValue = new Text(((Seller) user).getLastName());
        lastNameValue.getStyleClass().add("info-grid");
        info.add(lastNameValue, 1, 2);

        Text phone = new Text("phone number:");
        phone.getStyleClass().add("info-grid");
        info.add(phone, 0, 3);

        Text phoneValue = new Text(((Seller) user).getPhoneNumber());
        phoneValue.getStyleClass().add("info-grid");
        info.add(phoneValue, 1, 3);

        Text email = new Text("Email:");
        email.getStyleClass().add("info-grid");
        info.add(email, 0, 4);

        Text emailValue = new Text(((Seller) user).getEmail());
        emailValue.getStyleClass().add("info-grid");
        info.add(emailValue, 1, 4);

        info.add(new Text("credit:"), 0, 5);
        info.add(new Text("" + ((Seller) user).getCredit()), 1, 5);

        info.setMinSize(200, 200);

        AnchorPane.setLeftAnchor(info, 350.0);
        AnchorPane.setRightAnchor(info, 350.0);


        pane.getChildren().add(info);
    }

    public Menu editProfile()  {
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

                file = null;

                FileChooser imageChooser = new FileChooser();

                getImage.setOnAction(event -> setFile(imageChooser.showOpenDialog(App.getMainStage())));

                Text corporate = new Text("corporate : ");
                form.add(corporate, 0, 19);

                TextField corporateField = new TextField();
                corporateField.setPromptText("corporate...");
                corporateField.setText(((Seller) user).getFactory());
                form.add(corporateField, 1, 19);


                alerts.forEach((alert) -> alert.setVisible(false));

                Label emptyField = new Label("*please make sure no field is left empty");
                emptyField.setVisible(false);
                form.add(emptyField, 1, 21, 2, 1);

                Button submit = new Button("submit");
                form.add(submit, 1, 20);
                submit.setOnAction(event -> {
                    emptyField.setVisible(false);
                    if ((((passwordField.getText().isEmpty()) || passConfirm.getText().isEmpty()) || fNameField.getText().isEmpty())
                            || lNameField.getText().isEmpty() || emailField.getText().isEmpty() || phoneField.getText().isEmpty() || corporateField.getText().isEmpty()) {
                        emptyField.setVisible(true);
                        return;
                    } else {
                        for (Label alert : alerts) {
                            if (alert.isVisible())
                                return;
                        }
                    }

                    //edit request
                    user.setPassword(passwordField.getText());
                    user.setFirstName(fNameField.getText());
                    user.setLastName(lNameField.getText());
                    user.setEmail(emailField.getText());
                    user.setPhoneNumber(phoneField.getText());
                    ((Seller) user).setFactory(corporateField.getText());
                    if (file != null) {
                        user.setProfile(file.toURI().toString());
                    }
                });

                Button back = new Button("return");
                form.add(back, 1, 22);
                back.setOnAction(event -> {

                        App.getMainStage().setScene(new SellerPanel(new ScrollPane()));

                });

                App.getMainStage().getScene().getStylesheets().add("userpanel.css");
                pane.getStyleClass().add("pane");
            }
        };
    }

    public Menu manageProducts(){
        return new Menu(new ScrollPane()) {
            @Override
            public void init() {
                TableView<Product> productTableView = new TableView<>();
                pane.getChildren().add(productTableView);

                AnchorPane.setLeftAnchor(productTableView, 300.0);


                ObservableList<Product> products = FXCollections.observableArrayList();
                products.addAll(((Seller)user).getArrayProduct());
                productTableView.setItems(products);

                TableColumn<Product, ImageView> imageColumn = new TableColumn<>("picture");
                imageColumn.setCellValueFactory(new PropertyValueFactory<>("picture"));

                TableColumn<Product, String> nameColumn = new TableColumn<>("name");
                nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

                TableColumn<Product, String> brandColumn = new TableColumn<>("brand");
                brandColumn.setCellValueFactory(new PropertyValueFactory<>("brand"));


                TableColumn<Product, String> categoryColumn = new TableColumn<>("category");
                categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));

                TableColumn<Product, Float> priceColumn = new TableColumn<>("price");
                priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

                TableColumn<Product, ArrayList<Buyer>> buyerColumn = new TableColumn<>("buyers");
                buyerColumn.setCellValueFactory(new PropertyValueFactory<>("listOfBuyers"));


                productTableView.getColumns().addAll(imageColumn, nameColumn, brandColumn, categoryColumn, priceColumn, buyerColumn);

                HBox buttons = new HBox();

                Button add = new Button("add");
                add.setOnAction(event -> {

                        App.getMainStage().setScene(addProduct());

                });

                Button addFile=new Button("add file");
                addFile.setOnAction(actionEvent -> {

                        App.getMainStage().setScene(addFileProduct());

                });

                Button delete = new Button("delete");
                delete.setOnAction(event -> {
                    if (productTableView.getSelectionModel().getSelectedItem() != null) {
                        Product.removeProduct(productTableView.getSelectionModel().getSelectedItem());
                        products.clear();
                        products.addAll(Product.getAllProducts());
                        productTableView.setItems(products);
                    } else {
                        new Alert(Alert.AlertType.ERROR, "no product is chosen").show();
                    }
                });

                Button edit = new Button("edit");
                edit.setOnAction(event -> {
                    if (productTableView.getSelectionModel().getSelectedItem() != null) {

                            App.getMainStage().setScene(editProduct(productTableView.getSelectionModel().getSelectedItem()));

                    } else {
                        new Alert(Alert.AlertType.ERROR, "no product is chosen").show();
                    }
                });

                Button back = new Button("return");
                back.setOnAction(event -> {

                        App.getMainStage().setScene(new SellerPanel(new ScrollPane()));

                });

                buttons.getChildren().addAll(add,addFile, delete, edit, back);

                pane.getChildren().add(buttons);
            }
        };
    }

    public Menu addProduct()  {
        return new Menu(new ScrollPane()) {
            @Override
            public void init() {
                GridPane form = new GridPane();
                AnchorPane.setLeftAnchor(form, 300.0);
                AnchorPane.setTopAnchor(form, 200.0);
                AnchorPane.setBottomAnchor(form, 200.0);

                form.setHgap(20);
                form.setVgap(60);

                Text id = new Text("product id : ");
                form.add(id, 0, 0);

                Random random = new Random();

                Text idValue = new Text();

                idValue.setText(Integer.toString(random.nextInt(899999) + 100000));
                while (Product.getProductById(idValue.getText()) != null) {
                    idValue.setText(Integer.toString(random.nextInt(899999) + 100000));
                }

                form.add(idValue, 1, 0);

                Text price = new Text("price : ");
                form.add(price, 0, 1);

                TextField priceField = new TextField();
                priceField.setPromptText("price...");
                priceField.textProperty().addListener(new ChangeListener<String>() {
                    @Override
                    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                        if (!newValue.matches("\\d*"))
                            priceField.setText(oldValue);
                    }
                });
                form.add(priceField, 1, 1);

                Text category = new Text("category : ");
                form.add(category, 0, 2);

                ChoiceBox<Category> categoryChoiceBox = new ChoiceBox<>();

                //HashMap<Text,TextField> attributes;

                ObservableList<Category> categoryObservableList = FXCollections.observableArrayList();

                categoryObservableList.addAll(Category.getAllCategories());

                categoryChoiceBox.setItems(categoryObservableList);
                form.add(categoryChoiceBox, 1, 2);

                Button choose=new Button("choose category");
                form.add(choose,2,2);

                VBox attributeTexts = new VBox();
                VBox attributeTextFields = new VBox();

                attributeTexts.setSpacing(10);
                attributeTextFields.setSpacing(10);

                form.add(attributeTexts, 0, 3);
                form.add(attributeTextFields, 1, 3);

                choose.setOnAction(event -> {
                    if (categoryChoiceBox.getSelectionModel().getSelectedItem()!=null){
                    ArrayList<String> attributes = categoryChoiceBox.getSelectionModel().getSelectedItem().getSpecialAttributes();

                    attributeTexts.getChildren().clear();
                    attributeTextFields.getChildren().clear();

                    for (String text : attributes) {
                        attributeTexts.getChildren().add(new Text(text + " : "));

                        TextField textField = new TextField();
                        textField.setPromptText(text + "...");
                        attributeTextFields.getChildren().add(textField);
                    }
                }else {
                        new Alert(Alert.AlertType.ERROR,"no category choosen").show();
                    }
                });


                Text explanation = new Text("explanations : ");
                form.add(explanation, 0, 4);

                TextArea explanationArea = new TextArea();
                explanationArea.setPromptText("explanation...");
                form.add(explanationArea, 1, 4);

                Button getImage = new Button("upload image");
                form.add(getImage, 0, 5);
                file = null;

                FileChooser imageChooser = new FileChooser();

                getImage.setOnAction(event -> {
                    setFile(imageChooser.showOpenDialog(App.getMainStage()));
                });

                pane.getChildren().add(form);


                HBox buttons = new HBox();
                pane.getChildren().add(buttons);

                Button back = new Button("return");
                back.setOnAction(event -> {

                        App.getMainStage().setScene(manageProducts());

                });


                Button submit = new Button("send request");
                submit.setOnAction(event -> {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "please make sure all fields are filled");

                    if (priceField.getText().isEmpty()) {
                        alert.show();
                        return;
                    }
                    if (categoryChoiceBox.getSelectionModel().getSelectedItem() == null) {
                        alert.show();
                        return;
                    }
                    if (explanationArea.getText().isEmpty()) {
                        alert.show();
                        return;
                    }
                    HashMap<String, String> attributesHashMap = new HashMap<>();
                    ArrayList<String> categoryAttributes = categoryChoiceBox.getSelectionModel().getSelectedItem().getSpecialAttributes();

                    for (int i = 0; i < categoryAttributes.size(); i++) {
                        if (((TextField) attributeTextFields.getChildren().get(i)).getText().isEmpty()) {
                            alert.show();
                            return;
                        }
                        attributesHashMap.put(categoryAttributes.get(i), ((TextField) attributeTextFields.getChildren().get(i)).getText());
                    }

                    if (file == null) {
                        alert.show();
                        return;
                    }

                    //request send

                    new ProductAdditionRequest((Seller)user, idValue.getText(), Float.parseFloat(priceField.getText()),
                            categoryChoiceBox.getSelectionModel().getSelectedItem(), attributesHashMap, explanationArea.getText(), file.toURI().toString(),false);
                    new Alert(Alert.AlertType.INFORMATION,"request successfully sent").show();

                        App.getMainStage().setScene(manageProducts());


                });

                buttons.getChildren().addAll(back, submit);
                buttons.setSpacing(10);

            }
        };
    }

    public HashMap<Text, TextField> createSpecialAttributesFields(Category category) {
        HashMap<Text, TextField> attributes = new HashMap<>();

        Text attribute;
        TextField attributeField;

        for (String categoryAttribute : category.getSpecialAttributes()) {
            attribute = new Text(categoryAttribute);
            attributeField = new TextField();
            attributeField.setPromptText(categoryAttribute + "...");
            attributes.put(attribute, attributeField);
        }
        return attributes;
    }

    public Menu addFileProduct()  {


        return new Menu(new ScrollPane()) {

            @Override
            public void init() {
                GridPane form = new GridPane();
                AnchorPane.setLeftAnchor(form, 300.0);
                AnchorPane.setTopAnchor(form, 200.0);
                AnchorPane.setBottomAnchor(form, 200.0);

                form.setHgap(20);
                form.setVgap(60);

                Text id = new Text("product id : ");
                form.add(id, 0, 0);

                Random random = new Random();

                Text idValue = new Text();

                idValue.setText(Integer.toString(random.nextInt(899999) + 100000));
                while (Product.getProductById(idValue.getText()) != null) {
                    idValue.setText(Integer.toString(random.nextInt(899999) + 100000));
                }

                form.add(idValue, 1, 0);

                Text price = new Text("price : ");
                form.add(price, 0, 1);

                TextField priceField = new TextField();
                priceField.setPromptText("price...");
                priceField.textProperty().addListener(new ChangeListener<String>() {
                    @Override
                    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                        if (!newValue.matches("\\d*"))
                            priceField.setText(oldValue);
                    }
                });
                form.add(priceField, 1, 1);

                VBox content=new VBox();
                Button chooseFile=new Button("choose file");
                FileChooser fileChooser=new FileChooser();
                chooseFile.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        entryFile = fileChooser.showOpenDialog(App.getMainStage());
                    }
                });



                Text category = new Text("category : ");
                form.add(category, 0, 2);

                ChoiceBox<Category> categoryChoiceBox = new ChoiceBox<>();

                //HashMap<Text,TextField> attributes;

                ObservableList<Category> categoryObservableList = FXCollections.observableArrayList();

                categoryObservableList.addAll(Category.getAllCategories());

                categoryChoiceBox.setItems(categoryObservableList);
                form.add(categoryChoiceBox, 1, 2);

                Button choose=new Button("choose category");
                form.add(choose,2,2);

                VBox attributeTexts = new VBox();
                VBox attributeTextFields = new VBox();

                attributeTexts.setSpacing(10);
                attributeTextFields.setSpacing(10);

                form.add(attributeTexts, 0, 3);
                form.add(attributeTextFields, 1, 3);

                choose.setOnAction(event -> {
                    if (categoryChoiceBox.getSelectionModel().getSelectedItem()!=null){
                        ArrayList<String> attributes = categoryChoiceBox.getSelectionModel().getSelectedItem().getSpecialAttributes();

                        attributeTexts.getChildren().clear();
                        attributeTextFields.getChildren().clear();

                        for (String text : attributes) {
                            attributeTexts.getChildren().add(new Text(text + " : "));

                            TextField textField = new TextField();
                            textField.setPromptText(text + "...");
                            attributeTextFields.getChildren().add(textField);
                        }
                    }else {
                        new Alert(Alert.AlertType.ERROR,"no category choosen").show();
                    }
                });


                Text explanation = new Text("explanations : ");
                form.add(explanation, 0, 4);

                TextArea explanationArea = new TextArea();
                explanationArea.setPromptText("explanation...");
                form.add(explanationArea, 1, 4);

                Button getImage = new Button("upload image");
                form.add(getImage, 0, 5);
                file = null;

                FileChooser imageChooser = new FileChooser();

                getImage.setOnAction(event -> {
                    setFile(imageChooser.showOpenDialog(App.getMainStage()));
                });

                pane.getChildren().add(form);


                HBox buttons = new HBox();
                pane.getChildren().add(buttons);

                Button back = new Button("return");
                back.setOnAction(event -> {

                        App.getMainStage().setScene(manageProducts());

                });


                Button submit = new Button("send request");
                submit.setOnAction(event -> {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "please make sure all fields are filled");

                    if (priceField.getText().isEmpty()) {
                        alert.show();
                        return;
                    }
                    if (categoryChoiceBox.getSelectionModel().getSelectedItem() == null) {
                        alert.show();
                        return;
                    }
                    if (explanationArea.getText().isEmpty()) {
                        alert.show();
                        return;
                    }
                    HashMap<String, String> attributesHashMap = new HashMap<>();
                    ArrayList<String> categoryAttributes = categoryChoiceBox.getSelectionModel().getSelectedItem().getSpecialAttributes();

                    for (int i = 0; i < categoryAttributes.size(); i++) {
                        if (((TextField) attributeTextFields.getChildren().get(i)).getText().isEmpty()) {
                            alert.show();
                            return;
                        }
                        attributesHashMap.put(categoryAttributes.get(i), ((TextField) attributeTextFields.getChildren().get(i)).getText());
                    }

                    if (file == null || entryFile==null) {
                        alert.show();
                        return;
                    }

                    //request send

                    new ProductAdditionRequest((Seller)user, idValue.getText(), Float.parseFloat(priceField.getText()),
                            categoryChoiceBox.getSelectionModel().getSelectedItem(), attributesHashMap, explanationArea.getText(), file.toURI().toString(),entryFile.toURI().toString(),true);
                    new Alert(Alert.AlertType.INFORMATION,"request successfully sent").show();

                        App.getMainStage().setScene(manageProducts());


                });

                buttons.getChildren().addAll(back, submit);
                buttons.setSpacing(10);
            }
        };
    }


    public Menu editProduct(Product selected) {
        return new Menu(new ScrollPane()) {
            @Override
            public void init() {
                Product product = selected;

                GridPane form = new GridPane();
                AnchorPane.setLeftAnchor(form, 300.0);
                AnchorPane.setTopAnchor(form, 200.0);
                AnchorPane.setBottomAnchor(form, 200.0);

                form.setHgap(20);
                form.setVgap(60);

                Text id = new Text("product id : ");
                form.add(id, 0, 0);

                Random random = new Random();

                Text idValue = new Text(selected.getProductId());

                form.add(idValue, 1, 0);

                Text price = new Text("price : ");
                form.add(price, 0, 1);

                TextField priceField = new TextField();
                priceField.setText(Float.toString(product.getPrice()));
                priceField.setPromptText("price...");
                priceField.textProperty().addListener(new ChangeListener<String>() {
                    @Override
                    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                        if (!newValue.matches("\\d*"))
                            priceField.setText(oldValue);
                    }
                });
                form.add(priceField, 1, 1);

                Text category = new Text("category : ");
                form.add(category, 0, 2);

                ChoiceBox<Category> categoryChoiceBox = new ChoiceBox<>();
                categoryChoiceBox.getSelectionModel().select(product.getCategory());


                ObservableList<Category> categoryObservableList = FXCollections.observableArrayList();

                categoryObservableList.addAll(Category.getAllCategories());

                categoryChoiceBox.setItems(categoryObservableList);
                form.add(categoryChoiceBox, 1, 2);

                VBox attributeTexts = new VBox();
                VBox attributeTextFields = new VBox();

                attributeTexts.setSpacing(10);
                attributeTextFields.setSpacing(10);

                for (String text : product.getCategory().getSpecialAttributes()) {
                    attributeTexts.getChildren().add(new Text(text + " : "));

                    TextField textField = new TextField();
                    textField.setPromptText(text + "...");
                    textField.setText(product.getSpecialAttributes().get(text));
                    attributeTextFields.getChildren().add(textField);
                }

                form.add(attributeTexts, 0, 3);
                form.add(attributeTextFields, 1, 3);

                categoryChoiceBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                        ArrayList<String> attributes = categoryChoiceBox.getSelectionModel().getSelectedItem().getSpecialAttributes();

                        attributeTexts.getChildren().clear();
                        attributeTextFields.getChildren().clear();

                        for (String text : attributes) {
                            attributeTexts.getChildren().add(new Text(text + " : "));

                            TextField textField = new TextField();
                            textField.setPromptText(text + "...");
                            attributeTextFields.getChildren().add(textField);
                        }

                    }
                });

                Text explanation = new Text("explanations : ");
                form.add(explanation, 0, 4);

                TextArea explanationArea = new TextArea();
                explanationArea.setPromptText("explanation...");
                explanationArea.setText(product.getExplanation());
                form.add(explanationArea, 1, 4);

                Button getImage = new Button("upload picture");
                form.add(getImage, 0, 5);

                file=null;

                FileChooser imageChooser = new FileChooser();

                getImage.setOnAction(event -> setFile(imageChooser.showOpenDialog(App.getMainStage())));

                pane.getChildren().add(form);


                HBox buttons = new HBox();
                pane.getChildren().add(buttons);

                Button back = new Button("return");
                back.setOnAction(event -> {

                        App.getMainStage().setScene(manageProducts());

                });


                Button submit = new Button("send request");
                submit.setOnAction(event -> {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "please make sure all fields are filled");

                    if (priceField.getText().isEmpty()) {
                        alert.show();
                        return;
                    }
                    if (categoryChoiceBox.getSelectionModel().getSelectedItem() == null) {
                        alert.show();
                        return;
                    }
                    if (explanationArea.getText().isEmpty()) {
                        alert.show();
                        return;
                    }
                    HashMap<String, String> attributesHashMap = new HashMap<>();
                    ArrayList<String> categoryAttributes = categoryChoiceBox.getSelectionModel().getSelectedItem().getSpecialAttributes();

                    for (int i = 0; i < categoryAttributes.size(); i++) {
                        if (((TextField) attributeTextFields.getChildren().get(i)).getText().isEmpty()) {
                            alert.show();
                            return;
                        }
                        attributesHashMap.put(categoryAttributes.get(i), ((TextField) attributeTextFields.getChildren().get(i)).getText());
                    }


                    new ProductEditRequest((Seller) user, product, Float.parseFloat(priceField.getText()),
                            categoryChoiceBox.getSelectionModel().getSelectedItem(), attributesHashMap, explanationArea.getText(),
                            (file == null) ? product.getPicturePath() : file.toURI().toString());

                });

                buttons.getChildren().addAll(back, submit);
                buttons.setSpacing(10);
            }
        };
    }

    public Menu manageOffs() {
        return new Menu(new ScrollPane()) {
            @Override
            public void init() {
                TableView<Off> offTable = new TableView<>();
                AnchorPane.setLeftAnchor(offTable, 300.0);
                AnchorPane.setTopAnchor(offTable, 100.0);

                ObservableList<Off> offObservableList = FXCollections.observableArrayList();
                offObservableList.addAll(((Seller) user).getSellerOffs());
                offTable.setItems(offObservableList);

                TableColumn<Off, String> idColumn = new TableColumn<>("id");
                idColumn.setCellValueFactory(new PropertyValueFactory<>("offID"));

                TableColumn<Off, Integer> percentColumn = new TableColumn<>("off percent");
                percentColumn.setCellValueFactory(new PropertyValueFactory<>("offAmount"));

                TableColumn<Off, String> startDateColumn = new TableColumn<>("start date");
                startDateColumn.setCellValueFactory(new PropertyValueFactory<>("formattedStartDate"));

                TableColumn<Off, String> stopDateColumn = new TableColumn<>("stop date");
                stopDateColumn.setCellValueFactory(new PropertyValueFactory<>("formattedStopDate"));

                offTable.getColumns().addAll(idColumn, percentColumn, startDateColumn, stopDateColumn);

                pane.getChildren().add(offTable);

                HBox buttons = new HBox();

                Button back = new Button("return");
                back.setOnAction(event -> {

                        App.getMainStage().setScene(new SellerPanel(new ScrollPane()));

                });

                Button add = new Button("add");
                add.setOnAction(event -> {

                        App.getMainStage().setScene(addOff());

                });

                Button edit = new Button("edit");
                edit.setOnAction(event -> {

                        App.getMainStage().setScene(editOff(offTable.getSelectionModel().getSelectedItem()));

                });

                buttons.getChildren().addAll(back, add, edit);
                pane.getChildren().add(buttons);
            }
        };
    }

    public Menu addOff()  {
        return new Menu(new ScrollPane()) {
            @Override
            public void init() {
                GridPane form = new GridPane();
                AnchorPane.setLeftAnchor(form, 300.0);
                AnchorPane.setTopAnchor(form, 100.0);

                pane.getChildren().add(form);

                Text id = new Text("id : ");
                form.add(id, 0, 0);

                Random random = new Random();
                Text idValue = new Text(Integer.toString(random.nextInt(899999) + 100000));

                while (Off.getOffById(idValue.getText()) != null)
                    idValue.setText(Integer.toString(random.nextInt(899999) + 100000));
                form.add(idValue, 1, 0);

                Text percent = new Text("off percent : ");
                form.add(percent, 0, 1);

                TextField percentField = new TextField();
                percentField.setPromptText("percent...");
                form.add(percentField, 1, 1);
                percentField.textProperty().addListener(new ChangeListener<String>() {
                    @Override
                    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                        if (!newValue.matches("\\d*")) ;
                        percentField.setText(oldValue);
                    }
                });

                Text startDate = new Text("start date : ");
                form.add(startDate, 0, 2);

                DatePicker startDatePicker = new DatePicker();
                form.add(startDatePicker, 1, 2);
                startDatePicker.setDayCellFactory(d ->
                        new DateCell() {
                            @Override
                            public void updateItem(LocalDate item, boolean empty) {
                                super.updateItem(item, empty);
                                setDisable(item.isBefore(LocalDate.now()));
                            }
                        });

                Text stopDate = new Text("stop date : ");
                form.add(stopDate, 0, 3);

                DatePicker stopDatePicker = new DatePicker();
                form.add(stopDatePicker, 1, 3);
                stopDatePicker.setDayCellFactory(d -> new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        setDisable(item.isBefore(startDatePicker.getValue()) || item.isEqual(startDatePicker.getValue()));
                    }
                });

                Text products = new Text("applying products : ");
                form.add(products, 0, 4);

                VBox productChoices = new VBox();
                for (Product product : Product.getAllProducts()) {
                    if (product.getAssignedOff() == null)
                        productChoices.getChildren().add(new CheckBox(product.getProductId()));
                }
                productChoices.setSpacing(5);
                form.add(productChoices, 1, 4);

                Button submit = new Button("send request");
                form.add(submit, 1, 5);

                Label alert = new Label("*please make sure no fields are left empty");
                alert.setVisible(false);
                form.add(alert, 1, 6);
                submit.setOnAction(new EventHandler<ActionEvent>() {
                                       @Override
                                       public void handle(ActionEvent event) {
                                           alert.setVisible(false);
                                           if (percentField.getText().isEmpty())
                                               alert.setVisible(true);
                                           else if (startDatePicker.getValue() == null)
                                               alert.setVisible(true);
                                           else if (stopDatePicker.getValue() == null)
                                               alert.setVisible(true);
                                           else {
                                               ArrayList<Product> selected = new ArrayList<>();
                                               for (Node checkBox : productChoices.getChildren()) {
                                                   if (((CheckBox) checkBox).isSelected()) {
                                                       selected.add(Product.getProductById(((CheckBox) checkBox).getText()));
                                                   }
                                               }
                                               if (selected.isEmpty()) {
                                                   alert.setVisible(true);
                                               } else {
                                                   new OffAdditionRequest(((Seller) user), idValue.getText(), selected,
                                                           startDatePicker.getValue().atStartOfDay(), stopDatePicker.getValue().atStartOfDay(), Integer.parseInt(percentField.getText()));
                                               }
                                           }
                                       }
                                   }
                );

            }
        };
    }

    public Menu editOff(Off off)  {
        return new Menu(new ScrollPane()) {
            @Override
            public void init() {
                GridPane form = new GridPane();
                AnchorPane.setLeftAnchor(form, 300.0);
                AnchorPane.setTopAnchor(form, 100.0);
                pane.getChildren().add(form);

                Text id = new Text("id : ");
                form.add(id, 0, 0);

                Text idValue = new Text(off.getOffId());

                form.add(idValue, 1, 0);

                Text percent = new Text("off percent : ");
                form.add(percent, 0, 1);

                TextField percentField = new TextField();
                percentField.setText("" + off.getOffAmount());
                percentField.setPromptText("percent...");
                form.add(percentField, 1, 1);
                percentField.textProperty().addListener(new ChangeListener<String>() {
                    @Override
                    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                        if (!newValue.matches("\\d{0,2}")) ;
                        percentField.setText(oldValue);
                    }
                });

                Text startDate = new Text("start date : ");
                form.add(startDate, 0, 2);

                DatePicker startDatePicker = new DatePicker();
                startDatePicker.setValue(off.getStartDate().toLocalDate());
                form.add(startDatePicker, 1, 2);
                startDatePicker.setDayCellFactory(d ->
                        new DateCell() {
                            @Override
                            public void updateItem(LocalDate item, boolean empty) {
                                super.updateItem(item, empty);
                                setDisable(item.isBefore(LocalDate.now()));
                            }
                        });

                Text stopDate = new Text("stop date : ");
                form.add(stopDate, 0, 3);

                DatePicker stopDatePicker = new DatePicker();
                stopDatePicker.setValue(off.getStopDate().toLocalDate());
                form.add(stopDatePicker, 1, 3);
                stopDatePicker.setDayCellFactory(d -> new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        setDisable(item.isBefore(startDatePicker.getValue()) || item.isEqual(startDatePicker.getValue()));
                    }
                });

                Text products = new Text("applying products : ");
                form.add(products, 0, 4);

                VBox productChoices = new VBox();
                for (Product product : ((Seller) user).getArrayProduct()) {
                    if (product.getAssignedOff() == null || product.getAssignedOff().equals(off)) {
                        CheckBox checkBox = new CheckBox(product.getProductId());
                        if (off.getProductsArray().contains(product)) {
                            checkBox.setSelected(true);
                        }
                        productChoices.getChildren().add(checkBox);
                    }
                }
                productChoices.setSpacing(5);
                form.add(productChoices, 1, 4);

                Button submit = new Button("send request");
                form.add(submit, 1, 5);

                Label alert = new Label("*please make sure no fields are left empty");
                alert.setVisible(false);
                form.add(alert, 1, 6);
                submit.setOnAction(new EventHandler<ActionEvent>() {
                                       @Override
                                       public void handle(ActionEvent event) {
                                           alert.setVisible(false);
                                           if (percentField.getText().isEmpty())
                                               alert.setVisible(true);
                                           else if (startDatePicker.getValue() == null)
                                               alert.setVisible(true);
                                           else if (stopDatePicker.getValue() == null)
                                               alert.setVisible(true);
                                           else {
                                               ArrayList<Product> selected = new ArrayList<>();
                                               for (Node checkBox : productChoices.getChildren()) {
                                                   if (((CheckBox) checkBox).isSelected()) {
                                                       selected.add(Product.getProductById(((CheckBox) checkBox).getText()));
                                                   }
                                               }
                                               if (selected.isEmpty()) {
                                                   alert.setVisible(true);
                                               } else {
                                                   new OffEditRequest(off, ((Seller) user), selected,
                                                           startDatePicker.getValue().atStartOfDay(), stopDatePicker.getValue().atStartOfDay(), Integer.parseInt(percentField.getText()));
                                               }
                                           }
                                       }
                                   }
                );

            }
        };
    }

    public Menu viewCategories() {
        return new Menu(new ScrollPane()) {
            @Override
            public void init() {
                TreeTableView<Category> categoryTree = new TreeTableView<>();
                Category nullCategory=new Category("name",null,new ArrayList());
                Category.deleteCategory(nullCategory);
                categoryTree.setRoot(new TreeItem<>(nullCategory));
                categoryTree.setShowRoot(false);
                pane.getChildren().add(categoryTree);
                AnchorPane.setLeftAnchor(categoryTree, 300.0);
                AnchorPane.setTopAnchor(categoryTree, 100.0);

                for (Category category : Category.getAllCategories()) {
                    if (category.getParentCategory() == null) {
                        TreeItem<Category> item = new TreeItem(category);
                        categoryTree.getRoot().getChildren().add(item);
                        for (Category sub : category.getSubCategories())
                            item.getChildren().add(new TreeItem<>(sub));
                    }
                }

                TreeTableColumn<Category, String> name = new TreeTableColumn<>("name");
                name.setCellValueFactory(new TreeItemPropertyValueFactory<>("name"));
                categoryTree.getColumns().add(name);

                Button back = new Button("return");
                back.setOnAction(event -> {

                        App.getMainStage().setScene(new SellerPanel(new ScrollPane()));

                });
                pane.getChildren().add(back);

            }
        };
    }

    public Menu viewSells()  {
        return new Menu(new ScrollPane()) {
            @Override
            public void init() {
                TableView<SellLog> sellTable = new TableView<>();
                sellTable.setItems(FXCollections.observableArrayList(((Seller) user).getSellHistory()));

                TableColumn<SellLog, String> idColumn = new TableColumn<>("sell id");
                idColumn.setCellValueFactory(new PropertyValueFactory<>("logId"));

                TableColumn<SellLog, String> dateColumn = new TableColumn<>("date");
                dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));

                TableColumn<SellLog, Product> productColumn = new TableColumn<>("product");
                productColumn.setCellValueFactory(new PropertyValueFactory<>("boughtProduct"));

                TableColumn<SellLog, Integer> numberColumn = new TableColumn<>("quantity");
                numberColumn.setCellValueFactory(new PropertyValueFactory<>("number"));

                TableColumn<SellLog, Float> moneyColumn = new TableColumn<>("price");
                moneyColumn.setCellValueFactory(new PropertyValueFactory<>("purchasedMoney"));

                sellTable.getColumns().addAll(idColumn, dateColumn, productColumn, numberColumn, moneyColumn);
                AnchorPane.setLeftAnchor(sellTable, 300.0);
                AnchorPane.setTopAnchor(sellTable, 100.0);

                sellTable.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {

                            App.getMainStage().setScene(specificSell(sellTable.getSelectionModel().getSelectedItem()));

                    }
                });

                VBox buttons = new VBox();

                Button back = new Button("return");
                back.setOnAction(event -> {

                        App.getMainStage().setScene(new SellerPanel(new ScrollPane()));

                });

                buttons.getChildren().addAll(back);

                pane.getChildren().addAll(sellTable, buttons);
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
                    } catch (IOException  e) {
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
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

                pane.getChildren().add(new VBox(credit,new HBox(chargeField,charge),new HBox(withdrawField,withdraw)));
            }
        };
    }

    public Menu specificSell(SellLog sell)  {
        return new Menu(new ScrollPane()) {
            @Override
            public void init() {
                GridPane form = new GridPane();

                form.add(new Text("id : "), 0, 0);
                form.add(new Text(sell.getLogId()), 1, 0);
                form.add(new Text("date : "), 0, 1);
                form.add(new Text(sell.getDate()), 1, 1);
                form.add(new Text("buyer : "), 0, 2);
                form.add(new Text(sell.getBuyer().getUsername()), 1, 2);
                form.add(new Text("product : "), 0, 3);
                form.add(new Text(sell.getBoughtProduct().getName()), 1, 3);
                form.add(new Text("quantity : "), 0, 4);
                form.add(new Text("" + sell.getNumber()), 1, 4);
                form.add(new Text("purchased price : "), 0, 5);
                form.add(new Text("" + sell.getPurchasedMoney()), 1, 5);
                form.add(new Text("off percent : "), 0, 6);
                form.add(new Text("" + sell.getOffPercent()), 1, 6);

                Button back = new Button("return");
                back.setOnAction(event -> {

                        App.getMainStage().setScene(viewSells());

                });
                form.add(back, 0, 7);

                pane.getChildren().add(form);

            }
        };
    }

    public Menu manageAuctions() {
        return new Menu(new ScrollPane()) {
            @Override
            public void init() {
                HBox contents=new HBox();


                TableView<Auction> auctionTable=new TableView<>();
                auctionTable.setItems(FXCollections.observableArrayList(Auction.getAllAuctions()));

                TableColumn<Auction,String> idColumn=new TableColumn<>("id");
                idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

                TableColumn<Auction,Product> productColumn=new TableColumn<>("product");
                productColumn.setCellValueFactory(new PropertyValueFactory<>("product"));

                TableColumn<Auction,String> startDateColumn=new TableColumn<>("start date");
                startDateColumn.setCellValueFactory(new PropertyValueFactory<>("formattedStartDate"));

                TableColumn<Auction,String> stopDateColumn=new TableColumn<>("stop date");
                stopDateColumn.setCellValueFactory(new PropertyValueFactory<>("formattedStopDate"));

                auctionTable.getColumns().addAll(idColumn,productColumn,startDateColumn,stopDateColumn);

                Button add=new Button("add");
                add.setOnAction(actionEvent -> {

                        App.getMainStage().setScene(addAuction());

                });

                Button delete=new Button("delete");
                delete.setOnAction(actionEvent ->{
                    Auction.getAllAuctions().remove(auctionTable.getSelectionModel().getSelectedItem());
                });

                Button back=new Button("return");
                back.setOnAction(actionEvent -> {

                        App.getMainStage().setScene(new SellerPanel(new ScrollPane()));

                });

                contents.getChildren().addAll(add,delete,auctionTable);
                pane.getChildren().add(contents);
            }
        };
    }

    public Menu addAuction()  {
        //todo: do the rest
        return new Menu(new ScrollPane()) {
            @Override
            public void init() {
                VBox content=new VBox();

                ChoiceBox<Product> productChoiceBox=new ChoiceBox<>();
                productChoiceBox.setItems(FXCollections.observableArrayList(Product.getAllProducts()));

                DatePicker datePicker=new DatePicker();
                datePicker.setDayCellFactory(datePicker1 -> new DateCell(){
                    @Override
                    public void updateItem(LocalDate localDate, boolean b) {
                        super.updateItem(localDate, b);
                        setDisable(localDate.isBefore(LocalDate.now()));
                    }
                });
                datePicker.setPromptText("end date...");


                Button back=new Button("return");
                back.setOnAction(actionEvent -> {

                        App.getMainStage().setScene(manageAuctions());

                });

                Button submit=new Button("submit");
                submit.setOnAction(actionEvent -> {
                    if ((productChoiceBox.getValue()==null) || (datePicker.getValue()==null)){
                        new Alert(Alert.AlertType.ERROR,"please make sure to fill all the fields").show();
                    }else {
                        try {
                            new Auction(productChoiceBox.getValue(),datePicker.getValue().atStartOfDay());
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                        new Alert(Alert.AlertType.INFORMATION,"auction successfully added").show();

                            App.getMainStage().setScene(manageAuctions());

                    }
                });

                content.getChildren().addAll(productChoiceBox,datePicker,submit,back);
                pane.getChildren().add(content);
            }
        };
    }

    public void setFile(File input) {
        this.file = input;
    }
}
