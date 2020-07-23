package org.entity;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.awt.*;
import java.util.ArrayList;

public class Alert extends Label{

    private String message;
    private Node node;

    public Alert(String message, Node node){
        this.message=message;
        this.node=node;
        createAlert();

    }

    public void createAlert(){
        javafx.scene.control.Label alert=new Label("*"+message);
        AnchorPane.setBottomAnchor(alert,0.0);
        AnchorPane.setLeftAnchor(alert,0.0);
    }
}
