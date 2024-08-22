package engine;

import generator.*;
import datareader.*;
import update.*;

import java.awt.Graphics;
import java.util.ArrayList;

import Files.*;
import engine.*;
import engine.GUI.textbutton;
import engine.GUI.textstoredininput;
import player.*;

@SuppressWarnings("unused")
public class MenusAndInterfaces {
	GUI gui;
	static ArrayList<menutypes> menutypelist = new ArrayList<menutypes>();
	static ScrollingBlocks SB;
	buildworld BW = Manager.BW;
	static TwoD TD;
	enum menutypes{
		MAINMENU,
		NEWWORLD
	}
	
	static void print(Object o){
		System.out.println(o);
	}
	
	@SuppressWarnings("static-access")
	public MenusAndInterfaces(menutypes toadd, GUI gui, ScrollingBlocks SB) {
		menutypelist.add(toadd);
		this.gui = gui;
		this.SB = SB;
	}
	public MenusAndInterfaces() {}
	
	@SuppressWarnings("static-access")
	public void updatemenus() {
		for(menutypes current : menutypelist) {
			switch(current) {
			case MAINMENU:
				gui.new textbutton("Single Player", 520, 280, 240, 80, true);
				gui.new textbutton("Settings", 520, 380, 240, 80, true);
				break;
			case NEWWORLD:
				gui.new textbutton("Create World", 520, 400, 240, 80, true);
				gui.new fillintextbox(505, 280, 280, 50, "seed:");
				break;
				
			}
		}
		if(gui.getbuttonclickedID("Single Player") == 1) {
			menutypelist.remove(menutypes.MAINMENU);
			menutypelist.add(menutypes.NEWWORLD);
		}
		if(gui.getbuttonclickedID("Create World") == 1) {
			if(gui.stringinnputs.contains(gui.new textstoredininput("seed:", false))) {
				BW.seed = Math.abs(gui.stringinnputs.get(gui.stringinnputs.indexOf(gui.new textstoredininput("seed:", false))).input.hashCode());
				if(BW.seed > 1) {
					BW.updatenoise();
				}
				else
					BW.updatenoise();
			}
			else
				BW.updatenoise();
			SB.updateY();
			menutypelist.remove(menutypes.NEWWORLD);
			SB.lockmovement = false;
			SB.isInMenu = false;
		}
	}
}