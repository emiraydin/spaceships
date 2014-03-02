package state.ships;

import java.util.LinkedList;

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
		this.shipSize = 3; 
		this.shipSpeed = 9; 
		this.shipArmor = shipArmorTypes.Light; 
		this.shipWeapons = new LinkedList<shipWeaponTypes>(); 
		this.shipWeapons.add(shipWeaponTypes.LightCannon);
		this.shipWeapons.add(shipWeaponTypes.Torpedo); 
	}
}
