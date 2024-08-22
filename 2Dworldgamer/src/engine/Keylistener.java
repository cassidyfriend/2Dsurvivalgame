package engine;


import generator.buildworld;
import generator.spawnstructures;
import datareader.datareadermain;
import update.Update;
import Files.LoadTextures;
import engine.TwoD;

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
public class Keylistener {
	TwoD TD = new TwoD();
	public static boolean up;
	public static boolean down;
	public static boolean left;
	public static boolean right;
	public static boolean space;
	public static int CurnentHotKey;
	public static int lastkeypress = -1;
	@SuppressWarnings("static-access")
	public Keylistener() {
		TD.frame.addKeyListener(new KeyAdapter() {
	    	public void keyPressed(KeyEvent e) {
	    		int keyCode = e.getKeyCode();
	    		//System.out.println((char)keyCode);
	    		lastkeypress = keyCode;
	    		if (keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_W) {
	    			up = true;
	    		}
	    		if (keyCode == KeyEvent.VK_DOWN || keyCode == KeyEvent.VK_S) {
	    			down = true;
	    		}
	    		if (keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_A) {
	    			left = true;
	    		}
	    		if (keyCode == KeyEvent.VK_RIGHT || keyCode == KeyEvent.VK_D) {
	    			right = true;
	    		}
	    		if (keyCode == KeyEvent.VK_SPACE) {
	    			space = true;
	    		}
	    		if(e.getKeyCode() - 48 > 0 && e.getKeyCode() - 48 < 10) {
	      		CurnentHotKey = e.getKeyCode() - 48;
	    		}
	    		if(e.getKeyCode() - 48 == 0) {
	    			CurnentHotKey = 0;
	    		}
	    	}
	    	public void keyReleased(KeyEvent e) {
	    		int keyCode = e.getKeyCode();
	    		lastkeypress = -1;
	    		if (keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_W) {
	    			up = false;
	    		}
	    		if (keyCode == KeyEvent.VK_DOWN || keyCode == KeyEvent.VK_S) {
	    			down = false;
	    		}
	    		if (keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_A) {
	    			left = false;
	    		}
	    		if (keyCode == KeyEvent.VK_RIGHT || keyCode == KeyEvent.VK_D) {
	    			right = false;
	    		}
	    		if (keyCode == KeyEvent.VK_SPACE) {
	    			space = false;
	   		 	}
	    	}
	  	});
	}
}
