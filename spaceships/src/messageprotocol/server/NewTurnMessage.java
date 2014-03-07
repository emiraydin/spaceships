package messageprotocol.server;

import java.util.LinkedList;

import messageprotocol.*;

/**
 * 
 * This message is sent from the Server to the Client at the beginning of a turn.
 * It consists of the previous action, a set of GameState messages for updating the game state, and visibility.
 *
 */
public class NewTurnMessage
{
	/**
	 * Whatever action has taken place this turn.
	 */
	ActionMessage action;
	
	/**
	 * A list of state messages for updating the GameState.
	 */
	LinkedList<GameStateMessage> state;
	
	/**
	 * Radar and sonar visibility.
	 */
	boolean[][] radarVisibleTiles;
	boolean[][] sonarVisibleTiles;
}
