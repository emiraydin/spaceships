package messageprotocol;

import java.io.Serializable;
import java.util.Arrays;

import gameLogic.Constants.OrientationType;
import gameLogic.Constants.PlayerNumber;
import gameLogic.Constants.SpaceThingType;

public class GameStateMessage implements Serializable {
	
	private static final long serialVersionUID = -7885291047262853973L;
	
	
	int spaceThingId;
	PlayerNumber owner;
	SpaceThingType type;
	int posX;
	int posY;
	OrientationType orientation;
	int[] sectionHealth;
	
	/**
	 * 
	 * @param spaceThingId
	 * @param owner
	 * @param type
	 * @param posX
	 * @param posY
	 * @param orientation
	 * @param sectionHealth
	 */
	public GameStateMessage(
			int spaceThingId,
			PlayerNumber owner,
			SpaceThingType type,
			int posX,
			int posY,
			OrientationType orientation,
			int[] sectionHealth) {
		
		this.spaceThingId = spaceThingId;
		this.owner = owner;
		this.type = type;
		this.posX = posX;
		this.posY = posY;
		this.orientation = orientation;
		if (sectionHealth == null) {
			this.sectionHealth = null;
		} else {
			this.sectionHealth = sectionHealth.clone();			
		}
	}


	public int getSpaceThingId() {
		return spaceThingId;
	}


	public PlayerNumber getOwner() {
		return owner;
	}


	public SpaceThingType getType() {
		return type;
	}


	public int getPosX() {
		return posX;
	}


	public int getPosY() {
		return posY;
	}


	public OrientationType getOrientation() {
		return orientation;
	}


	public int[] getSectionHealth() {
		return sectionHealth;
	}


	@Override
	public String toString() {
		return "GameStateMessage [spaceThingId=" + spaceThingId + ", owner="
				+ owner + ", type=" + type + ", posX=" + posX + ", posY="
				+ posY + ", orientation=" + orientation + ", sectionHealth="
				+ Arrays.toString(sectionHealth) + "]";
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((orientation == null) ? 0 : orientation.hashCode());
		result = prime * result + ((owner == null) ? 0 : owner.hashCode());
		result = prime * result + posX;
		result = prime * result + posY;
		result = prime * result + Arrays.hashCode(sectionHealth);
		result = prime * result + spaceThingId;
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		GameStateMessage other = (GameStateMessage) obj;
		if (orientation != other.orientation)
			return false;
		if (owner != other.owner)
			return false;
		if (posX != other.posX)
			return false;
		if (posY != other.posY)
			return false;
		if (!Arrays.equals(sectionHealth, other.sectionHealth))
			return false;
		if (spaceThingId != other.spaceThingId)
			return false;
		if (type != other.type)
			return false;
		return true;
	}

}
