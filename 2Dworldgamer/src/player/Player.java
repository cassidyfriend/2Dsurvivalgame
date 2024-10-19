package player;

import generator.*;
import generator.*;
import datareader.*;
import update.*;
import Files.*;
import engine.*;
import engine.GUI.textbutton;

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
	
	public static int drawplayerx, drawplayery, framex, framey, smalldrawx, smalldrawy, blocksizex, blocksizey;
	GUI gui = new GUI();
	
	void print(Object o) {
		System.out.println(o);
	}
	
	public Player(GUI gui) {
		this.gui = gui;
	}
	public Player() {}
	
	public static void drawplayer(Graphics g) {
		g.fillRect(drawplayerx,drawplayery,Math.round(framex / 70),Math.round(framey / 40));
		//g.fillRect(drawplayerx - blocksizex,drawplayery,Math.round(framex / 70),Math.round(framey / 40));
		g.setColor(new Color(100,0,0));
		g.fillRect(drawplayerx - smalldrawx,drawplayery - smalldrawy,Math.round(framex / 110),Math.round(framey / 65));
	}
	BufferedImage constructplayer() {
		BufferedImage output = new BufferedImage(18, 18, BufferedImage.TYPE_INT_ARGB);
		return output;
	}
}