package state.ships;

import state.weapons.HeavyCannon;
import gameLogic.Constants.ArmourType;
import gameLogic.Constants.PlayerNumber;

/**
 * The Cruiser Class Ship. TODO: Come up with a cooler name. 
 */
public class CruiserShip extends AbstractShip
{
	/**
	 * Default constructor. 
	 * Sets the basic properties for the cruiser ship. 
	 */
	public CruiserShip(int id, PlayerNumber owner)
	{
		super(id, owner,5, ArmourType.Heavy, 10, 11, 15, 0, 3, 10);
		
		// Cruiser gets a heavy cannon
		this.addWeapon(new HeavyCannon());
	}
}
