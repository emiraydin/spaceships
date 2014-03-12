package logic.spacethings;

import logic.FleetCommander;
import logic.StarBoard;
import messageprotocol.GameStateMessage;

public abstract class SpaceThing {
	private int x, y;
	private int id;
	private FleetCommander owner;
	private StarBoard gameBoard;
	
	public SpaceThing(int x, int y, FleetCommander owner, StarBoard gameBoard){
		this.x = x;
		this.y = y;
		this.gameBoard = gameBoard;
		this.owner = owner;
		this.id = gameBoard.nextID();
	}
	
	public abstract GameStateMessage genGameStateMessage();

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
	
	public FleetCommander getOwner(){
		return owner;
	}
	
	public StarBoard getGameBoard() { 
		return gameBoard;
	}
	
	public void setOwner(FleetCommander owner) { 
		this.owner = owner;
	}
	
}
