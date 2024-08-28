package Files;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import org.json.*;

import Files.*;

public class loadbiomedata {
	
	public static  JSONObject biomedata;
	public static ArrayList<biomesindemtypes> demtypes = new ArrayList<biomesindemtypes>();
	
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
	
	public class heights{
		int temp;
		ArrayList<temperatures> weardnesses = new ArrayList<temperatures>();
		public heights(int temp){
			this.temp = temp;
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
	public static int findClosest(int[] array, int target) {
        return Arrays.stream(array)
                     .boxed() // Converts int to Integer for comparison
                     .min((a, b) -> Integer.compare(Math.abs(a - target), Math.abs(b - target)))
                     .orElseThrow(() -> new IllegalArgumentException("Array cannot be empty"));
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
		//tempsorder = new String[11][biomeslist.length()];
		JSONObject currentbiome = biomedata.getJSONObject(biomeslist.getString(0));
		for(int i = 0; i < biomeslist.length(); i++) {
			currentbiome = biomedata.getJSONObject(biomeslist.getString(i));
			
		}
	}
	public String getbiometype(int temp){
		return "plains";
	}
}
