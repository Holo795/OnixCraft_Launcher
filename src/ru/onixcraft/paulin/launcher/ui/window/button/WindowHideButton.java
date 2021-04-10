package ru.onixcraft.paulin.launcher.ui.window.button;

import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.layout.GridPane;
import ru.onixcraft.paulin.launcher.ui.PanelManager;
import ru.onixcraft.paulin.launcher.ui.window.WindowButton;

public class WindowHideButton extends WindowButton {

    PanelManager panelManager;

    public WindowHideButton(GridPane pane, PanelManager panelManager, String cssId, VPos vPos, HPos hPos) {
        super(cssId, vPos, hPos, pane);
        this.panelManager =  panelManager;
    }

    @Override
    public void onClick() {
        this.panelManager.getStage().setIconified(true);
    }

}
