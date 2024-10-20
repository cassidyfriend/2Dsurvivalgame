package engine;

import generator.*;
import datareader.*;
import update.*;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import Files.*;
import engine.*;
import engine.GUI.textbutton;
import engine.GUI.textstoredininput;
import player.*;

@SuppressWarnings("unused")
public class MenusAndInterfaces {
	GUI gui;
	static ArrayList<menutypes> menutypelist = new ArrayList<menutypes>();
	static ArrayList<Map<Integer, BufferedImage>> textures;
	static ScrollingBlocks SB;
	static buildworld BW = Manager.BW;
	static SpawnFeatures SF = new SpawnFeatures();
	static TwoD TD;
	static Player player = new Player();
	LoadTextures LT;
	static double playerscale = 50;
	enum menutypes{
		MAINMENU,
		NEWWORLD,
		COSTUME
	}
	
	static void print(Object o){
		System.out.println(o);
	}
	
	@SuppressWarnings("static-access")
	public MenusAndInterfaces(menutypes toadd, GUI gui, ScrollingBlocks SB) {
		menutypelist.add(toadd);
		textures = LoadTextures.textures;
		this.gui = gui;
		this.SB = SB;
	}
	public MenusAndInterfaces() {}
	
	@SuppressWarnings("static-access")
	public void updatemenus() {
		for(menutypes current : menutypelist) {
			switch(current) {
			case MAINMENU:
				gui.new textbutton("Single Player", 375, 280, 240, 80, true);
				gui.new textbutton("Settings", 375, 380, 240, 80, true);
				gui.new textbutton("Multiplayer", 645, 280, 240, 80, true);
				gui.new textbutton("Costume Room", 645, 380, 240, 80, true);
				gui.new textbutton("Exit", 520, 480, 240, 80, true);
				Map<Integer, BufferedImage> lightmap = textures.get(12);
				//lightmap.get(100);
				gui.new targetbox("test", 30, 30, 245, 123, 50, 50, false, lightmap.get(100));
				//gui.new slidingbar("test", lightmap.get(100), 30, 30, 245, 123, 0, 50, 10);
				//print(gui.getslideramount("test"));
				break;
			case NEWWORLD:
				gui.new textbutton("Create World", 520, 400, 240, 80, true);
				gui.new fillintextbox(505, 280, 280, 50, "seed:");
				break;
			case COSTUME:
				gui.new textbutton("return to main menu", 520, 570, 240, 80, true);
				break;
			default:
				break;
			}
		}
		if(gui.getbuttonclickedID("Single Player") == 1) {
			menutypelist.remove(menutypes.MAINMENU);
			menutypelist.add(menutypes.NEWWORLD);
		}
		if(gui.getbuttonclickedID("Settings") == 1) {
			menutypelist.remove(menutypes.MAINMENU);
		}
		if(gui.getbuttonclickedID("Multiplayer") == 1) {
			menutypelist.remove(menutypes.MAINMENU);
		}
		if(gui.getbuttonclickedID("Exit") == 1) {
			System.exit(0);
		}
		if(gui.getbuttonclickedID("test") == 1) {
			print(Arrays.toString(gui.gettargetboxclicked("test")));
		}
		if(gui.getbuttonclickedID("return to main menu") == 1) {
			menutypelist.remove(menutypes.COSTUME);
			menutypelist.add(menutypes.MAINMENU);
		}
		if(gui.getbuttonclickedID("Create World") == 1) {
			if(gui.stringinnputs.contains(gui.new textstoredininput("seed:", false))) {
				BW.seed = Math.abs(gui.stringinnputs.get(gui.stringinnputs.indexOf(gui.new textstoredininput("seed:", false))).input.hashCode())/100000;
				print(BW.seed);
				if(BW.seed > 1) {
					BW.updatenoise(Loaddimensions.dimensionsdata.getString("starting dimension"));
					SF.updatenoise(Loaddimensions.dimensionsdata.getString("starting dimension"), BW.seed);
				}
				else {
					BW.updatenoise(Loaddimensions.dimensionsdata.getString("starting dimension"));
					SF.updatenoise(Loaddimensions.dimensionsdata.getString("starting dimension"), BW.seed);
				}
			}
			else {
				BW.updatenoise(Loaddimensions.dimensionsdata.getString("starting dimension"));
				SF.updatenoise(Loaddimensions.dimensionsdata.getString("starting dimension"), BW.seed);
			}
			SB.updateY();
			menutypelist.remove(menutypes.NEWWORLD);
			SB.lockmovement = false;
			SB.isInMenu = false;
		}
		if(gui.getbuttonclickedID("Costume Room") == 1) {
			menutypelist.remove(menutypes.MAINMENU);
			menutypelist.add(menutypes.COSTUME);
		}
	}
}