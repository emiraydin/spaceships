package logic.spacethings;

import java.awt.Point;
import java.util.List;
import java.util.Random;

import logic.AbstractWeapon;
import logic.FleetCommander;
import logic.StarBoard;
import messageprotocol.GameStateMessage;

import common.GameConstants.ActionType;
import common.GameConstants.OrientationType;
import common.GameConstants.SpaceThingType;
import common.GameConstants.WeaponType;

public abstract class AbstractShip extends SpaceThing {
	private int speed;
	protected int length;
	protected int[] sectionHealth;
	protected OrientationType orientation;
	protected int cannonWidth;					// entire width (both sides)
	protected int cannonLength;
	protected int cannonLengthOffset; 			// towards head is positive
	protected int radarVisibilityWidth;			// entire width (both sides)
	protected int radarVisibilityLength;
	protected int radarVisibilityLengthOffset;	// towards head is positive
	protected AbstractWeapon[] arsenal;
	
	@Override
	public GameStateMessage genGameStateMessage() {
		return new GameStateMessage(getID(), getOwner().getPlayer(), getShipType(), 
				getX(), getY(), orientation, sectionHealth);
	}
	
	public abstract SpaceThingType getShipType();
	
	public AbstractShip(int x, int y, OrientationType orientation, FleetCommander owner, StarBoard gameBoard){
		super(x, y, owner, gameBoard);
		this.orientation = orientation;
	}
	
	public int[][] getShipCoords(){
		return getShipCoords(getX(), getY());
	}
	
	public int[][] getShipCoords(int x, int y){
		int[][] coords = new int[length][2];
		switch (orientation){
		// ORIENTATION FOLLOWS NEW ORIGIN CONVENTION
		case South:
			for (int i = 0; i < length; i++){
				coords[i][0] = x;
				coords[i][1] = y - i;
			}
			break;
		case North:
			for (int i = 0; i < length; i++){
				coords[i][0] = x;
				coords[i][1] = y + i;
			}
			break;
		case East:
			for (int i = 0; i < length; i++){
				coords[i][0] = x + i;
				coords[i][1] = y;
			}
			break;
		case West:
			for (int i = 0; i < length; i++){
				coords[i][0] = x - i;
				coords[i][1] = y;
			}
			break;
		}
		
		return coords;
	}
	
	
	/**
	 * Registers a collision, handles damage, and alerts players of collision.
	 * @param shipX Ship's x-coordinate in collision
	 * @param shipY Ship'x y-coordinate in collision
	 * @param obstacleX Obstacle's x-coordinate in collision
	 * @param obstacleY Obstacle's y-coordinate in collision
	 * @return True if successful collision, false otherwise
	 */
	public boolean collideWhileTurning(int obstacleX, int obstacleY) { 
		SpaceThing spaceThing = this.getGameBoard().getSpaceThing(obstacleX, obstacleY);
		
		// basic check 
		if(spaceThing == null) { 
			return false;
		}
		
		// if spaceThing was BaseTile, Asteriod, or other ship, no damage done
		if(spaceThing instanceof Asteroid) { 
			this.getOwner().setActionResponse(String.format("Ship collision with asteroid at (%d,%d)", obstacleX, obstacleY));
			return true;
		}
		else if(spaceThing instanceof BaseTile) { 
			this.getOwner().setActionResponse(String.format("Ship collision with a base at (%d,%d)", obstacleX, obstacleY));
			return true;
		}
		else if(spaceThing instanceof AbstractShip) { 
			this.getOwner().setActionResponse(String.format("Ship collision at (%d,%d)", obstacleX, obstacleY));
			return true;
		}		
		else if(spaceThing instanceof Mine) {
			// if ship is minelayer, mine doesn't explode. nothing happens. 
			if(this instanceof MineLayerShip) { 
				// non-destructive collision, not announced
				return true;
			}
			
			Mine mine = (Mine)spaceThing;
			
			// choose random section of ship to be be the square that collides
			int[][] shipCoords = this.getShipCoords();
			// detonate mine
			Random r = new Random(shipCoords.length);
			int[] shipLocation = shipCoords[r.nextInt()];
			mine.detonate(shipLocation[0], shipLocation[1]);
			this.getOwner().setActionResponse(String.format("Mine detonated at (%d,%d)", obstacleX, obstacleY));
		}
		else { 
			return false;
		}
		
		return true;
		
	}
	
	public boolean useWeapon(WeaponType wType, int x, int y){
		for (int i = 0; i < arsenal.length; i++){
			if (arsenal[i].getType() == wType){
				return arsenal[i].fire(x, y);
			}
		}
		this.getOwner().setActionResponse(this.getShipType() + " does not have weapon type " + wType.toString());
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
	
	public void setMaxSpeed(int speed) { 
		this.speed = speed;
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
		if(!StarBoard.inBounds(x, y)) { 
//			System.out.println("Ship would be out of bounds at (" + x + "," + y + ")");
			return false;
		}
		if(this.getGameBoard().getSpaceThing(x, y) != null) { 
			list.add(new Point(x,y));
		}
		return true;
	}
	
	public abstract List<Point> getObstaclesInTurnZone(ActionType direction);
		
	/** Checks to see if any obstacles in the way of the ship turning.
	 * If obstacles, deals with them (collides, etc)
	 * 
	 * @param obstaclesInTurnZone SHOULD NOT BE NULL PLS USE VALIDATETURN() THX
	 * @return true if turn completed (no collisions), false otherwise.
	 */
	public boolean tryTurning(ActionType direction) { 
		List<Point> obstacles = this.getObstaclesInTurnZone(direction);
		
		// this should not be null! pls guys
		if(obstacles == null) { 
			return false;
		}
		
		/* If there's a ship, asteroid or base in the way, turn is cancelled due to collision */
		for(Point coord : obstacles) { 
			SpaceThing spaceThing = this.getGameBoard().getSpaceThing(coord.x, coord.y);
			if(spaceThing instanceof AbstractShip || spaceThing instanceof Asteroid || spaceThing instanceof BaseTile) { 
				// collide takes care of message responses
				this.collideWhileTurning(coord.x, coord.y);
				return false;
			}
		}
		
		/* If there's a mine in the way, it detonates */
		for(Point coord : obstacles) { 
			SpaceThing spaceThing = this.getGameBoard().getSpaceThing(coord.x, coord.y);
			if(spaceThing instanceof Mine) { 	
				// mine only detonates if ship is not a MineLayer
				if(!(this instanceof MineLayerShip)) { 
					// collide takes care of message responses
					this.collideWhileTurning(coord.x, coord.y);
				} 
				// either way, turn not completed
				return false;
				
			}
		}
		
		// no obstacles! turn successful.
		return true;
	}
	
	/**
	 * Returns the coordinates of a turn about a pivot
	 * @param x Start x
	 * @param y Start y
	 * @param startOrientation Start orientation
	 * @param direction Direction of turn
	 * @return Point representing the final location.
	 */
	protected Point getLocationAfterPivot(int x, int y, OrientationType startOrientation, ActionType direction) { 
		// FOLLOWS NEW ORIGIN CONVENTION
		if(this instanceof TorpedoBoatShip || this instanceof RadarBoatShip) { 
			if(direction == ActionType.TurnLeft) {
				switch(startOrientation) { 
				case East:
					return new Point(x+1, y-1);
				case West:
					return new Point(x-1, y+1);
				case North:
					return new Point(x+1, y+1);
				case South:
					return new Point(x-1, y-1);
				}
			}
			else if (direction == ActionType.TurnRight) { 
				switch(startOrientation) { 
				case East:
					return new Point(x+1, y+1);
				case West:
					return new Point(x-1, y-1);
				case North:
					return new Point(x-1, y+1);
				case South:
					return new Point(x+1, y-1);
				}
			}
			return null;
		}
		else if(this instanceof CruiserShip || this instanceof MineLayerShip || this instanceof DestroyerShip) { 
			// This really shouldn't ever be called
			return new Point(x, y);
		}
		return null;				
	}
	
	/**
	 * Returns the orientation after a 90 degree pivot
	 * @param orientation start orientation
	 * @param direction direction to turn (left or right)
	 * @return the final orientation
	 */
	protected OrientationType getOrientationAfterPivot(OrientationType orientation, ActionType direction) { 
		if(direction == ActionType.TurnLeft) { 
			switch(orientation) { 
			case East:
				return OrientationType.North;
			case West:
				return OrientationType.South;
			case North:
				return OrientationType.West;
			case South:
				return OrientationType.East;
			}
		}
		else if (direction == ActionType.TurnRight) { 
			switch(orientation) { 
			case East:
				return OrientationType.South;
			case West:
				return OrientationType.North;
			case North:
				return OrientationType.East;
			case South:
				return OrientationType.West;
			}
		}
		return null;
	}
}	

	