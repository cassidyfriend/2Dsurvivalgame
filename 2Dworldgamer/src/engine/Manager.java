package engine;


import generator.buildworld;
import generator.spawnstructures;
import datareader.datareadermain;
import update.Update;
import loadFiles.LoadTextures;
import start.main;
import engine.TwoD;
import engine.MouseListerner;
import engine.BuildButtons;
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
public class Manager {
	static TwoD TD = new TwoD();
	static LoadTextures LT = new LoadTextures();
	static MouseListerner ML = new MouseListerner();
	static BuildButtons BB = new BuildButtons();
	static buildworld BW = new buildworld();
	@SuppressWarnings("static-access")
	public static void onstartup() {
		//System.out.println("test");
		try {
			LT.LoadTextures();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		BB.makebuttonlists(500);
		//TD.loadframe();
		newworld();
		TD.startframes("game name");
		eachframe();
	}
	@SuppressWarnings("static-access")
	public static void eachframe() {
		ML.onupdate();
	}
	public static void newworld() {
		BW.gencolomes();
	}
}
