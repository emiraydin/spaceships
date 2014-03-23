package logic.spacethings;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import logic.AbstractWeapon;
import logic.Explosives;
import logic.FleetCommander;
import logic.StarBoard;

import common.GameConstants.ActionType;
import common.GameConstants.OrientationType;
import common.GameConstants.SpaceThingType;

public class KamikazeBoatShip extends AbstractShip {
	
	public KamikazeBoatShip(int x, int y, OrientationType oType, FleetCommander owner, StarBoard gameBoard){
		super(x, y, oType, owner, gameBoard);

		this.length = 1;
		this.setMaxSpeed(-1); 		// UNIQUE
		
		initializeHealth(this.length, true);
		
		this.cannonLength = 0;
		this.cannonWidth = 0;
		this.cannonLengthOffset = 0;
		
		this.radarVisibilityWidth = 5;
		this.radarVisibilityLength = 5;
		this.radarVisibilityLengthOffset = -2;
		
		this.arsenal = new AbstractWeapon[1];
		arsenal[0] = new Explosives(this);
		
	}

	@Override
	public SpaceThingType getShipType() {
		return SpaceThingType.KamikazeBoatShip;
	}

	/*
	 * Useless for this ship type - doesn't need to turn
	 */
	@Override
	public List<Point> getObstaclesInTurnZone(ActionType direction) {
		return new ArrayList<Point>();
	}

}
