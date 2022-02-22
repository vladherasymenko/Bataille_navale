package ensta.model.ship;

import ensta.util.Orientation;

public class Destroyer extends AbstractShip {
	public Destroyer(char label, String name, int length, Orientation orientation) {
		super(label, name, length, orientation);
	}
	private static char label = 'D'; 
	private static String name = "Destroyer";
}
