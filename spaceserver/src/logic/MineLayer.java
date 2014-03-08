package logic;

import common.GameConstants.WeaponType;

public class MineLayer extends AbstractWeapon {

	@Override
	public WeaponType getType() {
		return WeaponType.Mine;
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
