package logic;

import logic.spacethings.AbstractShip;


public class HeavyCannon extends Cannon {

	public HeavyCannon(AbstractShip owner) {
		super(owner);
		// override damage from 1 to 2
		damage = 2;
	}
}
