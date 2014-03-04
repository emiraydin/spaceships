package state;

import gameLogic.Constants.PlayerNumber;


public class Mine extends SpaceThing {
	
	int damage;
	
	public Mine(int id, PlayerNumber owner) {
		super(id, owner);
		this.owner = owner;
	}
	
	public int getDamage() {
		return this.damage;
	}
	
	/**
	 * I'm not sure what this function is supposed to do... -Andrew
	 * 
	 * @return
	 */
	public PlayerNumber getOwner() {
		return this.owner;
	}
	
	

}
