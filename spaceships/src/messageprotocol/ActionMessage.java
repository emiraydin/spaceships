package messageprotocol;

import gameLogic.Constants.ActionType;

public class ActionMessage extends AbstractMessage
{
	ActionType action;
	int spaceThingId;
	int destX;
	int destY;
	
	/**
	 * Encode the action message.
	 * 
	 * @param type
	 * @param spaceThingId
	 * @param destX
	 * @param destY
	 */
	public ActionMessage(ActionType type, int spaceThingId, int destX, int destY) {
		this.action = type;
		this.spaceThingId = spaceThingId;
		this.destX = destX;
		this.destY = destY;
	}

}
