package util;

/**
 * Represents the request for a game made by one player towards another.
 * @author Tina
 *
 */
public class GameRequest { 
	String challenger;
	String opponent;
	
	public GameRequest(String challenger, String opponent) { 
		this.challenger = challenger;
		this.opponent = opponent;
	}	
}
