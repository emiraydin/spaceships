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


public class TorpedoBoatShip extends AbstractShip {
	public TorpedoBoatShip(int x, int y, FleetCommander owner, StarBoard gameBoard) {
		super(x, y, owner, gameBoard);
		
		this.length = 3;
		this.speed = 9;
		
		initializeHealth(this.length, false);
		
		this.cannonWidth = 5;
		this.cannonLength = 5;
		this.cannonLengthOffset = 0;
		
		this.radarVisibilityLength = 6;
		this.radarVisibilityWidth = 3;
		this.radarVisibilityLengthOffset = 1;
		
		// Torpedo Boats have cannons and torpedoes
		arsenal = new AbstractWeapon[2];
		arsenal[0] = new Cannon(this);
		arsenal[1] = new TorpedoLauncher(this);
	}
	
	/**
	 * Identical to method in RadarBoatShip
	 */
	private boolean tryTurning90(ActionType turnDirection) { 
		// Get obstacles in turn zone
		List<Point> obstaclesInTurnZone = getObstaclesIn90DegreeTurnZone(this.getX(), this.getY(), 
				this.orientation, turnDirection, true);
		
		// if obstaclesInTurnZone returned null, it means the turn puts the ship out of bounds!
		if(obstaclesInTurnZone == null) { 
			System.out.println("Turn not completed because out of bounds");
			return false;
		}
		
		return handleObstaclesWhileTurning(obstaclesInTurnZone);
	}
	
	/**
	 * Identical to method in RadarBoatShip
	 */
	private boolean tryTurning180(ActionType turnDirection) {
		if(!(turnDirection == ActionType.Turn180Left || turnDirection == ActionType.Turn180Right)) { 
			return false;
		}
		
		List<Point> obstaclesInTurnZone = new ArrayList<Point>();
		int x = this.getX();
		int y = this.getY();
		
		List<Point> obstacles1 = null;
		List<Point> obstacles2 = null;

		ActionType directionOfHalfTurns = null;
		
		if(turnDirection == ActionType.Turn180Left) { 
			directionOfHalfTurns = ActionType.TurnLeft;
		}
		else if(turnDirection == ActionType.Turn180Right) { 
			directionOfHalfTurns = ActionType.TurnRight;
		}
		
		// for first turn
		obstacles1 = getObstaclesIn90DegreeTurnZone(x, y, this.orientation, directionOfHalfTurns, true);
		// the second turn, after a single 90 degree turn has been made
		Point intermediateLocation = getLocationAfterPivot(x, y, this.orientation, directionOfHalfTurns);
		OrientationType intermediateOrientation = getOrientationAfterPivot(this.orientation, directionOfHalfTurns);
		obstacles2 = getObstaclesIn90DegreeTurnZone(intermediateLocation.x, intermediateLocation.y, 
				intermediateOrientation, directionOfHalfTurns, false);
				
		// if either list returned null, it means the turn puts the ship out of bounds!
		if(obstacles1 == null || obstacles2 == null) { 
			System.out.println("Turn not completed because out of bounds");
			return false;
		}
		
		obstaclesInTurnZone.addAll(obstacles1);
		obstaclesInTurnZone.addAll(obstacles2);
		
		return handleObstaclesWhileTurning(obstaclesInTurnZone);
	}

	/**
	 * Identical to method in RadarBoatShip
	 */
	@Override
	public boolean tryTurning(ActionType turnDirection) {
		if(turnDirection == ActionType.TurnLeft || turnDirection == ActionType.TurnRight) { 
			return tryTurning90(turnDirection);			
		}
		else if(turnDirection == ActionType.Turn180Left || turnDirection == ActionType.Turn180Right) { 
			return tryTurning180(turnDirection);
		}
		System.out.println("RadarBoatShip cannot turn in direction: " + turnDirection.name());
		return false;
		
	}

	
	/**
	 * Identical to method in RadarBoatShip
	 */
	private List<Point> getObstaclesIn90DegreeTurnZone(int x, int y, OrientationType orientation, ActionType turnDirection, 
			boolean includeFinalPosition) {
		List<Point> obstacles = new ArrayList<Point>();
//		int x = this.getX();
//		int y = this.getY();
		
		// ORIENTATION FOLLOWS NEW ORIGIN CONVENTION
		switch(orientation) { 
		case East:
			if(turnDirection == ActionType.TurnLeft) { 
				// in turn radius
				if(!this.addObstacleToList(x+2, y+1, obstacles)) { 
					return null;
				}
				if(!this.addObstacleToList(x, y-1, obstacles)) { 
					return null;
				}
				// final position of ship
				if(includeFinalPosition) { 
					// head
					if(!this.addObstacleToList(x+1, y+1, obstacles)) { 
						return null;
					}
					// tail
					if(!this.addObstacleToList(x+1, y-1, obstacles)) { 
						return null;
					}
				}
			}
			else if(turnDirection == ActionType.TurnRight) { 
				// in turn radius
				if(!this.addObstacleToList(x+2, y-1, obstacles)) { 
					return null;
				}
				if(!this.addObstacleToList(x, y+1, obstacles)) { 
					return null;
				}
				// final position of ship
				if(includeFinalPosition) { 
					// head
					if(!this.addObstacleToList(x+1, y-1, obstacles)) { 
						return null;
					}
					// tail
					if(!this.addObstacleToList(x+1, y+1, obstacles)) { 
						return null;
					}
				}
			}
		case West:
			if(turnDirection == ActionType.TurnLeft) { 
				// in turn radius
				if(!this.addObstacleToList(x-2, y-1, obstacles)) { 
					return null;
				}
				if(!this.addObstacleToList(x, y+1, obstacles)) { 
					return null;
				}
				// final position of ship
				if(includeFinalPosition) { 
					// head
					if(!this.addObstacleToList(x-1, y-1, obstacles)) { 
						return null;
					}
					// tail
					if(!this.addObstacleToList(x-1, y+1, obstacles)) { 
						return null;
					}
				}
			}
			else if(turnDirection == ActionType.TurnRight) { 
				// in turn radius
				if(!this.addObstacleToList(x-2, y+1, obstacles)) { 
					return null;
				}
				if(!this.addObstacleToList(x, y-1, obstacles)) { 
					return null;
				}
				// final position of ship
				if(includeFinalPosition) { 
					// head
					if(!this.addObstacleToList(x-1, y+1, obstacles)) { 
						return null;
					}
					// tail
					if(!this.addObstacleToList(x-1, y-1, obstacles)) { 
						return null;
					}
				}
			}
			break;
		case North:
			if(turnDirection == ActionType.TurnLeft) { 
				// in turn radius
				if(!this.addObstacleToList(x-1, y+2, obstacles)) { 
					return null;
				}
				if(!this.addObstacleToList(x+1, y, obstacles)) { 
					return null;
				}
				// final position of ship
				if(includeFinalPosition) { 
					// head
					if(!this.addObstacleToList(x-1, y+1, obstacles)) { 
						return null;
					}
					// tail
					if(!this.addObstacleToList(x+1, y+1, obstacles)) { 
						return null;
					}
				}
			}
			else if(turnDirection == ActionType.TurnRight) { 
				// in turn radius
				if(!this.addObstacleToList(x+1, y+2, obstacles)) { 
					return null;
				}
				if(!this.addObstacleToList(x-1, y, obstacles)) { 
					return null;
				}
				// final position of ship
				if(includeFinalPosition) { 
					// head
					if(!this.addObstacleToList(x+1, y+1, obstacles)) { 
						return null;
					}
					// tail
					if(!this.addObstacleToList(x-1, y+1, obstacles)) { 
						return null;
					}
				}
			}
			break;
		case South:
			if(turnDirection == ActionType.TurnLeft) { 
				// in turn radius
				if(!this.addObstacleToList(x+1, y-2, obstacles)) { 
					return null;
				}
				if(!this.addObstacleToList(x-1, y, obstacles)) { 
					return null;
				}
				// final position of ship
				if(includeFinalPosition) { 
					// head
					if(!this.addObstacleToList(x+1, y-1, obstacles)) { 
						return null;
					}
					// tail
					if(!this.addObstacleToList(x-1, y-1, obstacles)) { 
						return null;
					}
				}
			}
			else if(turnDirection == ActionType.TurnRight) { 
				// in turn radius
				if(!this.addObstacleToList(x-1, y-2, obstacles)) { 
					return null;
				}
				if(!this.addObstacleToList(x+1, y, obstacles)) { 
					return null;
				}
				// final position of ship
				if(includeFinalPosition) { 
					// head
					if(!this.addObstacleToList(x-1, y-1, obstacles)) { 
						return null;
					}
					// tail
					if(!this.addObstacleToList(x+1, y-1, obstacles)) { 
						return null;
					}
				}
			}
			break;
		}
		
		return obstacles;
	}
}
