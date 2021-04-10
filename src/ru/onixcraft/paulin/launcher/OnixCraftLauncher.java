package ru.onixcraft.paulin.launcher;

import java.awt.HeadlessException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import javax.swing.JOptionPane;

import org.json.JSONException;

import javafx.stage.Stage;
import ru.onixcraft.paulin.launcher.events.events;
import ru.onixcraft.paulin.launcher.ui.PanelManager;
import ru.onixcraft.paulin.launcher.ui.panels.HomePanel;
import ru.onixcraft.paulin.launcher.ui.panels.PanelLogin;
import ru.onixcraft.paulin.launcher.utils.Utils;

public class OnixCraftLauncher {

	private PanelManager panelManager;
	private static String path = "/ru/onixcraft/paulin/launcher/resources/";
	
	public void init(Stage stage) {
				
		this.panelManager = new PanelManager(this, stage);
		this.panelManager.init();
		if(Utils.config.get("save").contains("true")) {
			String username = Utils.config.get("username");
			String token = Utils.config.get("token");
			try {
				if(Utils.reAuth(username, token)) {
					events.Skinimg(username);
					if(Utils.getInfo(username).getString("active").contains("1")) {
						Utils.config.put("username", username);
						Utils.config.put("token", Utils.getInfo(username).getString("token"));
						this.panelManager.showPanel(new HomePanel());
					} else {
						JOptionPane.showMessageDialog(null, "Account inactive");
					}
				} else {
					this.panelManager.showPanel(new PanelLogin());
				}
			} catch (IOException | NoSuchAlgorithmException | HeadlessException | JSONException e1) {
				this.panelManager.showPanel(new PanelLogin());
				e1.printStackTrace();
			}
		} else {
		this.panelManager.showPanel(new PanelLogin());
		}
	}
	
	public void setResourcePath(String path) {
		OnixCraftLauncher.path = path;
	}
	
	public static String getResource(String img) {
		return path+img;
	}
	
	public static String getResourcePath() {
		return path;
	}
	
}
