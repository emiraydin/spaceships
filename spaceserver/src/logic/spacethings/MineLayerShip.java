package logic.spacethings;

import java.util.ArrayList;

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
		for (int i = 0; i < 5; i++){
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
	
	public void addMine(Mine mine){
		mines.add(mine);
	}
}
