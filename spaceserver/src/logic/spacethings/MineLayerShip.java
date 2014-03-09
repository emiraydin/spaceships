package logic.spacethings;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import logic.AbstractWeapon;
import logic.Cannon;
import logic.GameBoard;
import logic.MineLayer;

import common.GameConstants.ActionType;


public class MineLayerShip extends AbstractShip {
	private ArrayList<Mine> mines;
	private static int NUM_MINES = 5;
	
	public MineLayerShip(int x, int y, GameBoard gameBoard){
		super(x, y, gameBoard);
		
		mines = new ArrayList<Mine>(NUM_MINES);
		for (int i = 0; i < NUM_MINES; i++){
			//TODO: Add id generation
			// ^ fixed? lol
			mines.add(new Mine(gameBoard));
		}
		
		this.length = 2;
		this.speed = 6;
		
		initializeHealth(this.length, true);
		
		this.cannonWidth = 5;
		this.cannonLength = 4;
		this.cannonLengthOffset = -1;
		
		this.radarVisibilityLength = 6;
		this.radarVisibilityWidth = 5;
		this.radarVisibilityLengthOffset = -2;
		
		this.arsenal = new AbstractWeapon[2];
		arsenal[0] = new Cannon(this);
		arsenal[1] = new MineLayer(this);
	}
	
	public Mine removeMine(){
		if (mines.isEmpty()){
			return null;
		} else {
			return mines.remove(mines.size() - 1);
		}
	}
	
	public void pickUpMine(Mine mine){
		mines.add(mine);
		mine.setLocation(-1, -1);
	}
	
	/**
	 * Checks if a coordinate is within sonar range.
	 * The sonar range for a MineLayerShip is the squares directly adjacent to it.
	 * @param x
	 * @param y
	 * @return True of in sonar range, false otherwise.
	 */
	public boolean inSonarRange(int x, int y) { 
		if(!GameBoard.inBounds(x, y)) { 
			return false;
		}
		
		int shipX = this.getX();
		int shipY = this.getY();
		int shipLength = this.getLength();
		
		switch(this.getOrientation()) { 
		case East:
			if(x >= shipX - 1 && x <= shipX + shipLength) { 
				if(y >= shipY - 1 && y <= shipY + 1) { 
					return true;
				}
			}
			break;
		case West:
			if(x >= shipX - shipLength && x <= shipX + 1) { 
				if(y >= shipY - 1 && y <= shipY + 1) { 
					return true;
				}
			}
			break;
		case North:
			if(x >= shipX - 1 && x <= shipX + 1) { 
				if(y >= shipY - shipLength && y <= shipY + 1) { 
					return true;
				}
			}
			break;
		case South:
			if(x >= shipX - 1 && x <= shipX + 1) { 
				if(y >= shipY - 1 && y <= shipY + shipLength) { 
					return true;
				}
			}
			break;
		}
		return false;
	}

	@Override
	public boolean tryTurning(ActionType direction) {
		if(!(direction == ActionType.TurnLeft || direction == ActionType.TurnRight)) { 
			System.out.println("MineLayerShip cannot turn in direction: " + direction.name());
			return false;
		}
		
		List<Point> obstaclesInTurnZone = getObstaclesInTurnZone(direction);
		
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
			return false;
		}
		
		/* If there's a mine in the way, turn doesn't happen. No detonation though */
		for(Point coord : obstaclesInTurnZone) { 
			SpaceThing spaceThing = this.getGameBoard().getSpaceThing(coord.x, coord.y);
			if(spaceThing instanceof Mine) { 
				System.out.println("Mine in turn radius. Turn not completed.");
				return false;
			}
		}
		
		/* Otherwise, nothing in the way - can turn & turn successful */
		return true;
		
		
	}
	
	/**
	 * Gets the coordinates for all the obstacles in the turn zone. 
	 * @param turnDirection The direction of the desired turn.
	 * @return null if the turn puts the ship out of bounds,
	 *         otherwise a list of the coordinates of obstacles in the way of the turn
	 *         (ships are multiple coordinates for multiple collisions).
	 */
	protected List<Point> getObstaclesInTurnZone(ActionType turnDirection) {
		List<Point> obstacles = new ArrayList<Point>();
		
		int x = this.getX();
		int y = this.getY();
		
		// ORIENTATION FOLLOWS NEW ORIGIN CONVENTION
		switch(this.getOrientation()) { 
		case East:
			if(turnDirection == ActionType.TurnLeft) { 
				// in turn radius
				if(!this.addObstacleToList(x+1, y+1, obstacles)) { 
					return null;
				}
				// final position of ship
				if(!this.addObstacleToList(x, y+1, obstacles)) { 
					return null;
				}
				
			}
			else if(turnDirection == ActionType.TurnRight) { 
				// in turn radius
				if(!this.addObstacleToList(y+1, y-1, obstacles)) { 
					return null;
				}
				// final position of ship
				if(!this.addObstacleToList(x, y-1, obstacles)) { 
					return null;
				}
			} 
			break;
		case West:
			if(turnDirection == ActionType.TurnLeft) { 
				// in turn radius
				if(!this.addObstacleToList(x-1, y-1, obstacles)) { 
					return null;
				}			
				// final position of ship
				if(!this.addObstacleToList(x, y-1, obstacles)) { 
					return null;
				}
			}
			else if(turnDirection == ActionType.TurnRight) { 
				// in turn radius
				if(!this.addObstacleToList(x-1, y+1, obstacles)) { 
					return null;
				}
				// final position of ship
				if(!this.addObstacleToList(x, y+1, obstacles)) { 
					return null;
				}
			}
			break;
		case North:
			if(turnDirection == ActionType.TurnLeft) { 
				// in turn radius
				if(!this.addObstacleToList(x-1, y+1, obstacles)) { 
					return null;
				}
				// final position of ship
				if(!this.addObstacleToList(x-1, y, obstacles)) { 
					return null;
				}
			}
			else if(turnDirection == ActionType.TurnRight) { 
				// in turn radius
				if(!this.addObstacleToList(x+1, y+1, obstacles)) { 
					return null;
				}
				// final position of ship
				if(!this.addObstacleToList(x+1, y, obstacles)) { 
					return null;
				}
			}
			break;
		case South:
			if(turnDirection == ActionType.TurnLeft) { 
				// in turn radius
				if(!this.addObstacleToList(x+1, y-1, obstacles)) { 
					return null;
				}
				// final position of ship
				if(!this.addObstacleToList(x+1, y, obstacles)) { 
					return null;
				}
			}
			else if(turnDirection == ActionType.TurnRight) { 
				// in turn radius
				if(!this.addObstacleToList(x-1, y-1, obstacles)) { 
					return null;
				}
				// final position of ship
				if(!this.addObstacleToList(x-1, y, obstacles)) { 
					return null;
				}
			}
			break;
		}
		
		return obstacles;
	}
	
	
}
