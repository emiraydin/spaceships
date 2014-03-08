package logic.spacethings;

import logic.AbstractWeapon;

import common.GameConstants.OrientationType;
import common.GameConstants.WeaponType;

public abstract class AbstractShip extends SpaceThing {
	protected int speed;
	protected int length;
	protected int[] sectionHealth;
	protected OrientationType orientation;
	protected int cannonWidth;
	protected int cannonLength;
	protected int cannonLengthOffset;
	protected int sonarVisibilityWidth;
	protected int sonarVisibilityLength;
	protected int radarVisibilityWidth;
	protected int radarVisibilityLength;
	protected int radarVisibilityLengthOffset;
//	private ActionType[] possibleActions;
	protected AbstractWeapon[] arsenal;
	
	
	public AbstractShip(int x, int y, int gameID){
		super(x, y, gameID);
	}
	
	public boolean useWeapon(WeaponType wType, int x, int y){
		//TODO: useWeapon()
		for (int i = 0; i < arsenal.length; i++){
			if (arsenal[i].getType() == wType){
				return arsenal[i].fire(x, y);
			}
		}
		return false;
	}

//	public int getSectionAt(int x, int y, GameBoard gBoard){
//		if (gBoard.getSpaceThing(x, y) instanceof AbstractShip){
// 			AbstractShip ship = (AbstractShip) gBoard.getSpaceThing(x, y);
//			return Math.abs((x - ship.getX()) + (y - ship.getY()));
//		}
//		return -1;
//	}
	
	public void incrementSectionHealth(int amount, int section){
		sectionHealth[section] += amount;
	}
	
	public void decrementSectionHealth(int amount, int section){
		sectionHealth[section] -= amount;
	}
	
	public boolean isDead(){
		for (int i = 0; i < sectionHealth.length; i++){
			if (sectionHealth[i] > 0){
				return false;
			}
		}
		return true;
	}
	
	public OrientationType getOrientation() {
		return orientation;
	}

	public void setOrientation(OrientationType orientation) {
		this.orientation = orientation;
	}

	public int getSpeed() {
		int count = 0;
		for (int i = 0 ; i < length; i++){
			count += (sectionHealth[i] > 0 ? 1 : 0);
		}
		
		return (int) Math.ceil((length/(float) count)*speed);
	}

	public int getLength() {
		return length;
	}

	public int getCannonWidth() {
		return cannonWidth;
	}

	public int getCannonLength() {
		return cannonLength;
	}

	public int getCannonXOffset() {
		return cannonLengthOffset;
	}

	public int getSonarVisibilityWidth() {
		return sonarVisibilityWidth;
	}

	public int getSonarVisibilityLength() {
		return sonarVisibilityLength;
	}

	public int getRadarVisibilityWidth() {
		return radarVisibilityWidth;
	}

	public int getRadarVisibilityLength() {
		return radarVisibilityLength;
	}
	
	public int getRadarVisibilityLengthOffset() { 
		return radarVisibilityLengthOffset;
	}
	
	public void setArsenal(AbstractWeapon[] weapons){
		arsenal = weapons;
	}
	
	protected void initializeHealth(int length, boolean heavyArmour) { 
		this.sectionHealth = new int[length];
		if(heavyArmour) { 
			for(int i = 0; i < length; i++) { 
				sectionHealth[i] = 2;
			}
		} else { 
			for(int i = 0; i < length; i++) { 
				sectionHealth[i] = 1;
			}
		}
	}
}
