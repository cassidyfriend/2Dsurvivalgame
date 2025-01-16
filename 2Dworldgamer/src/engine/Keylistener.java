package engine;


import java.awt.event.*;


public class Keylistener {
	TwoD TD = new TwoD();
	public static boolean up;
	public static boolean down;
	public static boolean left;
	public static boolean right;
	public static boolean space;
	public static boolean shift;
	public static boolean ctrl;
	public static boolean EKey;
	public static boolean ESCKey;
	public static boolean EKeyToggle;
	public static boolean ESCKeyToggle;
	public static int CurnentHotKey;
	public static int lastkeypress = -1;
	public static int EKeyval = 0;
	public static int ESCKeyval = 0;
	
	void print(Object o) {
		System.out.println(o);
	}
	
	@SuppressWarnings("static-access")
	public Keylistener() {
		TD.frame.addKeyListener(new KeyAdapter() {
	    	public void keyPressed(KeyEvent e) {
	    		int keyCode = e.getKeyCode();
	    		lastkeypress = keyCode;
	    		if (keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_W) {
	    			up = true;
	    		}
	    		if (keyCode == KeyEvent.VK_DOWN || keyCode == KeyEvent.VK_S) {
	    			down = true;
	    		}
	    		if (keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_A) {
	    			left = true;
	    		}
	    		if (keyCode == KeyEvent.VK_RIGHT || keyCode == KeyEvent.VK_D) {
	    			right = true;
	    		}
	    		if (keyCode == KeyEvent.VK_SPACE) {
	    			space = true;
	    		}
	    		if(keyCode == KeyEvent.VK_SHIFT) {
	    			shift = true;
	    		}
	    		if(keyCode == KeyEvent.VK_CONTROL) {
	    			ctrl = true;
	    		}
	    		if(keyCode == KeyEvent.VK_E) {
	    			EKey = true;
	    			if(EKeyval == 0) EKeyToggle = !EKeyToggle;
	    			EKeyval++;
	    			//print(EKeyToggle);
	    		}
	    		if(keyCode == KeyEvent.VK_ESCAPE) {
	    			ESCKey = true;
	    			if(ESCKeyval == 0) ESCKeyToggle = !ESCKeyToggle;
	    			ESCKeyval++;
	    		}
	    		if(e.getKeyCode() - 48 > 0 && e.getKeyCode() - 48 < 10) {
	      		CurnentHotKey = e.getKeyCode() - 48;
	    		}
	    		if(e.getKeyCode() - 48 == 0) {
	    			CurnentHotKey = 0;
	    		}
	    	}
	    	public void keyReleased(KeyEvent e) {
	    		int keyCode = e.getKeyCode();
	    		lastkeypress = -1;
	    		if (keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_W) {
	    			up = false;
	    		}
	    		if (keyCode == KeyEvent.VK_DOWN || keyCode == KeyEvent.VK_S) {
	    			down = false;
	    		}
	    		if (keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_A) {
	    			left = false;
	    		}
	    		if (keyCode == KeyEvent.VK_RIGHT || keyCode == KeyEvent.VK_D) {
	    			right = false;
	    		}
	    		if (keyCode == KeyEvent.VK_SPACE) {
	    			space = false;
	   		 	}
	    		if(keyCode == KeyEvent.VK_SHIFT) {
	    			shift = false;
	    		}
	    		if(keyCode == KeyEvent.VK_CONTROL) {
	    			ctrl = false;
	    		}
	    		if(keyCode == KeyEvent.VK_E) {
	    			EKey = false;
	    			EKeyval = 0;
	    		}
	    		if(keyCode == KeyEvent.VK_ESCAPE) {
	    			ESCKey = false;
	    			ESCKeyval = 0;
	    		}
	    	}
	  	});
	}
}
