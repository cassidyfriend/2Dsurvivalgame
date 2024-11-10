package Files;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;

public class LoadFeatures {
	
	public enum featuretypes{
		STACK,
	}
	
	public record stackfeature(int scaleamount, String blockid, String[] replaceableblocks) {}
	
	void print(Object o) {
		System.out.println(o);
	}
	static int[] getIntArray(JSONArray numberArray, int multiplier) {
		int[] output = new int[numberArray.length()];
		for (int i = 0; i < numberArray.length(); i++) {
		    output[i] = numberArray.getInt(i) * multiplier;
		}
		return output;
	}
	
	static String[] getStringArray(JSONArray stringArray) {
		String[] output = new String[stringArray.length()];
		for (int i = 0; i < stringArray.length(); i++) {
		    output[i] = stringArray.getString(i);
		}
		return output;
	}
	
	public static JSONObject Featuresdata = new JSONObject();
	public static HashMap<String, ArrayList<String>> FeaturesDatabybiome = new HashMap<String, ArrayList<String>>();
	public static HashMap<featuretypes, ArrayList<String>> FeaturesDatabytype = new HashMap<featuretypes, ArrayList<String>>();
	public static HashMap<String, stackfeature> stackfeatures = new HashMap<String, stackfeature>();
	
	@SuppressWarnings("resource")
	public LoadFeatures() {
		String content = "";
		try {
			content = new Scanner(new File("resources/json data/game data jsons/worldFeatures.json")).useDelimiter("\\Z").next();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		Featuresdata = new JSONObject(content);
		for(int i = 0; i < Featuresdata.getJSONArray("feature's names").length(); i++) {
			JSONObject currentfeature = Featuresdata.getJSONObject(Featuresdata.getJSONArray("feature's names").getString(i));
			switch(currentfeature.getString("type")) {
			case "stack":
				if(FeaturesDatabytype.containsKey(featuretypes.STACK)) {
					FeaturesDatabytype.get(featuretypes.STACK).add(Featuresdata.getJSONArray("feature's names").getString(i));
				}
				else {
					FeaturesDatabytype.put(featuretypes.STACK, new ArrayList<String>());
					FeaturesDatabytype.get(featuretypes.STACK).add(Featuresdata.getJSONArray("feature's names").getString(i));
				}
				String [] replaceableblocks = getStringArray(currentfeature.getJSONArray("replaceable blocks"));
				Arrays.sort(replaceableblocks);
				stackfeatures.put(Featuresdata.getJSONArray("feature's names").getString(i), new stackfeature(
						currentfeature.getInt("scale amount"),
						currentfeature.getString("block id"),
						replaceableblocks
						));
				break;
			}
			for(int ix = 0; ix < currentfeature.getJSONArray("relevant biomes").length(); ix++) {
				if(FeaturesDatabybiome.containsKey(currentfeature.getJSONArray("relevant biomes").getString(ix))) {
					FeaturesDatabybiome.get(currentfeature.getJSONArray("relevant biomes").getString(ix)).add(Featuresdata.getJSONArray("feature's names").getString(i));
				}
				else {
					FeaturesDatabybiome.put(currentfeature.getJSONArray("relevant biomes").getString(ix), new ArrayList<String>());
					FeaturesDatabybiome.get(currentfeature.getJSONArray("relevant biomes").getString(ix)).add(Featuresdata.getJSONArray("feature's names").getString(i));
				}
			}
		}
	}
}
