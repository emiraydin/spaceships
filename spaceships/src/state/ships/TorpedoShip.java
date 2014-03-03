package state.ships;

import gameLogic.Constants.ArmourType;

/**
 * The Class for the Torpedo Ship type vessel. 
 */
public class TorpedoShip extends AbstractShip
{

	/**
	 * Default constructor. 
	 * Sets the basic properties for the Destroyer ship. 
	 */
	public TorpedoShip()
	{
		super(3, ArmourType.Normal, 9, 5, 5, 1, 3, 6);
//		this.shipWeapons = new LinkedList<shipWeaponTypes>(); 
//		this.shipWeapons.add(shipWeaponTypes.LightCannon);
//		this.shipWeapons.add(shipWeaponTypes.Torpedo); 
	}
}
