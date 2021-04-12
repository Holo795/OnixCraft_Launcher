package ru.onixcraft.paulin.launcher.ui.window.button;

import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.layout.GridPane;
import ru.onixcraft.paulin.launcher.ui.window.WindowButton;
import ru.onixcraft.paulin.launcher.utils.Utils;

public class WindowOpenButton extends WindowButton {

    public WindowOpenButton(GridPane pane, String cssId, VPos vPos, HPos hPos) {
        super(cssId, vPos, hPos, pane);
    }

    @Override
    public void onClick() {
        Utils.openFolder(Utils.dir);
    }
}
