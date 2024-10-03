package Files;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.json.JSONObject;

public class ReadAndWritePlayerDesign {
	
	void print(Object o){
		System.out.println(o);
	}

	public static JSONObject playerdesigndata = new JSONObject();
	String content = "";
	@SuppressWarnings("resource")
	public ReadAndWritePlayerDesign() {
		try {
			content = new Scanner(new File("player/playerdesign.json")).useDelimiter("\\Z").next();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		playerdesigndata = new JSONObject(content);
		playerdesigndata = playerdesigndata.getJSONObject("eye color");
		//playerdesigndata.put("red", 0);
		print(playerdesigndata.toString());
	}
}
