package ensta.model.ship;

import ensta.util.Orientation;

public abstract class AbstractShip {
	
	private static char label; 
	private static String name;
	private int length;
	private Orientation orientation;
	
	public void setLabel(char label) {
		this.label = label;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setLength(int length) {
		this.length = length;
	}
	public void setOrientation(Orientation newOrientation) {
		this.orientation = newOrientation;
	}
	
	public char getLabel() {
		return this.label;
	}
	public String getName() {
		return this.name;
	}
	public int getLength() {
		return this.length;
	}
	public Orientation getOrientation() {
		return this.orientation;
	}
	
	public AbstractShip(char label, String name, int length, Orientation orientation) {
		this.label = label;
		this.name = name;
		this.length = length;
		this.orientation = orientation;
	}

}
