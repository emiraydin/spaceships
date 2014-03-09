package logic.spacethings;

import java.awt.Point;
import java.util.List;
import java.util.Random;

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
	
	/**
	 * Ship hit something while turning, not sure what part of the ship it hit.
	 * @param obstacleX Obstacle's x-coordinate in collision
	 * @param obstacleY Obstacle's y-coordinate in collision
	 * @return true if successful collision, false otherwise
	 */
	public boolean collide(int obstacleX, int obstacleY) { 
		return collide(this.getX(), this.getY(), obstacleX, obstacleY);
	}
	
	/**
	 * Handles the actual collision. Does appropriate damage, and alerts players of collision.
	 * @param shipX Ship's x-coordinate in collision
	 * @param shipY Ship'x y-coordinate in collision
	 * @param obstacleX Obstacle's x-coordinate in collision
	 * @param obstacleY Obstacle's y-coordinate in collision
	 * @return True if successful collision, false otherwise
	 */
	public boolean collide(int shipX, int shipY, int obstacleX, int obstacleY) { 
		GameBoard board = this.getGameBoard();
		SpaceThing spaceThing = this.getGameBoard().getSpaceThing(obstacleX, obstacleY);
		
		// basic check 
		if(spaceThing == null || board.getSpaceThing(shipX, shipY) != this) { 
			return false;
		}
		
		// if spaceThing was BaseTile or Asteriod, no damage done
		if(spaceThing instanceof Asteroid || spaceThing instanceof BaseTile) {
			System.out.println("Collision at " + obstacleX + "," + obstacleY);
			return true;
		}			
		else if(spaceThing instanceof AbstractShip) { 
			System.out.println("Collision at " + obstacleX + "," + obstacleY);
			// TODO: does this take damage?
		}
		else if(spaceThing instanceof Mine) {
			// if ship is minelayer, mine doesn't explode
			// method shouldn't even be called but this is a safety
			if(this instanceof MineLayerShip) { 
				return true;
			}
			
			/* 2 cases: if ship hit the mine from a cartesian direction, mine.detonate() does damage to ship
			 * but if ship hit the mine while turning and the mine wasn't originally adjacent to the ship before
			 * the turn, then the ship needs to take damage, and that's not covered in mine.detonate() */
			
			Mine mine = (Mine)spaceThing;
			
			// CASE 1: ship adjacent to mine
			int mineX = mine.getX();
			int mineY = mine.getY();
			if(board.getSpaceThing(mineX, mineY+1) == this || board.getSpaceThing(mineX, mineY-1) == this
					|| board.getSpaceThing(mineX-1, mineY) == this || board.getSpaceThing(mineX+1, mineY) == this) { 
				// damage taken care of
				mine.detonate();
			} 
			else { 
				// detonate mine
				mine.detonate();
				
				// choose random section of ship to be damaged
				int[][] shipCoords = this.getShipCoords();
				int section = new Random().nextInt(shipCoords.length);
				
				// an adjacent section is also damaged
				int section2;
				if (section + 1 < this.getLength()){
					section2 = section + 1;
				} else {
					section2 = section - 1;
				}
				this.decrementSectionHealth(Mine.getDamage(), section);
				this.decrementSectionHealth(Mine.getDamage(), section2);
			}
			System.out.println("Mine exploded at " + mine.getX() + "," + mine.getY());
		}
		else { 
			// uuuuuuh so this shouldnt happen but just in case
			return false;
		}
		
		return true;
		
	}
	
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
