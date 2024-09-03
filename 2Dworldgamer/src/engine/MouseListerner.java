package engine;


import generator.buildworld;
import generator.spawnstructures;
import datareader.datareadermain;
import update.Update;
import Files.LoadTextures;
import engine.Keylistener;
import engine.TwoD;

import javax.swing.*;
import java.io.*;
import java.net.URL;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
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
	public static int mouseclickstimeout = 0;
	private ScheduledExecutorService scheduler;

    @SuppressWarnings("static-access")
	public MouseListerner() {
        scheduler = Executors.newSingleThreadScheduledExecutor();
        TD.frame.addMouseListener(new MouseListener() {
        	//System.out.println(button);
            public void mousePressed(MouseEvent mouseinput) {
                if (mouseinput.getButton() == MouseEvent.BUTTON1) {
                    button = 1;
                } else if (mouseinput.getButton() == MouseEvent.BUTTON2) {
                    button = 2;
                } else if (mouseinput.getButton() == MouseEvent.BUTTON3) {
                    button = 3;
                } else {
                    button = 0;
                }
                scheduler.schedule(() -> {
                    button = 0;
                }, mouseclickstimeout, TimeUnit.MILLISECONDS);
            }
            public void mouseReleased(MouseEvent mouseinput) {
                button = 0;
            }
            public void mouseEntered(MouseEvent mouseinput){}
            public void mouseExited(MouseEvent mouseinput){}
            public void mouseClicked(MouseEvent mouseinput){}
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
