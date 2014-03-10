package messageprotocol;

import java.io.Serializable;

import gameLogic.Constants.ActionType;

public class ActionMessage implements Serializable {
	
	private static final long serialVersionUID = 2802359185098055738L;
	
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

	public ActionType getAction() {
		return action;
	}

	public int getSpaceThingId() {
		return spaceThingId;
	}

	public int getDestX() {
		return destX;
	}

	public int getDestY() {
		return destY;
	}

	@Override
	public String toString() {
		return "ActionMessage [action=" + action + ", spaceThingId="
				+ spaceThingId + ", destX=" + destX + ", destY=" + destY + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((action == null) ? 0 : action.hashCode());
		result = prime * result + destX;
		result = prime * result + destY;
		result = prime * result + spaceThingId;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ActionMessage other = (ActionMessage) obj;
		if (action != other.action)
			return false;
		if (destX != other.destX)
			return false;
		if (destY != other.destY)
			return false;
		if (spaceThingId != other.spaceThingId)
			return false;
		return true;
	}
	
	

}
