package engine;


import generator.buildworld;
import generator.spawnstructures;
import datareader.datareadermain;
import update.Update;
import loadFiles.LoadTextures;
import start.main;
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
	@SuppressWarnings("static-access")
	public void Keylistener() {
		TD.frame.addKeyListener(new KeyAdapter() {
		    
	    	public void keyPressed(KeyEvent e) {
	      	int keyCode = e.getKeyCode();
	      	if (keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_W) {
	        	//System.out.println("Up Arrrow-Key is pressed!");
	        	//y = y + 4;
	        	//if(y > 18) {
	        	//    y = 0;
	      	// 	 checkblocky++;
	      	//  }
	        	up = true;
	      	}
	     	 
	      	if (keyCode == KeyEvent.VK_DOWN || keyCode == KeyEvent.VK_S) {
	        	//System.out.println("Down Arrrow-Key is pressed!");
	        	//y = y + -4;
	       	// if(y < -18) {
	       	//     y = 0;
	       	//     checkblocky--;
	       	// }
	        	down = true;
	     	 
	      	}
	     	 
	      	if (keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_A) {
	        	//System.out.println("Left Arrrow-Key is pressed!");
	      	//  x = x + 6;
	      	//  if(x > 19) {
	        	//    x = 2;
	        	//    checkblockx = checkblockx - 1000;
	        	//}
	        	left = true;
	      	}
	     	 
	      	if (keyCode == KeyEvent.VK_RIGHT || keyCode == KeyEvent.VK_D) {
	      	//System.out.println("Right Arrrow-Key is pressed!");
	     	// x = x + -6;
	      	//if(x < -19) {
	      	//    x = -2;
	     		 //checkblockx = checkblockx + 1000;
	    	//  }
	     	right = true;
	      	}
	     	 
	      	if (keyCode == KeyEvent.VK_SPACE) {
	   		   //blocks.set(checkblockx + 34000 + checkblocky - 22,1);
	   		   space = true;
	          	}
	      	//System.out.println(e.getKeyCode() - 48);
	      	if(e.getKeyCode() - 48 > 0 && e.getKeyCode() - 48 < 10) {
	      		CurnentHotKey = e.getKeyCode() - 48;
	      	}
	      	if(e.getKeyCode() - 48 == 0) {
	      		CurnentHotKey = 0;
	      	}
	    	}
	    	public void keyReleased(KeyEvent e) {
	   		 int keyCode = e.getKeyCode();
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
