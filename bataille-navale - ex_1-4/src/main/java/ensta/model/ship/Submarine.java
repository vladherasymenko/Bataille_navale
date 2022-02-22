package ensta.model.ship;

import ensta.util.Orientation;

public class Submarine extends AbstractShip{
	public Submarine(char label, String name, int length, Orientation orientation) {
		super(label, name, length, orientation);
	}
	private static char label = 'S'; 
	private static String name = "Submarine";
}
