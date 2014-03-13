package messageprotocol;

import util.PlayerProfile;

public class MatchRequestMessage implements Message {
	String challenger;
	String opponent;
	
	/**
	 * 
	 * @param challenger String
	 * @param opponent String
	 */
	public MatchRequestMessage(String challenger, String opponent) { 
		this.challenger = challenger;
		this.opponent = opponent;
	}

	/**
	 * 
	 */
	@Override
	public void execute() throws Exception {
		PlayerProfile challenger = null;
		PlayerProfile opponent = null;
		// TODO: get PlayerProfile from however we are storing them.
		// TODO: game request things
	}
	
	
}
