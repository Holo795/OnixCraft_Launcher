package ru.onixcraft.paulin.launcher.events;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;

public class events {
	
	public static Image skin;
		
	public static void Skinimg(String username) {
		
		try {
            URLConnection connection = new URL(/*https://onix-craft.ru/renderSkin."+username+".200*/"https://holo.chaun14.fr/skinview.php?u="+username+"&size=200").openConnection();
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/57.0.2987.98 Safari/537.36");
        	WritableImage image = SwingFXUtils.toFXImage(ImageIO.read(connection.getInputStream()), null);
        	skin = image;
        } catch (IOException ex) {
        	ex.getStackTrace();
        }
	}
	
}
