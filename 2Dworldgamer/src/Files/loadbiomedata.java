package Files;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;

import org.json.*;

import Files.*;

public class loadbiomedata {
	
	public static  JSONObject biomedata;
	public static ArrayList<biomesindemtypes> demtypes = new ArrayList<biomesindemtypes>();
	public static biomesindemtypes biomedemdata;
	
	public class biomesindemtypes{
		Map<String, ArrayList<String>> demandbiomematching = new HashMap<>();
		void addbiome(JSONObject currentdata, String name){
			if(demandbiomematching.containsKey(currentdata.getString("relivent demension"))){
				demandbiomematching.get(currentdata.getString("relivent demension")).add(name);
			}
			else{
				demandbiomematching.put(currentdata.getString("relivent demension"), new ArrayList<String>());
				demandbiomematching.get(currentdata.getString("relivent demension")).add(name);
			}
			print(demandbiomematching);
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
	public static int findClosestFloor(int[] array, int target) {
        return Arrays.stream(array)
                     .boxed() // Converts int to Integer
                     .filter(x -> x <= target) // Only consider values less than or equal to the target
                     .max(Integer::compare) // Find the maximum value among the remaining ones
                     .orElseThrow(() -> new IllegalArgumentException("No values less than or equal to target"));
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
		biomedemdata = new biomesindemtypes();
		for(int i = 0; i < biomeslist.length(); i++) {
			currentbiome = biomedata.getJSONObject(biomeslist.getString(i));
			biomedemdata.addbiome(currentbiome, biomeslist.getString(i));
		}
	}
	public String getbiometype(String dimensionname, int height, int temp, double weardness){
		return "plains";
	}
}
