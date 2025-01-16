package gameMechanics;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import Files.LoadSettings;
import Files.LoadTextures;
import gameMechanics.Tags.tagdata;
import gameMechanics.Tags.tagtype;

public class Blocks {
	
	public static HashMap<String, defaultblock> blockMap = new HashMap<String, defaultblock>();
	public static HashSet<String> overlaynames = new HashSet<String>();
	public static HashMap<Integer, HashMap<Integer, localblock>> blockatdata = new HashMap<Integer, HashMap<Integer, localblock>>();
	
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
			blockMap.put(name, this);
		}
		public defaultblock(String name, String texturename, tagdata[] defaulttags) {
			this(name, texturename);
			for(tagdata t : defaulttags) {
				if(!localtagmap.containsKey(t.type()))
					localtagmap.put(t.type(), new ArrayList<Object>());
				localtagmap.get(t.type()).add(t.tag());
				if(t.type() == tagtype.overlay) {
					overlaynames.add(name);
				}
			}
			if(!blockMap.containsKey(name))
				blockMap.replace(name, this);
		}
		public String toString() {
			return name;
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
	
	public void setBlock(int x, int y, localblock block) {
		if(blockatdata.containsKey(x)) {
			if(blockatdata.get(x).containsKey(y))
				blockatdata.get(x).replace(y, block);
			blockatdata.get(x).putIfAbsent(y, block);
		}
		else {
			blockatdata.put(x, new HashMap<Integer, localblock>());
			if(blockatdata.containsKey(x))
				blockatdata.get(x).putIfAbsent(y, block);
		}
	}
	
	public String getBlock(int x, int y) {
		if(!(blockatdata.containsKey(x) || blockatdata.get(x).containsKey(y)))
			return null;
		else
			return blockatdata.get(x).get(y).texturename;
	}
	
	public boolean containsBlock(int x, int y) {
		return blockatdata.containsKey(x) && blockatdata.get(x).containsKey(y);
	}
}
