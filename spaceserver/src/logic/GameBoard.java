package logic;

import logic.spacethings.AbstractShip;
import logic.spacethings.SpaceThing;


public class GameBoard {
	SpaceThing[][] map;
	
	public SpaceThing getSpaceThing(int x, int y){
		// TODO: Check bounds
		return map[x][y];
	}
	public void setSpaceThing(SpaceThing sThing, int x, int y){
		
	}
	public void setSpaceThing(AbstractShip ship, int x, int y){
		
	}
	public void clearSpaceThing(int x, int y){
		
	}
	public void generateAsteroids(){
		
	}
	public boolean isBlocked(int x, int y){
		return (map[x][y] != null);
	}
	public boolean handleMineExplosions(){
		//TODO: Mine explosions... can this be moved?..
		return false;
	}
	
//	I think this will be unneeded.
//	public getMaxMoveRange(AbstractShip ship, int x, int y){
//		
//	}

}
