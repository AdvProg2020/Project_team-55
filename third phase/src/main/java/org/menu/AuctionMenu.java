package org.menu;

import Model.Auction;
import Model.Product;
import javafx.collections.FXCollections;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.App;

import java.io.IOException;

public class AuctionMenu extends Menu{


    public AuctionMenu(ScrollPane root)  {
        super(root);
    }

    @Override
    public void init() {
        TableView<Auction> auctionTable=new TableView<>();
        auctionTable.setItems(FXCollections.observableArrayList(Auction.getAllAuctions()));

        TableColumn<Auction, Product> productColumn=new TableColumn<>("product");
        productColumn.setCellValueFactory(new PropertyValueFactory<>("product"));

        TableColumn<Auction, Integer> highestColumn=new TableColumn<>("highest proposal");
        highestColumn.setCellValueFactory(new PropertyValueFactory<>("highestProposal"));

        auctionTable.getColumns().addAll(productColumn,highestColumn);

        auctionTable.setOnMouseClicked(mouseEvent -> {
            App.getMainStage().setScene(auctionTable.getSelectionModel().getSelectedItem().getAuctionMenu(this));
        });

        pane.getChildren().add(auctionTable);
    }
}
