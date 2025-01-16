package engine;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.awt.*;
import java.awt.event.*;

@SuppressWarnings("static-access")
public class MouseListerner {

    static TwoD TD = new TwoD();
    static Point p = MouseInfo.getPointerInfo().getLocation();
    static BuildButtons BB = new BuildButtons();
    public static int mousex = p.x, mousey = p.y, button = 0, mouseclickstimeout = 0, rotation = 0;
    static int mouseonframex = 0, mouseonframey = 0;
    private static ScheduledExecutorService scheduler;
    public static int holdCounter = 0;
    public static final int HOLD_THRESHOLD = 10;

    static void print(Object o) {
        System.out.println(o);
    }

    static {
        scheduler = Executors.newSingleThreadScheduledExecutor();
        TD.frame.addMouseListener(new MouseListener() {
            @Override
            public void mousePressed(MouseEvent mouseinput) {
                if (mouseinput.getButton() == MouseEvent.BUTTON1) {
                    button = 1;
                } else if (mouseinput.getButton() == MouseEvent.BUTTON2) {
                    button = 2;
                } else if (mouseinput.getButton() == MouseEvent.BUTTON3) {
                    button = 3;
                } else {
                    button = 0;
                }
                scheduler.schedule(() -> {
                    if (holdCounter == 10) {
                        button = 0;
                    }
                }, mouseclickstimeout, TimeUnit.MILLISECONDS);
            }

            @Override
            public void mouseReleased(MouseEvent mouseinput) {
                button = 0;
                holdCounter = 0; // Reset counter on release
            }

            @Override
            public void mouseEntered(MouseEvent mouseinput) {}
            @Override
            public void mouseExited(MouseEvent mouseinput) {}
            @Override
            public void mouseClicked(MouseEvent mouseinput) {}
        });

        TD.frame.addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                rotation = e.getWheelRotation();
                scheduler.schedule(() -> {
                    rotation = 0;
                }, mouseclickstimeout, TimeUnit.MILLISECONDS);
            }
        });
    }

    @SuppressWarnings({ "deprecation" })
    public static void onupdate() {
        p = MouseInfo.getPointerInfo().getLocation();
        mousex = p.x;
        mousey = p.y;
        mouseonframex = (mousex - TD.frame.location().x) - 8;
        mouseonframey = (mousey - TD.frame.location().y) - 31;

        // Increment the hold counter if a button is being pressed
        if (button != 0) {
            holdCounter++;
        }
    }

    // Method to return the current held button if it has been held for more than one frame
    public static int getHeldButton() {
        if (holdCounter > HOLD_THRESHOLD) {
            return button;
        } else {
            return 0; // Return 0 if no button is held for more than one frame
        }
    }
}
