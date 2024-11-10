package engine;


import generator.buildworld;
import generator.spawnstructures;
import player.Player;
import datareader.datareadermain;
import update.Update;
import Files.*;
import engine.TwoD;
import gameMechanics.Tags;
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
	static Player player = new Player();
	public static Map<String,String> overrightblocks = new HashMap<String,String>();
	public static Map<String,String> playeroverrightblocks = new HashMap<String,String>();
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
		new LoadBlockDefaultData(LT);
		new Tags(LT);
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
			String currentworldpos = Integer.toString((int)(player.playerX<-0.1?player.playerX-1.0:player.playerX)) + " " + Integer.toString((int)(player.playerY<-0.1?player.playerY-1.0:player.playerY));
			playeroverrightblocks.put(currentworldpos,"stone iron ore");
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
		SB.updaterenderingpos();
		player.updatepos();
	}
	public static void newworld() {
		BW.gencolomes();
	}
}
