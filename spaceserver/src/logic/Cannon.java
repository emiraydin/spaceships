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
		
		// TODO: fill
		return false;
	}

	@Override
	protected boolean inRange(int x, int y) {
		int ownerX = owner.getX();
		int ownerY = owner.getY();
		int cannonWidth = owner.getCannonWidth();
		int cannonLength = owner.getCannonLength();
		int cannonLengthOffset = owner.getCannonLengthOffset();
		
		int minX = -1;
		int maxX = -1;
		int minY = -1;
		int maxY = -1;
		switch(owner.getOrientation()) { 
		case East: 
			minX = ownerX + cannonLengthOffset;
			maxX = minX + cannonLength;
			minY = ownerY - cannonWidth/2;
			maxY = ownerY + cannonWidth/2;
			break;
		case West:
			minX = ownerX - cannonLengthOffset;
			maxX = minX - cannonLength;
			minY = ownerY - cannonWidth/2;
			maxY = ownerY + cannonWidth/2;
			break;
		case North: 
			minX = ownerX - cannonWidth/2;
			maxX = ownerX + cannonWidth/2;
			maxY = ownerY - cannonLengthOffset;
			minY = maxY - cannonLength;
			break;
		case South: 
			minX = ownerX - cannonWidth/2;
			maxX = ownerX + cannonWidth/2;
			minY = ownerY + cannonLengthOffset;
			maxY = minY + cannonLength;
			break;
		}
		
		if(minX <= x && maxX >= x && minY <= y && maxY >= y) { 
			return true;
		}
		return false;
	}
	
}
