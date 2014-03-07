package logic;

import common.GameConstants.WeaponType;

public class Cannon extends AbstractWeapon {

	@Override
	public WeaponType getType() {
		return WeaponType.Cannon;
	}

	@Override
	public boolean fire(int x, int y) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
