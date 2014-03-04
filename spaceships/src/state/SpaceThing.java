package state;

import gameLogic.Constants.PlayerNumber;

public class SpaceThing {
	
	protected int uniqueId;
	protected PlayerNumber owner;
	protected int x;
	protected int y;
	
	public SpaceThing(int id, PlayerNumber owner) {
		this.uniqueId = id;
		this.owner = owner;
	}
	
	public void setOwner(PlayerNumber owner) {
		this.owner = owner;
	}
	
	public int getX() {
		return this.x;
	}
	public int getY() {
		return this.y;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	

}
