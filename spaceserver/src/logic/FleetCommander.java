package logic;

import java.util.ArrayList;

import logic.spacethings.AbstractShip;

import common.GameConstants.ActionType;
import common.GameConstants.VisibilityType;
import common.GameConstants.WeaponType;

public class FleetCommander {
	private int[][] sonarVisibility, radarVisibility;
	private int fcID;
	private ArrayList<AbstractShip> ships;
	
	public FleetCommander(int fcID){
		super();
		this.fcID = fcID;
		sonarVisibility = new int[30][30];
		radarVisibility = new int[30][30];
		ships = new ArrayList<>();
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
//			if (Math.abs((x - ship.getX()) + (y - ship.getY())) == 1){
//				
//			}
		}
		return 0;
	}
	
	private boolean handleCollisions(AbstractShip ship, int x, int y){
		//TODO: Return whether having this ship at this position would incur a collision or mine explosion
		return false;
	}
	
	private boolean validateMove(AbstractShip ship, int x, int y){
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
				} else {
					return true;
				}
			case North:
				if (y > ship.getY()){
					return false;
				} else if (y < ship.getY() - speed){
					return false;
				} else {
					return true;
				}
			case South:
				if (y < ship.getY()){
					return false;
				} else if (y > ship.getY() + speed){
					return false;
				} else {
					return true;
				}
			case West:
				if (x > ship.getX()){
					return false;
				} else if (x < ship.getX() - speed){
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
