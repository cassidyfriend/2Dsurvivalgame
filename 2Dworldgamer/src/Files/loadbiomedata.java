package Files;

import java.io.*;
import java.util.Scanner;

import org.json.*;

public class loadbiomedata {
	
	public static String tempsorder[][];
	public static  JSONObject biomedata;
	public static int maxheight = 0;
	public static int minheight = 0;
	void print(Object o) {
		System.out.println(o);
	}
	
	@SuppressWarnings("resource")
	public loadbiomedata(){
		String content = "";
		try {
			content = new Scanner(new File("src/Files/biomedata.json")).useDelimiter("\\Z").next();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		biomedata = new JSONObject(content);
		JSONArray biomeslist = biomedata.getJSONArray("biomes");
		tempsorder = new String[11][biomeslist.length()];
		JSONObject currentbiome = biomedata.getJSONObject(biomeslist.getString(0));
		minheight = currentbiome.getInt("height") - 100;
		for(int i = 0; i < biomeslist.length(); i++) {
			currentbiome = biomedata.getJSONObject(biomeslist.getString(i));
			checkcurrentheightmin(currentbiome);
			checkcurrentheightmax(currentbiome);
			for(int ix = currentbiome.getInt("temp") - currentbiome.getInt("temprange"); ix < currentbiome.getInt("temp") + currentbiome.getInt("temprange"); ix++) {
				int iy = 0;
				while(tempsorder[ix][iy] != null) {
					iy++;
				}
				tempsorder[ix][iy] = biomeslist.getString(i);
			}
		}
	}
	void checkcurrentheightmax(JSONObject currentdata) {
		int currentmaxheight = currentdata.getInt("height") + currentdata.getInt("maxadd");
		maxheight = currentmaxheight > maxheight? currentmaxheight : maxheight;
	}
	void checkcurrentheightmin(JSONObject currentdata) {
		int currentmaxheight = currentdata.getInt("height");
		minheight = currentmaxheight < minheight? currentmaxheight - 100 : minheight;
	}
}
