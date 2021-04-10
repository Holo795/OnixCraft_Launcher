package ru.onixcraft.paulin.launcher.ui;

import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import ru.onixcraft.paulin.launcher.OnixCraftLauncher;
import ru.onixcraft.paulin.launcher.ui.panels.HomePanel;

public class ServerTab {

    private Label title;
    private final ImageView background;
    private final GridPane pane;
    private final String backgroundModName;

    public ServerTab(String title, int posX, int posY, String backgroundModName, String color, int place) {
        // Background
        this.background = new ImageView(new Image(OnixCraftLauncher.getResource("servers/s_" + backgroundModName + "_inact.png")));
        this.backgroundModName = backgroundModName;
        // Pane setup.
        this.pane = new GridPane();
        this.pane.setMaxHeight(76);
        this.pane.setMinHeight(76);
        this.pane.setMaxWidth(197);
        this.pane.setMinWidth(197);
        // Label setup.
        initLabel(title);
        // Background setup.
        initBackground();

        // Style.
        switch (color) {
            case "blue":
                this.pane.setStyle("-fx-background-color: linear-gradient(from 25px 25px to 100px 100px, rgba(14, 15, 30, 0.7), rgba(9, 25, 59, 0.7));");
                break;
            case "green":
                this.pane.setStyle("-fx-background-color: linear-gradient(from 25% 25% to 100% 100%, rgba(14, 17, 31, 0.7), rgba(19, 50, 19, 0.7));");
                break;
            case "purple":
                this.pane.setStyle("-fx-background-color: linear-gradient(from 25px 25px to 100px 100px, rgba(16, 14, 31, 0.7), rgba(27, 29, 70, 0.7));");
                break;
        }

        // EventHandling. A CHANGER VU QUE T'AS UN TOUT NOUVEAU FIX ^^
        this.pane.setOnMouseEntered(e -> {
            // Fait gaffe au contenu du getRessource()
            this.background.setImage(new Image(OnixCraftLauncher.getResource("servers/s_" + backgroundModName + "_act.png")));
        });
        this.pane.setOnMouseExited(e -> {
            // Fait gaffe au contenu du getRessource()
            this.background.setImage(new Image(OnixCraftLauncher.getResource("servers/s_" + backgroundModName + "_inact.png")));
        });

        this.pane.setOnMouseExited(e->{
            if(HomePanel.paneNumber != place) {
                this.background.setImage(new Image(OnixCraftLauncher.getResource("servers/s_" + backgroundModName + "_inact.png")));
            } else {
                this.background.setImage(new Image(OnixCraftLauncher.getResource("servers/s_" + backgroundModName + "_act.png")));
            }
        });
        this.pane.setOnMouseClicked(e->{
            HomePanel.refresh();
            this.background.setImage(new Image(OnixCraftLauncher.getResource("servers/s_" + backgroundModName + "_act.png")));
            HomePanel.paneNumber = place;
        });

        // Translation.
        this.pane.setTranslateX(posX);
        this.pane.setTranslateY(posY);

        // Build.
        this.pane.getChildren().addAll(this.background, this.title);
    }

    private void initLabel(String title) {
        this.title = new Label(title);

        GridPane.setVgrow(this.title, Priority.ALWAYS);
        GridPane.setHgrow(this.title, Priority.ALWAYS);
        GridPane.setValignment(this.title, VPos.TOP);
        GridPane.setHalignment(this.title, HPos.LEFT);

        this.title.setTranslateX(3);
        this.title.setStyle("-fx-font-size: 13px;");
    }

    private void initBackground() {
        GridPane.setVgrow(this.background, Priority.ALWAYS);
        GridPane.setHgrow(this.background, Priority.ALWAYS);
        GridPane.setValignment(this.background, VPos.CENTER);
        GridPane.setHalignment(this.background, HPos.CENTER);
    }

    public GridPane getPane(){
        return pane;
    }

    public ImageView getBackground(){
        return background;
    }

    public String getBackgroundModName() {
        return backgroundModName;
    }
}