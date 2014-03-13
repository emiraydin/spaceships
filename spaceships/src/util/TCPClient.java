package util;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import messageprotocol.ActionMessage;
import messageprotocol.NewTurnMessage;
import messageprotocol.ServerMessageHandler;

public class TCPClient implements Runnable {

	// Initialize sockets and messageInput/messageOutput streams
	private static Socket clientSocket = null;
	private static PrintStream messageOutput = null;
	private static BufferedReader messageInput = null;
	private static ObjectInputStream objectInput = null;
	private static ObjectOutputStream objectOutput = null;

	private static BufferedReader inputLine = null;
	private static boolean connectionClosed = false;

	public static void main(String[] args) {

		System.out.println("Client is now running on " + Properties.HOST_NAME + " port " + Properties.PORT_NUMBER);

		// Open a socket on given host name and port number, and initialize messageInput/messageOutput streams
		try {
			clientSocket = new Socket(Properties.HOST_NAME, Properties.PORT_NUMBER);
			inputLine = new BufferedReader(new InputStreamReader(System.in));
			messageOutput = new PrintStream(clientSocket.getOutputStream());
			messageInput = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			objectInput = new ObjectInputStream(clientSocket.getInputStream());
			objectOutput = new ObjectOutputStream(clientSocket.getOutputStream());
		} catch (UnknownHostException e) {
			System.err.println("Unknown host: " + Properties.HOST_NAME);
		} catch (IOException e) {
			System.err.println("I/O connection to " + Properties.HOST_NAME + "cannot be established!");
		}

		// Write the data to the recently opened socket, given everything is set up
		if (clientSocket != null && messageOutput != null && messageInput != null) {
			
			try {
				// Create new thread to read from the server
				new Thread(new TCPClient()).start();
				while (!connectionClosed) {
					messageOutput.println(inputLine.readLine().trim());
				}
				// Close streams and sockets
				messageOutput.close();
				messageInput.close();
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
		NewTurnMessage responseObject;
		try {
			responseLine = messageInput.readLine();
			responseObject = (NewTurnMessage)objectInput.readObject();
			while (responseLine != null) {
				System.out.println(responseLine);
				if (responseLine.indexOf("Goodbye") != -1)
					break;
				ServerMessageHandler.executeNewTurnMessage(responseObject);
				
				if (ServerMessageHandler.hasChanged) {
					objectOutput.writeObject((ActionMessage) ServerMessageHandler.currentAction);
					ServerMessageHandler.hasChanged = false;
				}
			}
			connectionClosed = true;
			// Exit the client application
			System.exit(0);
			
		} catch (IOException e) {
			System.err.println(e);
		} catch (ClassNotFoundException e) {
			System.err.println(e);
		}
	}
}