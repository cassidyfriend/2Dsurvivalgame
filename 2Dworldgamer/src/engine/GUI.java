package engine;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

import Files.LoadTextures;
import engine.GUI.locationinslidingbar;

@SuppressWarnings("unused")
public class GUI {

	static HashMap<String, Object> GUIs = new HashMap<String, Object>();
	static Map<Integer, BufferedImage> textures;
	public static ArrayList<textstoredininput> stringinnputs = new ArrayList<textstoredininput>();
	static MouseListerner ML = new MouseListerner();
	static LoadTextures LT;
	static scrollinglistdata sld;
	static BufferedImage framelight, framedark;
	static int starterframesizex, starterframesizey, currentframesizex, currentframesizey;
	public static int fontsize = 25, textsize = 5, lastkey = -1;
	
	@SuppressWarnings("static-access")
	public GUI(int framesizex, int framesizey, LoadTextures LT) {
		this.starterframesizex = framesizex;
		this.starterframesizey = framesizey;
		this.LT = LT;
		textures = LT.textures.get(12).lightTextureMap();
		Map<Integer, BufferedImage> lightmap = LT.textures.get(12).lightTextureMap();
		framelight = lightmap.get(100);
		framedark = lightmap.get(50);
	}
	public GUI() {}
	
	static void print(Object o) {
		System.out.println(o);
	}
	
	BufferedImage createoutline(int width, int height, boolean islight) {
		width = width == 0? 10 : width;
		height = height == 0? 10 : height;
		BufferedImage output = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics g = output.getGraphics();
		if(islight) {
			double countheight = height/framelight.getHeight();
			double increasefor = (int)Math.floor(width/framelight.getWidth());
			double countwidth = 0;
			double firstincreasewith = width/increasefor;
			for(int i = 0; i < increasefor; i++) {
				g.drawImage(framelight.getSubimage(0,0, framelight.getWidth(), 2), (int)countwidth, 0, (int)Math.ceil(firstincreasewith), 2, null);
				countwidth += width/increasefor;
			}
			countwidth = 0;
			for(int i = 0; i < countheight; i++) {
				g.drawImage(framelight.getSubimage(0,0, 2, framelight.getHeight()), 0, (int)countwidth, 2, (int)Math.ceil(height/countheight), null);
				countwidth += height/countheight;
			}
			countwidth = 0;
			for(int i = 0; i < increasefor; i++) {
				g.drawImage(framelight.getSubimage(0,0, framelight.getWidth(), 2), (int)countwidth, height - 2, (int)Math.ceil(firstincreasewith), 2, null);
				countwidth += width/increasefor;
			}
			countwidth = 0;
			for(int i = 0; i < countheight; i++) {
				g.drawImage(framelight.getSubimage(0,0, 2, framelight.getHeight()), width - 2, (int)countwidth, 2, (int)Math.ceil(height/countheight), null);
				countwidth += height/countheight;
			}
		}
		else {
			double countheight = height/framedark.getHeight();
			double increasefor = (int)Math.floor(width/framedark.getWidth());
			double countwidth = 0;
			double firstincreasewith = width/increasefor;
			for(int i = 0; i < increasefor; i++) {
				g.drawImage(framedark.getSubimage(0,0, framedark.getWidth(), 2), (int)countwidth, 0, (int)Math.ceil(firstincreasewith), 2, null);
				countwidth += width/increasefor;
			}
			countwidth = 0;
			for(int i = 0; i < countheight; i++) {
				g.drawImage(framedark.getSubimage(0,0, 2, framedark.getHeight()), 0, (int)countwidth, 2, (int)Math.ceil(height/countheight), null);
				countwidth += height/countheight;
			}
			countwidth = 0;
			for(int i = 0; i < increasefor; i++) {
				g.drawImage(framedark.getSubimage(0,0, framedark.getWidth(), 2), (int)countwidth, height - 2, (int)Math.ceil(firstincreasewith), 2, null);
				countwidth += width/increasefor;
			}
			countwidth = 0;
			for(int i = 0; i < countheight; i++) {
				g.drawImage(framedark.getSubimage(0,0, 2, framedark.getHeight()), width - 2, (int)countwidth, 2, (int)Math.ceil(height/countheight), null);
				countwidth += height/countheight;
			}
		}
		return output;
	}
	
	public class textbutton {
		String text;
		int lox, locy, sizex, sizey;
		boolean background;
		public textbutton(String text, int lox, int locy, int sizex, int sizey, boolean background){
			this.text = text;
			this.lox = applydifx(lox);
			this.locy = applydify(locy);
			this.sizex = applydifx(sizex);
			this.sizey = applydify(sizey);
			this.background = background;
			GUIs.put(text, this);
		}
		@SuppressWarnings("static-access")
		public boolean isClicked(){
			//print(ML.button);
			return (ML.mouseonframex > lox && ML.mouseonframey > locy && ML.mouseonframex < sizex + lox && ML.mouseonframey < sizey + locy && ML.button != 0);
		}
		@SuppressWarnings("static-access")
		int clickedID() {
			return (ML.mouseonframex > lox && ML.mouseonframey > locy && ML.mouseonframex < sizex + lox && ML.mouseonframey < sizey + locy && ML.button != 0 ? ML.button : ML.button);
		}
		@SuppressWarnings("static-access")
		void render(Graphics g){
			if(background) {
				int backgroundcolor = 200, blocklightlevel = backgroundcolor/2;
				backgroundcolor = ML.mouseonframex > lox && ML.mouseonframey > locy && ML.mouseonframex < sizex + lox && ML.mouseonframey < sizey + locy ? 100 : 200;
				g.setColor(new Color(backgroundcolor,backgroundcolor,backgroundcolor));
				g.drawImage(textures.get(backgroundcolor/2), lox, locy, sizex, sizey, null);
				g.fillRect(lox + applydifx(3), locy + applydify(3), sizex - applydifx(6), sizey - applydify(6));
			}
			g.setColor(new Color(0,0,0));
			Font currentfont = getfont(null, Font.PLAIN);
			g.setFont(currentfont);
			FontMetrics metrics = g.getFontMetrics(currentfont);
			g.drawString(text, lox + ((sizex/2) - (metrics.stringWidth(text)/2)), locy + (sizey/2) + ((metrics.getHeight()/2)/2));
		}
		public int hashCode() {
			return text.hashCode();
		}
		public boolean equals(Object o) {
			if(o instanceof textbutton)
				if(o.hashCode() == this.hashCode())
					return true;
			if(o instanceof String)
				if(((String)o).equals(text))
					return true;
			if(o.hashCode() == text.hashCode())
				return true;
			return false;
		}
	}
	
	public class textbox {
		String text;
		int lox, locy, sizex, sizey;
		boolean background;
		textbox(String text, int lox, int locy, int sizex, int sizey, boolean background){
			this.text = text;
			this.lox = applydifx(lox);
			this.locy = applydify(locy);
			this.sizex = applydifx(sizex);
			this.sizey = applydify(sizey);
			this.background = background;
			GUIs.put(text, this);
		}
		void render(Graphics g){
			if(background) {
				g.setColor(new Color(200,200,200));
				g.fillRect(lox, locy, sizex, sizey);
				g.drawImage(textures.get(100), lox, locy, sizex, sizey, null);
			}
			g.setColor(new Color(0,0,0));
			Font currentfont = getfont(null, Font.PLAIN);
			g.setFont(currentfont);
			FontMetrics metrics = g.getFontMetrics(currentfont);
			g.drawString(text, lox + ((sizex/2) - (metrics.stringWidth(text)/2)), locy + (sizey/2) + ((metrics.getHeight()/2)/2));
		}

		public int hashCode() {
			return text.hashCode();
		}
		public boolean equals(Object o) {
			if(o instanceof textbutton)
				if(o.hashCode() == this.hashCode())
					return true;
			if(o instanceof String)
				if(((String)o).equals(text))
					return true;
			return false;
		}
	}
	
	public class image {
		int posx, posy, sizex, sizey;
		BufferedImage image;
		String name;
		boolean framed;
		public image(int posx, int posy, int sizex, int sizey, BufferedImage image, String name, boolean framed) {
			this.posx = applydifx(posx);
			this.posy = applydify(posy);
			this.sizex = applydifx(sizex);
			this.sizey = applydify(sizey);
			this.image = image;
			this.name = name;
			this.framed = framed;
			GUIs.put(name, this);
		}
		void render(Graphics g){
			g.drawImage(image, posx, posy, sizex, sizey, null);
			if(framed)
				g.drawImage(createoutline(sizex, sizey, true), posx, posy, sizex, sizey, null);
		}

		public int hashCode() {
			return image.hashCode();
		}
		public boolean equals(Object o) {
			if(o instanceof image)
				if(o.hashCode() == this.hashCode())
					return true;
			if(o instanceof BufferedImage)
				if(((BufferedImage)o).equals(image))
					return true;
			return false;
		}
	}
	
	public class statusbar{
		
		int posx, posy, sizex, sizey, barheight, fillpercent;
		imagebarfit fittype;
		BufferedImage label;
		Color fillcolor;
		String name;
		
		public enum imagebarfit {
			IMAGETOBAR,
			BARTOIMAGE,
			LOOSE
		}
		
		public statusbar(BufferedImage label, int posx, int posy, int sizex, int sizey, imagebarfit fittype, int fillpercent, Color fillcolor, String name) {
			this.label = label;
			this.posx = posx;
			this.posy = posy;
			this.sizex = sizex;
			this.sizey = sizey;
			this.fittype = fittype;
			this.fillpercent = fillpercent;
			this.fillcolor = fillcolor;
			this.name = name;
			GUIs.put(name, this);
		}
		public statusbar(BufferedImage label, int posx, int posy, int sizex, int sizey, int barheight, int fillpercent, Color fillcolor, String name) {
			this.label = label;
			this.posx = posx;
			this.posy = posy;
			this.sizex = sizex;
			this.sizey = sizey;
			this.barheight = barheight;
			this.fillpercent = fillpercent;
			this.fittype = imagebarfit.LOOSE;
			this.fillcolor = fillcolor;
			this.name = name;
			GUIs.put(name, this);
		}
		void render(Graphics g) {
			if(fittype == imagebarfit.BARTOIMAGE) {
				g.drawImage(label, applydifx(posx), applydify(posy), applydifx(label.getWidth()), applydify(label.getHeight()), null);
				g.drawImage(textures.get(100), applydifx(posx + label.getWidth() + 10), applydify(posy), applydifx(sizex), applydify(label.getHeight()), null);
				g.setColor(new Color(127,127,127));
				g.fillRect(applydifx(posx + label.getWidth() + 13), applydify(posy + 1), applydifx(sizex - 6.0), applydify(label.getHeight() - 2));
				g.setColor(fillcolor);
				g.fillRect(applydifx(posx + label.getWidth() + 13), applydify(posy + 1), applydifx((sizex - 6.0) * ((fillpercent + 0.0)/100.0)), applydify(label.getHeight() - 2));
			}
		}
		public int hashCode() {
			return label.hashCode();
		}
		@SuppressWarnings("unlikely-arg-type")
		public boolean equals(Object o) {
			if(o instanceof statusbar)
				if(o.hashCode() == this.hashCode())
					return true;
			if(o instanceof statusbar)
				if(((statusbar)o).equals(label))
					return true;
			return false;
		}
	}
	
	public class fillintextbox{
		String name;
		int xpos, ypos, xsize, ysize;
		public fillintextbox(int xpos, int ypos, int xsize, int ysize, String name) {
			this.xpos = applydifx(xpos);
			this.ypos = applydify(ypos);
			this.xsize = applydifx(xsize);
			this.ysize = applydify(ysize);
			this.name = name;
			GUIs.put(name, this);
		}
		@SuppressWarnings("static-access")
		public boolean isClicked(){
			return (ML.mouseonframex > xpos && ML.mouseonframey > ypos && ML.mouseonframex < xsize + xpos && ML.mouseonframey < ysize + ypos && ML.button != 0);
		}
		@SuppressWarnings("static-access")
		int clickedID() {
			return (ML.mouseonframex > xpos && ML.mouseonframey > ypos && ML.mouseonframex < xsize + xpos && ML.mouseonframey < ysize + ypos && ML.button != 0 ? ML.button : 0);
		}
		@SuppressWarnings("static-access")
		public boolean isClickedInvertedly(){
			return ((ML.mouseonframex < xpos || ML.mouseonframey < ypos || ML.mouseonframex > xsize + xpos || ML.mouseonframey > ysize + ypos) && ML.button != 0);
		}
		void render(Graphics g) {
			if(clickedID() == 1) {
				if(!stringinnputs.contains(new textstoredininput(name, false)))
					new textstoredininput(name, true);
				else
					stringinnputs.get(stringinnputs.indexOf(new textstoredininput(name, false))).isactive = true;
			}
			if(isClickedInvertedly() && stringinnputs.contains(new textstoredininput(name, false))) {
				stringinnputs.get(stringinnputs.indexOf(new textstoredininput(name, false))).isactive = false;
			}
			g.setColor(new Color(200,200,200));
			g.drawImage(textures.get(100), xpos, ypos, xsize, ysize, null);
			g.fillRect(xpos + applydifx(3), ypos + applydify(3), xsize - applydifx(6), ysize - applydify(6));
			g.setColor(new Color(0,0,0));
			Font currentfont = getfont(null, Font.PLAIN, 20);
			g.setFont(currentfont);
			FontMetrics metrics = g.getFontMetrics(currentfont);
			if(stringinnputs.contains(new textstoredininput(name, false)) && (!stringinnputs.get(stringinnputs.indexOf(new textstoredininput(name, false))).input.equals("")))
				g.drawString(stringinnputs.get(stringinnputs.indexOf(new textstoredininput(name, false))).input, xpos + applydifx(6), ypos + (ysize/2) + ((metrics.getHeight()/2)/2));
			else
				g.drawString(name, xpos + applydifx(6), ypos + (ysize/2) + ((metrics.getHeight()/2)/2));
		}
		public int hashCode() {
			return name.hashCode();
		}
		@SuppressWarnings("unlikely-arg-type")
		public boolean equals(Object o) {
			if(o instanceof fillintextbox)
				if(o.hashCode() == this.hashCode())
					return true;
			if(o instanceof fillintextbox)
				if(((fillintextbox)o).equals(name))
					return true;
			return false;
		}
	}
	
	public class targetbox {
		String name;
		int lox, locy, sizex, sizey, relevantXSize, relevantYSize;
		boolean invisible;
		BufferedImage background;
		targetbox(String name, int lox, int locy, int sizex, int sizey, int relevantXSize, int relevantYSize, boolean invisible, BufferedImage background){
			this.name = name;
			this.lox = (lox);
			this.locy = (locy);
			this.sizex = (sizex);
			this.sizey = (sizey);
			this.relevantXSize = relevantXSize;
			this.relevantYSize = relevantYSize;
			this.invisible = invisible;
			this.background = background;
			GUIs.put(name, this);
		}
		@SuppressWarnings("static-access")
		boolean isClicked(){
			return (ML.mouseonframex > applydifx(lox) &&
					ML.mouseonframey > applydify(locy) &&
					ML.mouseonframex < applydifx(sizex + lox) &&
					ML.mouseonframey < applydify(sizey + locy) &&
					ML.button != 0);
		}

		@SuppressWarnings("static-access")
		int clickedID() {
			return (ML.mouseonframex > applydifx(lox) &&
					ML.mouseonframey > applydify(locy) &&
					ML.mouseonframex < applydifx(sizex + lox) &&
					ML.mouseonframey < applydify(sizey + locy) &&
					ML.button != 0 ? ML.button : 0);
		}
		@SuppressWarnings("static-access")
		int[] getlocationclicked(){
			int output[] = {-1, -1};
			if(isClicked()){
				output[0] = (int) (ML.mouseonframex - applydifx(lox));
				output[1] = (int) (ML.mouseonframey - applydify(locy));
				output[0] = (int) ((output[0] / getframedifx()) / (sizex + 0.0) * relevantXSize);
				output[1] = (int) ((output[1] / getframedify()) / (sizey + 0.0) * relevantYSize);
			}
			return output;
		}
		@SuppressWarnings("static-access")
		int[] getlocationclickedbyID(){
			int output[] = {-1, -1, 0};
			if(isClicked()){
				output[0] = (int) (ML.mouseonframex - applydifx(lox));
				output[1] = (int) (ML.mouseonframey - applydify(locy));
				output[0] = (int) ((output[0] / getframedifx()) / (sizex + 0.0) * relevantXSize);
				output[1] = (int) ((output[1] / getframedify()) / (sizey + 0.0) * relevantYSize);
				output[2] = clickedID();
			}
			return output;
		}
		void render(Graphics g){
			if(!invisible) {
				g.drawImage(createoutline(sizex, sizey, true), applydifx(lox), applydify(locy), applydifx(sizex), applydify(sizey), null);
				g.drawImage(background, applydifx(lox + 2), applydify(locy + 2), applydifx(sizex - 2), applydify(sizey - 2), null);
			}
		}
		public int hashCode() {
			return name.hashCode();
		}
		public boolean equals(Object o) {
			if(o instanceof targetbox)
				if(o.hashCode() == this.hashCode())
					return true;
			if(o instanceof String)
				if(((String)o).equals(name))
					return true;
			//if(o.hashCode() == name.hashCode())
				//return true;
			return false;
		}
	}
	
	public class slidingbar {
		String name;
		int locx, locy, width, height, minvalue, maxvalue, blocksize;
		BufferedImage image;
		locationinslidingbar locationinslidingbar;
		public slidingbar(String name, BufferedImage image, int locx, int locy, int width, int height, int minvalue, int maxvalue, int blocksize) {
			if(width <= 0)
				return;
			 this.name = name;
			 this.locx = (locx);
			 this.locy = (locy);
			 this.width = (width);
			 this.height = (height);
			 this.minvalue = minvalue;
			 this.maxvalue = maxvalue;
			 this.blocksize = blocksize;
			 this.image = image;
			 GUIs.put(name, this);
			 locationinslidingbar = new locationinslidingbar(name, this);
		}
		
		void render(Graphics g) {
			g.drawImage(createoutline(width, height, true), applydifx(locx), applydify(locy), applydifx(width), applydify(height), null);
			g.drawImage(image, applydifx(locx + 2), applydify(locy + 2), applydifx(width - 4), applydify(height - 4), null);
			g.drawImage(framelight.getSubimage(0,0, framelight.getWidth(), 2), getblocklocation(), applydify(locy), applydifx(blocksize), applydify(height - 2), null);
		}
		
		int getblocklocation() {
			return applydifx(2) + (int) (applydifx(locx) + (applydifx(width - 12) * locationinslidingbar.getvalue(name)));
		}

		@SuppressWarnings("static-access")
		boolean isClicked(){
			return (ML.mouseonframex > applydifx(locx) &&
					ML.mouseonframey > applydify(locy) &&
					ML.mouseonframex < applydifx(width + locx) &&
					ML.mouseonframey < applydify(height + locy) &&
					ML.button != 0);
		}

		@SuppressWarnings("static-access")
		int clickedID() {
			return (ML.mouseonframex > applydifx(locx) &&
					ML.mouseonframey > applydify(locy) &&
					ML.mouseonframex < applydifx(width + locx) &&
					ML.mouseonframey < applydify(height + locy) &&
					ML.button != 0 ? ML.button : 0);
		}

		@SuppressWarnings("static-access")
		int[] getlocationclickedbyID(){
			int output[] = {-1, -1, 0};
			if(isClicked()){
				output[0] = (int) (ML.mouseonframex - applydifx(locx));
				output[1] = (int) (ML.mouseonframey - applydify(locy));
				output[2] = clickedID();
			}
			return output;
		}
		public int getslideramount() {
			return (int) (locationinslidingbar.getvalue(name) * (maxvalue - minvalue));
		}
		public int hashCode() {
			return name.hashCode();
		}
		public boolean equals(Object o) {
			if(o instanceof slidingbar)
				if(o.hashCode() == this.hashCode())
					return true;
			if(o instanceof String)
				if(((String)o).equals(name))
					return true;
			if(o.hashCode() == name.hashCode())
				return true;
			return false;
		}
	}
	
	public class scrollingList{
		String name;
		String[] textlist;
		int locX, locY, sizeX, sizeY, elementamount, location;
		int[] amounts;
		BufferedImage[] Icons, Iconslist[];
		public scrollingList(String name, String[] textlist, BufferedImage[] Icons, BufferedImage[][] Iconslist, int amounts[], int locX, int locY, int sizeX, int sizeY, int elementamount) {
			this.name = name;
			this.textlist = textlist;
			this.locX = locX;
			this.locY = locY;
			this.sizeX = sizeX;
			this.sizeY = sizeY;
			this.elementamount = elementamount;
			if(Icons != null)
				this.Icons = Icons;
			else
				this.Iconslist = Iconslist;
			this.amounts = amounts;
			sld.addtoscrollinglist(this);
			GUIs.put(name, this);
			location = sld.getvalue(name);
			for(int i = 0; i < elementamount; i++) {
				if(i + 1 > textlist.length || i + location > textlist.length - 1)
					break;
				if(i < 0 || i + location < 0)
					continue;
				if(Icons == null && Iconslist == null)
					new textbutton(textlist[i + location], locX, locY + (i * (sizeY/elementamount)), sizeX, sizeY/elementamount, true);
				else
					if(Iconslist == null) {
						new inventoryslot(textlist[i + location], amounts[i + location], locX, locY + (i * (sizeY/elementamount)), sizeX, sizeY/elementamount, Icons[i + location]);
					} else {
						new inventoryslot(textlist[i + location], amounts[i + location], locX, locY + (i * (sizeY/elementamount)), sizeX, sizeY/elementamount, Iconslist[i + location]);
			
					}
			}
		}
		void render(Graphics g) {
			g.drawImage(createoutline(sizeX, sizeY, true), applydifx(locX), applydify(locY), applydifx(sizeX), applydify(sizeY), null);
		}
		@SuppressWarnings("static-access")
		boolean isHoveredOver() {
			return (ML.mouseonframex > applydifx(locX) &&
					ML.mouseonframey > applydify(locY) &&
					ML.mouseonframex < applydifx(locX + sizeX) &&
					ML.mouseonframey < applydify(locY + sizeY));
		}
		public int hashCode() {
			return name.hashCode();
		}
		public boolean equals(Object o) {
			if(o instanceof scrollingList)
				if(o.hashCode() == this.hashCode())
					return true;
			if(o instanceof String)
				if(((String)o).equals(name))
					return true;
			if(o.hashCode() == name.hashCode())
				return true;
			return false;
		}
	}
	
	public class inventoryslot{
		String text;
		int lox, locy, sizex, sizey, amount;
		BufferedImage icons[];
		public inventoryslot(String text, int amount, int lox, int locy, int sizex, int sizey, BufferedImage icon[]) {
			this.text = text;
			this.lox = applydifx(lox);
			this.locy = applydify(locy);
			this.sizex = applydifx(sizex);
			this.sizey = applydify(sizey);
			this.amount = amount;
			this.icons = icon;
			GUIs.put(text, this);
		}
		public inventoryslot(String text, int amount, int lox, int locy, int sizex, int sizey, BufferedImage icon) {
			this.text = text;
			this.lox = applydifx(lox);
			this.locy = applydify(locy);
			this.sizex = applydifx(sizex);
			this.sizey = applydify(sizey);
			this.amount = amount;
			this.icons = new BufferedImage[]{icon};
			GUIs.put(text, this);
		}
		@SuppressWarnings("static-access")
		public boolean isClicked(){
			//print(ML.button);
			return (ML.mouseonframex > lox && ML.mouseonframey > locy && ML.mouseonframex < sizex + lox && ML.mouseonframey < sizey + locy && ML.button != 0);
		}
		@SuppressWarnings("static-access")
		int clickedID() {
			return (ML.mouseonframex > lox && ML.mouseonframey > locy && ML.mouseonframex < sizex + lox && ML.mouseonframey < sizey + locy && ML.button != 0 ? ML.button : ML.button);
		}
		@SuppressWarnings("static-access")
		void render(Graphics g){
			int backgroundcolor = 200, blocklightlevel = backgroundcolor/2;
			backgroundcolor = ML.mouseonframex > lox && ML.mouseonframey > locy && ML.mouseonframex < sizex + lox && ML.mouseonframey < sizey + locy ? 100 : 200;
			g.setColor(new Color(backgroundcolor,backgroundcolor,backgroundcolor));
			g.drawImage(createoutline(sizex, sizey, true), lox, locy, sizex, sizey, null);
			g.fillRect(lox + applydifx(1), locy + applydify(1), sizex - applydifx(2), sizey - applydify(2));
			g.setColor(new Color(0,0,0));
			Font currentfont = getfont(null, Font.PLAIN, 20);
			g.setFont(currentfont);
			FontMetrics metrics = g.getFontMetrics(currentfont);
			if (amount < 2)
				g.drawString(text, lox + applydifx(6), locy + (sizey/2) + ((metrics.getHeight()/2)/2));
			else {
				g.drawString("inventory contains: " + Integer.toString(amount) + " of", lox + applydifx(6), locy + (sizey/2) + ((metrics.getHeight()/2)/2));
				g.drawString(text, lox + applydifx(6), locy + (sizey/2) + metrics.getHeight());
			}
			if(icons != null)
				if(icons.length > 1)
					for(int i = 0; i < icons.length; i++)
						g.drawImage(icons[i],
								i == 0? lox + sizex - sizey : lox + sizex - ((sizey / 2) * (i + 1)) - (sizey),
								locy + applydify(2),
								i == 0? sizey - applydify(2) : (sizey / 2) - applydify(2),
								i == 0? sizey - applydify(4) : (sizey / 2) - applydify(4),
								null);
				else
					g.drawImage(icons[0], lox + sizex - (sizey), locy + applydify(2), sizey - applydify(2), sizey - applydify(4), null);
		}
		public int hashCode() {
			return text.hashCode();
		}
		public boolean equals(Object o) {
			if(o instanceof textbutton)
				if(o.hashCode() == this.hashCode())
					return true;
			if(o instanceof String)
				if(((String)o).equals(text))
					return true;
			if(o.hashCode() == text.hashCode())
				return true;
			return false;
		}
	}
	
	class scrollinglistdata{
		static HashMap<String, Integer> scrollinglists = new HashMap<String, Integer>();
		static HashMap<String, scrollingList> scrollinglistdata = new HashMap<String, scrollingList>();
		static ArrayList<String> scrollinglistsarray = new ArrayList<String>();
		
		scrollinglistdata(){}
		
		void addtoscrollinglist(scrollingList input) {
			if(!scrollinglistdata.containsKey(input.name)) {
				scrollinglists.put(input.name, 0);
				scrollinglistdata.put(input.name, input);
				scrollinglistsarray.add(input.name);
			}
		}
		int getvalue(String name) {
			if(scrollinglists.containsKey(name))
				return scrollinglists.get(name);
			return 0;
		}
		@SuppressWarnings("static-access")
		void update() {
			for(scrollingList current : scrollinglistdata.values()) {
				if(current.isHoveredOver()) {
					if(ML.rotation != 0) {
						int currentamount = scrollinglists.get(current.name) + ML.rotation;
						if(currentamount > -1 && currentamount < current.textlist.length - current.elementamount + 1) {
							scrollinglists.remove(current.name);
							scrollinglists.put(current.name, currentamount);
						}
					}
				}
			}
			for(String current : scrollinglistsarray) {
				if(!scrollinglistdata.containsKey(current)) {
					scrollinglists.remove(current);
					scrollinglistdata.remove(current);
				}
			}
			scrollinglistsarray.clear();
		}
	}
	
	class locationinslidingbar{
		static HashMap<String, Double> slidingbarinputs = new HashMap<String, Double>();
		static ArrayList<slidingbar> slidingbars = new ArrayList<slidingbar>();
		locationinslidingbar(String name, slidingbar value){
			addtoslidingbar(name);
			slidingbars.add(value);
		}
		locationinslidingbar(){}
		boolean contains(String name) {
			return slidingbarinputs.containsKey(name);
		}
		double getvalue(String name) {
			if(slidingbarinputs.containsKey(name))
				return slidingbarinputs.get(name);
			return 0.0;
		}
		void addtoslidingbar(String name) {
			if(!slidingbarinputs.containsKey(name)) {
				slidingbarinputs.put(name, 0.5);
			}
		}
		@SuppressWarnings("unlikely-arg-type")
		void update() {
			for(slidingbar current : slidingbars) {
				if(applydifx(current.width) == 0)
					slidingbarinputs.remove(current);
				if(current.clickedID() == 1) {
					double input = (current.getlocationclickedbyID()[0] / getframedifx()) / (current.width + 0.0);
					slidingbarinputs.replace(current.name, input);
				}
			}
			slidingbars.clear();
		}
	}
	
	class textstoredininput{
		String name, input = "";
		boolean isactive = false;
		textstoredininput(String name, boolean add){
			this.name = name;
			//isactive = true;
			if(add)
				stringinnputs.add(this);
		}
		void addtodata(char toadd){
			input += toadd;
		}
		void backspace() {
			input = input.substring(0, input.length() - 1);
		}
		public int hashCode() {
			return name.hashCode();
		}
		void update() {
			if(isactive && lastkey != -1 && KeyEvent.getKeyText(lastkey) == "Backspace" && input.length() != 0) {
				backspace();
				return;
			}
			if(isactive && lastkey != -1)
				addtodata(KeyEvent.getKeyText(lastkey).charAt(0));
		}
		public boolean equals(Object o) {
			if(o instanceof textstoredininput)
				if(((textstoredininput)o).name.equals(name))
					return true;
			if(o instanceof String)
				if(((String)o).equals(name))
					return true;
			if(o.hashCode() == this.hashCode())
				return true;
			return false;
		}
		public String toString() {
			return "name: " + name + "current data: " + input;
		}
	}
	
	@SuppressWarnings("static-access")
	public void update(int framex, int framey, int lastkeypress) {
		this.currentframesizex = framex;
		this.currentframesizey = framey;
		lastkey = lastkeypress;
		textsize = (int)Math.round(fontsize/2);
		//print(lastkeypress);
		locationinslidingbar locationinslidingbar = new locationinslidingbar();
		locationinslidingbar.update();
		if(sld == null)
			sld = new scrollinglistdata();
		sld.update();
		for(textstoredininput current : stringinnputs) {
			current.update();
		}
	}
	public void render(Graphics g) {
		for(Object current : GUIs.values()) {
			switch(current.getClass().getSimpleName()) {
				case "textbox":
					((textbox) current).render(g);
					break;
				case "textbutton":
					((textbutton) current).render(g);
					break;
				case "image":
					((image) current).render(g);
					break;
				case "statusbar":
					((statusbar) current).render(g);
					break;
				case "fillintextbox":
					((fillintextbox) current).render(g);
					break;
				case "targetbox":
					((targetbox) current).render(g);
					break;
				case "slidingbar":
					((slidingbar) current).render(g);
					break;
				case "scrollingList":
					((scrollingList) current).render(g);
					break;
				case "inventoryslot":
					((inventoryslot) current).render(g);
					break;
				default:
					break;
			}
		}
		GUIs.clear();
	}
	public boolean getbuttonclicked(Object o) {
		Object current = null;
		if(GUIs.containsKey(o))
			current = GUIs.get(o);
		else
			return false;
		if(current.hashCode() == o.hashCode())
			if(current instanceof textbutton) {
				//print(ML.button);
				return ((textbutton)current).isClicked();
			}
			else if(current instanceof targetbox) {
				//print(ML.button);
				return ((targetbox)current).isClicked();
			}
			else if(current instanceof inventoryslot) {
				//print(ML.button);
				return ((inventoryslot)current).isClicked();
			}
		return false;
	}
	public int getbuttonclickedID(Object o) {
		if(getbuttonclicked(o))
			if(GUIs.get(o) instanceof textbutton)
				return ((textbutton)GUIs.get(o)).clickedID();
			else if(GUIs.get(o) instanceof targetbox)
				return ((targetbox)GUIs.get(o)).clickedID();
			else if(GUIs.get(o) instanceof inventoryslot)
				return ((inventoryslot)GUIs.get(o)).clickedID();
		return 0;
	}
	public int[] gettargetboxclicked(Object o) {
		Object current = null;
		if(GUIs.containsKey(o))
			current = GUIs.get(o);
		else
			return null;
		if(current.hashCode() == o.hashCode())
			if(current instanceof targetbox) {
				//print(ML.button);
				return ((targetbox)current).getlocationclicked();
			}
		return null;
	}
	public int[] gettargetboxclickedID(Object o) {
		if(getbuttonclicked(o))
			return ((targetbox)GUIs.get(o)).getlocationclickedbyID();
		return null;
	}
	public int getslideramount(Object o) {
		for(Object current : locationinslidingbar.slidingbarinputs.keySet()) {
			if(current.hashCode() == o.hashCode()) {
				Object current2 = GUIs.get(current);
				if(current2 instanceof slidingbar && ((slidingbar)current2).equals(current))
					return ((slidingbar)current2).getslideramount();
			}
		}
		return 0;
	}
	public boolean getButtonClickedInScrollingList(String name, int mousecode, String buttonid) {
		if(name == null || buttonid == null || mousecode == 0)
			return false;
		if(GUIs.containsKey(buttonid) && GUIs.containsKey(name) && GUIs.get(name) instanceof scrollingList) {
			return getbuttonclickedID(buttonid) == mousecode && ((scrollingList)GUIs.get(name)).isHoveredOver();
		}
		return false;
	}
	Font getfont(String name, int style) {
		if(name == null || name.equals(null))
			return new Font(Font.SANS_SERIF, 
					style, 
					(int)Math.round(fontsize*(getframedifx() + getframedify())/2.0));
		return new Font(name, 
				style, 
				(int)Math.round(fontsize*(getframedifx() + getframedify())/2.0));
	}
	Font getfont(String name, int style, int fontsize) {
		if(name == null || name.equals(null))
			return new Font(Font.SANS_SERIF, 
					style, 
					(int)Math.round(fontsize*(getframedifx() + getframedify())/2.0));
		return new Font(name, 
				style, 
				(int)Math.round(fontsize*(getframedifx() + getframedify())/2.0));
	}
	double getframedifx() {
		return (currentframesizex + 0.0)/(starterframesizex + 0.0);
	}
	double getframedify() {
		return (currentframesizey + 0.0)/(starterframesizey + 0.0);
	}
	int applydifx(int input) {
		return (int)Math.round((input + 0.0) * getframedifx());
	}
	int applydifx(double input) {
		return (int)Math.round((input + 0.0) * getframedifx());
	}
	int applydify(int input) {
		return (int)Math.round((input + 0.0) * getframedify());
	}
	int applydify(double input) {
		return (int)Math.round((input + 0.0) * getframedify());
	}
}
