package ensta.model.ship;

import ensta.util.Orientation;

public class Submarine extends AbstractShip{
	public Submarine(char label, String name, int length, Orientation orientation) {
		super(label, name, length, orientation);
	}
	private char label; 
	private String name;
	
	public Submarine() {
		super('S', "Submarine", 3, Orientation.randomOrientation());
	}
}
