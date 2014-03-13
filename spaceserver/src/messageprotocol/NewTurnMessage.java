package messageprotocol;

import java.io.Serializable;
import java.util.Arrays;
import java.util.LinkedList;

import messageprotocol.*;

/**
 * 
 * This message is sent from the Server to the Client at the beginning of a turn.
 * It consists of the previous action, a set of GameState messages for updating the game state, and visibility.
 *
 */
public class NewTurnMessage implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 9172096966349741980L;
	
	/**
	 * Whatever action has taken place this turn.
	 */
	ActionMessage action;
	
	/**
	 * A list of state messages for updating the GameState.
	 */
	LinkedList<GameStateMessage> state;
	boolean turnSuccess;
	String response;
	
	/**
	 * Radar and sonar visibility.
	 */
	boolean[][] radarVisibleTiles;
	boolean[][] sonarVisibleTiles;
	int playerID;

	/**
	 * 
	 * @param action
	 * @param turnSuccess
	 * @param response
	 * @param state
	 * @param radarVisibleTiles
	 * @param sonarVisibleTiles
	 */
	public NewTurnMessage(
			ActionMessage action,
			boolean turnSuccess,
			String response,
			LinkedList<GameStateMessage> state,
			boolean[][] radarVisibleTiles,
			boolean[][]	sonarVisibleTiles,
			int pid
					) {
		
		this.action = action;
		this.turnSuccess = turnSuccess;
		this.response = response;
		
		// If the state is null, then make an empty linked list.
		if (state == null) {
			this.state = new LinkedList<GameStateMessage>();
		} else {
			this.state = state;			
		}
		
		this.radarVisibleTiles = radarVisibleTiles;
		this.sonarVisibleTiles = sonarVisibleTiles;
		
		this.playerID = pid;
	}
	
	public ActionMessage getAction() {
		return action;
	}

	public void setAction(ActionMessage action) {
		this.action = action;
	}

	public LinkedList<GameStateMessage> getState() {
		return state;
	}

	public void setState(LinkedList<GameStateMessage> state) {
		this.state = state;
	}

	public boolean[][] getRadarVisibleTiles() {
		return radarVisibleTiles;
	}

	public void setRadarVisibleTiles(boolean[][] radarVisibleTiles) {
		this.radarVisibleTiles = radarVisibleTiles;
	}

	public boolean[][] getSonarVisibleTiles() {
		return sonarVisibleTiles;
	}

	public void setSonarVisibleTiles(boolean[][] sonarVisibleTiles) {
		this.sonarVisibleTiles = sonarVisibleTiles;
	}

	/**
	 * Add a GameStateMessage to the end of the LinkedList.
	 * 
	 * @param input GameStateMessage the message to add
	 */
	public void addStateMessage(GameStateMessage input) {
		this.state.addLast(input);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String radVis = null;
		if(radarVisibleTiles != null) { 
			radVis = "[";
			for(boolean[] b : radarVisibleTiles) { 
				radVis += "[";
				for(int i = 0; i < b.length-1; i++) {
					radVis += b[i] + ", ";
				}
				radVis += b[b.length-1];
				radVis += "]";
			}
			radVis += "]";
		} 
 				
		String sonVis = null;
		if(sonarVisibleTiles != null) { 
			for(boolean[] b : sonarVisibleTiles) { 
				sonVis += "[";
				for(int i = 0; i < b.length-1; i++) { 
					sonVis += b[i] + ", ";
				}
				sonVis += b[b.length - 1];
				sonVis += "]";
			}
			sonVis += "]";
		}
		
		return "NewTurnMessage [action=" + action + ", state=" + state
				+ ", turnSuccess=" + turnSuccess + ", response=" + response
				+ ", radarVisibleTiles=" + radVis
				+ ", sonarVisibleTiles=" + sonVis
				+ ", playerID=" + playerID + "]";
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((action == null) ? 0 : action.hashCode());
		result = prime * result + Arrays.hashCode(radarVisibleTiles);
		result = prime * result
				+ ((response == null) ? 0 : response.hashCode());
		result = prime * result + Arrays.hashCode(sonarVisibleTiles);
		result = prime * result + ((state == null) ? 0 : state.hashCode());
		result = prime * result + playerID;
		result = prime * result + (turnSuccess ? 1231 : 1237);
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NewTurnMessage other = (NewTurnMessage) obj;
		if (action == null) {
			if (other.action != null)
				return false;
		} else if (!action.equals(other.action))
			return false;
		if (!Arrays.deepEquals(radarVisibleTiles, other.radarVisibleTiles))
			return false;
		if (response == null) {
			if (other.response != null)
				return false;
		} else if (!response.equals(other.response))
			return false;
		if (!Arrays.deepEquals(sonarVisibleTiles, other.sonarVisibleTiles))
			return false;
		if (state == null) {
			if (other.state != null)
				return false;
		} else if (!state.equals(other.state))
			return false;
		if (playerID != other.playerID)
			return false;
		if (turnSuccess != other.turnSuccess)
			return false;
		return true;
	}
	
	public boolean isTurnSuccess() {
		return turnSuccess;
	}

	public void setTurnSuccess(boolean turnSuccess) {
		this.turnSuccess = turnSuccess;
	}
	
	

	
}
