package messageprotocol;

import java.lang.reflect.Method;

import state.GameState;
import state.SpaceThing;
import state.ships.AbstractShip;
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
		if (sectionHealth == null) {
			this.sectionHealth = null;
		} else {
			this.sectionHealth = sectionHealth.clone();			
		}
	}

	/**
	 * When a GameStateUpdateMessage is executed, it updates the properties of some spaceThing.
	 * @throws Exception 
	 */
	@Override
	public void execute() throws Exception
	{
		// Doing some serious Reflection here...
		try
		{
			// Get the spacething and figure out if it has an "updateProperties" method, and run it if it can
			SpaceThing thing = GameState.getSpaceThing(spaceThingId);
			
			Class<? extends SpaceThing> thingClass = thing.getClass();
			
			// If it has the method then run it
			for (Method current : thingClass.getMethods()) {
				if (current.getName().equals("updateProperties")) {
					current.invoke(thing, this.posX, this.posY, this.orientation, this.sectionHealth);
					break;
				}
			}
		}
		catch (Exception e)
		{
			throw new Exception("Could not update GameState for thing: " + spaceThingId);
		}
	}
	

}
