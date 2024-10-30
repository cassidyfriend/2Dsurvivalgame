package Files;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import javax.imageio.ImageIO;

import org.json.JSONObject;

import player.Player;

public class ReadAndWritePlayerDesign {
	
	Player player = new Player();
	
	void print(Object o){
		System.out.println(o);
	}

	public static JSONObject playerdesigndata = new JSONObject();
	String content = "";
	
	
	public ReadAndWritePlayerDesign() {}
	
	@SuppressWarnings({ "resource", "static-access" })
	public void readPlayerDesign() {
		int defaultgameplayersize = player.defaultgameplayersize;
		try {
			content = new Scanner(new File("player/playerdesign.json")).useDelimiter("\\Z").next();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			player.playeroverlay = ImageIO.read(new File("player/playeroverlay.png"));
			if(player.playeroverlay == null) {
				player.playeroverlay = new BufferedImage(defaultgameplayersize, defaultgameplayersize, BufferedImage.TYPE_INT_ARGB);
			}
		} catch (IOException e) {
			player.playeroverlay = new BufferedImage(defaultgameplayersize, defaultgameplayersize, BufferedImage.TYPE_INT_ARGB);
			e.printStackTrace();
		}
		playerdesigndata = new JSONObject(content);
		JSONObject cuyrrentplayerelement = playerdesigndata;
		playerdesigndata = cuyrrentplayerelement.getJSONObject("eye color");
		player.eyecolor = JSONColorToColor(playerdesigndata);
		playerdesigndata = cuyrrentplayerelement.getJSONObject("skin color");
		player.skincolor = JSONColorToColor(playerdesigndata);
		playerdesigndata = cuyrrentplayerelement.getJSONObject("shirt color");
		player.shirtcolor = JSONColorToColor(playerdesigndata);
		playerdesigndata = cuyrrentplayerelement.getJSONObject("pants color");
		player.pantscolor = JSONColorToColor(playerdesigndata);
		playerdesigndata = cuyrrentplayerelement.getJSONObject("shoe color");
		player.shoecolor = JSONColorToColor(playerdesigndata);
	}
	@SuppressWarnings("static-access")
	public void writePlayerDesign() {
		JSONObject playerdesigndata = new JSONObject();
		playerdesigndata.put("eye color", colorToJSON(player.eyecolor));
		playerdesigndata.put("skin color", colorToJSON(player.skincolor));
		playerdesigndata.put("shirt color", colorToJSON(player.shirtcolor));
		playerdesigndata.put("pants color", colorToJSON(player.pantscolor));
		playerdesigndata.put("shoe color", colorToJSON(player.shoecolor));
		try {
			FileWriter myWriter = new FileWriter("player/playerdesign.json");
		    myWriter.write(playerdesigndata.toString());
		    myWriter.close();
        } catch (IOException e) {
            System.err.println("An error occurred while writing to file: " + e.getMessage());
        }
		try {
			ImageIO.write(player.playeroverlay, "png", new File("player/playeroverlay.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	JSONObject colorToJSON(Color input) {
		if(input == null)
			return null;
		JSONObject output = new JSONObject();
		output.put("red", input.getRed());
		output.put("green", input.getGreen());
		output.put("blue", input.getBlue());
		return output;
	}
	Color JSONColorToColor(JSONObject input) {
		if(input == null)
			return null;
		if(!(input.has("red") && input.has("green") && input.has("blue")))
			return null;
		return new Color(input.getInt("red"), input.getInt("green"), input.getInt("blue"));
	}
}
