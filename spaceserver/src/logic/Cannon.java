package logic;

import logic.spacethings.AbstractShip;
import logic.spacethings.BaseTile;
import logic.spacethings.Mine;
import logic.spacethings.SpaceThing;

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
		if(inRange(x, y)) { 
			SpaceThing spaceThing = owner.getGameBoard().getSpaceThing(x, y);
			/* If cannon hits a ship */
			if(spaceThing instanceof AbstractShip) { 
				// decrement ship section health 
				AbstractShip ship = (AbstractShip)spaceThing;
				int sectionIndex = ship.getSectionAt(x, y);
				ship.decrementSectionHealth(damage, sectionIndex);
				this.owner.getOwner().setActionResponse(String.format("Cannon hit a ship at (%d,%d)", x, y));
			}
			/* If cannon hits a mine */
			else if(spaceThing instanceof Mine) { 
				// mine is destroyed without exploding
				owner.getGameBoard().clearSpaceThing(x, y);
				this.owner.getOwner().setActionResponse(String.format("Cannont hit a mine at (%d,%d)", x, y));
			}
			/* If cannon hits a base tile */
			else if (spaceThing instanceof BaseTile) { 
				// base tile health decremented
				BaseTile baseTile = (BaseTile)spaceThing;
				baseTile.decrementBaseHealth(damage);
				this.owner.getOwner().setActionResponse(String.format("Cannon hit a base at (%d, %d)", x, y));
			}
			else { 
				/* Otherwise, cannon hit a coral reef (indestructible) or nothing */
				this.owner.getOwner().setActionResponse(String.format("Cannon shot and missed at (%d,%d)", x, y));
			}			
			return true;
		}
		this.owner.getOwner().setActionResponse(String.format("(%d,%d) out of range.", x, y));
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
		// UPDATED TO NEW ORIGIN SYSTEM 
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
			minY = ownerY + cannonLengthOffset;
			maxY = minY + cannonLength;
			break;
		case South: 
			minX = ownerX - cannonWidth/2;
			maxX = ownerX + cannonWidth/2;
			maxY = ownerY - cannonLengthOffset;
			minY = maxY - cannonLength;
			break;
		}
		
		if(minX <= x && maxX >= x && minY <= y && maxY >= y && StarBoard.inBounds(x, y)) { 
			return true;
		}
		return false;
	}
	
}
