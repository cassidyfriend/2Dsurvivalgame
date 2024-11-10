package Files;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import javax.imageio.ImageIO;

import org.json.JSONObject;

import engine.*;

public class LoadSettings {
	public JSONObject settingsdata = new JSONObject();
	public static BufferedImage errorimage;
	
	void print(Object s) {
		System.out.println(s);
	}
	
	@SuppressWarnings("resource")
	public LoadSettings() {
		String content = "";
		try {
			content = new Scanner(new File("player/settings.json")).useDelimiter("\\Z").next();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		settingsdata = new JSONObject(content);
		try {
			errorimage = ImageIO.read(new File(settingsdata.getString("error image")));
		} catch (IOException e) {
			print("error loading error image file");
			System.exit(-1);
		}
		MouseListerner.mouseclickstimeout = settingsdata.getInt("mouseclicktimeout");
	}
}
