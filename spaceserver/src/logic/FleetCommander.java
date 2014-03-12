package logic;

import java.util.ArrayList;

import logic.spacethings.AbstractShip;
import logic.spacethings.Mine;
import logic.spacethings.MineLayerShip;
import logic.spacethings.RadarBoatShip;
import logic.spacethings.SpaceThing;
import logic.spacethings.TorpedoBoatShip;

import common.GameConstants;
import common.GameConstants.ActionType;
import common.GameConstants.OrientationType;
import common.GameConstants.PlayerNumber;
import common.GameConstants.VisibilityType;
import common.GameConstants.WeaponType;

public class FleetCommander {
	private int[][] sonarVisibility, radarVisibility;
	private int fcID;
	private ArrayList<AbstractShip> ships;
	private StarBoard board;
	
	public FleetCommander(int fcID, StarBoard board){
		super();
		this.fcID = fcID;
		sonarVisibility = new int[30][30];
		radarVisibility = new int[30][30];
		/* TODO: set initial visibilities
		 * - your base has short radar range of 1 square
		 * - enemies base and coral reefs are visible
		 */
		ships = new ArrayList<AbstractShip>();
	}
	
	public void addShip(AbstractShip ship){
		ships.add(ship);
		incrementVisibility(ship);
	}
	
	public int[][] getSonarVisibility() {
		return sonarVisibility;
	}

	public int[][] getRadarVisibility() {
		return radarVisibility;
	}

	/**
	 * Perform an attack using a specified weapon.
	 * @param wType Weapon to be used.
	 * @param shipID Ship which should perform the attack
	 * @param x Location at which to use the weapon
	 * @param y Location at which to use the weapon
	 * @return True if the action was performed.  False if attack was invalid.
	 */
	public boolean useWeapon(WeaponType wType, int shipID, int x, int y){
		return getShip(shipID).useWeapon(wType, x, y);
	}
	
	/**
	 * Move a specified ship to a specified location.
	 * @param shipID Ship to move
	 * @param x Location to move to
	 * @param y Location to move to
	 * @return Number of spaces moved by ship
	 */
	public int moveShip(int shipID, int x, int y){
		//TODO: implement moveShip (Check for collision and mines)
		AbstractShip ship = getShip(shipID);
		
		if (validateMove(ship, x, y)){
			while (Math.abs((x - ship.getX()) + (y - ship.getY())) > 0){
				if (x > ship.getX()){
					if (handleCollisions(ship, x+1, y) || handleMineExplosions(ship, x, y)){
						break;
					} else {
						ship.setX(ship.getX() + 1);
					}
				} else if (x < ship.getX()){
					if (handleCollisions(ship, x-1, y) || handleMineExplosions(ship, x, y)){
						break;
					} else {
						ship.setX(ship.getX() - 1);
					}
				} else if (y > ship.getY()){
					if (handleCollisions(ship, x, y+1) || handleMineExplosions(ship, x, y)){
						break;
					} else {
						ship.setY(ship.getY() + 1);
					}
				} else if (y < ship.getY()){
					if (handleCollisions(ship, x, y-1) || handleMineExplosions(ship, x, y)){
						break;
					} else {
						ship.setY(ship.getY() - 1);
					}
				}
			}
		}
		return 0;
	}
	
	// Return true if having a ship at this position would incur a collision. (or out of bounds)
	private boolean handleCollisions(AbstractShip ship, int x, int y){
		if (x < 0 || y < 0 || x >= GameConstants.BOARD_WIDTH || y >= GameConstants.BOARD_HEIGHT){
			return true;
		}
		int[][] coords = ship.getShipCoords(x, y);
		SpaceThing thing;
		for (int i = 0; i < ship.getLength(); i++){
			if (coords[i][0] < 0 || coords[i][1] < 0 
					|| coords[i][0] >= GameConstants.BOARD_WIDTH || coords[i][1] >= GameConstants.BOARD_HEIGHT){
				return true;
			}
			thing = board.getSpaceThing(coords[i][0], coords[i][1]);
			if (thing != null && thing != ship){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Checks if there are mines in the vicinity of a ship and if so, detonates them
	 * unless ship is a Mine Layer, in which case it doesn't detonate anything.
	 * @param ship
	 * @param x
	 * @param y
	 * @return True if there were mines detonated, false otherwise
	 */
	private boolean handleMineExplosions(AbstractShip ship, int x, int y){
		boolean detonation = false;
		if(!(ship instanceof MineLayerShip)) { 
			int[][] coords = ship.getShipCoords();
			for (int i = 0; i < ship.getLength(); i++){
				int xCoord = coords[i][0];
				int yCoord = coords[i][1];
				detonation = (checkMine(xCoord + 1, yCoord) || detonation);
				detonation = (checkMine(xCoord - 1, yCoord) || detonation);
				detonation = (checkMine(xCoord, yCoord + 1) || detonation);
				detonation = (checkMine(xCoord, yCoord - 1) || detonation);
			}	
		}
		return detonation;
	}
	
	/**
	 * Check if the move can be performed by the ship (checks speed, heading, bounds)
	 */
	private static boolean validateMove(AbstractShip ship, int x, int y){
		//// Basic Validation ////
		if ((x - ship.getX()) != 0 && (y - ship.getY()) != 0){
			// Can't move diagonally
			return false;
		} else if (x == ship.getX() && y == ship.getY()){
			// Ship didn't move
			return false;
		} else if (Math.abs((x - ship.getX()) + (y - ship.getY())) == 1){
			// A ship can always move one square in each direction
			return true;
		}
		
		int speed = ship.getSpeed();
		// THESE FOLLOW NEW ORIGIN CONVENTION
		switch (ship.getOrientation()){
		case East:
			if (x < ship.getX()){
				return false;
			} else if (x > ship.getX() + speed){
				return false;
			} else if (y != ship.getY()){
				return false;
			} else {
				return true;
			}
		case South:
			if (y > ship.getY()){
				return false;
			} else if (y < ship.getY() - speed){
				return false;
			} else if (x != ship.getX()){
				return false;
			} else {
				return true;
			}
		case North:
			if (y < ship.getY()){
				return false;
			} else if (y > ship.getY() + speed){
				return false;
			} else if (x != ship.getX()){
				return false;
			} else {
				return true;
			}
		case West:
			if (x > ship.getX()){
				return false;
			} else if (x < ship.getX() - speed){
				return false;
			} else if (y != ship.getY()){
				return false;
			} else {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * If mine located at (x,y), detonates it.
	 * @param x
	 * @param y
	 * @return True if there was a mine, false otherwise.
	 */
	private boolean checkMine(int x, int y){
		if (board.getSpaceThing(x, y) instanceof Mine){
			((Mine) board.getSpaceThing(x, y)).detonate();
			return true;
		}
		return false;
	}
	
	/**
	 * Move a specified ship to a specified location.
	 * @param shipID Ship to move
	 * @param x Location to place at
	 * @param y Location to place at
	 * @return Whether placement was valid
	 */
	public boolean placeShip(int shipID, int x, int y){
		//TODO: implement placeShip
		return false;
	}
	
	public boolean pickupMine(int shipID, int x, int y){
		AbstractShip ship = getShip(shipID);
		SpaceThing thing = board.getSpaceThing(x, y);
		if (ship instanceof MineLayerShip && thing instanceof Mine){
			((MineLayerShip) ship).pickUpMine((Mine) thing);
		}
		return false;
	}
	
	/**
	 * Updates the ship (x,y) and the ship's orientation after a turn
	 * @param ship The ship that is turning
	 * @param direction The direction of turn
	 */
	private static void updateShipLocationAfterTurn(AbstractShip ship, ActionType direction) { 
		// if ship doesn't turn about stern (can turn 180)
		if(ship instanceof RadarBoatShip || ship instanceof TorpedoBoatShip) { 
			int x = ship.getX();
			int y = ship.getY();
			// THESE FOLLOW NEW ORIGIN CONVENTION
			switch(ship.getOrientation()) { 
			case East:
				if(direction == ActionType.TurnLeft) { 
					ship.setX(x+1);
					ship.setY(y-1);
					ship.setOrientation(OrientationType.North);
				}
				else if(direction == ActionType.TurnRight) { 
					ship.setX(x+1);
					ship.setY(y+1);
					ship.setOrientation(OrientationType.South);
				}
				else if(direction == ActionType.Turn180Left || direction == ActionType.Turn180Right) { 
					ship.setX(x+2);
					ship.setOrientation(OrientationType.West);
				}
				break;
			case West:
				if(direction == ActionType.TurnLeft) { 
					ship.setX(x-1);
					ship.setY(y+1);
					ship.setOrientation(OrientationType.South);
				}
				else if(direction == ActionType.TurnRight) { 
					ship.setX(x-1);
					ship.setY(y-1);
					ship.setOrientation(OrientationType.North);
				}
				else if(direction == ActionType.Turn180Left || direction == ActionType.Turn180Right) { 
					ship.setX(x-2);
					ship.setOrientation(OrientationType.East);
				}
				break;
			case North:
				if(direction == ActionType.TurnLeft) { 
					ship.setX(x+1);
					ship.setY(y+1);
					ship.setOrientation(OrientationType.West);
				}
				else if(direction == ActionType.TurnRight) { 
					ship.setX(x-1);
					ship.setY(y+1);
					ship.setOrientation(OrientationType.East);
				}
				else if(direction == ActionType.Turn180Left || direction == ActionType.Turn180Right) { 
					ship.setY(y+2);
					ship.setOrientation(OrientationType.South);
				}
				break;
			case South:
				if(direction == ActionType.TurnLeft) { 
					ship.setX(x-1);
					ship.setY(y-1);
					ship.setOrientation(OrientationType.East);
				}
				else if(direction == ActionType.TurnRight) { 
					ship.setX(x+1);
					ship.setY(y-1);
					ship.setOrientation(OrientationType.West);
				}
				else if(direction == ActionType.Turn180Left || direction == ActionType.Turn180Right) { 
					ship.setY(y-2);
					ship.setOrientation(OrientationType.North);
				}
				break;
			}
		}
		
		// other ships turn about stern and only turn 90 degrees
		// ship location doesn't change since stern is where ships are indexed at
		else { 
			// THESE FOLLOW NEW ORIGIN CONVENTION
			switch(ship.getOrientation()) { 
			case East:
				if(direction == ActionType.TurnLeft) { 
					ship.setOrientation(OrientationType.North);
				}
				else if(direction == ActionType.TurnRight) { 
					ship.setOrientation(OrientationType.South);
				}
				break;
			case West:
				if(direction == ActionType.TurnLeft) { 
					ship.setOrientation(OrientationType.South);
				}
				else if(direction == ActionType.TurnRight) { 
					ship.setOrientation(OrientationType.North);
				}
				break;
			case North:
				if(direction == ActionType.TurnLeft) { 
					ship.setOrientation(OrientationType.West);
				}
				else if(direction == ActionType.TurnRight) { 
					ship.setOrientation(OrientationType.East);
				}
				break;
			case South:
				if(direction == ActionType.TurnLeft) { 
					ship.setOrientation(OrientationType.East);
				}
				else if(direction == ActionType.TurnRight) { 
					ship.setOrientation(OrientationType.West);
				}
				break;
			}
		}
	}
	
	/** 
	 * Turns a given ship in a given direction.
	 * Updates the ship's location and orientation and updates the GameBoard location
	 * and visibility status.
	 * @param shipID ID of the ship that is turning.
	 * @param direction The direction of turn.
	 * @return True if the ship successfully turned, false otherwise.
	 */
	public boolean turnShip(int shipID, ActionType direction){
		AbstractShip ship = getShip(shipID);
		if(ship.tryTurning(direction)) { 
			/* turn completed successfully, so do bookkeeping */
			decrementVisibility(ship);
			board.clearSpaceThing(ship);
			updateShipLocationAfterTurn(ship, direction);
			board.setSpaceThing(ship, ship.getX(), ship.getY());
			incrementVisibility(ship);
			return true;
		}
		/* Otherwise, turn did not happen because ship went out of bounds, or because there
		 * was some obstacle in the way */
		
		return false;
	}
	
	public void incrementVisibility(AbstractShip ship) { 
		changeVisibility(ship, 1);
	}
	
	public void decrementVisibility(AbstractShip ship) { 
		changeVisibility(ship, -1);
	}
	
	private void changeVisibility(AbstractShip ship, int change) { 
		int shipX = ship.getX();
		int shipY = ship.getY();
		
		/* Radar */
		int radarLength = ship.getRadarVisibilityLength();
		int radarWidth = ship.getRadarVisibilityWidth();
		int radarLengthOffset = ship.getRadarVisibilityLengthOffset();
		// THESE FOLLOW NEW ORIGIN CONVENTION
		int minX = -1;
		int maxX = -1;
		int minY = -1;
		int maxY = -1;
		switch(ship.getOrientation()) { 
		case East: 
			minX = shipX + radarLengthOffset;
			maxX = minX + radarLength;
			minY = shipY - radarWidth/2;
			maxY = shipY + radarWidth/2;
			break;
		case West:
			minX = shipX - radarLengthOffset;
			maxX = minX - radarLength;
			minY = shipY - radarWidth/2;
			maxY = shipY + radarWidth/2;
			break;
		case South: 
			minX = shipX - radarWidth/2;
			maxX = shipX + radarWidth/2;
			maxY = shipY - radarLengthOffset;
			minY = maxY - radarLength;
			break;
		case North: 
			minX = shipX - radarWidth/2;
			maxX = shipX + radarWidth/2;
			minY = shipY + radarLengthOffset;
			maxY = minY + radarLength;
			break;
		}
		
		for(int i = minX; i <= maxX; i++) { 
			for(int j = minY; j <= maxY; j++) { 
				if(StarBoard.inBounds(i, j)) { 
					radarVisibility[i][j] = radarVisibility[i][j] + change;
				}
			}
		}
		
		/* Sonar */
		if(ship instanceof MineLayerShip) { 
			/* this should work???
			 * if you don't change the ship's section's visibility but change
			 * the surrounding tiles for each section, then you get the ship's sections
			 * covered for free right?? its 4 am why am i awake */
			int[][] coords = ship.getShipCoords();
			for(int[] s : coords) { 
				changeSonarVisibility(s[0], s[1]-1, change);
				changeSonarVisibility(s[0], s[1]+1, change);
				changeSonarVisibility(s[0]-1, s[1], change);
				changeSonarVisibility(s[0]+1, s[1], change);
			}
			
		}	
	}
	
	private void changeSonarVisibility(int x, int y, int change) { 
		if(StarBoard.inBounds(x, y)) { 
			sonarVisibility[x][y] = sonarVisibility[x][y] + change; 
		}
	}
	
	public VisibilityType getVisibility(int x, int y){
		if (x < 0 || y < 0 || x >= sonarVisibility.length || y >= sonarVisibility.length){
			// out of bounds
			return VisibilityType.None;
		} else if (sonarVisibility[x][y] > 0 && radarVisibility[x][y] > 0){
			return VisibilityType.Both;
		} else if (sonarVisibility[x][y] > 0){
			return VisibilityType.Sonar;
		} else if (radarVisibility[x][y] > 0){
			return VisibilityType.Radar;
		} else {
			return VisibilityType.None;
		}
	}
	
	public AbstractShip getShip(int shipID){
		for (AbstractShip ship : ships){
			if (ship.getID() == shipID){
				return ship;
			}
		}
		return null;
	}
	
	public int getID(){
		return fcID;
	}
	
	public PlayerNumber getPlayer(){
		if (fcID == 0){
			return PlayerNumber.PlayerOne;
		} else if (fcID == 1){
			return PlayerNumber.PlayerTwo;
		} else {
			System.out.println("ABORT ABORT EVERYTHING IS BROKEN");
			return null;
		}
	}
	
	public boolean hasLivingShips(){
		for (AbstractShip ship : ships){
			if (!ship.isDead()){
				return true;
			}
		}
		return false;
	}
}
