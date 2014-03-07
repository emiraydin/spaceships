package logic.spacethings;

import java.util.ArrayList;


public class MineLayerShip extends AbstractShip {
	private ArrayList<Mine> mines;
	private static int NUM_MINES = 5;
	
	public MineLayerShip(int x, int y, int gameID){
		super(x, y, gameID);
		mines = new ArrayList<Mine>(NUM_MINES);
		for (int i = 0; i < 5; i++){
			//TODO: Add id generation
			mines.add(new Mine(0));
		}
	}
	
	public Mine removeMine(){
		if (mines.isEmpty()){
			return null;
		} else {
			return mines.remove(mines.size() - 1);
		}
	}
	
	public void addMine(Mine mine){
		mines.add(mine);
	}
}
