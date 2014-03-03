package state.ships;

import state.weapons.Cannon;
import state.weapons.TorpedoLauncher;
import gameLogic.Constants.ArmourType;

/**
 * The Destroyer ship class. TODO: Come up with more badass names. 
 */
public class DestroyerShip extends AbstractShip
{
	/**
	 * Default constructor. 
	 * Sets the basic properties for the Destroyer ship. 
	 */
	public DestroyerShip()
	{
		super(4, ArmourType.Normal, 8, 9, 12, 0, 3, 8);

		this.addWeapon(new Cannon());
		this.addWeapon(new TorpedoLauncher());
	}
}
