package state;

import common.GameConstants.*;

/**
 * These are the tiles that make up the fleet commander's base.
 */
public class BaseTile extends SpaceThing {

	protected int[] sectionHealth;
	
	public BaseTile(int id, PlayerNumber owner)
	{
		super(id, SpaceThingType.BaseTile, owner);
		this.sectionHealth = new int[1];
		this.sectionHealth[0] = 1;
	}
	
	/*
	 * SectionHealth getters and setters
	 */
	public void setSectionHealth(int[] input) {
		this.sectionHealth = input.clone();
	}
	public int[] getSectionHealth() {
		return this.sectionHealth.clone();
	}

}
