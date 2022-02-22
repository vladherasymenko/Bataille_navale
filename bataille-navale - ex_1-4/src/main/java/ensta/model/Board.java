package ensta.model;

import ensta.model.ship.AbstractShip;
import ensta.util.Orientation;

public class Board implements IBoard{

	private String name;
	private Character[][] ships;
	private boolean[][] hits;
	private int size;
	
	private static final int DEFAULT_SIZE = 10;
	
	public Board() {
	}
	
	public Board(String name) {
		this.name = name;
		this.ships = new Character[10][10];
		this.hits = new boolean[10][10];
		this.size = 10;
	}
	
	public Board(String name, int size) {
		this.name = name;
		this.ships = new Character[size][size];
		this.hits = new boolean[size][size];
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
			for(int dx=x; dx<x+navire.getLength();dx+=increment) {
				ships[y][dx] = navire.getLabel();
			}
		}
		if(shipOrientation == Orientation.NORTH || shipOrientation == Orientation.SOUTH) {
			for(int dy=y; dy<y+navire.getLength();dy+=increment) {
				ships[dy][x] = navire.getLabel();
			}
		}
		return true;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	public void setShips(Character[][] ships) {
		this.ships = ships;
	}
	public void setHits(boolean[][] hits) {
		this.hits = hits;
	}
	 
	public boolean[][] getHits(){
		return this.hits;
	}
	public Character[][] getShips(){
		return this.ships;
	}
	public String getName() {
		return this.name;
	}
	
	public void print() {
		System.out.println("ships");
		String lineLetters = "  ";
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
					ligne += " "+this.ships[i][j];
				}
				else {
					ligne += " .";
				}
			}
			System.out.printf("%20s %n",ligne);
		}
		
		System.out.println("hits");
		System.out.println(lineLetters);
		for(int i=0; i < this.ships.length; i++) {
			String ligne = Integer.toString(i+1);
			if((2-Integer.toString(i+1).length()) != 0) {
				ligne += " ";
			}
			for(int j=0; j < this.ships.length; j++) {
				if(this.hits[i][j]) {
					ligne += " *";
				}
				else {
					ligne += " .";
				}
			} 
			System.out.println(ligne);
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
		// TODO Auto-generated method stub
		return 0;
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
		// TODO Auto-generated method stub
		return null;
	}
	
}
