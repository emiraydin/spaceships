package logic;

import common.GameConstants.WeaponType;

public abstract class AbstractWeapon {
	
	protected int damage;

	public abstract WeaponType getType();
	
	public boolean fire(int x, int y){
		return false;
	}
	
	public int getDamage(){
		return damage;
	}
}
