package engine;

import generator.*;
import physics.Point2D;
import datareader.datareadermain;
import update.Update;
import Files.LoadSettings;
import Files.LoadTextures;
import Files.loadbiomedata;
import engine.*;
import datawriter.DataWriter;
import player.*;

import org.json.JSONObject;

import javax.swing.*;
import java.io.*;
import java.math.BigInteger;
import java.net.URL;
import java.util.*;
import java.awt.*;
import java.awt.image.*;
import javax.imageio.*;
import java.awt.event.*;
import javax.sound.sampled.*;

@SuppressWarnings("unused")
public class ScrollingBlocks {
	
	record overlayblock(int blockID, Point2D location){}
	
	LoadTextures LT;
	public static buildworld BW = Manager.BW;
	static Keylistener kl = new Keylistener();
	static Player player = new Player(null);
	static LoadSettings settings = new LoadSettings();
	static SpawnFeatures SF = new SpawnFeatures();
	buildworld.column currentculum = null;
	loadbiomedata biomedata = new loadbiomedata();
	public static int startingframex = 0, startingframey = 0, currentframex = 0, currentframey = 0;
	public static final int blocksize = 18;
	public static double x, y;
	public static boolean isInMenu = true, lockmovement = true;
	public static ArrayList<overlayblock> overlayblocks = new ArrayList<overlayblock>();
	static int darknesslevel = 0;
	@SuppressWarnings("static-access")
	public ScrollingBlocks(LoadTextures LT, int startingframex, int startingframey) {
		this.startingframex = startingframex;
		this.startingframey = startingframey;
		this.LT = LT;
		y = BW.requestatx((int)Math.round(x + 64)).height() + 37;
	}
	public ScrollingBlocks(){}
	void print(Object o){
		System.out.println(o);
	}
	int fullfloor(double input) {
		return (int)Math.round(Math.floor(input));
	}
	
	public void updateY() {
		y = BW.requestatx((int)Math.round(x + 64)).height() + 37;
	}
	@SuppressWarnings("static-access")
	public void Blocks(Graphics g, int framesizex, int framesizey) {
		currentframex = framesizex;
		currentframey = framesizey;
		int blockaccrossframex = (currentframex/blocksize) + 20, blockaccrossframey = (currentframey/blocksize) + 20;
		overlayblocks = new ArrayList<overlayblock>();
		double blocksizex = applydifx(blocksize);
		double blocksizey = applydify(blocksize);
		int strartingposx = (int) fullfloor(applydifx((blocksize * -6.0) - (blocksize *(x - fullfloor(x)))));
		int strartingposy = (int) fullfloor(applydify((blocksize * -6.0) + (blocksize *(y - fullfloor(y)))));
		//print(Math.round(applydifx((blocksize * -6.0) + (blocksize *(x - Math.floor(x))))));
		//print(Math.round(applydifx((blocksize * -6.0) + ((x + 0.0) - Math.floor(x)))));
		double currentblockdrawx = strartingposx;
		double currentblockdrawy = strartingposy;
		int lastblock = 0;
		int currentx = fullfloor(x);
		currentculum = BW.requestatx(currentx);
		//player.blocksizex = blocksizex;
		//player.blocksizey = blocksizey;
		//System.out.println(y-37);
		//print(y);
		for(int i = 0; i < blockaccrossframex; i++) {
			for(int ix = 0; ix < blockaccrossframey; ix++) {
				int currentblock = gettexture(currentx+i,(int)fullfloor(y-ix), (int)fullfloor(currentblockdrawx), (int)fullfloor(currentblockdrawy), lastblock);
				Map<Integer, BufferedImage> lightmap = LT.textures.get(currentblock);
				g.drawImage(lightmap.get(currentblock % 2 == 0 ? 100 - darknesslevel : 50 - darknesslevel), (int)Math.round(currentblockdrawx), (int)Math.round(currentblockdrawy), (int)Math.round(blocksizex), (int)Math.round(blocksizey), null);
				//player.drawplayerx = x+i == x+64?currentblockdrawx:player.drawplayerx;
				//player.drawplayery = y-ix == y-37?currentblockdrawy:player.drawplayery;
				//player.smalldrawx = (int) smallx;
				//player.smalldrawy = (int) smally;
				currentblockdrawy += blocksizey;
				lastblock = currentblock;
			}
			currentblockdrawx += blocksizex;
			currentculum = BW.requestatx(currentx+i);
			currentblockdrawy = strartingposy;
		}
		renderoverlays(g,blocksizex,blocksizey);
		
		return;
	}
	@SuppressWarnings("static-access")
	public void buttonlisten() {
		double movementamount = 10;
		if(!lockmovement) {
			if(kl.up) {
				y += movementamount;
			}
			if(kl.down) {
				y -= movementamount;
			}
			if(kl.left) {
				x -= movementamount;
			}
			if(kl.right) {
				x += movementamount;
			}
		}
	}
	@SuppressWarnings({ "static-access"})
	int gettexture(int x, int y, int drawx, int drawy, int lastblock) {
		JSONObject currentbiomedata = null;
		int currentoutput = y > currentculum.height()? currentculum.fillAir() : currentculum.fillBlock();
		//print(currentoutput);
		try {
				currentbiomedata = (JSONObject) biomedata.biomedata.get(currentculum.biomeid());
		} catch(Exception e){
			print(e);
		}
		
		if(isInMenu) 
			return settings.settingsdata.getInt("backgroundblock") * 2;
		
		if(y == currentculum.height()) {
			currentoutput = currentbiomedata.getInt("surfaceblockid") * 2;
		}
		if(y < currentculum.height() && y > currentculum.height() - currentbiomedata.getInt("fillheight")) {
			currentoutput = currentbiomedata.getInt("fillblockid") * 2;
		}
		if(y == currentculum.height() + 1) {
			int current = SF.getBlockIDStackAtX(x, currentoutput, currentculum.biomeid());
			currentoutput = current != -1 ? current : currentoutput;
		}
		//if(currentoutput == -4 && y < 450)
			//currentoutput = 18;
		
		
		
		String currentworldpos = Integer.toString(x) + " " + Integer.toString(y);
		currentoutput =	Manager.overrightblocks.containsKey(currentworldpos) ? Manager.overrightblocks.get(currentworldpos) : currentoutput;
		currentoutput =	Manager.playeroverrightblocks.containsKey(currentworldpos) ? Manager.playeroverrightblocks.get(currentworldpos) : currentoutput;
		
		
		
		
		int[] overlayblockscheck = {12, 30, 32, 40, 42, 44};
		if(Arrays.binarySearch(overlayblockscheck, currentoutput) >= 0) {
			overlayblocks.add(new overlayblock(currentoutput, new Point2D(drawx, drawy)));
			currentoutput = 4;
		}
		currentoutput = currentoutput == -4? currentculum.fillAir() : currentoutput;
		return currentoutput;
	}
	@SuppressWarnings("static-access")
	void renderoverlays(Graphics g, double blocksizex, double blocksizey) {
		int i = 0;
		while(i < (overlayblocks.size())) {
			if(i > overlayblocks.size()) {
				break;
			}
			Map<Integer, BufferedImage> lightmap = LT.textures.get(overlayblocks.get(i).blockID);
			switch(overlayblocks.get(i).blockID) {
			case 12:
				g.drawImage(lightmap.get(overlayblocks.get(i).blockID % 2 == 0 ? 100 - darknesslevel : 50 - darknesslevel), (int)((int)overlayblocks.get(i).location.x - ((blocksizex * 5.0)/2.7)), (int) (overlayblocks.get(i).location.y - (blocksizey * 4)), (int)(Math.round(blocksizex) * 5), (int)(Math.round(blocksizey) * 5), null);
				overlayblocks.get(i).location.render(g);
				overlayblocks.get(i).location.remove();
				break;
			case 18:
				g.drawImage(lightmap.get(overlayblocks.get(i).blockID % 2 == 0 ? 100 - darknesslevel : 50 - darknesslevel), overlayblocks.get(i).location.x, (overlayblocks.get(i).location.y + 5), (int)Math.round(blocksizex), (int)Math.round(blocksizey) - 5, null);
				overlayblocks.get(i).location.render(g);
				overlayblocks.get(i).location.remove();
				break;
			case 30:
				g.drawImage(lightmap.get(overlayblocks.get(i).blockID % 2 == 0 ? 100 - darknesslevel : 50 - darknesslevel), overlayblocks.get(i).location.x, overlayblocks.get(i).location.y, (int)Math.round(blocksizex), (int)Math.round(blocksizey), null);
				overlayblocks.get(i).location.render(g);
				overlayblocks.get(i).location.remove();
				break;
			case 32:
				g.drawImage(lightmap.get(overlayblocks.get(i).blockID % 2 == 0 ? 100 - darknesslevel : 50 - darknesslevel), overlayblocks.get(i).location.x, overlayblocks.get(i).location.y, (int)Math.round(blocksizex), (int)Math.round(blocksizey), null);
				overlayblocks.get(i).location.render(g);
				overlayblocks.get(i).location.remove();
				break;
			case 40:
				g.drawImage(lightmap.get(overlayblocks.get(i).blockID % 2 == 0 ? 100 - darknesslevel : 50 - darknesslevel), overlayblocks.get(i).location.x, overlayblocks.get(i).location.y, (int)Math.round(blocksizex), (int)Math.round(blocksizey), null);
				overlayblocks.get(i).location.render(g);
				overlayblocks.get(i).location.remove();
				break;
			case 42:
				g.drawImage(lightmap.get(overlayblocks.get(i).blockID % 2 == 0 ? 100 - darknesslevel : 50 - darknesslevel), overlayblocks.get(i).location.x, overlayblocks.get(i).location.y, (int)Math.round(blocksizex), (int)Math.round(blocksizey), null);
				overlayblocks.get(i).location.render(g);
				overlayblocks.get(i).location.remove();
				break;
			case 44:
				g.drawImage(lightmap.get(overlayblocks.get(i).blockID % 2 == 0 ? 100 - darknesslevel : 50 - darknesslevel), overlayblocks.get(i).location.x, overlayblocks.get(i).location.y, (int)Math.round(blocksizex), (int)Math.round(blocksizey), null);
				overlayblocks.get(i).location.render(g);
				overlayblocks.get(i).location.remove();
				break;
			}
			i++;
		}
	}
	double getframedifx() {
		return (currentframex + 0.0)/(startingframex + 0.0);
	}
	double getframedify() {
		//statingframey
		return (currentframey + 0.0)/(startingframey + 0.0);
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
		return (int)Math.round((input + 0.0) * getframedifx());
	}
}