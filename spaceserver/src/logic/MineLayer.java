package logic;

import logic.spacethings.AbstractShip;

import common.GameConstants.WeaponType;

public class MineLayer extends AbstractWeapon {
	
	public MineLayer(AbstractShip owner) { 
		super(owner);
		// ???
		damage = -1;
	}
	
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
	protected boolean inRange(int x, int y) {
		// TODO Auto-generated method stub
		return false;
	}

}
