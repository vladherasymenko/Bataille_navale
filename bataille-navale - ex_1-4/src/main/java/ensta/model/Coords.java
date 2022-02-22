package ensta.model;

public class Coords {
	private int x;
	private int y;
	
	public Coords(Coords coords) {
		this.x = coords.getX();
		this.y = coords.getY();
	}
	
	public Coords(int x, int y) {
		this.x = x;
		this.y = y;
		}

	public Coords() {
		this.x = 0;
		this.y = 0;
	}

	public int getX() {
		return this.x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return this.y;
	}

	public void setY(int y) {
		this.y = y;
	}

}
