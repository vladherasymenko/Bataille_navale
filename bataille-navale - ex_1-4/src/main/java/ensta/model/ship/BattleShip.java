package ensta.model.ship;

import ensta.util.Orientation;

public class BattleShip extends AbstractShip{
	public BattleShip(char label, String name, int length, Orientation orientation) {
		super(label, name, length, orientation);
		
	}
	private static char label = 'B'; 
	private static String name = "Battleship";
}
