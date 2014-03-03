package messageprotocol;

import gameLogic.Constants.OrientationType;

public class GameStateUpdateMessage extends AbstractMessage
{
	int spaceThingId;
	int posX;
	int posY;
	OrientationType orientation;
	int[] sectionHealth;
	
	public GameStateUpdateMessage(int spaceThingId, int posX, int posY, OrientationType orientation, int[] sectionHealth) {
		this.spaceThingId = spaceThingId;
		this.posX = posX;
		this.posY = posY;
		this.orientation = orientation;
		this.sectionHealth = sectionHealth.clone();
	}

	/**
	 * When a GameStateUpdateMessage is executed, it updates the properties of some spaceThing.
	 */
	@Override
	public void execute()
	{
		// Fine some spaceThing based on its unique ID number and run updateProperties() on it.
		
	}
	

}
