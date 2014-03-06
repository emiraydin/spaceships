package messageprotocol;

/**
 * Generic message received by the server from the client.
 *
 */
public interface Message {
	
	/**
	 * The operation to execute when the message is received from the client.
	 * @throws Exception 
	 */
	public void execute() throws Exception;
}
