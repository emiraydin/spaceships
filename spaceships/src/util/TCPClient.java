package util;

import java.io.PrintStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import state.GameState;

import messageprotocol.ActionMessage;
import messageprotocol.NewTurnMessage;
import messageprotocol.ServerMessageHandler;

public class TCPClient implements Runnable {

	// Initialize sockets and input/output streams
	private static Socket clientSocket = null;
	private static PrintStream output = null;
	private static BufferedReader input = null;

	private static BufferedReader inputLine = null;
	private static boolean connectionClosed = false;
	public static boolean canStart;

	public static void start() {

		System.out.println("Client is now running on " + Properties.SERVER_HOST + " port " + Properties.PORT_NUMBER);

		// Open a socket on given host name and port number, and initialize input/output streams
		try {
			clientSocket = new Socket(Properties.SERVER_HOST, Properties.PORT_NUMBER);
			inputLine = new BufferedReader(new InputStreamReader(System.in));
			output = new PrintStream(clientSocket.getOutputStream(), true);
			input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		} catch (UnknownHostException e) {
			System.err.println("Unknown host: " + Properties.SERVER_HOST);
		} catch (IOException e) {
			System.err.println("I/O connection to " + Properties.SERVER_HOST + " cannot be established!");
		}

		// Write the data to the recently opened socket, given everything is set up
		if (clientSocket != null && output != null && input != null) {

			try {
				// Create new thread to read from the server
				new Thread(new TCPClient()).start();
				while (!connectionClosed) {
					// Send the message
					if (inputLine.ready()) {		
						output.println(inputLine.readLine().trim());
						System.out.println("I'm here inputLine.ready");
						output.flush();
					}
//					System.out.println(ServerMessageHandler.currentAction);
//					Thread.sleep(1000);
					// Send the ActionMessage
					if (ServerMessageHandler.hasChanged) {
						System.out.println("There is a new ActionMessage!");
						ActionMessage am = ServerMessageHandler.currentAction;
						System.out.println(am.toString());
						String amString = "@" + ObjectConverter.objectToString(am);
						output.println(amString);
						ServerMessageHandler.hasChanged = false;
					}

				}
				// Close streams and sockets
				output.close();
				input.close();
				clientSocket.close();
			} catch (IOException e) {
				System.err.println(e);
			}

		}

	}

	// Run the thread that will be created to read from the server
	public void run() {
		// Continue reading from the socket until the Goodbye signal is sent from the server
		String responseLine;
		try {
			while ((responseLine = input.readLine()) != null) {
				if (responseLine.startsWith("@")) {
					NewTurnMessage received = (NewTurnMessage) ObjectConverter.stringtoObject(responseLine.substring(1));
					// Process the NewTurnMessage
					ServerMessageHandler.executeNewTurnMessage(received);
					System.out.println(GameState.getAllSpaceThings().size()); 
					System.out.println(received.toString());
				} else if (responseLine.startsWith("//connected")) {
					canStart = true;
					System.out.println("Connected and matched!");
				} else {
					System.out.println(responseLine);
				}

				if (responseLine.indexOf("Goodbye") != -1)
					break;
			}

			connectionClosed = true;
			// Exit the client application
			System.exit(0);

		} catch (IOException | ClassNotFoundException e) {
			System.err.println(e);
		}
	}
}