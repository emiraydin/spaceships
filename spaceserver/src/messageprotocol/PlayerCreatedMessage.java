package messageprotocol;

import util.MatchMaker;

public class PlayerCreatedMessage implements Message {
	
	private final String userName; 
	private final String userPass; 
	
	/**
	 * 
	 * @param userName
	 * @param userPass
	 */
	public PlayerCreatedMessage(String userName, String userPass) { 
		this.userName = userName;
		this.userPass = userPass;
	}
	
	@Override
	public void execute() throws Exception {
		// TODO: create a new PlayerProfile and store it however we are storing it.
	}

}
