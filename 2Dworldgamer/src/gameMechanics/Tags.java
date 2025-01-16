package gameMechanics;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import Files.LoadSettings;
import Files.LoadTextures;

@SuppressWarnings("unused")
public class Tags {
	
	public Tags() {}
	@SuppressWarnings("static-access")
	public Tags(LoadTextures lt) {
		this.lt = lt;
	}
	
	void print(Object o) {
		System.out.println(o);
	}
	
	static LoadTextures lt;
	static Blocks b = new Blocks();
	
	public enum tagtype {
		overlay,
		inventoryBlock
	}
	
	public record tagdata(tagtype type, Object tag) {}
	
	
	public abstract class defaultOverlay {
		public Map<Integer, BufferedImage> Overlayimage = LoadTextures.texturemap.get("error").lightTextureMap();
		public double XOffset = 0, YOffset = 0, ScaleX = 1, ScaleY = 1;
		public defaultOverlay(Map<Integer, BufferedImage> Overlayimage, double XOffset, double YOffset, double ScaleX, double ScaleY) {
			if(Overlayimage != null) {
				this.Overlayimage = Overlayimage;
				this.XOffset = XOffset;
				this.YOffset = YOffset;
				this.ScaleX = ScaleX;
				this.ScaleY = ScaleY;
			}
		}
	}
	public class Overlay extends defaultOverlay {
		public Overlay(Map<Integer, BufferedImage> Overlayimage, double XOffset, double YOffset, double ScaleX, double ScaleY) {
			super(Overlayimage, XOffset, YOffset, ScaleX, ScaleY);
		}
	}
	
	public class inventoryBlock{
		public boolean allowedinInventory = false;
		public inventoryBlock() {
		}
	}
	
	@SuppressWarnings("unchecked")
	public tagdata createTag(tagtype type, Object args[]) {
		switch (type) {
			case overlay:
				if(args[0] instanceof String &&
						args[1] instanceof Map &&
						args[2] instanceof Double &&
						args[3] instanceof Double &&
						args[4] instanceof Double &&
						args[5] instanceof Double) {
					Overlay o = new Overlay((Map<Integer, BufferedImage>) args[1], (double)args[2], (double)args[3], (double)args[4], (double)args[5]);
					return new tagdata(type, o);
				}
			case inventoryBlock:
				if(args[0] instanceof String) {
					inventoryBlock b = new inventoryBlock();
					return new tagdata(type, b);
				}
			default:
				return null;
		}
	}
	@SuppressWarnings("static-access")
	public tagdata createtagfromJSON(JSONObject data) {
		if(data.has("type")) {
			switch(data.getString("type")) {
			case "overlay":
				tagdata tag = new tagdata(tagtype.overlay,new Overlay(
						lt.texturemap.get(data.getString("texture name")).lightTextureMap(),
						data.getDouble("XOffset"),
						data.getDouble("YOffset"),
						data.getDouble("ScaleX"),
						data.getDouble("ScaleY")
						));
				return tag;
			case "notAllowedInInventory":
				tag = new tagdata(tagtype.inventoryBlock, new inventoryBlock());
				return tag;
			default:
				return null;
			}
		}
		return null;
	}
}
