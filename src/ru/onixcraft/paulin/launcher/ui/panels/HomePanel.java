package ru.onixcraft.paulin.launcher.ui.panels;

import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import org.json.JSONException;
import ru.onixcraft.paulin.launcher.OnixCraftLauncher;
import ru.onixcraft.paulin.launcher.events.Events;
import ru.onixcraft.paulin.launcher.ui.PanelManager;
import ru.onixcraft.paulin.launcher.ui.ServerTab;
import ru.onixcraft.paulin.launcher.ui.SliderManager;
import ru.onixcraft.paulin.launcher.ui.panel.Panel;
import ru.onixcraft.paulin.launcher.ui.window.button.WindowExitButton;
import ru.onixcraft.paulin.launcher.ui.window.button.WindowHideButton;
import ru.onixcraft.paulin.launcher.ui.window.button.WindowRamButton;
import ru.onixcraft.paulin.launcher.ui.window.button.WindowSiteButton;
import ru.onixcraft.paulin.launcher.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class HomePanel extends Panel {

    public static int paneNumber = 0;

    private static final List<ServerTab> tabRegistry = new ArrayList<ServerTab>();


    private WindowExitButton exitButton;
    private WindowHideButton hideButton;
    private WindowRamButton ramButton;
    private WindowSiteButton siteButton;

    @Override
    public void init(PanelManager panelManager) {
        super.init(panelManager);
        this.panelManager.getStage().close();
        this.panelManager.getStage().show();
        this.panelManager.getStage().setHeight(565);
        this.panelManager.getStage().setWidth(1009);
        this.panelManager.getStage().centerOnScreen();

        GridPane homePanel = new GridPane();
        GridPane.setVgrow(homePanel, Priority.ALWAYS);
        GridPane.setHgrow(homePanel, Priority.ALWAYS);
        GridPane.setValignment(homePanel, VPos.CENTER);
        GridPane.setHalignment(homePanel, HPos.CENTER);
        homePanel.setId("homePanel");

        Label name = new Label();
        GridPane.setVgrow(name, Priority.ALWAYS);
        GridPane.setHgrow(name, Priority.ALWAYS);
        GridPane.setValignment(name, VPos.TOP);
        GridPane.setHalignment(name, HPos.LEFT);
        try {
            name.setText(Utils.getInfoJson().getString("name"));
        } catch (JSONException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        name.setId("nameLabel");

        Label disconnect = new Label("Выйти из аккаунта");
        GridPane.setVgrow(disconnect, Priority.ALWAYS);
        GridPane.setHgrow(disconnect, Priority.ALWAYS);
        GridPane.setValignment(disconnect, VPos.TOP);
        GridPane.setHalignment(disconnect, HPos.LEFT);
        disconnect.setOnMouseEntered(e -> this.layout.setCursor(Cursor.HAND));
        disconnect.setOnMouseExited(e -> this.layout.setCursor(Cursor.DEFAULT));
        disconnect.setOnMouseClicked(e -> {
            Utils.config.put("save", "false");
            Utils.config.put("token", "");
            this.panelManager.showPanel(new PanelLogin());
        });
        disconnect.setId("discLabel");

        ImageView rankImg = new ImageView();
        GridPane.setVgrow(rankImg, Priority.ALWAYS);
        GridPane.setHgrow(rankImg, Priority.ALWAYS);
        GridPane.setValignment(rankImg, VPos.TOP);
        GridPane.setHalignment(rankImg, HPos.LEFT);

        try {
            switch (Utils.getInfoJson().getString("rank")) {
                case "admin":
                    rankImg.setImage(new Image(OnixCraftLauncher.getResource("personal/stats_admin.png")));
                    break;
                case "dev":
                    rankImg.setImage(new Image(OnixCraftLauncher.getResource("personal/stats_dev.png")));
                    break;
                case "helper":
                    rankImg.setImage(new Image(OnixCraftLauncher.getResource("personal/stats_helper.png")));
                    break;
                case "mod":
                    rankImg.setImage(new Image(OnixCraftLauncher.getResource("personal/stats_mod.png")));
                    break;
                case "premium":
                    rankImg.setImage(new Image(OnixCraftLauncher.getResource("personal/stats_premium.png")));
                    break;
                case "ultra":
                    rankImg.setImage(new Image(OnixCraftLauncher.getResource("personal/stats_ultra.png")));
                    break;
                case "vip":
                    rankImg.setImage(new Image(OnixCraftLauncher.getResource("personal/stats_vip.png")));
                    break;
                default:
                    rankImg.setImage(new Image(OnixCraftLauncher.getResource("personal/stats_gamer.png")));
                    break;
            }
        } catch (JSONException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        rankImg.setId("rankImg");

        Label accountLink = new Label("ПОПОЛНИТЬ СЧЁТ");
        GridPane.setVgrow(accountLink, Priority.ALWAYS);
        GridPane.setHgrow(accountLink, Priority.ALWAYS);
        GridPane.setValignment(accountLink, VPos.TOP);
        GridPane.setHalignment(accountLink, HPos.LEFT);
        accountLink.setId("accountLink");

        Label voteLabel = new Label();
        GridPane.setVgrow(voteLabel, Priority.ALWAYS);
        GridPane.setHgrow(voteLabel, Priority.ALWAYS);
        GridPane.setValignment(voteLabel, VPos.TOP);
        GridPane.setHalignment(voteLabel, HPos.LEFT);
        try {
            voteLabel.setText(Utils.getInfoJson().getString("vote"));
        } catch (JSONException e3) {
            // TODO Auto-generated catch block
            e3.printStackTrace();
        }
        voteLabel.setId("voteLabel");

        Label moneyLabel = new Label();
        GridPane.setVgrow(moneyLabel, Priority.ALWAYS);
        GridPane.setHgrow(moneyLabel, Priority.ALWAYS);
        GridPane.setValignment(moneyLabel, VPos.TOP);
        GridPane.setHalignment(moneyLabel, HPos.LEFT);
        try {
            moneyLabel.setText(Utils.getInfoJson().getString("coins"));
        } catch (JSONException e2) {
            // TODO Auto-generated catch block
            e2.printStackTrace();
        }
        moneyLabel.setId("moneyLabel");

        Label creditLabel = new Label();
        GridPane.setVgrow(creditLabel, Priority.ALWAYS);
        GridPane.setHgrow(creditLabel, Priority.ALWAYS);
        GridPane.setValignment(creditLabel, VPos.TOP);
        GridPane.setHalignment(creditLabel, HPos.LEFT);
        try {
            creditLabel.setText(Utils.getInfoJson().getString("credit"));
        } catch (JSONException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        creditLabel.setId("creditLabel");

        ImageView skinImg = new ImageView(Events.skin);
        GridPane.setVgrow(skinImg, Priority.ALWAYS);
        GridPane.setHgrow(skinImg, Priority.ALWAYS);
        GridPane.setValignment(skinImg, VPos.BOTTOM);
        GridPane.setHalignment(skinImg, HPos.LEFT);
        skinImg.setId("skinImg");

        ImageView voteImg = new ImageView(new Image(OnixCraftLauncher.getResource("personal/voise.png")));
        GridPane.setVgrow(voteImg, Priority.ALWAYS);
        GridPane.setHgrow(voteImg, Priority.ALWAYS);
        GridPane.setValignment(voteImg, VPos.TOP);
        GridPane.setHalignment(voteImg, HPos.LEFT);
        voteImg.setId("voteImg");

        ImageView moneyImg = new ImageView(new Image(OnixCraftLauncher.getResource("personal/monets.png")));
        GridPane.setVgrow(moneyImg, Priority.ALWAYS);
        GridPane.setHgrow(moneyImg, Priority.ALWAYS);
        GridPane.setValignment(moneyImg, VPos.TOP);
        GridPane.setHalignment(moneyImg, HPos.LEFT);
        moneyImg.setId("moneyImg");

        ImageView creditImg = new ImageView(new Image(OnixCraftLauncher.getResource("personal/lk.png")));
        GridPane.setVgrow(creditImg, Priority.ALWAYS);
        GridPane.setHgrow(creditImg, Priority.ALWAYS);
        GridPane.setValignment(creditImg, VPos.TOP);
        GridPane.setHalignment(creditImg, HPos.LEFT);
        creditImg.setId("creditImg");

        Label srvList = new Label("СЕРВЕРА");
        GridPane.setVgrow(srvList, Priority.ALWAYS);
        GridPane.setHgrow(srvList, Priority.ALWAYS);
        GridPane.setValignment(srvList, VPos.TOP);
        GridPane.setHalignment(srvList, HPos.CENTER);
        srvList.setId("srvList");

        initServerTabs();

        final FlowPane content = new FlowPane();
        content.setMaxHeight(380);
        content.setMinHeight(380);
        for (ServerTab tab : tabRegistry) {
            content.getChildren().add(tab.getPane());
        }

        ScrollPane scrollPane = new ScrollPane();
        GridPane.setVgrow(scrollPane, Priority.ALWAYS);
        GridPane.setHgrow(scrollPane, Priority.ALWAYS);
        GridPane.setValignment(scrollPane, VPos.CENTER);
        GridPane.setHalignment(scrollPane, HPos.RIGHT);
        scrollPane.setMinWidth(475);
        scrollPane.setMaxWidth(475);
        scrollPane.setMinHeight(323);
        scrollPane.setMaxHeight(323);
        scrollPane.setVbarPolicy(ScrollBarPolicy.ALWAYS);
        scrollPane.setHbarPolicy(ScrollBarPolicy.NEVER);
        scrollPane.setPannable(true);
        scrollPane.setContent(content);
        scrollPane.setId("scrollpane");
        scrollPane.setVisible(false);

        SliderManager sliderManager = new SliderManager(homePanel, new Image(OnixCraftLauncher.getResource("personal/1.png")),
                new Image(OnixCraftLauncher.getResource("personal/2.png")),
                new Image(OnixCraftLauncher.getResource("personal/1.png")),
                new Image(OnixCraftLauncher.getResource("personal/2.png")));

        srvList.setOnMouseClicked(e-> {
            //scrollPane
            SliderManager.setVisible(false);
            scrollPane.setVisible(true);
        });

        ramButton = new WindowRamButton(homePanel, "ramButtonH", VPos.TOP, HPos.RIGHT);
        exitButton = new WindowExitButton(homePanel, "quitButtonH", VPos.TOP, HPos.RIGHT);
        hideButton = new WindowHideButton(homePanel, this.panelManager, "hideButtonH", VPos.TOP, HPos.RIGHT);
        siteButton = new WindowSiteButton(homePanel, "siteButtonH", VPos.CENTER, HPos.RIGHT, "https://onix-craft.ru/");

        homePanel.getChildren().addAll(skinImg, name,
                rankImg, disconnect, moneyImg, creditImg, voteImg, accountLink, creditLabel, voteLabel, moneyLabel, scrollPane, srvList);
        this.layout.getChildren().add(homePanel);
    }

    public static void refresh() {
        for (ServerTab tab : tabRegistry) {
            tab.getBackground().setImage(new Image(OnixCraftLauncher.getResource("servers/s_" + tab.getBackgroundModName() + "_inact.png")));
        }
    }

    private void initServerTabs() {
        tabRegistry.add(new ServerTab("Classic 1.15", 10, 0, "classic", "purple", 1));
        tabRegistry.add(new ServerTab("Industrial 1.12", 25, 0, "intustrial", "blue", 2));
        tabRegistry.add(new ServerTab("RPG 1.12", 10, 15, "rpg", "blue", 3));
        tabRegistry.add(new ServerTab("Dark 1.7.10", 25, 15, "dark", "green", 4));
        tabRegistry.add(new ServerTab("Galaxy 1.12", 10, 30, "galaxy", "blue", 5));
        tabRegistry.add(new ServerTab("Sky line 1.7.10", 25, 30, "skyline", "green", 6));
        tabRegistry.add(new ServerTab("Mini Games 1.7.10", 10, 45, "minigames", "green", 7));
        tabRegistry.add(new ServerTab("At Wars 1.12.2", 25, 45, "atwar", "blue", 8));
    }

}

