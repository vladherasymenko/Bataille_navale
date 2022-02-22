package ensta.model.ship;

import ensta.util.Orientation;

public class Destroyer extends AbstractShip {
	public Destroyer(char label, String name, int length, Orientation orientation) {
		super(label, name, length, orientation);
	}
	private char label; 
	private String name;
	
	public Destroyer() {
		super('D', "Destroyer", 2, Orientation.randomOrientation());
	}
}
