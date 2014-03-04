package state.ships;

import state.weapons.Cannon;
import state.weapons.TorpedoLauncher;
import gameLogic.Constants.ArmourType;
import gameLogic.Constants.PlayerNumber;

/**
 * The Class for the Torpedo Ship type vessel. 
 */
public class TorpedoShip extends AbstractShip
{

	/** 
	 * Default constructor. 
	 * Sets the basic properties for the Destroyer ship. 
	 */
	public TorpedoShip(int id, PlayerNumber owner) {
		super(id, owner, 3, ArmourType.Normal, 9, 5, 5, 0, 3, 6);

		this.addWeapon(new Cannon());
		this.addWeapon(new TorpedoLauncher());
	}
}
