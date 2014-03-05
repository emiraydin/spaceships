package logic.spacethings;

import java.util.ArrayList;

public class MineLayerShip extends AbstractShip {
	private ArrayList<Mine> mines;
	
	public MineLayerShip(){
		super();
		mines = new ArrayList<Mine>(5);
		for (int i = 0; i < 5; i++){
			mines.add(new Mine());
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
