package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import logic.GameHandler;
import messageprotocol.ActionMessage;
import messageprotocol.NewTurnMessage;
import common.GameConstants;

public class ClientThread extends Thread {

	private Socket clientSocket = null;
	private String clientName = null;
	private String matchName = null;
	private GameHandler currentGame = null;
	private boolean prompted = false;
	private BufferedReader input = null;
	private PrintStream output = null;
	private final ClientThread[] allThreads;
	private int playerID = 1;
	private int gameID;
	private ClientThread matchedThread = null;
	private int maxPlayersOnServer;

	public ClientThread(Socket clientSocket, ClientThread[] threads) {
		this.clientSocket = clientSocket;
		this.allThreads = threads;
		maxPlayersOnServer = threads.length;
	}

	public void run() {
		int maxPlayersOnServer = this.maxPlayersOnServer;
		ClientThread[] allThreads = this.allThreads;

		try {

			// Create I/O streams and set up the user
			input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			output = new PrintStream(clientSocket.getOutputStream());
			String userName;
			output.println("Enter your user name: ");
			userName = input.readLine().trim();

			// New client enters the game
			output.println("Welcome " + userName + "!\nType /exit to quit game.");

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
					output.println("You are the only online player. Wait for others to join!");
				} else {
					output.println("Online players:");
					for (String p: onlinePlayers)
						output.println(p);
				}

				// Let the unmatched user know about the new player
				for (int i = 0; i < maxPlayersOnServer; i++) {
					if (allThreads[i] != null && allThreads[i] != this && this.matchName == null) {
						allThreads[i].output.println(userName + " is now online!");
					}
				}

				// Match players
				while (this.matchName == null && this.prompted == false) {
					output.println("Type one of the usernames to start a game.");
					String matchUser = input.readLine().trim();

					if (matchUser.startsWith("/exit"))
						break;

					// Check if the user exists and prompt to match if so
					for (int i = 0; i < maxPlayersOnServer; i++) {
						ClientThread current = allThreads[i];
						if (current != null && current.clientName != null && current.clientName.equals(matchUser)) {
							this.matchedThread = current;
							this.matchedThread.setPrompted(true);
							this.setPrompted(true);
							output.println("Waiting for " + matchUser + " to respond...");
							this.matchedThread.output.println(clientName + " wants to play with you. Enter Y to accept, anything else to reject.");
							this.matchedThread.output.flush();
							String otherUserInput = this.matchedThread.input.readLine().trim();
							// Check if the other user confirms the match
							if (otherUserInput.startsWith("Y")) {
								this.matchName = matchUser;
								this.matchedThread.setMatchName(this.clientName);
								this.matchedThread.matchedThread = this;
								output.println("//connected");
								this.matchedThread.output.println("//connected");
								output.println("Accepted! You just matched with " + this.matchName);
								this.matchedThread.output.println("You just matched with " + this.clientName);
								this.matchedThread.output.println("You can now type a message to send.");
								
//								System.out.println("I requested the match and I'm player #" + this.playerID + " and I see it!");
								// Initialize the game handler
								this.currentGame = new GameHandler();
								this.matchedThread.setGameHandler(this.currentGame);
								this.playerID = 0;
								
								// Set up the database
//								Database db = new Database();
//								this.gameID = db.createGame(uid1, uid2, true);
								
								// Trigger a fake ActionMessage to start the game
				                ActionMessage trigger = new ActionMessage(GameConstants.ActionType.Initialize, 0, 0, 0);
				                NewTurnMessage[] replies = this.currentGame.doAction(trigger, this.playerID);
				                String triggerForP0 = "@" + ObjectConverter.objectToString(replies[0]);
				                String triggerForP1 = "@" + ObjectConverter.objectToString(replies[1]);
				                this.output.println(triggerForP0);
				                this.matchedThread.output.println(triggerForP1);

								break;
							} 

						}
					}

				} // end of match loop


			}

			// Start the connection and maintain it unless the user exits
			while (true) {

				String line = input.readLine();

				// Check if the user wants to exit
				if (line.startsWith("/exit")) {
					break;

				} else if (line.startsWith("@")) {
					// This is where we process game messages
//					System.out.println("I'm player #" + this.playerID + " my client has just sent an ActionMessage!");
					synchronized(this) {
						if (this.currentGame != null && this.currentGame.checkWin() == GameConstants.WinState.Playing) {
							// Receive ActionMessage
							ActionMessage received = (ActionMessage) ObjectConverter.stringtoObject(line.substring(1));
							NewTurnMessage[] messages = this.currentGame.doAction(received, this.playerID);
							
							String messageToPlayer0 = "@" + ObjectConverter.objectToString(messages[0]);
							String messageToPlayer1 = "@" + ObjectConverter.objectToString(messages[1]);

							// Pass the NewTurnMessages to both clients
							if (this.playerID == 0) {
								this.output.println(messageToPlayer0);
							} else {
								this.output.println(messageToPlayer1);
							}
							
							if (this.playerID == 0 && this.matchedThread != null) {
								this.matchedThread.output.println(messageToPlayer1);
							} else {
								this.matchedThread.output.println(messageToPlayer0);
							}


						}
					}


				} else {

					// Send a chat message between matched users
					synchronized (this) {
						for (int i = 0; i < maxPlayersOnServer; i++) {
							if (this.getClientName() != null && allThreads[i] != null &&
									allThreads[i].getClientName() != null && allThreads[i].getMatchName() != null) {
								if (allThreads[i].getMatchName().equals(this.getClientName()) ||
										allThreads[i].getClientName().equals(this.getClientName()))
									allThreads[i].output.println("<" + userName + "> " + line);
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
						allThreads[i].output.println(userName + " left the game!");
					}
				}
			}
			output.println("Goodbye " + userName + "!");

			// Clean up this thread to open up a space for new players on the server
			synchronized (this) {
				for (int i = 0; i < maxPlayersOnServer; i++) {
					if (allThreads[i] == this) {
						allThreads[i] = null;
					}
				}
			}

			// Close the socket and i/o streams
			input.close();
			output.close();
			clientSocket.close();

		} catch (IOException | ClassNotFoundException e) {
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