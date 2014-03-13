package logic.spacethings;

import logic.FleetCommander;
import logic.StarBoard;
import messageprotocol.GameStateMessage;

import common.GameConstants.SpaceThingType;

public class BaseTile extends SpaceThing {
	private int health = 1;
	
	public BaseTile(int x, int y, FleetCommander owner, StarBoard gameBoard){
		super(x, y, owner, gameBoard);
		owner.incrementRadarVisibility(x, y);
		if(StarBoard.inBounds(x+1, y)) { 
			owner.incrementRadarVisibility(x+1, y);
		}
		if(StarBoard.inBounds(x-1, y)) { 
			owner.incrementRadarVisibility(x-1, y);
		}
		if(StarBoard.inBounds(x, y-1)) { 
			owner.incrementRadarVisibility(x, y-1);
		}
		if(StarBoard.inBounds(x, y+1)) { 
			owner.incrementRadarVisibility(x, y+1);
		}
		
		// Opponent gets visibility
		// Don't worry, this is very clean code.  I'm sure of it.
		owner.getHandler().getFleetCommander((owner.getID() + 1) % 2).incrementRadarVisibility(x, y);
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}
	
	public void decrementBaseHealth(int amount) {
		if (health != 0){
			this.health = this.health - amount;
			}
			if (health <= 0){
				health = 0;
				getOwner().decrementRadarVisibility(getX(), getY());
				getOwner().decrementRadarVisibility(getX()+1, getY()-1);
				getOwner().decrementRadarVisibility(getX()+1, getY()+1);
				getOwner().decrementRadarVisibility(getX()+1, getY()+1);
				getOwner().decrementRadarVisibility(getX()-1, getY()+1);
				
				// Yeah... don't get mad.
				getOwner().getHandler().getFleetCommander((getOwner().getID() + 1) % 2).
					decrementRadarVisibility(getX(), getY());
		}
	}

	@Override
	public GameStateMessage genGameStateMessage() {
		return new GameStateMessage(getID(), null, SpaceThingType.BaseTile, 
				getX(), getY(), null, new int[]{health});
	}
	
	
}
