package state;

import gameLogic.Constants.PlayerNumber;


public class Mine {
	
	PlayerNumber owner;
	int damage;
	
	public Mine(PlayerNumber owner) {
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
