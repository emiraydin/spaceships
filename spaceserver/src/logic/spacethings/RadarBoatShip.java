package logic.spacethings;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import logic.AbstractWeapon;
import logic.Cannon;
import logic.FleetCommander;
import logic.StarBoard;
import common.GameConstants.ActionType;
import common.GameConstants.OrientationType;
import common.GameConstants.SpaceThingType;

public class RadarBoatShip extends AbstractShip {
	public RadarBoatShip(int x, int y, OrientationType oType, FleetCommander owner, StarBoard gameBoard) {
		super(x, y, oType, owner, gameBoard);
		
		this.length = 3;
		// by default, speed is 3
		this.speed = 3;
		
		initializeHealth(this.length, false);
		
		this.cannonLength = 5;
		this.cannonWidth = 3;
		this.cannonLengthOffset = -1;
		
		// by default, radar length 6
		this.radarVisibilityLength = 6;
		this.radarVisibilityWidth = 3;
		this.radarVisibilityLengthOffset = 1;
		
		arsenal = new AbstractWeapon[1];
		arsenal[0] = new Cannon(this);
	}
	
	public void turnOnLongRadar() { 
		speed = 0;
		radarVisibilityLength = 12;
	}
	
	public void turnOffLongRadar() { 
		speed = 3;
		radarVisibilityLength = 6;
	}
	
	/**
	 * Try turning 90 degrees. If collision/mines happen, deal with them.
	 * The turn is only completed if there are no obstacles/mines.
	 * @param turnDirection
	 * @return true if turn completed (ship moved), false otherwise
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
	 * Try turning 180 degrees. If collision/mines happen, deal with them.
	 * The turn is only completed if there are no obstacles/mines.
	 * @param turnDirection
	 * @return true if turn completed (ship moved), false otherwise
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
	 * Another awkward method serving a very specific purpose to make the turning code less nasty... 
	 * For ship at some coordinate (NOT necessarily at this.getX, this.getY) in some orientation
	 * (NOT necessarily this.orientation) wanting to turn in some direction (LEFT OR RIGHT),
	 * this returns the list of obstacles in the way.
	 * @param x The x coordinate of the ship when starting turn (makes 180 turns simpler) 
	 * @param y The y coordinate of the ship when starting turn (makes 180 turns simpler)
	 * @param orientation hypothetical orientation of ship
	 * @param turnDirection desired direction of turn (LEFT OR RIGHT)
	 * @param includeFinalPosition whether or not to include the final position of the ship in the list 
	 * 		  (true for 90 degree turns, true for the first turn in a 180 turn)
	 * @return null if turn puts ship out of bounds, or otherwise list of obstacles in the way of the turn.
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
	
	@Override
	public SpaceThingType getShipType() {
		return SpaceThingType.RadarBoatShip;
		
	}
	
}
