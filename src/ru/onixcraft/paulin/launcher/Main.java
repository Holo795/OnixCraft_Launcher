package ru.onixcraft.paulin.launcher;

import java.io.File;
import java.io.IOException;

import javax.swing.JOptionPane;

import javafx.application.Application;
import re.alwyn974.swinger.Swinger;
import ru.onixcraft.paulin.launcher.ui.PanelManager;
import ru.onixcraft.paulin.launcher.utils.Utils;

public class Main {
	PanelManager panelManager;

	public static void main(String[] args) {
		
		Swinger.setSystemLookNFeel();
        Swinger.setResourcePath("/ru/onixcraft/paulin/launcher/resources/");
        
		Utils.dir.mkdir();
		new File(Utils.dir, "java").mkdir();
				
		try {
			Utils.downloadJava("https://onix-craft.ru/files/java.zip", "java.zip");
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		System.setProperty("java.home", new File(Utils.dir, "java").getAbsolutePath());
		System.setProperty("javafx.home", new File(Utils.dir, "java").getAbsolutePath());

		System.setProperty("java.net.preferIPv4Stack" , "true");


		try {
			Class.forName("javafx.application.Application");
			Application.launch(FxApplication.class, args);
		} catch (ClassNotFoundException e) {
			System.err.println("Javafx not found");
			JOptionPane.showMessageDialog(null, "Обнаружена ошибка Java\n"+ e.getMessage() 
			+ " Not found", "Error Java", JOptionPane.ERROR_MESSAGE);
		}
	}
	
}
