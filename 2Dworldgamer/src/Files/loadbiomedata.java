package Files;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

import org.json.*;

public class loadbiomedata {
	
	public static String tempsorder[][];
	public static  JSONObject biomedata;
	
	public class biomesindemtypes{
		ArrayList<temperatures> types = new ArrayList<temperatures>();
		String name;
		public biomesindemtypes(String name){
			this.name = name;
		}
		public int hashCode(){
			return name.hashCode();
		}
		public boolean equals(Object o){
			if(o.hashCode() == this.hashCode()){
				return true;
			}
			return false;
		}
	}
	
	public class temperatures{
		int temp;
		ArrayList<weardness> weardnesses = new ArrayList<weardness>();
		public temperatures(int temp){
			this.temp = temp;
		}
	}
	
	public class weardness{
		static int maxweardness;
		ArrayList<String> biomeIDs = new ArrayList<String>();
		public weardness(int weardness){
		}
		void addbiome(String t){
			biomeIDs.add(t);
		}
	}
	
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
		for(int i = 0; i < biomeslist.length(); i++) {
			currentbiome = biomedata.getJSONObject(biomeslist.getString(i));
			for(int ix = currentbiome.getInt("temp") - currentbiome.getInt("temprange"); ix < currentbiome.getInt("temp") + currentbiome.getInt("temprange"); ix++) {
				int iy = 0;
				while(tempsorder[ix][iy] != null) {
					iy++;
				}
				tempsorder[ix][iy] = biomeslist.getString(i);
			}
		}
	}
}
