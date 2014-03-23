package messageprotocol;

import util.MatchMaker;
import util.PlayerProfile;

public class PlayerOfflineMessage implements Message {
	
	private final String userName;  
	
	/**
	 * 
	 * @param userName
	 */
	public PlayerOfflineMessage(String userName) { 
		this.userName = userName;
	}
	
	@Override
	public void execute() throws Exception {
		PlayerProfile player = null;
		// TODO: get PlayerProfile from however we are storing them.
		MatchMaker.getInstance().playerLoggedOff(player);
	}

}
