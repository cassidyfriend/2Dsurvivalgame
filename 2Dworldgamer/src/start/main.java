package start;


import generator.buildworld;
import generator.spawnstructures;
import datareader.datareadermain;
import engine.TwoD;
import engine.Manager;
import update.Update;
import loadFiles.LoadTextures;


import javax.swing.*;



import java.io.*;
import java.net.URL;
import java.util.*;
import java.awt.*;
import java.awt.image.*;
import javax.imageio.*;
import java.awt.event.*;
import javax.sound.sampled.*;


@SuppressWarnings({ "serial", "unused" })
public class main extends JPanel
{
	public static ArrayList<BufferedImage> images = new ArrayList<BufferedImage>();
	
	public static BufferedImage backdirt;
	public static BufferedImage backgrass;
	public static BufferedImage backstone;
	public static BufferedImage backsand;
	public static BufferedImage dirt;
	public static BufferedImage grass;
	public static BufferedImage sky;
	public static BufferedImage stone;
	public static BufferedImage leaves;
	public static BufferedImage wood;
	public static BufferedImage treeone;
	public static BufferedImage bluetree;
	public static BufferedImage boards;
	public static BufferedImage water;
	public static BufferedImage sand;
	public static BufferedImage enityone;
	public static BufferedImage invintory;
	static boolean makeworld = true;
	static boolean checkblock = true;
	static boolean playsound = true;
	static JFrame f = new JFrame("Window");
	static buildworld b = new buildworld();
	static Update update = new Update();
	static LoadTextures lt = new LoadTextures();
	static TwoD TD = new TwoD();
	static Manager M = new Manager();
	static boolean up;
	static boolean down;
	static boolean right;
	static boolean left;
	static boolean space;
	static boolean foundtextures = false;
	static int placeplack = 1;
	static int x = 0;
	static int y = 0;
	static int framex;
	static int framey;
	static int checkblockx = 0;
	static int checkblocky = 630;
	static int drawx = 0;
	static int drawy = 0;
  	static int whitchblock = 1;
  	static int blockchecker = 300;
  	static int worldhight = 0;
  	static int stoneheght = 0;
  	static int maketree = 1;
  	static int frameH = 0;
  	static int frameblockH = 0;
  	static int frameL = 0;
  	static int frameblockL = 0;
  	static int[] hotbar = {0,1,2,3,4,5,6,7,8,9};
  	static long fpsupdate;
  	RescaleOp backgroundstone = new RescaleOp(0.5f, 0.0f, null);
  	RescaleOp backgrounddirt = new RescaleOp(0.5f, 0.0f, null);
  	RescaleOp backgroundgrass = new RescaleOp(0.5f, 0.0f, null);
  	RescaleOp backgroundsand = new RescaleOp(0.5f, 0.0f, null);
  	public static ArrayList<Integer> blocks = new ArrayList<Integer>();
  	public static ArrayList<Integer> enitydata = new ArrayList<Integer>();
  	public static ArrayList<Integer> secondlayerblocks = new ArrayList<Integer>();
  	public static ArrayList<Integer> tiledata = new ArrayList<Integer>();
  	static Point p = MouseInfo.getPointerInfo().getLocation();
  	static int mousex = p.x;
  	static int mousey = p.y;
  	File audioFile = new File("adiotry2.wav").getAbsoluteFile();
  	String userDirectory = new File("").getAbsolutePath();
    //System.out.println(userDirectory);
  	
  // block size rule 18 on both x and y
 
  @SuppressWarnings({ "deprecation", "unused", "static-access" })
public void paintComponent(Graphics g)
  {
	  
      
      g.drawString(userDirectory, 50, 50);
      //System.out.println(fpsupdate);
      //if(playsound) {
   	   //makesound();
   	   playsound = false;
     // }
   	//System.out.println(b.why);
   	//b.check();
   	   
   	try {
		images.add(ImageIO.read(new File("src/testtextures/gamedirt.png")));
		backdirt = ImageIO.read(new File("src/testtextures/gamedirt.png"));
		backgrass = ImageIO.read(new File("src/testtextures/grasscopy.png"));
		backstone = ImageIO.read(new File("src/testtextures/stone.png"));
		backsand = ImageIO.read(new File("src/testtextures/sand.png"));
		dirt = ImageIO.read(new File("src/testtextures/gamedirt.png"));
		grass = ImageIO.read(new File("src/testtextures/grasscopy.png"));
		sky = ImageIO.read(new File("src/testtextures/skyartwork.png"));
		stone = ImageIO.read(new File("src/testtextures/stone.png"));
		leaves = ImageIO.read(new File("src/testtextures/leaves.png"));
		wood = ImageIO.read(new File("src/testtextures/wood.png"));
		treeone = ImageIO.read(new File("src/testtextures/treetry1.png"));
		boards = ImageIO.read(new File("src/testtextures/boards.png"));
		sand = ImageIO.read(new File("src/testtextures/sand.png"));
		water = ImageIO.read(new File("src/testtextures/water.png"));
		enityone = ImageIO.read(new File("src/testtextures/enity1.png"));
		bluetree = ImageIO.read(new File("src/testtextures/bluefruittree.png"));
		invintory = ImageIO.read(new File("src/testtextures/invintory_slot.png"));
	} catch (IOException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
   	backgrounddirt = new RescaleOp(0.25f, 0.0f, null);
	backgroundstone = new RescaleOp(0.25f, 0.0f, null);
	backgroundgrass = new RescaleOp(0.3f, 0.0f, null);
	backgroundsand = new RescaleOp(0.25f, 0.0f, null);
   	
      if(up) {
   	   y = y + 2;
      	if(y > 18) {
     		 y = 0;
     		 checkblocky++;
      	}  
      }
      if(down) {
   	   y = y + -2;
      	if(y < -18) {
     		 y = 0;
     		 checkblocky--;
      	}
      }
      if(left) {
   	   x = x + 2;
      }
      if(right) {
   	   x = x + -2;
      }
      if(space && checkblockx + (Math.round(frameblockL / 2) * 1000) + checkblocky - (Math.round(frameblockH / 2) - 5) > 1) {
   	   blocks.set(checkblockx + (Math.round(frameblockL / 2) * 1000) + checkblocky - (Math.round(frameblockH / 2) - 5),placeplack);
   	   //blocks.set(checkblockx + (Math.round(frameblockL / 2) * 1000) + checkblocky - (Math.round(frameblockH / 2) - 5),15);
   	   //callupdate();
   	   update.updateBlocks(checkblockx + Math.round(frameblockL / 2), checkblocky - (Math.round(frameblockH / 2) - 5), placeplack);
      }
      //blocks.set(checkblockx + 6000 + checkblocky + 40,1);
      secondlayerblocks.removeAll(secondlayerblocks);
      enitydata.removeAll(enitydata);
      p = MouseInfo.getPointerInfo().getLocation();
      framex = f.location().x;
      framey = f.location().y;
      mousex = p.x - framex;
      mousey = p.y - framey;
      mousey = mousey - 23;
      mousex--;
      
      if(makeworld) {
    	
 		
    	  //M.onstartup();
 		//lt.LoadTextures();
		//b.buildworld();
		//TD.render();
 	
 	
 	
   	   try {
   		 PrintWriter out = new PrintWriter("oceans.txt");
   		for (int i = 0; i < blocks.size(); i++) {
   			out.println(blocks.get(i));
   		}

   		 //System.out.println(blocks.size());
   		 out.close();
   	 } catch (FileNotFoundException e) {
   		 // TODO Auto-generated catch block
   		 e.printStackTrace();
   	 }
   	 
    /*    try {
   		 
   		 FileReader fr = new FileReader("oceans.txt");;
   		 
   		 @SuppressWarnings("resource")
   		 Scanner inFile = new Scanner(fr);
   		 int ghgh = inFile.nextInt();
   		 
   		 //System.out.println(ghgh);
   	 } catch (FileNotFoundException e) {
   		 // TODO Auto-generated catch block
   		 e.printStackTrace();
   	 }*/
        	//Scanner inFile = new Scanner(fr);
   	   
   	   makeworld = false;
   	   
      }
    //RescaleOp rescaleOp = new RescaleOp(0.25f, 0.0f, null);
  	//rescaleOp.filter(dirt, dirt);
    //backgroundblocks.filter(dirt, dirt);
  	
    backgrounddirt.filter(backdirt, backdirt);
    backgroundstone.filter(backstone, backstone);
    backgroundgrass.filter(backgrass, backgrass);
    backgroundsand.filter(backsand, backsand);
      
    blockchecker = checkblocky + checkblockx;
	for(int i = 0; i < frameblockL; i++) {
   	 
   	 for(int mi = 0; mi < frameblockH; mi++) {
   		 if (blockchecker > 1 && blockchecker < (blocks.size() - 1)) {
   			whitchblock = blocks.get(blockchecker);
   		 switch(whitchblock) {
   		 case 1:
   			 g.drawImage(stone, drawx, drawy, null);
   			 break;
   		 case 2:
   			 g.drawImage(dirt, drawx, drawy, null);
   			 break;
   		 case 3:
   			 g.drawImage(grass, drawx, drawy, null);
   			 break;
   		 case 4:
   			 g.drawImage(sky, drawx, drawy, null);
   			 break;
   		 case 5:
   			 g.drawImage(sky, drawx, drawy, null);
   			 secondlayerblocks.add(1);
   			 tiledata.add(1);
   			 secondlayerblocks.add(drawx);
   			 secondlayerblocks.add(drawy);
   			 
   			 break;
   		 case 6:
   			 g.drawImage(leaves, drawx, drawy, null);
   			 break;
   		 case 7:
   			 g.drawImage(wood, drawx, drawy, null);
   			 break;
   		 case 8:
   			 g.drawImage(boards, drawx, drawy, null);
   			 break;
   		 case 9:
   			 g.drawImage(sky, drawx, drawy, null);
   			 enitydata.add(1);
   			 enitydata.add(drawx);
   			 enitydata.add(drawy);
   			 enitydata.add(0);
   			 enitydata.add(0);
   			 break;
   		 case 0:
   			 g.drawImage(sky, drawx, drawy, null);
   			 secondlayerblocks.add(2);
   			 tiledata.add(2);
   			 secondlayerblocks.add(drawx);
   			 secondlayerblocks.add(drawy);
   			 break;
   		 case 11:
   			g.drawImage(backdirt, drawx, drawy, null);
   			break;
   		 case 12:
   			g.drawImage(backstone, drawx, drawy, null);
   			 break;
   		 case 13:
   			g.drawImage(backgrass, drawx, drawy, null);
   			 break;
   		 case 14:
   			g.drawImage(backsand, drawx, drawy, null);
   			 break;
   		 case 15:
   			g.drawImage(sand, drawx, drawy, null);
   			 break;
   		 case 16:
   			g.drawImage(water, drawx, drawy, null);
   			 break;
   		 }
   		 }
   		 else {
   			g.drawImage(sky, drawx, drawy, null);
   		 }
   		 if(blockchecker == checkblockx + (Math.round(frameblockL / 2) * 1000) + checkblocky - (Math.round(frameblockH / 2) - 5)) {
   			 g.drawRect(drawx - 1, drawy - 1, 18, 18);
   		 }
   		 if(x < -19) {
         		 x = -2;
         		 checkblockx = checkblockx + 1000;
          	}
   		 if(x > 19) {
       		 x = 2;
       		 checkblockx = checkblockx - 1000;
        	}
   		 blockchecker--;
   		 //whitchblock = blocks.get(blockchecker);
   		 //g.drawImage(stone, drawx, drawy, null);
   		 
   		 drawy = drawy + 18;
   	 }
   	 blockchecker = blockchecker + 1000 + frameblockH;
   	 drawx = drawx + 18;
   	 drawy = -50 + y;
	}
	//g.drawImage(leaves, mousex, mousey, null);
	drawx = -70 + x;
	int repeatesecount = 0;
	//System.out.println(secondlayerblocks.size());
	for (int i = 0; i < secondlayerblocks.size() / 3; i++) {
   	   switch(secondlayerblocks.get(repeatesecount)) {
   	   case 1:
   		   g.drawImage(treeone, secondlayerblocks.get(repeatesecount + 1) - 35, secondlayerblocks.get(repeatesecount + 2) - 72, null);    
   		   repeatesecount = repeatesecount + 3;
   		   break;
   	   case 2:
   		   g.drawImage(bluetree, secondlayerblocks.get(repeatesecount + 1) - 35, secondlayerblocks.get(repeatesecount + 2) - 72, null);    
   		   repeatesecount = repeatesecount + 3;
   		   break;
   	   }
   	 }
	int repeate_enity = 0;
	for (int i = 0; i < enitydata.size() / 5; i++) {
   	 int entiyx = 0;
   	 int entiyy = 0;
   	 switch(enitydata.get(repeate_enity)) {
   	 case 1:
   		 g.drawImage(enityone, enitydata.get(repeate_enity + 1) + enitydata.get(repeate_enity + 3), enitydata.get(repeate_enity + 2) + enitydata.get(repeate_enity + 4), null);
   		 repeate_enity = repeate_enity + 3;
   		 break;
   	 }
	}
	//System.out.println(hotbar[0]);
	int ivtory = (Math.round(frameL / 2) - 50);
	for (int i = 0; i < 10; i++) {
		switch(hotbar[i]) {
	    case 1:
	   	 g.drawImage(stone, ivtory + 5, 12, null);
	   	 break;
	    case 2:
	     g.drawImage(dirt, ivtory + 5, 12, null);
	   	 break;
	    case 3:
	   	 g.drawImage(grass, ivtory + 5, 12, null);
	   	 break;
	    case 4:
	   	 g.drawImage(sky, ivtory + 5, 12, null);
	   	 break;
	    case 5:
	   	 g.drawImage(treeone, ivtory + 5, 12, null);
	   	 break;
	    case 6:
	   	 g.drawImage(leaves, ivtory + 5, 12, null);
	   	 break;
	    case 7:
	   	 g.drawImage(wood, ivtory + 5, 12, null);
	   	 break;
	    case 8:
	   	 g.drawImage(boards, ivtory + 5, 12, null);
	   	 break;
	    case 9:
	   	 g.drawImage(enityone, ivtory + 5, 12, null);
	   	 break;
	    case 0:
	   	 g.drawImage(bluetree, ivtory + 5, 12, null);
	   	 break;
	    case 11:
   			g.drawImage(backdirt, ivtory + 5, 12, null);
   			break;
   		 case 12:
   			g.drawImage(backstone, ivtory + 5, 12, null);
   			 break;
   		 case 13:
   			g.drawImage(backgrass, ivtory + 5, 12, null);
   			 break;
   		 case 14:
   			g.drawImage(backsand, ivtory + 5, 12, null);
   			 break;
   		 case 15:
   			g.drawImage(sand, ivtory + 5, 12, null);
   			 break;
   		 case 16:
   			g.drawImage(water, ivtory + 5, 12, null);
   			 break;
	    }
		g.drawImage(invintory, ivtory + 3, 10, null);
		ivtory += 22;
		}
	frameH = f.getHeight();
	frameL = f.getWidth();
	frameblockH = (Math.round(frameH / 18) + 10);
	frameblockL = (Math.round(frameL / 18) + 10);
	g.drawImage(images.get(0), 50, 50, null);
	if (foundtextures == true) {
		//g.drawString("found!", 100, 100);
	}
	//foundtextures = false;
	if (foundtextures == false) {
		//g.drawString("not found!", 100, 100);
	}
	//System.out.println(Math.round(frameblockH / 2) - 5);
	//g.drawImage(invintory, 100, 10, null);
    
	//g.drawImage(treeone, 500, 300, null);
	//g.drawRect(579, 345, 19, 19);
    
	//System.out.println(wood);
	//g.drawImage(leaves, mousex, mousey, null);
	//f.toFront();
   // f.requestFocus();
	
	
	//RescaleOp rescaleOp = new RescaleOp(1.2f, -50, null);
	//rescaleOp.filter(dirt, dirt);
	//g.drawImage(dirt, 50, 50, null);
	g.drawString(userDirectory, 50, 50);
	//repaint();
	
	long start = System.currentTimeMillis();
	try {
		Thread.sleep((System.currentTimeMillis() - start) + 8);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
  }



  @SuppressWarnings({ "deprecation", "static-access" })
public static void main(String [] args){
	  //System.out.println("test");
      M.onstartup();
  }



@SuppressWarnings("unused")
private void play(String audioFilePath) {
    // TODO Auto-generated method stub
    
}

private void moveBall() throws InterruptedException   {

	// TODO Auto-generated method stub

	//x = x + 1;

	//y = y + 1;

	if(x > 500) {

    	x = 0;

	}

	Thread.sleep(2);
    

}

@SuppressWarnings("unused")
private void callupdate() {
    //checkblockx + 36000 + checkblocky - 22
    int which = 0;
    int block = 0;
    for(int i = 0; i < 4; i++) {
   	 which++;
   	 if(which == 1) {
   		 //System.out.println(blocks.get(checkblockx + 35000 + checkblocky - 22));
   		 if(blocks.get(checkblockx + ((Math.round(frameblockL / 2) * 1000) - 1000) + checkblocky - 22) == 5 && blocks.get(checkblockx + ((Math.round(frameblockL / 2) * 1000) - 1000) + checkblocky - ((Math.round(frameblockH / 2) - 5) + 1)) == 4) {
   			 blocks.set(checkblockx + 35000 + checkblocky - 22, 4);
   		 }
   	 }
   	 if(which == 2) {
   		 //System.out.println("2");
   		 if(blocks.get(checkblockx + ((Math.round(frameblockL / 2) * 1000) + 1000) + checkblocky - 22) == 5 && blocks.get(checkblockx + ((Math.round(frameblockL / 2) * 1000) + 1000) + checkblocky - ((Math.round(frameblockH / 2) - 5) + 1)) == 4) {
   			 blocks.set(checkblockx + 37000 + checkblocky - 22, 4);
   		 }
   	 }
   	 if(which == 3) {
   		 //System.out.println("3");
   		 /*if(blocks.get(checkblockx + 36000 + checkblocky - 23) == 5 && blocks.get(checkblockx + 36000 + checkblocky - 22) == 4) {
   			 blocks.set(checkblockx + 36000 + checkblocky - 23, 4);
   		 }*/
   	 }
   	 if(which == 4) {
   		 //System.out.println("4");
   		 /*if(blocks.get(checkblockx + 36000 + checkblocky - 21) == 5 && blocks.get(checkblockx + 36000 + checkblocky - 20) == 4) {
   			 blocks.set(checkblockx + 36000 + checkblocky - 21, 4);
   		 }*/
   	 }
    }
}
@SuppressWarnings("unused")
private void makesound(){
    try {
   	 AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(audioFile);
   	 Clip clip = AudioSystem.getClip();
   	 clip.open(audioInputStream);
   	 
   		   clip.start();
   		 
   	 
   	 
    } catch (UnsupportedAudioFileException e1) {
   	 // TODO Auto-generated catch block
   	 e1.printStackTrace();
    } catch (IOException e1) {
   	 // TODO Auto-generated catch block
   	 e1.printStackTrace();
	 } catch (LineUnavailableException e) {
   	 // TODO Auto-generated catch block
   	 e.printStackTrace();
    }
}


}