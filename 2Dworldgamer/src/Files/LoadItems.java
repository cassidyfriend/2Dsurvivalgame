package Files;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;

import gameMechanics.Tags;
import gameMechanics.Blocks.defaultblock;
import gameMechanics.Inventory.item;
import gameMechanics.Inventory.itemtype;
import gameMechanics.Tags.tagdata;
import gameMechanics.Tags.tagtype;

public class LoadItems {
	
	public static HashMap<String, blankItem> blankItemTypes;
	static Tags t = new Tags();
	static LoadTextures lt;
	
	public class blankItem {
		public HashMap<tagtype, ArrayList<Object>> localtagmap = new HashMap<tagtype, ArrayList<Object>>();
		public BufferedImage image;
		public String name;
		public itemtype type;
		public int amount = 1;
		
		public blankItem(BufferedImage image, String name, itemtype type){
			this.image = image;
			this.name = name;
			this.type = type;
		}

		public blankItem(BufferedImage image, String name, itemtype type, tagdata[] defaulttags){
			this.image = image;
			this.name = name;
			this.type = type;
			for(tagdata t : defaulttags) {
				if(!localtagmap.containsKey(t.type()))
					localtagmap.put(t.type(), new ArrayList<Object>());
				localtagmap.get(t.type()).add(t.tag());
			}
		}
	}
	
	void print(Object o) {
		System.out.println(o);
	}
	
	public itemtype gettype(String name) {
		switch(name) {
		case "tool":
			return itemtype.tool;
		case "block":
			return itemtype.block;
		case "weapon":
			return itemtype.weapon;
		case "consumable":
			return itemtype.consumable;
		default:
			return null;
		}
	}

	@SuppressWarnings({ "resource", "static-access" })
	public LoadItems(LoadTextures lt) {
		this.lt = lt;
		if(blankItemTypes == null) {
			blankItemTypes = new HashMap<String, blankItem>();
			String content = "";
			try {
				content = new Scanner(new File("resources/json data/game data jsons/itemdata.json")).useDelimiter("\\Z").next();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			JSONObject itemdata = new JSONObject(content);
			JSONArray itemlist = itemdata.getJSONArray("item data");
			for (int i = 0; i < itemlist.length(); i++) {
				JSONObject item = itemlist.getJSONObject(i);
				if(item.has("tags")) {
					for (int j = 0; j < item.getJSONArray("tags").length(); j++) {
						tagdata[] tags = new tagdata[item.getJSONArray("tags").getJSONArray(j).length()];
						for (int k = 0; k < item.getJSONArray("tags").getJSONArray(j).length(); k++) {
							tags[k] = t.createtagfromJSON(item.getJSONArray("tags").getJSONArray(j).getJSONObject(k));
						}
						addItem(lt.texturemap.get(item.getString("texture name")).lightTextureMap().get(100), item.getString("name"), item.getString("type"), tags);
					}
				}
				else
					addItem(lt.texturemap.get(item.getString("texture name")).lightTextureMap().get(100), item.getString("name"), item.getString("type"));
			}
		}
	}
	
	public void addItem(BufferedImage image, String name, itemtype type) {
		blankItemTypes.put(name, new blankItem(image, name, type));
	}
	
	public void addItem(BufferedImage image, String name, String type) {
		blankItemTypes.put(name, new blankItem(image, name, gettype(type)));
	}
	public void addItem(BufferedImage image, String name, String type, tagdata[] tags) {
		blankItemTypes.put(name, new blankItem(image, name, gettype(type), tags));
	}
}
