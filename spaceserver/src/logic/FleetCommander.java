package logic;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import logic.spacethings.AbstractShip;
import logic.spacethings.Asteroid;
import logic.spacethings.BaseTile;
import logic.spacethings.CruiserShip;
import logic.spacethings.DestroyerShip;
import logic.spacethings.KamikazeBoatShip;
import logic.spacethings.Mine;
import logic.spacethings.MineLayerShip;
import logic.spacethings.RadarBoatShip;
import logic.spacethings.SpaceThing;
import logic.spacethings.TorpedoBoatShip;

import common.GameConstants.ActionType;
import common.GameConstants.OrientationType;
import common.GameConstants.PlayerNumber;
import common.GameConstants.VisibilityType;
import common.GameConstants.WeaponType;

public class FleetCommander {
	private int[][] sonarVisibility, radarVisibility;
	private int fcID;
	private ArrayList<AbstractShip> ships;
	private StarBoard board;
	private GameHandler handler;
	
	public FleetCommander(int fcID, StarBoard board, GameHandler handler){
		super();
		this.fcID = fcID;
		this.board = board;
		sonarVisibility = new int[30][30];
		radarVisibility = new int[30][30];
		/* 
		 * - your base has short radar range of 1 square
		 * - enemies base and coral reefs are visible
		 */
		ships = new ArrayList<AbstractShip>();
		this.handler = handler;
	}
	
	/**
	 * Gives fleet commander all appropriate ships.
	 * Does not place them on the board, but declares ownership.
	 */
	public void createShips(){
		OrientationType orientation;
		if(fcID == 0) { 
			orientation = OrientationType.East;
		}
		else { 
			orientation = OrientationType.West;
		}
		
		// these indices dont need to make sense
		// note: keep x's negative
		AbstractShip ship1 = new CruiserShip(-50, 10, orientation, this, board);
		ships.add(ship1);
		board.unplacedShips.add(ship1);
		
		AbstractShip ship2 = new CruiserShip(-50, 11, orientation, this, board);
		ships.add(ship2);
		board.unplacedShips.add(ship2);
		
		AbstractShip ship3 = new DestroyerShip(-50, 12, orientation, this, board);
		ships.add(ship3);
		board.unplacedShips.add(ship3);
		
		AbstractShip ship4 = new DestroyerShip(-50, 13, orientation, this, board);
		ships.add(ship4);
		board.unplacedShips.add(ship4);
		
		AbstractShip ship5 = new DestroyerShip(-50, 14, orientation, this, board);
		ships.add(ship5);
		board.unplacedShips.add(ship5);

		AbstractShip ship6 = new TorpedoBoatShip(-50, 15, orientation, this, board);
		ships.add(ship6);
		board.unplacedShips.add(ship6);
		
		AbstractShip ship7 = new TorpedoBoatShip(-50, 16, orientation, this, board);
		ships.add(ship7);
		board.unplacedShips.add(ship7);		
		
		AbstractShip ship8 = new MineLayerShip(-50, 17, orientation, this, board);
		ships.add(ship8);
		board.unplacedShips.add(ship8);
		
		AbstractShip ship9 = new MineLayerShip(-50, 18, orientation, this, board);
		ships.add(ship9);
		board.unplacedShips.add(ship9);		
		
		AbstractShip ship10 = new RadarBoatShip(-50, 19, orientation, this, board);
		ships.add(ship10);
		board.unplacedShips.add(ship10);
		
		AbstractShip ship11 = new KamikazeBoatShip(-50, 19, orientation, this, board);
		ships.add(ship11);
		board.unplacedShips.add(ship11);
		
		System.out.println("========= " + fcID + " ===========");
		for (AbstractShip ship : ships){
			System.out.print(ship.getID() + " - " + ship.getShipType().name());
			System.out.println((ship instanceof RadarBoatShip)?
					" lre: "+((RadarBoatShip) ship).isLongRadarEnabled():"");
		}
		System.out.println("==============================");
	}
	
	/**
	 * Places a ship at a specific location on the board.
	 * @param shipID Ship to move
	 * @param x Location to place at
	 * @param y Location to place at
	 * @return Whether placement was valid
	 */
	public boolean placeShip(int shipID, int x, int y) { 
		AbstractShip ship = getShip(shipID);
		
		if(!board.validPlaceShipLocation(ship, x, y)) { 
			setActionResponse("Cannot place a ship on top of an obstacle");
			return false;
		}
		
		// obstacle-free 		
		ship.setX(x);
		ship.setY(y);
		board.setSpaceThing(ship);
		incrementVisibility(ship);
		
		return true;
	}
	
	public void removeShip(AbstractShip ship) { 
		decrementVisibility(ship);
		ships.remove(ship);
		board.clearSpaceThing(ship);
	}
	
	public int[][] getSonarVisibility() {
		return sonarVisibility;
	}

	public int[][] getRadarVisibility() {
		return radarVisibility;
	}
	
	public GameHandler getHandler() { 
		return this.handler;
	}

	/**
	 * Perform an attack using a specified weapon.
	 * @param wType Weapon to be used.
	 * @param shipID Ship which should perform the attack
	 * @param x Location at which to use the weapon
	 * @param y Location at which to use the weapon
	 * @return True if the action was performed.  False if attack was invalid.
	 */
	public boolean useWeapon(WeaponType wType, int shipID, int x, int y){
		AbstractShip ship = getShip(shipID);
		return ship.useWeapon(wType, x, y);
	}
	
	/**
	 * Tries to repair a given ship.
	 * @param shipID The ID of the ship to repair.
	 * @return True of ship was successfully repaired, false otherwise.
	 */
	public boolean repairShip(int shipID) { 
		AbstractShip ship = getShip(shipID);
		if(ship.repair()) { 
			return true;
		}
		else { 
			return false;
		}
	}
	
	public boolean toggleRadar(int shipID){
		AbstractShip ship = getShip(shipID);
		if (ship instanceof RadarBoatShip){
			RadarBoatShip rShip = (RadarBoatShip) ship;
			decrementVisibility(rShip);
			if (rShip.isLongRadarEnabled()){
				setActionResponse("Long-range Radar Disabled.");
				rShip.turnOffLongRadar();
			} else {
				setActionResponse("Long-range Radar Enabled.");
				rShip.turnOnLongRadar();
			}
			incrementVisibility(rShip);
			
			return true;
		}
		setActionResponse("This ship doesn't have long-range radar capabilities.");
		return false;
	}
	
	/**
	 * Move a specified ship to a specified location.
	 * @param shipID Ship to move
	 * @param x Location to move to
	 * @param y Location to move to
	 * @return Number of spaces moved by ship
	 */
	public int moveShip(int shipID, int x, int y){
		AbstractShip ship = getShip(shipID);	
		
		if(ship.getX() == x && ship.getY() == y) { 
			setActionResponse("Move the ship at least one space");
			return 0;
		}
		
		// Kamikaze boat is the only one that is different...
		if(ship instanceof KamikazeBoatShip) { 
			return ((KamikazeBoatShip)ship).move(x, y);
		}
		
		if (validateMove(ship, x, y)){
			
			decrementVisibility(ship);
			
			int spacesMoved = 0;
			while (Math.abs((x - ship.getX()) + (y - ship.getY())) > 0){
				
				board.clearSpaceThing(ship);
				
				if(x > ship.getX()) { 
					// if ship would be moving onto an obstacle
					if(handleCollisions(ship, ship.getX()+1, ship.getY())) { 
						break;
					}
					ship.setX(ship.getX() + 1);					
				}
				else if(x < ship.getX()) { 
					// if ship would be moving onto an obstacle
					if(handleCollisions(ship, ship.getX()-1, ship.getY())) { 
						break;
					}
					ship.setX(ship.getX()-1);					
				}
				else if(y > ship.getY()) { 
					// if ship would be moving onto an obstacle
					if(handleCollisions(ship, ship.getX(), ship.getY()+1)) { 
						break;
					}	
					ship.setY(ship.getY()+1);
				} 
				else if (y < ship.getY()) { 
					// if ship would be moving onto an obstacle
					if(handleCollisions(ship, ship.getX(), ship.getY()-1)) { 
						break;
					}
					ship.setY(ship.getY()-1);
				}
				spacesMoved++;
				board.setSpaceThing(ship);
				
				// move ship and if not a mine layer, detonate any surrounding mines
				if(!(ship instanceof MineLayerShip) && handleMineExplosions(ship, ship.getX(), ship.getY())) { 
					break;
				}
				
			}
			
			incrementVisibility(ship);
			return spacesMoved;
		}
		return 0;
	}
	
	
	
	// Return true if having a ship at this position would incur a collision. (or out of bounds)
	private boolean handleCollisions(AbstractShip ship, int x, int y){
		Point[] coords = ship.getShipCoords(x, y);
		SpaceThing thing;
		for (int i = 0; i < ship.getLength(); i++){
			thing = board.getSpaceThing(coords[i].x, coords[i].y);
			if(thing instanceof AbstractShip && thing != ship) { 
				setActionResponse(String.format("Ship collision at (%d,%d)", coords[i].x, coords[i].y));
				return true;
			} else if(thing instanceof BaseTile) { 
				setActionResponse(String.format("Ship collision with a base at (%d,%d)", coords[i].x, coords[i].y));
				return true;
			} else if(thing instanceof Asteroid) { 
				setActionResponse(String.format("Ship collision with asteroid at (%d,%d)", coords[i].x, coords[i].y));
				return true;
			} else if(thing instanceof Mine) { 
				if(!(ship instanceof MineLayerShip)) { 
					System.out.println("Something weird happening - ship was next to mine without detonating");
				}
				// no action response needed
				return true;
			}
			
		}
		return false;
	}
	
	/**
	 * Checks if there are mines in the vicinity of a ship and if so, detonates them
	 * unless ship is a Mine Layer, in which case it doesn't detonate anything.
	 * @param ship
	 * @param shipX
	 * @param shipY
	 * @return True if there were mines in the vicinity, false otherwise 
	 * 		   Note: returns true even if there are undetonated mines (ship is MineLayer)
	 */
	public boolean handleMineExplosions(AbstractShip ship, int shipX, int shipY){
		Point[] surroundings = ship.getShipSurroundings(shipX, shipY);
		for(Point coords : surroundings) { 
			int x = coords.x;
			int y = coords.y;
			
			if(board.getSpaceThing(x, y) instanceof Mine) { 
				if(!(ship instanceof MineLayerShip)) { 
					Mine mine = (Mine)board.getSpaceThing(x, y);
					if(mine.detonate()) { 
						setActionResponse(String.format("Mine detonated at (%d,%d)", x, y));
					}
				}
				return true;
			}
		}
		return false;
		
//		Point[] coords = ship.getShipSurroundings(shipX, shipY);
//		for (int i = 0; i < ship.getLength(); i++){
//			int sectionX = coords[i].x;
//			int sectionY = coords[i].y;
//			if(board.getSpaceThing(sectionX+1, sectionY) instanceof Mine) { 
//				if(!(ship instanceof MineLayerShip)) { 
//					Mine mine = (Mine)board.getSpaceThing(sectionX+1, sectionY);
//					if (mine.detonate(sectionX, sectionY)){
//						setActionResponse(String.format("Mine detonated at (%d,%d)", sectionX+1, sectionY));						
//					}
//				}
//				return true;				
//			} else if(board.getSpaceThing(sectionX-1, sectionY) instanceof Mine) { 
//				if(!(ship instanceof MineLayerShip)) { 
//					Mine mine = (Mine)board.getSpaceThing(sectionX-1, sectionY);
//					if (mine.detonate(sectionX, sectionY)){
//						setActionResponse(String.format("Mine detonated at (%d,%d)", sectionX-1, sectionY));
//					}
//				}
//				return true;
//			} else if(board.getSpaceThing(sectionX, sectionY+1) instanceof Mine) { 
//				if(!(ship instanceof MineLayerShip)) { 
//					Mine mine = (Mine)board.getSpaceThing(sectionX, sectionY+1);
//					if (mine.detonate(sectionX, sectionY)){
//						setActionResponse(String.format("Mine detonated at (%d,%d)", sectionX, sectionY+1));						
//					}
//				}
//				return true;
//			} else if(board.getSpaceThing(sectionX, sectionY-1) instanceof Mine) { 
//				if(!(ship instanceof MineLayerShip)) {
//					Mine mine = (Mine)board.getSpaceThing(sectionX, sectionY-1);
//					if (mine.detonate(sectionX, sectionY)){
//						setActionResponse(String.format("Mine detonated at (%d,%d)", sectionX, sectionY-1));						
//					}
//				}
//				return true;
//			}
//		}	
//		return false;
	}
	
	/**
	 * Check if the move can be performed by the ship (checks speed, heading, bounds)
	 * Handles ALL messages back to client regarding move
	 */
	private boolean validateMove(AbstractShip ship, int x, int y){
		int shipX = ship.getX();
		int shipY = ship.getY();
		if (ship instanceof RadarBoatShip && ((RadarBoatShip) ship).isLongRadarEnabled()){
			setActionResponse("Ship cannot move while long-range radar is enabled.");
			return false;
		}
		
		// not diagonal
		if(shipX - x != 0 && shipY - y != 0) { 
			setActionResponse("Ships cannot move diagonaly");
			return false;
		}
		// can't be out of bounds
		if(!StarBoard.inBounds(x, y)) { 
			setActionResponse("Ships cannot go out of bounds");
			return false;
		}
		
		switch(ship.getOrientation()) { 
		case East:
			// backwards too far
			if(x < shipX - 1) { 
				setActionResponse("Ships can only move back 1 space at a time");
				return false;
			}
			// sideways too far
			if(y > shipY + 1 || y < shipY - 1) {
				setActionResponse("Ships can only move sideways 1 space at a time");
				return false;
			}
			// moving faster than speed allows
			if(x > shipX + ship.getSpeed()) { 
				if (ship instanceof RadarBoatShip && ((RadarBoatShip) ship).isLongRadarEnabled()){
					setActionResponse("Ship cannot move while long-range radar is enabled.");
				}
				setActionResponse("Ship cannot move that fast");
				return false;
			}
			// head goes out of bounds
			if(!StarBoard.inBounds(x + ship.getLength() - 1, shipY)) { 
				setActionResponse("Ships cannot go out of bounds");
				return false;
			}
			break;
		case West: 
			// backwards too far
			if(x > shipX + 1) { 
				setActionResponse("Ships can only move back 1 space at a time");
				return false;
			}
			// sideways too far
			if(y > shipY + 1 || y < shipY - 1) {
				setActionResponse("Ships can only move sideways 1 space at a time");
				return false;
			}
			// moving faster than speed allows
			if(x < shipX - ship.getSpeed()) { 
				setActionResponse("Ship cannot move that fast");
				return false;
			}
			// head goes out of bounds
			if(!StarBoard.inBounds(x - ship.getLength() + 1, shipY)) { 
				setActionResponse("Ships cannot go out of bounds");
				return false;
			}
			break;
		case North:
			// backwards too far
			if(y < shipY - 1) { 
				setActionResponse("Ships can only move back 1 space at a time");
				return false;
			}
			// sideways too far
			if(x > shipX + 1 || x < shipX - 1) { 
				setActionResponse("Ships can only move sideways 1 space at a time");
				return false;
			}
			// moving faster than speed allows
			if(y > shipY + ship.getSpeed()) { 
				setActionResponse("Ship cannot move that fast");
				return false;
			}
			// head goes out of bounds
			if(!StarBoard.inBounds(shipX, shipY + ship.getLength() - 1)) { 
				setActionResponse("Ships cannot go out of bounds");
				return false;
			}
			break;
		case South:
			// backwards too far
			if(y > shipY + 1) { 
				setActionResponse("Ships can only move back 1 space at a time");
				return false;
			}
			// sideways too far
			if(x > shipX + 1 || x < shipX - 1) { 
				setActionResponse("Ships can only move sideways 1 space at a time");
				return false;
			}
			// moving faster than speed allows
			if(y < shipY - ship.getSpeed()) { 
				setActionResponse("Ship cannot move that fast");
				return false;
			}
			// head goes out of bounds
			if(!StarBoard.inBounds(shipX, shipY - ship.getLength() + 1)) { 
				setActionResponse("Ships cannot go out of bounds");
				return false;
			}
			break;
		}
			
		return true;			
	}
	
	public boolean pickupMine(int shipID, int x, int y){
		AbstractShip ship = getShip(shipID);
		SpaceThing thing = board.getSpaceThing(x, y);
		if (ship instanceof MineLayerShip && thing instanceof Mine){
			if (((MineLayerShip) ship).pickUpMine((Mine) thing)){
				setActionResponse(((MineLayerShip) ship).getPrettyMineCount());
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Updates the ship (x,y) and the ship's orientation after a turn
	 * @param ship The ship that is turning
	 * @param direction The direction of turn
	 */
	private static void updateShipLocationAfterTurn(AbstractShip ship, ActionType direction) { 
		// if ship doesn't turn about stern (can turn 180)
		if(ship instanceof RadarBoatShip || ship instanceof TorpedoBoatShip) { 
			int x = ship.getX();
			int y = ship.getY();
			// THESE FOLLOW NEW ORIGIN CONVENTION
			switch(ship.getOrientation()) { 
			case East:
				if(direction == ActionType.TurnLeft) { 
					ship.setX(x+1);
					ship.setY(y-1);
					ship.setOrientation(OrientationType.North);
				}
				else if(direction == ActionType.TurnRight) { 
					ship.setX(x+1);
					ship.setY(y+1);
					ship.setOrientation(OrientationType.South);
				}
				else if(direction == ActionType.Turn180Left || direction == ActionType.Turn180Right) { 
					ship.setX(x+2);
					ship.setOrientation(OrientationType.West);
				}
				break;
			case West:
				if(direction == ActionType.TurnLeft) { 
					ship.setX(x-1);
					ship.setY(y+1);
					ship.setOrientation(OrientationType.South);
				}
				else if(direction == ActionType.TurnRight) { 
					ship.setX(x-1);
					ship.setY(y-1);
					ship.setOrientation(OrientationType.North);
				}
				else if(direction == ActionType.Turn180Left || direction == ActionType.Turn180Right) { 
					ship.setX(x-2);
					ship.setOrientation(OrientationType.East);
				}
				break;
			case North:
				if(direction == ActionType.TurnLeft) { 
					ship.setX(x+1);
					ship.setY(y+1);
					ship.setOrientation(OrientationType.West);
				}
				else if(direction == ActionType.TurnRight) { 
					ship.setX(x-1);
					ship.setY(y+1);
					ship.setOrientation(OrientationType.East);
				}
				else if(direction == ActionType.Turn180Left || direction == ActionType.Turn180Right) { 
					ship.setY(y+2);
					ship.setOrientation(OrientationType.South);
				}
				break;
			case South:
				if(direction == ActionType.TurnLeft) { 
					ship.setX(x-1);
					ship.setY(y-1);
					ship.setOrientation(OrientationType.East);
				}
				else if(direction == ActionType.TurnRight) { 
					ship.setX(x+1);
					ship.setY(y-1);
					ship.setOrientation(OrientationType.West);
				}
				else if(direction == ActionType.Turn180Left || direction == ActionType.Turn180Right) { 
					ship.setY(y-2);
					ship.setOrientation(OrientationType.North);
				}
				break;
			}
		}
		
		// other ships turn about stern and only turn 90 degrees
		// ship location doesn't change since stern is where ships are indexed at
		else { 
			// THESE FOLLOW NEW ORIGIN CONVENTION
			switch(ship.getOrientation()) { 
			case East:
				if(direction == ActionType.TurnLeft) { 
					ship.setOrientation(OrientationType.North);
				}
				else if(direction == ActionType.TurnRight) { 
					ship.setOrientation(OrientationType.South);
				}
				break;
			case West:
				if(direction == ActionType.TurnLeft) { 
					ship.setOrientation(OrientationType.South);
				}
				else if(direction == ActionType.TurnRight) { 
					ship.setOrientation(OrientationType.North);
				}
				break;
			case North:
				if(direction == ActionType.TurnLeft) { 
					ship.setOrientation(OrientationType.West);
				}
				else if(direction == ActionType.TurnRight) { 
					ship.setOrientation(OrientationType.East);
				}
				break;
			case South:
				if(direction == ActionType.TurnLeft) { 
					ship.setOrientation(OrientationType.East);
				}
				else if(direction == ActionType.TurnRight) { 
					ship.setOrientation(OrientationType.West);
				}
				break;
			}
		}
	}
	
	/** 
	 * Turns a given ship in a given direction.
	 * Updates the ship's location and orientation and updates the GameBoard location
	 * and visibility status.
	 * @param shipID ID of the ship that is turning.
	 * @param direction The direction of turn.
	 * @return True if the ship successfully turned, false otherwise.
	 */
	public boolean turnShip(int shipID, ActionType direction){
		AbstractShip ship = getShip(shipID);
		
		List<Point> obstaclesInTurnZone = ship.getObstaclesInTurnZone(direction);
		// if out of bounds
		if(obstaclesInTurnZone == null) { 
			this.setActionResponse("Ships cannot go out of bounds");
			return false;
		}
		
		// if no obstacles in the way (i.e. turn complete), update location
		if(ship.tryTurning(direction)) { 
			decrementVisibility(ship);
			board.clearSpaceThing(ship);
			updateShipLocationAfterTurn(ship, direction);
			board.setSpaceThing(ship);
			incrementVisibility(ship);			
		} 
		// if obstacles in the way, no relocation
		return true;
	}
	
	public void incrementRadarVisibility(int x, int y) { 
		radarVisibility[x][y]++;
	}
	
	public void decrementRadarVisibility(int x, int y) { 
		radarVisibility[x][y]--;
	}
	
	public void incrementVisibility(AbstractShip ship) { 
		changeVisibility(ship, 1);
	}
	
	public void decrementVisibility(AbstractShip ship) { 
		changeVisibility(ship, -1);
	}
	
	private void changeVisibility(AbstractShip ship, int change) { 
		int shipX = ship.getX();
		int shipY = ship.getY();
		
		/* Radar */
		int radarLength = ship.getRadarVisibilityLength();
		int radarWidth = ship.getRadarVisibilityWidth();
		int radarLengthOffset = ship.getRadarVisibilityLengthOffset();
		// THESE FOLLOW NEW ORIGIN CONVENTION
		int minX = -1;
		int maxX = -1;
		int minY = -1;
		int maxY = -1;
		switch(ship.getOrientation()) { 
		case East: 
			minX = shipX + radarLengthOffset;
			maxX = minX + radarLength - 1;
			minY = shipY - radarWidth/2;
			maxY = shipY + radarWidth/2;
			break;
		case West:
			maxX = shipX - radarLengthOffset;
			minX = maxX - radarLength + 1;
			minY = shipY - radarWidth/2;
			maxY = shipY + radarWidth/2;
			break;
		case South: 
			minX = shipX - radarWidth/2;
			maxX = shipX + radarWidth/2;
			maxY = shipY - radarLengthOffset;
			minY = maxY - radarLength + 1;
			break;
		case North: 
			minX = shipX - radarWidth/2;
			maxX = shipX + radarWidth/2;
			minY = shipY + radarLengthOffset;
			maxY = minY + radarLength - 1;
			break;
		}
		
		for(int i = minX; i <= maxX; i++) { 
			for(int j = minY; j <= maxY; j++) { 
				if(StarBoard.inBounds(i, j)) { 
					radarVisibility[i][j] = radarVisibility[i][j] + change;
				}
			}
		}
		// add the ship's sections too!
		Point[] coords = ship.getShipCoords();
		for(Point section : coords) { 
			int xCoord = section.x;
			int yCoord = section.y;
			if(StarBoard.inBounds(xCoord, yCoord)) { 
				radarVisibility[xCoord][yCoord] = radarVisibility[xCoord][yCoord] + change;
			}
		}
		
		/* Sonar */
		if(ship instanceof MineLayerShip) { 
			/* this should work???
			 * if you don't change the ship's section's visibility but change
			 * the surrounding tiles for each section, then you get the ship's sections
			 * covered for free right?? its 4 am why am i awake */		
			// TODO: ship's sections are NOT in sonar because you cant drop mines there
			// but for the sake of visibility it shouldnt matter? 
			// also sonar not necessary for demo #yolo
			for(Point s : coords) { 
				changeSonarVisibility(s.x, s.y-1, change);
				changeSonarVisibility(s.x, s.y+1, change);
				changeSonarVisibility(s.x-1, s.y, change);
				changeSonarVisibility(s.x+1, s.y, change);
			}
			
		}	
	}
	
	private void changeSonarVisibility(int x, int y, int change) { 
		if(StarBoard.inBounds(x, y)) { 
			sonarVisibility[x][y] = sonarVisibility[x][y] + change; 
		}
	}
	
	public VisibilityType getVisibility(int x, int y){
		if (x < 0 || y < 0 || x >= sonarVisibility.length || y >= sonarVisibility.length){
			// out of bounds
			return VisibilityType.None;
		} else if (sonarVisibility[x][y] > 0 && radarVisibility[x][y] > 0){
			return VisibilityType.Both;
		} else if (sonarVisibility[x][y] > 0){
			return VisibilityType.Sonar;
		} else if (radarVisibility[x][y] > 0){
			return VisibilityType.Radar;
		} else {
			return VisibilityType.None;
		}
	}
	
	public AbstractShip getShip(int shipID){
		for (AbstractShip ship : ships){
			if (ship.getID() == shipID){
				return ship;
			}
		}
		System.out.println("Commander ID: " + fcID);
		System.out.println("Couldn't find shipID " + shipID);
		for (AbstractShip ship : ships){
			System.out.println(ship.getID() + " - " + ship.getShipType().name());
		}
		return null;
	}
	
	public int getID(){
		return fcID;
	}
	
	public PlayerNumber getPlayer(){
		if (fcID == 0){
			return PlayerNumber.PlayerOne;
		} else if (fcID == 1){
			return PlayerNumber.PlayerTwo;
		} else {
			System.out.println("ABORT ABORT EVERYTHING IS BROKEN");
			return null;
		}
	}
	
	public boolean hasLivingShips(){
		for (AbstractShip ship : ships){
			if (!ship.isDead()){
				return true;
			}
		}
		return false;
	}
	
	public void setActionResponse(String response) { 
		this.handler.getMessageResponder().setResponseMessage(response);
	}

	public ArrayList<AbstractShip> getShips() {
		return ships;
	}
}
