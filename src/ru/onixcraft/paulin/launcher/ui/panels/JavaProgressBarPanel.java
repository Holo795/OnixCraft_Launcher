package ru.onixcraft.paulin.launcher.ui.panels;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

import re.alwyn974.swinger.Swinger;
import re.alwyn974.swinger.textured.STexturedProgressBar;

@SuppressWarnings("serial")
public class JavaProgressBarPanel extends JPanel{
	
    private Image background = Swinger.getResource("download/download_page.png");
    
    private static STexturedProgressBar progressBar = new STexturedProgressBar(Swinger.getResource("download/download_tab_1.png"), Swinger.getResource("download/download_tab.png"));
	
	public JavaProgressBarPanel() {
		this.setLayout(null);
		
		this.setBackground(Swinger.TRANSPARENT);
		
		progressBar.setBounds(42, 88, 443, 60);
        progressBar.setVisible(true);
		this.add(progressBar);				
	}
	
    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        g.drawImage(background, 0, 0, this.getWidth(), this.getHeight(), this);
    }
    
    public static STexturedProgressBar getProgressBar() {
    	return progressBar;
    }

}
