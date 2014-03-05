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
	protected int cannonLength;
	protected int cannonXOffset;
	
	// Radar Properties
	protected int radarVisibilityWidth;
	protected int radarVisibilityLength;
	
	// Weapons
	protected LinkedList<AbstractWeapon> weapons;	
	
	/**
	 * Construct the abstract ship.
	 * 
	 * @param length
	 * @param armour
	 * @param speed
	 * @param cannonWidth
	 * @param cannonLength
	 * @param cannonXOffset
	 * @param radarVisibilityWidth
	 * @param radarVisibilityLength
	 */
	public AbstractShip(int id,
			PlayerNumber owner,
			int length,
			ArmourType armour,
			int speed,
			int cannonWidth,
			int cannonLength,
			int cannonXOffset,
			int radarVisibilityWidth,
			int radarVisibilityLength) {
		
		// Unique id
		super(id, owner);
		
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
		this.cannonLength = cannonLength;
		this.cannonXOffset = cannonXOffset;
		this.radarVisibilityWidth = radarVisibilityWidth;
		this.radarVisibilityLength = radarVisibilityLength;
	}
	
	
	/*
	 * Methods
	 */	
	
	/**
	 * Update the ships properties after a turn.
	 * Nothing changes if you pass null params.
	 *  
	 * @param posX new x position on map
	 * @param posY new y position on map
	 * @param orientation new orientation
	 * @param sectionHealth new sectionHealth array
	 */
	public void updateProperties(int posX, int posY, OrientationType orientation, int[] sectionHealth) {
		this.setX(posX);
		this.setY(posY);
		if (orientation != null) {
			this.setOrientation(orientation);			
		}
		if (sectionHealth != null) {
			this.sectionHealth = sectionHealth.clone();			
		}

	}
	
	/**
	 * Do we need this function on the client?
	 * 	
	 * @param wType
	 * @param x
	 * @param y
	 */
	public void useWeapon(WeaponType wType, int x, int y) {
		
	}
	
	/**
	 * Get the ship orientation.
	 * 
	 * @return
	 */
	public OrientationType getOrientation() {
		return this.orientation;
	}
	/**
	 * Set the ship orientation.
	 * 
	 * @param oType
	 */
	public void setOrientation(OrientationType oType) {
		this.orientation = oType;
	}
	
	/**
	 * Add a weapon to the ship.
	 * 
	 * @param input the weapon to add
	 */
	public void addWeapon(AbstractWeapon input) {
		this.weapons.add(input);
	}
	
	
	/*
	 * Basic Getters...
	 */
	public int[] getSectionHealth() {
		return this.sectionHealth.clone();
	}
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
	public int getCannonLength() {
		return this.cannonLength;
	}	
	public int getCannonXOffset() {
		return this.cannonXOffset;
	}	
	public int getRadarVisibilityWidth() {
		return this.radarVisibilityWidth;
	}	
	public int getRadarVisibilityLength() {
		return this.radarVisibilityLength;
	}
	
	// Methods that aren't in the client:
	// incrementSectionHealth()
	// decrementSEctionHealth()
	// isDead()
	
	public String toString() {
		return
				"===================\n"
				+ "Printing Ship Schematics:"
				+ "\n    ID: " + uniqueId
				+ "\n    Owner:" + this.getOwner()
				+ "\n    x: " + this.getX()
				+ "\n    y: " + this.getY()
				+ "\n    Speed: " + this.getSpeed()
				+ "\n    Length: " + this.getLength()
				+ "\n    Armour: " + armour
				+ "\n    Orientation:" + this.getOrientation()
				+ "\n    CannonWidth: " + this.getCannonWidth()
				+ "\n    CannonLength: " + this.getCannonLength()
				+ "\n    CannonXOffset: " + this.getCannonXOffset()
				+ "\n    RadarVisibilityWidth: " + this.getRadarVisibilityWidth()
				+ "\n    RadarVisibilityLength: " + this.getRadarVisibilityLength();
	}
	
	public LinkedList getWeapons()
	{
		return this.weapons; 
	}
 

}
