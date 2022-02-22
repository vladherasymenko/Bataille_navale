package ensta.model;

import java.io.Serializable;
import java.util.Random;

public class Coords implements Serializable{
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

	public static Coords randomCoords(int size) {
	
		Random random = new Random();
		int x;
		do {
			x = random.nextInt(size);
		} while(x > size && x < 0);
		
		int y;
		do {
			y = random.nextInt(size);
		} while(y > size && y < 0);
		
		return new Coords(x,y);
	}

	public void setCoords(Coords res) {
		this.x = res.getX();
		this.y = res.getY();
	}

	public boolean isInBoard(int size) {
		if(size > this.getX() && size > this.getY() && this.getY()>=0 && this.getX() >=0) {
			return true;
		}
		return false;
	}

}
