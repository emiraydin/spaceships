package logic.spacethings;

import logic.AbstractWeapon;
import logic.Cannon;
import logic.GameBoard;

public class RadarBoatShip extends AbstractShip {
	public RadarBoatShip(int x, int y, GameBoard gameBoard) {
		super(x, y, gameBoard);
		
		this.length = 3;
		// by default, speed is 3
		this.speed = 3;
		
		initializeHealth(this.length, false);
		
		this.cannonLength = 5;
		this.cannonWidth = 3;
		this.cannonLengthOffset = -1;
		
		// by default, radar length 6
		this.radarVisibilityLength = 6;
		this.radarVisibilityWidth = 3;
		this.radarVisibilityLengthOffset = 1;
		
		arsenal = new AbstractWeapon[1];
		arsenal[0] = new Cannon(this);
	}
	
	public void turnOnLongRadar() { 
		speed = 0;
		radarVisibilityLength = 12;
	}
	
	public void turnOffLongRadar() { 
		speed = 3;
		radarVisibilityLength = 6;
	}
}
