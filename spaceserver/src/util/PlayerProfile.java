package util;

public class PlayerProfile {
	private final String userName;
	private final String userPass; 
	private int numWins; 
	private int numLosses;  
	
	public PlayerProfile (String userName, String userPass) {
		this.userName = userName; 
		this.userPass = userPass; 
		this.numWins = 0; 
		this.numLosses = 0; 
	}

	/**
	 * 
	 * @return number of games won.
	 */
	public int getNumWins() {
		return numWins;
	}
	
	/**
	 * Updates user stats when player wins.
	 */
	public void addWin() { 
		numWins++;
	}
	
	/**
	 * Updates user stats when player loses.
	 */
	public void addLoss() { 
		numLosses++;
	}

	/**
	 * 
	 * @return number of games lost.
	 */
	public int getNumLosses() {
		return numLosses;
	}

	/**
	 * 
	 * @return player's username.
	 */
	public String getUserName() {
		return userName;
	}
	
	/* Generic Eclipse-generated hashcode/equals overrides
	 * Equality shouldn't be represented by stats
	 * due to fear of horrible coincidental situations that would break
	 * if stats changed concurrently to non-in-game player checks
	 */

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((userName == null) ? 0 : userName.hashCode());
		result = prime * result
				+ ((userPass == null) ? 0 : userPass.hashCode());
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
		PlayerProfile other = (PlayerProfile) obj;
		if (userName == null) {
			if (other.userName != null)
				return false;
		} else if (!userName.equals(other.userName))
			return false;
		if (userPass == null) {
			if (other.userPass != null)
				return false;
		} else if (!userPass.equals(other.userPass))
			return false;
		return true;
	}
	
	
}
