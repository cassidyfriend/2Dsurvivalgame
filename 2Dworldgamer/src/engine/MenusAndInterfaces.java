package engine;

import generator.*;
import datareader.*;
import update.*;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

import Files.*;
import engine.*;
import engine.GUI.textbutton;
import engine.GUI.textstoredininput;
import player.*;

@SuppressWarnings("unused")
public class MenusAndInterfaces {
	static GUI gui;
	static ArrayList<menutypes> menutypelist = new ArrayList<menutypes>();
	static pentypes currentpen = pentypes.PEN;
	static ScrollingBlocks SB;
	static buildworld BW = Manager.BW;
	static SpawnFeatures SF = new SpawnFeatures();
	static TwoD TD;
	static Player player = new Player();
	static BufferedImage colormapimage = null;
	static ReadAndWritePlayerDesign playerdesign = new ReadAndWritePlayerDesign();
	LoadTextures LT;
	static double playerscale = 50;
	enum menutypes{
		MAINMENU,
		NEWWORLD,
		COSTUME
	}
	enum pentypes{
		ERASE,
		PEN
	}
	
	static void print(Object o){
		System.out.println(o);
	}
	
	@SuppressWarnings("static-access")
	public MenusAndInterfaces(menutypes toadd, GUI gui, ScrollingBlocks SB) {
		menutypelist.add(toadd);
		this.gui = gui;
		this.SB = SB;
		if(colormapimage == null)
			colormapimage = makeColorMap();
	}
	public MenusAndInterfaces() {}
	
	@SuppressWarnings("static-access")
	public void updatemenus() {
		for(menutypes current : menutypelist) {
			switch(current) {
			case MAINMENU:
				gui.new textbutton("Single Player", 375, 280, 240, 80, true);
				gui.new textbutton("Settings", 375, 380, 240, 80, true);
				gui.new textbutton("Multiplayer", 645, 280, 240, 80, true);
				gui.new textbutton("Costume Room", 645, 380, 240, 80, true);
				gui.new textbutton("Exit", 520, 480, 240, 80, true);
				//lightmap.get(100);
				//gui.new targetbox("test", 30, 30, 245, 123, 50, 50, false, lightmap.get(100));
				//gui.new slidingbar("test", lightmap.get(100), 30, 30, 245, 123, 0, 50, 10);
				//print(gui.getslideramount("test"));
				break;
			case NEWWORLD:
				gui.new textbutton("Create World", 520, 400, 240, 80, true);
				gui.new fillintextbox(505, 280, 280, 50, "seed:");
				break;
			case COSTUME:
				gui.new textbutton("return to main menu", 520, 570, 240, 80, true);
				gui.new textbutton("set eye color", 50, 150, 240, 80, true);
				gui.new textbutton("set skin color", 50, 250, 240, 80, true);
				gui.new textbutton("set shirt color", 50, 350, 240, 80, true);
				gui.new textbutton("set pants color", 50, 450, 240, 80, true);
				gui.new textbutton("set shoes color", 50, 550, 240, 80, true);
				gui.new slidingbar("color", colormapimage, 20, 10, 1020, 30, 0, 1530, 10);
				Color currentcolor = new Color(colormapimage.getRGB(gui.getslideramount("color") == -1? 0 : gui.getslideramount("color"), 0));
				gui.new slidingbar("saturation", makeSaturationMap(currentcolor), 20, 60, 1020, 30, 0, 255, 10);
				gui.new slidingbar("value", makeValueMap(new Color(makeSaturationMap(currentcolor).getRGB(gui.getslideramount("saturation"), 0))), 20, 110, 1020, 30, 0, 255, 10);
				gui.new image(1050, 10, 130, 130, makeColorDisplay(new Color(makeValueMap(new Color(makeSaturationMap(currentcolor).getRGB(gui.getslideramount("saturation"), 0))).getRGB(gui.getslideramount("value"), 0))));
				gui.new textbutton("eraser", 1050, 150, 130, 40, true);
				gui.new textbutton("pen", 1050, 200, 130, 40, true);
				gui.new targetbox("playeroverlay", 1000, 250, 230, 230, 24, 24, false, player.playeroverlay);
				break;
			default:
				break;
			}
		}
		if(gui.getbuttonclickedID("Single Player") == 1) {
			menutypelist.remove(menutypes.MAINMENU);
			menutypelist.add(menutypes.NEWWORLD);
		}
		else if(gui.getbuttonclickedID("Settings") == 1) {
			//menutypelist.remove(menutypes.MAINMENU);
		}
		else if(gui.getbuttonclickedID("Multiplayer") == 1) {
			//menutypelist.remove(menutypes.MAINMENU);
		}
		else if(gui.getbuttonclickedID("Exit") == 1) {
			System.exit(0);
		}
		else if(gui.getbuttonclickedID("test") == 1) {
			print(Arrays.toString(gui.gettargetboxclicked("test")));
		}
		else if(gui.getbuttonclickedID("return to main menu") == 1) {
			menutypelist.remove(menutypes.COSTUME);
			menutypelist.add(menutypes.MAINMENU);
			player.location = player.location.HIDE;
			playerdesign.writePlayerDesign();
		}
		else if(gui.getbuttonclickedID("Create World") == 1) {
			if(gui.stringinnputs.contains(gui.new textstoredininput("seed:", false))) {
				BW.seed = Math.abs(gui.stringinnputs.get(gui.stringinnputs.indexOf(gui.new textstoredininput("seed:", false))).input.hashCode())/100000;
				print(BW.seed);
				if(BW.seed > 1) {
					BW.updatenoise(Loaddimensions.dimensionsdata.getString("starting dimension"));
					SF.updatenoise(Loaddimensions.dimensionsdata.getString("starting dimension"), BW.seed);
				}
				else {
					BW.updatenoise(Loaddimensions.dimensionsdata.getString("starting dimension"));
					SF.updatenoise(Loaddimensions.dimensionsdata.getString("starting dimension"), BW.seed);
				}
			}
			else {
				BW.updatenoise(Loaddimensions.dimensionsdata.getString("starting dimension"));
				SF.updatenoise(Loaddimensions.dimensionsdata.getString("starting dimension"), BW.seed);
			}
			SB.updateY();
			menutypelist.remove(menutypes.NEWWORLD);
			player.lockmovement = false;
			SB.isInMenu = false;
			player.location = player.location.INGAME;
			playerdesign.readPlayerDesign();
		}
		else if(gui.getbuttonclickedID("Costume Room") == 1) {
			menutypelist.remove(menutypes.MAINMENU);
			menutypelist.add(menutypes.COSTUME);
			playerdesign.readPlayerDesign();
			player.location = player.location.MENU;
		}
		Color currentcolor = new Color(colormapimage.getRGB(gui.getslideramount("color") == -1? 0 : gui.getslideramount("color"), 0));
		if(gui.getbuttonclickedID("set eye color") == 1) {
			player.eyecolor = new Color(makeValueMap(new Color(makeSaturationMap(currentcolor).getRGB(gui.getslideramount("saturation"), 0))).getRGB(gui.getslideramount("value"), 0));
		}
		else if(gui.getbuttonclickedID("set skin color") == 1) {
			player.skincolor = new Color(makeValueMap(new Color(makeSaturationMap(currentcolor).getRGB(gui.getslideramount("saturation"), 0))).getRGB(gui.getslideramount("value"), 0));
		}
		else if(gui.getbuttonclickedID("set shirt color") == 1) {
			player.shirtcolor = new Color(makeValueMap(new Color(makeSaturationMap(currentcolor).getRGB(gui.getslideramount("saturation"), 0))).getRGB(gui.getslideramount("value"), 0));
		}
		else if(gui.getbuttonclickedID("set pants color") == 1) {
			player.pantscolor = new Color(makeValueMap(new Color(makeSaturationMap(currentcolor).getRGB(gui.getslideramount("saturation"), 0))).getRGB(gui.getslideramount("value"), 0));
		}
		else if(gui.getbuttonclickedID("set shoes color") == 1) {
			player.shoecolor = new Color(makeValueMap(new Color(makeSaturationMap(currentcolor).getRGB(gui.getslideramount("saturation"), 0))).getRGB(gui.getslideramount("value"), 0));
		}
		else if(gui.getbuttonclickedID("playeroverlay") == 1) {
		    int penlocation[] = gui.gettargetboxclicked("playeroverlay");
		    BufferedImage overlayImage = player.playeroverlay;
		    if (currentpen == pentypes.PEN) {
		        Graphics2D g2d = overlayImage.createGraphics();
		        g2d.setColor(new Color(makeValueMap(new Color(makeSaturationMap(currentcolor).getRGB(gui.getslideramount("saturation"), 0)))
		            .getRGB(gui.getslideramount("value"), 0)));
		        g2d.fillRect(penlocation[0], penlocation[1], 1, 1);
		        g2d.dispose();
		    } else if (currentpen == pentypes.ERASE) {
		        Graphics2D g2d = overlayImage.createGraphics();
		        g2d.setComposite(AlphaComposite.Clear);
		        g2d.fillRect(penlocation[0], penlocation[1], 1, 1);
		        g2d.dispose();
		    }
		}
		else if(gui.getbuttonclickedID("pen") == 1) {
			currentpen = pentypes.PEN;
		}
		else if(gui.getbuttonclickedID("eraser") == 1) {
			currentpen = pentypes.ERASE;
		}
	}
	public BufferedImage makeColorMap() {
        int width = 255 * 6;
        int height = 1;
        BufferedImage output = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int x = 0; x < width; x++) {
            Color color;
            int segment = x / 255;
            int offset = x % 255;

            switch (segment) {
                case 0:
                    color = new Color(255, offset, 0);
                    break;
                case 1:
                    color = new Color(255 - offset, 255, 0);
                    break;
                case 2:
                    color = new Color(0, 255, offset);
                    break;
                case 3:
                    color = new Color(0, 255 - offset, 255);
                    break;
                case 4:
                    color = new Color(offset, 0, 255);
                    break;
                case 5:
                    color = new Color(255, 0, 255 - offset);
                    break;
                default:
                    color = Color.BLACK;
                    break;
            }
            output.setRGB(x, 0, color.getRGB());
        }
        return output;
    }
	public BufferedImage makeSaturationMap(Color baseColor) {
        int width = 255;
        int height = 1;
        BufferedImage output = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        float[] hsv = Color.RGBtoHSB(baseColor.getRed(), baseColor.getGreen(), baseColor.getBlue(), null);
        for (int x = 0; x < width; x++) {
            float saturation = x / 255f;
            Color color = Color.getHSBColor(hsv[0], saturation, hsv[2]);
            output.setRGB(x, 0, color.getRGB());
        }
        return output;
    }

	public BufferedImage makeValueMap(Color baseColor) {
        int width = 255;
        int height = 1;
        BufferedImage output = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        // Convert the base color to HSV
        float[] hsv = Color.RGBtoHSB(baseColor.getRed(), baseColor.getGreen(), baseColor.getBlue(), null);

        for (int x = 0; x < width; x++) {
            // Set value from 0 to the full brightness of the color across the width
            float value = (x / 255f) * hsv[2];
            Color color = Color.getHSBColor(hsv[0], hsv[1], value);
            output.setRGB(x, 0, color.getRGB());
        }
        return output;
    }
	public BufferedImage makeColorDisplay(Color baseColor) {
		BufferedImage output = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
		Graphics g = output.getGraphics();
		g.setColor(baseColor);
		g.fillRect(0, 0, 1, 1);
		return output;
	}
}