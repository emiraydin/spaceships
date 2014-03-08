package logic.spacethings;

import logic.FleetCommander;
import logic.GameBoard;

public class Mine extends SpaceThing {
	
	private static final int damage = 2;
	private FleetCommander owner;
	
	public Mine(GameBoard gameBoard){
		super(-1, -1, gameBoard);
	}
	
	public Mine(FleetCommander fc, int gameID, GameBoard gameBoard){
		super(-1, -1, gameBoard);
		owner = fc;
	}

	
	public void detonate(int x, int y){
		if (getGameBoard().getSpaceThing(x, y) instanceof AbstractShip){
			AbstractShip ship = (AbstractShip) getGameBoard().getSpaceThing(x, y);
			int section = ship.getSectionAt(x, y);
			int section2;
			if (section + 1 < ship.getLength()){
				section2 = section + 1;
			} else {
				section2 = section - 1;
			}
			ship.decrementSectionHealth(2, section);
			ship.decrementSectionHealth(2, section2);
		}
		
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
	
	public void setLocation(int x, int y) { 
		this.setX(x);
		this.setY(y);
	}
	
}
