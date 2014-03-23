package logic;

import logic.spacethings.AbstractShip;
import common.GameConstants.WeaponType;

public class Explosives extends AbstractWeapon {

	public Explosives(AbstractShip owner) {
		super(owner);
		damage = 2;
	}
	
	@Override
	public WeaponType getType() {
		return WeaponType.Explosives;
	}

	/*
	 * Useless method for this weapon type
	 */
	@Override
	protected boolean inRange(int x, int y) {
		return false;
	}

	@Override
	public boolean fire(int x, int y) {
		// TODO Auto-generated method stub
		return false;
	}

}
