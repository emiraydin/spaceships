package state;

import gameLogic.Constants.PlayerNumber;

/**
 * An asteroid in space.
 */
public class Asteroid extends SpaceThing
{

	public Asteroid(int id) {
		super(id, PlayerNumber.World);
	}

}
