
package state.ships;

import common.GameConstants.ArmourType;
import common.GameConstants.PlayerNumber;
import common.GameConstants.SpaceThingType;

/**
 * A boat that tries to blow itself up.  'Nuff said.
 *
 */
public class KamikazeBoatShip extends AbstractShip
{


	/**
	 * 
	 * @param id
	 * @param owner
	 */
	public KamikazeBoatShip(int id, PlayerNumber owner)
	{
		super(
				id,
				SpaceThingType.KamikazeBoatShip,
				owner,
				1,
				ArmourType.Heavy,
				1,
				0,
				0,
				0
				);
	}

}
