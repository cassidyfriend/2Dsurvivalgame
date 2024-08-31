package generator;

import org.json.*;

import java.io.*;
import java.net.URL;
import java.util.*;
import java.awt.*;
import java.awt.image.*;
import javax.imageio.*;
import java.awt.event.*;
import javax.sound.sampled.*;
import javax.swing.*;

import datareader.datareadermain;
import engine.*;
import mathrandomseed.*;
import gamelog.GameStatusLog;
import Files.*;
import math.GameMath;

@SuppressWarnings("unused")
public class buildworld {
	static ScrollingBlocks SB = new ScrollingBlocks();
	static GameMath GM = new GameMath();
	public static int seed = 205;
	//205
	//35360
	//6465
	public static int genlenght = 600000;
	static String savename = "save1";
	static GameStatusLog gamelog = new GameStatusLog();
	public static dimensionstypes currentdmconstructor = dimensionstypes.stack;
	public static String currentdmtypeID = "";
	public static Object currentdm;
	loadbiomedata biomedata = new loadbiomedata();
	
	
	public class column{
		public  String biomeid, currentdemname;
		public int height, fillBlock, fillAir;
		public boolean requestmoreinfo;
	}
	class dimension{
		int fillBlock, fillAir,height;
		boolean requestmoreinfo = false;
	}
	public enum dimensionstypes{
		stack,
		TDnoise,
		islands
	}
	class stack{
		int minHeight, maxHeight, divider[], times, seed, fillBlock, fillAir;
		double scaleoffsets[];
		boolean requestmoreinfo;
		simnoise sn;
		stack(int seed, int minHeight, int maxHeight, int divider[], double scaleoffsets[], int fillblock, int fillair, boolean requestmoreinfo) {
			if(divider.length != scaleoffsets.length) {
				System.out.println("lenghts of divider and scaleoffsets are not equal");
				return;
			}
			this.seed = seed;
			this.minHeight = minHeight;
			this.maxHeight = maxHeight;
			this.divider = divider;
			this.scaleoffsets = scaleoffsets;
			this.fillBlock = fillblock;
			this.fillAir = fillair;
			this.requestmoreinfo = requestmoreinfo;
			sn = new simnoise(seed);
		}
		public int getheight(int x) {
			double output = 0;
			int i = 0;
			output += sn.getnoise(x, scaleoffsets[0], minHeight, maxHeight);
			for(i = 0; i < scaleoffsets.length; i++) {
				output *= 1.0-(divider[i]+0.0)/(maxHeight+0.0);
				output += sn.getnoise(x, scaleoffsets[i], minHeight, maxHeight*(divider[i]+0.0)/(maxHeight+0.0));
			}
			return (int)Math.round(output);
		}
		public void update(int maxHeight) {
			this.maxHeight = maxHeight;
		}
	}
	
	public column requestatx(int x, int xmax) {
		column output = new column();
		dimension current = getdimension(x);
		output.height = current.height;
		output.fillBlock = current.fillBlock;
		output.fillAir = current.fillAir;
		output.requestmoreinfo = current.requestmoreinfo;
		output.biomeid = biomedata.getbiometype(currentdmtypeID, 500, 5, 0.5);
		return output;
	}
	@SuppressWarnings("static-access")
	public dimension getdimension(double x) {
		dimension output = new dimension();
		if(currentdm instanceof stack) {
			output.height = ((stack)currentdm).getheight((int)Math.round(x));
			output.fillBlock = ((stack)currentdm).fillBlock;
			output.fillAir = ((stack)currentdm).fillAir;
			output.requestmoreinfo = ((stack)currentdm).requestmoreinfo;
		}
		return output;
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
	public void updatenoise() {
		updatedimension(Loaddimensions.dimensionsdata.getString("starting dimension"));
	}
	public void updatedimension(String id) {
		currentdmtypeID = id;
		JSONObject current = Loaddimensions.dimensionsdata.getJSONObject(id);
		switch(current.getString("type")) {
		case "stack":
			currentdm = new stack(
					seed + current.getInt("seed offset"),
					current.getInt("min Height"),
					current.getInt("max Height"),
					getIntArray(current.getJSONArray("divider")),
					getDoubleArray(current.getJSONArray("scaleoffsets")),
					current.getInt("fill Block"),
					current.getInt("fill Air"),
					current.getBoolean("request more info")
					);
			break;
		}
	}
	
	static void print(Object o) {
		System.out.println(o);
	}
	static int[] getIntArray(JSONArray numberArray) {
		int[] output = new int[numberArray.length()];
		for (int i = 0; i < numberArray.length(); i++) {
		    output[i] = numberArray.getInt(i);
		}
		return output;
	}
	static double[] getDoubleArray(JSONArray numberArray) {
		double[] output = new double[numberArray.length()];
		for (int i = 0; i < numberArray.length(); i++) {
		    output[i] = numberArray.getDouble(i);
		}
		return output;
	}
	public boolean contains(Object o, @SuppressWarnings("rawtypes") ArrayList input) {
        return indexOf(o, input) >= 0;
    }
    public int indexOf(Object o, @SuppressWarnings("rawtypes") ArrayList input) {
        return indexOfRange(o, 0, input.size(), input);
    }

    int indexOfRange(Object o, int start, int end, @SuppressWarnings("rawtypes") ArrayList input) {
        if (o == null) {
            for (int i = start; i < end; i++) {
                if (input.get(i) == null) {
                    return i;
                }
            }
        } else {
            for (int i = start; i < end; i++) {
                if (input.get(i).equals(o)) {
                    return i;
                }
            }
        }
        return -1;
    }
	


	public void gencolomes() {
		if(seed <= 1) {
			gamelog.Log("seed", "bad seed");
			//seed = 2 + (int)(Math.random() * ((100000 - 2) + 1));
			gamelog.Log("seed", "the seed now is set to: " + seed);
		}
		
		
		updatedimension(Loaddimensions.dimensionsdata.getString("starting dimension"));
	}
}