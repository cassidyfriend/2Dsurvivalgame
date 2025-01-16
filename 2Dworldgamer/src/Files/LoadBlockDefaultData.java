package Files;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.json.JSONObject;

import gameMechanics.Blocks;
import gameMechanics.Tags;
import gameMechanics.Tags.tagdata;

import org.json.JSONArray;

public class LoadBlockDefaultData {
	
	void print(Object o) {
		System.out.println(o);
	}
	
	static Blocks b = new Blocks();
	static LoadTextures lt;
	static Tags t = new Tags();

	@SuppressWarnings({ "resource", "static-access" })
	public LoadBlockDefaultData(LoadTextures lt) {
		this.lt = lt;
		String content = "";
		try {
			content = new Scanner(new File("resources/json data/game data jsons/defaultblockdata.json")).useDelimiter("\\Z").next();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		JSONObject blockdata = new JSONObject(content);
		JSONArray blocklist = blockdata.getJSONArray("block data");
		for (int i = 0; i < blocklist.length(); i++) {
			JSONObject block = blocklist.getJSONObject(i);
			if(block.has("tags")) {
				tagdata[] tags = new tagdata[block.getJSONArray("tags").length()];
				for (int j = 0; j < block.getJSONArray("tags").length(); j++) {
					tags[j] = t.createtagfromJSON(block.getJSONArray("tags").getJSONObject(j));
				}
				b.new defaultblock(block.getString("name"), block.getString("texture name"), tags);
			}
			else {
				b.new defaultblock(block.getString("name"), block.getString("texture name"));
			}
		}
		
	}
}
