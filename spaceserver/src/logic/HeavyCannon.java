package logic;

import common.GameConstants.WeaponType;

public class HeavyCannon extends AbstractWeapon {

	@Override
	public WeaponType getType() {
		return WeaponType.Cannon;
	}

}
