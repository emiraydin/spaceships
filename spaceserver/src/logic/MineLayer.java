package logic;

import logic.spacethings.AbstractShip;
import logic.spacethings.Mine;
import logic.spacethings.MineLayerShip;
import logic.spacethings.SpaceThing;

import common.GameConstants.WeaponType;

public class MineLayer extends AbstractWeapon {
	
	public MineLayer(AbstractShip owner) { 
		super(owner);
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
			owner.getOwner().setActionResponse(ship.getPrettyMineCount());
			if (mine != null){
				mine.setLocation(x, y);
				ship.getGameBoard().setSpaceThing(mine);
				return true;
			} else { 
				this.owner.getOwner().setActionResponse("No more mines left!");
				return false;
			}
		}
		// if out of bounds, inRange() method set the error message
		return false;
		
	}

	/** 
	 * Note: this method is for checking if a location is within FIRE range, 
	 * not just within sonar range (see MineLayerShip.inSonarRange(x,y) for that)
	 * It returns false if x,y is in immediate contact with something (a mine layer cannot
	 * drop a mine that would be directly on/adjacent to something)
	 */
	@Override
	protected boolean inRange(int x, int y) {
		MineLayerShip ship = (MineLayerShip)owner;
		if(ship.inSonarRange(x, y)) {
			StarBoard board = owner.getGameBoard();
			
			// if something is on that square, can't drop mine
			SpaceThing spaceThing = board.getSpaceThing(x, y);
			if(spaceThing != null && spaceThing != owner) { 
				this.owner.getOwner().setActionResponse("Can't drop mine on an occupied square");
				return false;
			}
			
			// if something is directly adjacent to square, can't drop mine
			if(StarBoard.inBounds(x+1, y)) { 
				SpaceThing adjacentSpaceThing = board.getSpaceThing(x+1, y);
				if(adjacentSpaceThing != null && adjacentSpaceThing != owner && !(adjacentSpaceThing instanceof Mine)) { 
					this.owner.getOwner().setActionResponse("Can't drop mine adjacent to a ship, base or asteroid");
					return false;
				}
			}
			if(StarBoard.inBounds(x-1, y)) { 
				SpaceThing adjacentSpaceThing = board.getSpaceThing(x-1, y);
				if(adjacentSpaceThing != null && adjacentSpaceThing != owner && !(adjacentSpaceThing instanceof Mine)) { 
					this.owner.getOwner().setActionResponse("Can't drop mine adjacent to a ship, base or asteroid");
					return false;
				}
			}
			if(StarBoard.inBounds(x, y+1)) { 
				SpaceThing adjacentSpaceThing = board.getSpaceThing(x, y+1);
				if(adjacentSpaceThing != null && adjacentSpaceThing != owner && !(adjacentSpaceThing instanceof Mine)) { 
					this.owner.getOwner().setActionResponse("Can't drop mine adjacent to a ship, base or asteroid");
					return false;
				}
			}
			if(StarBoard.inBounds(x, y-1)) { 
				SpaceThing adjacentSpaceThing = board.getSpaceThing(x, y-1);
				if(adjacentSpaceThing != null && adjacentSpaceThing != owner && !(adjacentSpaceThing instanceof Mine)) { 
					this.owner.getOwner().setActionResponse("Can't drop mine adjacent to a ship, base or asteroid");
					return false;
				}
			}	
			
			// in bounds, in sonar range, not on/adjacent to anything except minelayer itself			
			return true;
		}
		this.owner.getOwner().setActionResponse("Can't drop a mine outside of the MineLayer range");
		return false;
		
	}

}
