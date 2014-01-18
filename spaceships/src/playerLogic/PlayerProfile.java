package playerLogic;

/**
 * Encapsulates all the logic associated with the Player Object. 
 * Allows for convenient access to Profile, Name, Wins, Losses, Etc. 
 * @author vikramsundaram
 *
 */
public class PlayerProfile
{
	private final String userName; 
	private final String userPass; 
	private int numWins; 
	private int numLosses;  
	
	/**
	 * Current Default Constructor. 
	 * TODO: Make changes so we can load and save the profile from Server. 
	 */
	public PlayerProfile (String userName, String userPass)
	{
		this.userName = userName; 
		this.userPass = userPass; 
		this.numWins = 0; 
		this.numLosses = 0; 
	}
	
	/**
	 * Returns the Players Name
	 */
	public String getUserName()
	{
		return this.userName; 
	}
	
	/**
	 * Returns the Number of Wins
	 */
	public int getNumberWins()
	{
		return this.numWins; 
	}
	
	/**
	 * Returns the Number of Losses
	 */
	public int getNumberLosses()
	{
		return this.numLosses; 
	}
	
	// TODO: We will add other logics to the Userprofile entity later. Such as validation and credential listing. 
}
