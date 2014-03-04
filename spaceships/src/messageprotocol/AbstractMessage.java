package messageprotocol;

public abstract class AbstractMessage
{
	
	/**
	 * The operation to execute when the message is received from the server.
	 */
	public abstract void execute();

}
