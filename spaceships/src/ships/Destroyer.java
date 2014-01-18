package ships;

import java.util.LinkedList;

/**
 * The Destroyer ship class. TODO: Come up with more badass names. 
 * @author vikramsundaram
 *
 */
public class Destroyer extends AbstractShip
{
	/**
	 * Default constructor. 
	 * Sets the basic properties for the Destroyer ship. 
	 */
	public Destroyer()
	{
		this.shipSize = 4; 
		this.shipSpeed = 8; 
		this.shipArmor = shipArmorTypes.Light; 
		this.shipWeapons = new LinkedList<shipWeaponTypes>(); 
		this.shipWeapons.add(shipWeaponTypes.LightCannon);
		this.shipWeapons.add(shipWeaponTypes.Torpedoe); 
	}
}
