package state.ships;

import common.GameConstants.*;

import java.util.LinkedList;

import state.weapons.Cannon;

/**
 * Class for the RadarShip type. 
 */
public class RadarBoatShip extends AbstractShip
{
	/**
	 * Default constructor. 
	 * Sets the basic properties for the Destroyer ship. 
	 */
	public RadarBoatShip(int id, PlayerNumber owner)
	{
		super(id, SpaceThingType.RadarBoatShip, owner, 3, ArmourType.Normal, 3, 3, 5, -1);
		
		this.addWeapon(new Cannon());
	}
}
