package state.ships;

import java.util.LinkedList;

/**
 * Class for the RadarShip type. 
 */
public class RadarBoatShip extends AbstractShip
{
	/**
	 * Default constructor. 
	 * Sets the basic properties for the Destroyer ship. 
	 */
	public RadarBoatShip()
	{
		this.shipSize = 2; 
		this.shipSpeed = 6; 
		this.shipArmor = shipArmorTypes.Heavy; 
		this.shipWeapons = new LinkedList<shipWeaponTypes>(); 
		this.shipWeapons.add(shipWeaponTypes.LightCannon);
		this.shipWeapons.add(shipWeaponTypes.Mine); 
	}
}
