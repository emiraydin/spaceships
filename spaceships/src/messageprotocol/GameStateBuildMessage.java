package messageprotocol;

import gameLogic.Constants.OrientationType;
import gameLogic.Constants.SpaceThingType;

public class GameStateBuildMessage extends AbstractMessage
{
	int spaceThingId;
	SpaceThingType type;
	int posX;
	int posY;
	OrientationType orientation;
	
	public GameStateBuildMessage(int spaceThingId, SpaceThingType type, int posX, int posY, OrientationType orientation) {
		this.spaceThingId = spaceThingId;
		this.type = type;
		this.posX = posX;
		this.posY = posY;
		this.orientation = orientation;
	}

	@Override
	public void execute()
	{
		// Create a new spaceThing and tie it to its unique id.
		
	}
	
	
	

}
