package messageprotocol;

import java.lang.reflect.Method;

import state.GameState;
import state.SpaceThing;

import messageprotocol.*;


public class ServerMessageHandler
{
	
	/**
	 * Execute a NewTurnMessage.  The server sends each client one of these 
	 * every turn.  If a NewTurnMessage field is null, this function ignores it.
	 * 
	 * @param turn
	 */
	public static void executeNewTurnMessage(NewTurnMessage turn) {
		// First, we execute the action message.  This should do nothing for now...
		if (turn.getAction() != null) {
			executeActionMessage(turn.getAction());			
		}
		
		// Now, execute allf of the gamestate messages.
		if  (turn.getState() != null) {
			for (GameStateMessage state : turn.getState()) {
				try {
					executeGameStateMessage(state);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}			
		}
		
		// Now, update the radar and sonar visible tiles...
		if (turn.getRadarVisibleTiles() != null) {
			GameState.setRadarVisibleTiles(turn.getRadarVisibleTiles());			
		}
		if (turn.getSonarVisibleTiles() != null) {
			GameState.setSonarVisibleTiles(turn.getSonarVisibleTiles());			
		}	
	}
	
	/**
	 * Execute an action message.  This usually corresponds to some animation
	 * and particle effects.
	 * 
	 * @param action
	 */
	public static void executeActionMessage(ActionMessage action) {
	  // TODO Write this method...
	}
	
	/**
	 * Executes a GameStateMessage.  This results in either a SpaceThing being updated
	 * or created anew.
	 * 
	 * @param state
	 * @throws Exception
	 */
	public static void executeGameStateMessage(GameStateMessage state) throws Exception {
		// Doing some serious Reflection here...
		try	{
			// Check if the specified spacething exists....
			if (GameState.spaceThingExists(state.getSpaceThingId())) {
				// Get the spacething and figure out if it has an "updateProperties" method, and run it if it can
				SpaceThing thing = GameState.getSpaceThing(state.getSpaceThingId());
				
				Class<? extends SpaceThing> thingClass = thing.getClass();
				
				// If it has the method then run it
				for (Method current : thingClass.getMethods()) {
					if (current.getName().equals("updateProperties")) {
						current.invoke(
								thing,
								state.getPosX(),
								state.getPosY(),
								state.getOrientation(),
								state.getSectionHealth());
						break;
					}
				}	
			} else {
				// Spacething does not exist, so we're going to build it...
				GameState.createSpaceThing(
				state.getSpaceThingId(),
				state.getOwner(), 
				state.getType(), 
				state.getPosX(), 
				state.getPosY(), 
				state.getOrientation());
			}
		}
		catch (Exception e)	{
			throw new Exception("Could not update GameState for thing: " + state.getSpaceThingId());
		}		
	}
}
