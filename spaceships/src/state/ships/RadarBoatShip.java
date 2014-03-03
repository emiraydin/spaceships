package state.ships;

import gameLogic.Constants.ArmourType;

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
		super(3, ArmourType.Normal, 3, 3, 5, 0, 3, 6);
//		this.shipSize = 2; 
//		this.shipSpeed = 6; 
//		this.shipArmor = shipArmorTypes.Heavy; 
//		this.shipWeapons = new LinkedList<shipWeaponTypes>(); 
//		this.shipWeapons.add(shipWeaponTypes.LightCannon);
//		this.shipWeapons.add(shipWeaponTypes.Mine); 
	}
}
