package state;

import common.GameConstants.*;

/**
 * An asteroid in space.
 */
public class Asteroid extends SpaceThing
{

	public Asteroid(int id) {
		super(id, SpaceThingType.Asteroid, PlayerNumber.World);
	}

}
