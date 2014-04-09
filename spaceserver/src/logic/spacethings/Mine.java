package logic.spacethings;

import java.awt.Point;
import java.util.HashSet;

import logic.FleetCommander;
import logic.StarBoard;
import messageprotocol.GameStateMessage;

import common.GameConstants.SpaceThingType;

public class Mine extends SpaceThing {
	
	private static final int damage = 2;
	
	public Mine(FleetCommander owner, StarBoard gameBoard){
		super(-1, -1, owner, gameBoard);
	}
	
	/**
	 * Removes mine, does damage to ship that detonates it at the specified location.
	 * @param x Of the ship detonating mine to be damaged
	 * @param y Of the ship detonating mine to be damaged
	 * @return True if detonation successful, false otherwise
	 */
	public boolean detonateWhileTurning(int x, int y) { 
		if(this.getX() == -1 && this.getY() == -1) { 
			System.out.println("Can't detonate unplaced mine");
			return false;
		}
		
		if (getGameBoard().getSpaceThing(x, y) instanceof AbstractShip){
			AbstractShip ship = (AbstractShip) getGameBoard().getSpaceThing(x, y);
			int section = ship.getSectionAt(x, y);
			ship.decrementSectionHealth(damage, section);
 			
			// catch for kamikaze ship
			if(ship.getLength() > 1) { 
				int section2;
				if (section + 1 < ship.getLength()){
					section2 = section + 1;
				} else {
					section2 = section - 1;
				}				
				ship.decrementSectionHealth(damage, section2);
			}
			
		}	
		
		// remove mine from game after detonation
		this.getGameBoard().clearSpaceThing(this.getX(), this.getY());	
		this.setX(-2);
		this.setY(-2);		
		return true;
	}
	
	/**
	 * Detonates a mine and damages the squares around it.
	 * If ships there, damages (including minelayers that may be sitting there).
	 * If other mine, safely removes other mine (like a cannon collision).
	 * @return True if successful detonation, false otherwise.
	 */
	public boolean detonate() { 
		if(this.getX() == -1 && this.getY() == -1) { 
			System.out.println("Can't detonate unplaced mine");
			return false;
		}
		
		StarBoard board = getGameBoard();
		int x = this.getX();
		int y = this.getY();
		
		HashSet<Point> surroundings = new HashSet<Point>();
		if(StarBoard.inBounds(x, y+1) && board.getSpaceThing(x, y+1) != null) { 
				surroundings.add(new Point(x, y+1));
		}
		if(StarBoard.inBounds(x, y-1) && board.getSpaceThing(x, y-1) != null) { 
			surroundings.add(new Point(x, y-1));
		}
		if(StarBoard.inBounds(x+1, y) && board.getSpaceThing(x+1, y) != null) { 
			surroundings.add(new Point(x+1, y));
		}
		if(StarBoard.inBounds(x-1, y) && board.getSpaceThing(x-1, y) != null) { 
			surroundings.add(new Point(x-1, y));
		}
		
		System.out.println("Mine detonating with #surroundings: " + surroundings.size());
		for(Point coord : surroundings) { 
			SpaceThing spaceThing = board.getSpaceThing(coord.x, coord.y);
			if(spaceThing instanceof AbstractShip) { 
				AbstractShip ship = (AbstractShip)spaceThing;
				
				int section = ship.getSectionAt(coord.x, coord.y);
				ship.decrementSectionHealth(damage, section);
				System.out.println("Decrementing health of " + ship.getShipType().toString() + " at section " + section);
	 			
				// catch for kamikaze ship
				if(ship.getLength() > 1) { 
					int section2;
					if (section + 1 < ship.getLength()){
						section2 = section + 1;
					} else {
						section2 = section - 1;
					}				
					ship.decrementSectionHealth(damage, section2);
					System.out.println("Decrementing health of " + ship.getShipType().toString() + " at section " + section2);
				}
			}
			else if(spaceThing instanceof Mine) { 
				Mine mine = (Mine)spaceThing;			
				// clear the mine safely
				board.clearSpaceThing(mine.getX(), mine.getY());	
				mine.setX(-2);
				mine.setY(-2);				
			}
		}
		
		// remove mine itself
		this.getGameBoard().clearSpaceThing(this.getX(), this.getY());
		this.setX(-2);
		this.setY(-2);
		
		return true;
	}
	
	public static int getDamage(){
		return damage;
	}
	
	public void setLocation(int x, int y) { 
		this.setX(x);
		this.setY(y);
	}

	@Override
	public GameStateMessage genGameStateMessage() {
		return new GameStateMessage(getID(), getOwner().getPlayer(), SpaceThingType.Mine, 
				getX(), getY(), null, null);
	}
	
}
