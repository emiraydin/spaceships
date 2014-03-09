package logic.spacethings;

import java.awt.Point;
import java.util.List;

import logic.AbstractWeapon;
import logic.GameBoard;

import common.GameConstants.ActionType;
import common.GameConstants.OrientationType;
import common.GameConstants.WeaponType;

public abstract class AbstractShip extends SpaceThing {
	protected int speed;
	protected int length;
	protected int[] sectionHealth;
	protected OrientationType orientation;
	protected int cannonWidth;					// entire width (both sides)
	protected int cannonLength;
	protected int cannonLengthOffset; 			// towards head is positive
//	protected int sonarVisibilityWidth;
//	protected int sonarVisibilityLength;
	protected int radarVisibilityWidth;			// entire width (both sides)
	protected int radarVisibilityLength;
	protected int radarVisibilityLengthOffset;	// towards head is positive
//	private ActionType[] possibleActions;
	protected AbstractWeapon[] arsenal;
	
	
	public AbstractShip(int x, int y, GameBoard gameBoard){
		super(x, y, gameBoard);
	}
	
	public int[][] getShipCoords(){
		return getShipCoords(getX(), getY());
	}
	
	public int[][] getShipCoords(int x, int y){
		int[][] coords = new int[length][2];
		switch (orientation){
		case North:
			for (int i = 0; i < length; i++){
				coords[i][0] = x;
				coords[i][1] = y - i;
			}
		case South:
			for (int i = 0; i < length; i++){
				coords[i][0] = x;
				coords[i][1] = y + i;
			}
		case East:
			for (int i = 0; i < length; i++){
				coords[i][0] = x + i;
				coords[i][1] = y;
			}
		case West:
			for (int i = 0; i < length; i++){
				coords[i][0] = x - i;
				coords[i][1] = y;
			}
		}
		
		return coords;
	}
	
	/**
	 * Try turning in given direction, see if crash/detonate mine.
	 * @param turnType Desired turn direction
	 * @return True if turned successfully, false otherwise
	 */
	public abstract boolean tryTurning(ActionType turnType);
	
	public boolean useWeapon(WeaponType wType, int x, int y){
		//TODO: useWeapon()
		for (int i = 0; i < arsenal.length; i++){
			if (arsenal[i].getType() == wType){
				return arsenal[i].fire(x, y);
			}
		}
		return false;
	}

	public int getSectionAt(int x, int y){
		if (getGameBoard().getSpaceThing(x, y) instanceof AbstractShip){
 			AbstractShip ship = (AbstractShip) getGameBoard().getSpaceThing(x, y);
			return Math.abs((x - ship.getX()) + (y - ship.getY()));
		}
		return -1;
	}
	
	public void incrementSectionHealth(int amount, int section){
		sectionHealth[section] += amount;
	}
	
	public void decrementSectionHealth(int amount, int section){
		sectionHealth[section] -= amount;
	}
	
	public boolean isSectionDamaged(int section) { 
		if(sectionHealth[section] > 0) { 
			return false;
		}
		return true;
	}
	
	public boolean isDead(){
		for (int i = 0; i < sectionHealth.length; i++){
			if (sectionHealth[i] > 0){
				return false;
			}
		}
		return true;
	}
	
	public OrientationType getOrientation() {
		return orientation;
	}

	public void setOrientation(OrientationType orientation) {
		this.orientation = orientation;
	}

	public int getSpeed() {
		int count = 0;
		for (int i = 0 ; i < length; i++){
			count += (sectionHealth[i] > 0 ? 1 : 0);
		}
		
		return (int) Math.ceil((length/(float) count)*speed);
	}

	public int getLength() {
		return length;
	}

	public int getCannonWidth() {
		return cannonWidth;
	}

	public int getCannonLength() {
		return cannonLength;
	}

	public int getCannonLengthOffset() {
		return cannonLengthOffset;
	}

//	public int getSonarVisibilityWidth() {
//		return sonarVisibilityWidth;
//	}
//
//	public int getSonarVisibilityLength() {
//		return sonarVisibilityLength;
//	}

	public int getRadarVisibilityWidth() {
		return radarVisibilityWidth;
	}

	public int getRadarVisibilityLength() {
		return radarVisibilityLength;
	}
	
	public int getRadarVisibilityLengthOffset() { 
		return radarVisibilityLengthOffset;
	}
	
	public void setArsenal(AbstractWeapon[] weapons){
		arsenal = weapons;
	}
	
	protected void initializeHealth(int length, boolean heavyArmour) { 
		this.sectionHealth = new int[length];
		if(heavyArmour) { 
			for(int i = 0; i < length; i++) { 
				sectionHealth[i] = 2;
			}
		} else { 
			for(int i = 0; i < length; i++) { 
				sectionHealth[i] = 1;
			}
		}
	}

	/**
	 * Very specific helper method for ship turning pls ignore how awkward it is
	 * If (x,y) is in bounds, then if there's a SpaceThing at (x,y), add it to the list.
	 * @param x
	 * @param y
	 * @return False if this location puts ship out of bounds, true otherwise.
	 */
	protected boolean addObstacleToList(int x, int y, List<Point> list) { 
		if(!GameBoard.inBounds(x, y)) { 
			System.out.println("Ship would be out of bounds at (" + x + "," + y + ")");
			return false;
		}
		if(this.getGameBoard().getSpaceThing(x, y) != null) { 
			list.add(new Point(x,y));
		}
		return true;
	}

}
