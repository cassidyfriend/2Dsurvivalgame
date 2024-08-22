package datareader;


import java.io.*;
import java.util.*;


public class datareadermain {
	private static String inputpath;
	private static String datatoget = null;
	private static String datatogetclass;
	private static String expectedtype;
	private static String outputString;
	private static char outputchar;
	private static int outputint = 0;
	private static float outputfloat = 0f;
	private static double outputdouble = 0d;
	private static boolean outputboolean;
	static String info = null;
	static boolean found = false;

	@SuppressWarnings("resource")
	private void mainreader() {
		int finder = 1;
			try {
				FileReader fr;
				fr = new FileReader(inputpath);
				Scanner inFile = new Scanner(fr);
				while(inFile.hasNext()) {
					info = inFile.next();
					switch(finder) {
						case 1:
							if(info.equals(datatogetclass)){
								finder = 2;
								break;
							}
							if(info.equals(datatogetclass + ":")) {
								finder = 3;
								break;
							} 
							if( info.equals(datatogetclass + ":{")) {
								finder = 4;
								break;
							}
							break;
						case 2:
							if(info.equals(":")) {
								finder = 3;
								break;
							}
							if(info.equals(":{")) {
								finder = 4;
								break;
							}
							break;
						case 3:
							if(info.equals("{")) {
								finder = 4;
								break;
							}
							break;
						case 4:
							if(info.contains(datatoget) && (!(info.contains("\"" + datatoget + "\"")))) {
									finder = 5;
									break;
							}else if(info.contains("}")) {
								System.out.println("no such element was found!");
								found = true;
							}
						break;
						case 5:
							if(info.equals("=")) {
								finder = 6;
							} else {
								System.out.println("improper syntax or are you missing a cast?");
								found = true;
							}
							break;
						case 6:
							switch(expectedtype) {
							case "String":
								outputString = info.substring(1, info.length() - 1);
								found = true;
								break;
							case "int":
								outputint = Integer.parseInt(info);
								found = true;
								break;
							case "float":
								outputfloat = Float.parseFloat(info);
								found = true;
								break;
							case "char":
								outputchar = info.charAt(1);
								found = true;
								break;
							case "boolean":
								if(info.equals("true") || info.equals("True")) {
									outputboolean = true;
									found = true;
									break;
								} else if(info.equals("false") || info.equals("False")) {
									outputboolean = false;
									found = true;
									break;
								}
								break;
							case "double":
								outputdouble = Double.parseDouble(info);
								found = true;
								break;
							}
							break;
					}
					if(found) {
						datatoget = null;
						info = null;
						found = false;
						break;
					}
				}
			
			} catch (FileNotFoundException e) {
			
				e.printStackTrace();
			}
		
		
	}
	public void inputpath(String path) {
		inputpath = path;
	}
	public void inputclass(String inclass) {
		datatogetclass = inclass;
	}
	public String getString(String factor) {
		datatoget = factor;
		expectedtype = "String";
		mainreader();
		return outputString;
	}
	public int getInt(String factor) {
		datatoget = factor;
		expectedtype = "int";
		mainreader();
		return outputint;
	}
	public float getFloat(String factor) {
		datatoget = factor;
		expectedtype = "float";
		mainreader();
		return outputfloat;
	}
	public char getChar(String factor) {
		datatoget = factor;
		expectedtype = "char";
		mainreader();
		return outputchar;
	}
	public boolean getBoolean(String factor) {
		datatoget = factor;
		expectedtype = "boolean";
		mainreader();
		return outputboolean;
		
	}
	public double getDouble(String factor) {
		datatoget = factor;
		expectedtype = "double";
		mainreader();
		return outputdouble;
	}
}