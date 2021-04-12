package ru.onixcraft.paulin.launcher.ui.panels;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import ru.onixcraft.paulin.launcher.OnixCraftLauncher;
import ru.onixcraft.paulin.launcher.ui.window.button.WindowDoneButton;
import ru.onixcraft.paulin.launcher.ui.window.button.WindowExitButton;
import ru.onixcraft.paulin.launcher.ui.window.button.WindowOpenButton;
import ru.onixcraft.paulin.launcher.ui.window.button.WindowResetButton;
import ru.onixcraft.paulin.launcher.utils.Utils;

import java.lang.management.ManagementFactory;

public class OptionPanel {

    private double xOffset = 0;
    private double yOffset = 0;

    private double ramValue = 0.0;
    private double ramOldValue = 0.0;
    private Boolean perfMod = false;

    private WindowExitButton exitButton;
    private WindowResetButton resetButton;
    private WindowOpenButton openButton;
    private WindowDoneButton doneButton;

    @SuppressWarnings("restriction")
    public OptionPanel() {

        Stage stage = new Stage();
        GridPane optionPane = new GridPane();

        stage.setTitle("Onix-Craft Settings");
        stage.getIcons().add(new Image(OnixCraftLauncher.getResource("login/icon.png")));
        stage.initStyle(StageStyle.TRANSPARENT);

        optionPane.setId("optionPane");
        Scene scene = new Scene(optionPane, Color.TRANSPARENT);
        scene.getStylesheets().add(OnixCraftLauncher.getResource("stylesheet.css"));
        stage.setScene(scene);

        optionPane.setMaxWidth(386);
        optionPane.setMinWidth(386);
        optionPane.setMaxHeight(467);
        optionPane.setMinHeight(467);
        GridPane.setVgrow(optionPane, Priority.ALWAYS);
        GridPane.setHgrow(optionPane, Priority.ALWAYS);
        GridPane.setValignment(optionPane, VPos.CENTER);
        GridPane.setHalignment(optionPane, HPos.CENTER);

        exitButton = new WindowExitButton(optionPane, "CloseO", VPos.TOP, HPos.RIGHT);
        exitButton.getButton().setOnMouseClicked(e -> {
            stage.close();
        });

        resetButton = new WindowResetButton(optionPane, "resetButton", VPos.CENTER, HPos.RIGHT);
        openButton = new WindowOpenButton(optionPane, "openButton", VPos.CENTER, HPos.LEFT);

        Label titleLabel = new Label("Настройки");
        GridPane.setVgrow(titleLabel, Priority.ALWAYS);
        GridPane.setHgrow(titleLabel, Priority.ALWAYS);
        GridPane.setValignment(titleLabel, VPos.TOP);
        GridPane.setHalignment(titleLabel, HPos.CENTER);
        titleLabel.setId("titleLabel");

        Label dirLabel = new Label(Utils.dir.getAbsolutePath());
        GridPane.setVgrow(dirLabel, Priority.ALWAYS);
        GridPane.setHgrow(dirLabel, Priority.ALWAYS);
        GridPane.setValignment(dirLabel, VPos.TOP);
        GridPane.setHalignment(dirLabel, HPos.LEFT);
        dirLabel.setId("dirLabel");

        Label performLabel = new Label("Режим производительности");
        GridPane.setVgrow(performLabel, Priority.ALWAYS);
        GridPane.setHgrow(performLabel, Priority.ALWAYS);
        GridPane.setValignment(performLabel, VPos.CENTER);
        GridPane.setHalignment(performLabel, HPos.CENTER);
        performLabel.setId("performLabel");

        Label memoryLabel = new Label("Выделение памяти");
        GridPane.setVgrow(memoryLabel, Priority.ALWAYS);
        GridPane.setHgrow(memoryLabel, Priority.ALWAYS);
        GridPane.setValignment(memoryLabel, VPos.CENTER);
        GridPane.setHalignment(memoryLabel, HPos.CENTER);
        memoryLabel.setId("memoryLabel");

        Label ramLabel = new Label(Utils.config.get("ram") + " МБ");
        GridPane.setVgrow(ramLabel, Priority.ALWAYS);
        GridPane.setHgrow(ramLabel, Priority.ALWAYS);
        GridPane.setValignment(ramLabel, VPos.CENTER);
        GridPane.setHalignment(ramLabel, HPos.CENTER);
        ramLabel.setId("ramLabel");

        com.sun.management.OperatingSystemMXBean mxbean = (com.sun.management.OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        Slider slider = new Slider(512, mxbean.getTotalPhysicalMemorySize() / (1024 * 1024), Integer.parseInt(Utils.config.get("ram")));
        slider.setId("SliderO");
        GridPane.setVgrow(slider, Priority.ALWAYS);
        GridPane.setHgrow(slider, Priority.ALWAYS);
        GridPane.setValignment(slider, VPos.CENTER);
        GridPane.setHalignment(slider, HPos.CENTER);
        slider.setDisable(Boolean.parseBoolean(Utils.config.get("performanceMod")));
        slider.setMinWidth(337);
        slider.setMaxWidth(337);
        slider.valueProperty().addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
                ramLabel.textProperty().setValue(
                        String.valueOf(newValue.intValue() + " МБ"));
                ramValue = (double) newValue;
                ramOldValue = (double) oldValue;
            }
        });

        CheckBox box = new CheckBox();
        GridPane.setVgrow(box, Priority.ALWAYS);
        GridPane.setHgrow(box, Priority.ALWAYS);
        GridPane.setValignment(box, VPos.BOTTOM);
        GridPane.setHalignment(box, HPos.LEFT);
        box.setSelected(Boolean.parseBoolean(Utils.config.get("performanceMod")));
        box.setId("optionBox");
        box.selectedProperty().addListener(
                (ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
                    perfMod = new_val;
                    slider.setDisable(new_val);
                    if(new_val) {
                        slider.setValue(mxbean.getFreePhysicalMemorySize()/ (1024 * 1024) - 1024 < 1024 ?
                         1024 : mxbean.getFreePhysicalMemorySize()/ (1024 * 1024) - 1024);
                        System.out.println(mxbean.getFreePhysicalMemorySize()/ (1024 * 1024));
                    } else {
                        slider.setValue(ramOldValue);
                    }
                });

        doneButton = new WindowDoneButton(optionPane, "doneButton", VPos.BOTTOM, HPos.CENTER);
        doneButton.getButton().setOnMouseClicked(e -> {
            if(ramValue != 0.0){
                Utils.config.put("ram", (Math.round((Double)ramValue)+""));
            }
            Utils.config.put("performanceMod", perfMod.toString());
            stage.close();
        });


        optionPane.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });
        optionPane.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                stage.setX(event.getScreenX() - xOffset);
                stage.setY(event.getScreenY() - yOffset);
            }
        });

        optionPane.getChildren().addAll(slider, ramLabel, box, dirLabel, titleLabel, performLabel, memoryLabel);
        stage.show();
    }

}
