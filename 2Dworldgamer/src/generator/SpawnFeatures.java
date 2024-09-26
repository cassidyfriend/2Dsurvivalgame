package generator;

import java.util.ArrayList;
import java.util.Arrays;

import Files.LoadFeatures;
import Files.loadbiomedata;
import mathrandomseed.SimplexNoise;

public class SpawnFeatures {
	
	static simnoise currentstacknoise;
	static String currentdem;
	
	public static ArrayList<String> cloneList(ArrayList<String> list) {
		if(list == null)
			return null;
		ArrayList<String> clone = new ArrayList<String>(list.size());
	    for (String item : list) clone.add(item);
	    return clone;
	}
	void print(Object o) {
		System.out.println(o);
	}

	public SpawnFeatures() {
		// TODO Auto-generated constructor stub
	}
	public int getBlockIDStackAtX(int x, int currentblock, String biome) {
		if(currentstacknoise == null || currentdem == null)
			return -1;
		ArrayList<String> currenttypes = cloneList(LoadFeatures.FeaturesDatabytype.get(LoadFeatures.featuretypes.STACK));
		ArrayList<String> currentByBiomes = cloneList(LoadFeatures.FeaturesDatabybiome.get(biome));
		if(currenttypes == null || currentByBiomes == null || currenttypes.size() == 0 || currentByBiomes.size() == 0)
			return -1;
		currenttypes.retainAll(currentByBiomes);
		int finalmax = 0;
		for(String current : currenttypes)
			finalmax += LoadFeatures.stackfeatures.get(current).scaleamount();
		int finalnoisemax = finalmax + (int)Math.round((finalmax + 0.0) * loadbiomedata.openness.get(biome));
		int randomout = (int) Math.round(currentstacknoise.getnoise(x, 1, 0, finalnoisemax));
		if(randomout < finalnoisemax - finalmax)
			return -1;
		int current = 0;
		for(int i = 0; i < currenttypes.size(); i++) {
			current += LoadFeatures.stackfeatures.get(currenttypes.get(i)).scaleamount();
			if(current > randomout && Arrays.binarySearch(LoadFeatures.stackfeatures.get(currenttypes.get(i)).replaceableblocks(), currentblock) >= 0)
				return LoadFeatures.stackfeatures.get(currenttypes.get(i)).blockid() * 2;
		}
		return -1;
	}
	public void updatenoise(String currentdemID, int seed) {
		currentdem = currentdemID;
		currentstacknoise = new simnoise(seed + 1);
	}
	public class simnoise{
		//noise work
		private SimplexNoise noise;
		public simnoise(int seed) {
			noise = new SimplexNoise(seed);
		}
		public double getnoise(double x, double scale, double min, double max) {
			double noiseValue = noise.noise(x * scale, 0);
			return map(noiseValue, -1, 1, min, max);
		}

		private double map(double value, double srcMin, double srcMax, double destMin, double destMax) {
			return destMin + (value - srcMin) * (destMax - destMin) / (srcMax - srcMin);
		}
	}
}
