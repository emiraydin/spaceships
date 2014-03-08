package state;

import gameLogic.Constants.PlayerNumber;
import gameLogic.Constants.SpaceThingType;

/**
 * These are the tiles that make up the fleet commander's base.
 */
public class BaseTile extends SpaceThing {

	int health;
	
	public BaseTile(int id, PlayerNumber owner)
	{
		super(id, SpaceThingType.BaseTile, owner);
		this.health = 1;
	}

}
