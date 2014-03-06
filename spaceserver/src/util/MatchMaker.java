package util;

import java.util.HashSet;

public class MatchMaker implements Runnable {

	private static MatchMaker instance;
	
	/** Online players at any time */
	HashSet<PlayerProfile> onlinePlayers;

	
	/**
	 * Get singleton
	 * */
	public synchronized static MatchMaker getInstance() { 
		if(instance == null) { 
			instance = new MatchMaker();
		}
		return instance;
	}
	
	private MatchMaker() { 
		onlinePlayers = new HashSet<PlayerProfile>();
	}
		
	/** 
	 * Called when user appears online and is ready to play game
	 * @param player The profile of the new player online
	 */
	public synchronized void playerLoggedOn(PlayerProfile player) { 
		onlinePlayers.add(player);
	}
	
	/**
	 * Called when user logs off.
	 * @param player The profile of the player who logged off.
	 */
	public synchronized void playerLoggedOff(PlayerProfile player) { 
		onlinePlayers.remove(player);
	}

	@Override
	public void run() {
		// TODO: ugh i'll deal with this later. Need to make singleton monitor that idles...
		
	}
	
}
