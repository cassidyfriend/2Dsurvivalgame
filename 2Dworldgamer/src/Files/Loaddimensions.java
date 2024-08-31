package Files;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.json.JSONObject;

public class Loaddimensions {
	public static JSONObject dimensionsdata = new JSONObject();
	@SuppressWarnings("resource")
	public Loaddimensions() {
		String content = "";
		try {
			content = new Scanner(new File("resources/json data/game data jsons/dimensions.json")).useDelimiter("\\Z").next();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		dimensionsdata = new JSONObject(content);
	}
}