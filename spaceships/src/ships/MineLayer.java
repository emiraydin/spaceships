package ships;

import java.util.LinkedList;

/**
 * Class for the MineLayer Class ship. 
 * @author vikramsundaram
 *
 */
public class MineLayer extends AbstractShip
{
	
	/**
	 * Default constructor. 
	 * Sets the basic properties for the Destroyer ship. 
	 */
	public MineLayer()
	{
		this.shipSize = 2; 
		this.shipSpeed = 6; 
		this.shipArmor = shipArmorTypes.Heavy; 
		this.shipWeapons = new LinkedList<shipWeaponTypes>(); 
		this.shipWeapons.add(shipWeaponTypes.LightCannon);
		this.shipWeapons.add(shipWeaponTypes.Mine); 
	}
}
