package logic.spacethings;

import logic.StarBoard;
import messageprotocol.GameStateMessage;

import common.GameConstants.SpaceThingType;

public class Asteroid extends SpaceThing {
	
	public Asteroid(int x, int y, StarBoard gameBoard){
		super(x, y, null, gameBoard);
	}

	@Override
	public GameStateMessage genGameStateMessage() {
		return new GameStateMessage(getID(), null, SpaceThingType.Asteroid, getX(), getY(), null, null);
	}
	
}
