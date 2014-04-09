package logic;

import logic.spacethings.AbstractShip;
import logic.spacethings.BaseTile;
import logic.spacethings.Mine;
import logic.spacethings.SpaceThing;

import common.GameConstants.WeaponType;

public class Explosives extends AbstractWeapon {

	public Explosives(AbstractShip owner) {
		super(owner);
		damage = 2;
	}
	
	@Override
	public WeaponType getType() {
		return WeaponType.Explosives;
	}

	/*
	 * Useless method for this weapon type
	 */
	@Override
	protected boolean inRange(int x, int y) {
		return false;
	}

	@Override
	public boolean fire(int x, int y) {		
		boolean damagedShips = false;
		String damagedShipsString = " Damaged ships at: ";
		
		boolean damagedBase = false;
		String damagedBaseString = " Damaged base at: ";
		
		boolean damagedMine = false;
		String damagedMineString = " Destroyed mine at: ";
		
		//move to desired location
		FleetCommander fc = this.owner.getOwner();
		if(fc.moveShip(this.owner.getID(), x, y) >= 0) { 
			// success - moved
			// damage all surrounding ships/bases
			for(int i = x-1; i <= x+1; i++) { 
				for(int j = y-1; j <= y+1; j++) { 
					if(StarBoard.inBounds(i, j)) { 
						SpaceThing spaceThing = fc.getHandler().getBoard().getSpaceThing(i, j);
						if(spaceThing instanceof AbstractShip && spaceThing != this.owner) { 
							// add ship damage to message
							if(damagedShips) { 
								damagedShipsString += ", ";
							}
							damagedShipsString += "(" + i + "," + j + ")";
							damagedShips = true;
							
							// actually do damage
							AbstractShip obstacleShip = (AbstractShip)spaceThing;
							obstacleShip.decrementSectionHealth(damage, obstacleShip.getSectionAt(i, j));
						}	
						else if(spaceThing instanceof BaseTile) { 
							if(damagedBase) { 
								damagedBaseString += ", ";
							}
							damagedBaseString += "(" + i + "," + j + ")";
							damagedBase = true;
							
							// actually do damage
							BaseTile baseTile = (BaseTile)spaceThing;
							baseTile.decrementBaseHealth(damage);
						}
						else if(spaceThing instanceof Mine) { 
							if(damagedMine) { 
								damagedMineString += ", ";
							}
							damagedMineString += "(" + i + "," + j + ")";
							damagedMine = true;
							
							((Mine)spaceThing).removeSafely();
						}
					}
				}
			}
			
			String responseString = "Kamikaze Boat exploded at (" + x + "," + y + ").";
			if(damagedShips) { 
				responseString += damagedShipsString;
			}			
			if(damagedBase) { 
				responseString += damagedBaseString;
			}
			if(damagedMine) { 
				responseString += damagedMineString;
			}
			
			fc.setActionResponse(responseString);
			
			// boat disappears in explosion
			this.owner.decrementSectionHealth(damage, 0);
			fc.removeShip(this.owner);
			
			return true;
						
		}
		else { 
			// could not move there
			fc.setActionResponse("Ship could not move to that location");
			return false;
		}
	}

}
