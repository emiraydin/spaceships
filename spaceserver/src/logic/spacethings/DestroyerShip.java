package logic.spacethings;

import logic.AbstractWeapon;
import logic.Cannon;
import logic.TorpedoLauncher;

public class DestroyerShip extends AbstractShip {
	public DestroyerShip(int x, int y, int gameID){
		super(x, y, gameID);
		
		this.speed = 8;
		this.length = 4;
		
		initializeHealth(this.length, false);
		
		this.cannonWidth = 9;
		this.cannonLength = 12;
		this.cannonXOffset = -4;
		
		this.radarVisibilityLength = 8;
		this.radarVisibilityWidth = 3;
		this.radarVisibilityXOffset = 1;
		
		// Destroyers carry Cannons and Torpedoes
		this.arsenal = new AbstractWeapon[2];
		arsenal[0] = new Cannon(this);
		arsenal[1] = new TorpedoLauncher(this);
	}
}
