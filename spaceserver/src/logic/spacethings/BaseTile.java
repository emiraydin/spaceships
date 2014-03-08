package logic.spacethings;

import logic.GameBoard;

public class BaseTile extends SpaceThing {
	private int health = 1;
	
	public BaseTile(int x, int y, int gameID, GameBoard gameBoard){
		super(x, y, gameID, gameBoard);
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}
}
