package org.menu;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.TextFieldTreeTableCell;
import javafx.scene.layout.AnchorPane;
import org.example.App;

public abstract class Menu extends Scene {
    protected ScrollPane stagePane;
    protected AnchorPane pane;
    protected Label label=new Label();

    public Menu(ScrollPane root) {
        super(root);
        pane=new AnchorPane();
        this.stagePane=root;
        init();
        stagePane.setFitToWidth(true);
        stagePane.setFitToHeight(true);
        stagePane.setContent(pane);
        getStylesheets().add("userPanel.css");
    }

    public abstract void init();
}
