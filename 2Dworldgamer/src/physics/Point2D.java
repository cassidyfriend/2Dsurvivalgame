package physics;

import java.awt.Color;
import java.awt.Graphics;
import java.util.HashMap;

public class Point2D {

	public int x = 0, y = 0;
	public static int statingframex = 0, statingframey = 0, currentframex = 0, currentframey = 0;
	public static boolean showPoints = false;
	public static HashMap<Point2D, Point2D> pointslist = new HashMap<Point2D, Point2D>();
	public Point2D(int x, int y) {
		this.x = x;
		this.y = y;
		pointslist.put(this, null);
	}
	@SuppressWarnings("static-access")
	public Point2D(int statingframex, int statingframey, boolean passfalse) {
		this.statingframex = statingframex;
		this.statingframey = statingframey;
	}
	public Point2D(Graphics g, int frameX, int frameY) {
		render(g, frameX, frameY);
	}
	public Point2D(Graphics g) {
		render(g);
	}
	public Point2D() {}
	void print(Object o) {
		System.out.println(o);
	}
	public void setcurrentframesize(int frameX, int frameY) {
		currentframex = frameX;
		currentframey = frameY;
	}
	public void moveto(int x, int y) {
		pointslist.remove(this);
		this.x = x;
		this.y = y;
		pointslist.put(this, null);
	}
	public void add(Point2D point) {
		pointslist.remove(this);
		this.x += point.x;
		this.y += point.y;
		pointslist.put(this, null);
	}
	public void sub(Point2D point) {
		pointslist.remove(this);
		this.x -= point.x;
		this.y -= point.y;
		pointslist.put(this, null);
	}
	public void times(Point2D point) {
		pointslist.remove(this);
		this.x *= point.x;
		this.y *= point.y;
		pointslist.put(this, null);
	}
	public void divide(Point2D point) {
		pointslist.remove(this);
		this.x /= point.x;
		this.y /= point.y;
		pointslist.put(this, null);
	}
	public void modulus(Point2D point) {
		pointslist.remove(this);
		this.x %= point.x;
		this.y %= point.y;
		pointslist.put(this, null);
	}
	public void remove() {
		pointslist.remove(this);
	}
	public void remove(Point2D point) {
		pointslist.remove(point);
	}
	@Override
    public int hashCode() {
        int result = 17; 
        result = 31 * result + x;
        result = 31 * result + y;
        return result;
    }
	@Override
    public boolean equals(Object input) {
		return input instanceof Point2D && ((Point2D)input).x == this.x && ((Point2D)input).y == this.y;
	}
	public void render(Graphics g, int frameX, int frameY) {
		if(showPoints) {
			for(Point2D point : pointslist.keySet()) {
				int xs[] = {point.x + frameX, point.x + 5 + frameX};
				int ys[] = {point.y + frameY, point.y - (5 + frameY)};
				g.setColor(Color.RED);
				g.drawLine(xs[0], ys[0], xs[1], ys[0]);
				g.setColor(Color.GREEN);
				g.drawLine(xs[0], ys[0], xs[0], ys[1]);
			}
		}
	}
	public void render(Graphics g) {
		if(showPoints) {
			for(Point2D point : pointslist.keySet()) {
				int xs[] = {applydifx(point.x), applydifx(point.x + 5)};
				int ys[] = {applydify(point.y), applydify(point.y - 5)};
				g.setColor(Color.RED);
				g.drawLine(xs[0], ys[0], xs[1], ys[0]);
				g.setColor(Color.GREEN);
				g.drawLine(xs[0], ys[0], xs[0], ys[1]);
			}
		}
	}
	double getframedifx() {
		return (currentframex + 0.0)/(statingframex + 0.0);
	}
	double getframedify() {
		//statingframey
		return (currentframey + 0.0)/(statingframey + 0.0);
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
