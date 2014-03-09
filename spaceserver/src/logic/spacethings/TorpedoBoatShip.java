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
	private void detonateMineWhileTurning(Mine mine) { 
		System.out.println("Mine in turn radius. Turn not completed. Mine exploded at " + mine.getX() + "," + mine.getY());
		// if mine adjacent to ship prior to moving, the detonate() method will damage ship appropriately
		// otherwise, must take care if damage in this method
		GameBoard board = this.getGameBoard();
		int x = this.getX();
		int y = this.getY(); 
		if(board.getSpaceThing(x, y+1) == mine || board.getSpaceThing(x, y-1) == mine 
				|| board.getSpaceThing(x-1, y) == mine || board.getSpaceThing(x+1, y) == mine) { 
			// damage taken care of
			mine.detonate();
		} 
		else { 
			// mine needs to be detonated, but ship also needs to be damaged
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
	}
	
	/**
	 * Identical to method in RadarBoatShip
	 */
	private boolean tryTurning90(ActionType turnDirection) { 
		// Get obstacles in turn zone
		List<Point> obstaclesInTurnZone = getObstaclesInTurnZone(this.orientation, turnDirection, true);
		
		// if obstaclesInTurnZone returned null, it means the turn puts the ship out of bounds!
		if(obstaclesInTurnZone == null) { 
			System.out.println("Turn not completed because out of bounds");
			return false;
		}
		
		/* If there's a ship, asteroid or base in the way, turn is cancelled due to collision */
		boolean collision = false;
		for(Point coord : obstaclesInTurnZone) { 
			SpaceThing spaceThing = this.getGameBoard().getSpaceThing(coord.x, coord.y);
			if(spaceThing instanceof AbstractShip || spaceThing instanceof Asteroid || spaceThing instanceof BaseTile) { 
				// TODO: HANDLE COLLISION AT COORD.X, COORD.Y!
				System.out.println("Collision at (" + coord.x + "," + coord.y + ") !");
				collision = true;
			}
		}
		if(collision) { 
			// collision => turn not completed
			return false;
		}
		
		/* If there's a mine in the way, it detonates */
		for(Point coord : obstaclesInTurnZone) { 
			SpaceThing spaceThing = this.getGameBoard().getSpaceThing(coord.x, coord.y);
			if(spaceThing instanceof Mine) { 				
				detonateMineWhileTurning((Mine)spaceThing);
				// mine exploded => turn not completed
				return false;
			}
		}
		
		// no collisions or mines => turn completed
		return true;
	}
	
	/** 
	 * Identical to method in RadarBoatShip
	 */
	private boolean tryTurning180() { 
		// TODO: figure out how turning 180 works
		return false;
	}

	/**
	 * Identical to method in RadarBoatShip
	 */
	@Override
	public boolean tryTurning(ActionType turnDirection) {
		// Check that the turn is legal
		if(!(turnDirection == ActionType.TurnLeft || turnDirection == ActionType.TurnRight) || turnDirection == ActionType.Turn180) { 
			System.out.println("RadarBoatShip cannot turn in direction: " + turnDirection.name());
			return false;
		}
	
		boolean turnSuccessful = false;
		// If ship turning 90 degrees, straight-forward
		if(turnDirection == ActionType.TurnLeft || turnDirection == ActionType.TurnRight) { 
			turnSuccessful = tryTurning90(turnDirection);			
		}
		else { 
			turnSuccessful = tryTurning180();
		}	
		
		return turnSuccessful;
	}

	
	/**
	 * Identical to method in RadarBoatShip
	 */
	private List<Point> getObstaclesInTurnZone(OrientationType orientation, ActionType turnDirection, 
			boolean includeFinalPosition) {
		List<Point> obstacles = new ArrayList<Point>();
		int x = this.getX();
		int y = this.getY();
		
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
