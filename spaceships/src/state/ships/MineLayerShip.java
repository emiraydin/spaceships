package state.ships;

import gameLogic.Constants.ArmourType;

import java.util.LinkedList;

import state.Mine;
import state.weapons.Cannon;
import state.weapons.MineLayer;

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
	 * Sonar
	 */
	protected int sonarVisibilityWidth;
	protected int sonarVisibilityHeight;
	
	
	/**
	 * Default constructor. 
	 * Sets the basic properties for the Destroyer ship. 
	 */
	public MineLayerShip()
	{
		// Length is 2 and health points are 1?
		super(2, ArmourType.Heavy, 6, 1, 1, 1, 1, 1);		
		
		mines = new LinkedList<Mine>();
		
		this.addWeapon(new Cannon());
		this.addWeapon(new MineLayer());
	}
	
	public int getSonarVisibilityWidth() {
		return this.sonarVisibilityWidth;
	}	
	public int getSonarVisibilityHeight() {
		return this.sonarVisibilityHeight;
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
