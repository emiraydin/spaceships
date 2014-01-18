package ships;

import java.util.LinkedList;

/**
 * All ships will be instances of AbstractShips. Tie together the majority of the Game Logic. 
 * Allows easy creation of new ships. 
 * @author vikramsundaram
 *
 */
public class AbstractShip
{
	// Basic Ships Properties
	public static enum shipArmorTypes {Light, Heavy}; // The types of Armor a Ship can Have
	public static enum shipWeaponTypes {LightCannon, HeavyCannon, Torpedoe, Mine}; // The types of Weapons a ship can have. 
	protected shipArmorTypes shipArmor; 
	protected LinkedList<shipWeaponTypes> shipWeapons; 
	protected int shipSpeed; // Speed -> The number of tiles the front of the ship can move in one turn. 
	protected int shipSize; // Size -> Number of squares it occupies. 
		
	/**
	 * Getter for the Ship Speed. 
	 * @return Int representing the Number of places a ship can move in one turn. 
	 */
	public int getShipSpeed()
	{
		return this.shipSpeed; 
	}
	
	/**
	 * Getter for the Ship Size. 
	 * @return Int representing the number of places a ship occupies. 
	 */
	public int getShipSize()
	{
		return this.shipSize; 
	}
	
	/**
	 * returns the Armor type of the ship. 
	 * @return Object of shipArmorTypes.t where t is the type of armor a ship has. 
	 */
	public shipArmorTypes getShipArmor()
	{
		return this.shipArmor; 
	}
	
	/**
	 * Returns the List of Weapons this ship has. 
	 */
	public LinkedList<shipWeaponTypes> getShipWeapons()
	{
		return this.shipWeapons; 
	}

	// Here follows the Logic for Ship Game Logic Manipulation. 
	// TODO: Write this.. 
	
	



}
