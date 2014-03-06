package logic.spacethings;

import common.GameConstants.ActionType;
import common.GameConstants.OrientationType;
import common.GameConstants.WeaponType;

public abstract class AbstractShip extends SpaceThing {
	private int id;
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
	
	public int getID(){
		return id;
	}
	
	public ActionType[] getPossibleActions(){
		//TODO: getPossibleAction()... should this be abstract?
		return null;
	}
	
	public boolean useWeapon(WeaponType wType, int x, int y){
		//TODO: useWeapon()
		return false;
	}

	public int getSectionAt(int x, int y){
		//TODO: getSectionAt()
		return -1;
	}
	
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
}
