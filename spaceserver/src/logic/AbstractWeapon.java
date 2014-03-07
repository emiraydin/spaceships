package logic;

import common.GameConstants.WeaponType;

public abstract class AbstractWeapon {
	
	protected int damage;

	public abstract WeaponType getType();
	
	public abstract boolean fire(int x, int y);
	
	public int getDamage(){
		return damage;
	}
	
	public void setDamage(int damage){
		this.damage = damage;
	}
}
