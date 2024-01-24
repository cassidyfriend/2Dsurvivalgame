package generator;

import javax.swing.*;
import java.io.*;
import java.net.URL;
import java.util.*;
import java.awt.*;
import java.awt.image.*;
import javax.imageio.*;
import java.awt.event.*;
import javax.sound.sampled.*;

import start.main;
import datareader.datareadermain;
import mathrandomseed.RandomSeed;
import gamelog.GameStatusLog;

@SuppressWarnings("unused")
public class buildworld {
	static RandomSeed RS = new RandomSeed();
	static int seed = 1100;
	public static int genlenght = 100000;
	static String savename = "save1";
	static GameStatusLog gamelog = new GameStatusLog();
	public static ArrayList<Integer> landorwater = new ArrayList<Integer>();
	public static ArrayList<Integer> landorwatersize = new ArrayList<Integer>();
	public static ArrayList<Integer> minuslandorwater = new ArrayList<Integer>();
	public static ArrayList<Integer> minuslandorwatersize = new ArrayList<Integer>();
	public static ArrayList<Integer> columnheight = new ArrayList<Integer>();
	public static void gencolomes() {
		if(seed <= 1) {
			gamelog.Log("seed", "bad seed");
			seed = 2 + (int)(Math.random() * ((1000 - 2) + 1));
			gamelog.Log("seed", "the seed now is set to: " + seed);
		}
		genwaterandland();
		makeculums();
	}
	static void genwaterandland() {
		int currentworldsize = 0;
		while (true) {
			int currentrandomnumber = RS.RandomInt(2000,400,seed);
			currentworldsize += currentrandomnumber;
			landorwater.add(RS.RandomInt(3,1,seed));
			landorwatersize.add(currentrandomnumber);
			//System.out.println(currentrandomnumber);
			if(currentworldsize > genlenght) {
				return;
			}
		}
	}
	static void makeculums() {
		for (int i = 0; i < genlenght; i++) {
			columnheight.add(500);
		}
		int changingspot = 0;
		while(changingspot < genlenght) {
			int max = 150;
			columnheight.set(changingspot, RS.RandomInt(max,0,seed) + 500);
			changingspot += RS.RandomInt(max,0,seed);
			gamelog.Log("columngen", Math.round((changingspot+0.0/genlenght+0.0)/100) + "%");
		}
		changingspot = 0;
		while(changingspot + 1 < genlenght) {
			int currentheight = columnheight.get(changingspot);
			if(columnheight.get(changingspot) == columnheight.get(changingspot + 1)) {
			}
			if(columnheight.get(changingspot) > columnheight.get(changingspot + 1)) {
				columnheight.set(changingspot + 1, columnheight.get(changingspot) - RS.RandomInt(1,0,seed));
			}
			changingspot++;
		}
		changingspot = genlenght - 1;
		while(changingspot - 1 > 0) {
			int currentheight = columnheight.get(changingspot);
			if(columnheight.get(changingspot) == columnheight.get(changingspot - 1)) {
			}
			if(columnheight.get(changingspot) > columnheight.get(changingspot - 1)) {
				columnheight.set(changingspot - 1, columnheight.get(changingspot) - RS.RandomInt(1,0,seed));
			}
			changingspot--;
		}
	}
}