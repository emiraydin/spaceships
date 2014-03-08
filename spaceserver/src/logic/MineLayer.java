package logic;

import logic.spacethings.AbstractShip;
import logic.spacethings.Mine;
import logic.spacethings.MineLayerShip;
import logic.spacethings.SpaceThing;

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
		if(inRange(x, y)) { 
			MineLayerShip ship = (MineLayerShip)owner;
			Mine mine = ship.removeMine();
			mine.setLocation(x, y);
			ship.getGameBoard().setSpaceThing(mine, x, y);
			return true;
		}
		return false;
		
	}

	/** 
	 * Note: this method is for checking if a location is within FIRE range, 
	 * not just within sonar range (see MineLayerShip.inSonarRange(x,y) for that)
	 * It returns false if x,y is in immediate contact with something
	 */
	@Override
	protected boolean inRange(int x, int y) {
		MineLayerShip ship = (MineLayerShip)owner;
		// can't drop mine outside range or on tile containing something else
		if(ship.inSonarRange(x, y)) {
			for(int i = x - 1; i < x + 1; i++) { 
				for(int j = y; j < y + 1; j++) { 
					SpaceThing spaceThing = owner.getGameBoard().getSpaceThing(i, j) ;
					if(spaceThing != null && spaceThing != owner) {
						// cannot drop mine adjacent to any other spacething
						return false;
					}
				}
			}		
			return true;
		}
		return false;
	}

}
