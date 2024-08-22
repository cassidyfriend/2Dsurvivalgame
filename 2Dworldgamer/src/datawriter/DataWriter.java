package datawriter;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class DataWriter {
	String location;
	File input;
	Scanner fr;
	boolean shouldmessagelog;
	overheadclass currentclasshandle;
	ArrayList<overheadclass> classlist = new ArrayList<overheadclass>();
	
	class variable {
		public String name;
		public String data;
		variable(String name, String data){
			this.name = name;
			this.data = data;
			hashCode();
			toString();
		}
		public int hashCode() {
			return name.hashCode();
		}
		public String toString(){
			return name + " = " + data;
		}
		public boolean equals(Object obj) {
	        if (this == obj)
	        	return true;
	        if (!(obj instanceof variable))
	        	return false;
	        variable other = (variable) obj;
	        return this.name.equals(other.name);
	    }
	}
	class overheadclass {
		public ArrayList<variable> variablelist = new ArrayList<variable>();
		String name;
		overheadclass(String classname){
			this.name = classname;
			hashCode();
		}
		
		
		void add(variable input) {
			variablelist.add(input);
		}
		boolean remove(variable input) {
			if(variablelist.contains(input)) {
				variablelist.remove(input);
				return true;
			}
			return false;
		}
		void set(variable input) {
			if(variablelist.contains(input)) {
				variablelist.set(variablelist.indexOf(input), input);
			}
		}
		
		
		public int hashCode() {
			return name.hashCode();
		}
		public String toString() {
			String output = name + ":{\n";
			for(variable current : variablelist) {
				output += current.toString() + "\n";
			}
			return output + "}\n";
		}
		public boolean equals(Object obj) {
	        if (this == obj)
	        	return true;
	        if (!(obj instanceof overheadclass))
	        	return false;
	        overheadclass other = (overheadclass) obj;
	        return this.name.equals(other.name);
	    }
	}
	
	public DataWriter(boolean messagelog) {
		shouldmessagelog = messagelog;
	}
	void print(Object input) {
		if(shouldmessagelog)
			System.out.println(input);
	}
	void error(Object input) {
		if(shouldmessagelog)
			System.out.println("\u001B[31m"+input+" "+"\u001B[0m");
	}
	public boolean loadfile(String location) {
		this.location = location;
		try {
			input = new File(location);
			unpackfile(input);
			loadVarsAndClasses(fr);
			return true;
		}
		catch(Exception e) {
			e.printStackTrace();
			error("could not load file");
			return false;
		}
	}
	void unpackfile(File input) throws FileNotFoundException {
		fr = new Scanner(input);
	}
	String removechar(String input, int amount) {
		String output = input;
		for(int i = 0; i < amount + 1; i++)
			output = output.substring(0, input.length()-i);
		return output;
	}
	void loadVarsAndClasses(Scanner input) {
		int type = 1;
		overheadclass currentclass = null;
		String currentbestclassname = null;
		String varname = null;
		String data = null;
		while(input.hasNext()) {
			String current = input.next();
			switch(type) {
			case 1:
				if(current.charAt(current.length()-1) == '{' && current.charAt(current.length()-2) == ':') {
					currentbestclassname = removechar(current, 2);
					currentclass = new overheadclass(currentbestclassname);
					type = 4;
				} else
				if(current.charAt(current.length()-1) == ':') {
					currentbestclassname = removechar(current, 1);
					type = 3;
				} else {
					currentbestclassname = current;
					type = 2;
				}
				break;
			case 2:
				if(current.equals(":{")) {
					currentclass = new overheadclass(currentbestclassname);
					type = 4;
				}
				if(current.equals(":")) {
					type = 3;
				}
				break;
			case 3:
				if(current.equals("{")) {
					currentclass = new overheadclass(currentbestclassname);
					type = 4;
				} else {
					print("Bad syntax! assumed that " + currentbestclassname + " is of type class but is not fallowed by the proper syntax. to do this write it as \"classname:{\"");
					return;
				}
				break;
			case 4:
				
				if(current.equals("}")) {
					type = 1;
					classlist.add(currentclass);
				} else {
					varname = current;
					type = 5;
				}
				break;
			case 5:
				if(current.equals("=")) {
					type = 6;
				} else {
					print("Bad syntax! assumed that " + varname + " is of type variable but does not have a value to set to. this occured in class: " + currentclass.name);
					return;
				}
				break;
			case 6:
				data = current;
				type = 4;
				variable currentvar = new variable(varname, data);
				currentclass.add(currentvar);
				break;
			}
		}
	}
	public void addclass(String classname) {
		overheadclass currenrtclass = new overheadclass(classname);
		classlist.add(currenrtclass);
	}
	public boolean removeclass(String classname) {
	    overheadclass currenrtclass = new overheadclass(classname);
	    if (classlist.contains(currenrtclass)) {
	        classlist.remove(currenrtclass);
	        return true;
	    }
	    return false;
	}
	public void setclass(String classname) {
		overheadclass currentclass = new overheadclass(classname);
		if(classlist.contains(currentclass))
			currentclasshandle = classlist.get(classlist.indexOf(currentclass));
	}
	public void addvariable(String name, String data) {
		currentclasshandle.add(new variable(name, data));
	}
	public boolean removevariable(String name) {
		variable currentvar = new variable(name, null);
		return currentclasshandle.remove(currentvar);
	}
	public void setvariable(String name, String data) {
		variable currentvar = new variable(name, data);
		currentclasshandle.set(currentvar);
	}
	public boolean overwrite() {
		String output = classlist.get(0).toString();
		Boolean loadedfirst = false;
		for(overheadclass current : classlist) {
			if(!loadedfirst) {
				loadedfirst = true;
				continue;
			}
			output += current.toString();
		}
		try {
			FileWriter fWriter = new FileWriter(location);
			fWriter.write(output);
			fWriter.close();
			return true;
		}catch (IOException e) {
			return false;
		}
	}
}