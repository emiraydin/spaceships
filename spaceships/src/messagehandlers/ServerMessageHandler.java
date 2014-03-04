package messagehandlers;

import java.util.List;

import messageprotocol.AbstractMessage;

public class ServerMessageHandler
{
	/**
	 * Execute a series of messages from the server.
	 * Uses Visitor design pattern.
	 * 
	 * @param messages
	 */
	public static void handleMessages(List<AbstractMessage> messages) {
		for (AbstractMessage current : messages) {
			current.execute();
		}
	}

}
