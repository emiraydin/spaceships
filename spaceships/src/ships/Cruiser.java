package ships;

import java.util.LinkedList;

/**
 * The Cruiser Class Ship. TODO: Come up with a cooler name. 
 * @author vikramsundaram
 *
 */
public class Cruiser extends AbstractShip
{
	/**
	 * Default constructor. 
	 * Sets the basic properties for the cruiser ship. 
	 */
	public Cruiser()
	{
		this.shipSize = 5; 
		this.shipSpeed = 10; 
		this.shipArmor = shipArmorTypes.Heavy; 
		this.shipWeapons = new LinkedList<shipWeaponTypes>(); 
		this.shipWeapons.add(shipWeaponTypes.HeavyCannon); 
	}
}
