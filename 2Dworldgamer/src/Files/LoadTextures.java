package Files;

import java.awt.image.*;
import java.io.*;
import java.util.*;

import javax.imageio.ImageIO;

import org.json.JSONArray;
import org.json.JSONObject;

import datareader.datareadermain;
import gamelog.GameStatusLog;

@SuppressWarnings("unused")
public class LoadTextures {
	static datareadermain dr = new datareadermain();
	static GameStatusLog gamelog = new GameStatusLog();
	public static ArrayList<Map<Integer, BufferedImage>> textures = new ArrayList<Map<Integer, BufferedImage>>();
	@SuppressWarnings({ "resource" })
	public LoadTextures() throws IOException {
		String content = "";
		try {
			content = new Scanner(new File("src/Files/texturepaths.json")).useDelimiter("\\Z").next();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		JSONObject texturepaths = new JSONObject(content);
		JSONArray texturelist = texturepaths.getJSONArray("textureslist");
		 for (int i = 1; i < texturelist.length(); i++) {
			Map<Integer, BufferedImage> currenttexture = new HashMap<Integer, BufferedImage>();
			int keydouble = 100;
			for (int ix = 1; ix < 22; ix++) {
				float currentlightlevel = (float)keydouble / 100.0f;
				RescaleOp backgroundstone = new RescaleOp(currentlightlevel, 0.0f, null);
				BufferedImage currentimage = ImageIO.read(new File(texturelist.getString(i-1)));
				currentimage = backgroundstone.filter(currentimage,null);
				currenttexture.put(keydouble, currentimage);
				keydouble -= 5;
			}
			textures.add(currenttexture);
			textures.add(currenttexture);
		}
		 gamelog.Log("fileLoader", "All textures have been loaded");
	}
}
