package physics;

public class Vector2D {
	public int x = 0, y = 0;
	public Vector2D(int x, int y) {
		this.x = x;
		this.y = y;
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
		return input instanceof Vector2D && ((Vector2D)input).x == this.x && ((Vector2D)input).y == this.y;
	}
}