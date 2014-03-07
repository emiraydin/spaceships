package logic;

import common.GameConstants.WeaponType;

public class TorpedoLauncher extends AbstractWeapon {

	@Override
	public WeaponType getType() {
		return WeaponType.Torpedo;
	}

	@Override
	public boolean fire(int x, int y) {
		// TODO Auto-generated method stub
		return false;
	}

}
