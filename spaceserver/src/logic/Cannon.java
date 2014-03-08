package logic;

import logic.spacethings.AbstractShip;

import common.GameConstants.WeaponType;

public class Cannon extends AbstractWeapon {

	public Cannon(AbstractShip owner) {
		super(owner);
		damage = 1;
	}
	
	@Override
	public WeaponType getType() {
		return WeaponType.Cannon;
	}

	@Override
	public boolean fire(int x, int y) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected boolean validate(int x, int y) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
