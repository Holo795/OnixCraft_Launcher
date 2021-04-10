package ru.onixcraft.paulin.launcher.ui.panels;

import java.io.File;
import java.lang.management.ManagementFactory;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
import ru.onixcraft.paulin.launcher.ui.window.button.WindowExitButton;
import ru.onixcraft.paulin.launcher.utils.Utils;

public class OptionPanel {
	
	private double xOffset = 0;
    private double yOffset = 0;

    private WindowExitButton exitButton;
		
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
		exitButton.getButton().setOnMouseClicked(e->{
			stage.close();
		});
		
		CheckBox box = new CheckBox();
		GridPane.setVgrow(box, Priority.ALWAYS);
		GridPane.setHgrow(box, Priority.ALWAYS);
		GridPane.setValignment(box, VPos.BOTTOM);
		GridPane.setHalignment(box, HPos.LEFT);
		box.setId("optionBox");
		
		Label ramLabel = new Label(Utils.config.get("ram") + " МБ");
		GridPane.setVgrow(ramLabel, Priority.ALWAYS);
		GridPane.setHgrow(ramLabel, Priority.ALWAYS);
		GridPane.setValignment(ramLabel, VPos.CENTER);
		GridPane.setHalignment(ramLabel, HPos.CENTER);
		ramLabel.setId("ramLabel");
		
		com.sun.management.OperatingSystemMXBean mxbean = (com.sun.management.OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
		Slider slider = new Slider(512, mxbean.getTotalPhysicalMemorySize()/(1024*1024), Integer.parseInt(Utils.config.get("ram")));
		slider.setId("SliderO");
		GridPane.setVgrow(slider, Priority.ALWAYS);
		GridPane.setHgrow(slider, Priority.ALWAYS);
		GridPane.setValignment(slider, VPos.CENTER);
		GridPane.setHalignment(slider, HPos.CENTER);
		slider.setMinWidth(337);
		slider.setMaxWidth(337);
		slider.valueProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
				ramLabel.textProperty().setValue(
                        String.valueOf(newValue.intValue()+ " МБ"));
				Utils.config.put("ram", (Math.round((Double)newValue)+""));
			}
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
		
		optionPane.getChildren().addAll(slider, ramLabel, box);
		stage.show();
	}

	public boolean deleteDirectory(File directoryToBeDeleted) {
		File[] allContents = directoryToBeDeleted.listFiles();
		if (allContents != null) {
			for (File file : allContents) {
				deleteDirectory(file);
			}
		}
		return directoryToBeDeleted.delete();
	}
}
