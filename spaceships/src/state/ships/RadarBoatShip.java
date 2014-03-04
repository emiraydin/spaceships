package state.ships;

import gameLogic.Constants.ArmourType;
import gameLogic.Constants.PlayerNumber;

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
		super(id, owner, 3, ArmourType.Normal, 3, 3, 5, 0, 3, 6);
		
		this.addWeapon(new Cannon());
	}
}
