package ru.onixcraft.paulin.launcher.ui;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import ru.onixcraft.paulin.launcher.OnixCraftLauncher;
import ru.onixcraft.paulin.launcher.ui.panel.IPanel;
import ru.onixcraft.paulin.launcher.utils.Utils;

public class PanelManager {

	private final OnixCraftLauncher onixcraftlauncher;
	private final Stage stage;
	private GridPane layout;

	private double xOffset = 0;
	private double yOffset = 0;

	public PanelManager(OnixCraftLauncher onixcraftlauncher, Stage stage) {
		this.onixcraftlauncher = onixcraftlauncher;
		this.stage = stage;
	}

	@SuppressWarnings("deprecation")
	public void init() {
		this.stage.setTitle("Onix-Craft Launcher");
		this.stage.getIcons().add(new Image(OnixCraftLauncher.getResource("login/icon.png")));
		this.stage.initStyle(StageStyle.TRANSPARENT);

		this.layout = new GridPane();
		this.layout.setId("layout");
		Scene scene = new Scene(layout, Color.TRANSPARENT);
		scene.getStylesheets().add(OnixCraftLauncher.getResource("stylesheet.css"));
		this.stage.setScene(scene);
		this.stage.setFocused(true);

		if (Utils.config.get("save") == null)
			Utils.config.put("save", "");
		if (Utils.config.get("ram") == null || Utils.config.get("ram").equals(""))
			Utils.config.put("ram", "1024");
		if (Utils.config.get("performanceMod") == null || Utils.config.get("performanceMod").equals(""))
			Utils.config.put("performanceMod", "false");

		if (!Utils.config.get("save").contains("true")) {
			if (Utils.config.get("username") == null) {
				Utils.config.put("username", "");
			}
			if (Utils.config.get("token") == null) {
				Utils.config.put("token", "");
			}
		}

		this.layout.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				xOffset = event.getSceneX();
				yOffset = event.getSceneY();
			}
		});
		this.layout.setOnMouseDragged(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				stage.setX(event.getScreenX() - xOffset);
				stage.setY(event.getScreenY() - yOffset);
			}
		});
	}

	public void showPanel(IPanel panel) {
		this.layout.getChildren().clear();
		this.layout.getChildren().add(panel.getLayout());
		panel.init(this);
		panel.onShow();
	}

	public Stage getStage() {
		return stage;
	}

	public OnixCraftLauncher getCraftLauncher() {
		return onixcraftlauncher;
	}

}
