package loadFiles;

import java.awt.image.*;
import java.io.*;
import java.util.*;

import javax.imageio.ImageIO;

import start.main;
import datareader.datareadermain;
import gamelog.GameStatusLog;

@SuppressWarnings("unused")
public class LoadTextures {
	static datareadermain dr = new datareadermain();
	static GameStatusLog gamelog = new GameStatusLog();
	public static ArrayList<Map<Integer, BufferedImage>> textures = new ArrayList<Map<Integer, BufferedImage>>();
	@SuppressWarnings("serial")
	public static void LoadTextures() throws IOException {
		 dr.inputpath("src/loadFiles/texturepaths.txt");
		 dr.inputclass("texturespath");
		 for (int i = 1; i < dr.getInt("amount") + 1; i++) {
			Map<Integer, BufferedImage> currenttexture = new HashMap<Integer, BufferedImage>();
			int keydouble = 100;
			for (int ix = 1; ix < 21 + 1; ix++) {
				float currentlightlevel = (float)keydouble / 100.0f;
				RescaleOp backgroundstone = new RescaleOp(currentlightlevel, 0.0f, null);
				BufferedImage currentimage = ImageIO.read(new File(dr.getString("case" + i)));
				currentimage = backgroundstone.filter(currentimage,null);
				currenttexture.put(keydouble, currentimage);
				keydouble -= 5;
			}
			textures.add(currenttexture);
		}
		 gamelog.Log("fileLoader", "All files have been loaded");
	}
}
