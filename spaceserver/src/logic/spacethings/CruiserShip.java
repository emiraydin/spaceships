package logic.spacethings;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import logic.AbstractWeapon;
import logic.FleetCommander;
import logic.HeavyCannon;
import logic.StarBoard;

import common.GameConstants.ActionType;
import common.GameConstants.OrientationType;
import common.GameConstants.SpaceThingType;

public class CruiserShip extends AbstractShip {
	public CruiserShip(int x, int y, OrientationType oType, FleetCommander owner, StarBoard gameBoard){
		super(x, y, oType, owner, gameBoard);
		
		this.length = 5;
		this.setMaxSpeed(10);
		
		initializeHealth(this.length, true);
		
		this.cannonWidth = 11;
		this.cannonLength = 15;
		this.cannonLengthOffset = -5; 	// 0 offset = flush with stern
		
		// ignoring sonar..
		this.radarVisibilityWidth = 3;
		this.radarVisibilityLength = 10;
		this.radarVisibilityLengthOffset = 1;
		
		this.arsenal = new AbstractWeapon[1];
		arsenal[0] = new HeavyCannon(this);
	}
	
	/**
	 * Gets the coordinates for all the obstacles in the turn zone. 
	 * @param turnDirection The direction of the desired turn.
	 * @return null if the turn puts the ship out of bounds,
	 *         otherwise a list of the coordinates of obstacles in the way of the turn
	 *         (ships are multiple coordinates for multiple collisions).
	 */
	public List<Point> getObstaclesInTurnZone(ActionType direction) {
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
			break;
		case West:
			for(int i = 1; i < this.length; i++) { 
				if(!this.addObstacleToList(x-i, y, obstacles)) { 
					return null;
				}
			}
			break;
		case North:
			for(int i = 1; i < this.length; i++) { 
				if(!this.addObstacleToList(x, y+i, obstacles)) { 
					return null;
				}
			}
			break;
		case South:
			for(int i = 1; i < this.length; i++) { 
				if(!this.addObstacleToList(x, y-i, obstacles)) { 
					return null;
				}
			}
			break;
		}
		
		return obstacles;
	}

	@Override
	public SpaceThingType getShipType() {
		return SpaceThingType.CruiserShip;
		
	}

}
