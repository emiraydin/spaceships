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
	
	protected abstract boolean validate(int x, int y);
	
	public abstract boolean fire(int x, int y);
	
	public int getDamage(){
		return damage;
	}
	
	public void setDamage(int damage){
		this.damage = damage;
	}
}
