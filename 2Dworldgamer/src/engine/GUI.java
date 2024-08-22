package engine;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Map;

import Files.LoadTextures;

public class GUI {

	static ArrayList<Object> GUIs = new ArrayList<Object>();
	static ArrayList<Map<Integer, BufferedImage>> textures;
	public static ArrayList<textstoredininput> stringinnputs = new ArrayList<textstoredininput>();
	static MouseListerner ML = new MouseListerner();
	LoadTextures LT;
	int starterframesizex, starterframesizey;
	int currentframesizex, currentframesizey;
	public int fontsize = 25, textsize = 5, lastkey = -1;
	
	@SuppressWarnings("static-access")
	public GUI(int framesizex, int framesizey, LoadTextures LT) {
		this.starterframesizex = framesizex;
		this.starterframesizey = framesizey;
		this.LT = LT;
		textures = LT.textures;
	}
	public GUI() {}
	
	static void print(Object o) {
		System.out.println(o);
	}
	
	public class textbutton {
		String text;
		int lox, locy, sizex, sizey;
		boolean background;
		textbutton(String text, int lox, int locy, int sizex, int sizey, boolean background){
			this.text = text;
			this.lox = lox;
			this.locy = locy;
			this.sizex = sizex;
			this.sizey = sizey;
			this.background = background;
			GUIs.add(this);
		}
		@SuppressWarnings("static-access")
		public boolean isClicked(){
			return (ML.mouseonframex > lox && ML.mouseonframey > locy && ML.mouseonframex < sizex + lox && ML.mouseonframey < sizey + locy && ML.button != 0);
		}
		@SuppressWarnings("static-access")
		int clickedID() {
			return (ML.mouseonframex > lox && ML.mouseonframey > locy && ML.mouseonframex < sizex + lox && ML.mouseonframey < sizey + locy && ML.button != 0 ? ML.button : ML.button);
		}
		@SuppressWarnings("static-access")
		void render(Graphics g){
			lox = applydifx(lox);
			locy = applydify(locy);
			sizex = applydifx(sizex);
			sizey = applydify(sizey);
			if(background) {
				@SuppressWarnings("unused")
				int backgroundcolor = 200, blocklightlevel = backgroundcolor/2;
				backgroundcolor = ML.mouseonframex > lox && ML.mouseonframey > locy && ML.mouseonframex < sizex + lox && ML.mouseonframey < sizey + locy ? 100 : 200;
				g.setColor(new Color(backgroundcolor,backgroundcolor,backgroundcolor));
				Map<Integer, BufferedImage> lightmap = textures.get(26);
				g.drawImage(lightmap.get(backgroundcolor/2), lox, locy, sizex, sizey, null);
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
			this.lox = lox;
			this.locy = locy;
			this.sizex = sizex;
			this.sizey = sizey;
			this.background = background;
			GUIs.add(this);
		}
		void render(Graphics g){
			lox = applydifx(lox);
			locy = applydify(locy);
			sizex = applydifx(sizex);
			sizey = applydify(sizey);
			if(background) {
				Map<Integer, BufferedImage> lightmap = textures.get(26);
				g.setColor(new Color(200,200,200));
				g.fillRect(lox, locy, sizex, sizey);
				g.drawImage(lightmap.get(100), lox, locy, sizex, sizey, null);
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
		int posx, posy;
		BufferedImage image;
		public image(int posx, int posy, BufferedImage image) {
			this.posx = posx;
			this.posy = posy;
			this.image = image;
			GUIs.add(this);
		}
		void render(Graphics g){
			posx = applydifx(posx);
			posy = applydify(posy);
			g.drawImage(image, posx, posy, (int)Math.round(image.getWidth() * getframedifx()), (int)Math.round(image.getHeight() * getframedify()), null);
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
		
		public enum imagebarfit {
			IMAGETOBAR,
			BARTOIMAGE,
			LOOSE
		}
		
		public statusbar(BufferedImage label, int posx, int posy, int sizex, int sizey, imagebarfit fittype, int fillpercent, Color fillcolor) {
			this.label = label;
			this.posx = posx;
			this.posy = posy;
			this.sizex = sizex;
			this.sizey = sizey;
			this.fittype = fittype;
			this.fillpercent = fillpercent;
			this.fillcolor = fillcolor;
			GUIs.add(this);
		}
		public statusbar(BufferedImage label, int posx, int posy, int sizex, int sizey, int barheight, int fillpercent, Color fillcolor) {
			this.label = label;
			this.posx = posx;
			this.posy = posy;
			this.sizex = sizex;
			this.sizey = sizey;
			this.barheight = barheight;
			this.fillpercent = fillpercent;
			this.fittype = imagebarfit.LOOSE;
			this.fillcolor = fillcolor;
			GUIs.add(this);
		}
		void render(Graphics g) {
			if(fittype == imagebarfit.BARTOIMAGE) {
				g.drawImage(label, applydifx(posx), applydify(posy), applydifx(label.getWidth()), applydify(label.getHeight()), null);
				Map<Integer, BufferedImage> lightmap = textures.get(26);
				g.drawImage(lightmap.get(100), applydifx(posx + label.getWidth() + 10), applydify(posy), applydifx(sizex), applydify(label.getHeight()), null);
				g.setColor(new Color(127,127,127));
				g.fillRect(applydifx(posx + label.getWidth() + 13), applydify(posy + 1), applydifx(sizex - 6.0), applydify(label.getHeight() - 2));
				g.setColor(fillcolor);
				g.fillRect(applydifx(posx + label.getWidth() + 13), applydify(posy + 1), applydifx((sizex - 6.0) * ((fillpercent + 0.0)/100.0)), applydify(label.getHeight() - 2));
			}
		}
		public int hashCode() {
			return label.hashCode();
		}
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
			this.xpos = xpos;
			this.ypos = ypos;
			this.xsize = xsize;
			this.ysize = ysize;
			this.name = name;
			GUIs.add(this);
		}
		@SuppressWarnings("static-access")
		public boolean isClicked(){
			return (ML.mouseonframex > xpos && ML.mouseonframey > ypos && ML.mouseonframex < xsize + xpos && ML.mouseonframey < ysize + ypos && ML.button != 0);
		}
		@SuppressWarnings("static-access")
		int clickedID() {
			return (ML.mouseonframex > xpos && ML.mouseonframey > ypos && ML.mouseonframex < xsize + xpos && ML.mouseonframey < ysize + ypos && ML.button != 0 ? ML.button : ML.button);
		}
		void render(Graphics g) {
			xpos = applydifx(xpos);
			ypos = applydify(ypos);
			xsize = applydifx(xsize);
			ysize = applydify(ysize);
			if(clickedID() == 1) {
				if(!stringinnputs.contains(new textstoredininput(name, false)))
					new textstoredininput(name, true);
				else
					stringinnputs.get(stringinnputs.indexOf(new textstoredininput(name, false))).isactive = true;
			}
			Map<Integer, BufferedImage> lightmap = textures.get(26);
			g.setColor(new Color(200,200,200));
			g.drawImage(lightmap.get(100), xpos, ypos, xsize, ysize, null);
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
		void update() {
			
		}
		public int hashCode() {
			return name.hashCode();
		}
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
	class textstoredininput{
		String name, input = "";
		boolean isactive = false;
		textstoredininput(String name, boolean add){
			this.name = name;
			isactive = true;
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
	
	public void update(int framex, int framey, int lastkeypress) {
		this.currentframesizex = framex;
		this.currentframesizey = framey;
		lastkey = lastkeypress;
		textsize = (int)Math.round(fontsize/2);
		//print(lastkeypress);
		for(textstoredininput current : stringinnputs) {
			current.update();
		}
	}
	public void render(Graphics g) {
		for(Object current : GUIs) {
			if(current instanceof textbox) {
				((textbox) current).render(g);
			}
			if(current instanceof textbutton) {
				((textbutton) current).render(g);
			}
			if(current instanceof image) {
				((image) current).render(g);
			}
			if(current instanceof statusbar) {
				((statusbar) current).render(g);
			}
			if(current instanceof fillintextbox) {
				((fillintextbox) current).render(g);
			}
		}
		GUIs.clear();
	}
	public boolean getbuttonclicked(Object o) {
		for(Object current : GUIs) {
			if(current.hashCode() == o.hashCode())
				if(current instanceof textbutton)
					return ((textbutton)current).isClicked();
		}
		return false;
	}
	public int getbuttonclickedID(Object o) {
		for(Object current : GUIs) {
			if(getbuttonclicked(o))
				return ((textbutton)current).clickedID();
		}
		return 0;
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
}
