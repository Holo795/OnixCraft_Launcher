package ru.onixcraft.paulin.launcher.ui.window.button;

import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.layout.GridPane;
import ru.onixcraft.paulin.launcher.ui.panels.OptionPanel;
import ru.onixcraft.paulin.launcher.ui.window.WindowButton;
import ru.onixcraft.paulin.launcher.utils.Utils;

public class WindowSiteButton extends WindowButton {

    private final String url;

    public WindowSiteButton(GridPane pane, String cssId, VPos vPos, HPos hPos, String url) {
        super(cssId, vPos, hPos, pane);
        this.url = url;
    }

    @Override
    public void onClick() {
        Utils.openUrl(url);
    }

}
