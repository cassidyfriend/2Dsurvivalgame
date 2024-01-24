package engine;


import generator.buildworld;
import generator.spawnstructures;
import datareader.datareadermain;
import update.Update;
import loadFiles.LoadTextures;
import start.main;
import engine.*;
import datawriter.DataWriter;


import javax.swing.*;
import java.io.*;
import java.net.URL;
import java.util.*;
import java.awt.*;
import java.awt.image.*;
import javax.imageio.*;
import java.awt.event.*;
import javax.sound.sampled.*;


@SuppressWarnings({ "serial", "unused" })
public class TwoD extends JPanel {
	static String theframename;
	static JFrame frame = new JFrame(theframename);
	static MouseListerner ML = new MouseListerner();
	static Manager M = new Manager();
	static BuildButtons BB = new BuildButtons();
	static DataWriter DW = new DataWriter();
	static Keylistener KL = new Keylistener();
	static ScrollingBlocks SB = new ScrollingBlocks();
	static LoadTextures LT = new LoadTextures();
	static int framex, framey,framesizex,framesizey;
	@SuppressWarnings("static-access")
	@Override
	public void paint(Graphics g) {
		//Graphics2D g2d = (Graphics2D) g;
		//frame.WIDTH
		SB.Blocks(g, framesizex, framesizey);
		g.setColor(Color.white);
		//g.fillRect(-5, -5, frame.getWidth(), frame.getHeight());
		M.eachframe();
		BB.buttoncount = 1;
		BB.posx.set(0,100);
		BB.posy.set(0,100);
		BB.sizex.set(0,300);
		BB.sizey.set(0,100);
		BB.buttontext.set(0, "game");
		BB.Buttons(g);
		//g.drawImage(LT.textures.get(1), 100, 100, null);
		g.drawRect(ML.mouseonframex, ML.mouseonframey, 1, 1);
		framex = frame.getX();
		framey = frame.getY();
		framesizex = frame.getWidth();
		framesizey = frame.getHeight();
		
		repaint();
	}
	public static void startframes(String framename) {
		theframename = framename;
		ML.MouseListerner();
		KL.Keylistener();
		frame.add(new TwoD());
		DW.DataWriter();
		frame.setSize(1080, 780);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}