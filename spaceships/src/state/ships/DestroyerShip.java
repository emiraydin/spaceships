package state.ships;

import state.weapons.Cannon;
import state.weapons.TorpedoLauncher;

import common.GameConstants.*;

/**
 * The Destroyer ship class. TODO: Come up with more badass names. 
 */
public class DestroyerShip extends AbstractShip
{
	/**
	 * Default constructor. 
	 * Sets the basic properties for the Destroyer ship. 
	 */
	public DestroyerShip(int id, PlayerNumber owner)
	{
		super(id, SpaceThingType.DestroyerShip, owner, 4, ArmourType.Normal, 8, 9, 12, 0);

		this.addWeapon(new Cannon());
		this.addWeapon(new TorpedoLauncher());
	}
}
