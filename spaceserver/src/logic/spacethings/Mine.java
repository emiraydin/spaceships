package logic.spacethings;

import logic.FleetCommander;

public class Mine extends SpaceThing {
	
	private static final int damage = 2;
	private FleetCommander owner;
	
	public Mine(){
		super(0,0);
	}
	
	public Mine(FleetCommander fc){
		super(0,0);
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
