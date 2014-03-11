package logic.spacethings;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import logic.AbstractWeapon;
import logic.Cannon;
import logic.FleetCommander;
import logic.StarBoard;
import logic.TorpedoLauncher;
import common.GameConstants.ActionType;
import common.GameConstants.OrientationType;
import common.GameConstants.SpaceThingType;

public class DestroyerShip extends AbstractShip {
	public DestroyerShip(int x, int y, OrientationType oType, FleetCommander owner, StarBoard gameBoard){
		super(x, y, oType, owner, gameBoard);
		
		this.speed = 8;
		this.length = 4;
		
		initializeHealth(this.length, false);
		
		this.cannonWidth = 9;
		this.cannonLength = 12;
		this.cannonLengthOffset = -4;
		
		this.radarVisibilityLength = 8;
		this.radarVisibilityWidth = 3;
		this.radarVisibilityLengthOffset = 1;
		
		// Destroyers carry Cannons and Torpedoes
		this.arsenal = new AbstractWeapon[2];
		arsenal[0] = new Cannon(this);
		arsenal[1] = new TorpedoLauncher(this);
	}

	@Override
	public boolean tryTurning(ActionType direction) {
		if(!(direction == ActionType.TurnLeft || direction == ActionType.TurnRight)) { 
			System.out.println("DestroyerShip cannot turn in direction: " + direction.name());
			return false;
		}
		
		List<Point> obstaclesInTurnZone = getObstaclesInTurnZone(direction);
		
		// if obstaclesInTurnZone returned null, it means the turn puts the ship out of bounds!
		if(obstaclesInTurnZone == null) { 
			System.out.println("Turn not completed because out of bounds");
			return false;
		}
		
		return handleObstaclesWhileTurning(obstaclesInTurnZone);		
	}
	
	/**
	 * Gets the coordinates for all the obstacles in the turn zone. 
	 * @param turnDirection The direction of the desired turn.
	 * @return null if the turn puts the ship out of bounds,
	 *         otherwise a list of the coordinates of obstacles in the way of the turn
	 *         (ships are multiple coordinates for multiple collisions).
	 */
	protected List<Point> getObstaclesInTurnZone(ActionType direction) {
		List<Point> obstacles = new ArrayList<Point>();
		
		int x = this.getX();
		int y = this.getY();
		OrientationType orientation = this.getOrientation();
		// THESE USE NEW ORIGIN CONVENTION
		
		/* First, add the blocks in the way (not in final position of ship) */
		
		// top-left quadrant blocks
		if((orientation == OrientationType.West && direction == ActionType.TurnRight)
				|| (orientation == OrientationType.North && direction == ActionType.TurnLeft)) { 							
			for(int j = length-1; j >= 1; j--) { 
				for(int i = 1; i <= length-j; i++) {
					if(!this.addObstacleToList(x-i, y+j, obstacles)) { 
						return null;
					}
				}
			}
		}
		// top-right quadrant blocks
		else if((orientation == OrientationType.East && direction == ActionType.TurnLeft)
				|| (orientation == OrientationType.North && direction == ActionType.TurnRight)) { 
			for(int j = length-1; j >= 1; j--) { 
				for(int i = 1; i <= length-j; i++) {
					if(!this.addObstacleToList(x+i, y+j, obstacles)) { 
						return null;
					}
				}
			}
		}
		// bottom-left quadrant blocks
		else if((orientation == OrientationType.West && direction == ActionType.TurnLeft)
				|| (orientation == OrientationType.South && direction == ActionType.TurnRight)) { 
			for(int j = length-1; j >= 1; j--) { 
				for(int i = 1; i <= length-j; i++) {
					if(!this.addObstacleToList(x-i, y-j, obstacles)) { 
						return null;
					}
				}
			}
		}
		// bottom-right quadrant blocks
		else if((orientation == OrientationType.East && direction == ActionType.TurnRight)
				|| (orientation == OrientationType.South && direction == ActionType.TurnLeft)) { 
			for(int j = length-1; j >= 1; j--) { 
				for(int i = 1; i <= length-j; i++) {
					if(!this.addObstacleToList(x+i, y-j, obstacles)) { 
						return null;
					}
				}
			}
		}
		
		/* Then add final position of ship */
		
		OrientationType finalOrientation = getOrientationAfterPivot(orientation, direction);
		switch(finalOrientation) { 
		case East:
			for(int i = 1; i < this.length; i++) { 
				if(!this.addObstacleToList(x+i, y, obstacles)) { 
					return null;
				}
			}
		case West:
			for(int i = 1; i < this.length; i++) { 
				if(!this.addObstacleToList(x-i, y, obstacles)) { 
					return null;
				}
			}
		case North:
			for(int i = 1; i < this.length; i++) { 
				if(!this.addObstacleToList(x, y+i, obstacles)) { 
					return null;
				}
			}
		case South:
			for(int i = 1; i < this.length; i++) { 
				if(!this.addObstacleToList(x, y-i, obstacles)) { 
					return null;
				}
			}
		}
		
		return obstacles;
	}
	
	@Override
	public SpaceThingType getShipType() {
		return SpaceThingType.DestroyerShip;
		
	}
}
