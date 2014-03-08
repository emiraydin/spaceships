package logic.spacethings;

import logic.GameBoard;

public abstract class SpaceThing {
	private int x, y;
	private int id;
	private GameBoard gameBoard;
	
	public SpaceThing(int x, int y, GameBoard gameBoard){
		this.x = x;
		this.y = y;
		this.gameBoard = gameBoard;
		this.id = gameBoard.nextID();
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
	
	public GameBoard getGameBoard() { 
		return gameBoard;
	}
}
