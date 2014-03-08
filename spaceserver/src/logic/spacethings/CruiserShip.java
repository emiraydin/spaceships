package logic.spacethings;

import logic.AbstractWeapon;
import logic.HeavyCannon;
import common.GameConstants.ActionType;

public class CruiserShip extends AbstractShip {
	public CruiserShip(int x, int y, int gameID){
		super(x, y, gameID);
		
		this.length = 5;
		this.speed = 10;
		
		initializeHealth(this.length, true);
		
		this.cannonWidth = 11;
		this.cannonLength = 15;
		this.cannonLengthOffset = -5; 	// 0 offset = flush with stern
		
		// ignoring sonar..
		this.radarVisibilityWidth = 3;
		this.radarVisibilityLength = 10;
		this.radarVisibilityLengthOffset = 1;
		
		this.arsenal = new AbstractWeapon[1];
		arsenal[0] = new HeavyCannon(this);
	}
}
