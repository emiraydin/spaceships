package messageprotocol;

public abstract class AbstractMessage
{
	
	/**
	 * The operation to execute when the message is received from the server.
	 * @throws Exception 
	 */
	public abstract void execute() throws Exception;

}
