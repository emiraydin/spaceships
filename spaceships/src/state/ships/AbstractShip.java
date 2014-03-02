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
public class AbstractShip extends SpaceThing
{

	/*
	 * Properties
	 */
	
	// Basic facts
	int speed;
	int length;
	int[] sectionHealth;
	//OrientationType orientation;
	
	// Cannon properties
	int cannonWidth;
	int cannonHeight;
	int cannonXOffset;
	
	// Sonar Properties
	int sonarVisibilityWidth;
	int sonarVisibilityHeight;
	
	// Radar Properties
	int radarVisibilityWidth;
	int radarVisibilityHeight;
	
	// Weapons
	LinkedList<AbstractWeapon> weapons;	
	
	
	
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
