package org.menu;

import Model.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.SubScene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTreeTableCell;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.example.App;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Random;

public class ManagerPanel extends Menu {

    SubScene discountSub, userSub;

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
        commands.setLayoutY(300);

        AnchorPane.setBottomAnchor(commands, 0.0);

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

        commands.getChildren().addAll(manageUsers, manageCategories, manageProducts,
                createDiscount, viewDiscounts, manageRequests);

        pane.getChildren().add(commands);

        ImageView profile = new ImageView();
        profile.setLayoutX(0);
        profile.setLayoutY(0);

        pane.getChildren().add(profile);
    }

    public void getInfoGrid() {
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


    public Menu manageUsers() {
        return new Menu(new ScrollPane()) {
            @Override
            public void init() {
                HBox list = new HBox();

                TableView<User> userList = new TableView<>();
                userList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

                TableColumn<User, String> name = new TableColumn<>("username");
                name.setCellValueFactory(new PropertyValueFactory<>("username"));

                TableColumn<User, String> role = new TableColumn<>("role");
                role.setCellValueFactory(new PropertyValueFactory<>(getClass().getSimpleName()));

                TableColumn<User, Void> select = new TableColumn<>();
                select.setCellValueFactory(new PropertyValueFactory<>("select"));

                ObservableList<User> users = FXCollections.observableArrayList();
                users.addAll(User.getUsers());
                userList.setItems(users);
                userList.getColumns().addAll(name, role, select);

                list.getChildren().add(userList);

                pane.getChildren().add(list);

                Button cancel = new Button("return");
                cancel.setOnAction(event -> App.getMainStage().setScene(new ManagerPanel(new ScrollPane())));
                AnchorPane.setBottomAnchor(cancel, 0.0);
                pane.getChildren().add(cancel);

                Button delete = new Button("delete selected");
                delete.setOnAction(event -> deleteUsers(users));

                pane.getChildren().add(delete);

                stagePane.setContent(pane);
            }
        };
    }


    public void deleteUsers(ObservableList<User> users) {
        User selected;
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getSelect().isSelected()) {
                selected = users.get(i--);
                users.remove(selected);
                User.getUsers().remove(selected);
            }
        }
    }


    public Menu manageCategories() {
        return new Menu(new ScrollPane()) {
            @Override
            public void init() {
                HBox categories = new HBox();
                categories.setFillHeight(true);
                AnchorPane.setLeftAnchor(categories, 300.0);

                TreeTableView<Category> categoryTree = new TreeTableView<>();
                categoryTree.setEditable(true);
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
                delete.setOnAction(event -> deleteCategory(categoryTree.getSelectionModel().getSelectedItem().getValue()));

                Button add = new Button("add");
                add.setOnAction(event -> App.getMainStage().setScene(getNewCategory()));

                Button edit = new Button("edit");
                edit.setOnAction(event ->
                        App.getMainStage().setScene(editCategory(categoryTree.getSelectionModel().getSelectedItem().getValue())));

                buttons.getChildren().addAll(back, delete, add,edit);

                pane.getChildren().addAll(categories, buttons);


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
            App.getMainStage().setScene(manageCategories());
    }

    public Menu editCategory(Category category){
        return new Menu(new ScrollPane()) {
            @Override
            public void init() {
                GridPane grid = new GridPane();


                Text name = new Text("name:");
                grid.add(name, 0, 0);

                TextField nameField = new TextField();
                nameField.setText(category.getName());
                nameField.setOnInputMethodTextChanged(event -> {
                    if (Category.getCategoryByName(nameField.getText()) != null)
                        System.out.println("existing category");
                });
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

                for (String attribute:category.getSpecialAttributes()){
                    TextField att=new TextField();
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
                    if (nameField.getText().isEmpty())
                        blankField.setVisible(true);
                    else if (parentChoice.getSelectionModel().isEmpty())
                        blankField.setVisible(true);
                    else if (((TextField) attributeBox.getChildren().get(0)).getText().isEmpty())
                        blankField.setVisible(true);
                    else {
                        //todo submit changes

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


                Text name = new Text("name:");
                grid.add(name, 0, 0);

                TextField nameField = new TextField();
                nameField.setOnInputMethodTextChanged(event -> {
                    if (Category.getCategoryByName(nameField.getText()) != null)
                        System.out.println("existing category");
                });
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
                    if (nameField.getText().isEmpty())
                        blankField.setVisible(true);
                    else if (parentChoice.getSelectionModel().isEmpty())
                        blankField.setVisible(true);
                    else if (((TextField) attributeBox.getChildren().get(0)).getText().isEmpty())
                        blankField.setVisible(true);
                    else {
                        addCategory(nameField.getText(), Category.getCategoryByName(parentChoice.getSelectionModel().getSelectedItem().toString()), attributeBox);
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
                        App.getMainStage().setScene(new ManagerPanel(new ScrollPane()));
                    }
                });
                form.add(ok, 1, 8);


                pane.getChildren().add(form);
                stagePane.setContent(pane);
            }
        };
    }

    public Menu manageDiscounts(){
        return new Menu(new ScrollPane()) {
            @Override
            public void init() {
                TableView<OffWithCode> discounts=new TableView<>();

                ObservableList<OffWithCode> codes=FXCollections.observableArrayList();
                codes.addAll(OffWithCode.getAllDiscounts());

                discounts.setItems(codes);

                TableColumn<OffWithCode,String> code=new TableColumn<>("code");
                code.setCellValueFactory(new PropertyValueFactory<>("offCode"));

                TableColumn<OffWithCode, LocalDateTime> startTime=new TableColumn<>("start date");
                startTime.setCellValueFactory(new PropertyValueFactory<>("startDate"));

                TableColumn<OffWithCode,LocalDateTime> stopTime=new TableColumn<>("stop date");
                stopTime.setCellValueFactory(new PropertyValueFactory<>("stopDate"));

                AnchorPane.setLeftAnchor(discounts,300.0);

                discounts.getColumns().addAll(code,startTime,stopTime);

                HBox buttons=new HBox();
                buttons.setSpacing(10);

                Button back=new Button("return");
                back.setOnAction(event -> App.getMainStage().setScene(new ManagerPanel(new ScrollPane())));

                Button delete=new Button("delete");
                delete.setOnAction(event -> deleteDiscount(discounts.getSelectionModel().getSelectedItem()));

                Button edit=new Button("edit");
                edit.setOnAction(event ->  App.getMainStage().setScene(editDiscount(discounts.getSelectionModel().getSelectedItem())));

                buttons.getChildren().addAll(back,delete,edit);

                pane.getChildren().addAll(buttons,discounts);
            }
        };
    }

    public Menu editDiscount(OffWithCode discount){
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

                for (Buyer buyer:discount.getApplyingAccounts().keySet()){
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
                        //todo apply edits
                        App.getMainStage().setScene(new ManagerPanel(new ScrollPane()));
                    }
                });
                form.add(ok, 1, 8);


                pane.getChildren().add(form);
                stagePane.setContent(pane);
            }
        };
    }

    public void deleteDiscount(OffWithCode discount){
       OffWithCode.deleteDiscount(discount);
       App.getMainStage().setScene(manageDiscounts());
    }

}
