package Files;

import java.awt.image.*;
import java.io.*;
import java.util.*;

import javax.imageio.ImageIO;

import org.json.JSONArray;
import org.json.JSONObject;

import datareader.datareadermain;
import gamelog.GameStatusLog;

public class LoadTextures {
	
	public record renderingdata(
			Map<Integer, BufferedImage> lightTextureMap,
			String name) {}
	
	static datareadermain dr = new datareadermain();
	static GameStatusLog gamelog = new GameStatusLog();
	public static ArrayList<renderingdata> textures = new ArrayList<renderingdata>();
	public static Map<String, renderingdata> texturemap = new HashMap<String, renderingdata>();
	@SuppressWarnings({ "resource" })
	public LoadTextures() throws IOException {
		String content = "";
		try {
			content = new Scanner(new File("resources/json data/texturepaths.json")).useDelimiter("\\Z").next();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		JSONObject texturepaths = new JSONObject(content);
		JSONArray texturelist = texturepaths.getJSONArray("textureslist");
		 for (int i = 1; i < texturelist.length() + 1; i++) {
			Map<Integer, BufferedImage> currenttexture = new HashMap<Integer, BufferedImage>();
			int keydouble = 100;
			for (int ix = 1; ix < 22; ix++) {
				float currentlightlevel = (float)keydouble / 100.0f;
				RescaleOp backgroundstone = new RescaleOp(currentlightlevel, 0.0f, null);
				BufferedImage currentimage = ImageIO.read(new File(texturelist.getJSONObject(i-1).getString("path")));
				currentimage = backgroundstone.filter(currentimage,null);
				currenttexture.put(keydouble, currentimage);
				keydouble -= 5;
			}
			textures.add(new renderingdata(currenttexture,
					texturelist.getJSONObject(i-1).getString("name")));
			texturemap.put(texturelist.getJSONObject(i-1).getString("name"), new renderingdata(currenttexture,
					texturelist.getJSONObject(i-1).getString("name")));
		}
		 gamelog.Log("fileLoader", "All textures have been loaded");
	}
}