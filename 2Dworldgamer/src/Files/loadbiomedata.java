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
	public static heights heightdata;
	public static temperatures temperaturedata;
	public static weardness weardnessdata;
	public static HashMap<String, Double> openness = new HashMap<String, Double>();
	
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
		}
		String[] getbiomebydemtype(String demtype){
			return demandbiomematching.get(demtype).toArray(new String[0]);
		}
	}
	
	public class heights{
		Map<Integer, ArrayList<String>> heightpackage = new HashMap<>();
		int[] heights;
		void addbiome(JSONObject currentdata, String name){
			if(heightpackage.containsKey(currentdata.getInt("minheight"))){
				heightpackage.get(currentdata.getInt("minheight")).add(name);
			}
			else{
				heightpackage.put(currentdata.getInt("minheight"), new ArrayList<String>());
				heightpackage.get(currentdata.getInt("minheight")).add(name);
			}
		}
		void finished(){
			heights = heightpackage.keySet().stream().mapToInt((Integer i)->i).toArray();
		}
		String[] getbiomebyheight(int height){
			height = findClosestFloor(heights, height);
			return heightpackage.get(height).toArray(new String[0]);
		}
	}
	
	public class temperatures{
		Map<Integer, ArrayList<String>> temperaturepackage = new HashMap<>();
		int[] temperatures;
		void addbiome(Integer currenttemp, String name){
			if(temperaturepackage.containsKey(currenttemp)){
				temperaturepackage.get(currenttemp).add(name);
			}
			else{
				temperaturepackage.put(currenttemp, new ArrayList<String>());
				temperaturepackage.get(currenttemp).add(name);
			}
		}
		void finished(){
			temperatures = temperaturepackage.keySet().stream().mapToInt((Integer i)->i).toArray();
		}
		String[] getbiomebytemp(int temp){
			temp = findClosestFloor(temperatures, temp);
			return temperaturepackage.get(temp).toArray(new String[0]);
		}
	}
	
	public class weardness{
		Map<String, Integer> weardpackage = new HashMap<>();
		int[] weardnesses;
		void addbiome(Integer currentweardness, String name){
			if(!weardpackage.containsKey(name)){
				weardpackage.put(name, currentweardness);
			}
		}
		void finished(){
			weardnesses = weardpackage.values().stream().mapToInt((Integer i)->i).toArray();
		}
		int getweardnessbyname(String name){
			return weardpackage.get(name).intValue();
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
			content = new Scanner(new File("resources/json data/game data jsons/biomedata.json")).useDelimiter("\\Z").next();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		biomedata = new JSONObject(content);
		JSONArray biomeslist = biomedata.getJSONArray("biomes");
		//tempsorder = new String[11][biomeslist.length()];
		JSONObject currentbiome = biomedata.getJSONObject(biomeslist.getString(0));
		biomedemdata = new biomesindemtypes();
		heightdata = new heights();
		temperaturedata = new temperatures();
		weardnessdata = new weardness();
		for(int i = 0; i < biomeslist.length(); i++) {
			currentbiome = biomedata.getJSONObject(biomeslist.getString(i));
			biomedemdata.addbiome(currentbiome, biomeslist.getString(i));
			heightdata.addbiome(currentbiome, biomeslist.getString(i));
			for(int ix = currentbiome.getInt("temp") - Math.abs(currentbiome.getInt("temprange")); ix < currentbiome.getInt("temp") + Math.abs(currentbiome.getInt("temprange")); ix++) {
				temperaturedata.addbiome(ix, biomeslist.getString(i));
			}
			weardnessdata.addbiome(currentbiome.getInt("weardness"), biomeslist.getString(i));
			openness.put(biomeslist.getString(i), currentbiome.getDouble("open spread percentage"));
		}
		heightdata.finished();
		temperaturedata.finished();
		weardnessdata.finished();
	}
	public static ArrayList<String> cloneList(ArrayList<String> list) {
		ArrayList<String> clone = new ArrayList<String>(list.size());
	    for (String item : list) clone.add(item);
	    return clone;
	}
	String applyweardness(ArrayList<String> names, double weardness){
		double currentweardness = 0;
		int[] weardnesses = new int[names.size()];
		for(int i = 0; i < names.size(); i++){
			weardnesses[i] = weardnessdata.getweardnessbyname(names.get(i));
			currentweardness += weardnesses[i];
		}
		currentweardness *= weardness;
		int currentoffset = 0;
		for(int i = 0; i < names.size(); i++){
			currentoffset += weardnesses[i];
			if(currentoffset > currentweardness){
				return names.get(i);
			}
		}
		return null;
	}
	public String getbiometype(String dimensionname, int height, int temp, double weirdness){
		ArrayList<String> possiblebiomes = new ArrayList<String>(Arrays.asList(biomedemdata.getbiomebydemtype(dimensionname)));
		if(possiblebiomes.size() == 1){
			return possiblebiomes.get(0);
		}
		if(possiblebiomes.size() == 0){
			return "plains";
		}
		ArrayList<String> currentheightbiomes = new ArrayList<String>(Arrays.asList(heightdata.getbiomebyheight(height)));
		currentheightbiomes.retainAll(possiblebiomes);
		if(currentheightbiomes.size() == 1){
			return currentheightbiomes.get(0);
		}
		if(currentheightbiomes.size() == 0){
			return applyweardness(possiblebiomes, weirdness);
		}
		possiblebiomes = currentheightbiomes;
		ArrayList<String> currenttempbiomes = new ArrayList<String>(Arrays.asList(temperaturedata.getbiomebytemp(temp)));
		currenttempbiomes.retainAll(possiblebiomes);
		if(currenttempbiomes.size() == 1){
			return currenttempbiomes.get(0);
		}
		if(currenttempbiomes.size() == 0){
			return applyweardness(possiblebiomes, weirdness);
		}
		possiblebiomes = currenttempbiomes;
		return applyweardness(possiblebiomes, weirdness);
	}
}
