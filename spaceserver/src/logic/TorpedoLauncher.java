package logic;

import logic.spacethings.AbstractShip;
import logic.spacethings.BaseTile;
import logic.spacethings.Mine;
import logic.spacethings.SpaceThing;

import common.GameConstants.OrientationType;
import common.GameConstants.WeaponType;

/**
 * Notes on TorpedoLauncher: 
 * 	1. Ship locations are indexed at the tail, but I'm interpreting the torpedo range to be 
 * 	   the range in front of the ship, so the location of the TorpedoLauncher is the head of the ship
 *  2. Torpedoes can't be aimed... the (x,y) of the fire(x,y) is pretty useless.
 *     When fired, the torpedo will just travel in front of the ship until it hits something or dies.
 *
 */
public class TorpedoLauncher extends AbstractWeapon {

	private final static int MAX_RANGE = 10;
	
	public TorpedoLauncher(AbstractShip owner) {
		super(owner);
		damage = 1;
	}
	
	@Override
	public WeaponType getType() {
		return WeaponType.Torpedo;
	}

	@Override
	public boolean fire(int x, int y) {	
		/* Must progress 1-by-1 in front of the ship, while within MAX_RANGE,
		 * until torpedo hits something or dies */
		
		int shipX = owner.getX();
		int shipY = owner.getY();
		int shipLength = owner.getLength();
		
		// bcus java is dum w/ switches
		// torpedoLauncher_ is location of actual torpedolauncher on ship
		int torpedoLauncherX = -1;
		int torpedoLauncherY = -1;
		// torpedo_ is location of actual torpedo (while moving)
		int torpedoX = -1;
		int torpedoY = -1;
		
		// UPDATED FOR NEW ORIGIN CONVENTION
		switch(owner.getOrientation()) { 
		case East:
			torpedoLauncherX = shipX + shipLength - 1;
			torpedoY = shipY;
			for(torpedoX = torpedoLauncherX; torpedoX <= torpedoLauncherX + MAX_RANGE; torpedoX++) { 
				if(!StarBoard.inBounds(torpedoX, torpedoY)) { 
					break;
				}
				SpaceThing spaceThing = owner.getGameBoard().getSpaceThing(torpedoX, torpedoY);  
				if(spaceThing != null) { 
					damageObstacle(spaceThing, torpedoX, torpedoY);
					break;
				}
				
			}
			break;
		case West:
			torpedoLauncherX = shipX - shipLength + 1;
			torpedoY = shipY;
			for(torpedoX = torpedoLauncherX; torpedoX >= torpedoLauncherX - MAX_RANGE; torpedoX--) { 
				if(!StarBoard.inBounds(torpedoX, torpedoY)) { 
					break;
				}
				SpaceThing spaceThing = owner.getGameBoard().getSpaceThing(torpedoX, torpedoY);
				if(spaceThing != null) { 
					damageObstacle(spaceThing, torpedoX, torpedoY);
					break;
				}
				
			}
			break;
		case North:
			torpedoX = shipX;
			torpedoLauncherY = shipY + shipLength - 1;
			for(torpedoY = torpedoLauncherY; torpedoY <= torpedoLauncherY + MAX_RANGE; torpedoY++) { 
				if(!StarBoard.inBounds(torpedoX, torpedoY)) { 
					break;
				}
				SpaceThing spaceThing = owner.getGameBoard().getSpaceThing(torpedoX, torpedoY);
				if(spaceThing != null) { 
					damageObstacle(spaceThing, torpedoX, torpedoY);
					break;
				}
			}
			break;
		case South:
			torpedoX = shipX;
			torpedoLauncherY = shipY - shipLength + 1;
			for(torpedoY = torpedoLauncherY; torpedoY >= torpedoLauncherY - MAX_RANGE; torpedoY--) { 
				if(!StarBoard.inBounds(torpedoX, torpedoY)) { 
					break;
				}
				SpaceThing spaceThing = owner.getGameBoard().getSpaceThing(torpedoX, torpedoY);
				if(spaceThing != null) { 
					damageObstacle(spaceThing, torpedoX, torpedoY);
					break;
				}
			}
			break;
		}
	
		return true;
	}
	
	private void damageObstacle(SpaceThing spaceThing, int x, int y) { 
		/* Obstacle is ship */
		if(spaceThing instanceof AbstractShip) { 
			// decrement ship section health 
			AbstractShip ship = (AbstractShip)spaceThing;
			int section = ship.getSectionAt(x, y);
			ship.decrementSectionHealth(damage, section);
			
			/* if ship is hit from the side (i.e. perpendicular to torpedo ship)
			 * then if there's an adjacent square still in-tact, it is damaged too */
			if(isPerpendicular(ship)) { 
				if(section + 1 < ship.getLength() && !ship.isSectionDamaged(section + 1)) { 
					ship.decrementSectionHealth(damage, section + 1);
				} else if(section - 1 >= 0 && !ship.isSectionDamaged(section - 1)) { 
					ship.decrementSectionHealth(damage, section - 1);
				}
			}		
		}
		
		/* Obstacle is mine */
		else if(spaceThing instanceof Mine) { 
			// mine is destroyed without exploding
			owner.getGameBoard().clearSpaceThing(x, y);
		}
		
		/* Obstacle is base */
		else if(spaceThing instanceof BaseTile) { 
			// base tile health decremented
			BaseTile baseTile = (BaseTile)spaceThing;
			baseTile.decrementBaseHealth(damage);
		}
	}
	
	/**
	 * Checks to see if a given ship is perpendicular to this ship.
	 * @param obstacle Some given obstacle ship on the board.
	 * @return True if perpendicular, false otherwise.
	 */
	private boolean isPerpendicular(AbstractShip obstacle) { 
		OrientationType shipOrientation = owner.getOrientation();
		OrientationType obstacleOrientation = obstacle.getOrientation();
		
		if(shipOrientation == OrientationType.East || shipOrientation == OrientationType.West) { 
			if(obstacleOrientation == OrientationType.North || obstacleOrientation == OrientationType.South) { 
				return true;
			}
		} else { 
			if(obstacleOrientation == OrientationType.East || obstacleOrientation == OrientationType.West) { 
				return true;
			}
		}
	
		return false;
	}

	/**
	 * This method is useless lol
	 */
	@Override
	protected boolean inRange(int x, int y) {
		return true; 			
	}

}
