package logic.spacethings;

import logic.GameBoard;

public class BaseTile extends SpaceThing {
	private int health = 1;
	
	public BaseTile(int x, int y, GameBoard gameBoard){
		super(x, y, gameBoard);
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}
	
	public void decrementBaseHealth(int amount) { 
		this.health = this.health - amount;
	}
}
