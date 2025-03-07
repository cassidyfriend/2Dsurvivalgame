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
	public static JFrame frame = new JFrame(theframename);
	static MouseListerner ML = new MouseListerner();
	static Manager M = new Manager();
	static BuildButtons BB = new BuildButtons();
	static Keylistener KL;
	static ScrollingBlocks SB;
	static Player player;
	static GUI gui;
	LoadTextures LT = null;
	static int framex, framey,framesizex,framesizey;
	final static int TARGET_FPS = 80, startingframex = 1280, startingframey = 720;
    final static long OPTIMAL_TIME = 1000000000 / TARGET_FPS;
    static long lastLoopTime = System.nanoTime();
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
		framesizex = frame.getWidth();
		framesizey = frame.getHeight();
		long now = System.nanoTime();
        long updateLength = now - lastLoopTime;
        lastLoopTime = now;
        double delta = updateLength / ((double) OPTIMAL_TIME);
		//g.drawRect(ML.mouseonframex, ML.mouseonframey, 1, 1);
		player.currentframesizex = framesizex;
		player.currentframesizey = framesizey;
		framex = frame.getX();
		framey = frame.getY();
        try {
            long sleepTime = getsleeptime();
			//System.out.println("Sleep Time: " + sleepTime);
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
			//SB.buttonlisten();
		    drawingorder(g);
		    //g.dispose();
		    //bufferStrategy.show();
        
		frame.repaint();
	}
	@SuppressWarnings("static-access")
	void drawingorder(Graphics g) {
		SB.Blocks(g, framesizex, framesizey);
		//print(frame.getWidth());
		player.drawplayer(g);
		new Point2D(g).setcurrentframesize(framesizex, framesizey);
		M.eachframe();
		gui.render(g);
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
		player = new Player(gui);
		new Point2D(startingframex, startingframey, false);
		frame.setVisible(true);
		frame.setTitle(framename);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.createBufferStrategy(2);
		//LT.textures.get(2).lightTextureMap.get(100).getScaledInstance(36, 36, Image.SCALE_DEFAULT)
		frame.setIconImage(LT.textures.get(1).lightTextureMap().get(100).getScaledInstance(36, 36, Image.SCALE_DEFAULT));
		MaI = new MenusAndInterfaces(MenusAndInterfaces.menutypes.MAINMENU, gui, SB);
		player.startingframex = startingframex;
		player.startingframey = startingframey;
	}
	public static long getsleeptime() {
		return (lastLoopTime - System.nanoTime() + OPTIMAL_TIME) / 1000000;
	}
}