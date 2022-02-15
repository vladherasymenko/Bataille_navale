package ensta.model;

import ensta.model.ship.AbstractShip;
import ensta.util.ColorUtil;
import ensta.util.Orientation;

public class Board implements IBoard{

	private String name;
	private ShipState[][] ships;
	private Boolean[][] hits;
	private int size;
	
	private static final int DEFAULT_SIZE = 10;
	
	public Board() {
	}
	
	public Board(String name) {
		this.name = name;
		this.ships = new ShipState[10][10];
		this.hits = new Boolean[10][10];
		this.size = 10;
	}
	
	public Board(String name, int size) {
		this.name = name;
		this.ships = new ShipState[size][size];
		this.hits = new Boolean[size][size];
		this.size = size;
	}
	
	public boolean putShip(AbstractShip navire, Coords point) {
		int x = point.getX();
		int y = point.getY();
		int increment = navire.getOrientation().getIncrement();
		Orientation shipOrientation = navire.getOrientation();
		if(!canPutShip(navire, point)) {
			System.out.println("Impossible de placer le navire !");
			return false;
		}
		if(shipOrientation == Orientation.EAST || shipOrientation == Orientation.WEST) {
			for(int dx=x; dx != x+increment*navire.getLength();dx+=increment) {
				ships[y][dx] = new ShipState(navire);
			}
		}
		if(shipOrientation == Orientation.NORTH || shipOrientation == Orientation.SOUTH) {
			for(int dy=y; dy!=y+increment*navire.getLength();dy+=increment) {
				ships[dy][x] = new ShipState(navire);
			}
		}
		return true;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	public void setShips(ShipState[][] ships) {
		this.ships = ships;
	}
	public void setHits(Boolean[][] hits) {
		this.hits = hits;
	}
	 
	public Boolean[][] getHits(){
		return this.hits;
	}
	public ShipState[][] getShips(){
		return this.ships;
	}
	public String getName() {
		return this.name;
	}
	
	public void print() {
		System.out.println("        Navires                        Frappes");
		String lineLetters = "   ";
		for(int k=65; k < 65+this.ships.length; k++) {
			lineLetters += String.valueOf((char) k);
			lineLetters += " ";
		}
		lineLetters += "         ";
		for(int k=65; k < 65+this.ships.length; k++) {
			lineLetters += String.valueOf((char) k);
			lineLetters += " ";
		}
		System.out.println(lineLetters);
		for(int i=0; i < this.ships.length; i++) {
			String ligne = Integer.toString(i+1);
			if((2-Integer.toString(i+1).length()) != 0) {
				ligne += " ";
			}
			for(int j=0; j < this.ships.length; j++) {
				if(this.ships[i][j] != null) {
					ligne += " "+this.ships[i][j].toString();
				}
				else {
					ligne += " .";
				}
			}
			ligne += "       " + Integer.toString(i+1);
			if((2-Integer.toString(i+1).length()) != 0) {
				ligne += " ";
			}
			for(int j=0; j < this.ships.length; j++) {
				if(this.hits[i][j] == null) {
					ligne += " .";
				}

				else if(this.hits[i][j] && this.ships[i][j] != null) {
					if(this.ships[i][j].isStruck()) {
						ligne += ColorUtil.colorize(" X", ColorUtil.Color.RED);
					}
					
				}
				else if(this.hits[i][j] && ships[i][j] == null) {
					ligne += " X";
				}
			} 
			System.out.printf("%20s %n",ligne);
		}
	
	}
	
	
	public boolean canPutShip(AbstractShip ship, Coords coords) {
		Orientation o = ship.getOrientation();
		int dx = 0, dy = 0;
		if (o == Orientation.EAST) {
			if (coords.getX() + ship.getLength() >= this.size) {
				return false;
			}
			dx = 1;
		} else if (o == Orientation.SOUTH) {
			if (coords.getY() + ship.getLength() >= this.size) {
				return false;
			}
			dy = 1;
		} else if (o == Orientation.NORTH) {
			if (coords.getY() + 1 - ship.getLength() < 0) {
				return false;
			}
			dy = -1;
		} else if (o == Orientation.WEST) {
			if (coords.getX() + 1 - ship.getLength() < 0) {
				return false;
			}
			dx = -1;
		}

		Coords iCoords = new Coords(coords);

		for (int i = 0; i < ship.getLength(); ++i) {
			if (this.hasShip(iCoords)) {
				return false;
			}
			iCoords.setX(iCoords.getX() + dx);
			iCoords.setY(iCoords.getY() + dy);
		}

		return true;
	}

	@Override
	public int getSize() {
		return this.size;
	}

	@Override
	public boolean hasShip(Coords coords) {
		if(this.ships[coords.getY()][coords.getX()] != null) {
			return true;
		}
		return false;
	}

	@Override
	public void setHit(boolean hit, Coords coords) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Boolean getHit(Coords coords) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Hit sendHit(Coords res) {
		int x = res.getX();
		int y = res.getY();
		if(hits[x][y] != null) {
			System.out.println("Cette case a été déjà touchée !");
			return Hit.STRIKE; 
		}
		else {
			if(this.ships[x][y] != null) {
				this.ships[x][y].addStrike();
				
				if(ships[x][y].getShipLabel() == 'C' && ships[x][y].isSunk()) {
					System.out.println("Porte-avion ennemi envoyé par le fond !");
					this.hits[x][y] = true; 
					return Hit.CARRIER;
				}
				else if(ships[x][y].getShipLabel() == 'D' && ships[x][y].isSunk()) {
					System.out.println("Frégate ennemi envoyé par le fond !");
					this.hits[x][y] = true; 
					return Hit.DESTROYER;
				}
				else if(ships[x][y].getShipLabel() == 'S' && ships[x][y].isSunk()) {
					System.out.println("Sous-marin ennemi envoyé par le fond !");
					this.hits[x][y] = true; 
					return Hit.SUBMARINE;
				}
				else if(ships[x][y].getShipLabel() == 'B' && ships[x][y].isSunk()) {
					System.out.println("Croiseur ennemi envoyé par le fond !");
					this.hits[x][y] = true; 
					return Hit.BATTLESHIP;
				}
				else if(ships[x][y].getShipLabel() == 'C' || ships[x][y].getShipLabel() == 'D' || ships[x][y].getShipLabel() == 'S' || ships[x][y].getShipLabel() == 'B') {
					System.out.println("Navire ennemi touché !");
					this.hits[x][y] = true; 
					return Hit.STRIKE;
				}
			}
			else {
				System.out.println("La frappe n'a pas touché la cible");
				this.hits[x][y] = true; 
				return Hit.MISS; 
			}
			return Hit.MISS; 
		}
	}
	
}
