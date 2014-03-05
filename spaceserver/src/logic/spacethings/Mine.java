package logic.spacethings;

import logic.FleetCommander;

public class Mine extends SpaceThing {
	
	private static final int damage = 2;
	private FleetCommander owner;
	
	public Mine(){
		super();
	}
	
	public Mine(FleetCommander fc){
		super();
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
