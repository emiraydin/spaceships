package logic.spacethings;

import common.GameConstants.ActionType;

import logic.AbstractWeapon;
import logic.Cannon;
import logic.GameBoard;
import logic.TorpedoLauncher;

public class DestroyerShip extends AbstractShip {
	public DestroyerShip(int x, int y, GameBoard gameBoard){
		super(x, y, gameBoard);
		
		this.speed = 8;
		this.length = 4;
		
		initializeHealth(this.length, false);
		
		this.cannonWidth = 9;
		this.cannonLength = 12;
		this.cannonLengthOffset = -4;
		
		this.radarVisibilityLength = 8;
		this.radarVisibilityWidth = 3;
		this.radarVisibilityLengthOffset = 1;
		
		// Destroyers carry Cannons and Torpedoes
		this.arsenal = new AbstractWeapon[2];
		arsenal[0] = new Cannon(this);
		arsenal[1] = new TorpedoLauncher(this);
	}

	@Override
	public boolean tryTurning(ActionType turnType) {
		// TODO Auto-generated method stub
		return false;
	}
}
