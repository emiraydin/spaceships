package logic;

import common.GameConstants.WeaponType;

public class Cannon extends AbstractWeapon {

	@Override
	public WeaponType getType() {
		return WeaponType.Cannon;
	}
	
}
