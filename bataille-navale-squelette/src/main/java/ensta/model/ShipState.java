package ensta.model;

import ensta.model.ship.AbstractShip;
import ensta.util.ColorUtil;

public class ShipState {
	private AbstractShip ship;
	private boolean struck;
	
	public ShipState(AbstractShip ship) {
		this.ship = ship;
		this.struck = false;
	}
	
	public void addStrike() {
		if(!this.ship.isSunk() && !this.struck) {
			ship.addStrike();
			this.struck = true;
		}
		else {
			System.out.println("Ce navire est déjà détruit ou a été déjà touché dans cet endoit-là");
		}
	}
	
	public boolean isStruck() {
		return this.struck;
	}
	
	public boolean isSunk() {
		return this.ship.isSunk();
	}
	
	public char getShipLabel() {
		return this.ship.getLabel();
	}
	
	public String toString() {
		if(!this.struck) {
			return Character.toString(this.ship.getLabel());
		}
		return (ColorUtil.colorize(Character.toString(this.ship.getLabel()), ColorUtil.Color.RED));
	}
}
