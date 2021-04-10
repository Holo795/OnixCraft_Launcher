package ru.onixcraft.paulin.launcher.ui.panels.include;

import javax.swing.JFrame;

import re.alwyn974.swinger.Swinger;
import re.alwyn974.swinger.util.WindowMover;
import ru.onixcraft.paulin.launcher.ui.panels.JavaProgressBarPanel;

@SuppressWarnings("serial")
public class JavaProgressBar extends JFrame {
	
	private static JavaProgressBar javaProgressBar;
	private JavaProgressBarPanel barPanel;
    
	public static void init() {
		javaProgressBar = new JavaProgressBar();
	}
	
    public JavaProgressBar()
    {
    	
        this.setTitle("OnixCraft -- Download");
        this.setSize(526, 218);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setIconImage(Swinger.getResource("download/icon.png"));
        this.setUndecorated(true);
        this.setBackground(Swinger.TRANSPARENT);

        this.setContentPane(barPanel = new JavaProgressBarPanel());

        WindowMover mover = new WindowMover(this);
        this.addMouseListener(mover);
        this.addMouseMotionListener(mover);  
        
        this.setVisible(true);
        
    }


    public static JavaProgressBar getJavaProgressBar()
    {
        return javaProgressBar;
    }

    public JavaProgressBarPanel getJavaProgressBarPanel()
    {
        return this.barPanel;
    }
    
}
