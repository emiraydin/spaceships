package logic;

import logic.spacethings.AbstractShip;

import common.GameConstants.OrientationType;
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
		int ownerX = owner.getX();
		int ownerY = owner.getY();
		int cannonWidth = owner.getCannonWidth();
		int cannonLength = owner.getCannonLength();
		int cannonXOffset = owner.getCannonXOffset();
		
		int minX;
		int maxX;
		int minY;
		int maxY;
		switch(owner.getOrientation()) { 
		case East: 
			minX = ownerX + cannonXOffset;
			maxX = minX + cannonLength;
			minY = ownerY - cannonWidth/2;
			maxY = minY + cannonWidth;
		case West:
			minX = ownerX - cannonXOffset;
			maxX = minX - cannonLength;
			minY = ownerY - cannonWidth/2;
			maxY = minY + cannonWidth;
		case North: 
			minX = ownerX - cannonWidth/2;
			maxX = minX + cannonWidth;
			maxY = onwerY - cannonXOffset;
		case South: 
		}
		
		return false;
	}

	@Override
	protected boolean validate(int x, int y) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
