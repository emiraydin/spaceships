package messageprotocol;

import state.GameState;
import gameLogic.Constants.OrientationType;
import gameLogic.Constants.PlayerNumber;
import gameLogic.Constants.SpaceThingType;

public class GameStateBuildMessage extends AbstractMessage
{
	int spaceThingId;
	PlayerNumber owner;
	SpaceThingType type;
	int posX;
	int posY;
	OrientationType orientation;
	
	/**
	 * Construct the GameStateBuildMessage.
	 * 
	 * @param spaceThingId
	 * @param owner
	 * @param type
	 * @param posX
	 * @param posY
	 * @param orientation
	 */
	public GameStateBuildMessage(int spaceThingId, PlayerNumber owner, SpaceThingType type, int posX, int posY, OrientationType orientation) {
		this.spaceThingId = spaceThingId;
		this.owner = owner;
		this.type = type;
		this.posX = posX;
		this.posY = posY;
		this.orientation = orientation;
	}

//	/**
//	 * Creates a SpaceThing.
//	 */
//	@Override
//	public void execute()
//	{
//		GameState.createSpaceThing(
//				this.spaceThingId,
//				this.owner, 
//				this.type, 
//				this.posX, 
//				this.posY, 
//				this.orientation);
//	}
	
	
	

}
