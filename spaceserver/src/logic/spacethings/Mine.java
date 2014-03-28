package logic.spacethings;

import common.GameConstants.SpaceThingType;

import logic.FleetCommander;
import logic.StarBoard;
import messageprotocol.GameStateMessage;

public class Mine extends SpaceThing {
	
	private static final int damage = 2;
	
	public Mine(FleetCommander owner, StarBoard gameBoard){
		super(-1, -1, owner, gameBoard);
	}
	
	/**
	 * Removes mine, does damage to ship that detonates it.
	 * @param x Of the ship detonating mine
	 * @param y Of the ship detonating mine
	 * @return True if detonation successful, false otherwise
	 */
	public boolean detonate(int x, int y) { 
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
