package logic.spacethings;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import common.GameConstants.ActionType;
import common.GameConstants.OrientationType;

import logic.AbstractWeapon;
import logic.Cannon;
import logic.GameBoard;
import logic.TorpedoLauncher;


public class TorpedoBoatShip extends AbstractShip {
	public TorpedoBoatShip(int x, int y, GameBoard gameBoard) {
		super(x, y, gameBoard);
		
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
		List<Point> obstaclesInTurnZone = getObstaclesInTurnZone(this.getX(), this.getY(), 
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
		
		if(turnDirection == ActionType.Turn180Left) { 
			// two 90 degree turns to the left
			obstacles1 = getObstaclesInTurnZone(x, y, this.orientation, ActionType.TurnLeft, true);
			
			// the second turn, after a single 90 degree turn has been made
			if(this.orientation == OrientationType.East) { 
				obstacles2 = getObstaclesInTurnZone(x+1, y-1, OrientationType.North, ActionType.TurnLeft, false);
			}
			else if(this.orientation == OrientationType.West) { 
				obstacles2 = getObstaclesInTurnZone(x-1, y+1, OrientationType.South, ActionType.TurnLeft, false);
			}
			else if(this.orientation == OrientationType.North) { 
				obstacles2 = getObstaclesInTurnZone(x+1, y+1, OrientationType.West, ActionType.TurnLeft, false);
			}
			else if(this.orientation == OrientationType.South){ 
				obstacles2 = getObstaclesInTurnZone(x-1, y-1, OrientationType.East, ActionType.TurnLeft, false);
			}
		} 
		else if(turnDirection == ActionType.Turn180Right) { 
			// two 90 degree turns to the left
			obstacles1 = getObstaclesInTurnZone(x, y, this.orientation, ActionType.TurnRight, true);
			
			// the second turn, after a single 90 degree turn has been made
			if(this.orientation == OrientationType.East) { 
				obstacles2 = getObstaclesInTurnZone(x+1, y+1, OrientationType.South, ActionType.TurnRight, false);
			}
			else if(this.orientation == OrientationType.West) { 
				obstacles2 = getObstaclesInTurnZone(x-1, y-1, OrientationType.North, ActionType.TurnRight, false);
			}
			else if(this.orientation == OrientationType.North) { 
				obstacles2 = getObstaclesInTurnZone(x-1, y+1, OrientationType.East, ActionType.TurnRight, false);
			}
			else if(this.orientation == OrientationType.South){ 
				obstacles2 = getObstaclesInTurnZone(x+1, y-1, OrientationType.West, ActionType.TurnRight, false);
			}
		}
				
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
	private List<Point> getObstaclesInTurnZone(int x, int y, OrientationType orientation, ActionType turnDirection, 
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
