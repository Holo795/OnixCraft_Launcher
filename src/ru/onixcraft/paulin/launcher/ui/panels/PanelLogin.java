package ru.onixcraft.paulin.launcher.ui.panels;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import org.json.JSONException;

import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import ru.onixcraft.paulin.launcher.OnixCraftLauncher;
import ru.onixcraft.paulin.launcher.events.Events;
import ru.onixcraft.paulin.launcher.ui.PanelManager;
import ru.onixcraft.paulin.launcher.ui.panel.Panel;
import ru.onixcraft.paulin.launcher.ui.window.button.WindowExitButton;
import ru.onixcraft.paulin.launcher.ui.window.button.WindowHideButton;
import ru.onixcraft.paulin.launcher.ui.window.button.WindowRamButton;
import ru.onixcraft.paulin.launcher.utils.Utils;

public class PanelLogin extends Panel {

    private static boolean saveIdCheck;

    private WindowExitButton exitButton;
    private WindowHideButton hideButton;
    private WindowRamButton ramButton;

    @Override
    public void init(PanelManager panelManager) {
        super.init(panelManager);

        this.panelManager.getStage().close();
        this.panelManager.getStage().show();
        this.panelManager.getStage().setHeight(470);
        this.panelManager.getStage().setWidth(612);
        this.panelManager.getStage().centerOnScreen();

        GridPane loginPanel = new GridPane();
        GridPane.setVgrow(loginPanel, Priority.ALWAYS);
        GridPane.setHgrow(loginPanel, Priority.ALWAYS);
        GridPane.setValignment(loginPanel, VPos.CENTER);
        GridPane.setHalignment(loginPanel, HPos.CENTER);
        loginPanel.setId("loginPanel");


        ImageView logoImg = new ImageView(new Image(OnixCraftLauncher.getResource("login/l_logo.png")));
        GridPane.setVgrow(logoImg, Priority.ALWAYS);
        GridPane.setHgrow(logoImg, Priority.ALWAYS);
        GridPane.setValignment(logoImg, VPos.TOP);
        GridPane.setHalignment(logoImg, HPos.CENTER);
        logoImg.setId("logoImg");

        TextField textField = new TextField(Utils.config.get("username"));
        GridPane.setVgrow(textField, Priority.ALWAYS);
        GridPane.setHgrow(textField, Priority.ALWAYS);
        textField.setId("textField");
        textField.setPromptText("Ник");

        PasswordField passwordField = new PasswordField();
        GridPane.setVgrow(passwordField, Priority.ALWAYS);
        GridPane.setHgrow(passwordField, Priority.ALWAYS);
        passwordField.setId("passwordField");
        passwordField.setPromptText("Пароль");

        Button loginButton = new Button();
        GridPane.setVgrow(loginButton, Priority.ALWAYS);
        GridPane.setHgrow(loginButton, Priority.ALWAYS);
        loginButton.setId("loginButton");
        loginButton.setOnMouseClicked(e -> {
            try {
                login(textField.getText(), passwordField.getText());
            } catch (JSONException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }

        });
        this.layout.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent ke) {
                if (ke.getCode().equals(KeyCode.ENTER)) {
                    try {
                        login(textField.getText(), passwordField.getText());
                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        });

        exitButton = new WindowExitButton(loginPanel, "quitButton", VPos.TOP, HPos.RIGHT);
        hideButton = new WindowHideButton(loginPanel, this.panelManager, "hideButton", VPos.TOP, HPos.RIGHT);
        ramButton = new WindowRamButton(loginPanel, "ramButton", VPos.TOP, HPos.RIGHT);

        Label saveLabel = new Label("Запомнить?");
        GridPane.setVgrow(saveLabel, Priority.ALWAYS);
        GridPane.setHgrow(saveLabel, Priority.ALWAYS);
        saveLabel.setId("saveLabel");

        Label forgetPassLabel = new Label("Забыли пароль?");
        GridPane.setVgrow(forgetPassLabel, Priority.ALWAYS);
        GridPane.setHgrow(forgetPassLabel, Priority.ALWAYS);
        GridPane.setValignment(forgetPassLabel, VPos.BOTTOM);
        GridPane.setHalignment(forgetPassLabel, HPos.CENTER);
        forgetPassLabel.setId("forgetPassLabel");
        forgetPassLabel.setOnMouseEntered(e -> this.layout.setCursor(Cursor.HAND));
        forgetPassLabel.setOnMouseExited(e -> this.layout.setCursor(Cursor.DEFAULT));
        forgetPassLabel.setOnMouseClicked(e -> Utils.openUrl(""));


        Label registerLabel = new Label("Регистрация");
        GridPane.setVgrow(registerLabel, Priority.ALWAYS);
        GridPane.setHgrow(registerLabel, Priority.ALWAYS);
        GridPane.setValignment(registerLabel, VPos.BOTTOM);
        GridPane.setHalignment(registerLabel, HPos.CENTER);
        registerLabel.setId("registerLabel");
        registerLabel.setOnMouseEntered(e -> this.layout.setCursor(Cursor.HAND));
        registerLabel.setOnMouseExited(e -> this.layout.setCursor(Cursor.DEFAULT));
        registerLabel.setOnMouseClicked(e -> Utils.openUrl("https://onix-craft.ru/index.php?do=register"));

        CheckBox saveBox = new CheckBox();
        saveBox.setSelected(Utils.config.get("save").contains("true"));
        GridPane.setVgrow(saveBox, Priority.ALWAYS);
        GridPane.setHgrow(saveBox, Priority.ALWAYS);
        saveBox.setId("saveBox");

        saveBox.selectedProperty().addListener(
                (ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
                    saveIdCheck = new_val;
                    Utils.config.put("save", new_val.toString());
                });


        loginPanel.getChildren().addAll(saveLabel, saveBox, textField, passwordField, logoImg, loginButton,
                forgetPassLabel, registerLabel);
        this.layout.getChildren().add(loginPanel);
    }

    public static boolean getSaveId() {
        return saveIdCheck;
    }

    private void login(String username, String password) throws JSONException {
        try {
            if (Utils.auth(username, password)) {
                Events.skinImg(username);
                if (Utils.getInfo(username).getString("active").contains("1")) {
                    Utils.config.put("username", username);
                    Utils.config.put("token", Utils.getInfo(username).getString("token"));
                    this.panelManager.showPanel(new HomePanel());
                } else {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Error account :");
                    alert.setContentText("Your account is disactivate");

                    alert.showAndWait();
                }
            } else {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Error login :");
                alert.setContentText("Invalid or incorrect login");

                alert.showAndWait();
            }
        } catch (IOException | NoSuchAlgorithmException e1) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error login :");
            alert.setContentText(e1.getMessage());

            alert.showAndWait();
        }
    }

}
