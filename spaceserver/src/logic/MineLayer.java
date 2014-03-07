package logic;

import common.GameConstants.WeaponType;

public class MineLayer extends AbstractWeapon {

	@Override
	public WeaponType getType() {
		return WeaponType.Mine;
	}

}
