package logic.spacethings;

import java.util.ArrayList;

import common.GameConstants.OrientationType;

import logic.AbstractWeapon;
import logic.Cannon;
import logic.GameBoard;
import logic.MineLayer;


public class MineLayerShip extends AbstractShip {
	private ArrayList<Mine> mines;
	private static int NUM_MINES = 5;
	
	public MineLayerShip(int x, int y, GameBoard gameBoard){
		super(x, y, gameBoard);
		
		mines = new ArrayList<Mine>(NUM_MINES);
		for (int i = 0; i < NUM_MINES; i++){
			//TODO: Add id generation
			// ^ fixed? lol
			mines.add(new Mine(gameBoard));
		}
		
		this.length = 2;
		this.speed = 6;
		
		initializeHealth(this.length, true);
		
		this.cannonWidth = 5;
		this.cannonLength = 4;
		this.cannonLengthOffset = -1;
		
		this.radarVisibilityLength = 6;
		this.radarVisibilityWidth = 5;
		this.radarVisibilityLengthOffset = -2;
		
		this.arsenal = new AbstractWeapon[2];
		arsenal[0] = new Cannon(this);
		arsenal[1] = new MineLayer(this);
	}
	
	public Mine removeMine(){
		if (mines.isEmpty()){
			return null;
		} else {
			return mines.remove(mines.size() - 1);
		}
	}
	
	public void pickUpMine(Mine mine){
		mines.add(mine);
		mine.setLocation(-1, -1);
	}
	
	/**
	 * Checks if a coordinate is within sonar range.
	 * The sonar range for a MineLayerShip is the squares directly adjacent to it.
	 * @param x
	 * @param y
	 * @return True of in sonar range, false otherwise.
	 */
	public boolean inSonarRange(int x, int y) { 
		if(!GameBoard.inBounds(x, y)) { 
			return false;
		}
		
		int shipX = this.getX();
		int shipY = this.getY();
		int shipLength = this.getLength();
		
		switch(this.getOrientation()) { 
		case East:
			if(x >= shipX - 1 && x <= shipX + shipLength) { 
				if(y >= shipY - 1 && y <= shipY + 1) { 
					return true;
				}
			}
			break;
		case West:
			if(x >= shipX - shipLength && x <= shipX + 1) { 
				if(y >= shipY - 1 && y <= shipY + 1) { 
					return true;
				}
			}
			break;
		case North:
			if(x >= shipX - 1 && x <= shipX + 1) { 
				if(y >= shipY - shipLength && y <= shipY + 1) { 
					return true;
				}
			}
			break;
		case South:
			if(x >= shipX - 1 && x <= shipX + 1) { 
				if(y >= shipY - 1 && y <= shipY + shipLength) { 
					return true;
				}
			}
			break;
		}
		return false;
	}
}
