package state;

import gameLogic.Constants.PlayerNumber;

/**
 * These are the tiles that make up the fleet commander's base.
 */
public class BaseTile extends SpaceThing {

	int health;
	
	public BaseTile(int id, PlayerNumber owner)
	{
		super(id, owner);
		this.health = 1;
	}

}
