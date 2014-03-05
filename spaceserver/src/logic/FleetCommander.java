package logic;

import java.util.ArrayList;

import logic.spacethings.AbstractShip;
import main.Main.OrientationType;
import main.Main.VisibilityType;
import main.Main.WeaponType;

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
	 * @return True if the action was performed.  False if move was invalid.
	 */
	public boolean useWeapon(WeaponType wType, int shipID, int x, int y){
		//TODO: Pass through to ship
		return false;
	}
	
	/**
	 * Move a specified ship to a specified location.
	 * @param shipID Ship to move
	 * @param x Location to move to
	 * @param y Location to move to
	 * @return Number of spaces moved by ship
	 */
	public int moveShip(int shipID, int x, int y){
		//TODO: implement moveShip
		return 0;
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
	
	
	public boolean turnShip(int shipID, OrientationType oType){
		//TODO: implement turnShip
		return false;
	}
	
	public VisibilityType getVisibility(int x, int y){
		if (x < 0 || y < 0 || x >= sonarVisibility.length || y >= sonarVisibility.length){
			// out of bounds
			return VisibilityType.NONE;
		} else if (sonarVisibility[x][y] > 0 && radarVisibility[x][y] > 0){
			return VisibilityType.BOTH;
		} else if (sonarVisibility[x][y] > 0){
			return VisibilityType.SONAR;
		} else if (radarVisibility[x][y] > 0){
			return VisibilityType.RADAR;
		} else {
			return VisibilityType.NONE;
		}
	}
	
	private AbstractShip getShip(int shipID){
		for (AbstractShip ship : ships){
			if (ship.getID() == shipID){
				return ship;
			}
		}
		return null;
	}
}
