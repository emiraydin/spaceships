package messageprotocol;

import java.util.Arrays;

import gameLogic.Constants.OrientationType;
import gameLogic.Constants.PlayerNumber;
import gameLogic.Constants.SpaceThingType;

public class GameStateMessage
{
	int spaceThingId;
	PlayerNumber owner;
	SpaceThingType type;
	int posX;
	int posY;
	OrientationType orientation;
	int[] sectionHealth;
	
	
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
	
	

//	/**
//	 * When a GameStateUpdateMessage is executed, it updates the properties of some spaceThing.
//	 * @throws Exception 
//	 */
//	public void execute() throws Exception
//	{
//		// Doing some serious Reflection here...
//		try
//		{
//			// Get the spacething and figure out if it has an "updateProperties" method, and run it if it can
//			SpaceThing thing = GameState.getSpaceThing(spaceThingId);
//			
//			Class<? extends SpaceThing> thingClass = thing.getClass();
//			
//			// If it has the method then run it
//			for (Method current : thingClass.getMethods()) {
//				if (current.getName().equals("updateProperties")) {
//					current.invoke(thing, this.posX, this.posY, this.orientation, this.sectionHealth);
//					break;
//				}
//			}
//		}
//		catch (Exception e)
//		{
//			throw new Exception("Could not update GameState for thing: " + spaceThingId);
//		}
//	}
	
	
	

}
