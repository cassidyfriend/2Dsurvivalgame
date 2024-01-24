package datawriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class DataWriter {
	static String path = "player/saves/save1/world"+"/";
	static String setfile;
	static String currentpath;
	static int lineplacement = 0;
	static int inplacement;
	public static void DataWriter() {
		//makefile("myfile","firstclass");
		setfilename("myfile"+".txt");
		getinfileclassname("secontclass");
		try {
			backupfile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//try {
			//write("myvar = 5");
		//} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		//}
	}
	//player/saves/save1/world/filename.txt
	@SuppressWarnings("static-access")
	public void setpath(String path) {
		this.path = path;
		
	}
	public static void makefile(String filename,String mainclassname) {
		currentpath = path + filename + ".txt";
		try {
		      File myObj = new File(currentpath);
		      if (myObj.createNewFile()) {} else {}
		      FileWriter myWriter = new FileWriter(currentpath);
		      myWriter.write(mainclassname + ":{");
		      myWriter.write("\n}");
		      myWriter.close();
		    } catch (IOException e) {
		      e.printStackTrace();
		    }
	}
	public static void setfilename(String filename) {
		setfile = filename;
		currentpath = path + filename;
	}
	public static void getinfileclassname(String classname) {
		try {
		      File myObj = new File(currentpath);
		      Scanner myReader = new Scanner(myObj);
		      boolean shouldIstop = false;
		      int classline = 0;
		      int caseplacement = 1;
		      while (myReader.hasNext()) {
		        String data = myReader.next();
		        classline++;
		        switch(caseplacement) {
		        case 1:
		        	if(data.equals(classname + ":{")) {
		        		shouldIstop = true;
		        	} else 
		        	if(data.equals(classname + ":")) {
		        		caseplacement = 3;
			        } else
			        if(data.equals(classname)) {
			        	caseplacement = 2;
			        }
		        	break;
		        case 2:
		        	if(data.equals(":")) {
		        		caseplacement = 3;
			        } else
			        if(data.equals(":{")) {
			        	shouldIstop = true;
			        }
		        	break;
		        case 3:
		        	if(data.equals("{")) {
		        		shouldIstop = true;
			        }
		        	break;
		        }
		        //System.out.println(classline);
		        if(shouldIstop) {
		        	inplacement = classline;
		        	myReader.close();
		        	break;
		        }
		        //System.out.println(data);
		      }
		      myReader.close();
		    } catch (FileNotFoundException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		    }
	}
	public static void backupfile() throws IOException {
		try {
		      File myObj = new File(currentpath);
		      Scanner myReader = new Scanner(myObj);
		      FileWriter myWriter = new FileWriter("src/datawriter/backup.txt");
		      while (myReader.hasNextLine()) {
		        String data = myReader.nextLine();
		        myWriter.write(data + "\n");
		        //System.out.println("Successfully wrote to the file.");
		      }
		      myWriter.close();
		      myReader.close();
		    } catch (FileNotFoundException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		    }
	}
	public static void write(String data) throws IOException {
		FileWriter myWriter = new FileWriter(currentpath);
		File myObj = new File("src/datawriter/backup.txt");
	    Scanner myReader = new Scanner(myObj);
		for (int i = 0; i < inplacement; i++) {
			String partdata = myReader.nextLine();
			myWriter.write(partdata); 
		}
		myWriter.write("\n"+data);
		myWriter.close();
	}
}
