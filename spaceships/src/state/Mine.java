package state;

import common.GameConstants.*;


public class Mine extends SpaceThing {
	
	int damage;
	
	public Mine(int id, PlayerNumber owner) {
		super(id, SpaceThingType.Mine, owner);
	}
	
	public int getDamage() {
		return this.damage;
	}
	
}
