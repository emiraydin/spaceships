package state.ships;

import gameLogic.Constants.*;

import java.util.LinkedList;

import state.SpaceThing;
import state.weapons.AbstractWeapon;

/**
 * All ships will be instances of AbstractShips. Tie together the majority of the Game Logic. 
 * Allows easy creation of new ships. 
 *
 */
public abstract class AbstractShip extends SpaceThing
{

	/*
	 * Properties
	 */
	
	// Basic facts
	protected int speed;
	protected int length;
	protected ArmourType armour;
	protected int[] sectionHealth;
	OrientationType orientation;
	
	// Cannon properties
	protected int cannonWidth;
	protected int cannonHeight;
	protected int cannonXOffset;
	
	// Sonar Properties
	protected int sonarVisibilityWidth;
	protected int sonarVisibilityHeight;
	
	// Radar Properties
	protected int radarVisibilityWidth;
	protected int radarVisibilityHeight;
	
	// Weapons
	protected LinkedList<AbstractWeapon> weapons;	
	
	/**
	 * Constructor requires length and health for each tile.
	 * 
	 * @param length length of the ship
	 * @param health how many health points each tile has
	 */
	public AbstractShip(int length,
			ArmourType armour,
			int speed,
			int cannonWidth,
			int cannonHeight,
			int cannonXOffset,
//			int sonarVisibilityWidth,
//			int sonarVisibilityHeight,
			int radarVisibilityWidth,
			int radarVisibilityHeight) {
		
		// Build the weapons list
		this.weapons = new LinkedList<AbstractWeapon>();
		
		// We need the length to construct the sectionHealth;
		this.length = length;
		this.armour = armour;
		this.sectionHealth = new int[length];
		for (int i = 0; i < length; i++) {
			this.sectionHealth[i] = armour.ordinal() + 1;
		}
		
		// Build the standard properties
		this.speed = speed;
		this.cannonWidth = cannonWidth;
		this.cannonHeight = cannonHeight;
		this.cannonXOffset = cannonXOffset;
//		this.sonarVisibilityWidth = sonarVisibilityWidth;
//		this.sonarVisibilityHeight = sonarVisibilityHeight;
		this.radarVisibilityWidth = radarVisibilityWidth;
		this.radarVisibilityHeight = radarVisibilityHeight;
	}
	
	
	
	/*
	 * Methods
	 */
	public void useWeapon(WeaponType wType, int x, int y) {
		
	}
	
	public OrientationType getOrientation() {
		return null;
	}
	
	public void setOrientation(OrientationType oType) {
		
	}
	
	
	/*
	 * Basic Getters...
	 */
	
	public ActionType[] getPossibleActions() {
		return null;
	}
	public int getSectionAt(int x, int y) {
		return 0;
	}	
	public int getSpeed() {
		return this.speed;
	}	
	public int getLength() {
		return this.length;
	}	
	public int getCannonWidth() {
		return this.cannonWidth;
	}	
	public int getCannonHeight() {
		return this.cannonHeight;
	}	
	public int getCannonXOffset() {
		return this.cannonXOffset;
	}	
	public int getSonarVisibilityWidth() {
		return this.sonarVisibilityWidth;
	}	
	public int getSonarVisibilityHeight() {
		return this.sonarVisibilityHeight;
	}	
	public int getRadarVisibilityWidth() {
		return this.radarVisibilityWidth;
	}	
	public int getRadarVisibilityHeight() {
		return this.radarVisibilityHeight;
	}
	
	// Methods that aren't in the client:
	// incrementSectionHealth()
	// decrementSEctionHealth()
	// isDead()
	
	
 

}
