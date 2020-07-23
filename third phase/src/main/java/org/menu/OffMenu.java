package org.menu;

import Model.Category;
import Model.Off;
import Model.Product;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.example.App;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class OffMenu extends Menu {

    private int itemsPerPage;
    private int dataSize;
    private Pagination pagination;
    private TableView<Product> productTableView;
    private ArrayList<Product> filteredProducts;
    private VBox filters;
    private Category selectedCategory;
    private GridPane attributes;

    public OffMenu(ScrollPane root) {
        super(root);
    }

    @Override
    public void init() {
        Off.updateOffs();
        pagination = new Pagination();
        productTableView = new TableView<>();
        filteredProducts = new ArrayList<>();

        for (Off off : Off.getOffArray()) {
            if (off.isActive())
            filteredProducts.addAll(off.getProductsArray());
        }

        setItems();

        pagination.setMaxPageIndicatorCount(7);

        productTableView = new TableView<>();

        TableColumn<Product, ImageView> imageColumn=new TableColumn<>("picture");
        imageColumn.setCellValueFactory(new PropertyValueFactory<>("picture"));

        TableColumn<Product, String> nameColumn = new TableColumn<>("name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Product, Float> priceColumn = new TableColumn<>("price");
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        TableColumn<Product, Float> priceWithOffColumn = new TableColumn<>("price with off");
        priceWithOffColumn.setCellValueFactory(new PropertyValueFactory<>("priceAfterOff"));

        TableColumn<Product, Integer> percentColumn = new TableColumn<>("off percent");
        percentColumn.setCellValueFactory(new PropertyValueFactory<>("offPercent"));

        TableColumn<Product, Float> scoreColumn = new TableColumn<>("score");
        scoreColumn.setCellValueFactory(new PropertyValueFactory<>("averageScore"));

        TableColumn<Product, String> timeColumn = new TableColumn<>("remained time");

        new Thread() {
            @Override
            public void run() {
                Scene scene = App.getMainStage().getScene();
                long lastUpdate = 0;
                long now;

                while (App.getMainStage().getScene().equals(scene)) {
                    now = System.currentTimeMillis();
                    if (now - lastUpdate >= 500) {
                        lastUpdate = now;
                        timeColumn.setCellValueFactory(new PropertyValueFactory<>("remainedTime"));
                    }
                }
            }
        }.start();

        productTableView.getColumns().addAll(imageColumn,nameColumn, priceColumn, priceWithOffColumn, percentColumn, scoreColumn, timeColumn);

        productTableView.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                    App.getMainStage().setScene(new UniqueProductMenu(new ScrollPane(), productTableView.getSelectionModel().getSelectedItem(), App.getMainStage().getScene()));

            }
        });

        pagination.setPageFactory(this::createPage);

        filters = new VBox();
        AnchorPane.setRightAnchor(filters, 0.0);
        initFilters();


        pane.getChildren().addAll(pagination, filters);
    }

    public Node createPage(int pageIndex) {
        int fromIndex = pageIndex * itemsPerPage;
        int toIndex = Math.min(fromIndex + itemsPerPage, dataSize);
        productTableView.setItems(FXCollections.observableList(filteredProducts.subList(fromIndex, toIndex)));
        return productTableView;
    }


    private void initFilters() {
        TreeView<Category> categoryTree = new TreeView<>();

        Category nullCategory=new Category("category",null,new ArrayList<String>());
        Category.getAllCategories().remove(nullCategory);
        categoryTree.setRoot(new TreeItem<Category>(nullCategory));
        categoryTree.setShowRoot(false);

        for (Category category : Category.getAllCategories()) {
            if (category.getParentCategory() == null) {
                TreeItem<Category> parent = new TreeItem<>(category);
                for (Category sub : category.getSubCategories()) {
                    parent.getChildren().add(new TreeItem<>(sub));
                }
                categoryTree.getRoot().getChildren().add(parent);
            }
        }

        categoryTree.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                selectedCategory = categoryTree.getSelectionModel().getSelectedItem().getValue();
                filters.getChildren().remove(categoryTree);
                categorizedFilters();
            }
        });

        filters.getChildren().add(0, categoryTree);

        HBox priceLimit = new HBox();

        priceLimit.getChildren().add(new Text("price limit : "));

        TextField minPrice = new TextField();
        minPrice.setPromptText("min price...");

        minPrice.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*")) {
                    minPrice.setText(oldValue);
                }
            }
        });

        TextField maxPrice = new TextField();
        maxPrice.setPromptText("max price");

        maxPrice.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*")) {
                    maxPrice.setText(oldValue);
                }
            }
        });

        priceLimit.getChildren().addAll(minPrice, maxPrice);
        priceLimit.setSpacing(5);

        filters.getChildren().add(1, priceLimit);

        Button update = new Button("update");
        filters.getChildren().add(2,update);

        Button view=new Button("view");
        view.setOnAction(event -> {

                App.getMainStage().setScene(new UniqueProductMenu(new ScrollPane(),productTableView.getSelectionModel().getSelectedItem(),App.getMainStage().getScene()));

        });

        update.setOnAction(event -> {
            filteredProducts.clear();
            for (Off off : Off.getOffArray()) {
                filteredProducts.addAll(off.getProductsArray());
            }

            if (!filters.getChildren().contains(categoryTree)) {

                filteredProducts.removeIf(searching -> searching.getCategory() != selectedCategory);

                for (int i = 0; i < attributes.getChildren().size() / 2; i++) {
                    String attribute = selectedCategory.getSpecialAttributes().get(i);

                    if (!((TextField) attributes.getChildren().get((i * 2) + 1)).getText().isEmpty()) {
                        int index = i;
                        filteredProducts.removeIf(
                                searching -> !searching.getSpecialAttributes().get(attribute).contains(((TextField) attributes.getChildren().get((index * 2) + 1)).getText()));
                    }
                }
            }

            if (!minPrice.getText().isEmpty()) {
                filteredProducts.removeIf(product -> product.getPrice() < Float.parseFloat(minPrice.getText()));
            }
            if (!maxPrice.getText().isEmpty()) {
                filteredProducts.removeIf(product -> product.getPrice() > Float.parseFloat(maxPrice.getText()));
            }

            productTableView.setItems(FXCollections.observableArrayList(filteredProducts));
            setItems();
        });

    }

    public void categorizedFilters() {

        attributes = new GridPane();
        attributes.setVgap(5);
        attributes.setHgap(3);

        for (int i = 0; i < selectedCategory.getSpecialAttributes().size(); i++) {
            attributes.add(new Text(selectedCategory.getSpecialAttributes().get(i)), 0, i);

            TextField textField = new TextField();
            textField.setPromptText(selectedCategory.getSpecialAttributes().get(i) + "...");
            attributes.add(textField, 1, i);
        }

        filters.getChildren().add(0, attributes);

    }

    private void setItems() {
        productTableView.setItems(FXCollections.observableArrayList(filteredProducts));

        dataSize = filteredProducts.size();
        itemsPerPage = 20;

        int pageNumber;
        if (dataSize == 0)
            pageNumber = 1;
        else if ((dataSize % itemsPerPage) == 0)
            pageNumber = dataSize / itemsPerPage;

        else
            pageNumber = dataSize / itemsPerPage + 1;

        pagination.setPageCount(pageNumber);
    }
}
