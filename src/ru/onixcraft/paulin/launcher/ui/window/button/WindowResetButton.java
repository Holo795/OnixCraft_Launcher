package ru.onixcraft.paulin.launcher.ui.window.button;

import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.layout.GridPane;
import ru.onixcraft.paulin.launcher.ui.window.WindowButton;
import ru.onixcraft.paulin.launcher.utils.Utils;

import java.io.File;

public class WindowResetButton extends WindowButton {
    public WindowResetButton( GridPane pane, String cssId, VPos vPos, HPos hPos) {
        super(cssId, vPos, hPos, pane);
    }

    @Override
    public void onClick() {
        if(!deleteDirectory(Utils.dir)) System.out.println("File can't be deleted");
        Utils.dir.mkdirs();
        Utils.config.load();
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
