package messagehandlers;

import java.util.ArrayList;

import messageprotocol.AbstractMessage;

public class ServerMessageHandler
{
	/**
	 * Execute a series of messages from the server.
	 * Uses Visitor design pattern.
	 * 
	 * @param messages
	 */
	public static void handleMessages(ArrayList<AbstractMessage> messages) {
		for (AbstractMessage current : messages) {
			current.execute();
		}
	}

}
