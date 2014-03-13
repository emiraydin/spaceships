package logic.spacethings;

import logic.FleetCommander;
import logic.StarBoard;
import messageprotocol.GameStateMessage;

import common.GameConstants.SpaceThingType;

public class BaseTile extends SpaceThing {
	private int health = 1;
	
	public BaseTile(int x, int y, FleetCommander owner, StarBoard gameBoard){
		super(x, y, owner, gameBoard);
		// Yeah... don't get mad.
		owner.getHandler().getFleetCommander(0).incrementRadarVisibility(x, y);
		owner.getHandler().getFleetCommander(1).incrementRadarVisibility(x, y);
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}
	
	public void decrementBaseHealth(int amount) { 
		this.health = this.health - amount;
	}

	@Override
	public GameStateMessage genGameStateMessage() {
		return new GameStateMessage(getID(), null, SpaceThingType.BaseTile, 
				getX(), getY(), null, new int[]{health});
	}
	
	
}
