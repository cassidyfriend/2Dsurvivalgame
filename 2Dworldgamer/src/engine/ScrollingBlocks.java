package engine;

import generator.*;
import datareader.datareadermain;
import update.Update;
import loadFiles.LoadTextures;
import start.main;
import engine.*;
import datawriter.DataWriter;

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
public class ScrollingBlocks {
	static LoadTextures LT = new LoadTextures();
	static buildworld BW = new buildworld();
	static Keylistener kl = new Keylistener();
	@SuppressWarnings("static-access")
	static int x = BW.genlenght / 2, y = 630;
	@SuppressWarnings("static-access")
	public void Blocks(Graphics g, int framesizex, int framesizey) {
		int blocksizex = Math.round(framesizex/60);
		int blocksizey = Math.round(framesizey/43);
		int strartingposx = Math.round(blocksizex*-5);
		int strartingposy = Math.round(blocksizey*-5);
		int currentblockdrawx = strartingposx;
		int currentblockdrawy = strartingposy;
		for(int i = 0; i < 70; i++) {
			for(int ix = 0; ix < 53; ix++) {
				int currentblock = gettexture(x+i,y-ix);
				Map<Integer, BufferedImage> lightmap = LT.textures.get(currentblock);
				g.drawImage(lightmap.get(30), currentblockdrawx, currentblockdrawy, blocksizex, blocksizey, null);
				currentblockdrawy += blocksizey;
			}
			currentblockdrawx += blocksizex;
			currentblockdrawy = strartingposy;
		}
		if(kl.down) {
			y -= 1;
		}
		if(kl.up) {
			y += 1;
		}
		if(kl.left) {
			x -= 1;
		}
		if(kl.right) {
			x += 1;
		}
		return;
	}
	@SuppressWarnings("static-access")
	int gettexture(int x, int y) {
		int currentoutput = 0;
		if(y > BW.columnheight.get(x)) {
			if(y < 570) {
				currentoutput = 10;
			}
			else
				currentoutput = 3;
		}
		if(y < BW.columnheight.get(x)) {
			currentoutput = 0;
		}
		if(y == BW.columnheight.get(x)) {
			currentoutput = 2;
		}
		if(y + 10 < BW.columnheight.get(x)) {
			currentoutput = 4;
		}
		return currentoutput;
	}
}
