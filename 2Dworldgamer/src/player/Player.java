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
		INGAME,
		HIDE
	}
	
	public static int startingframex, startingframey, currentframesizex, currentframesizey, blockoffsetx = 0, blockoffsety = 0;
	static final int defaultmenuplayersize = 200;
	public static final int defaultgameplayersize = ScrollingBlocks.blocksize + 6;
	static buildworld BW = Manager.BW;
	public static double playerX, playerY = 500, ingameplayerdrawx, ingameplayerdrawy;
	public static boolean lockmovement = true, isFacingLeft = true;
	GUI gui = new GUI();
	public static Location location = Location.HIDE;
	public static Color skincolor = new Color(219, 193, 161), eyecolor = new Color(23, 151, 196), shirtcolor = new Color(196, 30, 23), pantscolor = new Color(0, 0, 0), shoecolor = new Color(52, 52, 52);
	public static BufferedImage playeroverlay = null;
	static Keylistener kl = new Keylistener();
	
	void print(Object o) {
		System.out.println(o);
	}
	
	public Player(GUI gui) {
		this.gui = gui;
		playeroverlay = new BufferedImage(defaultgameplayersize, defaultgameplayersize, BufferedImage.TYPE_INT_ARGB);
	}
	public Player() {}
	
	public void drawplayer(Graphics g) {
		switch(location) {
		case HIDE:
			return;
		case INGAME:
			g.drawImage(constructplayer(),
					fullfloor(ingameplayerdrawx - applydiffloorX(4) + applydiffloorX(blockoffsetx)),
					fullfloor(ingameplayerdrawy - applydiffloorY(6) - applydiffloorY(blockoffsety)),
					applydifx(defaultgameplayersize), applydify(defaultgameplayersize), null);
			break;
		case MENU:
			g.drawImage(constructplayer(),(currentframesizex / 2) - applydifx(defaultmenuplayersize / 2), (currentframesizey / 2) - applydify(defaultmenuplayersize / 2), applydifx(defaultmenuplayersize), applydify(defaultmenuplayersize), null);
			break;
		default:
			return;
		}
	}
	BufferedImage constructplayer() {
		BufferedImage output = new BufferedImage(18, 18, BufferedImage.TYPE_INT_ARGB);
		Graphics g = output.getGraphics();
		g.setColor(shirtcolor);
		g.fillRect(4, 10, 10, 5);
		g.setColor(pantscolor);
		g.fillRect(4, 15, 10, 1);
		g.fillRect(5, 16, 2, 1);
		g.fillRect(10, 16, 2, 1);
		g.setColor(shoecolor);
		g.fillRect(5, 17, 2, 1);
		g.fillRect(10, 17, 2, 1);
		g.setColor(skincolor);
		g.fillRect(5, 5, 8, 1);
		g.fillRect(4, 6, 10, 4);
		g.fillRect(9, 12, 2, 2);
		g.setColor(eyecolor);
		g.fillRect(6, 7, 1, 1);
		g.fillRect(9, 7, 1, 1);
		if(!(playeroverlay == null))
			g.drawImage(playeroverlay, 0, 0, 18, 18, null);
		g.dispose();
		if(!isFacingLeft)
			output = flipImage(output, true);
		return output;
	}
	
	@SuppressWarnings("static-access")
	public void updatepos() {
		double movementamount = 0.1;
		movementamount = movementamount / 0.9;
		if(!lockmovement) {
			if(kl.up) {
				playerY += movementamount;
			}
			if(kl.down) {
				playerY -= movementamount;
			}
			if(kl.left) {
				playerX -= movementamount;
				isFacingLeft = true;
			}
			if(kl.right) {
				playerX += movementamount;
				isFacingLeft = false;
			}
		}
	}
	
	public static BufferedImage flipImage(BufferedImage image, boolean horizontal) {
        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage flippedImage = new BufferedImage(width, height, image.getType());
        Graphics2D g = flippedImage.createGraphics();
        if (horizontal) {
            g.drawImage(image, 0, 0, width, height, width, 0, 0, height, null);
        } else {
            g.drawImage(image, 0, 0, width, height, 0, height, width, 0, null);
        }
        g.dispose();
        return flippedImage;
    }
	
	double getframedifx() {
		return (currentframesizex + 0.0)/(startingframex + 0.0);
	}
	double getframedify() {
		return (currentframesizey + 0.0)/(startingframey + 0.0);
	}
	int applydifx(int input) {
		return (int)Math.round((input + 0.0) * getframedifx());
	}
	int applydifx(double input) {
		return (int)Math.round((input + 0.0) * getframedifx());
	}
	int applydify(int input) {
		return (int)Math.round((input + 0.0) * getframedify());
	}
	int applydify(double input) {
		return (int)Math.round((input + 0.0) * getframedify());
	}
	int applydiffloorX(int input) {
		return fullfloor((input + 0.0) * getframedifx());
	}
	int applydiffloorY(int input) {
		return fullfloor((input + 0.0) * getframedify());
	}
	int fullfloor(double input) {
		return (int)Math.round(Math.floor(input));
	}
	double roundto(double input, int places) {
		return Math.round(input * Math.pow(10, places)) / Math.pow(10, places);
	}
}