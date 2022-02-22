package ensta.controller;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import ensta.ai.BattleShipsAI;
import ensta.ai.PlayerAI;
import ensta.model.Board;
import ensta.model.Coords;
import ensta.model.Hit;
import ensta.model.Player;
import ensta.model.ship.AbstractShip;
import ensta.model.ship.BattleShip;
import ensta.model.ship.Carrier;
import ensta.model.ship.Destroyer;
import ensta.model.ship.Submarine;
import ensta.util.ColorUtil;
import ensta.util.Orientation;
import ensta.view.InputHelper;
import ensta.view.InputHelper.ShipInput;

public class Game {

	/*
	 * *** Constante
	 */
	public static final File SAVE_FILE = new File("savegame.dat");

	/*
	 * *** Attributs
	 */
	private Player player1;
	private Player player2;
	private Scanner sin;
	private boolean multiplayer;

	/*
	 * *** Constructeurs
	 */
	public Game(boolean multiplayer) {
		this.multiplayer = multiplayer;
	}
	
	private static void addShip(char label,String name, Board currentBoard) {
		ShipInput currentShip;
		Orientation currentOrientation;
		Coords currentCoords;
		boolean stop;
		do {
			System.out.println("Entrez les coordonnées et l'orientation de " + name);
			currentShip = InputHelper.readShipInput();
			currentOrientation = Orientation.stringToOrientation(currentShip.orientation);
			currentCoords = new Coords(currentShip.x, currentShip.y-1);
			if(label == 'C') {
				Carrier navire = new Carrier('C', name, 5, currentOrientation);
				stop = currentBoard.putShip(navire, currentCoords);
			}
			else if(label == 'B') {
				BattleShip navire = new BattleShip('B', name, 4, currentOrientation);
				stop = currentBoard.putShip(navire, currentCoords);
			}
			else if(label == 'D') {
				Destroyer navire = new Destroyer('D', name, 3, currentOrientation);
				stop = currentBoard.putShip(navire, currentCoords);
			}
			else if(label == 'S') {
				Submarine navire = new Submarine('S', name, 2, currentOrientation);
				stop = currentBoard.putShip(navire, currentCoords);
			}
			else {
				System.out.println("Choississez un type valable");
				return; // TODO Exception
			}
		} while(stop == false); 
	}
	
	public Game init() {
		if (!loadSave()) {
			
			// TODO init boards
			Board boardPlayer1 = new Board("Player1");
			Board boardPlayer2 = new Board("Player2");
			
			// TODO init this.player1 & this.player2
			AbstractShip[] shipsPlayer1 = createDefaultShips();
			this.player1 = new Player(boardPlayer1, boardPlayer2, shipsPlayer1);
			
			AbstractShip[] shipsPlayer2 = createDefaultShips();
			if(multiplayer) {
				this.player2 = new Player(boardPlayer2, boardPlayer1, shipsPlayer2);
			}
			else {
				this.player2 = new PlayerAI(boardPlayer2, boardPlayer1, shipsPlayer2);
			}
			
			// TODO place player ships
			System.out.println("Joueur " + boardPlayer1.getName() + ", placez vos navires.");
			player1.putShips();
			
			if(multiplayer) {
				System.out.println("\n\n\n\n\n\n\n\n\n\n\n\nPassez l'ordinateur au joueur " + boardPlayer2.getName());
				System.out.println("\n\n\n\n\n\n\n\n\n\n\n\nJoueur " + boardPlayer2.getName() + ", placez vos navires.");
			}
			player2.putShips();
			
			System.out.println("                   " + boardPlayer1.getName());
			boardPlayer1.print();
			System.out.println("                   " + boardPlayer2.getName());
			boardPlayer2.print();
			
			System.out.println("\n\n\n\n\n\n\n\n\nTous les navires ont été placés. \n\nBranle-bas de combat !");
			
			
		}
		return this;
	}

	/*
	 * *** Méthodes
	 */
	public void run() {
		Coords coords = new Coords();
		Board b1 = player1.getBoard();
		Board b2 = player2.getBoard();
		Hit hit;

		// main loop
		b1.print();
		boolean done;
		do {
			if(multiplayer) {
				System.out.println("\n\n\n\n\n\n\n\nTour de : " + b1.getName());
				b1.print();
			}
			hit = player1.sendHit(coords); // TODO player1 send a hit
			boolean strike = hit != Hit.MISS; // TODO set this hit on his board (b1)
			b1.setHit(strike, coords);

			done = updateScore();
			
			System.out.println("                     " + b1.getName());
			b1.print();
			System.out.println(makeHitMessage(false /* outgoing hit */, coords, hit));
			//save();
			
			if (!done && !strike) {
				if(multiplayer) {
					System.out.println("\n\n\n\n\n\n\n\n\n\nPassez l'ordinateur au joueur " + b2.getName());
				}
				do {
					if(multiplayer) {
						System.out.println("\n\n\n\n\n\n\n\n\n\nTour de : " + b2.getName());
						b2.print();
					}
					
					hit = player2.sendHit(coords); // TODO player2 send a hit.
					strike = hit != Hit.MISS;
					b2.setHit(strike, coords);
					
					if (strike) {
						if(multiplayer) {
							System.out.println("                     " + b2.getName());
							b2.print();
						}
						else {
							System.out.println("                     " + b1.getName());
							b1.print();
							System.out.println("                     " + b2.getName());
							b2.print();
						}
					}
					System.out.println(makeHitMessage(true /* incoming hit */, coords, hit));
					done = updateScore();

					if (!done) {
						if (!strike) {
							if(multiplayer) {
								System.out.println("\n\n\n\n\n\n\n\n\n\nPassez l'ordinateur au joueur " + b1.getName());
							}
						}
//						save();
					}
				} while (strike && !done);
			}

		} while (!done);

		SAVE_FILE.delete();
		System.out.println(String.format("joueur %d gagne", player1.isLose() ? 2 : 1));
		//sin.close();
		
	}

	private void save() {
//		try {
//			// TODO bonus 2 : uncomment
//			// if (!SAVE_FILE.exists()) {
//			// SAVE_FILE.getAbsoluteFile().getParentFile().mkdirs();
//			// }
//
//			// TODO bonus 2 : serialize players
//
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	}

	private boolean loadSave() {
//		if (SAVE_FILE.exists()) {
//			try {
//				// TODO bonus 2 : deserialize players
//
//				return true;
//			} catch (IOException | ClassNotFoundException e) {
//				e.printStackTrace();
//			}
//		}
		return false;
	}

	private boolean updateScore() {
		for (Player player : new Player[] { player1, player2 }) {
			int destroyed = 0;
			for (AbstractShip ship : player.getShips()) {
				if (ship.isSunk()) {
					destroyed++;
				}
			}

			player.setDestroyedCount(destroyed);
			player.setLose(destroyed == player.getShips().length);
			if (player.isLose()) {
				return true;
			}
		}
		return false;
	}

	private String makeHitMessage(boolean incoming, Coords coords, Hit hit) {
		String msg;
		ColorUtil.Color color = ColorUtil.Color.RESET;
		switch (hit) {
		case MISS:
			msg = hit.toString();
			break; 
		case STRIKE:
			msg = hit.toString();
			color = ColorUtil.Color.RED;
			break;
		default:
			msg = hit.toString() + " coulé";
			color = ColorUtil.Color.RED;
		}
		msg = String.format("%s Frappe en %c%d : %s", incoming ? "<=" : "=>", ((char) ('A' + coords.getX())),
				(coords.getY() + 1), msg);
		return ColorUtil.colorize(msg, color);
	}

	private static AbstractShip[] createDefaultShips() {
		return new AbstractShip[] { new Destroyer(), new Submarine(), new Submarine(), new BattleShip(), new Carrier() };
	}
	

}
