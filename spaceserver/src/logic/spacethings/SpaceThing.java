package logic.spacethings;

import logic.GameBoard;
import logic.IDManager;

public abstract class SpaceThing {
	private int x, y;
	private int id;
	GameBoard gameBoard;
	
	public SpaceThing(int x, int y, int gid, GameBoard gameBoard){
		this.x = x;
		this.y = y;
		this.id = IDManager.nextID(gid);
		this.gameBoard = gameBoard;
	}
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public int getID(){
		return id;
	}
}
