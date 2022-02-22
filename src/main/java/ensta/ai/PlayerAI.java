package ensta.ai;
import java.util.List;

import ensta.model.Board;
import ensta.model.Coords;
import ensta.model.Hit;
import ensta.model.Player;
import ensta.model.ship.AbstractShip;
import ensta.util.Orientation;

public class PlayerAI extends Player {
    /* **
     * Attribut
     */
	/*
	private Board board;
	protected Board opponentBoard;
	private int destroyedCount;
	protected AbstractShip[] ships;
	private boolean lose;
	*/
    private BattleShipsAI ai;

    /* **
     * Constructeur
     */
    public PlayerAI(Board ownBoard, Board opponentBoard, AbstractShip[] ships) {
        super(ownBoard, opponentBoard, ships);
        ai = new BattleShipsAI(ownBoard, opponentBoard);
    }

 // TODO AIPlayer must not inherit "keyboard behavior" from player. Call ai instead.
    @Override
	public void putShips() {
		ai.putShips(ships);
	}
    @Override
    public Hit sendHit(Coords coords) {
    	sleep(500);
		return ai.sendHit(coords);
	}
    
    private static void sleep(int ms) {
    	try {
    		Thread.sleep(ms);
    	} catch (InterruptedException e) {
    		e.printStackTrace();
    	}
    }
    
}
