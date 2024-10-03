package player;

import generator.*;
import generator.*;
import datareader.*;
import update.*;
import Files.*;
import engine.*;

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
public class Player {
	
	public enum Location{
		MENU,
		INGAME
	}
	
	public static int drawplayerx, drawplayery,framex,framey,smalldrawx,smalldrawy,blocksizex,blocksizey;
	
	void print(Object o) {
		System.out.println(o);
	}
	
	public Player() {}
	public Player(Location type) {
		if(type == null) {
			return;
		}
		if(type == Location.MENU) {
			//print("test");
			new ReadAndWritePlayerDesign();
		}
	}
	
	public static void drawplayer(Graphics g) {
		g.fillRect(drawplayerx,drawplayery,Math.round(framex / 70),Math.round(framey / 40));
		//g.fillRect(drawplayerx - blocksizex,drawplayery,Math.round(framex / 70),Math.round(framey / 40));
		g.setColor(new Color(100,0,0));
		g.fillRect(drawplayerx - smalldrawx,drawplayery - smalldrawy,Math.round(framex / 110),Math.round(framey / 65));
	}
}