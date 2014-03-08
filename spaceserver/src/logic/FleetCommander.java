package logic;

import java.util.ArrayList;
import java.util.List;

import logic.spacethings.AbstractShip;
import logic.spacethings.SpaceThing;
import common.GameConstants;
import common.GameConstants.ActionType;
import common.GameConstants.VisibilityType;
import common.GameConstants.WeaponType;

public class FleetCommander {
	private int[][] sonarVisibility, radarVisibility;
	private int fcID;
	private ArrayList<AbstractShip> ships;
	private GameBoard board;
	
	public FleetCommander(int fcID, GameBoard board){
		super();
		this.fcID = fcID;
		sonarVisibility = new int[30][30];
		radarVisibility = new int[30][30];
		ships = new ArrayList<AbstractShip>();
	}
	
	public void addShip(AbstractShip ship){
		ships.add(ship);
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
	
//	Could be useful...
//	private List<SpaceThing> getAdjacentThings(){
//		
//	}
	
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
//		switch (ship.getOrientation()){
//		case North:
//			if (y - ship.getLength() < 0) {return true;}
//			for (int i = 0; i < ship.getLength(); i++){
//				thing = board.getSpaceThing(x, y - i);
//				if (thing != null && thing != ship){
//					return true;
//				}
//			}
//		case East:
//			if (x - ship.getLength() < 0) {return true;}
//			for (int i = 0; i < ship.getLength(); i++){
//				thing = board.getSpaceThing(x + i, y);
//				if (thing != null && thing != ship){
//					return true;
//				}
//			}
//		case South:
//			if (y + ship.getLength() > GameConstants.BOARD_HEIGHT) {return true;}
//			for (int i = 0; i < ship.getLength(); i++){
//				thing = board.getSpaceThing(x, y + i);
//				if (thing != null && thing != ship){
//					return true;
//				}
//			}
//		case West:
//			if (x + ship.getLength() > GameConstants.BOARD_WIDTH) {return true;}
//			for (int i = 0; i < ship.getLength(); i++){
//				thing = board.getSpaceThing(x - i, y);
//				if (thing != null && thing != ship){
//					return true;
//				}
//			}
//		}
		return false;
	}
	
	private boolean handleMineExplosions(AbstractShip ship, int x, int y){
		if (x < 0 || y < 0 || x >= GameConstants.BOARD_WIDTH || y >= GameConstants.BOARD_HEIGHT){
			return true;
		}
		int[][] coords = ship.getShipCoords();
		SpaceThing thing;
		for (int i = 0; i < ship.getLength(); i++){
			thing = board.getSpaceThing(coords[i][0], coords[i][1]);
			if (thing != null && thing != ship){
				return true;
			}
		}
		return false;
	}
	
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
		case North:
			if (y > ship.getY()){
				return false;
			} else if (y < ship.getY() - speed){
				return false;
			} else if (x != ship.getX()){
				return false;
			} else {
				return true;
			}
		case South:
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
	
	
	public boolean turnShip(int shipID, ActionType aType){
		//TODO: implement turnShip (check if 180 is possible, check for collisions/mines)
		switch (aType){
		case Turn180:
		case TurnLeft:
		case TurnRight:
		default:
			return false;
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
	
	public boolean hasLivingShips(){
		for (AbstractShip ship : ships){
			if (!ship.isDead()){
				return true;
			}
		}
		return false;
	}
}
