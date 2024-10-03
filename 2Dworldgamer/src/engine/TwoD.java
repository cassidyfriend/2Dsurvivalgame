package engine;


import generator.*;
import physics.Point2D;
import datareader.*;
import update.*;
import Files.*;
import engine.*;
import engine.GUI.textbutton;
import player.*;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Map;


@SuppressWarnings({ "serial", "unused" })
public class TwoD extends JPanel {
	static String theframename;
	static JFrame frame = new JFrame(theframename);
	static MouseListerner ML = new MouseListerner();
	static Manager M = new Manager();
	static BuildButtons BB = new BuildButtons();
	static Keylistener KL;
	static ScrollingBlocks SB;
	static Player player = new Player(null);
	static GUI gui;
	LoadTextures LT = null;
	static int framex, framey,framesizex,framesizey;
	final static int TARGET_FPS = 80, startingframex = 1280, startingframey = 720;
    final long OPTIMAL_TIME = 1000000000 / TARGET_FPS;
    long lastLoopTime = System.nanoTime();
    static ArrayList<Map<Integer, BufferedImage>> textures;
    static MenusAndInterfaces MaI;
    static BufferStrategy bufferStrategy;
    static int bufferoffset = 0;
    void print(Object o) {
    	System.out.println(o);
    }
    
    TwoD(){
    	bufferStrategy = frame.getBufferStrategy();
    }
    public TwoD(int ags){}
    
	@SuppressWarnings("static-access")
	@Override
	public void paint(Graphics g) {
		long now = System.nanoTime();
        long updateLength = now - lastLoopTime;
        lastLoopTime = now;
        double delta = updateLength / ((double) OPTIMAL_TIME);
		//g.drawRect(ML.mouseonframex, ML.mouseonframey, 1, 1);
		player.framex = framesizex;
		player.framey = framesizey;
		framex = frame.getX();
		framey = frame.getY();
		framesizex = frame.getWidth();
		framesizey = frame.getHeight();
        try {
            long sleepTime = (lastLoopTime - System.nanoTime() + OPTIMAL_TIME) / 1000000;
            if (sleepTime > 0) {
                Thread.sleep(sleepTime);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        

		
			if (bufferStrategy == null) {
				//frame.createBufferStrategy(3);
				//bufferStrategy = frame.getBufferStrategy();
			}
		    //g = bufferStrategy.getDrawGraphics();
			SB.buttonlisten();
		    drawingorder(g);
		    //g.dispose();
		    //bufferStrategy.show();
        
		frame.repaint();
	}
	@SuppressWarnings("static-access")
	void drawingorder(Graphics g) {
		SB.Blocks(g, framesizex, framesizey);
		M.eachframe();
		player.drawplayer(g);
		if(MaI != null)
			MaI.updatemenus();
		new Point2D(g).setcurrentframesize(framesizex, framesizey);
		gui.update(frame.getWidth(), frame.getHeight(), KL.lastkeypress);
		gui.render(g);
		//print(ML.button);
	}
	@SuppressWarnings("static-access")
	public void startframes(String framename, LoadTextures LT) {
		this.LT = LT;
		SB = new ScrollingBlocks(LT, startingframex, startingframey);
		theframename = framename;
		//ML.MouseListerner();
		KL = new Keylistener();
		frame.add(new TwoD());
		frame.setSize(startingframex, startingframey);
		gui = new GUI(startingframex, startingframey, LT);
		new Point2D(startingframex, startingframey, false);
		frame.setVisible(true);
		frame.setTitle(framename);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.createBufferStrategy(2);
		frame.setIconImage(LT.textures.get(2).get(100).getScaledInstance(36, 36, Image.SCALE_DEFAULT));
		MaI = new MenusAndInterfaces(MenusAndInterfaces.menutypes.MAINMENU, gui, SB);
	}
}