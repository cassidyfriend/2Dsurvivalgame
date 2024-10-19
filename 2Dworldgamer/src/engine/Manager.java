package engine;


import generator.buildworld;
import generator.spawnstructures;
import datareader.datareadermain;
import update.Update;
import Files.*;
import engine.TwoD;
import engine.MouseListerner;
import engine.BuildButtons;
import javax.swing.*;
import java.io.*;
import java.net.URL;
import java.util.*;
import java.awt.*;
import java.awt.image.*;
import javax.imageio.*;
import java.awt.event.*;
import javax.sound.sampled.*;


@SuppressWarnings("unused")
public class Manager {
	public static TwoD TD = new TwoD();
	static LoadTextures LT;
	static MouseListerner ML = new MouseListerner();
	static BuildButtons BB = new BuildButtons();
	public static buildworld BW = new buildworld();
	static MenusAndInterfaces MaI;
	static Keylistener KL;
	static GUI gui;
	static ScrollingBlocks SB = new ScrollingBlocks();
	public static Map<String,Short> overrightblocks = new HashMap<String,Short>();
	public static Map<String,Short> playeroverrightblocks = new HashMap<String,Short>();
	static void print(Object o) {
		System.out.println(o);
	}
	@SuppressWarnings("static-access")
	public static void onstartup() {
		try {
			LT = new LoadTextures();
		} catch (IOException e) {
			e.printStackTrace();
		}
		new Loaddimensions();
		//new loadbiomedata();
		//new LoadSettings();
		new LoadFeatures();
		//TD.loadframe();
		newworld();
		TD.startframes("game name", LT);
		KL = new Keylistener();
		gui = new GUI(TD.startingframex, TD.startingframey, LT);
	}
	@SuppressWarnings("static-access")
	public static void eachframe() {
		if(Keylistener.space) {
			System.out.println("space");
			try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
			String currentworldpos = Integer.toString((int)ScrollingBlocks.x + 64) + " " + Integer.toString((int)ScrollingBlocks.y - 37);
			playeroverrightblocks.put(currentworldpos,(short) 38);
		}
		ML.onupdate();
		if(gui == null)
			gui = new GUI(TD.startingframex, TD.startingframey, LT);
		if(gui != null)
			gui.update(TD.frame.getWidth(), TD.frame.getHeight(), KL.lastkeypress);
		if(MaI == null)
			MaI = new MenusAndInterfaces(MenusAndInterfaces.menutypes.MAINMENU, gui, SB);
		if(MaI != null && gui != null)
			MaI.updatemenus();
	}
	public static void newworld() {
		BW.gencolomes();
	}
}
