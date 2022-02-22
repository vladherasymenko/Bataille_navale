package ensta.model.ship;

import ensta.util.Orientation;

public class Carrier extends AbstractShip{
	public Carrier(char label, String name, int length, Orientation orientation) {
		super(label, name, length, orientation);
	}
	private static char label = 'C'; 
	private static String name = "Carrier";
}
