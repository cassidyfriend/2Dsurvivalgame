package engine;

import generator.buildworld;
import generator.spawnstructures;
import datareader.datareadermain;
import update.Update;
import Files.LoadTextures;
import engine.Keylistener;

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
public class BuildButtons {
	static TwoD TD = new TwoD();
	static MouseListerner ML = new MouseListerner();
	static int buttoncount = 0;
	public static ArrayList<Integer> posx = new ArrayList<Integer>();
	public static ArrayList<Integer> posy = new ArrayList<Integer>();
	public static ArrayList<Integer> sizex = new ArrayList<Integer>();
	public static ArrayList<Integer> sizey = new ArrayList<Integer>();
	public static ArrayList<String> buttontext = new ArrayList<String>();
	public static boolean onbutton = false;
	@SuppressWarnings("static-access")
	public void Buttons(Graphics g) {
		for (int i = 0; i < buttoncount; i++) {
			if(ML.mouseonframex > posx.get(i) && ML.mouseonframey > posy.get(i) && ML.mouseonframex < posx.get(i) + sizex.get(i) && ML.mouseonframey < posy.get(i) + sizey.get(i)) {
				g.setColor(new Color(150,150,150));
				onbutton = true;
			} else {
				g.setColor(new Color(200,200,200));
				onbutton = false;
			}
			g.fillRect(posx.get(i), posy.get(i), sizex.get(i), sizey.get(i));
			g.setColor(new Color(0,0,0));
			g.setFont(new Font("TimesRoman", Font.PLAIN, 10));
			g.drawString(buttontext.get(i), posx.get(i) + (sizex.get(i) / 2), posy.get(i) + (sizey.get(i) / 2));
		}
	}
	public void makebuttonlists(int amount) {
		for (int i = 0; i < amount; i++) {
			posx.add(0);
			posy.add(0);
			sizex.add(0);
			sizey.add(0);
			buttontext.add(null);
		}
	}
}
