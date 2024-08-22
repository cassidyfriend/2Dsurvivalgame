package Files;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.json.JSONObject;

import engine.*;

public class LoadSettings {
	public JSONObject settingsdata = new JSONObject();
	@SuppressWarnings("resource")
	public LoadSettings() {
		String content = "";
		try {
			content = new Scanner(new File("player/settings.json")).useDelimiter("\\Z").next();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		settingsdata = new JSONObject(content);
		MouseListerner.mouseclickstimeout = settingsdata.getInt("mouseclicktimeout");
	}
}
