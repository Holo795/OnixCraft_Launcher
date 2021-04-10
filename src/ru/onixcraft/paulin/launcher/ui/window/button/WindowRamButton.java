package ru.onixcraft.paulin.launcher.ui.window.button;

import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.layout.GridPane;
import ru.onixcraft.paulin.launcher.ui.panels.OptionPanel;
import ru.onixcraft.paulin.launcher.ui.window.WindowButton;

public class WindowRamButton extends WindowButton {


    public WindowRamButton(GridPane pane, String cssId, VPos vPos, HPos hPos) {
        super(cssId, vPos, hPos, pane);
    }

    @Override
    public void onClick() {
        new OptionPanel();
    }

}
