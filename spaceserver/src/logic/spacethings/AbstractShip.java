package logic.spacethings;

import logic.AbstractWeapon;

import common.GameConstants.OrientationType;
import common.GameConstants.WeaponType;

public abstract class AbstractShip extends SpaceThing {
	private int speed;
	private int length;
	private int[] sectionHealth;
	private OrientationType orientation;
	private int cannonWidth;
	private int cannonLength;
	private int cannonXOffset;
	private int sonarVisibilityWidth;
	private int sonarVisibilityLength;
	private int radarVisibilityWidth;
	private int radarVisibilityLength;
//	private ActionType[] possibleActions;
	private AbstractWeapon[] arsenal;
	
	
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
		return speed;
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
		return cannonXOffset;
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
	
	public void setArsenal(AbstractWeapon[] weapons){
		arsenal = weapons;
	}
}
