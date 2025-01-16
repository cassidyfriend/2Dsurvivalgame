package gameMechanics;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

import gameMechanics.Tags.tagtype;

public class Inventory {
	
	public HashMap<String, item> inventory = new HashMap<String, item>();
	public HashMap<Integer, String> inventorytargets = new HashMap<Integer, String>();
	
	public enum itemtype {
		tool,
		block,
		weapon,
		consumable
	}
	
	public abstract class item {
		public HashMap<tagtype, ArrayList<Object>> localtagmap = new HashMap<tagtype, ArrayList<Object>>();
		public BufferedImage image;
		public String name;
		public itemtype type;
		public int amount = 1;
		
		public item(BufferedImage image, String name, itemtype type){
			this.image = image;
			this.name = name;
			this.type = type;
		}
		
		public abstract void used(int x, int y);
		
		public abstract void used();
		
		//public void attack() {}
		
	}
	
	public class blockItem extends item {
		
		String tagetblock;
		
		public blockItem(BufferedImage image, String name, String tagetblock) {
			super(image, name, itemtype.block);
			this.tagetblock = tagetblock;
		}

		@Override
		public void used(int x, int y) {
		}

		@Override
		public void used() {
		}
	}
	
	public class toolItem extends item {
		public toolItem(BufferedImage image, String name) {
			super(image, name, itemtype.tool);
		}

		@Override
		public void used(int x, int y) {
		}

		@Override
		public void used() {
		}
	}
	
	public class weaponItem extends item {
		public weaponItem(BufferedImage image, String name) {
			super(image, name, itemtype.weapon);
		}

		@Override
		public void used(int x, int y) {
		}

		@Override
		public void used() {
		}
	}
	
	public Inventory(HashMap<Integer, String> defaulttargets) {
		inventorytargets = defaulttargets;
	}
	
	public Inventory(int[] targets, String[] targetids) {
		if(targets.length == targetids.length) {
			for(int i = 0; i < targets.length; i++) {
				inventorytargets.put(targets[i], targetids[i]);
			}
		}
	}
}
