package state.ships;

import gameLogic.Constants.*;

import java.util.Arrays;
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
	
	
	// Weapons
	protected LinkedList<AbstractWeapon> weapons;	
	
	/**
	 * Construct the ship.
	 * 
	 * @param id
	 * @param type
	 * @param owner
	 * @param length
	 * @param armour
	 * @param speed
	 * @param cannonWidth
	 * @param cannonLength
	 * @param cannonXOffset
	 */
	public AbstractShip(int id,
			SpaceThingType type,
			PlayerNumber owner,
			int length,
			ArmourType armour,
			int speed,
			int cannonWidth,
			int cannonLength,
			int cannonXOffset) {
		
		// Unique id
		super(id, type, owner);
		
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
	
	public LinkedList getWeapons() {
		return this.weapons; 
	}


	@Override
	public String toString() {
		return "AbstractShip [speed=" + speed + ", length=" + length + ", armour=" + armour + ", sectionHealth="
				+ Arrays.toString(sectionHealth) + ", orientation=" + orientation + ", cannonWidth=" + cannonWidth
				+ ", cannonLength=" + cannonLength + ", cannonXOffset=" + cannonXOffset + ", weapons=" + weapons
				+ ", uniqueId=" + uniqueId + ", owner=" + owner + ", type=" + type + ", x=" + x + ", y=" + y + "]";
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((armour == null) ? 0 : armour.hashCode());
		result = prime * result + cannonLength;
		result = prime * result + cannonWidth;
		result = prime * result + cannonXOffset;
		result = prime * result + length;
		result = prime * result + ((orientation == null) ? 0 : orientation.hashCode());
		result = prime * result + Arrays.hashCode(sectionHealth);
		result = prime * result + speed;
		result = prime * result + ((weapons == null) ? 0 : weapons.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		AbstractShip other = (AbstractShip) obj;
		if (armour != other.armour)
			return false;
		if (cannonLength != other.cannonLength)
			return false;
		if (cannonWidth != other.cannonWidth)
			return false;
		if (cannonXOffset != other.cannonXOffset)
			return false;
		if (length != other.length)
			return false;
		if (orientation != other.orientation)
			return false;
		if (!Arrays.equals(sectionHealth, other.sectionHealth))
			return false;
		if (speed != other.speed)
			return false;
		if (weapons == null)
		{
			if (other.weapons != null)
				return false;
		}
		else if (!weapons.equals(other.weapons))
			return false;
		return true;
	}

}
