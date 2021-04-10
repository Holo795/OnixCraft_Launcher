package ru.onixcraft.paulin.launcher.ui.window;

import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;


public abstract class WindowButton {

    private Button button;
    private GridPane pane;

    public WindowButton(String cssId, VPos vPos, HPos hPos, GridPane pane){
        this.button = new Button();
        this.button.setId(cssId);
        GridPane.setVgrow(this.button, Priority.ALWAYS);
        GridPane.setHgrow(this.button, Priority.ALWAYS);
        GridPane.setValignment(this.button, vPos);
        GridPane.setHalignment(this.button, hPos);
        this.button.setOnMouseClicked(e->{
            onClick();
        });

        this.pane = pane;
        pane.getChildren().add(this.button);

    }

    public abstract void onClick();

    public Button getButton(){
        return button;
    }

}
