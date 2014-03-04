package state.weapons;

import state.ships.AbstractShip;

public abstract class AbstractWeapon {
	
	int damage;
	AbstractShip ownerShip;
	
	
	/**
	 * Fire the weapon.
	 * 
	 * @param x board location
	 * @param y board location
	 */
	public void fire(int x, int y) {
		if (validate(x,y)) doAction(x,y);
	}
	
	public int getDamage() {
		return this.damage;
	}
	
	
	abstract boolean validate(int x, int y);

	abstract public void doAction(int x, int y);

}
