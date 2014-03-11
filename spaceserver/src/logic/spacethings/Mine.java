package logic.spacethings;

import common.GameConstants.SpaceThingType;

import logic.FleetCommander;
import logic.StarBoard;
import messageprotocol.GameStateMessage;

public class Mine extends SpaceThing {
	
	private static final int damage = 2;
//	private FleetCommander owner;
	
	public Mine(FleetCommander owner, StarBoard gameBoard){
		super(-1, -1, owner, gameBoard);
	}
	
	/** 
	 * Mine detonates and damages anything adjacent to it.
	 * @return False if there's a problem, true otherwise.
	 */
	public boolean detonate() { 
		int x = this.getX();
		int y = this.getY();
		if(x == -1 && y == -1) { 
			System.out.println("Can't detonate unplaced mine");
			return false;
		}
		
		detonate(x, y-1);
		detonate(x, y+1);
		detonate(x-1, y);
		detonate(x+1, y);	
		
		// remove mine from game after detonation
		this.getGameBoard().clearSpaceThing(this.getX(), this.getY());
		
		return true;
	}
 	
	/**
	 * Does damage to a particular coordinate.
	 * @param x
	 * @param y
	 */
	private void detonate(int x, int y){
		if (getGameBoard().getSpaceThing(x, y) instanceof AbstractShip){
			AbstractShip ship = (AbstractShip) getGameBoard().getSpaceThing(x, y);
			int section = ship.getSectionAt(x, y);
			int section2;
			if (section + 1 < ship.getLength()){
				section2 = section + 1;
			} else {
				section2 = section - 1;
			}
			ship.decrementSectionHealth(damage, section);
			ship.decrementSectionHealth(damage, section2);
		}
		
	}
	
	public static int getDamage(){
		return damage;
	}
	
//	public FleetCommander getOwner(){
//		return owner;
//	}
//	
//	public void setOwner(FleetCommander fc){
//		owner = fc;
//	}
	
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
