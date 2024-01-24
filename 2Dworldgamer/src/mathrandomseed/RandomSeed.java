package mathrandomseed;

import java.nio.charset.Charset;
import java.util.*;
import java.lang.*;

@SuppressWarnings("unused")
public class RandomSeed {
	static int theseed;
	long trueseed = 0;
	int height = 0;
	int getcolumnbiome = 0;
	int alllandorwater = 0;
	int whitchlorw = 0;
	int allboimes = 0;
	double change = 0;
	double seedrandomness = 0;
	static boolean onstartup = true;
	Random random = new Random(theseed);
	static long mynum = 0;
	String alphabet = ("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
	private void RandomSeed() {
		if(mynum > Integer.MAX_VALUE - 1000) {
			mynum = Math.round(Math.sin(mynum) * 100);
		} else {
			mynum += random.nextInt(theseed);
		}
	}
	public int RandomInt(int max, int min, int seed) {
		theseed = seed;
		RandomSeed();
		int output = (int)Math.floor(Math.sin(mynum)*(max-min+1)+min);
		while(output < min) {
			RandomSeed();
			output = (int)Math.floor(Math.sin(mynum)*(max-min+1)+min);
		}
		while(output > max) {
			RandomSeed();
			output = (int)Math.floor(Math.sin(mynum)*(max-min+1)+min);
		}
		return output;
	}
	public double RandomDouble(double max, double min, int seed) {
		theseed = seed;
		RandomSeed();
		if(Math.sin(mynum)*(max-min+1)+min > max) {
			return (int)Math.floor(Math.sin(mynum)*(max-min+1)+min);
		} else {
			return (Math.sin(mynum)*(max-min+1)+min);
		}
	}
	public float RandomFloat(float max, float min, int seed) {
		theseed = seed;
		RandomSeed();
		if(Math.sin(mynum)*(max-min+1)+min > max) {
			return (int)Math.floor(Math.sin(mynum)*(max-min+1)+min);
		} else {
			return (float) (Math.sin(mynum)*(max-min+1)+min);
		}
	}
	public String RandomString(int length, boolean AllUppercase, boolean CanUppercaseAndLowercaseVary, int seed) {
		String output = "";
		char randomchar;
		theseed = seed;
		RandomSeed();
		for (int i = 0; i < length; i++) {
			randomchar = alphabet.charAt((int)Math.floor(Math.sin(mynum)*(26-1+1)+1));
			if(AllUppercase) {
				randomchar = Character.toUpperCase(randomchar);
			} else {
				randomchar = Character.toLowerCase(randomchar);
			}
			if(CanUppercaseAndLowercaseVary) {
				RandomSeed();
				if((int)Math.floor(Math.sin(mynum)*(1-0+1)+0) == 0) {
					randomchar = Character.toUpperCase(randomchar);
				} else {
					randomchar = Character.toLowerCase(randomchar);
				}
			}
			RandomSeed();
			output += randomchar;
		}
		return output;
	}
	public char RandomChar(boolean Uppercase, boolean IsUppercaseOrLowercaseInterchangeable, int seed) {
		char randomchar;
		theseed = seed;
		RandomSeed();
		randomchar = alphabet.charAt((int)Math.floor(Math.sin(mynum)*(26-1+1)+1));
		if(Uppercase) {
			randomchar = Character.toUpperCase(randomchar);
		} else {
			randomchar = Character.toLowerCase(randomchar);
		}
		if(IsUppercaseOrLowercaseInterchangeable) {
			RandomSeed();
			if((int)Math.floor(Math.sin(mynum)*(1-0+1)+0) == 0) {
				randomchar = Character.toUpperCase(randomchar);
			} else {
				randomchar = Character.toLowerCase(randomchar);
			}
		}
		return randomchar;
	}
	public boolean RandomBoolean(int seed) {
		theseed = seed;
		RandomSeed();
		if((int)Math.floor(Math.sin(mynum)*(10-0+1)+0) < 5) {
			return false;
		} else {
			return true;
		}
	}
	private int setup() {
		if(theseed < 0) {
			theseed *= -1;
		}
		
		trueseed = Math.round((Math.round(theseed + 3.14) * 13.14) * 10);
		change = trueseed;
		return 0;
	}
}