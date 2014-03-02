package state.ships;

import gameLogic.Constants.ArmourType;

import java.util.LinkedList;

import state.Mine;

/**
 * Class for the MineLayer Class ship. 
 */
public class MineLayerShip extends AbstractShip
{
	/**
	 * Mines!
	 */
	LinkedList<Mine> mines;
	
	
	/**
	 * Default constructor. 
	 * Sets the basic properties for the Destroyer ship. 
	 */
	public MineLayerShip()
	{
		// Length is 2 and health points are 1?
		super(2,
				ArmourType.Heavy,
				6,
				1,
				1,
				1,
//				int sonarVisibilityWidth,
//				int sonarVisibilityHeight,
				1,
				1)		
		
		mines = new LinkedList<Mine>();
		
		
//		this.shipArmor = shipArmorTypes.Heavy; 
//		this.shipWeapons = new LinkedList<shipWeaponTypes>(); 
//		this.shipWeapons.add(shipWeaponTypes.LightCannon);
//		this.shipWeapons.add(shipWeaponTypes.Mine); 
	}
	
	/**
	 * Add a mine.
	 * 
	 * @param mine
	 */
	public void addMine(Mine mine) {
		
	}
	
	/**
	 * Remove a mine.
	 * 
	 * @return
	 */
	public Mine removeMine() {
		return null;
	}
}
