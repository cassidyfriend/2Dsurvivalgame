package physics;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashMap;

public class Point2D {

	public int x = 0, y = 0;
	public static boolean showPoints = false;
	public static HashMap<Point2D, Point2D> pointslist = new HashMap<Point2D, Point2D>();
	public Point2D(int x, int y) {
		this.x = x;
		this.y = y;
		pointslist.put(this, null);
	}
	public void moveto(int x, int y) {
		this.x = x;
		this.y = y;
		pointslist.remove(this);
		pointslist.put(this, null);
	}
	public void add(Point2D point) {
		this.x += point.x;
		this.y += point.y;
		pointslist.remove(this);
		pointslist.put(this, null);
	}
	public void sub(Point2D point) {
		this.x -= point.x;
		this.y -= point.y;
		pointslist.remove(this);
		pointslist.put(this, null);
	}
	public void times(Point2D point) {
		this.x *= point.x;
		this.y *= point.y;
		pointslist.remove(this);
		pointslist.put(this, null);
	}
	public void divide(Point2D point) {
		this.x /= point.x;
		this.y /= point.y;
		pointslist.remove(this);
		pointslist.put(this, null);
	}
	public void modulus(Point2D point) {
		this.x %= point.x;
		this.y %= point.y;
		pointslist.remove(this);
		pointslist.put(this, null);
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
}
