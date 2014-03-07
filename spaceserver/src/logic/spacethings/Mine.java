package logic.spacethings;

import logic.FleetCommander;

public class Mine extends SpaceThing {
	
	private static final int damage = 2;
	private FleetCommander owner;
	
	public Mine(int gameID){
		super(0, 0, gameID);
	}
	
	public Mine(FleetCommander fc, int gameID){
		super(0, 0, gameID);
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
