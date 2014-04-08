package messageprotocol;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;

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
	LinkedList<Integer> longRadarEnabledShips;
	
	HashMap<Integer,Integer> mineParents;
	
	int playerID;
	int responseTo;			// for failed messages

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
			int pid,
			int responseTo
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
		this.responseTo = responseTo;
		
		this.mineParents = new HashMap<Integer, Integer>();
	}
	
	public ActionMessage getAction() {
		return action;
	}

	public void setAction(ActionMessage action) {
		this.action = action;
	}
	
	public void setLongRadarEnabledShips(LinkedList<Integer> lreShips){
		this.longRadarEnabledShips = lreShips;
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

	
	public boolean isTurnSuccess() {
		return turnSuccess;
	}

	public void setTurnSuccess(boolean turnSuccess) {
		this.turnSuccess = turnSuccess;
	}
	
	public int getPlayerID() {
		return this.playerID;
	}
	
	public String getResponseString() { 
		return this.response;
	}
	
	public LinkedList<Integer> getLongRadarEnabledShips(){
		return this.longRadarEnabledShips;
	}
	
	public int responseTo() { 
		return this.responseTo;
	}
	
	/**
	 * Add a mine's parent ship.
	 * 
	 * @param mineId id of mine
	 * @param parentId id of ship
	 */
	public void addMineParent(int mineId, int parentId) {
		this.mineParents.put(mineId, parentId);
	}
	
	/**
	 * Get the parent ship of a specific mine.
	 * 
	 * @param mineId id of mine
	 * @return
	 */
	public int getMineParent(int mineId) {
		return this.mineParents.get(mineId);
	}
	
	/**
	 * Get the entire mineParents hashmap.
	 * 
	 * @return
	 */
	public HashMap<Integer,Integer> getAllMineParents() {
		return this.mineParents;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "NewTurnMessage [action=" + action + ", state=" + state
				+ ", turnSuccess=" + turnSuccess + ", response=" + response
				+ ", radarVisibleTiles=" + Arrays.toString(radarVisibleTiles)
				+ ", sonarVisibleTiles=" + Arrays.toString(sonarVisibleTiles)
				+ ", mineParents=" + mineParents + ", playerID=" + playerID
				+ ", responseTo=" + responseTo + "]";
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((action == null) ? 0 : action.hashCode());
		result = prime * result
				+ ((mineParents == null) ? 0 : mineParents.hashCode());
		result = prime * result + playerID;
		result = prime * result + Arrays.hashCode(radarVisibleTiles);
		result = prime * result
				+ ((response == null) ? 0 : response.hashCode());
		result = prime * result + responseTo;
		result = prime * result + Arrays.hashCode(sonarVisibleTiles);
		result = prime * result + ((state == null) ? 0 : state.hashCode());
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
		if (mineParents == null) {
			if (other.mineParents != null)
				return false;
		} else if (!mineParents.equals(other.mineParents))
			return false;
		if (playerID != other.playerID)
			return false;
		if (!Arrays.deepEquals(radarVisibleTiles, other.radarVisibleTiles))
			return false;
		if (response == null) {
			if (other.response != null)
				return false;
		} else if (!response.equals(other.response))
			return false;
		if (responseTo != other.responseTo)
			return false;
		if (!Arrays.deepEquals(sonarVisibleTiles, other.sonarVisibleTiles))
			return false;
		if (state == null) {
			if (other.state != null)
				return false;
		} else if (!state.equals(other.state))
			return false;
		if (turnSuccess != other.turnSuccess)
			return false;
		return true;
	}
	
	
	
}