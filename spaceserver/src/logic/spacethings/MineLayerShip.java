package logic.spacethings;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import logic.AbstractWeapon;
import logic.Cannon;
import logic.FleetCommander;
import logic.MineLayer;
import logic.StarBoard;

import common.GameConstants.ActionType;
import common.GameConstants.OrientationType;
import common.GameConstants.SpaceThingType;


public class MineLayerShip extends AbstractShip {
	private ArrayList<Mine> mines;
	private static int NUM_MINES = 5;
	
	public MineLayerShip(int x, int y, OrientationType oType, FleetCommander owner, StarBoard gameBoard){
		super(x, y, oType, owner, gameBoard);
		
		mines = new ArrayList<Mine>(NUM_MINES);
		for (int i = 0; i < NUM_MINES; i++){
			mines.add(new Mine(owner, gameBoard));
		}
		
		this.length = 2;
		this.setMaxSpeed(6);
		
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
	
	public boolean pickUpMine(Mine mine){
		if(inSonarRange(mine.getX(), mine.getY())) { 
			mines.add(mine);
			mine.setLocation(-1, -1);
			mine.setOwner(this.getOwner());
			this.getGameBoard().clearSpaceThing(mine.getX(), mine.getY());
			return true;
		}
		return false;
		
	}
	
	/**
	 * Checks if a coordinate is within sonar range.
	 * The sonar range for a MineLayerShip is the squares directly adjacent to it.
	 * @param x
	 * @param y
	 * @return True of in sonar range, false otherwise.
	 */
	public boolean inSonarRange(int x, int y) { 
		// TODO: cant drop a mine directly under a ship
		if(!StarBoard.inBounds(x, y)) { 
			return false;
		}
		
		int shipX = this.getX();
		int shipY = this.getY();
		int shipLength = this.getLength();
		
		// UPDATED TO NEW ORIGIN CONVENTION
		switch(this.getOrientation()) { 
		case East:
			// behind/in front
			if(y == shipY) { 
				if(x == shipX-1 || x == shipX + shipLength) { 
					return true;
				}
			}
			// along ship length
			else if(y == shipY+1 || y == shipY-1) { 
				if(shipX <= x && x <= shipX + shipLength - 1) { 
					return true;
				}
			}
			break;
		case West:
			// behind/in front
			if(y == shipY) { 
				if(x == shipX + 1 || x == shipX - shipLength) { 
					return true;
				}
			}
			// along ship length
			else if(y == shipY+1 || y == shipY-1) { 
				if(shipX >= x && x >= shipX - shipLength + 1) { 
					return true;
				}
			}
			break;
		case North:
			// behind/in front
			if(x == shipX) { 
				if(y == shipY + shipLength || y == shipY-1) { 
					return true;
				}
			}
			// along ship length
			else if(x == shipX-1 || x == shipX+1) { 
				if(shipY <= y && y <= shipY + shipLength - 1) { 
					return true;
				}
			}
			break;
		case South:
			// in front/behind
			if(x == shipX) { 
				if(y == shipY+1 || y == shipY + shipLength) { 
					return true;
				}
			}
			else if(x == shipX-1 || x == shipX+1) { 
				if(shipY >= y && y >= shipY - shipLength + 1) { 
					return true;
				}
			}
			break;
		}
		return false;
	}
	
	@Override
	public List<Point> getObstaclesInTurnZone(ActionType turnDirection) {
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
	
	public boolean hasMines(){
		return !mines.isEmpty();
	}
	
	public ArrayList<Mine> getMines(){
		return mines;
	}
	
	@Override
	public SpaceThingType getShipType() {
		return SpaceThingType.MineLayerShip;
		
	}
	
}
