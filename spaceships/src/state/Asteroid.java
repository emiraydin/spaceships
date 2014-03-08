package state;

import gameLogic.Constants.PlayerNumber;
import gameLogic.Constants.SpaceThingType;

/**
 * An asteroid in space.
 */
public class Asteroid extends SpaceThing
{

	public Asteroid(int id) {
		super(id, SpaceThingType.Asteroid, PlayerNumber.World);
	}

}
