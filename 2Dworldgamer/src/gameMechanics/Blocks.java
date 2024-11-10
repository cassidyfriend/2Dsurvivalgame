package gameMechanics;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Files.LoadSettings;
import Files.LoadTextures;
import gameMechanics.Tags.tagdata;
import gameMechanics.Tags.tagtype;

public class Blocks {
	
	public static HashMap<String, localblock> blockMap = new HashMap<String, localblock>();
	public static HashMap<String, String> overlaynames = new HashMap<String, String>();
	
	public Blocks() {
		
	}
	public BufferedImage gettextureByName(String name) {
		BufferedImage output = LoadSettings.errorimage;
		return output;
	}
	
	public class defaultblock {
		public String texturename = "error";
		String name = "error";
		public HashMap<tagtype, ArrayList<Object>> localtagmap = new HashMap<tagtype, ArrayList<Object>>();
		
		public defaultblock(String name, String texturename) {
			if(name != null && texturename != null) {
				this.texturename = texturename;
				this.name = name;
			}
		}
		public defaultblock(String name, String texturename, tagdata[] defaulttags) {
			this(name, texturename);
			for(tagdata t : defaulttags) {
				if(!localtagmap.containsKey(t.type()))
					localtagmap.put(t.type(), new ArrayList<Object>());
				localtagmap.get(t.type()).add(t.tag());
				if(t.type() == tagtype.overlay) {
					overlaynames.put(name, name);
				}
			}
		}
	}
	
	public class localblock extends defaultblock {
		public localblock(String name, String texturename) {
			super(name, texturename);
			blockMap.put(name, this);
		}
		public localblock(String name, String texturename, tagdata[] defaulttags) {
			super(name, texturename, defaulttags);
			blockMap.put(name, this);
		}
		public void addTag(tagdata tag) {
			localtagmap.get(tag.type()).add(tag.tag());
		}
	}
}
