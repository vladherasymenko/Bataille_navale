package ensta.model;

import java.io.Serializable;
import java.util.List;

import ensta.model.ship.AbstractShip;
import ensta.util.Orientation;
import ensta.view.InputHelper;

public class Player implements Serializable{
	/*
	 * ** Attributs
	 */
	private Board board;
	protected Board opponentBoard;
	private int destroyedCount;
	protected AbstractShip[] ships;
	private boolean lose;


	/*
	 * ** Constructeur
	 */
	public Player(Board board, Board opponentBoard, AbstractShip[] ships) {
		this.setBoard(board);
		this.ships = ships;
		this.opponentBoard = opponentBoard;
	}

	/*
	 * ** Méthodes
	 */

	/**
	 * Read keyboard input to get ships coordinates. Place ships on given
	 * coodrinates.
	 */
	public void putShips() {
		boolean done = false;
		int i = 0;

		do {
			AbstractShip ship = ships[i];
			String msg = String.format("placer %d : %s(%d)", i + 1, ship.getName(), ship.getLength());
			System.out.println(msg);
			InputHelper.ShipInput res;
			
		    res = InputHelper.readShipInput();
			// set ship orientation
			ship.setOrientation(Orientation.stringToOrientation(res.orientation));
			// TODO put ship at given position
			boolean shipPlaced = board.putShip(ship, new Coords(res.x, res.y-1));
			// TODO when ship placement successful
			if(shipPlaced) {
				++i;
				done = i == 5;
			}
			board.print();
		} while (!done);
	} 

	public Hit sendHit(Coords coords) {
		boolean done = false;
		Hit hit = null;

		do {
			System.out.println("où frapper?");
			InputHelper.CoordInput hitInput = InputHelper.readCoordInput();
			Coords impendingHit = new Coords(hitInput.x, hitInput.y);
			if(impendingHit.isInBoard(board.getSize())) {
				if(board.getHits()[impendingHit.getY()][impendingHit.getX()] == null) {
					hit = opponentBoard.sendHit(impendingHit);
					done = true;
					coords.setCoords(impendingHit);
				}
				else{
					System.out.println("Vous avez déjà frappé cette case ! ");
				}

			}
			else {
				System.out.println("Veuillez entrer les coordonnées valides !");
			}
			
		} while (!done);

		return hit;
	}

	public AbstractShip[] getShips() {
		return ships;
	}

	public void setShips(AbstractShip[] ships) {
		this.ships = ships;
	}

	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}

	public int getDestroyedCount() {
		return destroyedCount;
	}

	public void setDestroyedCount(int destroyedCount) {
		this.destroyedCount = destroyedCount;
	}

	public boolean isLose() {
		return lose;
	}

	public void setLose(boolean lose) {
		this.lose = lose;
	}
}
