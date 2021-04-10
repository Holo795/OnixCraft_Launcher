package ru.onixcraft.paulin.launcher.ui.panel;

import javafx.scene.layout.GridPane;
import ru.onixcraft.paulin.launcher.ui.PanelManager;

public interface IPanel {

	void init(PanelManager panelManager);
	GridPane getLayout();
	void onShow();
	
}
