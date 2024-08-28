package engine;

import generator.*;
import datareader.datareadermain;
import update.Update;
import Files.LoadSettings;
import Files.LoadTextures;
import Files.loadbiomedata;
import engine.*;
import datawriter.DataWriter;
import player.*;

import javax.swing.*;

import org.json.JSONObject;

import java.io.*;
import java.net.URL;
import java.util.*;
import java.awt.*;
import java.awt.image.*;
import javax.imageio.*;
import java.awt.event.*;
import javax.sound.sampled.*;

@SuppressWarnings("unused")
public class ScrollingBlocks {
	LoadTextures LT;
	public static buildworld BW = Manager.BW;
	static Keylistener kl = new Keylistener();
	static Player player = new Player();
	static LoadSettings settings = new LoadSettings();
	buildworld.column currentculum = null;
	loadbiomedata biomedata = new loadbiomedata();
	public static int startingframex, startingframey;
	public static int x = 0, y = 550;
	public static double smallx = 0, smally = 0;
	public static boolean isInMenu = true, lockmovement = true;
	public static ArrayList<Integer> overlayblocks = new ArrayList<Integer>();
	static int darknesslevel = 0;
	
	@SuppressWarnings("static-access")
	public ScrollingBlocks(LoadTextures LT, int startingframex, int startingframey) {
		this.startingframex = startingframex;
		this.startingframey = startingframey;
		this.LT = LT;
		y = BW.requestatx(x + 64, 0).height + 37;
	}
	public ScrollingBlocks(){}
	void print(Object o){
		System.out.println(o);
	}
	
	public void updateY() {
		y = BW.requestatx(x + 64, 0).height + 37;
	}
	@SuppressWarnings("static-access")
	public void Blocks(Graphics g, int framesizex, int framesizey) {
		overlayblocks = new ArrayList<Integer>();
		int blockaccrossframex = 110, blockaccrossframey = 63;
		double blocksizex = Math.round(framesizex/blockaccrossframex);
		double blocksizey = Math.round(framesizey/blockaccrossframey);
		int strartingposx = (int) Math.round((blocksizex*-6) + smallx);
		int strartingposy = (int) Math.round((blocksizey*-5) + smally);
		double currentblockdrawx = strartingposx;
		double currentblockdrawy = strartingposy;
		int lastblock = 0;
		currentculum = BW.requestatx(x, blockaccrossframex + 20);
		//player.blocksizex = blocksizex;
		//player.blocksizey = blocksizey;
		//System.out.println(y-37);
		//print(y);
		for(int i = 0; i < blockaccrossframex + 20; i++) {
			for(int ix = 0; ix < blockaccrossframey + 10; ix++) {
				int currentblock = gettexture(x+i,y-ix, (int)Math.round(currentblockdrawx), (int)Math.round(currentblockdrawy), lastblock);
				Map<Integer, BufferedImage> lightmap = LT.textures.get(currentblock);
				g.drawImage(lightmap.get(currentblock % 2 == 0 ? 100 - darknesslevel : 50 - darknesslevel), (int)Math.round(currentblockdrawx), (int)Math.round(currentblockdrawy), (int)Math.round(blocksizex), (int)Math.round(blocksizey), null);
				//player.drawplayerx = x+i == x+64?currentblockdrawx:player.drawplayerx;
				//player.drawplayery = y-ix == y-37?currentblockdrawy:player.drawplayery;
				player.smalldrawx = (int) smallx;
				player.smalldrawy = (int) smally;
				currentblockdrawy += blocksizey;
				lastblock = currentblock;
			}
			currentblockdrawx += blocksizex;
			currentculum = BW.requestatx(x+i, blockaccrossframex + 20);
			currentblockdrawy = strartingposy;
		}
		//print(BW.worlddata.requestatx(x+64, 0).biomeid);
		renderoverlays(g,blocksizex,blocksizey);
		int devidesize = 2;
		if(!lockmovement) {
			if(kl.up) {
				smally += blocksizey/devidesize;
				if(smally > blocksizey) {
					smally = 0;
					y++;
				}
			}
			if(kl.down) {
				smally -= blocksizey/devidesize;
				if(smally < 0) {
					smally = blocksizey;
					y--;
				}
			}
			if(kl.left) {
				smallx += blocksizex/devidesize;
				if(smallx > blocksizex) {
					smallx = 0;
					x--;
				}
			}
			
			if(kl.right) {
				smallx -= blocksizex/devidesize;
				if(smallx < 0) {
					smallx = blocksizex;
					x++;
				}
			}
		}
		return;
	}
	@SuppressWarnings({ "static-access"})
	int gettexture(int x, int y, int drawx, int drawy, int lastblock) {
		JSONObject currentbiomedata = null;
		int currentoutput = y > currentculum.height? currentculum.fillAir : currentculum.fillBlock;
		//print(currentoutput);
		try {
				currentbiomedata = (JSONObject) biomedata.biomedata.get(currentculum.biomeid);
		} catch(Exception e){}
		
		if(isInMenu) 
			return settings.settingsdata.getInt("backgroundblock");
		
		if(y == currentculum.height) {
			try {
				currentbiomedata = (JSONObject) biomedata.biomedata.get(currentculum.biomeid);
				currentoutput = (int) currentbiomedata.get("surfaceblockid") * 2;
			} catch(Exception e){}
		}
		if(y < currentculum.height && y > currentculum.height - currentbiomedata.getInt("fillheight")) {
			currentoutput = currentbiomedata.getInt("fillblockid");
		}
		//if(currentoutput == -4 && y < 450)
			//currentoutput = 18;
		
		
		
		
		String currentworldpos = Integer.toString(x) + " " + Integer.toString(y);
		currentoutput =	Manager.overrightblocks.containsKey(currentworldpos) ? Manager.overrightblocks.get(currentworldpos) : currentoutput;
		currentoutput =	Manager.playeroverrightblocks.containsKey(currentworldpos) ? Manager.playeroverrightblocks.get(currentworldpos) : currentoutput;
		
		
		
		
		int[] overlayblockscheck = {12, 30, 32};
		if(Arrays.binarySearch(overlayblockscheck, currentoutput) >= 0) {
			overlayblocks.add(currentoutput);
			overlayblocks.add(drawx);
			overlayblocks.add(drawy);
			currentoutput = 4;
		}
		currentoutput = currentoutput == -4? currentculum.fillAir : currentoutput;
		return currentoutput;
	}
	@SuppressWarnings("static-access")
	void renderoverlays(Graphics g, double blocksizex, double blocksizey) {
		int i = 0;
		while(i < (overlayblocks.size() / 3)) {
			if(i * 3 > overlayblocks.size() || overlayblocks.get(i * 3) > LT.textures.size()) {
				break;
			}
			Map<Integer, BufferedImage> lightmap = LT.textures.get(overlayblocks.get(i * 3));
			switch(overlayblocks.get(i * 3)) {
			case 12:
				g.drawImage(lightmap.get(overlayblocks.get(i * 3) % 2 == 0 ? 100 - darknesslevel : 50 - darknesslevel), (int) ((int)overlayblocks.get((i * 3) + 1) - (int)(Math.round(blocksizex)) + ((int)(Math.round(blocksizex) / 1.3))), (int) (overlayblocks.get((i * 3) + 2) - (blocksizey * 4)), (int)(Math.round(blocksizex) * 5), (int)(Math.round(blocksizey) * 5), null);
				break;
			case 18:
				g.drawImage(lightmap.get(overlayblocks.get(i * 3) % 2 == 0 ? 100 - darknesslevel : 50 - darknesslevel), overlayblocks.get((i * 3) + 1), (overlayblocks.get((i * 3) + 2) + 5), (int)Math.round(blocksizex), (int)Math.round(blocksizey) - 5, null);
				break;
			case 30:
				g.drawImage(lightmap.get(overlayblocks.get(i * 3) % 2 == 0 ? 100 - darknesslevel : 50 - darknesslevel), overlayblocks.get((i * 3) + 1), overlayblocks.get((i * 3) + 2), (int)Math.round(blocksizex), (int)Math.round(blocksizey), null);
				break;
			case 32:
				g.drawImage(lightmap.get(overlayblocks.get(i * 3) % 2 == 0 ? 100 - darknesslevel : 50 - darknesslevel), overlayblocks.get((i * 3) + 1), overlayblocks.get((i * 3) + 2), (int)Math.round(blocksizex), (int)Math.round(blocksizey), null);
				break;
			}
			i++;
		}
	}
}