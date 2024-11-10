package engine;

import generator.*;
import physics.Point2D;
import datareader.datareadermain;
import update.Update;
import Files.LoadSettings;
import Files.LoadTextures;
import Files.loadbiomedata;
import datawriter.DataWriter;
import gameMechanics.Tags.Overlay;
import gameMechanics.Tags.tagdata;
import gameMechanics.Tags.tagtype;
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
	
	record overlayblock(String blockID, int x, int y, double xoffset, double yoffset, double xsize, double ysize){}
	
	LoadTextures LT;
	public static buildworld BW = Manager.BW;
	static Keylistener kl = new Keylistener();
	static Player player = new Player();
	static LoadSettings settings = new LoadSettings();
	static SpawnFeatures SF = new SpawnFeatures();
	buildworld.column currentculum = null;
	loadbiomedata biomedata = new loadbiomedata();
	gameMechanics.Blocks block = new gameMechanics.Blocks();
	public static int startingframex = 0, startingframey = 0, currentframex, currentframey;
	public static final int blocksize = 18;
	public static double x, y;
	static double blocksizex;
	static double blocksizey;
	static int blockaccrossframex, blockaccrossframey;
	static int strartingposx;
	static int strartingposy;
	//print(Math.round(applydifx((blocksize * -6.0) + (blocksize *(x - Math.floor(x))))));
	//print(Math.round(applydifx((blocksize * -6.0) + ((x + 0.0) - Math.floor(x)))));
	static double currentblockdrawx;
	static double currentblockdrawy;
	public static boolean isInMenu = true;
	public static ArrayList<overlayblock> overlayblocks = new ArrayList<overlayblock>();
	static int darknesslevel = 0;
	@SuppressWarnings("static-access")
	public ScrollingBlocks(LoadTextures LT, int startingframex, int startingframey) {
		this.startingframex = startingframex;
		this.startingframey = startingframey;
		this.LT = LT;
		y = BW.requestatx((int)Math.round(x)).height();
	}
	public ScrollingBlocks(){}
	void print(Object o){
		System.out.println(o);
	}
	int fullfloor(double input) {
		return (int)Math.round(Math.floor(input));
	}
	
	public void updateY() {
		player.playerY = BW.requestatx((int)Math.round(player.playerX)).height();
	}
	@SuppressWarnings("static-access")
	public void Blocks(Graphics g, int framesizex, int framesizey) {
		currentframex = framesizex;
		currentframey = framesizey;
		overlayblocks = new ArrayList<overlayblock>();
		blocksizex = applydifx(blocksize);
		blocksizey = applydify(blocksize);
		currentblockdrawx = strartingposx;
		currentblockdrawy = strartingposy;
		String lastblock = "";
		int currentx = fullfloor(x);
		currentculum = BW.requestatx(currentx);
		for(int i = 0; i < blockaccrossframex; i++) {
			for(int ix = 0; ix < blockaccrossframey; ix++) {
				String currentblock = gettexture(currentx+i,(int)fullfloor(y-ix), (int)fullfloor(currentblockdrawx), (int)fullfloor(currentblockdrawy), lastblock);
				Map<Integer, BufferedImage> lightmap = LT.texturemap.get(currentblock).lightTextureMap();
				//currentblock % 2 == 0 ? 100 - darknesslevel : 50 - darknesslevel
				g.drawImage(lightmap.get(100 - darknesslevel), (int)Math.round(currentblockdrawx), (int)Math.round(currentblockdrawy), (int)Math.round(blocksizex), (int)Math.round(blocksizey), null);
				if(ix == (blockaccrossframey/2))
					player.ingameplayerdrawy = (int) currentblockdrawy;
				currentblockdrawy += blocksizey;
				lastblock = currentblock;
			}
			if(i == (blockaccrossframex/2))
				player.ingameplayerdrawx = (int) currentblockdrawx;
			currentblockdrawx += blocksizex;
			currentculum = BW.requestatx(currentx+i);
			currentblockdrawy = strartingposy;
		}
		renderoverlays(g,blocksizex,blocksizey);
		return;
	}
	@SuppressWarnings("static-access")
	public void updaterenderingpos() {
		int allowedoverlay = 20;
		blockaccrossframex = (int) (currentframex/blocksizex) + (allowedoverlay*2);
		blockaccrossframey = (int) (currentframey/blocksizey) + (allowedoverlay*2);
		x = player.playerX - ((blockaccrossframex/2));
		y = player.playerY + ((blockaccrossframey/2));
		strartingposx = (int) fullfloor(applydifx((blocksize * (allowedoverlay * -1.0)) - (blocksize *(x - fullfloor(x)))));
		strartingposy = (int) fullfloor(applydify((blocksize * (allowedoverlay * -1.0)) + (blocksize *(y - fullfloor(y)))));
		player.blockoffsetx = (int) (blocksize *(x - fullfloor(x)));
		player.blockoffsety = (int) (blocksize *(y - fullfloor(y)));
	}
	@SuppressWarnings({ "static-access"})
	String gettexture(int x, int y, int drawx, int drawy, String lastblock) {
		JSONObject currentbiomedata = null;
		String currentoutput = y > currentculum.height()? currentculum.fillAir() : currentculum.fillBlock();
		//print(currentoutput);
		try {
				currentbiomedata = (JSONObject) biomedata.biomedata.get(currentculum.biomeid());
		} catch(Exception e){
			print(e);
		}
		
		if(isInMenu) 
			return settings.settingsdata.getString("backgroundblock");
		
		if(y == currentculum.height()) {
			currentoutput = currentbiomedata.getString("surfaceblockid");
		}
		if(y < currentculum.height() && y > currentculum.height() - currentbiomedata.getInt("fillheight")) {
			currentoutput = currentbiomedata.getString("fillblockid");
		}
		if(y == currentculum.height() + 1) {
			String current = SF.getBlockIDStackAtX(x, currentoutput, currentculum.biomeid());
			currentoutput = current != null ? current : currentoutput;
		}
		
		
		
		String currentworldpos = Integer.toString(x) + " " + Integer.toString(y);
		currentoutput =	Manager.overrightblocks.containsKey(currentworldpos) ? Manager.overrightblocks.get(currentworldpos) : currentoutput;
		currentoutput =	Manager.playeroverrightblocks.containsKey(currentworldpos) ? Manager.playeroverrightblocks.get(currentworldpos) : currentoutput;
		
		
		
		if(block.overlaynames.containsKey(currentoutput)) {
			double xoffset = 0, yoffset = 0, xsize = 1, ysize = 1;
			for(int i = 0; i < block.blockMap.get(currentoutput).localtagmap.get(tagtype.overlay).size(); i++) {
				Overlay t = (Overlay)block.blockMap.get(currentoutput).localtagmap.get(tagtype.overlay).get(i);
				xoffset = t.XOffset;
				yoffset = t.YOffset;
				xsize = t.ScaleX;
				ysize = t.ScaleY;
				overlayblocks.add(new overlayblock(currentoutput, drawx, drawy, xoffset, yoffset, xsize, ysize));
			}
			currentoutput = block.blockMap.get(currentoutput).texturename;
		}
		currentoutput = currentoutput == null? currentculum.fillAir() : currentoutput;
		return currentoutput;
	}
	@SuppressWarnings("static-access")
	void renderoverlays(Graphics g, double blocksizex, double blocksizey) {
		int i = 0;
		while(i < (overlayblocks.size())) {
			if(i > overlayblocks.size()) {
				break;
			}
			Map<Integer, BufferedImage> lightmap = LT.texturemap.get(overlayblocks.get(i).blockID).lightTextureMap();
			g.drawImage(lightmap.get(100 - darknesslevel),
					overlayblocks.get(i).x - (int)Math.round(blocksizex*overlayblocks.get(i).xoffset),
					overlayblocks.get(i).y - (int)Math.round(blocksizey*overlayblocks.get(i).yoffset),
					(int)Math.round(blocksizex*overlayblocks.get(i).xsize),
					(int)Math.round(blocksizey*overlayblocks.get(i).ysize),
					null);
			i++;
		}
	}
	double getframedifx() {
		return (currentframex + 0.0)/(startingframex + 0.0);
	}
	double getframedify() {
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