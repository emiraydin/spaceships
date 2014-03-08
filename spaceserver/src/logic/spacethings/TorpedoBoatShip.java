package logic.spacethings;

import logic.AbstractWeapon;
import logic.Cannon;
import logic.TorpedoLauncher;

public class TorpedoBoatShip extends AbstractShip {
	public TorpedoBoatShip(int x, int y, int gameID) {
		super(x, y, gameID);
		
		this.length = 3;
		this.speed = 9;
		
		initializeHealth(this.length, false);
		
		this.cannonWidth = 5;
		this.cannonLength = 5;
		this.cannonLengthOffset = 0;
		
		this.radarVisibilityLength = 6;
		this.radarVisibilityWidth = 3;
		this.radarVisibilityLengthOffset = 1;
		
		// Torpedo Boats have cannons and torpedoes
		arsenal = new AbstractWeapon[2];
		arsenal[0] = new Cannon(this);
		arsenal[1] = new TorpedoLauncher(this);
	}
}
