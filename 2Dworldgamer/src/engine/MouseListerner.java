package engine;


import generator.buildworld;
import generator.spawnstructures;
import datareader.datareadermain;
import update.Update;
import loadFiles.LoadTextures;
import start.main;
import engine.Keylistener;
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
public class MouseListerner {
	static TwoD TD = new TwoD();
	static Point p = MouseInfo.getPointerInfo().getLocation();
	static BuildButtons BB = new BuildButtons();
  	public static int mousex = p.x;
  	public static int mousey = p.y;
  	static int mouseonframex = 0;
  	static int mouseonframey = 0;
	public static int button = 0;
	@SuppressWarnings("static-access")
	public void MouseListerner() {
		TD.frame.addMouseListener(new MouseListener() {
        	public void mousePressed(MouseEvent mouseinput) { }
        	public void mouseReleased(MouseEvent mouseinput) { }
        	public void mouseEntered(MouseEvent mouseinput) { }
        	public void mouseExited(MouseEvent mouseinput) { }
        	public void mouseClicked(MouseEvent mouseinput) {
          	if(mouseinput.getButton() == MouseEvent.BUTTON1) {
          		button = 1;
          		if(BB.onbutton) {
          			System.out.println("one has be pressed");
          		}
          	} else
          	if(mouseinput.getButton() == MouseEvent.BUTTON2) {
          		button = 2;
          		if(BB.onbutton) {
          			System.out.println("two has be pressed");
          		}
          		//System.out.println("two has be pressed");
          	} else
          	if(mouseinput.getButton() == MouseEvent.BUTTON3) {
          		button = 3;
          		if(BB.onbutton) {
          			System.out.println("three has be pressed");
          		}
          		//System.out.println("three has be pressed");
          	} else {
          		button = 0;
          	}
        	}
        	//Point p = MouseInfo.getPointerInfo().getLocation();
       	 
       	 
    	});
	}
	@SuppressWarnings({ "static-access", "deprecation" })
	public static void onupdate() {
		p = MouseInfo.getPointerInfo().getLocation();
	  	mousex = p.x;
	  	mousey = p.y;
	  	mouseonframex = (mousex - TD.frame.location().x) - 8;
      	mouseonframey = (mousey - TD.frame.location().y) - 31;
	}
}
