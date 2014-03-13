package state.ships;

import state.weapons.HeavyCannon;

import common.GameConstants.*;

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
		super(id, SpaceThingType.CruiserShip, owner, 5, ArmourType.Heavy, 10, 11, 15, 0);
		
		// Cruiser gets a heavy cannon
		this.addWeapon(new HeavyCannon());
	}
	
}
