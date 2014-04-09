package logic;

import logic.spacethings.AbstractShip;
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
		String damagedShipsString = "Kamikaze Boat exploded at (" + x + "," + y + ") and damaged ships at: ";
		
		//move to desired location
		FleetCommander fc = this.owner.getOwner();
		if(fc.moveShip(this.owner.getID(), x, y) >= 0) { 
			// success - moved
			// damage all surrounding ships
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
					}
				}
			}
			
			if(damagedShips) { 
				fc.setActionResponse(damagedShipsString);
			}
			else { 
				fc.setActionResponse(String.format("Kamikaze Boat exploded at (%d,%d) and damaged no ships", x, y));
			}
			
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
