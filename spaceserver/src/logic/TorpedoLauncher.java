package logic;

import common.GameConstants.WeaponType;

public class TorpedoLauncher extends AbstractWeapon {

	@Override
	public WeaponType getType() {
		return WeaponType.Torpedo;
	}

}
