package ensta.model.ship;

import ensta.util.Orientation;

public class BattleShip extends AbstractShip{
	public BattleShip(char label, String name, int length, Orientation orientation) {
		super(label, name, length, orientation);
		
	}
	private char label; 
	private String name;
	
	public BattleShip() {
		super('B', "Battleship", 4, Orientation.randomOrientation());
	}
}
