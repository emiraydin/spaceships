package logic.spacethings;

import logic.FleetCommander;
import logic.GameBoard;

public class Mine extends SpaceThing {
	
	private static final int damage = 2;
	private FleetCommander owner;
	
	public Mine(int gameID, GameBoard gameBoard){
		super(-1, -1, gameID, gameBoard);
	}
	
	public Mine(FleetCommander fc, int gameID, GameBoard gameBoard){
		super(-1, -1, gameID, gameBoard);
		owner = fc;
	}
	
	public static int getDamage(){
		return damage;
	}
	
	public FleetCommander getOwner(){
		return owner;
	}
	
	public void setOwner(FleetCommander fc){
		owner = fc;
	}
	
}
