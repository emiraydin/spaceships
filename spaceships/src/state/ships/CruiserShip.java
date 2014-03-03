package state.ships;

import gameLogic.Constants.ArmourType;

/**
 * The Cruiser Class Ship. TODO: Come up with a cooler name. 
 */
public class CruiserShip extends AbstractShip
{
	/**
	 * Default constructor. 
	 * Sets the basic properties for the cruiser ship. 
	 */
	public CruiserShip()
	{
		super(5, ArmourType.Heavy, 10, 11, 15, 0, 3, 10);
//		this.shipWeapons = new LinkedList<shipWeaponTypes>(); 
//		this.shipWeapons.add(shipWeaponTypes.HeavyCannon); 
	}
}
