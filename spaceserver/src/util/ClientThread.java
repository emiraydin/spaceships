package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;

import logic.GameHandler;
import messageprotocol.ActionMessage;
import messageprotocol.NewTurnMessage;

import common.GameConstants;

class ClientThread extends Thread {

	private Socket clientSocket = null;
	private String clientName = null;
	private String matchName = null;
	private GameHandler currentGame = null;
	private boolean prompted = false;
	private int playerID;
	private BufferedReader messageInput = null;
	private PrintStream messageOutput = null;
	private ObjectInputStream objectInput = null;
	private ObjectOutputStream objectOutput = null;
	private final ClientThread[] allThreads;
	private int maxPlayersOnServer;

	public ClientThread(Socket clientSocket, ClientThread[] threads) {
		this.clientSocket = clientSocket;
		this.allThreads = threads;
		maxPlayersOnServer = threads.length;
		this.playerID = 1;
	}

	public void run() {
		int maxPlayersOnServer = this.maxPlayersOnServer;
		ClientThread[] allThreads = this.allThreads;

		try {

			// Create I/O streams and set up the user
			messageInput = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			messageOutput = new PrintStream(clientSocket.getOutputStream());
			objectInput = new ObjectInputStream(clientSocket.getInputStream());
			objectOutput = new ObjectOutputStream(clientSocket.getOutputStream());
			
			String userName;
			messageOutput.println("Enter your user name: ");
			userName = messageInput.readLine().trim();

			// New client enters the game
			messageOutput.println("Welcome " + userName + "!.\nType /exit to quit game.");
			
			ArrayList<String> onlinePlayers = new ArrayList<String>();
			
			synchronized (this) {
				// Initialize the user name for the new client and list all online users
				for (int i = 0; i < maxPlayersOnServer; i++) {
					if (allThreads[i] != null && allThreads[i] == this) {
						clientName = userName;
						break;
					}
					String currentName = allThreads[i].clientName;
					if (currentName != null)
						onlinePlayers.add(currentName);
				}
				
				// Show all online users
				if (onlinePlayers.isEmpty()) {
					messageOutput.println("You are the only online player. Wait for others to join!");
				} else {
					messageOutput.println("Online players:");
					for (String p: onlinePlayers)
						messageOutput.println(p);
				}
					
				// Let the unmatched user know about the new player
				for (int i = 0; i < maxPlayersOnServer; i++) {
					if (allThreads[i] != null && allThreads[i] != this && this.matchName == null) {
						allThreads[i].messageOutput.println(userName + " is now online!");
					}
				}
				
				// Match players
				while (this.matchName == null && this.prompted == false) {
					messageOutput.println("Type one of the usernames to start a game.");
					String matchUser = messageInput.readLine().trim();
					
					if (matchUser.startsWith("/exit"))
						break;
					
					// Check if the user exists and prompt to match if so
					for (int i = 0; i < maxPlayersOnServer; i++) {
						ClientThread matchThread = allThreads[i];
						if (matchThread != null && matchThread.clientName != null && matchThread.clientName.equals(matchUser)) {
							matchThread.setPrompted(true);
							this.setPrompted(true);
							messageOutput.println("Waiting for " + matchUser + " to respond...");
							matchThread.messageOutput.println(clientName + " wants to play with you. Enter Y to accept, anything else to reject.");
							String otherUserInput = matchThread.messageInput.readLine().trim();
							
							// Check if the other user confirms the match
							if (otherUserInput.startsWith("Y")) {
								this.matchName = matchUser;
								matchThread.setMatchName(this.clientName);
								messageOutput.println("You just matched with " + this.matchName);
								matchThread.messageOutput.println("You just matched with " + this.clientName);
								matchThread.messageOutput.println("You can now type a message to send.");
								
								// Initialize the game handler
								this.currentGame = new GameHandler();
								matchThread.setGameHandler(this.currentGame);
								this.playerID = 0;
//								NewTurnMessage[] messages = currentGame.doAction(new ActionMessage(ActionType.Place, 0, 0, 0), 0);
								ArrayList<Integer> myArray = new ArrayList<>(Arrays.asList(15, 20, 30));
								this.objectOutput.writeObject(myArray);
								matchThread.objectOutput.writeObject(myArray);
//								this.objectOutput.writeObject(messages[0]);
//								matchThread.objectOutput.writeObject(messages[1]);
								
								while (this.currentGame.checkWin() == GameConstants.WinState.Playing) {
									// continue passing on messages
									NewTurnMessage[] messages = currentGame.doAction(new ActionMessage(GameConstants.ActionType.Place, 0, 0, 0), this.playerID);
								}
								break;
							} 
							
						}
					}
					
				} // end of match loop
				

			}

			// Start the connection and maintain it unless the user exits
			while (true) {

				String line = messageInput.readLine();
	
				// Check if the user wants to exit
				if (line.startsWith("/exit")) {
					break;
	
				} else {
	
					// Send the message between matched users!
					synchronized (this) {
						for (int i = 0; i < maxPlayersOnServer; i++) {
							if (this.getClientName() != null && allThreads[i] != null &&
									allThreads[i].getClientName() != null && allThreads[i].getMatchName() != null) {
								if (allThreads[i].getMatchName().equals(this.getClientName()) ||
										allThreads[i].getClientName().equals(this.getClientName()))
									allThreads[i].messageOutput.println("<" + userName + "> " + line);
							}
						}
					}
						
				}
				
			} // end of connection

			// Check any client left the game and notify all other clients 
			synchronized (this) {
				for (int i = 0; i < maxPlayersOnServer; i++) {
					if (allThreads[i] != null && allThreads[i] != this
							&& allThreads[i].clientName != null) {
						allThreads[i].messageOutput.println(userName + " left the game!");
					}
				}
			}
			messageOutput.println("Goodbye " + userName + "!");

			// Clean up this thread to open up a space for new players on the server
			synchronized (this) {
				for (int i = 0; i < maxPlayersOnServer; i++) {
					if (allThreads[i] == this) {
						allThreads[i] = null;
					}
				}
			}

			// Close the socket and i/o streams
			messageInput.close();
			messageOutput.close();
			clientSocket.close();

		} catch (IOException e) {
			System.out.println(e);
		}

	}
	
	public void setGameHandler(GameHandler g) {
		this.currentGame = g;
	}
	
	public void setPrompted(boolean v) {
		this.prompted = v;
	}
	
	public String getClientName() {
		return this.clientName;
	}
	
	public String getMatchName() {
		return this.matchName;
	}
	
	public void setMatchName(String user) {
		this.matchName = user;
	}
}