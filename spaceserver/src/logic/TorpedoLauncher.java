package logic;

import common.GameConstants.WeaponType;

public class TorpedoLauncher extends AbstractWeapon {

	private final static int MAX_RANGE = 10;
	
	public TorpedoLauncher() { 
		damage = 1;
	}
	
	@Override
	public WeaponType getType() {
		return WeaponType.Torpedo;
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
