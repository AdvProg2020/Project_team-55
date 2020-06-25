package org.menu;

import Model.*;
import com.sun.java.swing.plaf.motif.MotifOptionPaneUI;
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
import java.util.HashMap;
import java.util.Random;

public class SellerPanel extends Menu {
    public SellerPanel(ScrollPane root) {
        super(root);
    }

    @Override
    public void init() {
        VBox commands = new VBox();
        AnchorPane.setTopAnchor(commands,100.0);
        AnchorPane.setBottomAnchor(commands, 500.0);
        commands.setSpacing(15);

        Button company = new Button("view company information");

        Button sale = new Button("view sell history");
        sale.setOnAction(event -> App.getMainStage().setScene(viewSells()));

        Button manageProducts = new Button("manage products");
        manageProducts.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                App.getMainStage().setScene(manageProducts());
            }
        });

        Button categories = new Button("show categories");
        categories.setOnAction(event -> App.getMainStage().setScene(viewCategories()));

        Button offs = new Button("view offs");
        offs.setOnAction(event -> App.getMainStage().setScene(manageOffs()));

        commands.getChildren().addAll(company, sale, manageProducts, categories, offs);
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

    public Menu manageProducts() {
        return new Menu(new ScrollPane()) {
            @Override
            public void init() {
                TableView<Product> productTableView = new TableView<>();
                pane.getChildren().add(productTableView);

                AnchorPane.setLeftAnchor(productTableView, 300.0);


                ObservableList<Product> products = FXCollections.observableArrayList();
                // products.addAll(((Seller)User.getActiveUser()).getArrayProduct()); right one
                products.addAll(Product.getAllProducts()); //wrong one
                productTableView.setItems(products);

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


                productTableView.getColumns().addAll(nameColumn, brandColumn, categoryColumn, priceColumn, buyerColumn);

                HBox buttons = new HBox();

                Button add = new Button("add");
                add.setOnAction(event -> App.getMainStage().setScene(addProduct()));

                Button delete = new Button("delete");
                delete.setOnAction(event -> {
                    //todo check nullification
                    Product.removeProduct(productTableView.getSelectionModel().getSelectedItem());
                    products.clear();
                    products.addAll(Product.getAllProducts());
                    productTableView.setItems(products);
                });

                Button edit = new Button("edit");
                edit.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        App.getMainStage().setScene(editProduct(productTableView.getSelectionModel().getSelectedItem()));
                    }
                });

                Button back = new Button("return");
                back.setOnAction(event -> App.getMainStage().setScene(new SellerPanel(new ScrollPane())));

                buttons.getChildren().addAll(add, delete, edit, back);

                pane.getChildren().add(buttons);
            }
        };
    }

    public Menu addProduct() {
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

                VBox attributeTexts = new VBox();
                VBox attributeTextFields = new VBox();

                attributeTexts.setSpacing(10);
                attributeTextFields.setSpacing(10);

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
                form.add(explanationArea, 1, 4);

                Button getImage=new Button("upload image");

                form.add(getImage,0,5);

                FileChooser imageChooser=new FileChooser();
                File file=null;

                getImage.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                       setFile(file,imageChooser.showOpenDialog(App.getMainStage()));
                    }
                });


                getImage.setOnAction(event ->{
                 setFile(file,imageChooser.showOpenDialog(App.getMainStage()));
                });

                pane.getChildren().add(form);


                HBox buttons = new HBox();
                pane.getChildren().add(buttons);

                Button back = new Button("return");
                back.setOnAction(event -> App.getMainStage().setScene(manageProducts()));


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


                    if(file==null){
                        alert.show();
                        return;
                    }


                    new ProductAdditionRequest((Seller) User.getActiveUser(), idValue.getText(), Float.parseFloat(priceField.getText()),
                            categoryChoiceBox.getSelectionModel().getSelectedItem(), attributesHashMap, explanationArea.getText() , new Image(file.toURI().toString()));

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

                Text idValue = new Text();

                idValue.setText(Integer.toString(random.nextInt(899999) + 100000));
                while (Product.getProductById(idValue.getText()) != null) {
                    idValue.setText(Integer.toString(random.nextInt(899999) + 100000));
                }

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


                pane.getChildren().add(form);


                HBox buttons = new HBox();
                pane.getChildren().add(buttons);

                Button back = new Button("return");
                back.setOnAction(event -> App.getMainStage().setScene(manageProducts()));


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


                    new ProductEditRequest((Seller) User.getActiveUser(), product, Float.parseFloat(priceField.getText()),
                            categoryChoiceBox.getSelectionModel().getSelectedItem(), attributesHashMap, explanationArea.getText());

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
                offObservableList.addAll(((Seller) User.getActiveUser()).getSellerOffs());
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
                back.setOnAction(event -> App.getMainStage().setScene(new SellerPanel(new ScrollPane())));

                Button add = new Button("add");
                add.setOnAction(event -> App.getMainStage().setScene(addOff()));

                Button edit = new Button("edit");

                edit.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        App.getMainStage().setScene(editOff(offTable.getSelectionModel().getSelectedItem()));
                    }
                });

                buttons.getChildren().addAll(back, add, edit);
                pane.getChildren().add(buttons);
            }
        };
    }

    public Menu addOff() {
        return new Menu(new ScrollPane()) {
            @Override
            public void init() {
                GridPane form = new GridPane();
                AnchorPane.setLeftAnchor(form, 300.0);
                AnchorPane.setTopAnchor(form, 100.0);

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
                        if (!newValue.matches("\\d{0,2}")) ;
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

                VBox buyerChoices = new VBox();
                for (Product product : ((Seller)User.getActiveUser()).getArrayProduct()) {
                    buyerChoices.getChildren().add(new CheckBox(product.getProductId()));
                }
                buyerChoices.setSpacing(5);
                form.add(buyerChoices, 1, 4);

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
                                               ArrayList<Product> selected=new ArrayList<>();
                                               for (Node checkBox : buyerChoices.getChildren()) {
                                                   if (((CheckBox)checkBox).isSelected()){
                                                       selected.add(Product.getProductById(((CheckBox)checkBox).getText()));
                                                   }
                                               }
                                               if (selected.isEmpty()){
                                                   alert.setVisible(true);
                                               }else {
                                                   new OffAdditionRequest(((Seller)User.getActiveUser()),idValue.getText(),selected,
                                                           startDatePicker.getValue().atStartOfDay(),stopDatePicker.getValue().atStartOfDay(),Integer.parseInt(percentField.getText()));
                                               }
                                           }
                                       }
                                   }
                );

            }
        };
    }


    public Menu editOff(Off off){
        return new Menu(new ScrollPane()) {
            @Override
            public void init() {
                    GridPane form = new GridPane();
                    AnchorPane.setLeftAnchor(form, 300.0);
                    AnchorPane.setTopAnchor(form, 100.0);

                    Text id = new Text("id : ");
                    form.add(id, 0, 0);

                    Random random = new Random();
                    Text idValue = new Text(off.getOffId());


                    form.add(idValue, 1, 0);

                    Text percent = new Text("off percent : ");
                    form.add(percent, 0, 1);

                    TextField percentField = new TextField();

                    percentField.setText(""+off.getOffAmount());

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
                    for (Product product : ((Seller)User.getActiveUser()).getArrayProduct()) {
                        productChoices.getChildren().add(new CheckBox(product.getProductId()));

                        CheckBox checkBox=new CheckBox(product.getProductId());
                        if(off.getProductsArray().contains(product)){

                            checkBox.setSelected(true);
                        }

                        productChoices.getChildren().add(checkBox);
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
                                                   ArrayList<Product> selected=new ArrayList<>();
                                                   for (Node checkBox : productChoices.getChildren()) {
                                                       if (((CheckBox)checkBox).isSelected()){
                                                           selected.add(Product.getProductById(((CheckBox)checkBox).getText()));
                                                       }
                                                   }
                                                   if (selected.isEmpty()){
                                                       alert.setVisible(true);
                                                   }else {
                                                       new OffEditRequest(off,((Seller)User.getActiveUser()),selected,
                                                               startDatePicker.getValue().atStartOfDay(),stopDatePicker.getValue().atStartOfDay(),Integer.parseInt(percentField.getText()));
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
                categoryTree.setRoot(null);
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
                back.setOnAction(event -> App.getMainStage().setScene(new SellerPanel(new ScrollPane())));
                pane.getChildren().add(back);

            }
        };
    }

    public Menu viewSells(){
        return new Menu(new ScrollPane()) {
            @Override
            public void init() {
                TableView<SellLog> sellTable=new TableView<>();
                sellTable.setItems(FXCollections.observableArrayList(((Seller)User.getActiveUser()).getSellHistory()));

                TableColumn<SellLog,String> idColumn=new TableColumn<>("sell id");
                idColumn.setCellValueFactory(new PropertyValueFactory<>("logId"));

                TableColumn<SellLog,String> dateColumn=new TableColumn<>("date");
                dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));

                TableColumn<SellLog,Product> productColumn=new TableColumn<>("product");
                productColumn.setCellValueFactory(new PropertyValueFactory<>("boughtProduct"));

                TableColumn<SellLog,Integer> numberColumn=new TableColumn<>("quantity");
                numberColumn.setCellValueFactory(new PropertyValueFactory<>("number"));

                TableColumn<SellLog,Float> moneyColumn=new TableColumn<>("price");
                moneyColumn.setCellValueFactory(new PropertyValueFactory<>("purchasedMoney"));

                sellTable.getColumns().addAll(idColumn,dateColumn,productColumn,numberColumn,moneyColumn);
                AnchorPane.setLeftAnchor(sellTable,300.0);
                AnchorPane.setTopAnchor(sellTable,100.0);

                sellTable.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        App.getMainStage().setScene(specificSell(sellTable.getSelectionModel().getSelectedItem()));
                    }
                });

                VBox buttons=new VBox();

                Button back=new Button("return");
                back.setOnAction(event -> App.getMainStage().setScene(new SellerPanel(new ScrollPane())));

                buttons.getChildren().addAll(back);

                pane.getChildren().addAll(sellTable,buttons);
            }
        };
    }

    public Menu specificSell(SellLog sell){
        return new Menu(new ScrollPane()) {
            @Override
            public void init() {
                GridPane form=new GridPane();

                form.add(new Text("id : "),0,0);
                form.add(new Text(sell.getLogId()),1,0);
                form.add(new Text("date : "),0,1);
                form.add(new Text(sell.getDate()),1,1);
                form.add(new Text("buyer : "),0,2);
                form.add(new Text(sell.getBuyer().getUsername()),1,2);
                form.add(new Text("product : "),0,3);
                form.add(new Text(sell.getBoughtProduct().getName()),1,3);
                form.add(new Text("quantity : "),0,4);
                form.add(new Text(""+sell.getNumber()),1,4);
                form.add(new Text("purchased price : "),0,5);
                form.add(new Text(""+sell.getPurchasedMoney()),1,5);
                form.add(new Text("off percent : "),0,6);
                form.add(new Text(""+sell.getOffPercent()),1,6);

                Button back=new Button("return");
                back.setOnAction(event -> App.getMainStage().setScene(viewSells()));
                form.add(back,0,7);

                pane.getChildren().add(form);

            }
        };
    }

    public void setFile (File file , File input){
        file=input;
    }

}
