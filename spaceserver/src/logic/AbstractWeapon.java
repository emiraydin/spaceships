package logic;

import logic.spacethings.AbstractShip;

import common.GameConstants.WeaponType;

public abstract class AbstractWeapon {
	
	protected AbstractShip owner;
	protected int damage;
	
	public AbstractWeapon(AbstractShip owner) { 
		this.owner = owner;
	}

	public abstract WeaponType getType();
	
	/**
	 * Verify if desired target is within the range of the weapon.
	 * @param x desired x
	 * @param y desired y
	 * @return True if target within range, false otherwise.
	 */
	protected abstract boolean inRange(int x, int y);
	
	public abstract boolean fire(int x, int y);
	
	public int getDamage(){
		return damage;
	}
	
	public void setDamage(int damage){
		this.damage = damage;
	}
}
