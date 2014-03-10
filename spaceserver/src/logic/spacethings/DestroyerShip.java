package logic.spacethings;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

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
	public boolean tryTurning(ActionType direction) {
		if(!(direction == ActionType.TurnLeft || direction == ActionType.TurnRight)) { 
			System.out.println("DestroyerShip cannot turn in direction: " + direction.name());
			return false;
		}
		
		List<Point> obstaclesInTurnZone = getObstaclesInTurnZone(direction);
		
		// if obstaclesInTurnZone returned null, it means the turn puts the ship out of bounds!
		if(obstaclesInTurnZone == null) { 
			System.out.println("Turn not completed because out of bounds");
			return false;
		}
		
		return handleObstaclesWhileTurning(obstaclesInTurnZone);		
	}
	
	/**
	 * Gets the coordinates for all the obstacles in the turn zone. 
	 * @param turnDirection The direction of the desired turn.
	 * @return null if the turn puts the ship out of bounds,
	 *         otherwise a list of the coordinates of obstacles in the way of the turn
	 *         (ships are multiple coordinates for multiple collisions).
	 */
	protected List<Point> getObstaclesInTurnZone(ActionType turnDirection) {
		List<Point> obstacles = new ArrayList<Point>();
		
		int x = this.getX();
		int y = this.getY();
		
		// ORIENTATION FOLLOWS NEW ORIGIN CONVENTION
		switch(this.getOrientation()) { 
		}
		
		// TODO
	}
}
