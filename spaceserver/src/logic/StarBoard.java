package logic;

import static common.GameConstants.BOARD_HEIGHT;
import static common.GameConstants.BOARD_WIDTH;
import static common.GameConstants.NUM_ASTEROIDS;

import java.util.ArrayList;
import java.util.List;

import logic.spacethings.AbstractShip;
import logic.spacethings.Asteroid;
import logic.spacethings.SpaceThing;

public class StarBoard {
	SpaceThing[][] map;
	int thingIDCount;
	public List<AbstractShip> unplacedShips;
	public int unplacedCounter = 2; 
	public List<AbstractShip> deadShips;
	
	public StarBoard(){
		map = new SpaceThing[BOARD_HEIGHT][BOARD_WIDTH];
		thingIDCount = 0;
		unplacedShips = new ArrayList<AbstractShip>();
		deadShips = new ArrayList<AbstractShip>();
	}
	
	public int nextID(){
		return thingIDCount++;
	}
	
	public void addToDeadShipList(AbstractShip ship) { 
		deadShips.add(ship);
	}
	
	public SpaceThing getSpaceThing(int x, int y){
		if (inBounds(x, y)){
			return map[x][y];
		} else {
			return null;
		}
	}
	
	public void setSpaceThing(SpaceThing sThing){
		int x = sThing.getX();
		int y = sThing.getY();
		if (inBounds(x, y)){
			if (map[x][y] != null){
				System.out.println("Warning: overriding spacething!");
			}
			map[x][y] = sThing;
		} else {
			System.out.println("Can't set SpaceThing out of bounds.");
		}
	}
	
	public void setSpaceThing(AbstractShip ship){
		int x = ship.getX();
		int y = ship.getY();
		for (int i = 0; i < ship.getLength(); i++){
			//UPDATED FOR NEW ORIENTATION
			switch (ship.getOrientation()){
				case South:
					map[x][y-i] = ship;
					break;
				case North:
					map[x][y+i] = ship;
					break;
				case East:
					map[x+i][y] = ship;
					break;
				case West:
					map[x-i][y] = ship;
					break;
			}
		}
	}
	
	/**
	 * Checks if a ship can be placed at a location with no overlap with any other spacethings
	 * @param ship
	 * @return true if possible, false otherwise
	 */
	public boolean validPlaceShipLocation(AbstractShip ship, int x, int y) { 
		for (int i = 0; i < ship.getLength(); i++){
			//UPDATED FOR NEW ORIENTATION
			switch (ship.getOrientation()){
				case South:
					if(getSpaceThing(x, y-i) != null) { 
						return false;
					}
					break;
				case North:
					if(getSpaceThing(x, y+i) != null) { 
						return false;
					}
					break;
				case East:
					if(getSpaceThing(x+i, y) != null) { 
						return false;
					}
					break;
				case West:
					if(getSpaceThing(x-i, y) != null) { 
						return false;
					}
					break;
			}
		}
		
		return true;
	}
	
	/*
	 * This should be called carefully!
	 * If the ship is being removed from the game, use FleetCommander.removeShip()!
	 */
	public void clearSpaceThing(AbstractShip ship) { 
		clearSpaceThing(ship.getX(), ship.getY());
	}
	
	public void clearSpaceThing(int x, int y){
		if (inBounds(x, y)){
			if (map[x][y] instanceof AbstractShip){
				AbstractShip ship = (AbstractShip) map[x][y];
				for (int i = 0; i < ship.getLength(); i++){
					// UPDATED FOR NEW ORIENTATION
					switch (ship.getOrientation()){
						case South:
							map[ship.getX()][ship.getY()-i] = null;
							break;
						case North:
							map[ship.getX()][ship.getY()+i] = null;
							break;
						case East:
							map[ship.getX()+i][ship.getY()] = null;
							break;
						case West:
							map[ship.getX()-i][ship.getY()] = null;
							break;
					}
				}
			} else {
				map[x][y] = null;
			}
		} else {
			System.out.println("Can't clear SpaceThing out of bounds.");
		}
	}
	
	public void generateAsteroids(FleetCommander[] players){
		int count = 0; 
		while(count < NUM_ASTEROIDS){
			int randX = 10 + (int) (Math.random() * ((20 - 10) + 1)); 
			int randY = 3 + (int) (Math.random() * ((27 - 3) + 1));
			
			if(getSpaceThing(randX, randY) == null){
				setSpaceThing(new Asteroid(randX, randY, this));
				players[0].incrementRadarVisibility(randX, randY);
				players[1].incrementRadarVisibility(randX, randY);
				count++; 
			}
		}
	}
	
	public static boolean inBounds(int x, int y){
		return (x >=0 && y >= 0 && x < BOARD_WIDTH && y < BOARD_HEIGHT);
	}

}
